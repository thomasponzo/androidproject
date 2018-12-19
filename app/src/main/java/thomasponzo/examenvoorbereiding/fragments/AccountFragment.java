package thomasponzo.examenvoorbereiding.fragments;

//Imports

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import thomasponzo.examenvoorbereiding.R;

public class AccountFragment extends Fragment {

    //Global Declaration

    //Initialize new fragment
    public static AccountFragment newInstance() {
        AccountFragment rankingFragment = new AccountFragment();
        return rankingFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //the orderview_layout the fragment uses
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        return view;
    }

}
