package com.android.sergiomarquez.raisingsheep1.BaseDeDatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


//Clase para sentencias SQLite

public class BDManager {

    private SQLiteHelperBD helper; //helper es la variable para acceder a la clase de SQLiteHelperBD
    private SQLiteDatabase base_datos; // SQLiteDatabase sirve para acceder a los metodos de SQLite "insert,update,delete,select"
    private Cursor cursor = null;

    ///////////////NOMBRE DE LAS TABLAS//////////////////

    public static final String name_table_borregos = "borregos";  //Nombre de la tabla para el registro de borregos
    public static final String name_table_usr = "usuarios"; //Nombre de la tabla para el registro de usuarios
    public static final String name_table_razas = "razas";
    public static final String name_table_etapas = "etapas";
    public static final String name_table_salidas = "salidas";
    public static final String name_table_consultas = "consultas";
    public static final String name_table_diagnostico = "diagnosticos";
    public static final String name_table_sintomas = "sintomas";
    public static final String name_table_enfermedades = "enfermedades";
    public static final String name_table_diversos_sin = "diversos_sintomas";
    public static final String name_table_detalle_diversos_sin = "detalle_diversos_sintomas";

    /////////////////////COLUMNAS////////////////////

    //COLUMNAS PARA LA TABLA DE RAZAS

    public static final String column_id_raza = "id_raza";
    public static final String column_tipo_raza = "tipo_raza";

    //COLUMNAS PARA LA TABLA DE ETPAS

    public static final String column_id_etapa = "id_etapa";
    public static final String column_tipo_etapa ="tipo_etapa";

    //COLUMNAS PARA LA TABLA DE SALIDAS

    public static final String column_id_salida = "id_salida";
    public static final String column_tipo_salida = "tipo_salida";


    //COLUMNAS PARA LA TABLA DE REGISTRO DE BORREGOS

    public static final String column_id_borrego = "id_borrego";
    public static final String column_nombre_borre = "nombre";
    public static final String column_sexo = "sexo";
    public static final String column_fecha = "fecha_registro";
    public static final String column_descripcion = "descripcion";
    public static final String column_raza_idf = "id_raza";
    public static final String column_etapa_idf = "id_etapa";
    public static final String column_salida_idf = "id_salida";


    //public static final String column_peso = "peso";


    //COLUMNAS PARA LA TABLA DE CONSULTAS

    public static final String column_id_consulta = "id_consulta";
    public static final String column_fecha_consulta = "fecha_consulta";
    public static final String column_borrego_idf = "id_borrego";

    //COLUMNAS PARA LA TABLA DE DIVERSOS SINTOMAS

    public static final String column_id_diverso_sin = "id_diverso_sintoma";
    public static final String column_tipo_sintoma = "tipo_sintoma";


    //COLUMNAS PARA LA TABLA DE DIAGNOSTICO

    public static final String column_id_diagnostico = "id_diagnostico";
    public static final String column_consulta_idf = "id_consulta";
    public static final String column_diverso_sin_idf = "id_diverso_sintoma";

    //COLUMNAS PARA LA TABLA DE SINTOMAS

    public static final String column_id_sintoma = "id_sintoma";
    public static final String column_nombre_sintoma = "nombre";
    public static final String column_enfermedad_idf = "id_enfermedad";

    //COLUMNAS PARA LA TABLA DE ENFERMEDADES

    public static final String column_id_enfermedad = "id_enfermedad";
    public static final String column_tipo_enfermedad = "tipo_enfermedad";

    //COLUMNAS PARA LA TABLA DE DETALLE SINTOMAS_DIVERSOS SINTOMAS

    public static final String column_id_detalle_diverso_sin = "id_detalle_diverso_sin";
    public static final String column_sintoma_idf = "id_sintoma";
    public static final String column_sin_diverso_idf = "id_diverso_sintoma";


     //COLUMNAS PARA LA TABLA DE REGISTRO DE USUARIOS

    public static final String column_id = "id_usuario";
    public static final String column_nombre = "nombre";
    public static final String column_password = "contrasena";
    public static final String column_foto = "foto";

    ///////////////////CREACION D TABLAS////////////////


    //TABLA DE RAZAS

    public static final String create_table_razas = " create table " + name_table_razas + " ("
            +column_id_raza + " integer primary key autoincrement,"
            +column_tipo_raza + " varchar(15) not null);";

    //TABLA ETAPAS

    public static final String create_table_etapas = " create table " + name_table_etapas + " ("
            +column_id_etapa + " integer primary key autoincrement,"
            +column_tipo_etapa + " varchar(20) not null);";


    //TABLA ENFERMEDADES

    public static final String create_table_enfermedades = " create table " +name_table_enfermedades + " ("
            +column_id_enfermedad + " integer primary key autoincrement,"
            +column_tipo_enfermedad + " varchar(30) not null);";

    //TABLA SALIDAS

    public static final String create_table_salidas = " create table " +name_table_salidas + " ("
            +column_id_salida + " integer primary key autoincrement,"
            +column_tipo_salida + " varchar(15) not null);";

    //TABLA DIVERSOS SINTOMAS

    public static final String create_table_diversos_sin = " create table " +name_table_diversos_sin + " ("
            +column_id_diverso_sin + " integer primary key autoincrement,"
            +column_tipo_sintoma + " varchar(25) not null);";

    //TABLA DE USUARIOS

    public static final String create_table_usuarios = " create table " + name_table_usr + " ("
            + column_id + " integer primary key autoincrement,"
            + column_nombre + " varchar(20) not null,"
            + column_password + " password not null,"
            + column_foto + " blob not null);";


    //TABLA DE BORREGOS

    public static final String create_table_borregos = " create table " + name_table_borregos + " ("
            +column_id_borrego + " integer primary key autoincrement,"
            +column_nombre_borre + " varchar(20) not null,"
            +column_sexo + " varchar(8) not null,"
            +column_fecha + " date not null,"
            +column_descripcion + " text not null,"
            +column_raza_idf + " integer, "
            +column_etapa_idf + " integer, "
            +column_salida_idf + " integer, "
            +"foreign key (" + column_raza_idf +") references " + name_table_razas + "(id_raza),"
            +"foreign key (" + column_etapa_idf +") references " + name_table_etapas + "(id_etapa),"
            +"foreign key (" + column_salida_idf +") references " + name_table_salidas + "(id_salida)" +")";

    //TABLA DE CONSULTAS

    public static final String create_table_consultas = " create table " +name_table_consultas + " ("
            +column_id_consulta + " integer primary key autoincrement,"
            +column_fecha_consulta + " date not null,"
            +column_borrego_idf + " integer,"
            +"foreign key (" + column_borrego_idf +") references " +name_table_borregos + "(id_borrego)" +")";

    //TABLA DE DIAGNOSTICO

    public static final String create_table_diagnostico = " create table " +name_table_diagnostico + " ("
            +column_id_diagnostico + " integer primary key autoincrement,"
            +column_consulta_idf + " integer,"
            +column_diverso_sin_idf + " integer,"
            +"foreign key (" +column_consulta_idf + ") references " +name_table_consultas + "(id_consulta),"
            +"foreign key (" + column_diverso_sin_idf + ") references " +name_table_diversos_sin + "(id_diverso_sintoma)" + ")";

    //TABLA SINTOMAS

    public static final String create_table_sitomas = " create table " +name_table_sintomas + " ("
            +column_id_sintoma + " integer primary key autoincrement,"
            +column_nombre_sintoma + " varchar(30) not null,"
            +column_enfermedad_idf + " integer,"
            +"foreign key (" + column_enfermedad_idf + ") references " +name_table_enfermedades + "(id_enfermedad)" + ")";

    //TABLA DETALLE SINTOMAS_DIVERSOS_SINTOMAS

    public static final  String create_table_detalle_ds = " create table " +name_table_detalle_diversos_sin + " ("
            +column_id_detalle_diverso_sin + " integer primary key autoincrement,"
            +column_sintoma_idf + " integer,"
            +column_sin_diverso_idf + " integer,"
            +"foreign key (" + column_sintoma_idf + ") references " +name_table_sintomas + "(id_sintoma),"
            +"foreign key (" + column_sin_diverso_idf +") references " +name_table_diversos_sin + "(id_diverso_sintoma)" + ")";


///////////////////////////METODOS////////////////////


    public BDManager(Context context) {

        helper = new SQLiteHelperBD(context);

    }

    //Metodo para poder escribir en la base de datos
    public SQLiteDatabase abrirBaseDatos() {

        base_datos = helper.getWritableDatabase();
        return base_datos;
    }

    //Metodo para cerrar la base de datos
    public void cerrarBaseDatos() {

        base_datos.close();
    }

    //Metodo para leer la base de datos
    public SQLiteDatabase leerBaseDatos() {

        base_datos = helper.getReadableDatabase();

        return base_datos;

    }


    //Metodo en donde se almacenan los datos del usuario
    private ContentValues generarValores(String nombre, String password, byte[] image) {

        ContentValues usuarios = new ContentValues();

        usuarios.put(column_nombre, nombre);
        usuarios.put(column_password, password);
        usuarios.put(column_foto,image);

        return usuarios;

    }

    //Metodo para insertar a los usuarios
    public void insertarUsuarios(String nombre, String password, byte[] image) {

        base_datos.insert(name_table_usr, null, generarValores(nombre, password,image));

    }

    //Metodo para editar a los usuarios
    public void editarPerfil(String nombre,String password, byte[] foto, String usuario){

        base_datos.update(name_table_usr,generarValores(nombre,password,foto),column_nombre + "=?",new String[]{usuario});
    }

    //Metodo para buscar a un usuario registrado
    public Cursor inicarSesion(String nombre, String contrasena) {

        cursor = abrirBaseDatos().rawQuery("Select nombre,contrasena from usuarios where nombre='" + nombre + "' and contrasena='" + contrasena + "'", null);

        return cursor;
    }

    //Metodo que verifica si un usuario ya existe en la base de datos
    public Cursor existeUsuario(String nombre) {

        cursor = abrirBaseDatos().rawQuery("Select nombre from usuarios where nombre='" + nombre + "'", null);

        return cursor;

    }

    //Metodo que busca al usuario segun su nombre
    public Cursor getUsuario(String nombre){

        leerBaseDatos();

        cursor = base_datos.rawQuery("Select nombre, foto from usuarios where nombre='"+nombre+"'",null);

        return cursor;
    }

    //Metodo que busca la foto del usuario que esta logeado
    public Cursor getImagen(String nombre){

        leerBaseDatos();

        cursor = base_datos.rawQuery("Select foto from usuarios where nombre ='"+nombre+"'",null);

        return cursor;
    }
}
