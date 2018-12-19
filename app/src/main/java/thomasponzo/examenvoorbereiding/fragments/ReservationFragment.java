package thomasponzo.examenvoorbereiding.fragments;

//Imports

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.alexzaitsev.meternumberpicker.MeterView;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.DatePicker;
import com.applandeo.materialcalendarview.builders.DatePickerBuilder;
import com.applandeo.materialcalendarview.listeners.OnSelectDateListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.instacart.library.truetime.TrueTime;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

import br.com.joinersa.oooalertdialog.Animation;
import br.com.joinersa.oooalertdialog.OnClickListener;
import br.com.joinersa.oooalertdialog.OoOAlertDialog;
import thomasponzo.examenvoorbereiding.InitTrueTimeAsyncTask;
import thomasponzo.examenvoorbereiding.Model.ReservationModel;
import thomasponzo.examenvoorbereiding.R;
import thomasponzo.examenvoorbereiding.activities.MainActivity;
import thomasponzo.examenvoorbereiding.activities.QuitApplicationActivity;


public class ReservationFragment extends Fragment implements OnSelectDateListener {

    private FirebaseAuth mAuth;

    DatabaseReference mDatabaseReference;

    TextInputEditText mNameInput, mPhoneInput;

    String mDate, mString;

    MeterView meterView;

    //Global Declaration

    //Todo this screen is not in my tasks dont review this one for the exams

    TextView mDatetext, mTimeText;

    //Initialize new fragment
    public static ReservationFragment newInstance() {
        ReservationFragment rankingFragment = new ReservationFragment();
        return rankingFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reservation, container, false);
        mAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        meterView = view.findViewById(R.id.meterView);

        Button test1 = view.findViewById(R.id.buttonreservationTime);

        Button test2 = view.findViewById(R.id.buttoncompletereservation);

        mNameInput = view.findViewById(R.id.inputfieldname);
        mPhoneInput = view.findViewById(R.id.inputfieldphone);

        meterView.setValue(1);

        mDatetext = view.findViewById(R.id.datetext);
        mTimeText = view.findViewById(R.id.Timetext);

        Calendar now = Calendar.getInstance();

        test2.setOnClickListener(view12 -> {
            String key = mDatabaseReference.child("jobs").push().getKey();
            String numbers = String.valueOf(meterView.getValue());

            //Set the new values in the database
            mDatabaseReference.child("reservations").child(key).setValue(new ReservationModel(mDate, numbers, mPhoneInput.getText().toString(),
                    mNameInput.getText().toString(), "18", mString));

            //Create the dialog to notify the user the reservation has be made succesfully
            new OoOAlertDialog.Builder(getActivity())
                    .setCancelable(false)
                    .setTitle("Reservering")
                    .setMessage("Uw reservering is sucsesvol afgerond")
                    .setImage(R.drawable.companylogo)
                    .setAnimation(Animation.POP)
                    .setPositiveButton("Ok", new OnClickListener() {
                        @Override
                        public void onClick() {
                            //Clear the textvields
                            mDatetext.setText("Datum:");
                            mTimeText.setText("Tijd:");
                            mNameInput.setText("");
                            mPhoneInput.setText("");
                            meterView.setValue(1);
                        }
                    })
                    .build();
        });

        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {

                mString = String.valueOf(hourOfDay) + ":" + String.valueOf(minute);

                if (minute < 10) {
                    mString = String.valueOf(hourOfDay) + ":" + "0" + String.valueOf(minute);
                }

                mTimeText.setText("Tijd: " + mString);
            }
        };

        TimePickerDialog dpd = TimePickerDialog.newInstance(
                onTimeSetListener,
                now.get(Calendar.YEAR), // Initial year selection
                now.get(Calendar.MONTH), // Initial month selection
                true // Inital day selection
        );

        test1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dpd.setMinTime(17, 0, 0);
                dpd.setCancelable(false);
                dpd.setAccentColor(Color.BLUE);
                dpd.show(getActivity().getFragmentManager(), "TimePickerDialog");
            }
        });

        //Create the current time without using device time
        initTrueTime(getActivity());

        Button mReservationButtonDate = view.findViewById(R.id.buttonreservationdate);

        OnSelectDateListener listener = new OnSelectDateListener() {
            @Override
            public void onSelect(List<Calendar> calendar) {
                DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getActivity());

                mDate = String.valueOf(dateFormat.format(calendar.get(0).getTime()));
                mDatetext.setText("Datum: " + mDate);
            }
        };

        mReservationButtonDate.setOnClickListener(view1 -> {
            DatePickerBuilder builder = new DatePickerBuilder(getActivity(), listener)
                    .minimumDate(toCalendarMinium(getTrueTime()))
                    .maximumDate(toCalendarMax(getTrueTime()))
                    .pickerType(CalendarView.ONE_DAY_PICKER);

            DatePicker datePicker = builder.build();
            datePicker.show();
        });
        return view;
    }

    @Override
    public void onSelect(List<Calendar> calendar) {
    }

    public static Date getTrueTime() {
        //Convert the truetime to Date
        Date date = TrueTime.isInitialized() ? TrueTime.now() : new Date();
        return date;
    }

    public static void initTrueTime(Context ctx) {
        //Get real time from google
        if (isNetworkConnected(ctx)) {
            if (!TrueTime.isInitialized()) {
                InitTrueTimeAsyncTask trueTime = new InitTrueTimeAsyncTask(ctx);
                trueTime.execute();
            }
        }
    }

    public static boolean isNetworkConnected(Context ctx) {
        //Check if the user has currently connection
        ConnectivityManager cm = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();

        return ni != null && ni.isConnectedOrConnecting();
    }

    public static Calendar toCalendarMinium(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // - one day because the day is 1 day to late
        cal.add(Calendar.DAY_OF_YEAR, -1);
        return cal;
    }

    public static Calendar toCalendarMax(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //Amount of months the user can make a reservation
        cal.add(Calendar.MONTH, +2);
        return cal;
    }
}
