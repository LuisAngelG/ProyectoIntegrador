package com.lgastelu.proyectointegrador;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.lgastelu.proyectointegrador.models.Publicaciones;
import com.lgastelu.proyectointegrador.service.ApiService;
import com.lgastelu.proyectointegrador.service.ApiServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register_PublicacionesActivity extends AppCompatActivity {

    private static final String TAG = Register_PublicacionesActivity.class.getSimpleName();

    private EditText txt_contenido, txt_title;
    private Button btn_register_publication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register__publicaciones);

        txt_contenido=findViewById(R.id.txt_content);
        txt_title=findViewById(R.id.txt_title);

        btn_register_publication=findViewById(R.id.btn_register_publication);
        btn_register_publication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callRegisterPublication2();
            }
        });

    }
        public void callRegisterPublication (View view){

            String pub_contenido = txt_contenido.getText().toString();

            if (pub_contenido.isEmpty()) {
                Toast.makeText(this, "contenido es un campo muy importante", Toast.LENGTH_SHORT).show();
                return;
            }

            ApiService service = ApiServiceGenerator.createService(ApiService.class);

            Call<Publicaciones> call;
            call = service.createPublicaciones(pub_contenido);

            call.enqueue(new Callback<Publicaciones>() {
                @Override
                public void onResponse(@NonNull Call<Publicaciones> call, @NonNull Response<Publicaciones> response) {
                    try {
                        if (response.isSuccessful()) {

                            Publicaciones publicaciones = response.body();
                            Log.d(TAG, "usuario: " + publicaciones);

                            Toast.makeText(Register_PublicacionesActivity.this, "Registro guardado satisfactoriamente", Toast.LENGTH_SHORT).show();

                            setResult(RESULT_OK);

                            startActivity(new Intent(Register_PublicacionesActivity.this, MainActivity.class));
                            finish();

                        } else {
                            throw new Exception(ApiServiceGenerator.parseError(response).getMessage());
                        }
                    } catch (Throwable t) {
                        Log.e(TAG, "onThrowable: " + t.getMessage(), t);
                        Toast.makeText(Register_PublicacionesActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Publicaciones> call, @NonNull Throwable t) {
                    Log.e(TAG, "onFailure: " + t.getMessage(), t);
                    Toast.makeText(Register_PublicacionesActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
    }

    public void callRegisterPublication2(){

    }
}
