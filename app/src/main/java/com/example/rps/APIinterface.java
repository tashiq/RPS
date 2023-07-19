package com.example.rps;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
public interface APIinterface {

        @GET("users")
        Call<List<User>> getUsers();

}
