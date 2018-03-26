package com.example.shamim.nstubds;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Shamim on 16-Feb-18.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {


    private Context context;
    private List<String> ListDataHeader;
    private HashMap<String, List<String>> ListHashMap;

    public ExpandableListAdapter(Context context, List<String> listDataHeader, HashMap<String, List<String>> listHashMap) {
        this.context = context;
        ListDataHeader = listDataHeader;
        ListHashMap = listHashMap;
    }

    @Override
    public int getGroupCount() {
        return ListDataHeader.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return ListHashMap.get(ListDataHeader.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return ListDataHeader.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return ListHashMap.get(ListDataHeader.get(i)).get(i1);  // i = group item , i1= child item
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String headerTitle = (String) getGroup(i);

        if(view == null){

            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_group,null);
        }

        TextView txtlistHeader = (TextView) view.findViewById(R.id.lvListHeader);
        txtlistHeader.setTypeface(null, Typeface.BOLD);
        txtlistHeader.setText(headerTitle);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {

        final  String childText = (String)getChild(i,i1);

        if(view==null){

            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item,null);

        }

        TextView txtListChild = (TextView) view.findViewById(R.id.lvListchild);
        txtListChild.setText(childText);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
