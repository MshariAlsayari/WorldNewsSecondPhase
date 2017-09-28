package com.example.msharialsayari.worldnews.Retrofit.Interface.Articles;


import com.example.msharialsayari.worldnews.Retrofit.Model.Articles.Articles;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by msharialsayari on 9/26/2017 AD.
 */


public interface ArticlesApi {


    @GET("articles?apiKey=31b2ac0ae06a4230a479c1f4df8a8a2b&")
    Observable<Articles> getArticales(@Query("source") String source);
}
