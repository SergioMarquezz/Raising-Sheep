package com.android.sergiomarquez.raisingsheep1;


import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.sergiomarquez.raisingsheep1.BaseDeDatos.BDManager;

import java.util.ArrayList;


public class EnfermedadInfecciosaFragment extends Fragment implements View.OnClickListener,AdapterView.OnItemClickListener {

    private View vista;
    private ListView listView_infeccion;
    private BDManager bdManager;
    private Cursor cursor;
    private TextView textView_infecciosa;
    private Button link;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        vista =  inflater.inflate(R.layout.fragment_enfermedad_infecciosa, container, false);

        inicio();
        llenarListView();

        return vista;
    }

    public void inicio(){

        PrincipalActivity.toolbar.setTitle("Infecciosas");
        textView_infecciosa = (TextView) vista.findViewById(R.id.textViewInfeciosa);
        listView_infeccion = (ListView) vista.findViewById(R.id.listViewInfeciosa);
        bdManager = new BDManager(getContext());
        textView_infecciosa.setVisibility(View.INVISIBLE);
        link = (Button) vista.findViewById(R.id.buttonMasIfeccion);
        link.setOnClickListener(this);
        listView_infeccion.setOnItemClickListener(this);

    }

    public void linkGoogle(){

        Intent google = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.uno.org.mx/empezar/infecciosas.html"));
        startActivity(google);

    }



    public void llenarListView(){

        ArrayList<String> lista_infecciosa = new ArrayList<String>();

        int num1 = Integer.parseInt(String.valueOf(textView_infecciosa.getText()));


        cursor = bdManager.enfermedades(num1);

        while (cursor.moveToNext()){

            String nombre = cursor.getString(cursor.getColumnIndex("nombre"));
            lista_infecciosa.add(nombre);

            ArrayAdapter adapter_infecciosa= new ArrayAdapter(getContext(),R.layout.spinner_item_diseno,lista_infecciosa);
            adapter_infecciosa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            listView_infeccion.setAdapter(adapter_infecciosa);

        }
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.buttonMasIfeccion){

            linkGoogle();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

         switch ((int) listView_infeccion.getItemIdAtPosition(position)){

             case 0:
                 Intent google = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.uno.org.mx/empezar/neumonia.html"));
                 startActivity(google);
                 break;

         }



           ;








    }
}
