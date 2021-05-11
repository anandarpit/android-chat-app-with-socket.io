package com.initiatetenet.chat_socket_android;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.initiatetenet.chat_socket_android.globalClass.Chat_socket_Android;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;


public class ChatBoxActivity extends AppCompatActivity {

    private Socket mSocket;
    private String Nickname ;

    public RecyclerView myRecylerView ;
    public List<Message> MessageList = new ArrayList<>(); ;
    public ChatBoxAdapter chatBoxAdapter;
    public EditText messagetxt ;
    public Button send ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_box);

        messagetxt = findViewById(R.id.message) ;
        send = findViewById(R.id.send);
        myRecylerView = findViewById(R.id.messagelist);

        chatBoxAdapter = new ChatBoxAdapter(MessageList, getApplicationContext());

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        myRecylerView.setLayoutManager(mLayoutManager);
        myRecylerView.setAdapter(chatBoxAdapter);
        myRecylerView.setItemAnimator(new DefaultItemAnimator());
        
        Nickname= getIntent().getExtras().getString(MainActivity.NICKNAME);

        Chat_socket_Android app = (Chat_socket_Android) getApplication();
        mSocket = app.getmSocket();

        mSocket.connect();
        mSocket.emit("join", Nickname);

        mSocket.on("userjoinedthechat", args -> runOnUiThread(() -> {
            String data = (String) args[0];
            Toast.makeText(ChatBoxActivity.this, data, Toast.LENGTH_SHORT).show();
        }));

        send.setOnClickListener(v -> {
            if(!messagetxt.getText().toString().isEmpty()){
                mSocket.emit("messagedetection", Nickname, messagetxt.getText().toString());
                messagetxt.setText(" ");
            }
        });

        mSocket.on("message", args -> runOnUiThread(() -> {
            JSONObject data = (JSONObject) args[0];
            try {
                String nickname = data.getString("senderNickname");
                String message = data.getString("message");

                Message m = new Message(nickname,message);
                MessageList.add(m);
                chatBoxAdapter.notifyDataSetChanged();


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }));

        mSocket.on("userdisconnect", args -> runOnUiThread(() -> {
            String data = (String) args[0];
            Toast.makeText(ChatBoxActivity.this,data,Toast.LENGTH_SHORT).show();
        }));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSocket.disconnect();
    }
}