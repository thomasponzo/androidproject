package thomasponzo.examenvoorbereiding.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import thomasponzo.examenvoorbereiding.Model.ReservationModel;
import thomasponzo.examenvoorbereiding.R;
import thomasponzo.examenvoorbereiding.viewholder.ReservationsViewHolder;

public class ReservationsFragmentLayout extends Fragment {

    //Global Declaration

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;

    FirebaseRecyclerAdapter<ReservationModel, ReservationsViewHolder> mAdapter;
    FirebaseRecyclerOptions<ReservationModel> mOptions;

    DatabaseReference mCategories;
    DatabaseReference mDatabase;


    //Initialize new fragment
    public static ReservationsFragmentLayout newInstance() {
        ReservationsFragmentLayout reservationsFragmentLayout = new ReservationsFragmentLayout();
        return reservationsFragmentLayout;
    }

    //Oncreate of the fragment
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCategories = FirebaseDatabase.getInstance().getReference().child("reservations");
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    //Oncreate of the fragment view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //The orderview_layout the fragment use
        View view = inflater.inflate(R.layout.layout_reservations, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerview);

        mOptions = new FirebaseRecyclerOptions.Builder<ReservationModel>()
                .setQuery(mCategories, ReservationModel.class)
                .build();

        loadCategories();

        return view;
    }

    //Load and create the views in the recyclerview
    private void loadCategories() {
        mAdapter = new FirebaseRecyclerAdapter<ReservationModel, ReservationsViewHolder>(mOptions) {
            @NonNull
            @Override
            public ReservationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                //The orderview_layout that the viewholer uses
                View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.reservationview_layout, parent, false);
                return new ReservationsViewHolder(mView);
            }

            @Override
            protected void onBindViewHolder(@NonNull ReservationsViewHolder holder, int position, @NonNull ReservationModel model) {
                //Set all the values inside the view
                holder.personNumber.setText(model.getPersonNumber());
                holder.tableNumber.setText(model.getTableNumBer());
                holder.timeNumber.setText(model.getTimeNumber());
                holder.dateNumber.setText(model.getDateNumber());
                holder.phoneNumber.setText(model.getPhoneNumber());
                holder.reservationName.setText("reservering: " + model.getReservationName());
                holder.buttonOrder.setOnClickListener(view -> {
                    String keyReservation = mAdapter.getRef(position).getKey();
                    FragmentTransaction transaction = getParentFragment().getChildFragmentManager().beginTransaction();

                    transaction.replace(R.id.containerreservations, OrderMenuFragment.newInstance());
                    transaction.commit();
                });

            }
        };
        //Get the orderview_layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        //Listening to changes in the database and also Changing the view in real time based on the changes
        mAdapter.startListening();
        mAdapter.notifyDataSetChanged();

        mRecyclerView.setAdapter(mAdapter);
    }
}
