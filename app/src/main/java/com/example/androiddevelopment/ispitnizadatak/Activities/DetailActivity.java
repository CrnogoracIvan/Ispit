package com.example.androiddevelopment.ispitnizadatak.Activities;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RatingBar;

import com.example.androiddevelopment.ispitnizadatak.DB.ORMLightHelper;
import com.example.androiddevelopment.ispitnizadatak.DB.Kontakt;
import com.example.androiddevelopment.ispitnizadatak.R;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;

public class DetailActivity extends AppCompatActivity {

    private ORMLightHelper databaseHelper;
    private SharedPreferences prefs;
    private Kontakt k;

    private EditText ime;
    private EditText prezime;
    private EditText photo;
    private EditText adresa;


    public DetailActivity() {
    }


    ///Metoda koja komunicira sa bazom podataka
    public ORMLightHelper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, ORMLightHelper.class);
        }
        return databaseHelper;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        //JAKO BITAN RED KODA - Na ovaj nacin se ubacuje tacno onaj glumac na kojeg smo kliknuli!
        int key = getIntent().getExtras().getInt(MainActivity.CONTACT_KEY);

        //Uzima polja iz baze i ubacuje ih u polja u aktivitiju
        try {
            k = getDatabaseHelper().getKontaktDao().queryForId(key);

            ime = (EditText) findViewById(R.id.kontakt_name);
            prezime = (EditText) findViewById(R.id.kontakt_prezime);
            photo = (EditText) findViewById(R.id.kontakt_photo);
            adresa = (EditText) findViewById(R.id.kontakt_adresa);

            ime.setText(k.getIme());
            prezime.setText(k.getPrezime());
            photo.setText(k.getSlika());
            adresa.setText(k.getAdresa());
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        // nakon rada sa bazo podataka potrebno je obavezno
        //osloboditi resurse!
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }
}
