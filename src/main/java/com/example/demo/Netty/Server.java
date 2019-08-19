package com.example.demo.Netty;

public class Server {
    private static final int FILE_PORT = 9991;
    public static void main(String[] args) {
        try {
            new FileServer().bind(FILE_PORT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
