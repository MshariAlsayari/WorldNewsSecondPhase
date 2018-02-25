package com.example.msharialsayari.worldnews.Dagger.Component;

import com.example.msharialsayari.worldnews.Dagger.Module.NewsAppModule;
import com.example.msharialsayari.worldnews.Tabs.NewsTab;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by msharialsayari on 9/27/2017 AD.
 */

@Singleton
@Component(modules = NewsAppModule.class)
public interface NewsAppComponent {



    void inject(NewsTab target);


}
