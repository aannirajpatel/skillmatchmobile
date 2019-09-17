package com.aanpatel.skillmatch3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SignUpActivity extends AppCompatActivity {

    EditText editReg, editEmail, editPass, editFname, editLname, editPhone, editCity, editBio;

    String url= "http://192.168.43.139/skillmatchmobile/signup.php";

    JSONParser jsonParser=new JSONParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    private class SignUpTry extends AsyncTask<String,String,JSONObject> {

        @Override
        protected JSONObject doInBackground(String... strings) {
            ArrayList params = new ArrayList();
            params.add(new BasicNameValuePair("username",strings[0]));
            params.add(new BasicNameValuePair("email",strings[1]));
            params.add(new BasicNameValuePair("password",strings[2]));
            params.add(new BasicNameValuePair("fname",strings[3]));
            params.add(new BasicNameValuePair("lname",strings[4]));
            params.add(new BasicNameValuePair("phone",strings[5]));
            params.add(new BasicNameValuePair("city",strings[6]));
            params.add(new BasicNameValuePair("bio",strings[7]));
            JSONObject json = jsonParser.makeHttpRequest(url,"POST",params);
            return json;
        }

        protected void onPostExecute(JSONObject jsonObject) {
            try{
                Toast.makeText(SignUpActivity.this,jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                Log.i("Passed",jsonObject.getString("success"));
                if(Integer.parseInt(jsonObject.getString("success"))==1){
                    Intent intent = new Intent(getApplicationContext(),DashboardActivity.class);
                    EditText et = (EditText) findViewById(R.id.reg);
                    intent.putExtra("username",et.getText().toString());
                    SharedPreferences sp = getSharedPreferences("com.aanpatel.skillmatch3", Context.MODE_PRIVATE);
                    sp.edit().putString("username",et.getText().toString()).commit();
                    et = (EditText) findViewById(R.id.pass);
                    sp.edit().putString("password",et.getText().toString()).commit();
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

    public void signUp(View view){
        editReg=(EditText) findViewById(R.id.reg);
        editEmail = (EditText) findViewById(R.id.email);
        editPass = (EditText) findViewById(R.id.pass);
        editFname = (EditText) findViewById(R.id.fname);
        editLname = (EditText) findViewById(R.id.lname);
        editPhone = (EditText) findViewById(R.id.phone);
        editCity = (EditText) findViewById(R.id.city);
        editBio = (EditText) findViewById(R.id.bio);
        SignUpTry signUpTry = new SignUpTry();
        signUpTry.execute(editReg.getText().toString(), editEmail.getText().toString(), editPass.getText().toString(), editFname.getText().toString(), editLname.getText().toString(),editPhone.getText().toString(), editCity.getText().toString(), editBio.getText().toString());

    }

    public void backClick(View view){
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        finish();
        startActivity(intent);
    }
}
