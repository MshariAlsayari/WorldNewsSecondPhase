package com.example.msharialsayari.worldnews;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

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
        adapter.addFragment(NewsTab.newInstance("General"), "General");
        adapter.addFragment(NewsTab.newInstance("Business"), "Business");
        adapter.addFragment(NewsTab.newInstance("Entertainment"), "Entertainment");
        adapter.addFragment(NewsTab.newInstance("Gaming"), "Gaming");
        adapter.addFragment(NewsTab.newInstance("Music"), "Music");
        adapter.addFragment(NewsTab.newInstance("Politics"), "Politics");
        adapter.addFragment(NewsTab.newInstance("Sport"), "Sport");
        adapter.addFragment(NewsTab.newInstance("Technology"), "Technology");
        adapter.addFragment(NewsTab.newInstance("science-and-nature"), "science-and-nature");
        viewPager.setAdapter(adapter);

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


    private AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You need to have SIM Data or wifi to access this. Press ok to Exit");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
            }
        });

        return builder;
    }
}
