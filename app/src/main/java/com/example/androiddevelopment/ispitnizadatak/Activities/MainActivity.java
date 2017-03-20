package com.example.androiddevelopment.ispitnizadatak.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.androiddevelopment.ispitnizadatak.DB.ORMLightHelper;
import com.example.androiddevelopment.ispitnizadatak.Dialogs.oAppDialog;
import com.example.androiddevelopment.ispitnizadatak.Preferences.Preferences;
import com.example.androiddevelopment.ispitnizadatak.R;
import com.j256.ormlite.android.apptools.OpenHelperManager;

public class MainActivity extends AppCompatActivity {

    private ORMLightHelper databaseHelper;
    private SharedPreferences SharedPrefs;

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
        setContentView(R.layout.activity_main);

        //ubacujem funkcionalnost preferences/a
        SharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

//            //DIALOG ZA UNOS PODATAKA o novom glumcu
//            case R.id.add_new_actor:
//
//                final Dialog dialog = new Dialog(this);
//                dialog.setContentView(R.layout.add_actor_dialog_layout);
//
//                //akcije koja se desava kada se dugme klikne
//                Button add = (Button) dialog.findViewById(R.id.add_actor);
//                add.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        EditText uzetNameIzDialoga = (EditText) dialog.findViewById(R.id.actor_name);
//                        EditText uzetBioIzDialoga = (EditText) dialog.findViewById(R.id.actor_biography);
//                        RatingBar uzetRatingIzDialoga = (RatingBar) dialog.findViewById(R.id.acrtor_rating);
//                        EditText uzetBirthIzDialog = (EditText) dialog.findViewById(R.id.actor_birth);
//
//                        Actor a = new Actor();
//                        a.setIme(uzetNameIzDialoga.getText().toString());
//                        a.setBiografija(uzetBioIzDialoga.getText().toString());
//                        a.setOcena(uzetRatingIzDialoga.getRating());
//                        a.setDatumRodjenja(uzetBirthIzDialog.getText().toString());
//
//
//                        try {
//
//                            getDatabaseHelper().getActorDao().create(a);
//
//                            boolean toast = SharedPrefs.getBoolean(NOTIF_TOAST, false);
//                            boolean status = SharedPrefs.getBoolean(NOTIF_STATUS, false);
//
//                            if (toast) {
//                                Toast.makeText(MainActivity.this, "Dodat nov glumac", Toast.LENGTH_SHORT).show();
//                            }
//
//                            if (status) {
//                                prikaziStatusPoruka("Added new actor");
//                            }
//
//                            //REFRESH liste
//                            refresh();
//
//                        } catch (java.sql.SQLException e) {
//                            e.printStackTrace();
//                        }
//
//                        dialog.dismiss();
//
//                    }
//                });
//                dialog.show();
//                break;
            //About dialog

            case R.id.about:

                AlertDialog alertDialog = new oAppDialog(this).prepareDialog();
                alertDialog.show();
                break;
            //Dialog za podesavanja.
            case R.id.preferences:
                startActivity(new Intent(MainActivity.this, Preferences.class));
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
