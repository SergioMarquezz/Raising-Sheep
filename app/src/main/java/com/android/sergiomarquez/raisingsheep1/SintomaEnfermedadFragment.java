package com.android.sergiomarquez.raisingsheep1;


import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.sergiomarquez.raisingsheep1.BaseDeDatos.BDManager;

import java.security.acl.LastOwnerException;
import java.util.ArrayList;


public class SintomaEnfermedadFragment extends Fragment {

    private TextView textView_enfermedad;
    private View vista;
    private ListView listView_enfermedades;
    private Cursor cursor;
    private BDManager bdManager;
    String texto;

    private Button boton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        vista =  inflater.inflate(R.layout.fragment_sintoma_enfermedad, container, false);

        inicio();

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                ArrayList<String> enfermedad = new ArrayList<String>();


                int nombre = Integer.parseInt(String.valueOf(textView_enfermedad.getText()));

               // Log.w("nombre", nombre);

                cursor = bdManager.enfermedadesSintomas(nombre);

               // Log.w("Cursir", (Throwable) cursor);
                while (cursor.moveToNext()) {


                    String enfermedades = cursor.getString(cursor.getColumnIndex("nombre"));
                    String tipo = cursor.getString(cursor.getColumnIndex("tipo_enfermedad"));

                    enfermedad.add(enfermedades);
                    ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, enfermedad);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    listView_enfermedades.setAdapter(adapter);
                    //enfermedad.add(enfermedades);

                    //ArrayAdapter adapter_infecciosa= new ArrayAdapter(getContext(),R.layout.spinner_item_diseno,enfermedad);
                    // adapter_infecciosa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    //  listView_enfermedades.setAdapter(adapter_infecciosa);
                }


            }

        });

        // llenarListView();

        return vista;
    }

    public void inicio(){

        textView_enfermedad = (TextView) vista.findViewById(R.id.textViewEnfermedad);
        listView_enfermedades = (ListView) vista.findViewById(R.id.listViewEnfermedad);
        boton = (Button) vista.findViewById(R.id.buttonconsulta);
        bdManager = new BDManager(getContext());

         // Bundle bundle = getArguments();

         //texto = bundle.getString("texto");

         //textView_enfermedad.setText(texto);
    }

    public void llenarListView(){



        ArrayList<String> enfermedad = new ArrayList<String>();


        int nombre = Integer.parseInt(textView_enfermedad.getText().toString());

        try{

            //Log.w("nombre",nombre);
            cursor = bdManager.enfermedadesSintomas(nombre);

            while (cursor.moveToNext()){

                String enfermedades = cursor.getString(cursor.getColumnIndex("nombre"));
                String tipo = cursor.getString(cursor.getColumnIndex("tipo_enfermedad"));

                Log.w("enfermedades",enfermedades);
                enfermedad.add(enfermedades);
                ArrayAdapter adapter_infecciosa= new ArrayAdapter(getContext(),R.layout.spinner_item_diseno,enfermedad);
                adapter_infecciosa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                listView_enfermedades.setAdapter(adapter_infecciosa);


            }


        }catch(Exception e){

            Log.w("Error",e.getMessage());
            Toast.makeText(getContext(), "eeror no encontrado "+e.getMessage(), Toast.LENGTH_SHORT).show();

        }


    }


}
