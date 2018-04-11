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


    public static final String name_table_registro = "registros_borre";  //Nombre de la tabla para el registro de borregos
    public static final String name_table_usr = "usuarios"; //Nombre de la tabla para el registro de usuarios


    //Columnas para la tabla de registro de borregos

    public static final String column_clave = "clave_borre";
    public static final String column_nombre_borre = "nombre";
    public static final String column_fecha = "fecha_registro";
    public static final String column_sexo = "sexo";
    public static final String column_etapa = "etapa";
    public static final String column_peso = "peso";
    public static final String column_descripcion = "descripcion";
    public static final String column_raza = "raza";

    //Creacion de la tabla registro de borregos

    public static final String create_table_registro = " create table " + name_table_registro + " ("
            + column_clave + " integer primary key autoincrement,"
            + column_nombre_borre + " varchar(20) not null,"
            + column_sexo + " varchar(6),"
            + column_raza + " varchar(30) not null,"
            + column_etapa + " varchar(30) not null,"
            + column_peso + " double(10) not null,"
            + column_fecha + " date not null,"
            + column_descripcion + " text);";


    //Columnas para la tabla de registro de usuarios

    public static final String column_id = "id_usuario";
    public static final String column_nombre = "nombre";
    public static final String column_password = "contrasena";
    public static final String column_foto = "foto";

    //Creacion de la tabla registro de usuarios

    public static final String create_table_usuarios = " create table " + name_table_usr + " ("
            + column_id + " integer primary key autoincrement,"
            + column_nombre + " varchar(20) not null,"
            + column_password + " password not null,"
            + column_foto + " blob not null);";


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
