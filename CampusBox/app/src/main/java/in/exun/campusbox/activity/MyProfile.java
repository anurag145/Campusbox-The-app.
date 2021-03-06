package in.exun.campusbox.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.exun.campusbox.R;
import in.exun.campusbox.fragments.MyProfileFrags.CreativityCreated;
import in.exun.campusbox.fragments.MyProfileFrags.EventsCreated;
import in.exun.campusbox.fragments.MyProfileFrags.Followers;
import in.exun.campusbox.fragments.MyProfileFrags.Following;
import in.exun.campusbox.fragments.MyProfileFrags.Overview;
import in.exun.campusbox.fragments.MyProfileFrags.RSVP;
import in.exun.campusbox.fragments.MyProfileFrags.Recommended;
import in.exun.campusbox.helper.AppConstants;
import in.exun.campusbox.helper.AppController;
import in.exun.campusbox.helper.SessionManager;

public class MyProfile extends AppCompatActivity {
    private static final String TAG = "MyProfile";
    private TabLayout mTablayout;
    private ViewPager mViewPager;
    public SessionManager session;
    public String value;


    private ViewPagerAdapter mViewPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        session = new SessionManager(getApplicationContext());
         requestBuild();
    }
    public void requestBuild()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, AppConstants.URL_PROFILE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response!=null)
                        {    value=response;

                            mTablayout=(TabLayout)findViewById(R.id.myprofile_tablayout);
                            mViewPager=(ViewPager)findViewById(R.id.view_pager);
                            mViewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());
                            mViewPager.setOffscreenPageLimit(5);
                            mViewPager.setAdapter(mViewPagerAdapter);
                            mTablayout.setupWithViewPager(mViewPager);
                            mViewPagerAdapter.notifyDataSetChanged();
                            mViewPager.setCurrentItem(0);
                            mTablayout.setSelectedTabIndicatorColor(Color.WHITE);

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        ){
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                String token = "Bearer " + session.getLoginToken();
                params.put("Content-Type", "application/json");
                params.put("Authorization",token);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest, TAG);
    }
    class ViewPagerAdapter extends FragmentStatePagerAdapter
    {
        public ViewPagerAdapter(FragmentManager fm)
        {
           super (fm);
        }
        int count =7;
        @Override
        public Fragment getItem(int position) {
         try {
             Bundle ob = new Bundle();
             ob.putString("data",value);
             switch (position) {
                 case 0:
                     Overview overview= Overview.instance();
                     overview.setArguments(ob);
                     return overview;
                 case 1:
                     CreativityCreated creativityCreated=CreativityCreated.instance();
                     creativityCreated.setArguments(ob);
                     return creativityCreated;
                 case 2: Recommended recommended=Recommended.instance();
                     recommended.setArguments(ob);
                     return recommended;
                 case 3: RSVP rsvp=RSVP.instance();
                        rsvp.setArguments(ob);
                     return rsvp;
                 case 4: Following following=Following.instance();
                      following.setArguments(ob);
                     return following;
                 case 5:Followers followers=Followers.instance();
                     followers.setArguments(ob);
                     return followers;
                 case 6: EventsCreated eventsCreated= EventsCreated.instance();
                     eventsCreated.setArguments(ob);
                     return  eventsCreated;

                 default:
                     return null;
             }
         }catch (Exception e)
         {
             return null;
         }

        }

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    return "Overview";
                case 1:
                    return "Creative Content";
                case 2:
                    return "Events Created";
                case 3:
                    return  "Rsvp";
                case 4: return "Following";
                case 5:
                    return  "Followers";
                case 6:
                    return "Recommended";
            }
            return "";
        }
        @Override
        public int getCount() {
            return count;
        }
    }
}
