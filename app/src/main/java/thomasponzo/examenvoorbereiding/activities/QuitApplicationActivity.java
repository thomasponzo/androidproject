package thomasponzo.examenvoorbereiding.activities;
//Imports
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class QuitApplicationActivity extends AppCompatActivity {

    //Global Declaration

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);

        //Force to close the application
        finishAndRemoveTask();
    }
}