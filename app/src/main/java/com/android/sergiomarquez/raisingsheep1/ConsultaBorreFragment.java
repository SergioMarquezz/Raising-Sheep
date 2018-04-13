package com.android.sergiomarquez.raisingsheep1;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.sergiomarquez.raisingsheep1.BaseDeDatos.BDManager;

import java.util.ArrayList;


public class ConsultaBorreFragment extends Fragment implements AdapterView.OnItemClickListener {


    View vista;
    ListView listView_borregos;
    Cursor cursor;
    BDManager bdManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista =  inflater.inflate(R.layout.fragment_consulta_borre, container, false);


        iniciar();
        llenarListView();

        return vista;

    }

    public void iniciar(){

        listView_borregos = (ListView) vista.findViewById(R.id.listViewBorregos);
        bdManager = new BDManager(getContext());
        listView_borregos.setOnItemClickListener(this);
    }

    public void llenarListView(){

        ArrayList<String> lista_borregos = new ArrayList<String>();

        cursor = bdManager.selectTodosBorregos();

        while (cursor.moveToNext()){

            int id = cursor.getInt(cursor.getColumnIndex("id_borrego"));
            String nombre = cursor.getString(cursor.getColumnIndex("nombre"));
            lista_borregos.add(id +" - " + nombre);

            ArrayAdapter adapter_borre = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,lista_borregos);
            adapter_borre.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            listView_borregos.setAdapter(adapter_borre);
        }

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        cursor = bdManager.selectTodosBorregos();


        if (cursor.moveToPosition(position)){

            String informe = "id: "+ cursor.getInt(cursor.getColumnIndex("id_borrego"))+"\n";
            informe +="Nombre: " + cursor.getString(cursor.getColumnIndex("nombre")) +"\n";
            informe += "Sexo: " + cursor.getString(cursor.getColumnIndex("sexo"))+"\n";
            informe += "Peso: " + cursor.getDouble(cursor.getColumnIndex("peso"));

            Toast.makeText(getContext(),informe ,Toast.LENGTH_LONG).show();
        }








    }
}
