package com.example.msharialsayari.worldnews;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.msharialsayari.worldnews.Adapters.SectionPageAdapter;
import com.example.msharialsayari.worldnews.Tabs.NewsTab;

public class MainActivity extends AppCompatActivity {


    private SectionPageAdapter sectionPageAdapter;

    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        sectionPageAdapter = new SectionPageAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


    }

    private void setupViewPager(ViewPager viewPager) {
        SectionPageAdapter adapter = new SectionPageAdapter(getSupportFragmentManager());
        adapter.addFragment(NewsTab.newInstance("General"), getString(R.string.General));
        adapter.addFragment(NewsTab.newInstance("Business"), getString(R.string.Business));
        adapter.addFragment(NewsTab.newInstance("Entertainment"), getString(R.string.Entertainment));
        adapter.addFragment(NewsTab.newInstance("Gaming"), getString(R.string.Gaming));
        adapter.addFragment(NewsTab.newInstance("Music"), getString(R.string.Music));
        adapter.addFragment(NewsTab.newInstance("Politics"), getString(R.string.Politics));
        adapter.addFragment(NewsTab.newInstance("Sport"), getString(R.string.Sport));
        adapter.addFragment(NewsTab.newInstance("Technology"), getString(R.string.Technology));
        adapter.addFragment(NewsTab.newInstance("science-and-nature"), getString(R.string.science_and_nature));
        viewPager.setAdapter(adapter);

    }

}
