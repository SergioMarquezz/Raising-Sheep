package com.android.sergiomarquez.raisingsheep1.BaseDeDatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class SQLiteHelperBD extends SQLiteOpenHelper {

    private static final String bd_name = "raising_sheep.db"; //Variable para el nombre de la base de datos
    private static final int bd_scheme_version = 1; //Variable para la version de la base de datos




    public SQLiteHelperBD(Context context) {
        super(context, bd_name, null, bd_scheme_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(BDManager.create_table_registro);
        db.execSQL(BDManager.create_table_usuarios);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " +BDManager.name_table_registro);
        db.execSQL("DROP TABLE IF EXISTS " +BDManager.name_table_usr);
        onCreate(db);
    }
}
