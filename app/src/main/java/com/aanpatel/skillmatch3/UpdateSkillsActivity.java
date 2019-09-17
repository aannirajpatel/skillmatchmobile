package com.aanpatel.skillmatch3;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UpdateSkillsActivity extends AppCompatActivity {

    EditText skillName0, skillName1, skillName2, skillName3, skillName4, skillLevel0, skillLevel1, skillLevel2, skillLevel3, skillLevel4;

    String url = "http://192.168.43.139/skillmatchmobile/editskills.php";

    JSONParser jsonParser = new JSONParser();

    public void backClick(View view) {
        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
        intent.putExtra("username",getUser);
        startActivity(intent);
    }

    private class EditSkillsTry extends AsyncTask<String, String, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... strings) {
            ArrayList params = new ArrayList();
            //0:s,1:l,2:s,3:l,4:s,5:l,6:s,7:l,8:s,9:l
            for (int i = 0; i < 10; i = i + 2) {
                params.add(new BasicNameValuePair("skillName", strings[i]));
                params.add(new BasicNameValuePair("skillLevel", strings[i + 1]));
            }
            params.add(new BasicNameValuePair("username",getUser));
            JSONObject json = jsonParser.makeHttpRequest(url, "GET", params);
            return json;
        }

        protected void onPostExecute(JSONObject jsonObject) {
            try {
                Toast.makeText(UpdateSkillsActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                Log.i("JSONException", "JSON Problem is there");
                e.printStackTrace();
            }

        }
    }

    public void updateSkills(View view) {
        String s0, s1, s2, s3, s4, l0, l1, l2, l3, l4;
        skillName0 = (EditText) findViewById(R.id.skillName0);
        s0 = skillName0.getText().toString();
        skillName1 = (EditText) findViewById(R.id.skillName1);
        s1 = skillName1.getText().toString();
        skillName2 = (EditText) findViewById(R.id.skillName2);
        s2 = skillName2.getText().toString();
        skillName3 = (EditText) findViewById(R.id.skillName3);
        s3 = skillName3.getText().toString();
        skillName4 = (EditText) findViewById(R.id.skillName4);
        s4 = skillName4.getText().toString();
        skillLevel0 = (EditText) findViewById(R.id.skillLevel0);
        l0 = skillLevel0.getText().toString();
        skillLevel1 = (EditText) findViewById(R.id.skillLevel1);
        l1 = skillLevel1.getText().toString();
        skillLevel2 = (EditText) findViewById(R.id.skillLevel2);
        l2 = skillLevel2.getText().toString();
        skillLevel3 = (EditText) findViewById(R.id.skillLevel3);
        l3 = skillLevel3.getText().toString();
        skillLevel4 = (EditText) findViewById(R.id.skillLevel4);
        l4 = skillLevel4.getText().toString();

        EditSkillsTry editSkillsTry = new EditSkillsTry();
        editSkillsTry.execute(s0, l0, s1, l1, s2, l2, s3, l3, s4, l4);
    }

    public String getUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_skills);
        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        if(bd != null){
            getUser=bd.getString("username");
        }
    }

}