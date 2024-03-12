package egovframework.com.cmm.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import egovframework.com.cmm.filter.JwtFilter.ErrorResponse;
import egovframework.com.exception.dto.ErrorCode;
import egovframework.com.exception.dto.IErrorCode;
import egovframework.com.jwt.config.EgovJwtTokenUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtFilter extends OncePerRequestFilter{

	
	private EgovJwtTokenUtil jwtTokenProvider;

    public JwtFilter(EgovJwtTokenUtil jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
    								HttpServletResponse response,
    								FilterChain filterChain) throws ServletException, IOException {
    	
    	
    	
        String token = jwtTokenProvider.resolveToken(request);
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String requestURI = httpServletRequest.getRequestURI();
        try {
            if (token != null && jwtTokenProvider.validateToken(token)) {
                Authentication auth = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(auth); // 정상 토큰이면 SecurityContext에 저장
            }
        } catch (RedisConnectionFailureException e) {
            SecurityContextHolder.clearContext();
            log.error(ErrorCode.REDIS_ERROR.getMessage() );
            //throw new Exception(ErrorCode.REDIS_ERROR.getMessage());
            //throw new Exception(); 
        } catch (Exception e) {
        	log.error(ErrorCode.INVALID_JWT.getMessage() );
        	//throw new setErrorResponse(ErrorCode.INVALID_JWT.getMessage());
        }

        filterChain.doFilter(request, response);
    }
    private void setErrorResponse(HttpServletResponse response,
    							  IErrorCode errorCode){
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(errorCode.getHttpStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ErrorResponse errorResponse = new ErrorResponse(500, errorCode.getMessage());
        try{
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    @Data
    public static class ErrorResponse{
        private final Integer code;
        private final String message;
    }
}
