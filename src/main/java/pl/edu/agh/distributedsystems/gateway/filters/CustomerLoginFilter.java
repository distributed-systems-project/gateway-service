package pl.edu.agh.distributedsystems.gateway.filters;

import com.netflix.zuul.context.RequestContext;
import pl.edu.agh.distributedsystems.gateway.security.JwtCreator;

public class CustomerLoginFilter extends LoginFilter {

    public CustomerLoginFilter(JwtCreator jwtCreator) {
        super(jwtCreator);
    }

    @Override
    public boolean shouldFilter() {
        RequestContext context = RequestContext.getCurrentContext();

        return context.get("proxy").equals("customer/login") && context.getResponseStatusCode() == 200;
    }
}
