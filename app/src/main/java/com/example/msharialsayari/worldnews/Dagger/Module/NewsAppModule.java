package com.example.msharialsayari.worldnews.Dagger.Module;

import com.example.msharialsayari.worldnews.Retrofit.Interface.NetworkApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by msharialsayari on 9/27/2017 AD.
 */
@Module
public class NewsAppModule {





    @Provides
    @Singleton
    public Retrofit createRetrofitObject (){

       return new Retrofit.Builder()
                .baseUrl("https://newsapi.org/v1/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();


    }

    @Provides
    @Singleton
    NetworkApi provideSourcesApi(Retrofit retrofit) {
        return retrofit.create(NetworkApi.class);
    }


}
