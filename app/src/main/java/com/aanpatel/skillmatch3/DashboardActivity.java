package com.aanpatel.skillmatch3;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {

    String url= "http://192.168.43.139/skillmatchmobile/logout.php";

    JSONParser jsonParser=new JSONParser();

    String getUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        TextView tV = (TextView) findViewById(R.id.welcomeText);
        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        if(bd != null)
        {
            getUser = (String) bd.get("username");
            tV.setText("Welcome, "+getUser);
        }
    }

    public void search(View view){
        Intent intent = new Intent(getApplicationContext(),SearchResultActivity.class);
        TextView tV = (TextView) findViewById(R.id.searchBox);
        intent.putExtra("query",tV.getText().toString());
        intent.putExtra("username",getUser);
        startActivity(intent);
    }

    public void showSkills(View view){
        Intent intent = new Intent(getApplicationContext(),SkillShowActivity.class);
        intent.putExtra("usersrc",getUser);
        intent.putExtra("username",getUser);
        startActivity(intent);
    }

    public void editSkills(View view){
        Intent intent = new Intent(getApplicationContext(),UpdateSkillsActivity.class);
        intent.putExtra("username",getUser);
        startActivity(intent);
    }

    public void editProfile(View view){
        //Toast.makeText(DashboardActivity.this,"Feature will be added later",Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getApplicationContext(),UpdateProfileActivity.class);
        intent.putExtra("username",getUser);
        startActivity(intent);
    }

    public void acquaintances(View view){
        Intent intent = new Intent(getApplicationContext(),AcquaintanceListActivity.class);
        intent.putExtra("username",getUser);
        startActivity(intent);
    }

    public void logout(View view){
        LogoutTry logoutTry = new LogoutTry();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            finishAffinity();
        }
        logoutTry.execute();
    }

    private class LogoutTry extends AsyncTask<String,String,JSONObject> {

            @Override
            protected JSONObject doInBackground(String... strings) {
                ArrayList params = new ArrayList();
                JSONObject json = jsonParser.makeHttpRequest(url,"POST",params);
                return json;
            }

            protected void onPostExecute(JSONObject jsonObject) {
                try{
                    Toast.makeText(DashboardActivity.this,jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                    Log.i("Passed",jsonObject.getString("success"));
                    if(Integer.parseInt(jsonObject.getString("success"))==1){
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        finish();
                        startActivity(intent);
                        Log.i("Finished","Yes");
                    }
                    else{
                        Log.i("Finished","No");
                    }
                } catch (JSONException e) {
                    Log.i("JSONException","JSON Problem is there");
                    e.printStackTrace();
                }

            }
    }
}
