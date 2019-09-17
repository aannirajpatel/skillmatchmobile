package com.aanpatel.skillmatch3;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ContactDetailsShowActivity extends AppCompatActivity {

    String showWhom = "", getUser = "";
    String url = "http://192.168.43.139/skillmatchmobile/contactShow.php";
    JSONParser jsonParser = new JSONParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details_show);
        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        if (bd != null) {
            showWhom = (String) bd.get("usersrc");
            getUser = (String) bd.get("username");
        }
        ContactTry contactTry = new ContactTry();
        contactTry.execute(showWhom, getUser);
    }

    private class ContactTry extends AsyncTask<String, String, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... strings) {
            ArrayList params = new ArrayList();
            params.add(new BasicNameValuePair("query", strings[0]));
            params.add(new BasicNameValuePair("username", strings[1]));
            JSONObject json = jsonParser.makeHttpRequest(url, "GET", params);
            return json;
        }

        protected void onPostExecute(JSONObject jsonObject) {
            try {
                Toast.makeText(ContactDetailsShowActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                if (Integer.parseInt(jsonObject.getString("success")) == 1) {
                    Log.i("JSON DATA", "onPostExecute: "+jsonObject.toString());
                    String fullName = jsonObject.getString("fullName");
                    Log.i("JSON DATA", fullName);
                    String title = "Contact Details: " + fullName;
                    TextView tVTitle = (TextView) findViewById(R.id.contactTitleBar);
                    tVTitle.setText(title);

                    JSONObject jsonObject1 = new JSONObject(jsonObject.getString("data"));
                    ArrayList<String> arrayList = new ArrayList<String>();

                    String phone = jsonObject1.getString("phone");
                    String city = jsonObject1.getString("city");
                    String email = jsonObject1.getString("email");
                    if(phone=="null") phone = "Not Specified";
                    if(city=="null") city = "Not Specified";
                    if(email=="null") email = "Not Specified";
                    arrayList.add("Phone: "+ phone);
                    arrayList.add("City: "+ city);
                    arrayList.add("E-mail: "+ email);

                    ListView listView = (ListView) findViewById(R.id.contactInfoList);
                    ArrayAdapter<String> contactAdapter = new ArrayAdapter<String>(ContactDetailsShowActivity.this, android.R.layout.simple_list_item_1, arrayList);
                    listView.setAdapter(contactAdapter);

                    //Log.i("First data point",jsonArray.getJSONObject(0).toString());

                    Log.i("JSON Data", jsonObject.getString("data"));
                    Log.i("Success", "Yes");

                } else {
                    Log.i("Success", "No");
                }
            } catch (JSONException e) {
                Log.i("JSONException", "JSON Problem is there");
                e.printStackTrace();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public void backClick(View view){
        Intent intent = new Intent(getApplicationContext(),DashboardActivity.class);
        intent.putExtra("username",getUser);
        startActivity(intent);
    }
}
