package com.example.insightly;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.insightly.databinding.ActivityHomePageBinding;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.material.navigation.NavigationView;

public class HomePage extends AppCompatActivity {
    private ActivityHomePageBinding binding;
    private GoogleSignInClient googleSignInClient;
    private DrawerLayout drawerLayout;
    private ImageView profile, notifications;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        profile = findViewById(R.id.iv_profile);
        notifications = findViewById(R.id.iv_notifications);

//        // Set up the toolbar
//        setSupportActionBar(binding.toolbar);

        // Initialize DrawerLayout
        drawerLayout = binding.drawerLayout;

//        // Set up ActionBarDrawerToggle
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this,
//                drawerLayout,
//                binding.toolbar,
//                R.string.open_drawer,
//                R.string.close_drawer
//        );
//        drawerLayout.addDrawerListener(toggle);
//        toggle.syncState();

        // Set up NavigationView
        binding.navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if(id == R.id.nav_logout) {
                    signOut();
                }

//                switch (id) {
//                    case R.id.nav_home:
//                        replaceFragment(new HomeFragment());
//                        break;
//                    case R.id.nav_profile:
//                        replaceFragment(new SearchFragment());
//                        break;
//                    case R.id.nav_settings:
//                        replaceFragment(new Option2Fragment());
//                        break;
//                    case R.id.nav_logout:
//                        replaceFragment(new Option3Fragment());
//                        break;
//                    case R.id.nav_logout:
//                        signOut();
//                        break;
//                }

                // Close the drawer after selecting an item
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.openDrawer(GravityCompat.START); // Open drawer
                } else {
                    drawerLayout.closeDrawer(GravityCompat.START); // Close drawer if already open
                }
            }
        });

        // Default fragment
        replaceFragment(new HomeFragment());

        // Set up BottomNavigationView
        binding.bottomNavigationMenu.setOnItemSelectedListener(menuItem -> {
            int itemId = menuItem.getItemId();

            if (itemId == R.id.home_item) {
                replaceFragment(new HomeFragment());
            } else if (itemId == R.id.search_item) {
                replaceFragment(new SearchFragment());
            } else if (itemId == R.id.option2_item) {
                replaceFragment(new Option2Fragment());
            } else if (itemId == R.id.option3_item) {
                replaceFragment(new Option3Fragment());
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    private void signOut() {
        googleSignInClient.signOut().addOnCompleteListener(task -> {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
