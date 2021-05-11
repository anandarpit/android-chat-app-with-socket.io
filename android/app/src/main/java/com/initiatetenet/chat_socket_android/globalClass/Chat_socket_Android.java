package com.initiatetenet.chat_socket_android.globalClass;

import android.app.Application;


import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;


public class Chat_socket_Android extends Application {

    private Socket mSocket;
    private static final String URL = "http://192.168.43.123:3000/";

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            mSocket = IO.socket(URL);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
    public Socket getmSocket(){
        return mSocket;
    }
}
