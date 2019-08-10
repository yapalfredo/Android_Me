package com.example.android.android_me.ui;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.android_me.R;
import com.example.android.android_me.data.AndroidImageAssets;

public class AndroidMeActivity extends AppCompatActivity {

    //This layout should contain a single ImageView


    //In this class, you'll need to implement an empty constructor and the onCreateView method
    // Soon, you'll update this image display code to show any image you want.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_me);

        //Only create new fragments when there is no previously saved state
        if (savedInstanceState == null) {
            // Create a new head, body, and leg BodyPartFragment
            BodyPartFragment headFragment = new BodyPartFragment();
            BodyPartFragment bodyFragment = new BodyPartFragment();
            BodyPartFragment legFragment = new BodyPartFragment();

            // Set the list of image id's for the head fragment and get the correct position in the from the intent
            int headIndex = getIntent().getIntExtra("headIndex",0);
            int bodyIndex = getIntent().getIntExtra("bodyIndex",0);
            int legIndex = getIntent().getIntExtra("legIndex", 0);

            headFragment.setmImageIds(AndroidImageAssets.getHeads());
            bodyFragment.setmImageIds(AndroidImageAssets.getBodies());
            legFragment.setmImageIds(AndroidImageAssets.getLegs());

            headFragment.setmListIndex(headIndex);
            bodyFragment.setmListIndex(bodyIndex);
            legFragment.setmListIndex(legIndex);

            // add the fragment to its container using a FragmentManager and a Transaction
            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .add(R.id.head_container, headFragment)
                    .add(R.id.body_container, bodyFragment)
                    .add(R.id.leg_container, legFragment)
                    .commit();

        }


    }
}
