package com.lgastelu.proyectointegrador;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lgastelu.proyectointegrador.adapters.PublicacionesAdapter;
import com.lgastelu.proyectointegrador.models.ApiError;
import com.lgastelu.proyectointegrador.models.Publicaciones;
import com.lgastelu.proyectointegrador.service.ApiService;
import com.lgastelu.proyectointegrador.service.ApiServiceGenerator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView publicacionesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        publicacionesList = findViewById(R.id.listview_publicaciones);

        publicacionesList.setLayoutManager(new LinearLayoutManager(this));

        publicacionesList.setAdapter(new PublicacionesAdapter());

        initialize();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_logout:
                logout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        sp.edit().remove("islogged").remove("token").commit();

        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }


    public void initialize(){

        ApiService service = ApiServiceGenerator.createService(ApiService.class);

        service.findAll().enqueue(new Callback<List<Publicaciones>>() {
            @Override
            public void onResponse(Call<List<Publicaciones>> call, Response<List<Publicaciones>> response) {
                if (response.isSuccessful()){

                    List<Publicaciones> publicaciones = response.body();
                    Log.d(TAG, "publicaciones" + publicaciones);

                    PublicacionesAdapter adapter = (PublicacionesAdapter)publicacionesList.getAdapter();
                    adapter.setPublicaciones(publicaciones);
                    adapter.notifyDataSetChanged();

                } else {
                    ApiError error = ApiServiceGenerator.parseError(response);
                    Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Publicaciones>> call, Throwable t) {

                Toast.makeText(MainActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void callPublicationRegister(View view){
        startActivityForResult(new Intent(getApplicationContext(), Register_PublicacionesActivity.class), 100);
    }

}
