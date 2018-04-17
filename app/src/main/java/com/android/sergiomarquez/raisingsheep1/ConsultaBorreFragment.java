package com.android.sergiomarquez.raisingsheep1;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.sergiomarquez.raisingsheep1.BaseDeDatos.BDManager;

import java.util.ArrayList;


public class ConsultaBorreFragment extends Fragment implements AdapterView.OnItemClickListener{


    View vista;
    BDManager bdManager;
    public static TextView textView_fecha,textView_id_borre,textView_marca,textView_name,textView_sex;
    public static TextView textView_pes,textView_raz,textView_etapa,textView_estatus,textView_descr;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista =  inflater.inflate(R.layout.fragment_consulta_borre, container, false);


        iniciar();


        return vista;

    }

    public void iniciar(){


        bdManager = new BDManager(getContext());

        textView_fecha = (TextView) vista.findViewById(R.id.textViewFecha);
        textView_etapa = (TextView) vista.findViewById(R.id.textViewEtapa);
        textView_id_borre = (TextView) vista.findViewById(R.id.textViewNumBorre);
        textView_marca = (TextView) vista.findViewById(R.id.textViewMarcaje);
        textView_name = (TextView) vista.findViewById(R.id.textViewName);
        textView_sex = (TextView) vista.findViewById(R.id.textViewSexo);
        textView_pes = (TextView) vista.findViewById(R.id.textViewPeso);
        textView_raz = (TextView) vista.findViewById(R.id.textViewRaza);
        textView_estatus = (TextView) vista.findViewById(R.id.textViewEstatus);
        textView_descr = (TextView) vista.findViewById(R.id.textViewDescrip);

    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


/*
            int id_borre =  cursor.getInt(cursor.getColumnIndex("id_borrego"));
            String fecha = cursor.getString(cursor.getColumnIndex("fecha_registro"));
            String marca = cursor.getString(cursor.getColumnIndex("marcaje"));
            String nombre = cursor.getString(cursor.getColumnIndex("nombre"));
            String sex = cursor.getString(cursor.getColumnIndex("sexo"));
            double pes = cursor.getDouble(cursor.getColumnIndex("peso"));
            String raz = cursor.getString(cursor.getColumnIndex("tipo_raza"));
            String eta = cursor.getString(cursor.getColumnIndex("tipo_etapa"));
            String sali = cursor.getString(cursor.getColumnIndex("tipo_salida"));
            String descri = cursor.getString(cursor.getColumnIndex("descripcion"));

            Intent detalles = new Intent(getActivity(),DetallesListViewActivity.class);

            Bundle datos = new Bundle();
            datos.putInt("id",id_borre);
            datos.putString("fecha",fecha);
            datos.putString("marca",marca);
            datos.putString("name",nombre);
            datos.putString("sexo",sex);
            datos.putDouble("pes",pes);
            datos.putString("raza",raz);
            datos.putString("etapa",eta);
            datos.putString("salida",sali);
            datos.putString("des",descri);

            detalles.putExtras(datos);
            startActivity(detalles);
            */
           // int informe =  cursor.getInt(cursor.getColumnIndex("id_borrego"));

            /*
            informe += "Fecha: " + cursor.getString(cursor.getColumnIndex("fecha_registro"))+"\n";
            informe += "Marcaje: " + cursor.getString(cursor.getColumnIndex("marcaje"))+"\n";
            informe += "Nombre: " + cursor.getString(cursor.getColumnIndex("nombre"))+"\n";
            informe += "Sexo: " + cursor.getString(cursor.getColumnIndex("sexo"))+"\n";
            informe += "Peso: " + cursor.getDouble(cursor.getColumnIndex("peso"))+"\n";
            informe += "Raza: " + cursor.getString(cursor.getColumnIndex("tipo_raza"))+"\n";
            informe += "Etapa: " + cursor.getString(cursor.getColumnIndex("tipo_etapa"))+"\n";
            informe += "Estatus: " + cursor.getString(cursor.getColumnIndex("tipo_salida"))+"\n";
            informe += "Descripcion: " + cursor.getString(cursor.getColumnIndex("descripcion"));
        */


            //Toast.makeText(getContext(),informe ,Toast.LENGTH_LONG).show();

        }

}
