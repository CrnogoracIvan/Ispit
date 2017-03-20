package com.example.androiddevelopment.ispitnizadatak.DB;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by androiddevelopment on 20.3.17..
 */

@DatabaseTable(tableName = Kontakt.TABLE_NAME_USERS)

public class Kontakt {

    public static final String TABLE_NAME_USERS = "ime";
    public static final String TABLE_NAME_USERS_SN = "prezime";
    public static final String FIELD_NAME_ID = "id";
    public static final String TABLE_NAME_PHOTO = "slika";
    public static final String TABLE_NAME_ADRESS = "adresa";
    public static final String TABLE_NAME_PHONES = "telefoni";


    @DatabaseField(columnName = FIELD_NAME_ID, generatedId = true)
    private int id;

    @DatabaseField (columnName = TABLE_NAME_USERS)
    private String ime;

    @DatabaseField (columnName = TABLE_NAME_USERS_SN)
    private String prezime;

    @DatabaseField (columnName = TABLE_NAME_PHOTO)
    private String slika;

    @DatabaseField (columnName = TABLE_NAME_ADRESS)
    private String adresa;

    @ForeignCollectionField(columnName = Kontakt.TABLE_NAME_PHONES, eager = true)
    private ForeignCollection<Telefon> telefoni;


    public Kontakt() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getSlika() {
        return slika;
    }

    public void setSlika(String slika) {
        this.slika = slika;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public ForeignCollection<Telefon> getTelefoni() {
        return telefoni;
    }

    public void setTelefoni(ForeignCollection<Telefon> telefoni) {
        this.telefoni = telefoni;
    }

    @Override
    public String toString() {
        return  "ime i prezime: " + ime + " " + prezime;
    }
}
