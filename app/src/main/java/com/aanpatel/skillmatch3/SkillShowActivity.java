package com.aanpatel.skillmatch3;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SkillShowActivity extends AppCompatActivity {
    String showWhom = "", getUser = "";
    String url = "http://192.168.43.139/skillmatchmobile/profileshow.php";
    JSONParser jsonParser = new JSONParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill_show);
        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        if (bd != null) {
            showWhom = (String) bd.get("usersrc");
            getUser = (String) bd.get("username");
        }
        SkillTry skillTry = new SkillTry();
        skillTry.execute(showWhom, getUser);
    }

    public void backClick(View view) {
        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
        intent.putExtra("username",getUser);
        startActivity(intent);
    }

    private class SkillTry extends AsyncTask<String, String, JSONObject> {

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
                Toast.makeText(SkillShowActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                if (Integer.parseInt(jsonObject.getString("success")) == 1) {
                    String fullName = jsonObject.getString("fullName");
                    TextView tVTitle = (TextView) findViewById(R.id.personTV);
                    tVTitle.setText("Profile: " + fullName);
                    JSONArray jsonArray = new JSONArray(jsonObject.getString("data"));
                    ArrayList<Skill> skillArrayList = new ArrayList<Skill>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jObj = new JSONObject(jsonArray.get(i).toString());
                        String skillName = jObj.getString("skillName");
                        String skillLevel = jObj.getString("skillLevel");
                        skillLevel = "Level: " + skillLevel;
                        skillArrayList.add(new Skill(skillName, skillLevel));
                        Log.i("DATA", skillName + " " + skillLevel);
                    }

                    ListView listView = (ListView) findViewById(R.id.skillNameList);

                    SkillListAdapter skillListAdapter = new SkillListAdapter(SkillShowActivity.this, R.layout.skill_result_layout, skillArrayList);
                    listView.setAdapter(skillListAdapter);

                    //Log.i("First data point",jsonArray.getJSONObject(0).toString());

                    Log.i("JSON Data", jsonObject.getString("data"));
                    Log.i("Success", "Yes");
                } else {
                    Log.i("Success", "No");
                }
            } catch (JSONException e) {
                Log.i("JSONException", "JSON Problem is there");
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void contactClick(View view) {
        Intent intent = new Intent(getApplicationContext(), ContactDetailsShowActivity.class);
        intent.putExtra("usersrc", showWhom);
        intent.putExtra("username", getUser);
        Log.i("click", "contactClick");
        startActivity(intent);
    }
}
