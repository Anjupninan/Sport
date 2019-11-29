package com.example.sport;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import cz.msebera.android.httpclient.Header;

import static java.util.Calendar.YEAR;

public class MainActivity extends AppCompatActivity {
    final Context context = this;
    RecyclerView recyclerView,recyclerView1,recyclerView2,recyclerView3;
AsyncHttpClient asyncHttpClient;
RequestParams requestParams;
ArrayList<String>text,image,description,id;
ArrayList<String>league,sport,sptid;
ArrayList<String>tvid,tvsport,tvevent,tvimag;
ArrayList<String>livsport,livevent,sportid,sptime,sphome,spaway;
String url="https://www.thesportsdb.com/api/v1/json/1/all_sports.php";
String url1="https://www.thesportsdb.com/api/v1/json/1/all_leagues.php";
String url2="https://www.thesportsdb.com/api/v1/json/1/eventsday.php?";
String url3="https://www.thesportsdb.com/api/v1/json/1/eventstv.php?";
//    https://www.thesportsdb.com/api/v1/json/1/eventstv.php?d=2019-11-29
//    https://www.thesportsdb.com/api/v1/json/1/eventsday.php?d=2014-10-10
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recycle);
        asyncHttpClient=new AsyncHttpClient();
        text=new ArrayList<>();
        sportid=new ArrayList<>();
        image=new ArrayList<>();
        tvid=new ArrayList<>();
        tvsport=new ArrayList<>();
        tvevent=new ArrayList<>();
        sptid=new ArrayList<>();
        livsport=new ArrayList<>();
        livevent=new ArrayList<>();
        recyclerView1=findViewById(R.id.recycle1);
        recyclerView2=findViewById(R.id.recycle2);
        id=new ArrayList<>();
        tvimag=new ArrayList<>();
        league=new ArrayList<>();
        sptime = new ArrayList<>();
        sphome=new ArrayList<>();
        spaway=new ArrayList<>();
        recyclerView3=findViewById(R.id.recycle3);
        sport=new ArrayList<>();
        description=new ArrayList<>();
        requestParams=new RequestParams();
        Calendar calendar=Calendar.getInstance();

        String date=calendar.get(YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DAY_OF_MONTH);


        requestParams.put("d",date);
        Calendar calendar1=Calendar.getInstance();

        String date1=calendar1.get(YEAR)+"-"+(calendar1.get(Calendar.MONTH)+1)+"-"+calendar1.get(Calendar.DAY_OF_MONTH);


        requestParams.put("d",date1);

        asyncHttpClient.get(url,requestParams,new JsonHttpResponseHandler()
                {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        JSONObject jsonObject=response;
                        try {

                            JSONArray array=jsonObject.getJSONArray("sports");
                            for (int i=0;i<array.length();i++){
                                JSONObject obj=array.getJSONObject(i);
                                String idsport=obj.getString("idSport");
                                String strsport=obj.getString("strSport");
                                String strformat=obj.getString("strFormat");
                                String strimg=obj.getString("strSportThumb");
                                String strdes=obj.getString("strSportDescription");
                                 text.add(strsport);
                                 image.add(strimg);
                                 description.add(strdes);
                                 id.add(idsport);
                            }
                            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(MainActivity.this, RecyclerView.HORIZONTAL, false);
                            recyclerView.setLayoutManager(linearLayoutManager);
                              horizontaladapter adapt = new horizontaladapter(text,image,description,id);
                            recyclerView.setAdapter(adapt);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        asyncHttpClient.get(url1,requestParams,new JsonHttpResponseHandler()
                {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        JSONObject jsonObject=response;
                        try {
                             JSONArray array1=jsonObject.getJSONArray("leagues");
                             for(int i=0;i<array1.length();i++){
                                 JSONObject obj=array1.getJSONObject(i);
                                 String idlg=obj.getString("idLeague");
                                 String strlg=obj.getString("strLeague");
                                 String strspo=obj.getString("strSport");
                                 league.add(strlg);
                                 sport.add(strspo);
                                 sptid.add(idlg);
                             }
                            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(MainActivity.this, RecyclerView.HORIZONTAL, false);
                            recyclerView1.setLayoutManager(linearLayoutManager);
                           horizontalladapter adaptt = new horizontalladapter(league,sport,sptid);
                            recyclerView1.setAdapter(adaptt);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
      asyncHttpClient.get(url2,requestParams,new JsonHttpResponseHandler()
              {

                  @Override
                  public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                      super.onSuccess(statusCode, headers, response);
                      JSONObject jsonObject=response;
                      try {
                          JSONArray array2=jsonObject.getJSONArray("events");
                          for (int i=0;i<array2.length();i++){
                              JSONObject obj=array2.getJSONObject(i);
                              String idevent=obj.getString("idEvent");
                              String strevent=obj.getString("strEvent");
                              String strsport=obj.getString("strSport");
                              String homtem=obj.getString("strHomeTeam");
                              String awaytam=obj.getString("strAwayTeam");
                              String homscor=obj.getString("intHomeScore");
                              String awayscor=obj.getString("intAwayScore");
                              String strtim=obj.getString("strTime");
                              livsport.add(strsport);
                              livevent.add(strevent);
                              sportid.add(idevent);
                              sphome.add(homtem);
                              spaway.add(awaytam);
                              sptime.add(strtim);
                          }
                          LinearLayoutManager linearLayoutManager=new LinearLayoutManager(MainActivity.this, RecyclerView.HORIZONTAL, false);
                          recyclerView2.setLayoutManager(linearLayoutManager);
                          Toast.makeText(MainActivity.this, livsport.get(0), Toast.LENGTH_SHORT).show();
                         horizontaladaptrliv adaptt1 = new horizontaladaptrliv(livsport,livevent,sportid,sphome,spaway,sptime);
                          recyclerView2.setAdapter(adaptt1);

                      } catch (JSONException e) {
                          e.printStackTrace();
                      }

                  }
              }
      );
      asyncHttpClient.get(url3,requestParams,new JsonHttpResponseHandler()
              {
                  @Override
                  public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                      super.onSuccess(statusCode, headers, response);
                      JSONObject jsonObject=response;
                      try {
                          JSONArray array3=jsonObject.getJSONArray("tvevents");
                          for(int i=0;i<array3.length();i++){
                              JSONObject obj=array3.getJSONObject(i);
                              String tvvid=obj.getString("id");
                              String tvsort=obj.getString("strSport");
                              String tvvevent=obj.getString("strEvent");
                              String tvcountry=obj.getString("strCountry");
                              String tvlogo=obj.getString("strLogo");
                              String tvtim=obj.getString("strTime");
                              tvid.add(tvvid);
                              tvsport.add(tvsort);
                              tvevent.add(tvvevent);
                              tvimag.add(tvlogo);
                          }
                          LinearLayoutManager linearLayoutManager=new LinearLayoutManager(MainActivity.this, RecyclerView.HORIZONTAL, false);
                          recyclerView3.setLayoutManager(linearLayoutManager);
                          horizontaladaptertv adaptv = new horizontaladaptertv(tvid,tvevent,tvsport,tvimag);
                          recyclerView3.setAdapter(adaptv);

                      } catch (JSONException e) {
                          e.printStackTrace();
                      }
                  }
              }
      );
    }

    private String getCurrentDate() {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);
        Log.e("Todaysdate",formattedDate);

return formattedDate;

    }

    public class horizontaladapter extends RecyclerView.Adapter<horizontaladapter.myviewholder>{
        private ArrayList<String>text,image,description,id;

        public horizontaladapter(ArrayList<String>text,ArrayList<String>image,ArrayList<String>description,ArrayList<String>id) {
            this.text = text;
            this.image = image;
            this.description=description;
            this.id=id;
        }
        @NonNull
        @Override
        public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemview= LayoutInflater.from(parent.getContext()).inflate(R.layout.bagroun,parent,false);
            return new myviewholder(itemview);
        }

        @Override
        public void onBindViewHolder(@NonNull myviewholder holder, final int position) {
            Glide
                    .with(MainActivity.this)/////context
                    .load(image.get(position))///image
                    .placeholder(R.drawable.ima)
                    .into(holder.img);

           holder.txt.setText(text.get(position));
           holder.itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   String title1=text.get(position);
                   String description1=description.get(position);
                   String image1=image.get(position);
                   SharedPreferences sp=getApplicationContext().getSharedPreferences("P",MODE_PRIVATE);
                   SharedPreferences.Editor sportt=sp.edit();
                   sportt.putString("k1",title1);
                   sportt.putString("k2",description1);
                   sportt.putString("k3",image1);
                   sportt.apply();
                   Intent intent=new Intent(MainActivity.this,Next.class);
                   startActivity(intent);
               }
           });

        }

        @Override
        public int getItemCount() {
            return text.size();
        }

        public  class myviewholder extends RecyclerView.ViewHolder{
            TextView txt;
            ImageView img;

            public myviewholder(@NonNull View itemView) {
                super(itemView);
                txt=itemView.findViewById(R.id.txt);
                img=itemView.findViewById(R.id.profile_image);

            }
        }
    }
    public class horizontalladapter extends RecyclerView.Adapter<horizontalladapter.myvieholder> {
        public String[]colours={ "000272","ff0000","44000d","6b778d","ffd700","a7d129","00818a","e41f7b","ff5733","f6c90e","ff004d","5f85db"};
        private ArrayList<String>league,sport;

        public horizontalladapter(ArrayList<String>league,ArrayList<String>sport,ArrayList<String>sptid) {
            this.league= league;
            this.sport = sport;

        }

        @NonNull
        @Override
        public myvieholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemview= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout,parent,false);
            return new myvieholder(itemview);

        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onBindViewHolder(@NonNull myvieholder holder, int position) {
            GradientDrawable shape = new GradientDrawable();
           // shape.setShape(GradientDrawable.RECTANGLE);

          //  shape.setCornerRadius((float) 30);
            shape.setColor(Color.parseColor ("#"+colours[new Random().nextInt(11)]));


            holder.linearLayout.setBackground(shape);

        holder.sportname.setText(league.get(position));
        holder.sportleague.setText(sport.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,team.class);
                startActivity(i);
            }
        });
        }

        @Override
        public int getItemCount() {
            return league.size();
        }

        public  class myvieholder extends RecyclerView.ViewHolder{
            TextView sportleague,sportname;
            LinearLayout linearLayout;
            CardView llayout;

            public myvieholder(@NonNull View itemView) {
                super(itemView);
                llayout=itemView.findViewById(R.id.rlay);
                linearLayout=itemView.findViewById(R.id.rlayout);

                sportleague=itemView.findViewById(R.id.textView);
                sportname=itemView.findViewById(R.id.text1);
            }
        }
    }
    public class horizontaladaptrliv extends RecyclerView.Adapter<horizontaladaptrliv.myviewholder>{
        private ArrayList<String>livsport,livevent,sportid,sphome,spaway,sptime;

        public horizontaladaptrliv(ArrayList<String>livsport,ArrayList<String>livevent,ArrayList<String>sportid,ArrayList<String>sphome,ArrayList<String>spaway,ArrayList<String>sptime) {
            this.livsport= livsport;
            this.livevent = livevent;
            this.sportid=sportid;
            this.sphome=sphome;
            this.spaway=spaway;
            this.sptime=sptime;

        }
        @NonNull
        @Override
        public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemview= LayoutInflater.from(parent.getContext()).inflate(R.layout.liv,parent,false);
            return new myviewholder(itemview);

        }

        @Override
        public void onBindViewHolder(@NonNull myviewholder holder, final int position) {
        holder.textView.setText(livevent.get(position));
        holder.textView1.setText(livsport.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.live);
                TextView event,name,time,home,away,score1,score2;
                event=dialog.findViewById(R.id.eventli);
                name=dialog.findViewById(R.id.name);
                time=dialog.findViewById(R.id.timeli);
                home=dialog.findViewById(R.id.homeli);
                away=dialog.findViewById(R.id.away);
                score1=dialog.findViewById(R.id.score);
                score2=dialog.findViewById(R.id.awayscore);
                event.setText(livevent.get(position));
                name.setText(livsport.get(position));
                time.setText(sptime.get(position));
                home.setText(sphome.get(position));
                away.setText(spaway.get(position));
                dialog.show();

            }
        });

        }

        @Override
        public int getItemCount() {
            return livevent.size();
        }

        public class myviewholder extends RecyclerView.ViewHolder{
        TextView textView,textView1;
            public myviewholder(@NonNull View itemView) {
                super(itemView);
                textView=itemView.findViewById(R.id.event);
                textView1=itemView.findViewById(R.id.sport);
            }
        }
    }
    public class horizontaladaptertv extends RecyclerView.Adapter<horizontaladaptertv.myviewholder> {
        private ArrayList<String>tvid,tvevent,tvsport,tvimag;

        public horizontaladaptertv(ArrayList<String>tvid,ArrayList<String>tvevent,ArrayList<String>tvsport,ArrayList<String>tvimag) {
            this.tvid=tvid;
            this.tvevent =tvevent;
            this.tvsport =tvsport;
            this.tvimag=tvimag;

        }
        @NonNull
        @Override
        public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemview= LayoutInflater.from(parent.getContext()).inflate(R.layout.tv,parent,false);
            return new myviewholder(itemview);
        }

        @Override
        public void onBindViewHolder(@NonNull myviewholder holder, int position) {
            Glide
                    .with(MainActivity.this)/////context
                    .load(tvimag.get(position))///image
                    .placeholder(R.drawable.ima)
                    .into(holder.imageView);
        holder.textView.setText(tvevent.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,Tvshow.class);
                startActivity(i);
            }
        });
        }

        @Override
        public int getItemCount() {
            return tvevent.size();
        }

        public class myviewholder extends RecyclerView.ViewHolder{
        TextView textView;
        ImageView imageView;
            public myviewholder(@NonNull View itemView) {
                super(itemView);
                textView=itemView.findViewById(R.id.tvtxt);
                imageView=itemView.findViewById(R.id.tvimg);
            }
        }
    }

}
