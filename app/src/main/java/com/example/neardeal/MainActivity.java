package com.example.neardeal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.neardeal.fragment.FragHome;
import com.example.neardeal.fragment.FragProductContainer;
import com.example.neardeal.preference.PrefStore;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    PrefStore prefStore;

    Toolbar toolbar;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;

    TextView tvUserNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefStore = new PrefStore(this);

        toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.app_name);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frag_container, new FragHome())
                    .commit();
        }


        //setup navigationview
        drawerLayout = findViewById(R.id.drawer_main);
        navigationView = findViewById(R.id.navigation_main);

        View view = navigationView.getHeaderView(0);

        tvUserNav = view.findViewById(R.id.tv_user_nav);
        tvUserNav.setText(prefStore.getUsername());

        drawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout,
                R.string.open_drawer,
                R.string.close_drawer);

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_add_user:
                Intent intentAddUser = new Intent(this, AddUser.class);
                startActivity(intentAddUser);
                break;
            case R.id.action_map:
                Intent intentMap = new Intent(this, MapActivity.class);
                startActivity(intentMap);
                break;
            case R.id.action_logout:
                Intent intentLogin = new Intent(this, LoginActivity.class);
                startActivity(intentLogin);
                prefStore.deleteUser();
                finish();
                break;
        }

        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_store:
                getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, new FragHome()).commit();
                item.setChecked(true);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_product:
                getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, new FragProductContainer()).commit();
                item.setChecked(true);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
        }

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

//        Toast.makeText(this, "Main onResume", Toast.LENGTH_SHORT).show();
    }
}
