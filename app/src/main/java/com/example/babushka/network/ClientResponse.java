package com.example.babushka.network;

public class ClientResponse<T> {

    private T data;
    private String status;
    private String message;

    public T getData() {
        return data;
    }
}
