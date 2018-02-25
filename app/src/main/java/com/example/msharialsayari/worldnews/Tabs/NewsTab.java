package com.example.msharialsayari.worldnews.Tabs;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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
import com.example.msharialsayari.worldnews.Retrofit.Interface.NetworkApi;
import com.example.msharialsayari.worldnews.Retrofit.Model.Articles.Article;
import com.example.msharialsayari.worldnews.Retrofit.Model.Articles.Articles;
import com.example.msharialsayari.worldnews.Retrofit.Model.Sources.Source;
import com.example.msharialsayari.worldnews.Retrofit.Model.Sources.Sources;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    //public static final String KEY_MY_List = "Articles";
    @BindView(R2.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private ArrayList<Article> articlesList = new ArrayList<>();
    private List<Source> sources = new ArrayList<>();
    private NewsAdapter newsAdapter;

    private NewsAppComponent component;

    @Inject
    NetworkApi networkApi;

    String category;
    WifiManager wifi;


    public static NewsTab newInstance(String category) {

        Bundle args = new Bundle();
        args.putString(KEY_MY_CATEGORY, category);
        NewsTab fragment = new NewsTab();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        component = DaggerNewsAppComponent.builder().newsAppModule(new NewsAppModule()).build();
        component.inject(this);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recyclerview, container, false);
        ButterKnife.bind(this, view);


        category = getArguments().getString(KEY_MY_CATEGORY);
        if (isConnected(getContext()) == false) {
            Snackbar.make(getActivity().findViewById(android.R.id.content), "you should connect with internet", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);

                            wifi = (WifiManager) getActivity().getApplicationContext().getSystemService(getContext().WIFI_SERVICE);
                            wifi.setWifiEnabled(true);//Turn on Wifi
                            retreiveNews();
                        }
                    }).show();
        }

        if (savedInstanceState != null) {
            articlesList = savedInstanceState.getParcelableArrayList("Articles");
            newsAdapter = new NewsAdapter(articlesList);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(newsAdapter);
        } else
            retreiveNews();


        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("Articles", articlesList);

    }


    private boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if ((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting()))
                return true;
            else return false;
        } else
            return false;
    }


    private void retreiveNews() {

        progressBar.setVisibility(View.VISIBLE);
        networkApi.getSources(category).flatMap(new Func1<Sources, Observable<Source>>() {
            @Override
            public Observable<Source> call(Sources sources) {
                return Observable.from(sources.getSources());
            }
        }).flatMap(new Func1<Source, Observable<Articles>>() {
            @Override
            public Observable<Articles> call(Source sources) {
                return networkApi.getArticales(sources.getId()).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());

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

            }
        });


    }


}
