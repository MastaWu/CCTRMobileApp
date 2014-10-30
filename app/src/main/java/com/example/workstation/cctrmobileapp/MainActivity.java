package com.example.workstation.cctrmobileapp;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;


public class MainActivity extends ActionBarActivity {

    ArrayList<String> items = new ArrayList<String>();
    ArrayList<String> sharedData = new ArrayList<String>();
    static InputStream is = null;
    private static String url = "https://mobile-api.forteresearch.com/protocols";
    JSONArray people = null;
    private static final String TAG_ID = "id";
    private static final String TAG_PROTOCOL = "protocolNo";
    private static final String TAG_TITLE = "title";
    private static final String TAG_SHORTTITLE = "shortTitle";
    private static final String TAG_STATUS = "status";
    private static final String TAG_NAME = "name";
    static JSONObject jObj = null;
    static String json = "";
    boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new MyTasks().execute();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cctr_clinical_trials, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.menu_favorite){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class MyTasks extends AsyncTask<URL, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(URL... urls) {
            try {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(url);
                HttpResponse httpResponse = httpClient.execute(httpGet);

                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                InputStream inputStream = is;
                InputStreamReader reader = new InputStreamReader(inputStream);
                BufferedReader in = new BufferedReader(reader);
                StringBuilder sb = new StringBuilder();
                String line = null;

                while ((line = in.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                json = sb.toString();
            } catch (Exception e) {
                Log.e("Buffer Error", "Error converting result " + e.toString());
            }
            try {

                JSONArray people = new JSONArray(json);

                for (int i = 0; i < people.length(); i++) {
                    JSONObject p = people.getJSONObject(i);
                    String id = p.getString(TAG_ID);
                    String protocolNo = p.getString(TAG_PROTOCOL);
                    String title = p.getString(TAG_TITLE);
                    String shortTitle = p.getString(TAG_SHORTTITLE);
                    String status = p.getString(TAG_STATUS);
                    String name = p.getString(TAG_NAME);

                    items.add("ID: " + id + "\nProtocol Number: " + protocolNo + "\nTitle: " + title + "\nShort Title: " + shortTitle + "\nStatus: " + status + "\nName: " + name);
/*                    items.add("Protocol Number: " + protocolNo);
                    items.add("Title: " + title);
                    items.add("Short Title: " + shortTitle);
                    items.add("Status: " + status);
                    items.add("Name: " + name);*/
                }

            } catch (JSONException e) {
                Log.e("JSON Parser", "Error parsing data " + e.toString());
            }
            return jObj;
        }

        @SuppressWarnings({"unchecked", "rawtypes"})
        public void onPostExecute(JSONObject json) {
            final ListView myListView = (ListView) findViewById(R.id.list);
            myListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            myListView.setAdapter(new ArrayAdapter(MainActivity.this, R.layout.custom_textview, items));
            LoadPreferences();

            myListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    String itemAtPosition = (String) myListView.getItemAtPosition(i);

                    SavePreferences("jsonData", itemAtPosition);

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        }



        protected void SavePreferences(String key, String value) {

            SharedPreferences jsonData = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
            SharedPreferences.Editor editor = jsonData.edit();
            editor.putString(key, value);
            editor.commit();

        }

        protected void LoadPreferences(){

            SharedPreferences loadData = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
            String dataSet = loadData.getString("jsonData", "none available");

            items.add(dataSet);

        }

    }

}
