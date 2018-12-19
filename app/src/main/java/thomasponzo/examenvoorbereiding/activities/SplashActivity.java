package thomasponzo.examenvoorbereiding.activities;

//Imports
import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import thomasponzo.examenvoorbereiding.Model.UsertypeModel;
import thomasponzo.examenvoorbereiding.R;
import thomasponzo.examenvoorbereiding.common.Common;

public class SplashActivity extends AppCompatActivity {

    //Global Declaration

    private FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference mDatabaseReference;

    LottieAnimationView mSplashscreenLogo;

    //When activity gets created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //The layou the activity use
        setContentView(R.layout.activity_splash);

        //Reset the user role because user can have a different one
        if (Common.mUserRole != null) {
            Common.mUserRole = null;
        }

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        mSplashscreenLogo = findViewById(R.id.splash_logo_animation);

        //Use hardwareacceleration for smoother animation
        mSplashscreenLogo.useHardwareAcceleration();

        //Listening when animation starts and finisches
        mSplashscreenLogo.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                //If the user is signed in
                if (mAuth.getCurrentUser() != null) {
                    checkUsertype();
                }
            }

            //When the animation ends
            @Override
            public void onAnimationEnd(Animator animator) {

                //Check if the user can login with current stored auth key
                if (mUser != null) {
                    mAuth.getCurrentUser()
                            .reload()
                            .addOnSuccessListener(aVoid -> {

                                //Skip the login screen and go right away to MainActivity because auth key is valid
                                Intent mIntent = new Intent(SplashActivity.this, MainActivity.class);
                                startActivity(mIntent);
                                finish();
                            });
                }

                //User doesn't have valid stored auth key go to login
                Intent mIntent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(mIntent);
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        //If the user insert the app in the background pause the animation
        mSplashscreenLogo.pauseAnimation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //If the user opens the app from the background resume the animation
        mSplashscreenLogo.resumeAnimation();

    }

    @Override
    public void onBackPressed() {
        //Back button is disabled so the user cant interupt the animation
    }

    public void checkUsertype() {
        //If there is a userrol delete it
        if (Common.mUserRole != null) {
            Common.mUserRole = null;
        }

        //Get user information from database
        mDatabaseReference.child("user-roles").child(mAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UsertypeModel mStaffType = dataSnapshot.getValue(UsertypeModel.class);
                //Check if stafftype excist
                if (mStaffType != null)
                    //if the user has a staff save it
                    Common.mUserRole = mStaffType.getStafftype();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Server got error
            }
        });
    }
}
