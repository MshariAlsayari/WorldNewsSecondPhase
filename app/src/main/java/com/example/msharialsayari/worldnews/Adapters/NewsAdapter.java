package com.example.msharialsayari.worldnews.Adapters;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.msharialsayari.worldnews.NewsActivity;
import com.example.msharialsayari.worldnews.R;
import com.example.msharialsayari.worldnews.R2;
import com.example.msharialsayari.worldnews.Retrofit.Model.Articles.Article;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by msharialsayari on 9/26/2017 AD.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> {

    List<Article> articleList;


    public NewsAdapter(List<Article> articleList) {
        this.articleList = articleList;

    }

    @Override
    public NewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       // View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_holder, parent, false);
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.another_design_card_view, parent, false);
        NewsHolder holder = new NewsHolder(row);
        return holder;
    }

    @Override
    public void onBindViewHolder(NewsHolder holder, final int position) {
        YoYo.with(Techniques.FadeIn).playOn(holder.mycard);
        Article article = articleList.get(position);
        holder.bind(article);
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }


    class NewsHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textViewTitle)
        TextView title;
//
//        @BindView(R2.id.textViewDescreption)
//        TextView description;
//
//        @BindView(R2.id.textViewAuthor)
//        TextView author;
//
//        @BindView(R2.id.textViewPublishedDate)
//        TextView publishedDate;
//
        @BindView(R2.id.cardView)
        CardView mycard;
//
        @BindView(R2.id.imageView)
        ImageView myImage;


        public NewsHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        public void bind(final Article article) {

            title.setText(article.getTitle());

            Picasso.with(itemView.getContext()).load(article.getUrlToImage()).into(myImage);
            myImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
            mycard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(itemView.getContext(), NewsActivity.class);
                    i.putExtra("Title", article.getTitle());
                    i.putExtra("Description", article.getDescription());
                    i.putExtra("PublishedDate", article.getPublishedAt());
                    i.putExtra("Author" , article.getAuthor());
                    i.putExtra("Image" ,  article.getUrlToImage());
                    i.putExtra("Url" , article.getUrl());
                    view.getContext().startActivity(i);
                }
            });

        }


    }
}
