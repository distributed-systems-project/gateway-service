package pl.edu.agh.distributedsystems.gateway.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

public class JwtValidator {

    public EmployeePrincipal authenticate(String authHeader) {
        if (authHeader.startsWith(JwtAuthenticationConfig.getPrefix() + " ")) {
            String token = authHeader.substring(6);
            return getPrincipal(token);
        }
        throw new JwtException("{\"message\": \"Invalid authentication header. Missing Bearer prefix.\"}");
    }

    private EmployeePrincipal getPrincipal(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JwtAuthenticationConfig.getSecret().getBytes())
                .parseClaimsJws(token)
                .getBody();

        EmployeePrincipal principal = new EmployeePrincipal();
        principal.setEmployeeId(claims.getSubject());
        principal.setHotelId(claims.get("hotelId", Long.class));
        principal.setPosition(claims.get("authority", String.class));

        return principal;
    }
}
