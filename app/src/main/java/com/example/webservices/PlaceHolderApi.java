package com.example.webservices;

import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


public  interface PlaceHolderApi {
   @GET("v2/posts.json")
    Call<model> getJsonObjectData();
}
