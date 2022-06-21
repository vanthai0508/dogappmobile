package com.example.dog_app.View;

import com.example.dog_app.Model.DogBreed;
import com.example.dog_app.Model.DogsAPI;

import java.util.List;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DogApiService {
    private static final String BASE_URL="https://raw.githubusercontent.com/";
    private DogsAPI api;
    public DogApiService()
    {
        api = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build().create(DogsAPI.class);
    }
    public Single<List<DogBreed>> getDogs()
    {
         return  api.getDogs();
    }
}
