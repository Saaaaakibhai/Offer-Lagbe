package com.example.offerlagbe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Toast;

import com.example.offerlagbe.Fragment.AddFragment;
import com.example.offerlagbe.Fragment.HomeFragment;
import com.example.offerlagbe.Fragment.NotificationFragment;
import com.example.offerlagbe.Fragment.ProfileFragment;
import com.example.offerlagbe.Fragment.SearchFragment;
import com.example.offerlagbe.databinding.ActivityMainBinding;
import com.iammert.library.readablebottombar.ReadableBottomBar;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container,new HomeFragment());
        transaction.commit();

        binding.readableBottomBar.setOnItemSelectListener(new ReadableBottomBar.ItemSelectListener() {
            @Override
            public void onItemSelected(int i) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                switch (i){
                    case 0:
                        Toast.makeText(MainActivity.this, "Home Selected", Toast.LENGTH_SHORT).show();
                        transaction.replace(R.id.container,new HomeFragment());
                        break;
                    case 1:
                        transaction.replace(R.id.container,new NotificationFragment());
                        break;
                    case 2:
                        transaction.replace(R.id.container,new AddFragment());
                        break;
                    case 3:
                        transaction.replace(R.id.container,new SearchFragment());
                        break;
                    case 4:
                        Toast.makeText(MainActivity.this, "Profile Selected", Toast.LENGTH_SHORT).show();
                        transaction.replace(R.id.container,new ProfileFragment());
                        break;
                }
                transaction.commit();
            }
        });
    }
}