package de.esports.aeq.admins.security.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger LOG = LoggerFactory.getLogger(JwtTokenAuthenticationFilter.class);

    private JwtConfig config;

    public JwtTokenAuthenticationFilter(JwtConfig config) {
        this.config = config;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain chain) throws ServletException, IOException {
        String header = request.getHeader(config.getHeader());

        if (header == null || !header.startsWith(config.getPrefix())) {
            chain.doFilter(request, response);
            return;
        }

        String token = header.replace(config.getPrefix(), "");

        Claims claims = Jwts.parser()
                .setSigningKey(config.getSecret().getBytes())
                .parseClaimsJws(token)
                .getBody();

        try {
            String username = claims.getSubject();
            if (username != null) {
                @SuppressWarnings("unchecked")
                List<String> authorities = (List<String>) claims.get("authorities");

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        username, null, authorities.stream().map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList()));

                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (Exception e) {
            LOG.error("Cannot authenticate user {} with token {}", claims.getSubject(), token, e);
            SecurityContextHolder.clearContext();
        }

        chain.doFilter(request, response);
    }
}
