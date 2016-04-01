package com.vidyo.vidyosample;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PatientEdContent extends Fragment {

    public static Fragment newInstance(Context context) {
        PatientEdContent f = new PatientEdContent();

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.patient_ed_content, null);
        return root;
    }

}
