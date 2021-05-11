package com.initiatetenet.chat_socket_android;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;


public class ChatBoxActivity extends AppCompatActivity {

    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://192.168.43.123:3000/");
        } catch (URISyntaxException e) {}
    }

    private String Nickname ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_box);
        
        Nickname= (String)getIntent().getExtras().getString(MainActivity.NICKNAME);

        mSocket.connect();
        mSocket.emit("join",Nickname);
    }

}