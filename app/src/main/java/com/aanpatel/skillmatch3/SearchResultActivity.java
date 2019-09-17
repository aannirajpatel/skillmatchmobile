package com.aanpatel.skillmatch3;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchResultActivity extends AppCompatActivity {

    String url= "http://192.168.43.139/skillmatchmobile/search.php";

    JSONParser jsonParser=new JSONParser();

    String currentUser;
    String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search_result);
        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        if(bd != null)
        {
            currentUser = (String) bd.get("username");
            query = (String) bd.get("query");
        }
        SearchTry searchTry = new SearchTry();
        searchTry.execute(query);
    }

    public void backClick(View view) {
        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
        intent.putExtra("username",currentUser);
        startActivity(intent);
    }

    private class SearchTry extends AsyncTask<String,String,JSONObject> {

        @Override
        protected JSONObject doInBackground(String... strings) {
            ArrayList params = new ArrayList();
            params.add(new BasicNameValuePair("query",strings[0]));
            JSONObject json = jsonParser.makeHttpRequest(url,"GET",params);
            return json;
        }

        protected void onPostExecute(JSONObject jsonObject) {
            try{
                Toast.makeText(SearchResultActivity.this,jsonObject.getString("message"),Toast.LENGTH_LONG).show();


                JSONArray jsonArray = new JSONArray(jsonObject.getString("data"));
                ArrayList<User> userArrayList = new ArrayList<User>();
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jObj = new JSONObject(jsonArray.get(i).toString());
                    String name = jObj.getString("fname")+" "+jObj.getString("lname");
                    String reg = jObj.getString("reg");
                    String bio = jObj.getString("bio");
                    userArrayList.add(new User(name,reg,bio));
                }

                ListView listView = (ListView) findViewById(R.id.searchResultsListView);

                UserListAdapter userListAdapter = new UserListAdapter(SearchResultActivity.this, R.layout.search_result_layout,userArrayList);
                listView.setAdapter(userListAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        TextView nameTV = (TextView) view.findViewById(R.id.regSearchResult);
                        Toast.makeText(SearchResultActivity.this, "Clicked on " + nameTV.getText().toString(), Toast.LENGTH_SHORT).show();
                        //new coding
                        Intent intent = new Intent(getApplicationContext(),SkillShowActivity.class);
                        intent.putExtra("usersrc",nameTV.getText().toString());
                        intent.putExtra("username",currentUser);
                        startActivity(intent);
                    }
                });

                //Log.i("First data point",jsonArray.getJSONObject(0).toString());

                Log.i("JSON Data",jsonObject.getString("data"));

                Log.i("Passed",jsonObject.getString("success"));

                if(Integer.parseInt(jsonObject.getString("success"))==1){

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