package com.example.schoolupdaterapp.menu;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("http://192.168.100.81:8080/schoolUpdater/webresources/institution")
    Call<ResponseMethod> getAllResponses();
}
