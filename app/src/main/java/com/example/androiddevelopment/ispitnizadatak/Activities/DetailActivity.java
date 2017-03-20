package com.example.androiddevelopment.ispitnizadatak.Activities;

import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androiddevelopment.ispitnizadatak.DB.ORMLightHelper;
import com.example.androiddevelopment.ispitnizadatak.DB.Kontakt;
import com.example.androiddevelopment.ispitnizadatak.R;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;

import static com.example.androiddevelopment.ispitnizadatak.Activities.MainActivity.NOTIF_STATUS;
import static com.example.androiddevelopment.ispitnizadatak.Activities.MainActivity.NOTIF_TOAST;

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

    //kreiranje menija
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //metoda za ispis notifikacija
    private void showStatusMesage(String message){
        NotificationManager mNotificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.ic_notif_icon);
        mBuilder.setContentTitle("Ispitni test");
        mBuilder.setContentText(message);

        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.ic_menu_add);

        mBuilder.setLargeIcon(bm);
        // notificationID allows you to update the notification later on.
        mNotificationManager.notify(1, mBuilder.build());
    }


    private void showMessage(String message){

        //provera podesavanja

        boolean toast = prefs.getBoolean(NOTIF_TOAST, false);
        boolean status = prefs.getBoolean(NOTIF_STATUS, false);

        if (toast){
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }

        if (status){
            showStatusMesage(message);
        }

    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //ubacujem funkcionalnost preferences/a
        prefs = PreferenceManager.getDefaultSharedPreferences(this);


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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
//            case R.id.priprema_add_movie:
//                //OTVORI SE DIALOG UNESETE INFORMACIJE
//                final Dialog dialog = new Dialog(this);
//                dialog.setContentView(R.layout.priprema_add_movie);
//
//                Button add = (Button) dialog.findViewById(R.id.add_movie);
//                add.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        EditText name = (EditText) dialog.findViewById(R.id.movie_name);
//                        EditText genre = (EditText) dialog.findViewById(R.id.movie_genre);
//                        EditText year = (EditText) dialog.findViewById(R.id.movie_year);
//
//                        Movie m = new Movie();
//                        m.setmName(name.getText().toString());
//                        m.setmGenre(genre.getText().toString());
//                        m.setmYear(year.getText().toString());
//                        m.setmUser(a);
//
//                        try {
//                            getDatabaseHelper().getMovieDao().create(m);
//                        } catch (SQLException e) {
//                            e.printStackTrace();
//                        }
//                        //URADITI REFRESH
//                        refresh();
//
//                        showMessage("New movie added to actor");
//
//                        dialog.dismiss();
//                    }
//                });
//
//                dialog.show();
//
//                break;
            case R.id.contact_edit:

                //POKUPITE INFORMACIJE SA EDIT POLJA
                k.setIme(ime.getText().toString());
                k.setPrezime(prezime.getText().toString());
                k.setSlika(photo.getText().toString());


                try {
                    getDatabaseHelper().getKontaktDao().update(k);

                    showMessage("Kontakt podaci azurirani");

                } catch (SQLException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.contact_delete:
                try {
                    getDatabaseHelper().getKontaktDao().delete(k);

                    showMessage("Kontakt izbrisan!");

                    finish(); //moramo pozvati da bi se vratili na prethodnu aktivnost
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }

        return super.onOptionsItemSelected(item);
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
