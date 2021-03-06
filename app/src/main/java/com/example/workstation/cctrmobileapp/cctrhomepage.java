package com.example.workstation.cctrmobileapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class cctrhomepage extends ActionBarActivity {

    Button cctr_news;
    Button cctr_ct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cctrhomepage);

        //Locate buttons in activity_cctrhomepage.xml
        cctr_news = (Button)findViewById(R.id.cctr_news);
        cctr_ct = (Button)findViewById(R.id.cctr_ct);

        //Capture button click for cctr_ct to open cctr_ct homepage
        cctr_ct.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent openCCTR_CT = new Intent(cctrhomepage.this, cctr_clinical_trials.class);

                startActivity(openCCTR_CT);

            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cctrhomepage, menu);
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
        return super.onOptionsItemSelected(item);
    }
}
