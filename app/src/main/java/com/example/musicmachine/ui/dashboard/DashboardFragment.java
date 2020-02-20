package com.example.musicmachine.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.musicmachine.R;
import com.example.musicmachine.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


public class DashboardFragment extends Fragment  implements AdapterView.OnItemClickListener
{

    private DashboardViewModel dashboardViewModel;

ListView listViewHistory;
    ArrayList<String> arrayList;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mHistoryRef = mRootRef.child("history");
    ArrayAdapter arrayAdapter;
    HashMap<String, Object> hashMap;
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

//        listViewHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//               // String value = (String)new ArrayList<>(hashMap.values()).get(position);
//
//                Toast.makeText(getContext(), "song name: ", Toast.LENGTH_SHORT);
//            }
//        });
        listViewHistory.setClickable(true);
        listViewHistory.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
        Toast.makeText(getActivity(), "Song Name: " + arrayList.get(position), Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(Intent.ACTION_SEARCH);
        intent.setPackage("com.google.android.youtube");
        intent.putExtra("query",  arrayList.get(position));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
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
                hashMap = new HashMap<>();
                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                    hashMap.put(childSnapshot.getKey(), childSnapshot.getValue());
                    arrayList.add(childSnapshot.getValue().toString());
                }
                Collections.reverse(arrayList);


                arrayAdapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1, arrayList);
                listViewHistory.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });


    }

}