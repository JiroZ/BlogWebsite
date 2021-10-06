package com.axisbank.server.filters

import com.axisbank.server.service.DefaultUserDetailService
import com.axisbank.server.utils.JwtUtil
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtRequestFilter(
    val defaultUserDetailService: DefaultUserDetailService,
) : OncePerRequestFilter() {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val authorizationHeader = request.getHeader("Authorization")
        var userName: String? = null;
        var jwt: String? = null

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7)
            userName = JwtUtil.extractUsername(jwt)
        }

        if(userName != null && SecurityContextHolder.getContext().authentication != null) {
            val userDetails = defaultUserDetailService.loadUserByUsername(userName)
            if(JwtUtil.validateToken(jwt, userDetails)) {
                val userNamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.authorities
                )
                userNamePasswordAuthenticationToken
                    .details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = userNamePasswordAuthenticationToken

            }
        }
        chain.doFilter(request, response)
    }
}