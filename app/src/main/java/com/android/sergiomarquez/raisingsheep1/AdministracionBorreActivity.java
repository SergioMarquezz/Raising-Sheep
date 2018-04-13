package com.android.sergiomarquez.raisingsheep1;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.sergiomarquez.raisingsheep1.BaseDeDatos.BDManager;

public class AdministracionBorreActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private TabLayout tabLayout;
    private MenuItem item_guardar;
    private MenuItem item_edit;
    private MenuItem item_delete;
    private MenuItem item_añadir;
    private AlertDialog.Builder builder;
    private Dialog dialog;



    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administracion_borre);

        toolbar = (Toolbar) findViewById(R.id.toolbarMenu);
        setSupportActionBar(toolbar);



        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_18dp);


        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));


        //tabLayout.setupWithViewPager(mViewPager);


        inicio();
        fragments();

    }

    public void inicio(){

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }


    public void fragments(){

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                switch (tab.getPosition()){

                    case 0:

                        item_añadir.setEnabled(true);
                        item_guardar.setEnabled(false);
                        item_delete.setEnabled(false);
                        item_edit.setEnabled(false);

                        if (item_añadir.isVisible() == false){

                            RegistroBorreFragment.desbloqueo();
                            item_guardar.setEnabled(true);
                        }

                        break;

                    case 1:
                        item_añadir.setEnabled(false);
                        item_guardar.setEnabled(false);
                        item_delete.setEnabled(false);
                        item_edit.setEnabled(true);
                        break;

                    case 2:
                        item_añadir.setEnabled(false);
                        item_guardar.setEnabled(false);
                        item_edit.setEnabled(false);
                        item_delete.setEnabled(true);
                        break;

                    case 3:

                        item_añadir.setEnabled(false);
                        item_guardar.setEnabled(false);
                        item_edit.setEnabled(false);
                        item_delete.setEnabled(false);

                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_administracion_borre, menu);

        item_guardar = menu.findItem(R.id.itemSave).setEnabled(false);
        item_edit = menu.findItem(R.id.itemEdit).setEnabled(false);
        item_delete = menu.findItem(R.id.itemDelete).setEnabled(false);
        item_añadir = menu.findItem(R.id.itemAñadir).setEnabled(true);



        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        dialog = null;
        builder = new AlertDialog.Builder(this);


        switch (item.getItemId()){


            case R.id.action_settings:
                break;

            case R.id.itemSave:

                builder.setTitle("Registro de borrego");
                builder.setMessage("¿Quiere guardar los datos?");
                builder.setIcon(R.drawable.logo);
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                item_añadir.setVisible(true);
                                RegistroBorreFragment.registrarBorregos();
                                Toast.makeText(getApplicationContext(),"Datos Guardados",Toast.LENGTH_LONG).show();
                                RegistroBorreFragment.bloqueo();
                                item_guardar.setEnabled(false);

                            }
                        });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                dialog = builder.create();
                dialog.show();


                break;

            case R.id.itemEdit:
                Toast.makeText(getApplicationContext(),"Datos Editados",Toast.LENGTH_LONG).show();

                break;

            case R.id.itemDelete:
                Toast.makeText(getApplicationContext(),"Datos Eliminados",Toast.LENGTH_LONG).show();
                break;

            case R.id.itemCerrarSesion:

                LoginActivity.borrarSharedPreferences(getApplicationContext());
                Intent login = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(login);
                finish();
                break;

            case R.id.itemAñadir:
                RegistroBorreFragment.desbloqueo();
                item_añadir.setVisible(false);
                item_guardar.setEnabled(true);

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_administracion_borre, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        String[] name_tabs = {"Registrar","Editar","Baja","Consulta"};

        @Override
        public Fragment getItem(int position) {



            switch (position){

                case 0: return new RegistroBorreFragment();

                case 1: return new EdicionBorreFragment();

                case 2: return new BajaBorreFragment();

                case 3: return new ConsultaBorreFragment();
            }


            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return name_tabs[position];

        }
    }
}
