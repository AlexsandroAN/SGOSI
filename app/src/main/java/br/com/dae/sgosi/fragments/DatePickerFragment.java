package br.com.dae.sgosi.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

/**
 * Created by 39091 on 08/07/2016.
 */
public class DatePickerFragment extends DialogFragment {

    private DatePickerDialog.OnDateSetListener listener;
    private int dia, mes, ano;

    public DatePickerFragment() {

    }

    public void setDateListener(DatePickerDialog.OnDateSetListener listener) {
        this.listener = listener;
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        dia = args.getInt("dia");
        mes = args.getInt("mes");
        ano = args.getInt("ano");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new DatePickerDialog(getActivity(), listener, ano, mes, dia);
    }
}
