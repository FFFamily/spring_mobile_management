package org.example.config;

import io.vertx.core.Vertx;
import io.vertx.core.http.ServerWebSocket;
import io.vertx.core.http.impl.ServerWebSocketImpl;

public class VertxWebSocketConfig {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(WebSocketVerticle.class.getName());
    }
}
