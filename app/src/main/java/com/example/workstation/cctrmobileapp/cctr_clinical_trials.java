package com.example.workstation.cctrmobileapp;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class cctr_clinical_trials extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks, cctrct_search.OnFragmentInteractionListener, cctrct_search_results.OnFragmentInteractionListener,cctrct_favorites.OnFragmentInteractionListener, cctrct_contactus.OnFragmentInteractionListener, cctrct_recent.OnFragmentInteractionListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cctr_clinical_trials);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        setFragment(PlaceholderFragment.newInstance(0));

    }

    public void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (position) {
            case 0: fragmentManager.beginTransaction()
                    .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                    .addToBackStack(null)
                    .commit(); break;
            case 1: fragmentManager.beginTransaction()
                    .replace(R.id.container, cctrct_search.newInstance("test1", "test2"))
                    .addToBackStack(null)
                    .commit(); break;
            case 2: fragmentManager.beginTransaction()
                    .replace(R.id.container, cctrct_favorites.newInstance("test1", "test2"))
                    .addToBackStack(null)
                    .commit(); break;
            case 3: fragmentManager.beginTransaction()
                    .replace(R.id.container, cctrct_recent.newInstance("test1", "test2"))
                    .addToBackStack(null)
                    .commit(); break;
            case 4: fragmentManager.beginTransaction()
                    .replace(R.id.container, cctrct_contactus.newInstance("test1", "test2"))
                    .addToBackStack(null)
                    .commit(); break;
        }

    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.cctr_ct_home);
                break;
            case 2:
                mTitle = getString(R.string.cctr_ct_search);
                break;
            case 3:
                mTitle = getString(R.string.cctr_ct_favorite);
                break;
            case 4:
                mTitle = getString(R.string.cctr_ct_recent);
                break;
            case 5:
                mTitle = getString(R.string.cctr_ct_contactus);
                break;

        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.cctr_clinical_trials, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_cctr_clinical_trials, container, false);


            Button cctrct_searchButton;

            Button cctrct_favoriteButton;

            Button cctrct_recentButton;

            Button cctrct_contactUsButton;

            cctrct_searchButton = (Button)rootView.findViewById(R.id.cctr_search);

            cctrct_favoriteButton = (Button) rootView.findViewById(R.id.cctr_favorites);

            cctrct_recentButton = (Button) rootView.findViewById(R.id.cctr_recent);

            cctrct_contactUsButton = (Button) rootView.findViewById(R.id.cctr_contactus);

            cctrct_searchButton.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Fragment cctrct_search = new cctrct_search();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                    transaction.replace(R.id.container, cctrct_search);
                    transaction.addToBackStack(null);

                    transaction.commit();
                }
            });

            cctrct_favoriteButton.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Fragment cctrct_favorite = new cctrct_favorites();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                    transaction.replace(R.id.container, cctrct_favorite);
                    transaction.addToBackStack(null);

                    transaction.commit();
                }
            });

            cctrct_recentButton.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Fragment cctrct_recent = new cctrct_recent();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                    transaction.replace(R.id.container, cctrct_recent);
                    transaction.addToBackStack(null);

                    transaction.commit();
                }
            });

            cctrct_contactUsButton.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Fragment cctrct_contactUs = new cctrct_contactus();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                    transaction.replace(R.id.container, cctrct_contactUs);
                    transaction.addToBackStack(null);

                    transaction.commit();
                }
            });

            return rootView;

        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((cctr_clinical_trials) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }



    }

}
