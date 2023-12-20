package com.example.carpool_project.ui.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.carpool_project.WordViewModel;
import com.example.carpool_project.databinding.FragmentProfileBinding;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {
    private WordViewModel mWordViewModel;
    FirebaseAuth auth;

    private FragmentProfileBinding binding;
    TextView nameTextView, ageTextView, phoneTextView, emailTextView;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        auth = FirebaseAuth.getInstance();
        ProfileViewModel profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        findLayoutElements(binding);

        readUserData(auth.getCurrentUser().getEmail());
//        final TextView textView = binding.textSlideshow;
        //TODO: consider adding a view model for each fragment/activity.
//        slideshowViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    private void readUserData(String email) {

        mWordViewModel = new ViewModelProvider(this).get(WordViewModel.class);
        mWordViewModel.getUserData(email).observe(getViewLifecycleOwner(), words -> {
            if (words == null) {
                Log.d("null words - profile ", "null words - reg");
            }
            if (words != null){
                Log.d("word info  - profile ", words.getEmail());

                 nameTextView.setText(words.getName());
                // TODO: remember to handle this. otherwise, replace it with the uesrname.
                ageTextView.setText("Placeholder");
                phoneTextView.setText(words.getPhoneNumber());
                emailTextView.setText(words.getEmail());
            }
        });

    }

    private void findLayoutElements(FragmentProfileBinding binding) {
        nameTextView = binding.textView02;
        ageTextView = binding.textView04;
        phoneTextView = binding.textView06;
        emailTextView = binding.textView08;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}