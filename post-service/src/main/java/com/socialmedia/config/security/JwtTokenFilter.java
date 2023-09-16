package com.socialmedia.config.security;

import com.socialmedia.utility.JwtTokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {


    private final JwtTokenManager jwtTokenManager;
    private final JwtUserDetails jwtUserDetails;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        System.out.println(request.getRequestURL());

        String authorizationHeader = request.getHeader("Authorization");
        System.out.println( "authHeade ==>>"+authorizationHeader);


        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")
//                && SecurityContextHolder.getContext().getAuthentication()==null
        ) {
            String token = authorizationHeader.substring(7);
            Optional<String> role = jwtTokenManager.getRoleFromToken(token);
            UserDetails userDetails = null;
            System.out.println(token);

            if (role.isPresent()) {
                userDetails = jwtUserDetails.loadUserByUserRole(role.get());
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            }
            System.out.println("User ==>>" + userDetails);
        }

        System.out.println(SecurityContextHolder.getContext().getAuthentication());

        filterChain.doFilter(request, response);
    }
}
