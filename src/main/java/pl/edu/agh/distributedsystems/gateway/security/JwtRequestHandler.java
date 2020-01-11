package pl.edu.agh.distributedsystems.gateway.security;

import javax.servlet.http.HttpServletRequest;

public class JwtRequestHandler {

    public boolean isAuthorized(HttpServletRequest request, EmployeePrincipal principal) {
        String uri = request.getRequestURI();
        String requestMethod = request.getMethod();
        String authorities = principal.getPosition();

        if (uri.contains("/building") && isWriteOrModifyMethod(requestMethod) && !isManager(authorities)) {
            return false;
        }

        if (uri.contains("/employee") && !isManager(authorities)) {
            return false;
        }

        return true;
    }

    private boolean isManager(String authorities) {
        return "Manager".equalsIgnoreCase(authorities);
    }

    private boolean isWriteOrModifyMethod(String requestMethod) {
        return "POST".equalsIgnoreCase(requestMethod) ||
                "PUT".equalsIgnoreCase(requestMethod) ||
                "PATCH".equalsIgnoreCase(requestMethod) ||
                "DELETE".equalsIgnoreCase(requestMethod);
    }
}
