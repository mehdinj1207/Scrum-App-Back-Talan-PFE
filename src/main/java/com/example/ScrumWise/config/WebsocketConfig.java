package com.example.ScrumWise.config;

import com.example.ScrumWise.rest.controller.MessagesController;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.HandshakeInterceptor;


import java.util.Map;

@Configuration
@EnableWebSocket
@EnableWebSocketMessageBroker
public class WebsocketConfig implements WebSocketMessageBrokerConfigurer {
/* websocket te5dem public class WebsocketConfig implements WebSocketConfigurer {*/
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        System.out.println("my config works");
        registry.addEndpoint("/ichat").setAllowedOrigins("*").addInterceptors(new HttpSessionHandshakeInterceptor());
    }
    /* websocket te5dem private final static String CHAT_ENDPOINT = "/chat";*/


    /*websocket te5dem @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(getChatWebSocketHandler(), CHAT_ENDPOINT)
                .setAllowedOrigins("*")
                .addInterceptors(new HttpSessionHandshakeInterceptor());
    }*/
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/channel");
        config.setApplicationDestinationPrefixes("/app");
    }
    /* websocket te5dem @Bean
    public WebSocketHandler getChatWebSocketHandler() {
        return new MessagesController();
    }*/

    private class HttpSessionHandshakeInterceptor implements HandshakeInterceptor {

        @Override
        public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                       WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
            HttpHeaders headers = request.getHeaders();
            String accessToken = getAccessTokenFromSecurityContext();

            headers.add("Authorization", "Bearer " + accessToken);

            return true;
        }

        @Override
        public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Exception exception) {
            // Do nothing after the handshake
        }

        private String getAccessTokenFromSecurityContext() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication instanceof KeycloakAuthenticationToken) {
                KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) authentication;
                KeycloakSecurityContext keycloakSecurityContext = (KeycloakSecurityContext) keycloakAuthenticationToken.getCredentials();
                return keycloakSecurityContext.getTokenString();
            }
            return null;
        }
    }
}