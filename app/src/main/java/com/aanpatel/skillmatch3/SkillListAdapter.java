package com.aanpatel.skillmatch3;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Gamer Patel on 12/11/18.
 */

public class SkillListAdapter extends ArrayAdapter<Skill> {
    int storeResourceId;
    private Context storeContext;
    public SkillListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Skill> objects) {
        super(context, resource, objects);
        storeContext= context;
        storeResourceId=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String sname = getItem(position).getSkillName();
        String slevel= getItem(position).getSkillLevel();

        Skill skill = new Skill(sname,slevel);

        LayoutInflater inflater = LayoutInflater.from(storeContext);
        convertView = inflater.inflate(storeResourceId,parent,false);

        TextView snameTView = (TextView) convertView.findViewById(R.id.skillResultName);
        TextView slevelTView = (TextView) convertView.findViewById(R.id.skillResultLevel);

        snameTView.setText(sname); slevelTView.setText(slevel);
        Log.i("HELLOTRY",sname+"TRY "+slevel);
        return convertView;
    }
}
