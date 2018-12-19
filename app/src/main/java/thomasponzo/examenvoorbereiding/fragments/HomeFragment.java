package thomasponzo.examenvoorbereiding.fragments;

//Imports
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import thomasponzo.examenvoorbereiding.R;

public class HomeFragment extends Fragment {

    //Initialize new fragment
    public static HomeFragment newInstance() {
        HomeFragment homeFragment = new HomeFragment();
        return homeFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //The orderview_layout the fragment uses
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        return view;
    }
}
