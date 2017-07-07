package com.example.kom.zaliczeniowa2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Dimension;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Kom on 2017-07-06.
 */

public class Wyswietlanie extends AppCompatActivity
{
    String name;
    BazaDanych bd;
    ListView listview;
    ImageView image;
    TextView textView;
    String [] x;
    String Name;
    Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        name = getIntent().getStringExtra("name");
       // x = name.split(" ");
       // Name = x[2];
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wyswietlanie);

      image= (ImageView)findViewById(R.id.imageView);
     //   textView= (TextView)findViewById(R.id.text) ;
      // textView.setText(name);
       setPic();
    }
    private void setPic()
    {

        File imagesFolder = new File(name);
        Uri uriSavedImage = Uri.fromFile(imagesFolder);
        image.setImageURI(uriSavedImage);


    }





}




