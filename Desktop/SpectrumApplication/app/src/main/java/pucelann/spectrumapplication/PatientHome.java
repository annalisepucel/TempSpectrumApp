package pucelann.spectrumapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import org.json.JSONArray;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PatientHome extends ActionBarActivity {

    ExpandableListAdapter mListAdapter;
    ExpandableListView mExpListView;
    DrawerLayout mDrawerLayout;
    List<String> mContactList;
    List<String> mContactOptions;
    List<String> mListDataHeader;
    HashMap<String, List<String>> mListDataChild;
    private final String[] fragments = {
            "pucelann.spectrumapplication.PatientTimeline",
            "pucelann.spectrumapplication.PatientEdContent",
            "pucelann.spectrumapplication.ContactStaff"
    };
    private String mPatientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home);

        SharedPreferences pref = getSharedPreferences("PatientInfo", Context.MODE_PRIVATE);
        mPatientId = pref.getString("PatientId", "");

        //temp
        mPatientId = "10";

        mContactList = new ArrayList<String>();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mExpListView = (ExpandableListView) findViewById(R.id.drawer_list);
        RetreiveStaffInfo asyn = new RetreiveStaffInfo();
        asyn.execute(mPatientId);

        prepareListData();


        mListAdapter = new ExpandableListAdapter(this, mListDataHeader, mListDataChild);
        mExpListView.setAdapter(mListAdapter);


        mExpListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, final int groupPosition, long id) {
                mDrawerLayout.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
                    @Override
                    public void onDrawerClosed(View drawerView) {
                        super.onDrawerClosed(drawerView);

                        //If timeline or content selected switch fragments
                        if (groupPosition == 0 || groupPosition == 1) {
                            FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
                            tx.replace(R.id.main, Fragment.instantiate(PatientHome.this, fragments[groupPosition]));
                            tx.commit();
                        }else{
                            mExpListView.collapseGroup(groupPosition);
                        }
                    }
                });
                if(groupPosition != 2) {
                    mDrawerLayout.closeDrawer(mExpListView);
                    return false;
                }else if(mExpListView.isGroupExpanded(groupPosition)){
                    mExpListView.collapseGroup(groupPosition);
                } else{
                    mExpListView.expandGroup(groupPosition);
                }
                return true;
            }
        });

        mExpListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("Staff Name", mContactList.get(childPosition));

                ContactStaff contactFrag = new ContactStaff();
                contactFrag.setArguments(bundle);

                FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
                fTrans.replace(R.id.main, contactFrag);
                fTrans.commit();
                mDrawerLayout.closeDrawer(parent);
                return true;
            }
        });
    }

    /*
    * Preparing the list data
    */
    private void prepareListData() {
        mListDataHeader = new ArrayList<String>();
        mListDataChild = new HashMap<String, List<String>>();

        //Add child to list
        mListDataHeader.add("Timeline");
        mListDataHeader.add("Educational Content");
        mListDataHeader.add("Contact");

        mContactOptions = new ArrayList<String>();
        mContactOptions.add("Message");
        mContactOptions.add("Video Chat");

        mListDataChild.put(mListDataHeader.get(2), mContactList);

    }

    /***
     * Async class to grab staff info based on patient ID
     */
    private class RetreiveStaffInfo extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {

        }

        protected String doInBackground(String ... params){

            try{
                String pid = params[0];

                //URL from webapi to grab staff related to specific patient
                URL STAFF_CONTACT_URL = new URL("http://35.9.22.233:81/Api/patients/staff/");
                URL staff_contact_url = new URL(STAFF_CONTACT_URL + pid);

                HttpURLConnection urlStaffConnection = (HttpURLConnection) staff_contact_url.openConnection();

                try{
                    BufferedReader br = new BufferedReader(new InputStreamReader(urlStaffConnection.getInputStream()));
                    StringBuilder strBuilder = new StringBuilder();

                    String line;
                    //Grab results from webapi
                    while((line = br.readLine()) != null){
                        strBuilder.append(line).append("\n");
                    }

                    br.close();
                    return strBuilder.toString();

                }catch(Exception e){
                    Log.d("RETREIVING ERROR:", e.toString());
                    return null;
                }finally {
                    urlStaffConnection.disconnect();
                }

            }catch(Exception e) {
                Log.d("RETREIVING STAFF:", e.toString());
                return null;
            }
        }

        protected void onPostExecute(String response){

            if(response== null) {
                Log.d("NO DATA", response);
            }else{
                try {
                    //Convert string to array of json objects
                    JSONArray jsonArr = new JSONArray(response);
                    String temp = "";

                    for(int i = 0; i < jsonArr.length(); i++) {
                        temp = jsonArr.getJSONObject(i).getString("first").trim();
                        temp += " ";
                        temp += jsonArr.getJSONObject(i).getString("last").trim();
                        mContactList.add(temp);
                    }

                }catch(Exception e){
                    Log.d("JSON Error", e.toString());
                }
            }
        }

    }
}
