package com.example.android.android_me.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.example.android.android_me.R;
import com.example.android.android_me.data.AndroidImageAssets;

public class MainActivity extends AppCompatActivity implements MasterListFragment.OnImageClickListener {

    // Variables to store the values for the list index of the selected images
    // The default value be index = 0
    private int headIndex;
    private int bodyIndex;
    private int legIndex;

    // Create a variable to track whethere to  display a two-pane or single-pane layout
    // A single-pane display refers to phone screens, and two-pane to a larger tablet
    private boolean mTwoPane;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //If you are making a two-pane display, add new BodyPartFragments to
        //ALso for two-pane display get rid fot the next button in the master list
        //Determine if you're creating a two-pane or single-pane display
        if (findViewById(R.id.android_me_linear_layout) != null) {
            //this LinearLayout will only initially exist in the two-pane table case
            mTwoPane = true;

            //Getting rid of the Next button that appears on the phones for launching.....
            Button nextButton = findViewById(R.id.next_button);
            nextButton.setVisibility(View.GONE);

            // Change the GridView to space out the images more on tablet
            GridView gridView = findViewById(R.id.images_grid_view);
            gridView.setNumColumns(2);

            //Create new body part fragments
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

        } else{
            mTwoPane = false;
        }
    }

    // define the behavior for onItemSelected; create a Toast that displays the positioned clicked
    public void onImageSelected(int position) {
        // Create a Toast that displays the position that was clicked
        Toast.makeText(this, "Position clicked = " + position, Toast.LENGTH_SHORT).show();


        //Based on where a user has clicked, store the selected list index for the head, body, and
        //bodyPartNumber will = 0 for each the head fragment, 1 for the body, and 2 for the leg fragment
        //Dividing by 12 gives us these integer values because each list of images resources has size of 12
        int bodyPartNumber = position / 12;

        // Store the correct list index no matter where in the image list has been clicked
        // This ensures that the index will always be a value between 0 - 11
        int listIndex = position - 12 * bodyPartNumber;

        // Handle the two-pane case and replace existing fragments right when a new image is selected from the master list
        if (mTwoPane){
            // handle twoPane case

            BodyPartFragment newFragment = new BodyPartFragment();

            //set the currently displayed item for the correct body part fragment
            switch (bodyPartNumber){
                case 0:
                    // A head image has been clicked
                    // Give the correct image resources to the new fragment
                    newFragment.setmImageIds(AndroidImageAssets.getHeads());
                    newFragment.setmListIndex(listIndex);
                    //Replace old head fragment with a new one
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.head_container, newFragment)
                            .commit();
                    break;
                case 1:
                    // A body image has been clicked
                    // Give the correct image resources to the new fragment
                    newFragment.setmImageIds(AndroidImageAssets.getBodies());
                    newFragment.setmListIndex(listIndex);
                    //Replace old body fragment with a new one
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.body_container, newFragment)
                            .commit();
                    break;
                case 2:
                    // A leg image has been clicked
                    // Give the correct image resources to the new fragment
                    newFragment.setmImageIds(AndroidImageAssets.getLegs());
                    newFragment.setmListIndex(listIndex);
                    //Replace old leg fragment with a new one
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.leg_container, newFragment)
                            .commit();
                    break;
            }
        } else {
            // Set the currently displayed item for the correct body part fragment
            switch (bodyPartNumber) {
                case 0:
                    headIndex = listIndex;
                    break;
                case 1:
                    bodyIndex = listIndex;
                    break;
                case 2:
                    legIndex = listIndex;
                    break;
                default:
                    break;
            }
        }

        // Put this information in a Bundle and attach it to an Intent that will launch an AndroidMeActivity
        Bundle b = new Bundle();
        b.putInt("headIndex", headIndex);
        b.putInt("bodyIndex", bodyIndex);
        b.putInt("legIndex", legIndex);

        // Attach the Bundle to an intent
        final Intent intent = new Intent(this, AndroidMeActivity.class);
        intent.putExtras(b);

        // Get a reference to the "Next" button and launch the intent when this button is clicked
        // The "Next" button launches a new AndroidMeActivity
        Button nextButton = findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });
    }
}
