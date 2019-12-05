package com.lgastelu.proyectointegrador;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.lgastelu.proyectointegrador.models.Publicaciones;
import com.lgastelu.proyectointegrador.service.ApiService;
import com.lgastelu.proyectointegrador.service.ApiServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = DetailActivity.class.getSimpleName();

    private Long id;

    private TextView titleText;
    private TextView contextText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        titleText = findViewById(R.id.txt_title);
        contextText = findViewById(R.id.txt_content);

        id = getIntent().getExtras().getLong("ID");
        Log.e(TAG, "id:" + id);

        initialize();
    }

    private void initialize() {

        ApiService service = ApiServiceGenerator.createService(ApiService.class);

        Call<Publicaciones> call = service.showPublicaciones(id);

        call.enqueue(new Callback<Publicaciones>() {
            @Override
            public void onResponse(@NonNull Call<Publicaciones> call, @NonNull Response<Publicaciones> response) {
                try {

                    if (response.isSuccessful()) {

                        Publicaciones publicaciones = response.body();
                        Log.d(TAG, "publicaciones: " + publicaciones);

                        contextText.setText(publicaciones.getPub_contenido());

                    } else {
                        throw new Exception(ApiServiceGenerator.parseError(response).getMessage());
                    }

                } catch (Throwable t) {
                    Log.e(TAG, "onThrowable: " + t.getMessage(), t);
                    Toast.makeText(DetailActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Publicaciones> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage(), t);
                Toast.makeText(DetailActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }

}
