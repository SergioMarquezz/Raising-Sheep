package com.android.sergiomarquez.raisingsheep1;


import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
    Bundle  bundle;
    Dialog dialogo;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        vista = inflater.inflate(R.layout.fragment_sintomas, container, false);

        inicio();
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
        dialogo = new Dialog(getContext());


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

        String item = ((TextView)view).getText().toString();

        if (selectItems.contains(item)){

            selectItems.remove(item);
        }
        else {

            selectItems.add(item);


        }

    }

    public void showSelectedItems(){

        String items = "";

        for (String item:selectItems){

            items+=""+item+"\n";
        }
       // Toast.makeText(getContext(),"Selecionaste"+items,Toast.LENGTH_LONG).show();
        // bundle = new Bundle();
        // bundle.putString("texto",items);

    }



    @Override
    public void onClick(View v) {

        if (v.getId()==R.id.buttonListView){


            if (listView_sintomas.getCheckedItemCount() > 5){


                dialogo.OnCreateDialog("Seleccionar solo 5 sintomas").show();

            }
            else {

                if (listView_sintomas.getCheckedItemCount()!= 0){

                    showSelectedItems();

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    SintomaEnfermedadFragment sintomaEnfermedadFragment = new SintomaEnfermedadFragment();
                   // sintomaEnfermedadFragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.containerPrincipal,sintomaEnfermedadFragment);
                    fragmentTransaction.addToBackStack(null).commit();

                }

                else{

                    dialogo.OnCreateDialog("Seleccione los sintomas").show();
                }

            }

        }
    }


}
