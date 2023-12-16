package com.example.carpool_project.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.carpool_project.R;
import com.example.carpool_project.databinding.FragmentHomeBinding;
import com.example.carpool_project.ui.routes.RoutesFragment;

public class HomeFragment extends Fragment {

    String[] datesList = {"Now", "tomorrow", "Dec 3", "Dec 4", "Dec 5"};
    String[] districtNames = {"Maadi", "Nasr City", "Zamalek", "Mohandseen", "Abassia Square", "ASU, Faculty of Engineering - Gate 3"};
    private FragmentHomeBinding binding;
    AutoCompleteTextView datesAutoCompleteTextView, sourceAutoCompleteTextView, destinationAutoCompleteTextView;
    ArrayAdapter<String> datesArrayAdapter, districtArrayAdapter;
    ImageView imageView;
    Button submitButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        findLayoutElements(binding);
        setClickListeners();
        createArrayAdapters();
        setArrayAdapters();


//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    private void setArrayAdapters() {

        datesAutoCompleteTextView.setAdapter(datesArrayAdapter);
        sourceAutoCompleteTextView.setAdapter(districtArrayAdapter);
        destinationAutoCompleteTextView.setAdapter(districtArrayAdapter);

    }

    private void createArrayAdapters() {
        datesArrayAdapter = new ArrayAdapter<String>(this.getActivity(), R.layout.list_item, datesList);
        districtArrayAdapter = new ArrayAdapter<String>(this.getActivity(), R.layout.list_item, districtNames);

    }

    private void setClickListeners() {
        datesAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(getActivity(), "Item " + item, Toast.LENGTH_SHORT).show();
            }
        });

        sourceAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(getActivity(), "Item " + item, Toast.LENGTH_SHORT).show();
            }
        });
        destinationAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(getActivity(), "Item " + item, Toast.LENGTH_SHORT).show();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: add the logic to swap the selected values in the two drop down lists.
//                String temp = sourceAutoCompleteTextView.getText().toString();
//                sourceAutoCompleteTextView.setText(destinationAutoCompleteTextView.getText());
//                destinationAutoCompleteTextView.setText(temp);
//                setArrayAdapters();
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragM = getParentFragmentManager();
                FragmentTransaction fragT = fragM.beginTransaction();
                RoutesFragment routesFragment = new RoutesFragment();
                Bundle args = new Bundle();
                //TODO: consider other data passing options.
                args.putString("source", sourceAutoCompleteTextView.getText().toString());
                args.putString("destination", destinationAutoCompleteTextView.getText().toString());
                args.putString("time", datesAutoCompleteTextView.getText().toString());
                routesFragment.setArguments(args);
                fragT.replace(R.id.nav_host_fragment_content_first, routesFragment);
                fragT.commit();
            }
        });

    }

    private void findLayoutElements(FragmentHomeBinding binding) {
        datesAutoCompleteTextView = binding.autoCompleteTextView00;
        sourceAutoCompleteTextView = binding.autoCompleteTextView01;
        destinationAutoCompleteTextView = binding.autoCompleteTextView02;
        imageView = binding.imageView00;
        submitButton = binding.button00;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}