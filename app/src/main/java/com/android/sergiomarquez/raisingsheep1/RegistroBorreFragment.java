package com.android.sergiomarquez.raisingsheep1;


import android.annotation.SuppressLint;
import android.app.*;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.app.Dialog;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;



public class RegistroBorreFragment extends Fragment implements View.OnClickListener{

    View vista;
    static TextView date;
    static EditText nombre;
    static EditText peso;
    static EditText descripcion;
    static Spinner etapa,raza,sexo;
    com.android.sergiomarquez.raisingsheep1.Dialog dialogos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_registro_borre, container, false);


           inicializacion();
           spinners();
           bloqueo();

        return vista;


    }



    public void inicializacion(){

        date = (TextView) vista.findViewById(R.id.textViewtDate);
        date.setOnClickListener(this);

        etapa = (Spinner) vista.findViewById(R.id.spinnerEtapa);
        raza = (Spinner) vista.findViewById(R.id.spinnerRaza);
        sexo = (Spinner) vista.findViewById(R.id.spinnerSexo);

        nombre = (EditText) vista.findViewById(R.id.editTextName);
        descripcion = (EditText) vista.findViewById(R.id.editTextDescri);
        peso = (EditText) vista.findViewById(R.id.editTextPeso);
        descripcion.setOnClickListener(this);

        dialogos = new com.android.sergiomarquez.raisingsheep1.Dialog(getContext());

        descripcion.setFocusable(false);

    }

    public static void bloqueo(){

        date.setEnabled(false);
        nombre.setEnabled(false);
        descripcion.setEnabled(false);
        peso.setEnabled(false);
        etapa.setEnabled(false);
        raza.setEnabled(false);
        sexo.setEnabled(false);


    }

    public static void desbloqueo(){

        date.setEnabled(true);
        nombre.setEnabled(true);
        descripcion.setEnabled(true);
        peso.setEnabled(true);
        etapa.setEnabled(true);
        raza.setEnabled(true);
        sexo.setEnabled(true);


    }

    public void spinners(){

        List lista_etapa = new ArrayList();
        List lista_raza = new ArrayList();
        List sexos = new ArrayList();

        lista_etapa.add("Cordero");
        lista_etapa.add("Semental");
        lista_etapa.add("Cargada");
        lista_etapa.add("Cuarentena");


        lista_raza.add("Dorset");
        lista_raza.add("Dorper");
        lista_raza.add("Pelibuey");
        lista_raza.add("Hampshire");
        lista_raza.add("Suffolk");

        sexos.add("Macho");
        sexos.add("Hembra");



        ArrayAdapter adapter_etapa = new ArrayAdapter(getActivity(),R.layout.spinner_item_diseno,lista_etapa);
        adapter_etapa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        etapa.setAdapter(adapter_etapa);


        ArrayAdapter adapter_raza = new ArrayAdapter(getActivity(),R.layout.spinner_item_diseno,lista_raza);
        adapter_raza.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        raza.setAdapter(adapter_raza);

        ArrayAdapter adapter_sexo = new ArrayAdapter(getActivity(),R.layout.spinner_item_diseno,sexos);
        adapter_sexo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sexo.setAdapter(adapter_sexo);

    }

    public void dialogoEditText(){

        AlertDialog.Builder dialogo_edit_text = new AlertDialog.Builder(getContext());
        dialogo_edit_text.setTitle("Descripcion de borrego");
        dialogo_edit_text.setMessage("Escriba sus observaciones");
        final EditText descripcionn = new EditText(getContext());
        dialogo_edit_text.setView(descripcionn);

        dialogo_edit_text.setPositiveButton(getString(R.string.acepetar), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String caja_descripcion = descripcionn.getText().toString().trim();

                if (caja_descripcion.length()!=0){

                    descripcion.setText(caja_descripcion);
                }

                else {

                    dialogos.OnCreateDialog("Ingrese sus observaciones").show();
                }
            }
        });

        dialogo_edit_text.setNegativeButton(getString(R.string.cancelar), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        dialogo_edit_text.show();
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.textViewtDate){
            obtenerFecha();
        }

        else if (v.getId() == R.id.editTextDescri){
            dialogoEditText();


        }

    }


    public void obtenerFecha(){

        final Calendar calendario = Calendar.getInstance();
        int yy = calendario.get(Calendar.YEAR);
        int mm = calendario.get(Calendar.MONTH);
        int dd = calendario.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                String fecha = String.valueOf(year) +"/"+String.valueOf(monthOfYear)
                        +"/"+String.valueOf(dayOfMonth);
                date.setText(fecha);

            }
        }, yy, mm, dd);

        datePicker.show();

    }
}


