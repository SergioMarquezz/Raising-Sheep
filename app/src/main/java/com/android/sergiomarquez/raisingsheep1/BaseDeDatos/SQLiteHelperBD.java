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

      db.execSQL(BDManager.create_table_usuarios);
      db.execSQL(BDManager.create_table_borregos);
      db.execSQL(BDManager.create_table_etapas);
      db.execSQL(BDManager.create_table_razas);

      db.execSQL(BDManager.create_table_enfermedades);
      db.execSQL(BDManager.create_table_salidas);
      db.execSQL(BDManager.create_table_diversos_sin);
      db.execSQL(BDManager.create_table_consultas);
      db.execSQL(BDManager.create_table_diagnostico);
      db.execSQL(BDManager.create_table_sitomas);
      db.execSQL(BDManager.create_table_detalle_ds);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " +BDManager.name_table_usr);
        db.execSQL("DROP TABLE IF EXISTS " +BDManager.name_table_borregos);
        db.execSQL("DROP TABLE IF EXISTS " +BDManager.name_table_etapas);
        db.execSQL("DROP TABLE IF EXISTS " +BDManager.name_table_razas);
        db.execSQL("DROP TABLE IF EXISTS " +BDManager.name_table_enfermedades);
        db.execSQL("DROP TABLE IF EXISTS " +BDManager.name_table_sintomas);
        db.execSQL("DROP TABLE IF EXISTS " +BDManager.name_table_diversos_sin);
        db.execSQL("DROP TABLE IF EXISTS " +BDManager.name_table_detalle_diversos_sin);
        db.execSQL("DROP TABLE IF EXISTS " +BDManager.name_table_consultas);
        db.execSQL("DROP TABLE IF EXISTS " +BDManager.name_table_diagnostico);
        db.execSQL("DROP TABLE IF EXISTS " +BDManager.name_table_salidas);

        onCreate(db);
    }
}
