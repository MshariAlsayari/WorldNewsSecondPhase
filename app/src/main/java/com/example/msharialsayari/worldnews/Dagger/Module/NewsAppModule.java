package com.example.msharialsayari.worldnews.Dagger.Module;

import android.content.Context;

import com.example.msharialsayari.worldnews.Retrofit.Interface.Sources.SourcesApi;
import com.example.msharialsayari.worldnews.Retrofit.Model.Articles.Article;
import com.example.msharialsayari.worldnews.Retrofit.Model.Sources.Source;
import com.example.msharialsayari.worldnews.Retrofit.Model.Sources.Sources;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by msharialsayari on 9/27/2017 AD.
 */
@Module
public class NewsAppModule {


    List<Article> articles;
    Retrofit retrofit;

    public NewsAppModule(List<Article> articles, Retrofit retrofit) {
        this.retrofit = retrofit;
        this.articles = articles;


    }


    @Provides
    @Singleton
    List<Article> provideArticles() {
        return articles;
    }

    @Provides
    SourcesApi provideSourcesApi(Retrofit retrofit) {
        return retrofit.create(SourcesApi.class);
    }


}
