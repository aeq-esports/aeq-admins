package de.esports.aeq.admins.security.impl;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@Profile("!dev")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtConfig jwtConfig;
    private final UserDetailsService service;
    private final PasswordEncoder encoder;

    @Autowired
    public WebSecurityConfig(JwtConfig jwtConfig, UserDetailsService service,
        PasswordEncoder encoder) {
        this.jwtConfig = jwtConfig;
        this.service = service;
        this.encoder = encoder;
    }

    @Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/").permitAll();
        http
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .exceptionHandling()
            .authenticationEntryPoint(
                (req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
            .and()
            .addFilter(new JwtUsernamePasswordAuthenticationFilter(authenticationManager(),
                jwtConfig))
            .addFilterAfter(new JwtTokenAuthenticationFilter(jwtConfig),
                UsernamePasswordAuthenticationFilter.class)
            .authorizeRequests()
            .antMatchers(HttpMethod.POST, jwtConfig.getUri()).permitAll()
            .antMatchers(HttpMethod.POST, "/users").permitAll()
            .antMatchers("/app/**").permitAll()
            .antMatchers("/lib/**").permitAll()
            .antMatchers("/api/**").permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(service).passwordEncoder(encoder);
    }
}
