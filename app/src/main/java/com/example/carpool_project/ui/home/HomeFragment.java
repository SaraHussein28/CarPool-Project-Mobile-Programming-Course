package com.example.carpool_project.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.carpool_project.R;
import com.example.carpool_project.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    String[] datesList = {"Now", "tomorrow", "Dec 3", "Dec 4", "Dec 5"};
    String[] districtNames = {"Maadi", "Nasr City", "Zamalek", "Mohandseen", "Abassia Square"};
    private FragmentHomeBinding binding;
    AutoCompleteTextView datesAutoCompleteTextView, sourceAutoCompleteTextView, destinationAutoCompleteTextView;
    ArrayAdapter<String> datesArrayAdapter, districtArrayAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        datesAutoCompleteTextView = binding.autoCompleteTextView00;
        sourceAutoCompleteTextView = binding.autoCompleteTextView01;
        destinationAutoCompleteTextView = binding.autoCompleteTextView02;

        datesArrayAdapter = new ArrayAdapter<String>(this.getActivity(), R.layout.list_item, datesList);
        datesAutoCompleteTextView.setAdapter(datesArrayAdapter);

        districtArrayAdapter = new ArrayAdapter<String>(this.getActivity(), R.layout.list_item, districtNames);
        sourceAutoCompleteTextView.setAdapter(districtArrayAdapter);
        destinationAutoCompleteTextView.setAdapter(districtArrayAdapter);

        datesAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(getActivity(), "Item "+ item, Toast.LENGTH_SHORT).show();
            }
        });

        sourceAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(getActivity(), "Item "+ item, Toast.LENGTH_SHORT).show();
            }
        });

        destinationAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(getActivity(), "Item "+ item, Toast.LENGTH_SHORT).show();
            }
        });
//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}