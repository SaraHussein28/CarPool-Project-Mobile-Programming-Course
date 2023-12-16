package com.example.carpool_project.ui.routes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carpool_project.Character;
import com.example.carpool_project.CharacterAdapter;
import com.example.carpool_project.databinding.FragmentRoutesBinding;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RoutesFragment extends Fragment {

    private final String FACULTY_POINT = "ASU, Faculty of Engineering - Gate 3";
    private FragmentRoutesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RoutesViewModel routesViewModel = new ViewModelProvider(this).get(RoutesViewModel.class);

        binding = FragmentRoutesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        List<Character> characters = getCharacters();
        RecyclerView recyclerView = binding.recyclerView00;


        try {
            String source = getArguments().getString("source", "default");
            String destination = getArguments().getString("destination", "default");
            String time = getArguments().getString("time", "default");

            List<Character> filteredCharacters = filterResults(characters, source, destination, time);
            CharacterAdapter characterAdapter = new CharacterAdapter(filteredCharacters);
            recyclerView.setAdapter(characterAdapter);

            Toast.makeText(this.getContext(), "source" + source, Toast.LENGTH_SHORT).show();

        } catch (NullPointerException e) {
            CharacterAdapter characterAdapter = new CharacterAdapter(characters);
            recyclerView.setAdapter(characterAdapter);
        }

//        final TextView textView = binding.textGallery;
//        galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    private List<Character> filterResults(List<Character> characters, String source, String destination, String time) {
        List<Character> filteredCharacters = new ArrayList<>();

       // TODO: consider changing the data type for the time;
        LocalDateTime maxReservationTime;
        if (Objects.equals(source, FACULTY_POINT)){
            maxReservationTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(13, 0));
        }
        else {
            maxReservationTime =  LocalDateTime.of(LocalDate.now(), LocalTime.of(22, 0));
        }

        for (int i = 0; i < characters.size(); ++i) {
            //ُTODO: remember to add a switch to bypass the time validation.

//            if (Objects.equals(characters.get(i).getSourcePoint(), source) && Objects.equals(characters.get(i).getDestinationPoint(), destination)) {
//                filteredCharacters.add(characters.get(i));
//            }
        }
        return filteredCharacters;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private List<Character> getCharacters() {
        ArrayList<Character> characters = new ArrayList<>();
        characters.add(new Character("Abassia Square", "ASU, Faculty of Engineering - Gate 3", "7:30 am", "7:50 am"));
        characters.add(new Character("Nasr city", "ASU, Faculty of Engineering - Gate 3", "7:30 am", "8:00 am"));
        characters.add(new Character("Zamalek", "ASU, Faculty of Engineering - Gate 3", "7:30 am", "8:30 am"));
        characters.add(new Character("ASU, Faculty of Engineering - Gate 3", "Abassia Square", "5:30 pm", "5:50 pm"));
        characters.add(new Character("ASU, Faculty of Engineering - Gate 3", "Nasr city", "5:30 pm", "6:00 pm"));
        characters.add(new Character("ASU, Faculty of Engineering - Gate 3", "Zamalek", "5:30 pm", "6:30 pm"));
        characters.add(new Character("ASU, Faculty of Engineering - Gate 3", "Maadi", "5:30 pm", "6:30 pm"));

        return characters;
    }
}


