package com.example.sport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class Next extends AppCompatActivity {
TextView textView,textView1;
ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);
        textView=findViewById(R.id.spnam);
        textView1=findViewById(R.id.spdes);
        imageView=findViewById(R.id.imagesp);
        SharedPreferences sp=getApplicationContext().getSharedPreferences("P",MODE_PRIVATE);
        String textsp=sp.getString("k1",null);
        String descriptionsp=sp.getString("k2",null);
        String imagesp=sp.getString("k3",null);
        textView.setText(textsp);
        textView1.setText(descriptionsp);
        Glide
                .with(Next.this)/////context
                .load(imagesp)///image
                .placeholder(R.drawable.ima)
                .into(imageView);


    }
    }
