package pl.edu.agh.distributedsystems.gateway.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.io.IOException;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;

public class JwtCreator {

    private final ObjectMapper mapper = new ObjectMapper();

    public String createJwt(String principalString) throws IOException {
        Instant now = Instant.now();
        EmployeePrincipal principal = mapper.readValue(principalString, EmployeePrincipal.class);

        return Jwts.builder()
                .setSubject(principal.getEmployeeId())
                .claim("authority", principal.getPosition())
                .claim("hotelId", principal.getHotelId())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(JwtAuthenticationConfig.getExpiration())))
                .signWith(SignatureAlgorithm.HS256, JwtAuthenticationConfig.getSecret().getBytes())
                .compact();
    }
}
