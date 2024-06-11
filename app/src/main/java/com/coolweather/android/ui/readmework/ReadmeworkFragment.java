package com.coolweather.android.ui.readmework;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.coolweather.android.R;

public class ReadmeworkFragment extends Fragment {

    private ReadmeworkViewModel readmeworkViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        readmeworkViewModel = ViewModelProviders.of(this).get(ReadmeworkViewModel.class);

        View root = inflater.inflate(R.layout.readmework, container, false);
        final TextView textView = root.findViewById(R.id.text_readMeWork);
        final TextView textView2 = root.findViewById(R.id.readme);

        readmeworkViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        readmeworkViewModel.getDescription().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView2.setText(s);
            }
        });

        return root;
    }
}
