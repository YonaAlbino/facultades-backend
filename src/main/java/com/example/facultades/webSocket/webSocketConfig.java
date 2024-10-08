package com.example.facultades.webSocket;

import com.example.facultades.enums.Socket;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class webSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry){
        registry.enableSimpleBroker(Socket.TOPICO_PRINCIPAL.getRuta(), Socket.TOPICO_PERSONAL.getRuta());
    }

    @Override
    public void  registerStompEndpoints(StompEndpointRegistry registry){
        registry.addEndpoint("websocket").setAllowedOrigins("http://localhost:4200").withSockJS();;
    }
}
