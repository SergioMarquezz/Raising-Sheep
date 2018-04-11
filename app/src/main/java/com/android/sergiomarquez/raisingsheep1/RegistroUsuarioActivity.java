package com.android.sergiomarquez.raisingsheep1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.sergiomarquez.raisingsheep1.BaseDeDatos.BDManager;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegistroUsuarioActivity extends AppCompatActivity implements View.OnClickListener {

    ActionBar actionBar;
    private Button button_registrar, button_cancelar;
    private EditText editText_nombre, editText_password,editText_confirmar;
    private BDManager bd_maestra = new BDManager(this);
    private Dialog dialogos;
    private Cursor usuario;
    private CircleImageView image_view_foto;
    private TextView text_view_foto;



    private static final int select_foto = 1;
    private static final int capture_foto = 2;
    private Bitmap bitmap;
    private Uri image_uri;

    private ProgressDialog progressBar;
    private int progressBarStatus = 0;
    private Handler progressBarbHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.usr_registro);
        iniciacion();
        bloqueo();

    }

    public void iniciacion(){

        button_registrar = (Button) findViewById(R.id.buttonRegistrar);
        button_cancelar = (Button) findViewById(R.id.buttonCancelar);
        editText_confirmar = (EditText) findViewById(R.id.editTextPasswordConfirmar);
        editText_password = (EditText) findViewById(R.id.editTextPasswordRegistro);
        editText_nombre = (EditText) findViewById(R.id.editTextName);
        button_registrar.setOnClickListener(this);
        button_cancelar.setOnClickListener(this);
        image_view_foto = (CircleImageView) findViewById(R.id.imageViewFoto);
        image_view_foto.setOnClickListener(this);
        text_view_foto = (TextView) findViewById(R.id.textViewFoto);


    }

    public void bloqueo(){

        editText_nombre.setEnabled(false);
        editText_password.setEnabled(false);
        editText_confirmar.setEnabled(false);
    }


    public boolean desbloqueo(){

        editText_nombre.setEnabled(true);
        editText_password.setEnabled(true);
        editText_confirmar.setEnabled(true);

        return true;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.buttonRegistrar:


                dialogos = new Dialog(this);

                button_registrar.setText(getString(R.string.save));
                desbloqueo();

                String nombre = String.valueOf(editText_nombre.getText()).trim();
                String password = String.valueOf(editText_password.getText()).trim();
                String paswword_confirmar = String.valueOf(editText_confirmar.getText()).trim();




                if (nombre.equals("")) {

                    dialogos.OnCreateDialog(getString(R.string.campos_requeridos)).show();

                }

                else{

                    if (password.equals("") && paswword_confirmar.equals("")){

                        dialogos.OnCreateDialog(getString(R.string.contraseña)).show();
                    }

                    else if (paswword_confirmar.equals("")){

                        dialogos.OnCreateDialog(getString(R.string.confirme_contraseña)).show();
                    }



                    else if (password.equals(paswword_confirmar)){

                        usuario = bd_maestra.existeUsuario(nombre);

                        if (usuario.getCount() == 0){

                            if (text_view_foto.getVisibility() == View.INVISIBLE){

                                bd_maestra.abrirBaseDatos();

                                bd_maestra.insertarUsuarios(nombre,password,imageViewToByte(image_view_foto));
                                bd_maestra.cerrarBaseDatos();


                                Toast.makeText(getApplicationContext(),getString(R.string.usr_registrado),Toast.LENGTH_LONG).show();

                                Intent login = new Intent(getApplicationContext(),LoginActivity.class);
                                startActivity(login);
                                finish();

                            }

                            else {

                                dialogos.OnCreateDialog(getString(R.string.sin_imagen)).show();
                            }


                        }


                        else{


                            dialogos.OnCreateDialog(getString(R.string.usr_existe)).show();


                        }
                    }


                    else{

                        dialogos.OnCreateDialog(getString(R.string.contraseñas_distintas)).show();
                    }


                }

                break;

            case R.id.imageViewFoto:

                new MaterialDialog.Builder(this)
                        .title(getString(R.string.opcion))
                        .items(R.array.subirImagen)
                        .itemsIds(R.array.itemIds)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {

                                switch (position){

                                    case 0:
                                        cargarImagen();

                                        break;

                                    case 1:
                                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                        startActivityForResult(intent, capture_foto);
                                        break;

                                    case 2:
                                        image_view_foto.setImageResource(R.drawable.camara);
                                        text_view_foto.setVisibility(View.VISIBLE);
                                        break;
                                }
                            }
                        })
                        .show();

                break;



            case R.id.buttonCancelar:

                Intent regreso = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(regreso);
                finish();
                break;

        }

    }

    public void cargarImagen(){

        Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(Intent.createChooser(intent,getString(R.string.select_app)),select_foto);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode){

            case select_foto:

                image_uri = data.getData();
                progreesBar();
                image_view_foto.setImageURI(image_uri);
                text_view_foto.setVisibility(View.INVISIBLE);

                break;

            case capture_foto:

                camara(data);
                break;
        }
    }

    private byte[] imageViewToByte(ImageView imagen) {

        imagen.setDrawingCacheEnabled(true);
        imagen.buildDrawingCache();
        Bitmap bitmap = imagen.getDrawingCache();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    private void camara(Intent data){

        bitmap = (Bitmap) data.getExtras().get("data");
        progreesBar();
        image_view_foto.setMaxWidth(200);
        image_view_foto.setImageBitmap(bitmap);
        text_view_foto.setVisibility(View.INVISIBLE);
    }

    public void progreesBar(){

        progressBar = new ProgressDialog(this);
        progressBar.setCancelable(true);
        progressBar.setMessage(getString(R.string.carga_imagen));
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
                    progressBar.dismiss();
                }

            }
        }).start();

    }
}
