package com.example.diplom_examle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import Data_classes.Comments;
import Data_classes.Document;
import Data_classes.FuncClass;
import Fragments.About_company;
import Fragments.BlankFragmentDownload;
import Fragments.Categories;
import Fragments.Com_image;
import Fragments.Contakts;
import Fragments.Fragment_about_theme;
import Fragments.Fragment_comments;
import Fragments.Fragment_documents_list;
import Fragments.Infocom_tech;
import Fragments.MainFragment;
import Fragments.Only_Text;
import Fragments.Prof_Stand;
import Fragments.ThemeFragment;
import Fragments.Video_Class;
import Interfaces.Data;
import Interfaces.OnFragmentInteractionListener;

public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnFragmentInteractionListener {
    public static final String Extra_News_Id = "news_intent_id";
    FragmentManager fm;
    Fragment fragment;
    BaseTask task;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        task = new BaseTask(this);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        fm = getSupportFragmentManager();
        fragment = fm.findFragmentById(R.id.frame_navigation);
        if (fragment == null) {
            fragment = new MainFragment();
            fm.beginTransaction()
                    .add(R.id.frame_navigation, fragment)
                    .commit();
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

   //Menu  with three det.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    //Menu  with three det.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        switch (item.getItemId()) {
            case R.id.nav_main: {
                fm = getSupportFragmentManager();

                fragment = new MainFragment();
                fm.beginTransaction()
                        .replace(R.id.frame_navigation, fragment)
                        .commit();
                break;
            }

            case R.id.nav_courses: {
                fm = getSupportFragmentManager();

                fragment = new Categories();
                fm.beginTransaction()
                        .replace(R.id.frame_navigation, fragment)
                        .commit();

                break;
            }
            case R.id.nav_about: {
                fm = getSupportFragmentManager();

                fragment = new About_company();
                fm.beginTransaction()
                        .replace(R.id.frame_navigation, fragment)
                        .commit();
                break;
            }
            case R.id.nav_contakts: {
                fm = getSupportFragmentManager();

                fragment = new Contakts();
                fm.beginTransaction()
                        .replace(R.id.frame_navigation, fragment)
                        .commit();
                break;
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMassageLang(Integer fragment_type, Integer parent_id) {
        Fragment mbad = new Fragment();
        Bundle bd = new Bundle();

        switch (fragment_type) {
            case (1): {
                switch (parent_id) {
                    case (21): {
                        Toast.makeText(this, "В данной версии не поддерживается платный контент", Toast.LENGTH_SHORT).show();
                        mbad = new Categories();
                        break;
                    }

                    case (71): {
                        bd.putInt("parent_id", parent_id);
                        mbad = new Prof_Stand();
                        mbad.setArguments(bd);
                        break;
                    }
                    case (86): {
                        bd.putInt("parent_id", parent_id);
                        mbad = new Infocom_tech();
                        mbad.setArguments(bd);
                        break;
                    }
                    default: {
                        bd.putInt("parent_id", parent_id);
                        mbad = new ThemeFragment();
                        mbad.setArguments(bd);
                    }
                }


                break;
            }
            case (2): {
                if(parent_id==89){
                    bd.putInt("parent_id", parent_id);
                    mbad = new Fragment_documents_list();
                    mbad.setArguments(bd);
                    break;
                }
                int id=FuncClass.getParent(this,parent_id);
                if((id==71&&parent_id!=74)||id==105||(id==86)){
                         if(parent_id==85) {
                        bd.putInt("categorie_id", parent_id);
                        mbad = new Fragment_comments();
                        mbad.setArguments(bd);
                    }
                    else {
                        bd.putInt("categorie_id", parent_id);
                        mbad = new Only_Text();
                        mbad.setArguments(bd);

                    }
                }
                else if(parent_id==74) {
                    bd.putInt("parent_id", parent_id);
                    mbad = new ThemeFragment();
                    mbad.setArguments(bd);
                }
                else {
                    bd.putInt("categorie_id", parent_id);
                    mbad = new Fragment_about_theme();
                    mbad.setArguments(bd);
                }

                break;
            }
            case (3): {
                bd.putInt("parent_id", parent_id);
                mbad = new Fragment_documents_list();
                mbad.setArguments(bd);
                break;
            }
            case (4): {

                mbad = new BlankFragmentDownload();
                break;
            }
            default:
                mbad = new MainFragment();
        }


        FragmentManager fragM = getSupportFragmentManager();
        fragM.beginTransaction().replace(R.id.frame_navigation, mbad, null)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void CallFragment(Data d) {
        Fragment mbad = new Fragment();
        Bundle bd = new Bundle();
          if(d instanceof Comments){
                  bd.putString("name", ((Comments) d).getText());
                  bd.putString("image_url", ((Comments) d).getUrl());
                  mbad = new Com_image();
                  mbad.setArguments(bd);

        }
        if (d instanceof Document) {
            String type = ((Document) d).getCont_type();
            bd.putInt("parent_id", ((Document) d).getId());
            bd.putDouble("price", ((Document) d).getPrice());
            bd.putString("deliver_time", ((Document) d).getPost());
            bd.putInt("deliver_type", ((Document) d).getDoc_type());
            bd.putString("name", ((Document) d).getDesc_text());
            bd.putString("title", ((Document) d).getName());
            bd.putString("code", ((Document) d).getCode());
            bd.putString("content", type);
            if (!type.equals("26")) {
                mbad = new BlankFragmentDownload();
                mbad.setArguments(bd);
            } else {
                mbad = new Video_Class();
                mbad.setArguments(bd);
            }

        }
        FragmentManager fragM = getSupportFragmentManager();
        fragM.beginTransaction().replace(R.id.frame_navigation, mbad, null)
                .addToBackStack(null)
                .commit();
    }
}


