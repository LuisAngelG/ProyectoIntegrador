package com.lgastelu.proyectointegrador;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lgastelu.proyectointegrador.models.ApiError;
import com.lgastelu.proyectointegrador.models.Usuarios;
import com.lgastelu.proyectointegrador.service.ApiService;
import com.lgastelu.proyectointegrador.service.ApiServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    private EditText emailInput;
    private EditText passwordInput;
    private Button loginButton,registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailInput = findViewById(R.id.txt_email_login);
        passwordInput = findViewById(R.id.txt_password_login);

        registerButton=findViewById(R.id.btn_register_login);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callRegister();
            }
        });


        loginButton = findViewById(R.id.btn_login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        loadLastUsername();

        verifyLoginStatus();

    }

    private void login(){

        String correo = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        if(correo.isEmpty()){
            Toast.makeText(this, "Ingrese el correo electronico", Toast.LENGTH_SHORT).show();
            return;
        }

        if(password.isEmpty()){
            Toast.makeText(this, "Ingrese el password", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService service = ApiServiceGenerator.createService(ApiService.class);

        Call<Usuarios> call = service.login(correo, password);

        call.enqueue(new Callback<Usuarios>() {
            @Override
            public void onResponse(Call<Usuarios> call, Response<Usuarios> response) {
                if(response.isSuccessful()) { // code 200
                    Usuarios usuario = response.body();
                    Log.d(TAG, "usuario" + usuario);

                    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                    sp.edit()
                            .putString("correo", usuario.getCorreo())
                            .putBoolean("islogged", true)
                            .commit();

                    // Go Main Activity
                    startActivity(new Intent(LoginActivity.this, SplashActivity.class));
                    finish();

                    Toast.makeText(LoginActivity.this, "Bienvenido " + usuario.getNombre(), Toast.LENGTH_LONG).show();

                }else{
                    ApiError error = ApiServiceGenerator.parseError(response);
                    Toast.makeText(LoginActivity.this, "onError:" + error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Usuarios> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "onFailure: " + t.toString(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void loadLastUsername(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

        String user_email = sp.getString("username", null);
        if(user_email != null){
            emailInput.setText(user_email);
        }
    }

    private void verifyLoginStatus(){

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        boolean islogged = sp.getBoolean("islogged", false);

        if(islogged){
            startActivity(new Intent(this, SplashActivity.class));
            finish();
        }

    }

    public void callRegister(){
        startActivityForResult(new Intent(this, Register_LoginActivity.class), 100);
    }

}

