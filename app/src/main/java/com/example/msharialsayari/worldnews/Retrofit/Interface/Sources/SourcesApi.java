package com.example.msharialsayari.worldnews.Retrofit.Interface.Sources;


import com.example.msharialsayari.worldnews.Retrofit.Model.Sources.Sources;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by msharialsayari on 9/26/2017 AD.
 */

public interface SourcesApi {

//    @GET("sources?language=en&")
//    Call<Sources> getSources(@Query("category") String category);

    @GET("sources?language=en&")
    Observable<Sources> getSources(@Query("category") String category);


}
