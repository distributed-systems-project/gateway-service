package pl.edu.agh.distributedsystems.gateway.security;

import javax.servlet.http.HttpServletRequest;

public class JwtRequestHandler {

    public void handleRequest(HttpServletRequest request, EmployeePrincipal principal) {
        System.out.println(principal);
    }
}
