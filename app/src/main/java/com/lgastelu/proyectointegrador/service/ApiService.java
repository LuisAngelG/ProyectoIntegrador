package com.lgastelu.proyectointegrador.service;

import com.lgastelu.proyectointegrador.models.Publicaciones;
import com.lgastelu.proyectointegrador.models.Usuarios;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

//    String API_BASE_URL = "http://10.0.2.2:8082"; //no acepta https:, solo http:
    String API_BASE_URL = "https://conocimientoperuano.herokuapp.com";


    @GET("/api/usuarios")
    Call<List<Usuarios>> buscartodos();

    @GET("/api/publicaciones")
    Call<List<Publicaciones>> findAll();

    @POST("/api/usuarios")
    Call<Usuarios> createUsuario(@Body Usuarios usuarios);

    @FormUrlEncoded
    @POST("/api/publicaciones")
    Call<Publicaciones> createPublicaciones
                               (@Field("pub_contenido") String pub_contenido);

    @GET("/api/publicaciones/{id}")
    Call<Publicaciones> showPublicaciones(@Path("id") Long id);

    @FormUrlEncoded
    @POST("/auth/login")
    Call<Usuarios> login(@Field("correo") String correo,
                        @Field("password") String password);


}
