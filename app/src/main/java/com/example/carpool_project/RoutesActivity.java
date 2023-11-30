package com.example.carpool_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class RoutesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes);
        List <Character> characters= getCharacters();

        CharacterAdapter characterAdapter = new CharacterAdapter(characters);
        RecyclerView recyclerView = findViewById(R.id.recyclerView00);
        recyclerView.setAdapter(characterAdapter);

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


