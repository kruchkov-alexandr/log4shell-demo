package com.example;

import com.sun.net.httpserver.HttpServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class SimpleServer {
    private static final Logger logger = LogManager.getLogger(SimpleServer.class);
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(9090), 0);
        server.createContext("/", (exchange -> {
            exchange.getRequestHeaders().forEach((key, value) ->
                logger.info("Header - " + key + ": " + value)
            );
            String userAgent = exchange.getRequestHeaders().getFirst("User-Agent");
            logger.info("Processing User-Agent: " + userAgent);
            String response = "Hello, World!";
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }));
        server.setExecutor(null);
        server.start();
        System.out.println("Server started on port 9090");
    }
}
