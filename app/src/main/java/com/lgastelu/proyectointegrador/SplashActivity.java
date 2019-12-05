package com.lgastelu.proyectointegrador;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private Button btn_go_login, btn_go_publication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        btn_go_login=findViewById(R.id.btn_go_login);
        btn_go_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callGoLogin();
            }
        });

        btn_go_publication=findViewById(R.id.btn_go_publication);
        btn_go_publication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callGoPublication();
            }
        });
    }

    public void callGoLogin(){
        startActivityForResult(new Intent(getApplicationContext(), MainActivity.class), 100);
    }

    public void callGoPublication(){
        startActivityForResult(new Intent(getApplicationContext(), Register_PublicacionesActivity.class), 100);
    }
}
