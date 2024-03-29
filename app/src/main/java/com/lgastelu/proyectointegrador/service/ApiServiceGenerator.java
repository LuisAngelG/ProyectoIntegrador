package com.lgastelu.proyectointegrador.service;

import android.os.Build;

import com.lgastelu.proyectointegrador.BuildConfig;
import com.lgastelu.proyectointegrador.models.ApiError;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiServiceGenerator {

    private static Retrofit retrofit;

    public static <s> s createService(Class<s> serviceClass){

        if (retrofit==null){ //patron cingleton = creacion de nu solo objeto.

            OkHttpClient.Builder httpClient=new OkHttpClient.Builder();

            httpClient.readTimeout(60, TimeUnit.SECONDS).connectTimeout(60, TimeUnit.SECONDS);

            if (BuildConfig.DEBUG){

                httpClient.addInterceptor(new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY));

            }

            retrofit=new Retrofit
                    .Builder()
                    .baseUrl(ApiService.API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

        }

        return retrofit.create(serviceClass);

    }

    public static ApiError parseError(retrofit2.Response<?> response){
        try {
            Converter<ResponseBody, ApiError> converter=retrofit.responseBodyConverter(ApiError.class, new Annotation[0]);
            return converter.convert(response.errorBody());
        } catch (Exception e) {
            return new ApiError("Error desconocido en el servicio");
        }
    }

}
