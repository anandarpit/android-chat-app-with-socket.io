package com.initiatetenet.chat_socket_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {


    private Button btn;
    private EditText nickname;
    public static final String NICKNAME = "usernickname";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button)findViewById(R.id.enterchat) ;
        nickname = (EditText) findViewById(R.id.nickname);

        btn.setOnClickListener(v -> {

            if(!nickname.getText().toString().isEmpty()){

                    Intent i  = new Intent(MainActivity.this, ChatBoxActivity.class);
                    i.putExtra(NICKNAME, nickname.getText().toString());
                    startActivity(i);
            }
        });
    }
}