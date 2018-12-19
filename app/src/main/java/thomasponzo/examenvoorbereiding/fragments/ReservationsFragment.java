package thomasponzo.examenvoorbereiding.fragments;

//Imports

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import thomasponzo.examenvoorbereiding.R;

public class ReservationsFragment extends Fragment {

    //Global Declaration

    //Initialize new fragment
    public static ReservationsFragment newInstance() {
        ReservationsFragment reservationsFragment = new ReservationsFragment();
        return reservationsFragment;
    }

    //Oncreate of the fragment
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    //Oncreate of the fragment view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //The orderview_layout the fragment use
        View view = inflater.inflate(R.layout.fragment_reservations, container, false);

        setDefaultFragment();
        return view;
    }

    //The default fragment when the activity gets openned for the first time
    private void setDefaultFragment() {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        transaction.replace(R.id.containerreservations, ReservationsFragmentLayout.newInstance());
        transaction.commit();
    }
}
