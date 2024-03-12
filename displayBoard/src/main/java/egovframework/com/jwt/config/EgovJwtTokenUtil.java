package egovframework.com.jwt.config;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.egovframe.rte.fdl.cmmn.exception.BaseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import egovframework.com.cmm.LoginVO;
import egovframework.com.exception.dto.ErrorCode;
import egovframework.let.uat.uia.service.EgovLoginService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

//security 관련 제외한 jwt util 클래스
@Slf4j
@Component
public class EgovJwtTokenUtil implements Serializable{

	private static final long serialVersionUID = -5180902194184255251L;
	//public static final long JWT_TOKEN_VALIDITY = 24 * 60 * 60; //하루
	
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

    public String generateToken(LoginVO loginVO, Map<String, Object> claims) {
        return doGenerateToken(claims, loginVO.getUserSe()+loginVO.getId());
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
    

}
