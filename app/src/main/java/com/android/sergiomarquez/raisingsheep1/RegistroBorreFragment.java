package com.android.sergiomarquez.raisingsheep1;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.sergiomarquez.raisingsheep1.BaseDeDatos.BDManager;

import java.util.ArrayList;
import java.util.Calendar;



public class RegistroBorreFragment extends Fragment implements View.OnClickListener,
        AdapterView.OnItemSelectedListener{

    //VARIABLES GLOBALES

    private View vista;
    private static TextView date;
    private static EditText nombre;
    private static EditText peso;
    private static EditText descripcion;
    private static Spinner etapa,raza,sexo,salida;
    private Cursor cursor;


    private com.android.sergiomarquez.raisingsheep1.Dialog dialogos;
    private static BDManager bd_manager;

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


    /*EN ESTE METODO SE HACE VINCULACION HACIA CADA WIDGET DE LA INTERFACE (Layout)
      CON SUS RESPECTIVO id DE CADA UNO Y SE LLAMA A LOS METODOS DE CLICKEOS PARA QUE
      SEAN ESCUCHADOS POR LOS WIDGETS CORRESPONDIENTES
     */

    public void inicializacion(){

        date = (TextView) vista.findViewById(R.id.textViewtDate);
        date.setOnClickListener(this);

        etapa = (Spinner) vista.findViewById(R.id.spinnerEtapa);
        raza = (Spinner) vista.findViewById(R.id.spinnerRaza);
        sexo = (Spinner) vista.findViewById(R.id.spinnerSexo);
        salida = (Spinner) vista.findViewById(R.id.spinnerEstatus);

        raza.setOnItemSelectedListener(this);
        etapa.setOnItemSelectedListener(this);
        sexo.setOnItemSelectedListener(this);
        salida.setOnItemSelectedListener(this);

        nombre = (EditText) vista.findViewById(R.id.editTextName);
        descripcion = (EditText) vista.findViewById(R.id.editTextDescri);
        peso = (EditText) vista.findViewById(R.id.editTextPeso);
        descripcion.setOnClickListener(this);

        dialogos = new com.android.sergiomarquez.raisingsheep1.Dialog(getContext());
        bd_manager = new BDManager(getContext());

        descripcion.setFocusable(false);

    }

    //METODO PARA BLOQUEAR AL INICIO DE LA ACTIVITY TODOS LOS WIDGETS

    public static void bloqueo(){

        date.setEnabled(false);
        nombre.setEnabled(false);
        descripcion.setEnabled(false);
        peso.setEnabled(false);
        etapa.setEnabled(false);
        raza.setEnabled(false);
        sexo.setEnabled(false);
        salida.setEnabled(false);


    }

    //METODO PARA DESBLOQUEAR TODOS LOS WIDGETS

    public static void desbloqueo(){

        date.setEnabled(true);
        nombre.setEnabled(true);
        descripcion.setEnabled(true);
        peso.setEnabled(true);
        etapa.setEnabled(true);
        raza.setEnabled(true);
        sexo.setEnabled(true);
        salida.setEnabled(true);


    }

    //METODO CON EL CUAL SE LLENA LOS SPINNERS AL INICIAR LA ACTIVITY

    public void spinners(){

    ArrayList<String> lista_raza = new ArrayList<String>();
    ArrayList<String> lista_etapa = new ArrayList<String>();
    ArrayList<String> lista_sexo = new ArrayList<String>();
    ArrayList<String> lista_salida = new ArrayList<String>();

        lista_raza.add("Seleccione");
        lista_etapa.add("Seleccione");
        lista_sexo.add("Seleccione");
        lista_salida.add("Seleccione");
        lista_sexo.add("Hembra");
        lista_sexo.add("Macho");

        cursor = bd_manager.selectRazas();

        while (cursor.moveToNext()){

            String raza_borre = cursor.getString(cursor.getColumnIndex("tipo_raza"));
            lista_raza.add(raza_borre);

            ArrayAdapter adapter_raza = new ArrayAdapter(getActivity(),R.layout.spinner_item_diseno,lista_raza);
            adapter_raza.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            raza.setAdapter(adapter_raza);
        }

        cursor = bd_manager.selectEtapas();

        while (cursor.moveToNext()){

            String etapa_borre = cursor.getString(cursor.getColumnIndex("tipo_etapa"));
            lista_etapa.add(etapa_borre);

            ArrayAdapter adapter_etapa = new ArrayAdapter(getActivity(),R.layout.spinner_item_diseno,lista_etapa);
            adapter_etapa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            etapa.setAdapter(adapter_etapa);
        }

        cursor = bd_manager.selectSalidas();

        while (cursor.moveToNext()){

            String salidas = cursor.getString(cursor.getColumnIndex("tipo_salida"));
            lista_salida.add(salidas);

            ArrayAdapter adapter_salida = new ArrayAdapter(getActivity(),R.layout.spinner_item_diseno,lista_salida);
            adapter_salida.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            salida.setAdapter(adapter_salida);
        }

            ArrayAdapter adapter_sexo = new ArrayAdapter(getActivity(),R.layout.spinner_item_diseno,lista_sexo);
            adapter_sexo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sexo.setAdapter(adapter_sexo);


    }

    public static void registrarBorregos(){

        bd_manager.abrirBaseDatos();

        String fecha = String.valueOf(date.getText()).trim();
        String nombre_borre = String.valueOf(nombre.getText()).trim();
        String descrip = String.valueOf(descripcion.getText()).trim();
        double pesaje = Double.parseDouble(String.valueOf(peso.getText().toString()).trim());
        int etapas = Integer.parseInt(String.valueOf(etapa.getSelectedItemPosition()));
        int razas = Integer.parseInt(String.valueOf(raza.getSelectedItemPosition()));
        int salidas = Integer.parseInt(String.valueOf(salida.getSelectedItemPosition()));
        String sex = String.valueOf(sexo.getSelectedItem().toString());


        bd_manager.insertarBorregos(fecha,nombre_borre,sex,pesaje,descrip,razas,etapas,salidas);

        bd_manager.cerrarBaseDatos();
    }

    /*
    METODO QUE LLAMA A UN AlertDialog AL MOMENTO DE DAR CLIC
    EN UN TextView PARA PODER ESCRIBIR EN UN EditText
     */

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
            obtenerFecha(); //SE LLAMA AL METODO PARA GENERAR EL DatePikerDialog
        }

        else if (v.getId() == R.id.editTextDescri){
            dialogoEditText(); //SE LLAMA AL METODO PARA GENERAR EL AlertDialog CON UN EditText


        }

    }


    /*
    METODO QUE GENERA UN DatePickerDialog PARA COLOCAR LA FECHA EN UN
    TextView ESTE METODO SE EJECUTA AL MOMENTO DE DAR CLIC EN EL TextView
    DE LA FECHA
     */
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}


