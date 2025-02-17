package com.bookstar.bookingservice.configuration.security;

import com.bookstar.bookingservice.configuration.context.UserContext;
import com.bookstar.bookingservice.configuration.exception.UnauthorizedException;
import com.bookstar.bookingservice.configuration.security.dto.Token;
import com.bookstar.bookingservice.model.User;
import com.bookstar.bookingservice.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

@Slf4j
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

    private final UserRepository userRepository;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager,
                                  UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(HEADER_STRING);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = null;
        try {
            authentication = getAuthentication(req);
        } catch (Exception e) {
            log.warn("Auth error: " + e.getMessage());
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    public UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken = null;
        String token = request.getHeader(HEADER_STRING);

        if (token != null) {
            token = token.replace(TOKEN_PREFIX, "");

            Token decodedToken = JWTUtil.decodeToken(token);

            if (decodedToken == null || (new Date(decodedToken.getExp() * 1000).before(new Date()))) {
                throw new UnauthorizedException();
            }

            Optional<User> optionalUser = userRepository.findByEmail(decodedToken.getIss());

            if (optionalUser.isEmpty()) {
                throw new UnauthorizedException();
            }

            User foundUser = optionalUser.get();

            UserContext.getInstance().setUser(foundUser);

            authenticationToken = new UsernamePasswordAuthenticationToken(foundUser.getEmail(), null, null);
        }

        return authenticationToken;
    }
}
