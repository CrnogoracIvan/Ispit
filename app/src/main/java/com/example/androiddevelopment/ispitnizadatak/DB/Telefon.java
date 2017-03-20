package com.example.androiddevelopment.ispitnizadatak.DB;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by androiddevelopment on 20.3.17..
 */

@DatabaseTable(tableName = Telefon.TABLE_NAME_USERS)

public class Telefon {

    public static final String TABLE_NAME_USERS = "telefoni";
    public static final String FIELD_NAME_ID = "id";
    public static final String FIELD_NAME_HOME = "kucni telefon";
    public static final String FIELD_NAME_BUSS = "poslovni telefon";
    public static final String FIELD_NAME_MOB = "mobilni telefon";
    public static final String FIELD_NAME_USER = "user";


    @DatabaseField(columnName = FIELD_NAME_ID, generatedId = true)
    private int tId;

    @DatabaseField (columnName = TABLE_NAME_USERS)
    private String telefon;

    @DatabaseField (columnName = FIELD_NAME_HOME)
    private String kucniTelefon;

    @DatabaseField (columnName = FIELD_NAME_MOB)
    private String mobilniTelefon;

    @DatabaseField (columnName = FIELD_NAME_BUSS)
    private String poslovniTelefon;

    @DatabaseField (columnName = FIELD_NAME_USER, foreign = true, foreignAutoRefresh = true)
    private Kontakt mUser;

    public Telefon() {
    }

    public int gettId() {
        return tId;
    }

    public void settId(int tId) {
        this.tId = tId;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getKucniTelefon() {
        return kucniTelefon;
    }

    public void setKucniTelefon(String kucniTelefon) {
        this.kucniTelefon = kucniTelefon;
    }

    public String getMobilniTelefon() {
        return mobilniTelefon;
    }

    public void setMobilniTelefon(String mobilniTelefon) {
        this.mobilniTelefon = mobilniTelefon;
    }

    public String getPoslovniTelefon() {
        return poslovniTelefon;
    }

    public void setPoslovniTelefon(String poslovniTelefon) {
        this.poslovniTelefon = poslovniTelefon;
    }

    public Kontakt getmUser() {
        return mUser;
    }

    public void setmUser(Kontakt mUser) {
        this.mUser = mUser;
    }
}
