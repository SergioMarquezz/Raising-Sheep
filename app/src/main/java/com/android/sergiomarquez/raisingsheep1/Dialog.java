package com.android.sergiomarquez.raisingsheep1;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;

/**
 * Created by SergioMarquez on 06/03/2018.
 */

public class Dialog  {

    AlertDialog.Builder dialogo;
    AlertDialog dialog_save;
    Context contexto;

    public Dialog(Context contexto) {

        this.contexto = contexto;
        dialogo = new AlertDialog.Builder(this.contexto);

    }

    protected android.app.Dialog OnCreateDialog(String mensaje) {

        android.app.Dialog dialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(contexto);
        builder = builder.setMessage(mensaje)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        dialog = builder.create();

        return dialog;


    }


}