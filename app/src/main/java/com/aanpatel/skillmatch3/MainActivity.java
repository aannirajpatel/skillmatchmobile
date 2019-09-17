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

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static android.text.TextUtils.isEmpty;

public class MainActivity extends AppCompatActivity {

    EditText editReg, editPass;
    Button btnLogin, btnSignUp;

    String url= "http://192.168.43.139/skillmatchmobile/login.php";

    JSONParser jsonParser=new JSONParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sp = getSharedPreferences("com.aanpatel.skillmatch3", Context.MODE_PRIVATE);
        String tUser = sp.getString("username","");
        String tPass = sp.getString("password","");
        TextView tU = (TextView) findViewById(R.id.reg);
        TextView tP = (TextView) findViewById(R.id.pass);
        tU.setText(tUser);
        tP.setText(tPass);
        Log.i("Info",tUser);
    }

    private class LoginTry extends AsyncTask<String,String,JSONObject>{

        @Override
        protected JSONObject doInBackground(String... strings) {
            String username = strings[0];
            String password = strings[1];

            ArrayList params = new ArrayList();
            params.add(new BasicNameValuePair("username",username));
            params.add(new BasicNameValuePair("password",password));
            JSONObject json = jsonParser.makeHttpRequest(url,"POST",params);
            return json;
        }

        protected void onPostExecute(JSONObject jsonObject) {
            try{
                Toast.makeText(MainActivity.this,jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                if(Integer.parseInt(jsonObject.getString("success"))==1){
                    Intent intent = new Intent(getApplicationContext(),DashboardActivity.class);
                    EditText et = (EditText) findViewById(R.id.reg);
                    SharedPreferences sp = getSharedPreferences("com.aanpatel.skillmatch3", Context.MODE_PRIVATE);
                    sp.edit().putString("username",et.getText().toString()).commit();
                    intent.putExtra("username",et.getText().toString());
                    Log.i("username",et.getText().toString());
                    et = (EditText) findViewById(R.id.pass);
                    sp.edit().putString("password",et.getText().toString()).commit();
                    startActivity(intent);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public void login(View view){
        LoginTry loginTry = new LoginTry();
        editReg = (EditText) findViewById(R.id.reg);
        editPass = (EditText) findViewById(R.id.pass);
        String textReg = editReg.getText().toString();
        String textPass = editPass.getText().toString();
        if(!isEmpty(textReg) && !isEmpty(textPass)) {
            loginTry.execute(textReg, textPass);
        }
        else{
            Toast.makeText(MainActivity.this,"Please fill both the fields above",Toast.LENGTH_LONG).show();
        }
    }

    public void signUp(View view){
        Intent intent = new Intent(getApplicationContext(),SignUpActivity.class);
        startActivity(intent);
    }
}
