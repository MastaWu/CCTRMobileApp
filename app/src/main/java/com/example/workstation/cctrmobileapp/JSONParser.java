package com.example.workstation.cctrmobileapp;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Workstation on 11/3/2014.
 */

    public class JSONParser {

        ArrayList<String> items = new ArrayList<String>();
        static InputStream is = null;
        private static final String TAG_ID = "id";
        private static final String TAG_PROTOCOL = "protocolNo";
        private static final String TAG_TITLE = "title";
        private static final String TAG_SHORTTITLE = "shortTitle";
        private static final String TAG_STATUS = "status";
        private static final String TAG_NAME = "name";
        static JSONObject jObj = null;
        static String json = "";

        JSONObject jsonParse(URI url) {
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

        }


