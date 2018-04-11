package com.android.sergiomarquez.raisingsheep1;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.sergiomarquez.raisingsheep1.BaseDeDatos.BDManager;

import de.hdodenhof.circleimageview.CircleImageView;

public class PrincipalActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    BDManager bd_maestra = new BDManager(this);


    private CircleImageView imagen_usuario;
    public static TextView nombre_usuario;
    private Cursor cursor_usuario;
    private TextView text_view_bienvenido;
    private View header;
    private Toolbar toolbar;
    private ImageView image_view_bienvenido;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        toolbar = (Toolbar) findViewById(R.id.toolbarPrincipal);
        setSupportActionBar(toolbar);




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        iniciar();
        selectUsuario();


    }

    public void iniciar(){
        header = ((NavigationView)findViewById(R.id.nav_view)).getHeaderView(0);
        nombre_usuario = (TextView) header.findViewById(R.id.textViewUsuario);
        imagen_usuario = (CircleImageView) header.findViewById(R.id.imageViewUsuario);
        image_view_bienvenido = (ImageView) findViewById(R.id.imageViewBienvenido);
        text_view_bienvenido = (TextView) findViewById(R.id.textViewBienvenido);
    }

    public void selectUsuario(){

        Bundle datos = getIntent().getExtras();
        String identificar = datos.getString("usuario");


        cursor_usuario = bd_maestra.getUsuario(identificar);

        if (cursor_usuario.moveToFirst()){

            String urs = cursor_usuario.getString(0);
            byte[] img = cursor_usuario.getBlob(1);

            Bitmap bm = BitmapFactory.decodeByteArray(img, 0 ,img.length);
            imagen_usuario.setImageBitmap(bm);

            nombre_usuario.setText(urs);

        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu);



        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.action_settings:

                break;

            case R.id.itemCerrarSesion:

                LoginActivity.borrarSharedPreferences(getApplicationContext());
                Intent login = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(login);
                finish();


                break;
        }


        return super.onOptionsItemSelected(item);


    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {


        int id = item.getItemId();

        FragmentManager fragmentManager = getSupportFragmentManager();

        Fragment fragment = fragmentManager.findFragmentByTag("fragments");

        if (id == R.id.itemAdministracion) {

            if (fragment!= null){

                fragmentManager.beginTransaction().remove(fragment).commit();
                image_view_bienvenido.setImageResource(R.drawable.raising_sheep);
                text_view_bienvenido.setVisibility(View.VISIBLE);
                findViewById(R.id.containerPrincipal).setBackgroundResource(R.drawable.backgroud);
                toolbar.setTitle(R.string.app_name);
            }

            Intent activity_administraccion = new Intent(getApplicationContext(),AdministracionBorreActivity.class);
            startActivity(activity_administraccion);


        } else if (id == R.id.itemSintomas) {


                fragment = new SintomasFragment();

                fragmentManager.beginTransaction().replace(R.id.containerPrincipal,fragment,"fragments").commit();





        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.itemCambioPefil){

            toolbar.setTitle(R.string.toolbar_perfil);

            Bundle args = new Bundle();

            args.putString("name", nombre_usuario.getText().toString());

            fragment = new CambioPerfilFragment();
            fragment.setArguments(args);

            fragmentManager.beginTransaction().replace(R.id.containerPrincipal,fragment,"fragments").commit();
            image_view_bienvenido.setImageDrawable(null);
            text_view_bienvenido.setVisibility(View.INVISIBLE);
            findViewById(R.id.containerPrincipal).setBackgroundColor(Color.WHITE);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
