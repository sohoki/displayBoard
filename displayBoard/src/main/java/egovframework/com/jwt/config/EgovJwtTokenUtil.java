package egovframework.com.jwt.config;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.egovframe.rte.fdl.cmmn.exception.BaseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.display.backoffice.util.service.RedisUtil;

import egovframework.com.cmm.AdminLoginVO;
import egovframework.com.cmm.LoginVO;
import egovframework.com.exception.dto.ErrorCode;
import egovframework.let.uat.uia.service.EgovLoginService;
import egovframework.let.utl.fcc.service.EgovStringUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//security 관련 제외한 jwt util 클래스
@RequiredArgsConstructor
@Slf4j
@Component
public class EgovJwtTokenUtil implements Serializable{

	private final RedisTemplate<String, String> redisTemplate;
	
	private final RedisUtil redis;
	
    @Value("${token.secret}")
    private String secretKey;

    @Value("${token.expiration_time}")
    private long JWT_TOKEN_VALIDITY;

    @Value("${token.refresh_time}")
    private long TOKEN_REFRESH_TIME;
	
	@Value("egovframe")
    private String secret;
	
	/** EgovLoginService */
	@Resource(name = "loginService")
	private EgovLoginService loginService;
	
	public boolean isRefreshToken(String refreshKey) {
		
		return  redis.isKeyNullCheck(refreshKey);
    }
	
	//retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }
    
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
	
    //for retrieveing any information from token we will need the secret key
    public Claims getAllClaimsFromToken(String token) {
    	System.out.println("===>>> secret = "+secret);
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }
    
    //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
    
    //generate token for user
    public String generateToken(LoginVO loginVO) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, loginVO.getUserSe()+loginVO.getId());
    }
    //generate token for admin
    public String generateAdminToken(AdminLoginVO loginVO) {
        Map<String, Object> claims = new HashMap<>();
        //추후 변경 예정 
        claims.put("partId" , loginVO.getPartId());
        claims.put("roleGubun" , loginVO.getRoleGubun());
        claims.put("secret" , loginVO.getRoleId());
        //권한 체크 
        /*
        for (UserRoleInfo info : loginVO.getRoleInfo()) {
        	//claims.put(Globals.TOKEN_CLAIM_NAME , info.getRoleId());
        	claims.put(secret , info.getRoleId());
        }
        */
        return doGenerateToken(claims, loginVO.getAdminName()+"|"+loginVO.getAdminId());
    }

    public String generateToken(LoginVO loginVO, Map<String, Object> claims) {
        return doGenerateToken(claims, loginVO.getUserSe()+loginVO.getId());
    }
    public String generateAdminToken(AdminLoginVO loginVO, Map<String, Object> claims) {
        //return doGenerateToken(claims, loginVO.getUserSe()+loginVO.getId());
    	return doGenerateToken(claims, loginVO.getAdminName()+"|"+loginVO.getAdminId());
    }
    //refreshToken 생성 나중에 spring spring secur 생성
    public String generateRefreshToken(AdminLoginVO loginVO) {
    	
    	//token 있는지 확인 후 있으면 삭제 하 다시 전송
    	redis.keyDelete(loginVO.getAdminId());
    	
    	//Claims claims = Jwts.claims().setSubject(loginVO.getAdminId());
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + TOKEN_REFRESH_TIME);
         
    	String refreshToken = Jwts.builder()
    			.setExpiration(expireDate)
    			.setSubject(loginVO.getAdminId())
    			.signWith(SignatureAlgorithm.HS512, secret )
    			.compact();
    	
    	redisTemplate.opsForValue().set(
    			loginVO.getAdminId(),
                refreshToken,
                expireDate.getTime(),
                TimeUnit.MILLISECONDS
        );
    	return refreshToken;
    }
	//while creating the token -
	//1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
	//2. Sign the JWT using the HS512 algorithm and secret key.
	//3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
	//   compaction of the JWT to a URL-safe string
    private String doGenerateToken(Map<String, Object> claims, String subject) {
    	System.out.println("===>>> secret = "+secret);
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
            .signWith(SignatureAlgorithm.HS512, secret).compact();
    }
    
    //validate token
    public Boolean validateToken(String token, LoginVO loginVO) {
        final String username = getUsernameFromToken(token);
        return (username.equals(loginVO.getUserSe()+loginVO.getId()) && !isTokenExpired(token));
    }
    //validate Admin token
    public Boolean validateAdminToken(String token, AdminLoginVO loginVO) {
    	
        final String username = getUsernameFromToken(token);
        return (username.equals(loginVO.getAdminId()) && !isTokenExpired(token));
    }
    //validate token
    public Boolean validateToken(String token) throws BaseException {
    	try{
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch(ExpiredJwtException e) {
            log.error(ErrorCode.EXPIRED_JWT.getMessage() );
            throw new BaseException(ErrorCode.EXPIRED_JWT.getMessage());
        } catch(JwtException e) {
            log.error(ErrorCode.INVALID_JWT.getMessage());
            throw new BaseException(ErrorCode.INVALID_JWT.getMessage());
        }
    }
    
    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
    /**
     * 토큰으로부터 클레임을 만들고, 이를 통해 User 객체 생성해 Authentication 객체 반환
     * @throws Exception 
    */
    public Authentication getAuthentication(String token) throws Exception {
        String userPrincipal = Jwts.parser().
                setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody().getSubject();
        
        //LoginVO resultVO = loginService.actionLogin(null) .actionLogin(userPrincipal );// .actionLogin(loginVO);
		
        //UserDetails userDetails = userDetailsService.loadUserByUsername(userPrincipal);
        
       
        
        return new UsernamePasswordAuthenticationToken(userPrincipal, "USER_PASSWORD"); 
    }
  //blacklist 등록 하기 
    public Boolean deleteRefreshToken(HttpServletRequest request ) {
    	
    	
    	String accessToken, refreshToken;
    	
    	accessToken = EgovStringUtil.isNullToString(request.getHeader("authorization").replace("Bearer", ""));
    	refreshToken = EgovStringUtil.isNullToString(request.getHeader("refreshToken"));
    	Boolean deleteRefresh = true;
    	if (!refreshToken.isEmpty()) {
	    	try {
    			String id = getUsernameFromToken(refreshToken);
    			redis.keyDelete(id);
	    	}catch(Exception e) {
	    		log.error("delete refresh token error:" + e.toString());
	    		deleteRefresh = false;
	    	}
    	}
    	if (!accessToken.isEmpty()) {
    		try {
        		String username = getUsernameFromToken(accessToken);
        		redisTemplate.opsForValue().set(accessToken, 
        										"access_token", 
        										getClaimFromToken(accessToken, Claims::getExpiration).getTime());
    		} catch (IllegalArgumentException e) {
    			log.debug("delete Unable to get JWT Token");
    		} catch (ExpiredJwtException e) {
    			log.debug("delete JWT Token has expired");
    		} catch (MalformedJwtException e) {
    		    	log.debug("delete JWT strings must contain exactly 2 period characters");
    		} catch (UnsupportedJwtException e) {
    		    	log.debug("delete not support JWT token.");
    		}
    	}
    	return deleteRefresh;
    }

}
