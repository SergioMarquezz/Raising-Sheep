package com.android.sergiomarquez.raisingsheep1;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.sergiomarquez.raisingsheep1.BaseDeDatos.BDManager;

import java.util.ArrayList;


public class SintomasFragment extends Fragment implements AdapterView.OnItemClickListener,View.OnClickListener {

    View vista;
    Button show;
    Cursor cursor;
    BDManager bdManager;
    ArrayList<String> selectItems = new ArrayList<>();
    ListView listView_sintomas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        vista = inflater.inflate(R.layout.fragment_sintomas, container, false);

        inicio();


        //String[] items = {"Sintoma 1","Sintoma 2","Sintoma 3","Sintoma 4", "Sintoma 5","Sintoma 6"};
        //ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),R.layout.item_check,R.id.checkboxItem,items);
        //listView_sintomas.setAdapter(adapter);

    llenarListView();


        return vista;
    }

    public void inicio(){
        show = (Button) vista.findViewById(R.id.buttonListView);
        show.setOnClickListener(this);
        listView_sintomas = (ListView) vista.findViewById(R.id.listViewCheck);
        listView_sintomas.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView_sintomas.setOnItemClickListener(this);
        bdManager = new BDManager(getContext());

    }

    public void llenarListView(){

        ArrayList<String> items = new ArrayList<>();

        cursor = bdManager.diversosSintomas();

        while (cursor.moveToNext()){

            String sintoma = cursor.getString(cursor.getColumnIndex("tipo_sintoma"));
            items.add(sintoma);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),R.layout.item_check,R.id.checkboxItem,items);
            listView_sintomas.setAdapter(adapter);


        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        String selectItem = ((TextView)view).getText().toString();

        if (selectItems.contains(selectItem)){

            selectItems.remove(selectItem);
        }

        else {

            selectItems.add(selectItem);

            cursor = bdManager.enfermedadesSintomas(selectItem);

            if (cursor.moveToNext()){
                String nombre = cursor.getString(cursor.getColumnIndex("tipo_sintoma"));
                Toast.makeText(getContext(),"Selecionasten\n"+nombre,Toast.LENGTH_LONG).show();
            }
        }
    }

    public void showSelectItem(View view){

        String items = "";

        for (String item:selectItems){

            items+="-"+item+"\n";
        }

        Toast.makeText(getContext(),"Selecionasten\n"+items,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {

        if (v.getId()==R.id.buttonListView){
            showSelectItem(v);
        }
    }
}
