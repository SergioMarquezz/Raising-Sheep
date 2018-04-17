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


public class EnfermedadParasitoriaFragment extends Fragment {


    private View vista;
    private Cursor cursor;
    private ListView listView_parasitoria;
    private TextView textView_parasitoria;
    private BDManager bdManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        vista =  inflater.inflate(R.layout.fragment_enfermedad_parasitoria, container, false);

        inicio();
        llenarListView();

        return vista;
    }

    public void inicio(){

        PrincipalActivity.toolbar.setTitle("Parasitorias");
        listView_parasitoria = (ListView) vista.findViewById(R.id.listViewParasitoria);
        textView_parasitoria = (TextView) vista.findViewById(R.id.textViewParasitoria);
        bdManager = new BDManager(getContext());
        textView_parasitoria.setVisibility(View.INVISIBLE);
    }

    public void llenarListView(){

        ArrayList<String> lista_parasitoria = new ArrayList<String>();

        int num1 = Integer.parseInt(String.valueOf(textView_parasitoria.getText()));


        cursor = bdManager.enfermedades(num1);

        while (cursor.moveToNext()){

            String nombre = cursor.getString(cursor.getColumnIndex("nombre"));
            lista_parasitoria.add(nombre);

            ArrayAdapter adapter_parasitoria= new ArrayAdapter(getActivity(),R.layout.spinner_item_diseno,lista_parasitoria);
            adapter_parasitoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            listView_parasitoria.setAdapter(adapter_parasitoria);

        }
    }
}
