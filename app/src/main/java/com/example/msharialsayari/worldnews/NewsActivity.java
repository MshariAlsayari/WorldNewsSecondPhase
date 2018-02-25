package com.example.msharialsayari.worldnews;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewsActivity extends AppCompatActivity {
    @BindView(R2.id.imageView)
    ImageView image;



    @OnClick(R2.id.toolbar)
    void backTopreviousActivity(){
       finish();
    }

    @BindView(R2.id.textViewTitle)
    TextView title;

    @BindView(R2.id.textViewPublishedDate)
    TextView publishedDate;


    @BindView(R2.id.textViewDescreption)
    TextView description;

    @BindView(R2.id.textViewUrl)
    TextView Url;

    URL url ;


    String ImageUrlString;
    String TitleString;
    String DescriptionString;
    String AuthorString;
    String PublishedDateString;
    String UrlString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);


        ImageUrlString = getIntent().getStringExtra("Image");
        TitleString = getIntent().getStringExtra("Title");
        DescriptionString = getIntent().getStringExtra("Description");
        AuthorString = getIntent().getStringExtra("Author");
        if (AuthorString == null || AuthorString.equals(""))
            AuthorString = "the author isn't available";
        PublishedDateString = getIntent().getStringExtra("PublishedDate");
        UrlString = getIntent().getStringExtra("Url");
        try {
            url = new URL(UrlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Picasso.with(this).load(ImageUrlString).into(image);
        title.setText(TitleString);
        publishedDate.setText(getSpecificDay(PublishedDateString) + ", " + PublishedDateString.substring(0, 10) + " | " + AuthorString);
        description.setText(DescriptionString);
        Url.setText(": " + url.getHost());
        SpannableString spannableStringObject = new SpannableString(UrlString);
        spannableStringObject.setSpan(new UnderlineSpan(), 0, spannableStringObject.length(), 0);
        Url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(UrlString));
                startActivity(browserIntent);
            }
        });


    }

    private String getSpecificDay(String date) {

        date = date.substring(0,10);
        String stringArray[] = date.split("-");
        int intArray[] = new int [stringArray.length];
        for (int i = 0; i < stringArray.length; i++) {

            if (stringArray[i].equals("08"))
                intArray[i] = 8;
            else if (stringArray[i].equals("09"))
                intArray[i] = 9;
             else  intArray[i] = Integer.parseInt(stringArray[i]);
        }

        Calendar calendar = Calendar.getInstance();

        calendar.set(intArray[0], intArray[1], intArray[2]);
        int dayInnumber = calendar.get(Calendar.DAY_OF_WEEK);

        switch (dayInnumber) {
            case 1:
                return "Thu";
            case 2:
                return "Fri";
            case 3:
                return "Sat";
            case 4:
                return "Sun";
            case 5:
                return "Mon";
            case 6:
                return "Tue";
            case 7:
                return "Wed";
        }
        return "";


    }

}
