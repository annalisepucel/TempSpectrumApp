package com.vidyo.vidyosample;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ContactStaff extends Fragment {
    Button send_btn;
    EditText send_message;
    String patient_id;
    String staff_id;
    String staff_name;
    String message;
    TextView view_history;
    String message_history_url_str = "http://35.9.22.233:81/Api/messages/conversation/";

    public static Fragment newInstance(Context context) {
        ContactStaff f = new ContactStaff();

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        final ViewGroup root = (ViewGroup) inflater.inflate(R.layout.contact_staff, null);
        TextView staff_text = (TextView) root.findViewById(R.id.StaffNameText);

        SharedPreferences pref = this.getActivity().getSharedPreferences("PatientInfo", Context.MODE_PRIVATE);
        String patientId = pref.getString("PatientId", "");


        patient_id = patientId;
        staff_id = getArguments().getString("Staff ID");
        staff_name = getArguments().getString("Staff Name");

        staff_text.setText(getArguments().getString("Staff Name"));


        send_btn = (Button) root.findViewById(R.id.btnSendMessage);
        send_message = (EditText) root.findViewById(R.id.CreateMessage);
        view_history = (TextView) root.findViewById(R.id.ViewMessage);

        UpdateHistory asyn = new UpdateHistory();
        asyn.execute();


        //MOVE TO ASYNC CALL
        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get the message to be sent
                message = send_message.getText().toString().trim();
                Log.d("LENGTH", String.valueOf(message.length()));

                if (message.length() == 0) {

                    AlertDialog alert = new AlertDialog.Builder(root.getContext()).create();
                    alert.setTitle("EMPTY MESSAGE");
                    alert.setMessage("The message you're sending is empty.");
                    alert.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alert.show();


                }else{

                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                try {
                    URL url = new URL("http://35.9.22.233:81/Api/message/create");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoOutput(true);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.connect();

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("message1", message);
                    jsonObject.put("sender_id", patient_id);
                    jsonObject.put("sender_role", "patient");
                    jsonObject.put("receiver_id", staff_id);
                    jsonObject.put("receiver_role", "staff");
                    String input = jsonObject.toString();

                    OutputStream os = conn.getOutputStream();
                    os.write(input.getBytes());
                    os.flush();

                    if (conn.getResponseCode() != HttpURLConnection.HTTP_NO_CONTENT) {
                        throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
                    }

                    conn.disconnect();
                    send_message.setText("");
                    UpdateHistory asyn = new UpdateHistory();
                    asyn.execute();

                } catch (MalformedURLException e) {

                    e.printStackTrace();

                } catch (IOException e) {

                    e.printStackTrace();

                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
            }
        });

        return root;
    }

    /***
     * Class to update message history between staff member and patient
     *
     */
    class UpdateHistory extends AsyncTask<String, Void, String> {

        private Exception exception;

        protected void onPreExecute() {

        }

        protected String doInBackground(String... params) {

            try {

                try{
                    URL message_history_url = new URL(message_history_url_str);
                    message_history_url = new URL (message_history_url + patient_id + "/" + staff_id);

                    HttpURLConnection urlConnection = (HttpURLConnection) message_history_url.openConnection();
                    try{
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        StringBuilder stringBuilder = new StringBuilder();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            stringBuilder.append(line).append("\n");
                        }
                        bufferedReader.close();
                        return stringBuilder.toString();

                    }
                    finally{
                        urlConnection.disconnect();
                    }

                }catch(MalformedURLException e){
                    Log.d("URL Exception", e.toString());
                }
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
            return null;
        }

        protected void onPostExecute(String response) {
            if(response == null) {
                response = "THERE WAS AN ERROR";
            }else {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    String message_history = "\n";

                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject temp = jsonArray.getJSONObject(i);
                        String message = temp.getString("message1");
                        String sender_id = temp.getString("sender_id");

                        if(Integer.parseInt(sender_id) == Integer.parseInt(patient_id)){
                            message = "You: " + message;

                        }else{
                            message = staff_name + ": " +  message;
                        }

                        message_history = message_history + message + "\n";

                    }

                    if(jsonArray.length() == 0){
                        view_history.setText("No Messages To Display");
                    }else{
                        view_history.setText(message_history);
                    }

                }catch(Exception e){
                    Log.d("JSON ERROR", e.toString());
                }
            }

        }
    }


}