package com.example.journey;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Retro {

    @GET("todos")
    Call<List<Todo>> listTodos();

}
