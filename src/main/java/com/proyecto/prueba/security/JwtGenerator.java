package com.proyecto.prueba.security;

import com.proyecto.prueba.Exceptions.ExceptionsClass;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtGenerator {


        public String generarToken(Authentication authentication) {

            String username = authentication.getName();
            Date tiempoActual = new Date();
            Date expiracionToken = new Date(tiempoActual.getTime() + SecurityConsts.JWT_EXPIRATION_TOKEN);

            String token = Jwts.builder()
                    .setSubject(username)
                    .setIssuedAt(new Date())
                    .setExpiration(expiracionToken)
                    .signWith(SignatureAlgorithm.HS512, SecurityConsts.JWT_FIRMA)
                    .compact();
            return token;
        }

        public String obtenerUsernameDeJwt(String token) {
            Claims claims = Jwts.parser()
                    .setSigningKey(SecurityConsts.JWT_FIRMA)
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        }

        public Boolean validarToken(String token) {
            try {
                Jwts.parser().setSigningKey(SecurityConsts.JWT_FIRMA).parseClaimsJws(token);
                return true;
            } catch (Exception e) {
                throw new ExceptionsClass("El token JWT ha expirado o esta incorrecto");
            }
        }


}
