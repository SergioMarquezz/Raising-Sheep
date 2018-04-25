package com.android.sergiomarquez.raisingsheep1;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.sergiomarquez.raisingsheep1.BaseDeDatos.BDManager;


public class EdicionBorreFragment extends Fragment {

    View vista;
   static EditText editText_peso, editText_etapa, editText_estatus, editText_descripcion, editText_marca;
   // Cursor cursor;
   static BDManager bdManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        vista = inflater.inflate(R.layout.fragment_edicion_borre, container, false);

        inicio();

        return vista;
    }

    public void inicio(){

        /*
        editText_descripcion = (EditText) vista.findViewById(R.id.editTextEditarDescri);
        editText_peso = (EditText) vista.findViewById(R.id.editTextEditarPeso);
        editText_etapa = (EditText) vista.findViewById(R.id.editTextEditarEtapa);
        editText_estatus = (EditText) vista.findViewById(R.id.editTextEditarEstatus);
        editText_marca = (EditText) vista.findViewById(R.id.editTextEditarMarca);

        bdManager = new BDManager(getContext());

        */
    }

    public static  void editarBorrego(){

        bdManager.abrirBaseDatos();
        double peso = Double.parseDouble(editText_peso.getText().toString());
        String etapa = editText_etapa.getText().toString();
        String estatus = editText_estatus.getText().toString();
        String descripcion = editText_descripcion.getText().toString();
        String marca = editText_marca.getText().toString();

        bdManager.editarBorregos(peso,etapa,estatus,descripcion,marca);

        bdManager.cerrarBaseDatos();
    }
}
