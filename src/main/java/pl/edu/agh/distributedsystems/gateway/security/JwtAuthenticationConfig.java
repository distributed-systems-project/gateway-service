package pl.edu.agh.distributedsystems.gateway.security;

import org.springframework.http.HttpHeaders;

class JwtAuthenticationConfig {

    private static String url = "/login";

    private static String header = HttpHeaders.AUTHORIZATION;

    private static String prefix = "Bearer";

    private static int expiration = 60 * 60; // default 1 hour

    private static String secret = "secret";

    public static String getUrl() {
        return url;
    }

    public static String getHeader() {
        return header;
    }

    public static String getPrefix() {
        return prefix;
    }

    public static int getExpiration() {
        return expiration;
    }

    public static String getSecret() {
        return secret;
    }
}
