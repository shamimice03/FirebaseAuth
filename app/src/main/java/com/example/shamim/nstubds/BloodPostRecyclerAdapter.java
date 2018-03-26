package com.example.shamim.nstubds;

import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

/**
 * Created by Shamim on 12-Mar-18.
 */

public class BloodPostRecyclerAdapter extends RecyclerView.Adapter<BloodPostRecyclerAdapter.ViewHolder> {

    public List<BloodPost> post_list;

    public BloodPostRecyclerAdapter(List<BloodPost> post_list){
        this.post_list = post_list;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.blog_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String des_data = post_list.get(position).getDescription();
        String user_name = post_list.get(position).getUserName();
        String blood_unit = post_list.get(position).getUnit();
        String blood_group = post_list.get(position).getBloodGroup();
        String phone_number = post_list.get(position).getPhoneNumber();
        String address_location = post_list.get(position).getLocation();

        //combaining first name and last name
        String first_name = post_list.get(position).getFirstName();
        String last_name = post_list.get(position).getLastName();
        String name = String.format("%s %s",first_name,last_name);

        //time stamp

        long milisec = post_list.get(position).getTimestamp().getTime();
        String dateFormat = DateFormat.format("MM/dd/yyyy",new Date(milisec)).toString();


        holder.setDesText(des_data);
        holder.setUserText(user_name);
        holder.setNameView(name);
        holder.setLocation(address_location);
        holder.setBloodgroup(blood_group);
        holder.setPhoneView(phone_number);
        holder.setUnit(blood_unit);
        holder.setDate(dateFormat);
    }

    @Override
    public int getItemCount() {
        return post_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private View mView;
        private TextView textView;


        public ViewHolder(View itemView) {
            super(itemView);
            mView =itemView;
        }

        public void setDesText(String destext){

            textView = mView.findViewById(R.id.post_desc);
            textView.setText(destext);
        }

       public void setUserText(String userText){

           textView = mView.findViewById(R.id.userName);
           textView.setText(userText);
       }
       public  void setNameView(String nameText){

           textView = mView.findViewById(R.id.NameOfNeed);
           textView.setText(nameText);
       }

       public void setPhoneView(String phn){

           textView = mView.findViewById(R.id.contactNumber);
           textView.setText(phn);
       }

       public  void setLocation(String loc){
           textView = mView.findViewById(R.id.addressLocation);
           textView.setText(loc);
       }
        public  void setUnit(String unit){
            textView = mView.findViewById(R.id.unitOfblood);
            textView.setText(unit);
        }

        public  void setBloodgroup(String group){
            textView = mView.findViewById(R.id.groupOfblood);
            textView.setText(group);
        }

        public  void setDate(String date){

            textView = mView.findViewById(R.id.postdate);
            textView.setText(date);
        }

    }
}
