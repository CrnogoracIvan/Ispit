package com.example.androiddevelopment.ispitnizadatak.Activities;

import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.androiddevelopment.ispitnizadatak.DB.Kontakt;
import com.example.androiddevelopment.ispitnizadatak.DB.ORMLightHelper;
import com.example.androiddevelopment.ispitnizadatak.Dialogs.oAppDialog;
import com.example.androiddevelopment.ispitnizadatak.Preferences.Preferences;
import com.example.androiddevelopment.ispitnizadatak.R;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ORMLightHelper databaseHelper;
    private SharedPreferences SharedPrefs;

    public static String CONTACT_KEY = "ACTOR_KEY";
    public static String NOTIF_TOAST = "notif_toast";
    public static String NOTIF_STATUS = "notif_statis";


    ///Metoda koja komunicira sa bazom podataka
    public ORMLightHelper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, ORMLightHelper.class);
        }
        return databaseHelper;
    }

    //Refresh metoda
    private void refresh() {
        ListView listview = (ListView) findViewById(R.id.lista_kontakata);

        if (listview != null) {
            ArrayAdapter<Kontakt> adapter = (ArrayAdapter<Kontakt>) listview.getAdapter();

            if (adapter != null) {
                try {
                    adapter.clear();
                    List<Kontakt> list = getDatabaseHelper().getKontaktDao().queryForAll();

                    adapter.addAll(list);

                    adapter.notifyDataSetChanged();
                } catch (java.sql.SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //Metoda za prikaz notifikacija
    private void prikaziStatusPoruka(String message) {

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.ic_notif_icon);
        mBuilder.setContentTitle("Ispit - obavestenje");
        mBuilder.setContentText(message);

        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.ic_menu_add);

        mBuilder.setLargeIcon(bm);
        // notificationID allows you to update the notification later on.
        mNotificationManager.notify(1, mBuilder.build());
    }




//----------------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ubacujem funkcionalnost preferences/a
        SharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        final ListView listView = (ListView) findViewById(R.id.lista_kontakata);
        try {
            List<Kontakt> list = getDatabaseHelper().getKontaktDao().queryForAll();

            ListAdapter adapter = new ArrayAdapter<>(MainActivity.this, R.layout.list_item, list);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Kontakt p = (Kontakt) listView.getItemAtPosition(position);

                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                    intent.putExtra(CONTACT_KEY, p.getId());
                    startActivity(intent);
                }
            });

        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    protected void onResume() {
        super.onResume();

        refresh();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

//            //DIALOG ZA UNOS PODATAKA o novom kontaktu
           case R.id.add_new_contact:

              final Dialog dialog = new Dialog(this);
               dialog.setContentView(R.layout.add_cotact_dialog_layout);

               //akcije koja se desava kada se dugme klikne
               Button add = (Button) dialog.findViewById(R.id.dodaj_kontakt);
               add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText uzetImeIzDialoga = (EditText) dialog.findViewById(R.id.kontakt_name);
                        EditText uzetPrezimeIzDialoga = (EditText) dialog.findViewById(R.id.kontakt_prezime);
                        EditText uzetSlikuIzDialoga = (EditText) dialog.findViewById(R.id.kontakt_photo);
                        EditText uzetAdresuIzDialog = (EditText) dialog.findViewById(R.id.kontakt_adresa);

                        Kontakt k = new Kontakt();
                        k.setIme(uzetImeIzDialoga.getText().toString());
                        k.setPrezime(uzetPrezimeIzDialoga.getText().toString());
                        k.setSlika(uzetSlikuIzDialoga.getText().toString());
                        k.setAdresa(uzetAdresuIzDialog.getText().toString());


                        try {

                            getDatabaseHelper().getKontaktDao().create(k);

                            boolean toast = SharedPrefs.getBoolean(NOTIF_TOAST, false);
                            boolean status = SharedPrefs.getBoolean(NOTIF_STATUS, false);

                            if (toast) {
                                Toast.makeText(MainActivity.this, "Dodat nov kontakt", Toast.LENGTH_SHORT).show();
                            }

                            if (status) {
                                prikaziStatusPoruka("Info - Dodat je nov kontakt");
                            }

                            //REFRESH liste
                            refresh();

                        } catch (java.sql.SQLException e) {
                            e.printStackTrace();
                        }

                        dialog.dismiss();

                    }
                });
                dialog.show();
                break;
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
