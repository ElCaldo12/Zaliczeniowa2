package com.example.kom.zaliczeniowa2;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    Button b;
    ImageView i;
    TextView textView;
    String s;
    ListView list;
    BazaDanych bd = new BazaDanych(this);
    String k;

    Intent intent;
    Context context;

    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    public static final String Pozwolenie = "ALLOWED";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        b = (Button) findViewById(R.id.button2);

        list = (ListView) findViewById(R.id.list);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (getFromPref(this, Pozwolenie)) {
                showSettingsAlert();
            } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                    showAlert();

                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
                }
            }
        }


        View.OnClickListener o = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obsAparatu();
                CzyZapisac();
            }

        };
        b.setOnClickListener(o);
    }
public void Exit(View view)
{
    this.finish();
}

    public void CzyZapisac()
    {
        String gps="0.0 X 0.0";
        //gps=PodanieLokalizacji();
        bd.dodajElemnt(k, gps, s);
    }


    public void obsAparatu() {
        Intent in = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File image = new File("/storage/7E16-53D9", "MyImage");
        image.mkdirs();

        k = new SimpleDateFormat("yyyy-MM-dd=HH.mm.ss").format(new Date());
        File i = new File(image,k+".jpg");
        Uri urisaved = Uri.fromFile(i);
        in.putExtra(MediaStore.EXTRA_OUTPUT, urisaved);

        s = "/storage/7E16-53D9/MyImage" + k+".jpg";
        CzyZapisac();
        startActivityForResult(in, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");
            i.setImageBitmap(bitmap);
        }
    }

Button clear;

    public void Wyswietl(View v) {

        clear=(Button) findViewById(R.id.button);

        final String [] elementy_listy=new String [100];
        int iterator=0;
        list.setVisibility(View.VISIBLE);
        clear.setVisibility(View.VISIBLE);

        for(Zdjecie x: bd.dajWszyskie())
        {
            String sx = "" + x.getNr();
            elementy_listy[iterator] = sx + " " + x.getNazwa() + " " + x.getLokalizacja();
            iterator++;
        }
            ListView prosta_lista = (ListView) findViewById(R.id.list);

            ArrayAdapter adapter_listy = new ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, elementy_listy);
            prosta_lista.setAdapter(adapter_listy);

        prosta_lista.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent, View v, int pos, long id)
            {   String pozycja="";
                for(Zdjecie zd:bd.dajWszyskie())
                    if(zd.getNr()==pos)
                    {
                       pozycja=zd.getLokalizacja();
                    }

                context = getApplicationContext();
                intent = new Intent(context, Wyswietlanie.class);
                intent.putExtra("name", pozycja);
                startActivity(intent);
            }
        });

/*
        List<Zdjecie> listazdjec;
        listazdjec = bd.dajWszyskie();
        ArrayAdapter<Zdjecie> adapter = new ArrayAdapter<Zdjecie>(this, R.layout.activity_main, listazdjec);
        list.setAdapter(adapter);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                context = getApplicationContext();
                intent = new Intent(context, Wyswietlanie.class);
                intent.putExtra("nazwa", String.valueOf(id));
                startActivity(intent);
            }
        });
*/

        //String  json = new Gson().toString(bd);
        //Intent i = new Intent(this, Wyswietlanie.class);
        //i.putExtra("nazwa", json);
        // startActivity(i);
    }

    public void Clear(View view)
    {
        list.setVisibility(View.INVISIBLE);
        clear.setVisibility(View.INVISIBLE);
    }

    public String  PodanieLokalizacji() {
        LocationManager lm;
        Criteria kr;
        Location loc;
        String najDostawca;

        kr = new Criteria();
        lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        najDostawca = lm.getBestProvider(kr,true);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }
        loc = lm.getLastKnownLocation(najDostawca);

        return loc.getLongitude()+" X "+loc.getLatitude();

    }

    private void showSettingsAlert()
    {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Uwaga");
        alertDialog.setMessage("Aplikacja wymaga dostepu do Aparatu.");

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Odmawiam", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ustawienia",new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
                //startInstalledAppDetailsActivity(MainActivity.this);
            }
        });

        alertDialog.show();
    }



    private void showAlert()
    {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("uwaga");
        alertDialog.setMessage("Aplikacja wymaga dostepu do Aparatu");

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Odmawiam", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
                finish();
            }
        });

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Pozwalam", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CAMERA},CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
            }
        });
        alertDialog.show();
    }


    public static Boolean getFromPref(Context context, String key)
    {
        SharedPreferences myPrefs = context.getSharedPreferences("camera_pref", Context.MODE_PRIVATE);
        return (myPrefs.getBoolean(key, false));
    }

}

