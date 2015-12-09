package com.example.alyezz.beygollak;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

import com.example.alyezz.util.ApiRouter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class Streets extends Fragment {


    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.activity_streets,container,false);
        return v;
    }
    @Override
    public void onStart() {
        super.onStart();

        // get the listview
        expListView = (ExpandableListView) getView().findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        // Listview Group click listener
        expListView.setOnGroupClickListener(new OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview Group expanded listener
//        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {
//
//            @Override
//            public void onGroupExpand(int groupPosition) {
//                Toast.makeText(getActivity().getApplicationContext(),
//                        listDataHeader.get(groupPosition) + " Expanded",
//                        Toast.LENGTH_SHORT).show();
//            }
//        });

        // Listview Group collasped listener
//        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
//
//            @Override
//            public void onGroupCollapse(int groupPosition) {
//                Toast.makeText(getActivity().getApplicationContext(),
//                        listDataHeader.get(groupPosition) + " Collapsed",
//                        Toast.LENGTH_SHORT).show();
//
//            }
//        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                Intent a = new Intent(getActivity().getApplicationContext(), Street.class);
                a.putExtra("name",  listDataHeader.get(groupPosition) + " - " + listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition));
                startActivity(a);
                return false;
            }
        });
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("6th October");
        listDataHeader.add("Maadi");
        listDataHeader.add("Mohandessin");
        listDataHeader.add("Nasr City");
        listDataHeader.add("Zamalek");

        // Adding child data
        List<String> october = new ArrayList<String>();
        october.add("Hossary");
        october.add("Mehwar");
        october.add("Sahrawy");
        october.add("Zayed");
        october.add("Wahat");

        List<String> maadi = new ArrayList<String>();
        maadi.add("Kornich");
        maadi.add("Nasr St");
        maadi.add("Share3 9");
        maadi.add("Zahraa");

        List<String> mohandessin = new ArrayList<String>();
        mohandessin.add("Gam3et El Dewal");
        mohandessin.add("Mohy El Deen");
        mohandessin.add("Share3 El Batal");
        mohandessin.add("Share3 3orabi");
        mohandessin.add("Share3 Shehab");
        mohandessin.add("Share3 Lebnan");

        List<String> nasr_city = new ArrayList<String>();
        nasr_city.add("3abas El 3a2ad");
        nasr_city.add("Ahmed fakhry");
        nasr_city.add("Makram 3ebeid");
        nasr_city.add("Nasr St");
        nasr_city.add("Share3 El Tayaran");

        List<String> zamalek = new ArrayList<String>();
        zamalek.add("26 July");
        zamalek.add("Kobry 15 Mayo");
        zamalek.add("Nady El Gezira");
        zamalek.add("Share3 Om Kalsoom");
        zamalek.add("Share3 El Brazil");

        listDataChild.put(listDataHeader.get(0), october); // Header, Child data
        listDataChild.put(listDataHeader.get(1), maadi);
        listDataChild.put(listDataHeader.get(2), mohandessin);
        listDataChild.put(listDataHeader.get(3), nasr_city);
        listDataChild.put(listDataHeader.get(4), zamalek);
    }


}


