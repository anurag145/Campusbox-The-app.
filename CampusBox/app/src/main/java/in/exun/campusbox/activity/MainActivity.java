package in.exun.campusbox.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import in.exun.campusbox.R;
import in.exun.campusbox.fragments.Main.Creativity;
import in.exun.campusbox.fragments.Main.Events;
import in.exun.campusbox.fragments.Main.Home;
import in.exun.campusbox.fragments.Main.Profile;
import in.exun.campusbox.fragments.Main.Search;
import in.exun.campusbox.helper.AppConstants;
import in.exun.campusbox.helper.SessionManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    LinearLayout navHome, navEvent, navSearch, navCreativity, navMe;
    public SessionManager session;
    public Fragment fragment;
    private int currIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialise();
        final Intent intent = getIntent();
        final String action = intent.getAction();

        if (Intent.ACTION_VIEW.equals(action)) {
            String url = intent.getData().toString();
            if (url.contains("/singleEvent/")){
                String id = url.substring(url.indexOf("Event/") + 6);
                Log.d(TAG, "onCreate: Event "+ id) ;
                Intent i = new Intent(this, SingleEvent.class);
                i.putExtra(AppConstants.TAG_OBJ, id);
                startActivity(i);
            } else if (url.contains("/singleContent/")){
                String id = url.substring(url.indexOf("Content/") + 8);
                Log.d(TAG, "onCreate: Creativity "+ id) ;
                Intent i = new Intent(this, SinglePost.class);
                i.putExtra(AppConstants.TAG_OBJ, id);
                startActivity(i);
            }

        }
        setupBottomNav();

    }

    private void initialise() {

        session = new SessionManager(getApplicationContext());
        displayView(1, false);
    }

    private void setupBottomNav() {

        navHome = (LinearLayout) findViewById(R.id.nav_home);
        navEvent = (LinearLayout) findViewById(R.id.nav_event);
        navSearch = (LinearLayout) findViewById(R.id.nav_search);
        navCreativity = (LinearLayout) findViewById(R.id.nav_creativity);
        navMe = (LinearLayout) findViewById(R.id.nav_me);

        navHome.setOnClickListener(this);
        navEvent.setOnClickListener(this);
        navSearch.setOnClickListener(this);
        navCreativity.setOnClickListener(this);
        navMe.setOnClickListener(this);

    }

    public void displayView(int index, boolean allow) {
        fragment = new Fragment();

        if (index != currIndex) {

            switch (index) {
                case 0:
                    fragment = new Home();
                    setSelectedNav(index);
                    break;
                case 1:
                    fragment = new Events();
                    setSelectedNav(index);
                    break;
                case 2:
                    fragment = new Search();
                    setSelectedNav(index);
                    break;
                case 3:
                    fragment = new Creativity();
                    setSelectedNav(index);
                    break;
                case 4:
                    fragment = new Profile();
                    setSelectedNav(index);
                    break;
            }

            FragmentManager fragmentManager = getSupportFragmentManager();

            if (fragment != null) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.disallowAddToBackStack();
                if (allow)
                    if (index > currIndex)
                        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                    else
                        fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
                fragmentTransaction.replace(R.id.frame, fragment);
                fragmentTransaction.commit();
                invalidateOptionsMenu();
            }

            currIndex = index;
        }

    }

    public void signOut() {
        Log.d(TAG, "onTabSelected: Signing out");
        session.setLoginToken("-1");
        startActivity(new Intent(MainActivity.this, Login.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.nav_home:
                displayView(0, true);
                break;
            case R.id.nav_event:
                displayView(1, true);
                break;
            case R.id.nav_search:
                displayView(2, true);
                break;
            case R.id.nav_creativity:
                displayView(3, true);
                break;
            case R.id.nav_me:
                displayView(4, true);
                break;
        }
    }

    private void setSelectedNav(int index) {

        switch (currIndex) {
            case 0:
                ((TextView) findViewById(R.id.text_home)).setTextColor(Color.parseColor("#1F8ADA"));
                ((ImageView) findViewById(R.id.img_home)).setColorFilter(Color.parseColor("#1F8ADA"));
                break;
            case 1:
                ((TextView) findViewById(R.id.text_event)).setTextColor(Color.parseColor("#1F8ADA"));
                ((ImageView) findViewById(R.id.img_event)).setColorFilter(Color.parseColor("#1F8ADA"));
                break;
            case 2:
                ((TextView) findViewById(R.id.text_search)).setTextColor(Color.parseColor("#1F8ADA"));
                ((ImageView) findViewById(R.id.img_search)).setColorFilter(Color.parseColor("#1F8ADA"));
                break;
            case 3:
                ((TextView) findViewById(R.id.text_creative)).setTextColor(Color.parseColor("#1F8ADA"));
                ((ImageView) findViewById(R.id.img_creative)).setColorFilter(Color.parseColor("#1F8ADA"));
                break;
            case 4:
                ((TextView) findViewById(R.id.text_me)).setTextColor(Color.parseColor("#1F8ADA"));
                ((ImageView) findViewById(R.id.img_me)).setColorFilter(Color.parseColor("#1F8ADA"));
                break;
        }

        switch (index) {
            case 0:
                ((TextView) findViewById(R.id.text_home)).setTextColor(Color.parseColor("#06558F"));
                ((ImageView) findViewById(R.id.img_home)).setColorFilter(Color.parseColor("#06558F"));
                break;
            case 1:
                ((TextView) findViewById(R.id.text_event)).setTextColor(Color.parseColor("#06558F"));
                ((ImageView) findViewById(R.id.img_event)).setColorFilter(Color.parseColor("#06558F"));
                break;
            case 2:
                ((TextView) findViewById(R.id.text_search)).setTextColor(Color.parseColor("#06558F"));
                ((ImageView) findViewById(R.id.img_search)).setColorFilter(Color.parseColor("#06558F"));
                break;
            case 3:
                ((TextView) findViewById(R.id.text_creative)).setTextColor(Color.parseColor("#06558F"));
                ((ImageView) findViewById(R.id.img_creative)).setColorFilter(Color.parseColor("#06558F"));
                break;
            case 4:
                ((TextView) findViewById(R.id.text_me)).setTextColor(Color.parseColor("#06558F"));
                ((ImageView) findViewById(R.id.img_me)).setColorFilter(Color.parseColor("#06558F"));
                break;
        }
    }
}
