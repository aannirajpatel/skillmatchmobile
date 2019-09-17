package com.aanpatel.skillmatch3;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aan Patel on 12/11/18.
 */

public class UserListAdapter extends ArrayAdapter<User> {
    int storeResourceId;
    private Context storeContext;
    public UserListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<User> objects) {
        super(context, resource, objects);
        storeContext= context;
        storeResourceId=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String name = getItem(position).getName();
        String reg = getItem(position).getReg();
        String bio = getItem(position).getBio();

        User user = new User(name,reg,bio);

        LayoutInflater inflater = LayoutInflater.from(storeContext);
        convertView = inflater.inflate(storeResourceId,parent,false);

        TextView nameTView = (TextView) convertView.findViewById(R.id.nameSearchResult);
        TextView regTView = (TextView) convertView.findViewById(R.id.regSearchResult);
        TextView bioTView = (TextView) convertView.findViewById(R.id.bioSearchResult);

        nameTView.setText(name); regTView.setText(reg); bioTView.setText(bio);

        return convertView;
    }
}
