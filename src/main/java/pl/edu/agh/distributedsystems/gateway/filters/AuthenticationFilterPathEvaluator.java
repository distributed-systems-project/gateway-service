package pl.edu.agh.distributedsystems.gateway.filters;

import javax.servlet.http.HttpServletRequest;

public class AuthenticationFilterPathEvaluator {

    boolean shouldFilter(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String requestMethod = request.getMethod();

        if (isLoginPath(uri)) {
            return false;
        }

        if (isCreateClientReservationEndpoint(uri, requestMethod)) {
            return false;
        }

        if (isGetReservationDetailsByNumber(uri, requestMethod)) {
            return false;
        }

        return true;
    }

    private boolean isLoginPath(String uri) {
        return "/login".equals(uri) || "/customer/login".equals(uri);
    }

    private boolean isCreateClientReservationEndpoint(String uri, String requestMethod) {
        return "/reservation/reservations".equals(uri) && "POST".equalsIgnoreCase(requestMethod);
    }

    private boolean isGetReservationDetailsByNumber(String uri, String requestMethod) {
        return uri.contains("/reservation/reservations/client") && "GET".equalsIgnoreCase(requestMethod);
    }
}
