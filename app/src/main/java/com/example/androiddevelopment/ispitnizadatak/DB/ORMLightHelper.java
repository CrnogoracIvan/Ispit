package com.example.androiddevelopment.ispitnizadatak.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;


    public class ORMLightHelper extends OrmLiteSqliteOpenHelper{

        private static final String DATABASE_NAME    = "DataBase.db";
        private static final int    DATABASE_VERSION = 1;

        private Dao<Telefon, Integer> mTelefonDao = null;
        private Dao<Kontakt, Integer> mKontaktDao = null;

        public ORMLightHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
            try {
                TableUtils.createTable(connectionSource, Telefon.class);
                TableUtils.createTable(connectionSource, Kontakt.class);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
            try {
                TableUtils.dropTable(connectionSource, Telefon.class, true);
                TableUtils.dropTable(connectionSource, Kontakt.class, true);
                onCreate(database, connectionSource);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        public Dao<Telefon, Integer> getMovieDao() throws SQLException {
            if (mTelefonDao == null) {
                mTelefonDao = getDao(Telefon.class);
            }

            return mTelefonDao;
        }

        public Dao<Kontakt, Integer> getActorDao() throws SQLException {
            if (mKontaktDao == null) {
                mKontaktDao = getDao(Kontakt.class);
            }

            return mKontaktDao;
        }

        //obavezno prilikom zatvarnaj rada sa bazom osloboditi resurse
        @Override
        public void close() {
            mTelefonDao = null;
            mKontaktDao = null;

            super.close();
        }
    }
