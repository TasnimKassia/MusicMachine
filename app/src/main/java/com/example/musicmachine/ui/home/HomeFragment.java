package com.example.musicmachine.ui.home;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.musicmachine.R;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment implements View.OnClickListener
{

    private HomeViewModel homeViewModel;
    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1001;
    private ImageButton imageButtonSpeak;
    private TextView textViewResult;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });


        imageButtonSpeak = view.findViewById(R.id.imageButtonSpeak);
        textViewResult = view.findViewById(R.id.textViewResult);
        checkVoiceRecognition();

        imageButtonSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

                intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getClass().getPackage().getName());

                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);

                startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);

            }
        });

        return view;
    }

    

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButtonSpeak:

                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

                intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getClass().getPackage().getName());

                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);

                startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);

                break;
        }
    }

    public void checkVoiceRecognition()
    {
        // Check if voice recognition is present
        PackageManager pm = getActivity().getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(
                RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() == 0)
        {
            imageButtonSpeak.setEnabled(false);
            Toast.makeText(this.getContext(), "Voice recognizer not present", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE)

            // If Voice recognition is successful then it returns RESULT_OK
            if (resultCode == RESULT_OK)
            {

                ArrayList<String> textMatchList = data
                        .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                if (!textMatchList.isEmpty())
                {
                    String searchQuery = textMatchList.get(0);

                    textViewResult.setText(searchQuery);

                }
                // Result code for various error.
            }
        super.onActivityResult(requestCode, resultCode, data);
    }

}