package com.vidyo.vidyosample;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

public class PatientTimeline extends Fragment {

    private WebView webView;


    public static Fragment newInstance(Context context) {
        PatientTimeline f = new PatientTimeline();

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.patient_timeline, null);


        SharedPreferences pref = this.getActivity().getSharedPreferences("PatientInfo", Context.MODE_PRIVATE);
        String patientId = pref.getString("PatientId","" );


        TextView patientIDfield = (TextView)root.findViewById(R.id.patientID);
        patientIDfield.setText(patientId);


        webView = (WebView) root.findViewById(R.id.webView);
//        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
//        webView.setBackgroundColor(0x00000000);
//
//
//        webView.loadUrl("file:///android_asset/timeline.html");


        webView.getSettings().setJavaScriptEnabled(true);
        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);


        webView.loadUrl("http://35.9.22.233:81/app/timeline/timeline.html?id=10");

        return root;


    }

}