package pl.edu.agh.distributedsystems.gateway.filters;

import com.netflix.zuul.context.RequestContext;
import pl.edu.agh.distributedsystems.gateway.security.JwtCreator;

public class EmployeeLoginFilter extends LoginFilter {

    public EmployeeLoginFilter(JwtCreator jwtCreator) {
        super(jwtCreator);
    }

    @Override
    public boolean shouldFilter() {
        RequestContext context = RequestContext.getCurrentContext();

        return context.get("proxy").equals("login") && context.getResponseStatusCode() == 200;
    }
}
