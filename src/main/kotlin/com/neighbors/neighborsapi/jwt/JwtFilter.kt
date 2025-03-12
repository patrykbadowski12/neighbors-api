package com.neighbors.neighborsapi.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.web.filter.GenericFilterBean

class JwtFilter(
    private val jwtService: JwtService,
) : GenericFilterBean() {
    override fun doFilter(
        req: ServletRequest,
        res: ServletResponse,
        chain: FilterChain,
    ) {
        val request = req as HttpServletRequest
        val authHeader = request.getHeader("Authorization")

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            val token = authHeader.substring(7)

            if (jwtService.isTokenBlacklisted(token)) {
                throw RuntimeException("JWT token $token is blacklisted")
            }

            if (!JwtUtil.validateJwt(token)) {
                throw RuntimeException("Not valid token JWT")
            }

            val username = JwtUtil.getUsername(token)
            val roles = JwtUtil.getRoles(token)
            val authorities = roles.map { SimpleGrantedAuthority(it) }
            val auth = UsernamePasswordAuthenticationToken(User(username, "", authorities), null, authorities)
            SecurityContextHolder.getContext().authentication = auth
        }
        chain.doFilter(req, res)
    }
}
