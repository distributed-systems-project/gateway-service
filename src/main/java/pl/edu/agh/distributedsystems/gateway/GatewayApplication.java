package pl.edu.agh.distributedsystems.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import pl.edu.agh.distributedsystems.gateway.filters.AuthenticationFilter;
import pl.edu.agh.distributedsystems.gateway.filters.AuthenticationFilterPathEvaluator;
import pl.edu.agh.distributedsystems.gateway.filters.LoginFilter;
import pl.edu.agh.distributedsystems.gateway.security.JwtCreator;
import pl.edu.agh.distributedsystems.gateway.security.JwtRequestHandler;
import pl.edu.agh.distributedsystems.gateway.security.JwtValidator;

@SpringBootApplication
@EnableZuulProxy
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	@Bean
	public LoginFilter loginFilter() {
		return new LoginFilter(new JwtCreator());
	}

    @Bean
    public AuthenticationFilter authFilter() {
        return new AuthenticationFilter(new JwtValidator(), new JwtRequestHandler(), new AuthenticationFilterPathEvaluator());
    }
	
}
