package com.example.msharialsayari.worldnews.Tabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.msharialsayari.worldnews.Adapters.NewsAdapter;
import com.example.msharialsayari.worldnews.Dagger.Component.DaggerNewsAppComponent;
import com.example.msharialsayari.worldnews.Dagger.Component.NewsAppComponent;
import com.example.msharialsayari.worldnews.Dagger.Module.NewsAppModule;
import com.example.msharialsayari.worldnews.R;
import com.example.msharialsayari.worldnews.R2;
import com.example.msharialsayari.worldnews.Retrofit.Interface.Articles.ArticlesApi;
import com.example.msharialsayari.worldnews.Retrofit.Interface.Sources.SourcesApi;
import com.example.msharialsayari.worldnews.Retrofit.Model.Articles.Article;
import com.example.msharialsayari.worldnews.Retrofit.Model.Articles.Articles;
import com.example.msharialsayari.worldnews.Retrofit.Model.Sources.Source;
import com.example.msharialsayari.worldnews.Retrofit.Model.Sources.Sources;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * Created by msharialsayari on 9/26/2017 AD.
 */

public class NewsTab extends Fragment {

    public static final String KEY_MY_CATEGORY = "general";
    @BindView(R2.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private List<Article> articlesList = new ArrayList<>();
    private List<Source> sources = new ArrayList<>();
    private NewsAdapter newsAdapter;

    private NewsAppComponent component;


    public static NewsTab newInstance(String category) {

        Bundle args = new Bundle();
        args.putString(KEY_MY_CATEGORY, category);
        NewsTab fragment = new NewsTab();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recyclerview, container, false);
        ButterKnife.bind(this, view);
        String category = getArguments().getString(KEY_MY_CATEGORY);


        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://newsapi.org/v1/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        SourcesApi sourceApi = retrofit.create(SourcesApi.class);
        sourceApi.getSources(category).flatMap(new Func1<Sources, Observable<Source>>() {
            @Override
            public Observable<Source> call(Sources sources) {
                return Observable.from(sources.getSources());
            }
        }).flatMap(new Func1<Source, Observable<Articles>>() {
            @Override
            public Observable<Articles> call(Source sources) {
                return retreiveDataFromArticalApiByRx(sources.getId());

            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Articles>() {
            @Override
            public void onCompleted() {
                newsAdapter = new NewsAdapter(articlesList);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(newsAdapter);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Articles articles) {
                articlesList.addAll(articles.getArticles());
                component = DaggerNewsAppComponent.builder().newsAppModule(new NewsAppModule(articlesList, retrofit)).build();

            }
        });


        return view;
    }


    private Observable<Articles> retreiveDataFromArticalApiByRx(String idSource) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://newsapi.org/v1/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ArticlesApi articleApi = retrofit.create(ArticlesApi.class);
        Observable<Articles> connection = articleApi.getArticales(idSource).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
        return connection;
    }


}
