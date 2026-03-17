package com.antiblaze;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.util.Scanner;

import static com.antiblaze.AccessDatabase.getRepo;
import static com.antiblaze.AccessDatabase.getReposFromDB;
import static com.antiblaze.ConsoleCommands.doCommand;
import static com.antiblaze.Constants.stopServerDelay;

public class Main {
    private static HttpServer server;
    public static void main(String[] args) {
        getReposFromDB();
        new Thread(()->
        {
            try {
                startServer();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        ).start();


        new Thread(()->
        {
            System.out.println(getRepo("foo").getName());
            Scanner scanner=new Scanner(System.in);
            final String message= """
                    ==================================================
                      Welcome to StuffTracker server admin terminal.
                    ==================================================
                    
                    Type HELP for all available commands
                    Type QUIT or STOP to stop the server after a delay.
                    """;
            System.out.println(message);
            boolean running=true;
            while (running) {
                running=doCommand(scanner.nextLine());
//                System.out.println("what: "+scanner.nextLine());
            }
        }
                ).start();

//        System.out.println("stuff");
    }

    public static void startServer() throws Exception {
        server=HttpServer.create(new InetSocketAddress(8000),0);
        server.createContext("/",new TrackerHandler());
        server.setExecutor(null);
        System.out.println("Starting server...");
        server.start();
        System.out.println("Server started.");
    }

    public static void stopServer() {
        System.out.println("Stopping server in "+stopServerDelay+" seconds...");
        server.stop(stopServerDelay);
        System.out.println("Stopped server.");
    }

    static class TrackerHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
//            String response = "Goofy Goober";
//            exchange.sendResponseHeaders(200, response.length());
//            if (exchange.getRequestURI().getPath())
            System.out.println("path: "+exchange.getRequestURI().getPath());
            InputStream stream = exchange.getRequestBody();
            System.out.println(new Scanner(stream).nextLine());
        }

    }

}