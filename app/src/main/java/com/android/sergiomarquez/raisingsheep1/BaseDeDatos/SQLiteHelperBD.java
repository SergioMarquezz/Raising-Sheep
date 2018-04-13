package com.android.sergiomarquez.raisingsheep1.BaseDeDatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class SQLiteHelperBD extends SQLiteOpenHelper {

    private static final String bd_name = "raising_sheep.db"; //Variable para el nombre de la base de datos
    private static final int bd_scheme_version1 = 1; //Variable para la version de la base de datos


    public SQLiteHelperBD(Context context) {

        super(context, bd_name, null, bd_scheme_version1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //SE CREAN TODAS LAS TABLAS EN LA BASE DE DATOS

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

        insertarRazas(db, "Pelibuey");
        insertarRazas(db, "Dorper");
        insertarRazas(db, "Dorset");
        insertarRazas(db, "Hampshire");
        insertarRazas(db, "Suffolk");
        insertarRazas(db, "Black belly");

        insertarEtapas(db, "Cordero");
        insertarEtapas(db, "Lactancia");
        insertarEtapas(db, "Ovino cargada");
        insertarEtapas(db, "Gestacion");
        insertarEtapas(db, "Semental");
        insertarEtapas(db, "Cuarentena");

        insertarSalidas(db,"Activo");
        insertarSalidas(db, "Muerte");
        insertarSalidas(db, "Venta");
        insertarSalidas(db, "Enfermedad");

        insertarEnfermedades(db, "Parasitorias");
        insertarEnfermedades(db, "Infecciosas");

        insertarDiversosSintomas(db, "Moco con sangre");
        insertarDiversosSintomas(db, "Estornudos");
        insertarDiversosSintomas(db, "Respiracion dificil");
        insertarDiversosSintomas(db, "Diarrea");
        insertarDiversosSintomas(db, "Come poco");
        insertarDiversosSintomas(db, "Mucosas palidas");
        insertarDiversosSintomas(db, "Dolor");
        insertarDiversosSintomas(db, "Tos seca");
        insertarDiversosSintomas(db, "Diarrea maloliente");
        insertarDiversosSintomas(db, "Perida de peso");
        insertarDiversosSintomas(db, "Cuajar roto");
        insertarDiversosSintomas(db, "Anemia");
        insertarDiversosSintomas(db, "No come");
        insertarDiversosSintomas(db, "Caida de lana");
        insertarDiversosSintomas(db, "Mucosa en ojos");
        insertarDiversosSintomas(db, "Parpados amarillentos");
        insertarDiversosSintomas(db, "Lana seca y erizada");
        insertarDiversosSintomas(db, "Barriga hinchada");
        insertarDiversosSintomas(db, "Inquieto");
        insertarDiversosSintomas(db, "Picor");
        insertarDiversosSintomas(db, "Costras");
        insertarDiversosSintomas(db, "Piel gruesa");
        insertarDiversosSintomas(db, "Fiebre alta");
        insertarDiversosSintomas(db, "Falta de apetito");
        insertarDiversosSintomas(db, "Tos fuerte");
        insertarDiversosSintomas(db, "Respiracion agitada");
        insertarDiversosSintomas(db, "Moco en la nariz");
        insertarDiversosSintomas(db, "Convulsiones");
        insertarDiversosSintomas(db, "Sangre (nariz,boca,vulva)");
        insertarDiversosSintomas(db, "Seguera");
        insertarDiversosSintomas(db, "Enrojecimiento en los ojos");
        insertarDiversosSintomas(db, "Lagrimeo");
        insertarDiversosSintomas(db, "Presencia de nube");
        insertarDiversosSintomas(db, "Cojera");
        insertarDiversosSintomas(db, "Cascos hinchados");
        insertarDiversosSintomas(db, "Sangre y pus");
        insertarDiversosSintomas(db, "Olor a podrido");
        insertarDiversosSintomas(db, "Pezones sucios");
        insertarDiversosSintomas(db, "Ubre hinchada y caliente");
        insertarDiversosSintomas(db, "Ubre roja(Negra)");
        insertarDiversosSintomas(db, "Debilidad y temblores");
        insertarDiversosSintomas(db, "Camina en circulos");
        insertarDiversosSintomas(db, "Echado y no se levanta");
        insertarDiversosSintomas(db, "Alta temperatura");
        insertarDiversosSintomas(db, "Liquido por las pezullas");
        insertarDiversosSintomas(db, "Echa baba");
        insertarDiversosSintomas(db, "Secrecion nasal");

        insertarSintomas(db, "Sinusitis parasitoria", 1);
        insertarSintomas(db, "Coccidiosis", 1);
        insertarSintomas(db, "Tos seca(Bronquitis verminosa)", 1);
        insertarSintomas(db, "Gastroenteritis parasitoria", 1);
        insertarSintomas(db, "Fasciola Hepatica(Coscoja)", 1);
        insertarSintomas(db, "Solitaria(Lombriztenia)", 1);
        insertarSintomas(db, "Garrapatas", 1);
        insertarSintomas(db, "Sarna", 1);
        insertarSintomas(db, "Pulmonia(Neumonia)", 2);
        insertarSintomas(db, "Carbunco Sintomatico", 2);
        insertarSintomas(db, "Conjuntivitis(Mal de ojo)", 2);
        insertarSintomas(db, "Casco podrido(ponodizo)", 2);
        insertarSintomas(db, "Mastitis", 2);
        insertarSintomas(db, "Toxemia", 2);
        insertarSintomas(db, "Fiebre aftosa", 2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        /*SI LAS TABLAS YA EXISTEN EN LA BASE DE DATOS SE ELIMINAN Y SE CREAN
        LAS NUEVAS TABLAS MEDIANTE EL METODO onCreate
         */

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


    public void insertarRazas(SQLiteDatabase db, String tipo_raza){

        ContentValues razas = new ContentValues();
        razas.put(BDManager.column_tipo_raza,tipo_raza);

        db.insert(BDManager.name_table_razas,null,razas);

    }

    public void insertarEtapas(SQLiteDatabase db, String tipo_etpa){

        ContentValues etapas = new ContentValues();
        etapas.put(BDManager.column_tipo_etapa,tipo_etpa);

        db.insert(BDManager.name_table_etapas,null,etapas);

    }

    public void insertarSalidas(SQLiteDatabase db, String tipo_salida){

        ContentValues salidas = new ContentValues();
        salidas.put(BDManager.column_tipo_salida,tipo_salida);

        db.insert(BDManager.name_table_salidas,null,salidas);

    }

    public void insertarEnfermedades(SQLiteDatabase db, String tipo_enferemdad){

        ContentValues enfermedad = new ContentValues();
        enfermedad.put(BDManager.column_tipo_enfermedad,tipo_enferemdad);

        db.insert(BDManager.name_table_enfermedades,null,enfermedad);
    }

    public void insertarDiversosSintomas(SQLiteDatabase db, String tipo_sintoma){

        ContentValues diverso_sintoma = new ContentValues();
        diverso_sintoma.put(BDManager.column_tipo_sintoma,tipo_sintoma);

        db.insert(BDManager.name_table_diversos_sin,null,diverso_sintoma);
    }

    public void insertarSintomas(SQLiteDatabase db, String nombre , int id_enfermedad){

        ContentValues sintomas = new ContentValues();
        sintomas.put(BDManager.column_nombre_sintoma,nombre);
        sintomas.put(BDManager.column_enfermedad_idf,id_enfermedad);

        db.insert(BDManager.name_table_sintomas,null,sintomas);
    }


}
