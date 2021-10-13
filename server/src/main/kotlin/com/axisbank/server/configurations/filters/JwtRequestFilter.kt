package com.axisbank.server.configurations.filters

import com.axisbank.server.service.DefaultUserDetailService
import com.axisbank.server.utils.JwtUtil
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*
import java.util.stream.Collectors.toList
import javax.servlet.FilterChain
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtRequestFilter(
    val defaultUserDetailService: DefaultUserDetailService,
) : OncePerRequestFilter() {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val authorizationHeader = request.getHeader("Authorization")

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            println(authorizationHeader)
            val jwt = authorizationHeader.substring(7)

            val userName = JwtUtil.extractUsername(jwt)
            val claims = JwtUtil.extractAllClaims(jwt)

            if (claims["authorities"] != null && SecurityContextHolder.getContext().authentication == null) {
                val userDetails = defaultUserDetailService.loadUserByUsername(userName)
                println(userDetails.authorities)
                if (JwtUtil.validateToken(jwt, userDetails)) {
                    val userNamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.authorities
                    )
                    userNamePasswordAuthenticationToken
                        .details = WebAuthenticationDetailsSource().buildDetails(request)
                    SecurityContextHolder.getContext().authentication = userNamePasswordAuthenticationToken
                    response.addHeader("Authorization", jwt)
                }
            }
        }
        chain.doFilter(request, response)
    }
}