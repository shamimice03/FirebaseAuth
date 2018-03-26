package com.example.shamim.nstubds;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class TipsForDonors extends AppCompatActivity {

    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<String> > listHash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips_for_donors);


        Toolbar toolbar = (Toolbar) findViewById(R.id.tipstoolbar);
        toolbar.setTitle("Tips For Blood Donors");
        toolbar.setTitleTextColor(getResources().getColor(R.color.greyLight));
        setSupportActionBar(toolbar);

        listView = (ExpandableListView) findViewById(R.id.IvExp);
        initData();
        listAdapter = new ExpandableListAdapter(this,listDataHeader,listHash);
        listView.setAdapter(listAdapter);


    }

    private void initData() {

        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();

        listDataHeader.add("Eligibility Requirements");
        listDataHeader.add("Before Your Donation");
        listDataHeader.add("During Your Donation");
        listDataHeader.add("After Your Donation");


        List<String>  Eligibility_Requirements= new ArrayList<>();
        List<String> Before_Your_Donation = new ArrayList<>();
        List<String> During_Your_Donation = new ArrayList<>();
        List<String> After_Your_Donation = new ArrayList<>();


        //Content Adding



        //Eligibility Requirements


        Eligibility_Requirements.add("1. Be in good health and feeling well\n\n" +
                "2. Be at least 18 years old\n\n" +
                "3. Weigh at least 110 pounds\n\n" +
                "4. Not take any medications containing aspirin for three days (72 hours) or ibuprofen for 24 hours prior to donation");


        //Before Your Donation


        Before_Your_Donation.add("1. In the days before your donation, eat healthy, iron-rich foods such as spinach, red meat, fish, poultry, beans, iron-fortified cereals and raisins. This will help maintain a healthy iron level. The number one reason for deferrals (especially women) is anemia\n\n" +
                "2. Get a good night’s sleep\n\n" +
                "3. At least 3 hours before donating, eat a balanced meal and avoid fatty foods, such as hamburgers, fries, or ice cream\n\n" +
                "4. Drink an extra 16 oz. of water and fluids before the donation; you can be deferred for dehydration\n\n" +
                "5. Remember that your system must be free of aspirin for three days (72 hours) or ibuprofen for 24 hours prior to donation");

        //During Your Donation

        During_Your_Donation.add("1. Wear clothing with short sleeves or sleeves that can be raised above the elbow\n\n" +
                "2. If you have a preference of arm or vein that you like the phlebotomist to use for your donation, let them know\n\n" +
                "3. Relax, listen to music, talk to others, or simply just catch up on some reading on our comfortable donor lounge chairs designed specifically for apheresis donations, which are equipped for our donors to surf the internet or watch movies");



     //After Your Donation
        After_Your_Donation.add("1. Drink plenty of fluids over the next 24-48 hours to replenish any fluids you lost during donation\n\n" +
                "2. Avoid strenuous physical activity or heavy lifting for about 24 hours after donation\n\n" +
                "3. If you feel light headed, lie down, preferably with feet elevated, until the feeling passes\n\n" +
                "5. Enjoy your day and know that you have made a positive difference!");


        //------//

        listHash.put(listDataHeader.get(0),Eligibility_Requirements);
        listHash.put(listDataHeader.get(1),Before_Your_Donation);
        listHash.put(listDataHeader.get(2),During_Your_Donation);
        listHash.put(listDataHeader.get(3),After_Your_Donation);
    }
}
