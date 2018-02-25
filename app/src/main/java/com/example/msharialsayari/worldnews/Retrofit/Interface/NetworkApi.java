package com.example.msharialsayari.worldnews.Retrofit.Interface;

import com.example.msharialsayari.worldnews.Retrofit.Model.Articles.Articles;
import com.example.msharialsayari.worldnews.Retrofit.Model.Sources.Sources;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by msharialsayari on 10/1/2017 AD.
 */

public interface NetworkApi {

    @GET("articles?apiKey=31b2ac0ae06a4230a479c1f4df8a8a2b&")
    Observable<Articles> getArticales(@Query("source") String source);

    @GET("sources?language=en&")
    Observable<Sources> getSources(@Query("category") String category);


}
