package com.android.sergiomarquez.raisingsheep1;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.sergiomarquez.raisingsheep1.BaseDeDatos.BDManager;

import java.util.ArrayList;


public class EnfermedadInfecciosaFragment extends Fragment {

    private View vista;
    private ListView listView_infeccion;
    private BDManager bdManager;
    private Cursor cursor;
    private TextView textView_infecciosa;


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
}
