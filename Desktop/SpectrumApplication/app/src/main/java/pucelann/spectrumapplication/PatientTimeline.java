package pucelann.spectrumapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PatientTimeline extends Fragment {

    public static Fragment newInstance(Context context) {
        PatientTimeline f = new PatientTimeline();

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.patient_timeline, null);
        return root;
    }

}