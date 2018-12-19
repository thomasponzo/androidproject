package thomasponzo.examenvoorbereiding.activities;

//Imports

import android.animation.Animator;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


import com.airbnb.lottie.LottieAnimationView;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.List;

import br.com.joinersa.oooalertdialog.Animation;
import br.com.joinersa.oooalertdialog.OnClickListener;
import br.com.joinersa.oooalertdialog.OoOAlertDialog;
import thomasponzo.examenvoorbereiding.Model.UsertypeModel;
import thomasponzo.examenvoorbereiding.R;
import thomasponzo.examenvoorbereiding.common.Common;

public class LoginActivity extends AppCompatActivity {

    //Global Declaration

     FirebaseAuth mAuth;
    FirebaseUser mUser;

    boolean mUserAnimationFinished;
    public LottieAnimationView mAnimationUser, mAnimationChecked;

    DatabaseReference mDatabaseReferencetest;
    DatabaseReference mDatabaseReference;

    List<AuthUI.IdpConfig> mProviders;

    int RC_SIGN_IN = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //The lay the activity uses
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        mDatabaseReferencetest = FirebaseDatabase.getInstance().getReference().child("users");
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        //Setting the default values for the user animation
        mAnimationUser = findViewById(R.id.login_avatar);
        mAnimationUser.setMaxProgress(0.5f);
        mAnimationUser.useHardwareAcceleration();
        mAnimationUser.setVisibility(View.GONE);
        mUserAnimationFinished = false;

        //Setting the default values for the checked animation
        mAnimationChecked = findViewById(R.id.login_finisched);
        mAnimationChecked.useHardwareAcceleration();
        mAnimationChecked.setVisibility(View.GONE);

        //Create login screen
        createLogin();
    }

    @Override
    protected void onPause() {
        super.onPause();

        //Pause the animations
        if (mAnimationUser.isAnimating()) {
            //If animation User is Animating
            mAnimationUser.pauseAnimation();
        }
        if (mAnimationChecked.isAnimating()) {
            //If animation Checked is Animating
            mAnimationChecked.pauseAnimation();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Resume the animations
        if (mUserAnimationFinished == false) {
            //if animation checked is not animating
            mAnimationUser.resumeAnimation();
        }

        if (mUserAnimationFinished == true) {
            //if animation user is not animating
            mAnimationChecked.resumeAnimation();
        }
    }

    @Override
    public void onBackPressed() {
        //Back button is disabled
    }

    //Gets the login state
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //If the user pressed a login button
        if (requestCode == RC_SIGN_IN) {
            // Successfully signed in
            if (resultCode == RESULT_OK) {

                checkUsertype();

                //Play animation user
                mAnimationUser.setVisibility(View.VISIBLE);
                mAnimationUser.playAnimation();
                mAnimationUser.addAnimatorListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                    }

                    //When the animation ends
                    @Override
                    public void onAnimationEnd(Animator animator) {
                        //Set this animation gone
                        mAnimationUser.setVisibility(View.GONE);
                        //Set checked animation visible and play it
                        mAnimationChecked.setVisibility(View.VISIBLE);
                        mAnimationChecked.playAnimation();
                        mUserAnimationFinished = true;
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {
                    }
                });

                mAnimationChecked.addAnimatorListener(new Animator.AnimatorListener() {

                    @Override
                    public void onAnimationStart(Animator animator) {
                    }

                    //When the animation ends
                    @Override
                    public void onAnimationEnd(Animator animator) {
                        //Start activity Mainactivity
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {
                    }
                });
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button.

                //Create dialog promt to ask user to close the app
                new OoOAlertDialog.Builder(this)
                        .setCancelable(false)
                        .setTitle("Afsluiten")
                        .setMessage("Wilt u de app afsluiten?")
                        .setImage(R.drawable.companylogo)

                        .setAnimation(Animation.POP)
                        .setPositiveButton("JA", new OnClickListener() {
                            @Override
                            public void onClick() {
                                //Close the app
                                finishAndRemoveTask();

                            }
                        })
                        .setNegativeButton("NEE", new OnClickListener() {
                            @Override
                            public void onClick() {
                                //Create login screen
                                createLogin();
                            }
                        })
                        .build();
            }
        }
    }

    public void createLogin() {
        // Choose authentication Providers
        mProviders = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.FacebookBuilder().build(),
                new AuthUI.IdpConfig.TwitterBuilder().build());

        // Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(mProviders)
                        .setLogo(R.drawable.companylogowhite)
                        .setTheme(R.style.LoginTheme)
                        .setIsSmartLockEnabled(false)
                        .build(),
                RC_SIGN_IN);
    }

    //Check the userrole
    public void checkUsertype() {
        //If userrole excist
        if (Common.mUserRole != null) {
            //Delete userrole
            Common.mUserRole = null;
        }
        //search in the database
        mDatabaseReference.child("user-roles").child(mAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UsertypeModel staffType = dataSnapshot.getValue(UsertypeModel.class);
                //if stafftype is not null
                if (staffType != null)
                    //Save stafftype
                    Common.mUserRole = staffType.getStafftype();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Database error
            }
        });
    }
}
