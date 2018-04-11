package com.android.sergiomarquez.raisingsheep1;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.sergiomarquez.raisingsheep1.BaseDeDatos.BDManager;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;


public class CambioPerfilFragment extends Fragment implements View.OnClickListener {

    private View vista;
    private Button guardar;
    private EditText nombre, pasword_actual,new_pasword,confirme_pasword;
    private BDManager bdManager;
    private Dialog dialog;
    private Cursor imagen;
    private TextView cambiar_image;
    private CircleImageView circle_image;

    private static final int select_foto = 1;
    private static final int capture_foto = 2;
    private Bitmap bitmap;
    private Uri image_uri;
    private Cursor cambio_usr;

    private ProgressDialog progressBar;
    private int progressBarStatus = 0;
    private Handler progressBarbHandler = new Handler();

    private  AlertDialog.Builder builder;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista =  inflater.inflate(R.layout.fragment_cambio_perfil, container, false);

        iniciar();
        traerUsuario();


        return vista;
    }

    public void iniciar(){

        nombre = (EditText) vista.findViewById(R.id.editTextNameActual);
        pasword_actual = (EditText) vista.findViewById(R.id.editTextPasswordActual);
        new_pasword = (EditText) vista.findViewById(R.id.editTextPasswordNuevo);
        confirme_pasword = (EditText) vista.findViewById(R.id.editTextPasswordConfirmarNuevo);
        circle_image = (CircleImageView) vista.findViewById(R.id.imageViewImagen);
        cambiar_image = (TextView) vista.findViewById(R.id.textViewImagen);
        guardar = (Button) vista.findViewById(R.id.buttonGuardar);
        guardar.setOnClickListener(this);
        circle_image.setOnClickListener(this);
        bdManager = new BDManager(getContext());
        dialog = new Dialog(getContext());

    }

    public void traerUsuario(){

        String texto = getArguments().getString("name");
        nombre.setText(texto);

        imagen = bdManager.getImagen(texto);

        if (imagen.moveToFirst()){

            byte[] img = imagen.getBlob(0);

            Bitmap bm = BitmapFactory.decodeByteArray(img, 0 ,img.length);
            circle_image.setImageBitmap(bm);

        }
    }

    public void modificarPerfil(){

        bdManager.abrirBaseDatos();

        String cambio_name = nombre.getText().toString().trim();
        String cambio_pasword = new_pasword.getText().toString().trim();
        byte[] imgen = imageViewToByte(circle_image);
        String usuario = PrincipalActivity.nombre_usuario.getText().toString();

        bdManager.editarPerfil(cambio_name,cambio_pasword,imgen,usuario);

        bdManager.cerrarBaseDatos();

        Toast.makeText(getContext(),getString(R.string.perfil_update),Toast.LENGTH_LONG).show();


    }

    public void abrirLogin(){

        LoginActivity.borrarSharedPreferences(getContext());
        Intent login = new Intent(getContext(),LoginActivity.class);
        startActivity(login);

    }

    public void dialogo(){

        builder = new AlertDialog.Builder(getContext());

        builder.setTitle(getString(R.string.cierre_sesion));
        builder.setMessage(getString(R.string.continuar));
        builder.setPositiveButton(getString(R.string.acepetar), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                modificarPerfil();
                abrirLogin();

            }
        });

        builder.setNegativeButton(getString(R.string.cancelar), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.create().show();


    }

    public void cambioPerfil(){

        builder = new AlertDialog.Builder(getContext());

        builder.setTitle(getString(R.string.sin_cambio_image));
        builder.setMessage(getString(R.string.continuar));
        builder.setPositiveButton(getString(R.string.acepetar), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialogo();

            }
        });

        builder.setNegativeButton(getString(R.string.cancelar), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.create().show();

    }

    @Override
    public void onClick(View v) {




        switch (v.getId()){


            case R.id.buttonGuardar:

                String cambio_name = nombre.getText().toString().trim();
                String cambio_pasword = new_pasword.getText().toString().trim();
                String pasword = pasword_actual.getText().toString().trim();
                String confirmar = confirme_pasword.getText().toString().trim();
                String usuario = PrincipalActivity.nombre_usuario.getText().toString();

                if (cambio_name.equals("")){

                    dialog.OnCreateDialog(getString(R.string.write_name)).show();
                }

                else {

                    if (pasword.equals("")){

                        dialog.OnCreateDialog(getString(R.string.write_pass_actual)).show();
                    }

                    else if (cambio_pasword.equals("") && confirmar.equals("")){

                        dialog.OnCreateDialog(getString(R.string.write_new_pass)).show();
                    }

                    else if (confirmar.equals("")){

                        dialog.OnCreateDialog(getString(R.string.confirmar_new_paword)).show();
                    }

                    else if (cambio_pasword.equals(confirmar)){

                        cambio_usr = bdManager.inicarSesion(usuario,pasword);

                        if (cambio_usr.moveToFirst()){

                            String imagen = cambiar_image.getText().toString();

                            if (imagen.equals(getString(R.string.cambio_imagen))){

                                cambioPerfil();
                            }

                            else if (imagen.equals(getString(R.string.new_image))){

                                dialogo();
                            }

                            else if (imagen.equals(getString(R.string.sin_image))){

                                Dialog dialog = new Dialog(getContext());
                                dialog.OnCreateDialog(getString(R.string.select_photo)).show();
                            }


                        }

                        else {

                            dialog.OnCreateDialog(getString(R.string.incorrecto_pass)).show();
                        }

                    }

                    else {

                        dialog.OnCreateDialog(getString(R.string.incorrecta_contraseña)).show();
                    }
                }

                break;

            case R.id.imageViewImagen:

                new MaterialDialog.Builder(getContext())
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
                                        circle_image.setImageResource(R.drawable.camara);
                                        cambiar_image.setText(getString(R.string.sin_image));
                                        break;
                                }
                            }
                        })
                        .show();


                break;
        }

    }

    public void cargarImagen(){

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(Intent.createChooser(intent,getString(R.string.select_app)),select_foto);
    }

    private void camara(Intent data){

        bitmap = (Bitmap) data.getExtras().get("data");

        circle_image.setMaxWidth(200);
        progreesBar();
        circle_image.setImageBitmap(bitmap);
        cambiar_image.setText(getString(R.string.new_image));
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode){

            case select_foto:

                image_uri = data.getData();
                progreesBar();
                circle_image.setImageURI(image_uri);
                cambiar_image.setText(getString(R.string.new_image));


                break;

            case capture_foto:

                camara(data);
                break;
        }
    }

    public void progreesBar(){

        progressBar = new ProgressDialog(getContext());
        progressBar.setCancelable(true);
        progressBar.setMessage(getString(R.string.cambio_image));
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

    @Override
    public void onPause() {

        Toast.makeText(getContext(),"onPause",Toast.LENGTH_LONG).show();

        super.onPause();
    }

    @Override
    public void onDestroy() {

        Toast.makeText(getContext(),"onDestroy",Toast.LENGTH_LONG).show();
        super.onDestroy();
    }

    @Override
    public void onDetach() {

        Toast.makeText(getContext(),"onDetach",Toast.LENGTH_LONG).show();
        super.onDetach();
    }
}
