package com.ideabox.bloodbank;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {

    DonerData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        int id = getIntent().getIntExtra("Id", 0);
        data = dbHelper.getDonerDetail(this, id);

        TextView name = (TextView) findViewById(R.id.name);
        TextView bloodgrp = (TextView) findViewById(R.id.bloodgrp);
        TextView phone = (TextView) findViewById(R.id.phone);
        TextView email = (TextView) findViewById(R.id.email);
        TextView city = (TextView) findViewById(R.id.city);
        TextView area = (TextView) findViewById(R.id.area);
        TextView addr = (TextView) findViewById(R.id.addr);

        name.setText(data.full_name);
        bloodgrp.setText(bloodgrp.getText() + data.bloodgrp);
        phone.setText(phone.getText() + data.phone);
        email.setText(email.getText() + data.email);
        city.setText(city.getText() + data.city);
        area.setText(area.getText() + data.area);
        addr.setText(addr.getText() + data.addr);

    }

    public void call(View v) {

        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + data.phone));
        try
        {
            startActivity(intent);
        }
        catch (Exception e)
        {
            Toast.makeText(this,"Unable to place call.\nCheck app permission.",Toast.LENGTH_SHORT).show();
            Log.d("info", "call: "+e);
        }
    }
}
