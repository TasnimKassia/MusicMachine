package com.example.musicmachine.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.musicmachine.R;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

ListView listViewHistory;
    ArrayList<String> arrayList;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mHistoryRef = mRootRef.child("history");
    ArrayAdapter arrayAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

     arrayList = new ArrayList<>();

        dashboardViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });

        listViewHistory = view.findViewById(R.id.listViewHistory);

//        ArrayList<String> arrayList = new ArrayList<>();
//        arrayList.add("Song name1");
//        arrayList.add("Song name1");
//        arrayList.add("Song name1");
//        arrayList.add("Song name1");
//        arrayList.add("Song name1");
//        arrayList.add("Song name1");
//        ArrayAdapter arrayAdapter = new ArrayAdapter(view.getContext(),android.R.layout.simple_list_item_1, arrayList);
//listViewHistory.setAdapter(arrayAdapter);
         return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        mHistoryRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                arrayList.clear();
                HashMap<String, Object> hashMap = new HashMap<>();
                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                    hashMap.put(childSnapshot.getKey(), childSnapshot.getValue());
                    arrayList.add(childSnapshot.getValue().toString());
                }
                Collections.reverse(arrayList);


//                ArrayList<String> arrayList = new ArrayList<>();
//                arrayList.add("Song name1");
//                arrayList.add("Song name1");
//                arrayList.add("Song name1");
//                arrayList.add("Song name1");
//                arrayList.add("Song name1");
//                arrayList.add("Song name1");
                arrayAdapter = new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1, arrayList);
                listViewHistory.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });


    }

}