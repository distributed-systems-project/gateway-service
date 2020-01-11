package pl.edu.agh.distributedsystems.gateway.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import pl.edu.agh.distributedsystems.gateway.security.EmployeePrincipal;
import pl.edu.agh.distributedsystems.gateway.security.JwtRequestHandler;
import pl.edu.agh.distributedsystems.gateway.security.JwtValidator;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

public class AuthenticationFilter extends ZuulFilter {

    private JwtValidator jwtValidator;
    private JwtRequestHandler requestHandler;
    private AuthenticationFilterPathEvaluator filterPathEvaluator;

    public AuthenticationFilter(JwtValidator jwtValidator,
                                JwtRequestHandler requestHandler,
                                AuthenticationFilterPathEvaluator filterPathEvaluator) {
        this.jwtValidator = jwtValidator;
        this.requestHandler = requestHandler;
        this.filterPathEvaluator = filterPathEvaluator;
    }

    @Override
    public int filterOrder() {
        return PRE_DECORATION_FILTER_ORDER - 1;
    }

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();

        return filterPathEvaluator.shouldFilter(request);
    }

    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && !authHeader.isEmpty()) {
            try {
                EmployeePrincipal principal = jwtValidator.authenticate(authHeader);
                requestHandler.handleRequest(request, principal);
            } catch (Exception e) {
                rejectRequest(context, e.getMessage());
            }
        } else {
            rejectRequest(context, "Missing authentication header");
        }

        return null;
    }

    private void rejectRequest(RequestContext context, String message) {
        context.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
        context.setResponseBody("{\"message\": \"" + message + "\"}");
        context.setSendZuulResponse(false);
    }
}
