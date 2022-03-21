package org.mixed.exam.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class FrameFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest shr= exchange.getRequest().mutate().headers(httpHeaders -> {
            httpHeaders.add("X-Frame-Options","SAMEORIGIN");
        }).build();
//        response.addHeader("x-frame-options","SAMEORIGIN");
        return chain.filter(exchange.mutate().request(shr).build());
    }
//    @Autowired
//    public void setFrameOptions(HttpServletResponse response){
//        response.addHeader("x-frame-options","SAMEORIGIN");
//    }
}
