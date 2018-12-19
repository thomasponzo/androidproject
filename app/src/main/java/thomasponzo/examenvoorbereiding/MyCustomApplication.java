package thomasponzo.examenvoorbereiding;

//Imports
import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.WindowManager;

public class MyCustomApplication extends Application {
    // Called when the application is starting, before any other application objects have been created.

    @Override
    public void onCreate() {
        super.onCreate();

        // register to be informed of activities starting up
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            //oncreate of the activity
            @Override
            public void onActivityCreated(Activity activity,
                                          Bundle savedInstanceState) {

                //Put all activities on fullscreen
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


                //Force activities orientation to portrait
                activity.setRequestedOrientation(
                        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }


            @Override
            public void onActivityStarted(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
            }
        });
    }
}
