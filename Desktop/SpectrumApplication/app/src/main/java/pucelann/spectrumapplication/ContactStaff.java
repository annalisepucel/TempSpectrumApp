package pucelann.spectrumapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ContactStaff extends Fragment {

    public static Fragment newInstance(Context context) {
        ContactStaff f = new ContactStaff();

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.contact_staff, null);
        TextView staff_name = (TextView) root.findViewById(R.id.StaffNameText);

        staff_name.setText(getArguments().getString("Staff Name"));

        return root;
    }

}