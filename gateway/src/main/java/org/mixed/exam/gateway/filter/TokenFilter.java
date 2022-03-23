package org.mixed.exam.gateway.filter;

import com.netflix.ribbon.proxy.annotation.Http;
import io.jsonwebtoken.Jwts;
import io.netty.handler.codec.http.HttpUtil;
import org.mixed.exam.auth.api.AuthUtil;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;

@Component
public class TokenFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain)
    {
        if(exchange.getRequest().getURI().getPath().startsWith("/login"))
        {
            return chain.filter(exchange);
        }
        ServerHttpRequest request = exchange.getRequest();
        MultiValueMap<String, HttpCookie> cookies = request.getCookies();
        Set<String> keys = cookies.keySet();
        HttpCookie tokenCookie = null;
        outer:
        for(String key : keys)
        {
            List<HttpCookie> httpCookies = cookies.get(key);
            for (HttpCookie cookie : httpCookies)
            {
                if(cookie.getName().equals("token"))
                {
                    tokenCookie = cookie;
                    break outer;
                }
            }
        }
        if(tokenCookie != null)
        {
            HttpCookie finalTokenCookie = tokenCookie;
            String jwt=finalTokenCookie.getValue();
            if(!AuthUtil.checkIsExp(jwt))
            {
                ServerHttpRequest shr= exchange.getRequest().mutate().headers(httpHeaders -> {
                    httpHeaders.add("Authorization","bearer "+ jwt);
                }).build();
                return chain.filter(exchange.mutate().request(shr).build());
            }
        }
        //重定向
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.SEE_OTHER);
        response.getHeaders().set(HttpHeaders.LOCATION, "/login/sign_in");
        return response.setComplete();
    }
}
