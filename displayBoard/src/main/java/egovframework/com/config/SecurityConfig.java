package egovframework.com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import egovframework.com.jwt.config.EgovJwtTokenUtil;
import lombok.RequiredArgsConstructor;
import egovframework.com.jwt.config.JwtAuthenticationEntryPoint;
import egovframework.com.jwt.config.JwtSecurityConfig;
import egovframework.com.jwt.config.JwtAccessDeniedHandler;
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

	private final EgovJwtTokenUtil jwtTokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

   

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()  // 비인증시 login form redirect X (rest api)
                .csrf().disable()       // crsf 보안 X (rest api)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // jwt token으로 인증 > 세션 필요없음

                .and()
                .authorizeRequests()    // 다음 리퀘스트에 대한 사용권한 체크
                .antMatchers("/**").permitAll() // 모든 주소 허용
//                .requestMatchers("/api/users/login", "/api/users/signup").permitAll() // 허용된 주소
                .anyRequest().authenticated() // Authentication 필요한 주소
                .and()                  // exception handling for jwt
                .exceptionHandling()
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .authenticationEntryPoint(jwtAuthenticationEntryPoint);
        // jwt 적용
        http.apply(new JwtSecurityConfig(jwtTokenProvider));
        return http.build();
    }
}
