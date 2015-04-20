package com.example.workstation.cctrmobileapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
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

//"driver" class
//refers to when you get the search results page
public class MainActivity extends ActionBarActivity {

    ArrayList<String> items = new ArrayList<String>();
    static InputStream is = null;

    private static String url = "https://mobile-api.forteresearch.com/protocols";

    private static final String TAG_ID = "id";
    private static final String TAG_PROTOCOL = "protocolNo";
    private static final String TAG_TITLE = "title";
    private static final String TAG_SHORTTITLE = "shortTitle";
    private static final String TAG_STATUS = "status";
    private static final String TAG_NAME = "name";

    static JSONObject jObj = null;
    static String json = "";

    sqliteDatabase database;

    ArrayList arrayList;
    ArrayList<String> index = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        database = new sqliteDatabase(this, "cctr.db", null, 1);
        database.clearSearchTable();
        database.getWritableDatabase();

        new MyTasks().execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cctr_clinical_trials, menu);
        return true;
    }

    @Override
    // Handle action bar item clicks here. The action bar will automatically handle clicks on the Home/Up button, so long as you specify a parent activity in AndroidManifest.xml.
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //go back home
        if (id == R.id.action_home) {
            Intent actionHome = new Intent(this,cctrhomepage.class);
            startActivity(actionHome);
            return true;
        }

        //When you select "Favorites" from the top right after selecting the trials, it gets added.
        //The only time this works is when you are in the favorites menu.
        if (id == R.id.menu_favorite){
            for(String s : index){
                database.insertFavoritesData(s);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    //Async for background tasks
    private class MyTasks extends AsyncTask<URL, Void, JSONObject> {

        @Override
        //background operations
        protected JSONObject doInBackground(URL... urls) {

            try {

                //HTTP client that supports streaming uploads and downloads
                DefaultHttpClient httpClient = new DefaultHttpClient();

                //grabbing data from url
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
                //get the main content from URL
                InputStream inputStream = is;
                InputStreamReader reader = new InputStreamReader(inputStream);

                //bufferedreader reads data from InputStream until the Buffer is full
                BufferedReader in = new BufferedReader(reader);

                //stores data
                StringBuilder sb = new StringBuilder();

                String line = null;

                //read in the data from the buffer until nothing is left
                while ((line = in.readLine()) != null) {

                    //add data from the buffer to the StringBuilder
                    sb.append(line + "\n");
                }

                is.close();
                json = sb.toString();

            } catch (Exception e) {
                Log.e("Buffer Error", "Error converting result " + e.toString());
            }

            try {

                //holds Key Value pairs from a JSON source
                JSONArray people = new JSONArray(json);

                for (int i = 0; i < people.length(); i++) {
                    JSONObject p = people.getJSONObject(i);

                    String id = p.getString(TAG_ID);
                    String protocolNo = p.getString(TAG_PROTOCOL);
                    String title = p.getString(TAG_TITLE);
                    String shortTitle = p.getString(TAG_SHORTTITLE);
                    String status = p.getString(TAG_STATUS);
                    String name = p.getString(TAG_NAME);

                    database.insertSearchData(id, protocolNo, title, shortTitle, status, name);

                    items.add(
                            "ID: " + id
                            + "\nProtocol Number: " + protocolNo
                            + "\nTitle: " + title
                            + "\nShort Title: " + shortTitle
                            + "\nStatus: " + status
                            + "\nName: " + name);
                }

            } catch (JSONException e) {
                Log.e("JSON Parser", "Error parsing data " + e.toString());
            }
            return jObj;
        }

        @SuppressWarnings({"unchecked", "rawtypes"})
        //GUI manipulation
        public void onPostExecute(JSONObject json) {

            //from the activity_main.xml
            final ListView myListView = (ListView) findViewById(R.id.list);

            arrayList = database.fetchSearchData();

            myListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

            ArrayAdapter adapter = new ArrayAdapter(MainActivity.this, R.layout.custom_textview, arrayList);

            myListView.setAdapter(adapter);


            myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    /*for(String s : index){

                        if(s.equals(""+i)){

                            index.remove(""+i);
                            Toast.makeText(getApplicationContext(), "Removed " + i, Toast.LENGTH_SHORT).show();

                        } else {

                            Toast.makeText(getApplicationContext(), "Added " + i, Toast.LENGTH_SHORT).show();
                        }
                    }*/

                    index.add(""+i);
                    Toast.makeText(getApplicationContext(), "Click Favorites to store: " + (i+1) + " item(s)", Toast.LENGTH_SHORT).show();
                }
            });

        }

    }

}
