package com.example.dog_app.Model;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;

public interface DogsAPI {
    @GET("DevTides/DogsApi/master/dogs.json")
    Single<List<DogBreed>> getDogs();
}
