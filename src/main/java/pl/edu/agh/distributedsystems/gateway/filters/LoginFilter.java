package pl.edu.agh.distributedsystems.gateway.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.util.StreamUtils;
import pl.edu.agh.distributedsystems.gateway.security.JwtCreator;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class LoginFilter extends ZuulFilter {

    private JwtCreator jwtCreator;

    public LoginFilter(JwtCreator jwtCreator) {
        this.jwtCreator = jwtCreator;
    }

    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.SEND_RESPONSE_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext context = RequestContext.getCurrentContext();

        return context.get("proxy").equals("login") && context.getResponseStatusCode() == 200;
    }

    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        try {
            InputStream responseDataStream = context.getResponseDataStream();
            String responseAsString = StreamUtils.copyToString(responseDataStream, Charset.forName("UTF-8"));
            String jwt = jwtCreator.createJwt(responseAsString);
            context.addZuulResponseHeader("x-auth-token", jwt);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
