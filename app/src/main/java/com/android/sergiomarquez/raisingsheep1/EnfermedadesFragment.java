package com.android.sergiomarquez.raisingsheep1;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class EnfermedadesFragment extends Fragment implements View.OnClickListener {


    View vista;
    ImageView imageView_parasitoria, imageView_infeciosa;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        vista = inflater.inflate(R.layout.fragment_enfermedades, container, false);

        iniciar();

        return vista;
    }

    public void iniciar(){

        imageView_infeciosa = (ImageView) vista.findViewById(R.id.imageViewInfecciosas);
        imageView_parasitoria = (ImageView) vista.findViewById(R.id.imageViewParasitorias);
        imageView_parasitoria.setOnClickListener(this);
        imageView_infeciosa.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag("fragments");

        if (v.getId() == R.id.imageViewParasitorias){

            fragment = new EnfermedadParasitoriaFragment();

            fragmentManager.beginTransaction().replace(R.id.containerPrincipal,fragment,"fragments").addToBackStack(null).commit();

        }

        else if (v.getId() == R.id.imageViewInfecciosas){

            fragment = new EnfermedadInfecciosaFragment();

            fragmentManager.beginTransaction().replace(R.id.containerPrincipal,fragment,"fragments").addToBackStack(null).commit();
        }
    }
}
