package thomasponzo.examenvoorbereiding.activities;

//Imports

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;

import br.com.joinersa.oooalertdialog.Animation;
import br.com.joinersa.oooalertdialog.OnClickListener;
import br.com.joinersa.oooalertdialog.OoOAlertDialog;
import nl.psdcompany.duonavigationdrawer.views.DuoDrawerLayout;
import nl.psdcompany.duonavigationdrawer.views.DuoMenuView;
import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle;
import thomasponzo.examenvoorbereiding.R;
import thomasponzo.examenvoorbereiding.common.Common;
import thomasponzo.examenvoorbereiding.fragments.CheckFragment;
import thomasponzo.examenvoorbereiding.fragments.HomeFragment;
import thomasponzo.examenvoorbereiding.fragments.ReservationFragment;
import thomasponzo.examenvoorbereiding.fragments.ReservationsFragment;

public class MainActivity extends AppCompatActivity implements DuoMenuView.OnMenuClickListener {

    //Global Declaration

     MenuAdapter mMenuAdapter;
     ViewHolder mViewHolder;
    Fragment mSelectedFragment;

    ImageView mLoggoutButton;
    FirebaseUser mUser;
    FirebaseAuth mAuth;

    private DatabaseReference mDatabase;

    private ArrayList<String> mTitles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //The orderview_layout the activity uses
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        //Get the navigationmenu_layout mOptions
        mTitles = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.menuUser)));

        //Check if the user has a userrole
        if (Common.mUserRole != null) {
            //Change the navigationmenu_layout items based on the userrole
            if (Common.mUserRole.equals("server")) {
                mTitles = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.menuServer)));
            } else if (Common.mUserRole.equals("admin")) {
                mTitles = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.menuServer)));
            } else if (Common.mUserRole.equals("cook")) {
                mTitles = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.menuCooks)));
            } else if (Common.mUserRole.equals("counter")) {
                mTitles = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.menuCounter)));
            }
        }

        mLoggoutButton = findViewById(R.id.logout_button);

        mSelectedFragment = null;

        // Initialize the views
        mViewHolder = new ViewHolder();

        // Handle toolbar actions
        handleToolbar();

        // Handle navigationmenu_layout actions
        handleMenu();

        // Handle drawer actions
        handleDrawer();

        // Show main fragment in container
        mMenuAdapter.setViewSelected(0, true);
        setTitle(mTitles.get(0));

        //Set the default fragment
        setDefaultFragment();

        //If the user clicks on the logout button
        mLoggoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new OoOAlertDialog.Builder(MainActivity.this)
                        .setCancelable(false)
                        .setTitle("Uitloggen")
                        .setMessage("Wilt u uitloggen?")
                        .setImage(R.drawable.companylogo)
                        .setAnimation(Animation.POP)
                        .setPositiveButton("JA", new OnClickListener() {
                            @Override
                            public void onClick() {
                                //Close the app
                                FirebaseAuth.getInstance().signOut();
                                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(i);
                                finishAffinity();
                            }
                        })
                        .setNegativeButton("NEE", new OnClickListener() {
                            @Override
                            public void onClick() {
                                //Close the dialog
                            }
                        })
                        .build();
            }
        });
    }

    @Override
    public void onBackPressed() {
        //If the user pressed on the back button create dialog
        new OoOAlertDialog.Builder(MainActivity.this)
                .setCancelable(false)
                .setTitle("Afsluiten")
                .setMessage("Wilt u afsluiten?")
                .setImage(R.drawable.companylogo)
                .setAnimation(Animation.POP)
                .setPositiveButton("JA", new OnClickListener() {
                    @Override
                    public void onClick() {
                        //Close the app
                        Intent intent = new Intent(MainActivity.this, QuitApplicationActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        getApplicationContext().startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("NEE", new OnClickListener() {
                    @Override
                    public void onClick() {
                        //Close the dialog
                    }
                })
                .build();
    }

    private void handleToolbar() {
        setSupportActionBar(mViewHolder.mToolbar);
    }

    //Handle the drawer states of open and close
    private void handleDrawer() {
        DuoDrawerToggle duoDrawerToggle = new DuoDrawerToggle(this,
                mViewHolder.mDuoDrawerLayout,
                mViewHolder.mToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        mViewHolder.mDuoDrawerLayout.setDrawerListener(duoDrawerToggle);
        duoDrawerToggle.syncState();

    }

    //Handle the navigationmenu_layout items and click listener
    private void handleMenu() {
        mMenuAdapter = new MenuAdapter(mTitles);

        mViewHolder.mDuoMenuView.setOnMenuClickListener(this);
        mViewHolder.mDuoMenuView.setAdapter(mMenuAdapter);
    }

    @Override
    public void onFooterClicked() {
    }

    @Override
    public void onHeaderClicked() {
    }


    //When the user clicks on navigationmenu_layout item
    @Override
    public void onOptionClicked(int position, Object objectClicked) {
        // Set the toolbar title
        setTitle(mTitles.get(position));



        // Set the right mOptions selected
        mMenuAdapter.setViewSelected(position, true);

        // Navigate to the right fragment
        switch (position) {

            case 0:
                //Check if the user has a userrole
                if (Common.mUserRole != null) {
                    if (Common.mUserRole.equals("server")) {
                        mSelectedFragment = ReservationsFragment.newInstance();
                    }

                    //If the user doesn't have a userrole
                } else {
                    mSelectedFragment = HomeFragment.newInstance();
                }
                break;

            case 1:
                //Check if the user has a userrole
                if (Common.mUserRole != null) {
                    if (Common.mUserRole.equals("server")) {
                        mSelectedFragment = CheckFragment.newInstance();
                    }

                    //If the user doesn't have a userrole
                } else {
                    mSelectedFragment = ReservationFragment.newInstance();
                }
                break;
        }
        //Open new fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, mSelectedFragment);
        transaction.commit();
        // Close the drawer
        mViewHolder.mDuoDrawerLayout.closeDrawer();
    }

    //The default fragment when the activity gets openned for the first time
    private void setDefaultFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        //Check if the user has a userrole
        if (Common.mUserRole != null) {
            if (Common.mUserRole.equals("server")) {
                transaction.replace(R.id.container, ReservationsFragment.newInstance());
            }
            //User doesn't have userrole
        } else {
            transaction.replace(R.id.container, HomeFragment.newInstance());

        }
        transaction.commit();
    }

    //The viewholer of the menudrawer
    private class ViewHolder {
        private DuoDrawerLayout mDuoDrawerLayout;
        private DuoMenuView mDuoMenuView;
        private Toolbar mToolbar;

        ViewHolder() {
            mDuoDrawerLayout = (DuoDrawerLayout) findViewById(R.id.drawer);
            mDuoMenuView = (DuoMenuView) mDuoDrawerLayout.getMenuView();
            mToolbar = (Toolbar) findViewById(R.id.toolbar);
        }
    }
}
