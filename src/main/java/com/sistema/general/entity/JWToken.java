package com.sistema.general.entity;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.filter.GenericFilterBean;

import com.sistema.general.table.Usuarios;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

 

public class JWToken extends GenericFilterBean {
	
	public static final String API_SECRET_KEY = "systemgeneralapikey";
	
	public static final long TOKEN_VALIDITY = 2 * 60 * 60 * 1000;
	
	public JWToken() {
		
	}
	
	public String generarJWToken(Usuarios usuarios) {
        long timestamp = System.currentTimeMillis();
        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, API_SECRET_KEY)
						        	 .setIssuedAt(new Date(timestamp))
						        	 .setExpiration(new Date(timestamp + TOKEN_VALIDITY))
						        	 .claim("usuarioId", usuarios.getUsuarioId())
						        	 .claim("email", usuarios.getEmail())
						        	 .claim("nombre", usuarios.getNombre())
						        	 .claim("apellidoPaterno", usuarios.getApellidoPaterno())
						        	 .claim("apellidoMaterno", usuarios.getApellidoMaterno())
						        	 .compact(); 
        return token;
    }

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		String authHeader = httpRequest.getHeader("Authorization");
		
		if (authHeader != null) {
			String[] authHeaderarr = authHeader.split("Bearer ");
			if(authHeaderarr.length > 1 && authHeaderarr[1] != null) {
				String token = authHeaderarr[1];
				try {
					Claims claims = Jwts.parser().setSigningKey(API_SECRET_KEY).parseClaimsJws(token).getBody();
					httpRequest.setAttribute("usuarioId", Long.parseLong(claims.get("usuarioId").toString()));
					
					
				} catch(Exception e) {
					httpResponse.sendError(HttpStatus.FORBIDDEN.value(), "Token invalida o expirada.");
					return;
				}
			} else {
				httpResponse.sendError(HttpStatus.FORBIDDEN.value(), "Necesita proporcionar el token de autenticación Bearer.");
				return;
			}
		}  else {
			httpResponse.sendError(HttpStatus.FORBIDDEN.value(), "Necesita proporcionar el token de autenticación.");
			return;
		}
		chain.doFilter(httpRequest, httpResponse);
	}
	

}
