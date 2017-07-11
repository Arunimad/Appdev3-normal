package com.delta.appdev3;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    JSONObject job = null;
    String  ii;
    TextView text2;
    EditText edit1;
    ImageView image1;
    StringBuilder stringBuilder ;
    TextView textt1;
    TextView textt2;
    TextView textt3;
    TextView textt4;
    TextView textt5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text2 = (TextView) findViewById(R.id.responseView);
        edit1 = (EditText) findViewById(R.id.edit1);
        image1 = (ImageView) findViewById(R.id.image1);
        textt1 = (TextView) findViewById(R.id.textt1);
        textt2 = (TextView) findViewById(R.id.textt2);
        textt3 = (TextView) findViewById(R.id.textt3);
        textt4 = (TextView) findViewById(R.id.textt4);
        textt5 = (TextView) findViewById(R.id.textt5);













        Button b1 = (Button) findViewById(R.id.button1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ii = edit1.getText().toString();




                RetrieveFeedTask a = new RetrieveFeedTask();
                a.execute();












            }
        });



    }




    class RetrieveFeedTask extends AsyncTask<Void, Void, String> {



        protected void onPreExecute() {

        }

        public String doInBackground(Void... urls) {


            try {
                URL url = new URL("http://pokeapi.co/api/v2/pokemon/" +ii+"/");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                stringBuilder= new StringBuilder();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }





                    bufferedReader.close();

                    return stringBuilder.toString();
                }
                finally{
                    urlConnection.disconnect();
                }
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(String response) {


            try {
                job=new JSONObject(stringBuilder.toString());
                text2.setText(job.getString("name"));
                Picasso.with(MainActivity.this).load(job.getJSONObject("sprites").getString("front_default")).resize(500,500).into(image1);
                textt1.setText(job.getString("height"));
                textt2.setText(job.getString("weight"));
                textt3.setText(job.getJSONArray("types").getJSONObject(0).getJSONObject("type").getString("name"));
                textt4.setText(job.getJSONArray("abilities").getJSONObject(0).getJSONObject("ability").getString("name"));
                textt5.setText(job.getString("base_experience"));




            }catch (JSONException e){}







        }
    }




}
