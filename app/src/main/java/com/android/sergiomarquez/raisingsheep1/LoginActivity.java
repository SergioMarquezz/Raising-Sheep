package com.android.sergiomarquez.raisingsheep1;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.sergiomarquez.raisingsheep1.BaseDeDatos.BDManager;

import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    ActionBar actionBar;
    Button login;
    TextView registro;


    private Button buttonlogin;
    private TextView textViewregistro;
    private EditText editTextpassword;
    public EditText autoCompleteTextViewnombre;
    public RadioButton sesion;

    private boolean radio_activado;
    private static final String iniciar_sesion_preference = "com.android.sergiomarquez.raisingsheep1";
    private static final String estado_radio_preference = "estado.button.sesion";
    private static final String preferencias = "preferences";


    private Cursor usuario;
    private Dialog dialog;
    private AlertDialog.Builder builder;

    private ProgressDialog progressBar;
    private int progressBarStatus = 0;
    private Handler progressBarbHandler = new Handler();

    private Context contexto = this;
    private CircleImageView imagen;



    private BDManager bd_maestra = new BDManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


         actionBar = getSupportActionBar();
         actionBar.setTitle(getString(R.string.acceso));

        inicializacion();



         bd_maestra.abrirBaseDatos();



         traerDatosUsuarioPreferences(getApplicationContext());

        imagenUsuario();


          if (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {


             android.support.v13.app.ActivityCompat.requestPermissions(LoginActivity.this, new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }

    }

    public void inicializacion(){

        buttonlogin = (Button) findViewById(R.id.buttonEntrar);
        textViewregistro = (TextView) findViewById(R.id.textViewRegistrar);
        buttonlogin.setOnClickListener(this);
        textViewregistro.setOnClickListener(this);
        imagen = (CircleImageView) findViewById(R.id.imageViewLogin);

        editTextpassword = (EditText) findViewById(R.id.editTextPasswordLogin);
        autoCompleteTextViewnombre = (EditText) findViewById(R.id.AutoCompleteName);

        sesion = (RadioButton) findViewById(R.id.radioButtonRecordar);
        sesion.setOnClickListener(this);

        radio_activado = sesion.isChecked(); //Desactivado el radio button

    }

    public void imagenUsuario(){

        String nombre = autoCompleteTextViewnombre.getText().toString();
        usuario = bd_maestra.getImagen(nombre);

        if (usuario.moveToFirst()){

            byte[] img = usuario.getBlob(0);

            Bitmap bm = BitmapFactory.decodeByteArray(img, 0 ,img.length);
            imagen.setImageBitmap(bm);
        }
    }
/*
    public void guardarEstadoRadioButton(){

        SharedPreferences preferences = getSharedPreferences(iniciar_sesion_preference,MODE_PRIVATE);
        preferences.edit().putBoolean(estado_radio_preference,sesion.isChecked()).apply();
    }

    public boolean obtenerEstadoRadioButton(){

        SharedPreferences preferences = getSharedPreferences(iniciar_sesion_preference,MODE_PRIVATE);
        return preferences.getBoolean(estado_radio_preference,false);

    }

    public static void cambiarEstadoRadioButton(Context context, boolean radio_button){

        SharedPreferences preferences = context.getSharedPreferences(iniciar_sesion_preference,MODE_PRIVATE);
        preferences.edit().putBoolean(estado_radio_preference,radio_button).apply();

    }
*/
    public void guardarDatosUsuarioPreferences(String nombre, String password){

        if (sesion.isChecked()){

            SharedPreferences preferences = getSharedPreferences(preferencias,contexto.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("nombre",nombre);
            editor.putString("pasword",password);
            editor.commit();

        }

    }

    public void traerDatosUsuarioPreferences(Context context) {
        SharedPreferences preferences = getSharedPreferences(preferencias, MODE_PRIVATE);
       autoCompleteTextViewnombre.setText(preferences.getString("nombre",""));
       editTextpassword.setText(preferences.getString("pasword",""));

    }

    public static void borrarSharedPreferences(Context context){
        SharedPreferences preferences = context.getSharedPreferences(preferencias,MODE_PRIVATE);
        preferences.edit().clear().apply();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

            }

            else {
                Toast.makeText(getApplicationContext(),getString(R.string.permisos),Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    @Override
    public void onClick(View v) {

        dialog = null;
        builder = new AlertDialog.Builder(this);

        if (v.getId() == R.id.buttonEntrar){



            final String nombre = autoCompleteTextViewnombre.getText().toString();
            final String password = editTextpassword.getText().toString();

            usuario = bd_maestra.inicarSesion(nombre,password);

            if (usuario.moveToFirst()){

                builder.setTitle(getString(R.string.permitido));
                builder.setMessage(getString(R.string.continuar));
                builder.setPositiveButton(getString(R.string.acepetar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                       //   autoCompleteTextViewnombre.setText("");
                       //   editTextpassword.setText("");
                          guardarDatosUsuarioPreferences(nombre,password);
                          progreesBar();
                    }
                });

                builder.setNegativeButton(getString(R.string.cancelar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        finish();
                    }
                });

                dialog = builder.create();
                dialog.show();
            }

            else {

                builder.setTitle(getString(R.string.aceso_denegado));
                builder.setMessage(getString(R.string.verificar_acceso));
                builder.setPositiveButton(getString(R.string.acepetar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                dialog = builder.create();
                dialog.show();

            }

        }

        else if (v.getId() == R.id.textViewRegistrar){


            Intent activity_registro = new Intent(this,RegistroUsuarioActivity.class);
            startActivity(activity_registro);
            finish();
        }

        else if (v.getId() == R.id.radioButtonRecordar){

            if (radio_activado){

                sesion.setChecked(false);
            }

            radio_activado = sesion.isChecked();
          //  guardarEstadoRadioButton();

        }
    }

    public void progreesBar(){

        progressBar = new ProgressDialog(this);
        progressBar.setCancelable(true);
        progressBar.setMessage(getString(R.string.logeo));
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setProgress(0);
        progressBar.setMax(100);
        progressBar.show();
        progressBarStatus = 0;

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progressBarStatus < 100){
                    progressBarStatus += 30;

                    try {
                        Thread.sleep(1000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    progressBarbHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(progressBarStatus);
                        }
                    });
                }
                if (progressBarStatus >= 100) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    finally {
                        Intent principal = new Intent(getApplicationContext(),PrincipalActivity.class);
                        principal.putExtra("usuario",autoCompleteTextViewnombre.getText().toString());
                        startActivity(principal);
                        finish();
                    }
                    progressBar.dismiss();
                }

            }
        }).start();

    }
}
