package br.com.dae.sgosi.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.dae.sgosi.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClienteFragment extends Fragment {

    View view;

    public ClienteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       view = inflater.inflate(R.layout.fragment_cliente, container, false);
        return view;
    }

}
