package com.lgastelu.proyectointegrador;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.lgastelu.proyectointegrador.models.Usuarios;
import com.lgastelu.proyectointegrador.service.ApiService;
import com.lgastelu.proyectointegrador.service.ApiServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register_LoginActivity extends AppCompatActivity {

    private EditText txt_nombre,txt_apellido,txt_correo,txt_password,txt_co_password, txt_nacionalidad;

    private static final String TAG = Register_LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register__login);
        txt_nombre = findViewById(R.id.txt_firstname);
        txt_apellido = findViewById(R.id.txt_lastname);
        txt_correo = findViewById(R.id.txt_email);
        txt_password = findViewById(R.id.txt_password);
        txt_co_password = findViewById(R.id.txt_re_password);
        txt_nacionalidad = findViewById(R.id. txt_nacionality);

    }

    public void callRegister(View view){

        String nombre = txt_nombre.getText().toString();
        String apellido = txt_apellido.getText().toString();
        String correo = txt_correo.getText().toString();
        String contraseña = txt_password.getText().toString();
        String re_contraseña = txt_co_password.getText().toString();
        String tipo= txt_nacionalidad.getText().toString();

        if (correo.isEmpty() || contraseña.isEmpty() || re_contraseña.isEmpty()) {
            Toast.makeText(this, "correo o contraseña son campos requeridos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!contraseña.equals(re_contraseña)){
            Toast.makeText(this, "contraseña son campos requeridos", Toast.LENGTH_SHORT).show();
            return;
        }
        Usuarios usuarios = new Usuarios(nombre, apellido, tipo,correo, contraseña);
        ApiService service = ApiServiceGenerator.createService(ApiService.class);

        Call<Usuarios> call;

        call=service.createUsuario(usuarios);

        call.enqueue(new Callback<Usuarios>() {
            @Override
            public void onResponse(@NonNull Call<Usuarios> call, @NonNull Response<Usuarios> response) {
                try {
                    if(response.isSuccessful()) {

                        Usuarios usuarios = response.body();
                        Log.d(TAG, "usuario: " + usuarios);

                        Toast.makeText(Register_LoginActivity.this, "Usuario creado correctamente", Toast.LENGTH_SHORT).show();

                        setResult(RESULT_OK);

                        startActivity(new Intent(Register_LoginActivity.this, LoginActivity.class));
                        finish();

                    }else{
                        throw new Exception(ApiServiceGenerator.parseError(response).getMessage());
                    }
                } catch (Throwable t) {
                    Log.e(TAG, "onThrowable: " + t.getMessage(), t);
                    Toast.makeText(Register_LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Usuarios> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage(), t);
                Toast.makeText(Register_LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
}
