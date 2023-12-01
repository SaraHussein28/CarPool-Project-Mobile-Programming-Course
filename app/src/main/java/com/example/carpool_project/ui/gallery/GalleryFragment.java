package com.example.carpool_project.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carpool_project.Character;
import com.example.carpool_project.CharacterAdapter;
import com.example.carpool_project.R;
import com.example.carpool_project.databinding.FragmentGalleryBinding;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        List<Character> characters= getCharacters();

        CharacterAdapter characterAdapter = new CharacterAdapter(characters);
        RecyclerView recyclerView = binding.recyclerView00;
        recyclerView.setAdapter(characterAdapter);

        String value = getArguments().getString("source");
        Toast.makeText(this.getContext(), "source" + value, Toast.LENGTH_SHORT).show();
//        final TextView textView = binding.textGallery;
//        galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private List<Character> getCharacters(){
        ArrayList<Character> characters= new ArrayList<>();
        characters.add(new Character("Abassia Square", "ASU, Faculty of Engineering - Gate 3", "7:30 am", "7:50 am"));
        characters.add(new Character("Nasr city", "ASU, Faculty of Engineering - Gate 3", "7:30 am", "8:00 am"));
        characters.add(new Character("Zamalek", "ASU, Faculty of Engineering - Gate 3", "7:30 am", "8:30 am"));
        characters.add(new Character( "ASU, Faculty of Engineering - Gate 3", "Abassia Square","5:30 pm", "5:50 pm"));
        characters.add(new Character( "ASU, Faculty of Engineering - Gate 3", "Nasr city", "5:30 pm", "6:00 pm"));
        characters.add(new Character( "ASU, Faculty of Engineering - Gate 3", "Zamalek","5:30 pm", "6:30 pm"));
        characters.add(new Character( "ASU, Faculty of Engineering - Gate 3", "Maadi","5:30 pm", "6:30 pm"));

        return characters;
    }
}


