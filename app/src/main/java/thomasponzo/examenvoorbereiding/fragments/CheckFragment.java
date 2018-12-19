package thomasponzo.examenvoorbereiding.fragments;

//Imports

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import thomasponzo.examenvoorbereiding.Model.CheckModel;
import thomasponzo.examenvoorbereiding.R;
import thomasponzo.examenvoorbereiding.viewholder.CheckViewHolder;

public class CheckFragment extends Fragment {

    //Global Declaration

    RecyclerView mRecyclerView;

    FirebaseRecyclerAdapter<CheckModel, CheckViewHolder> mAdapter;
    FirebaseRecyclerOptions<CheckModel> mOptions;

    DatabaseReference mCategories;

    //Initialize new fragment
    public static CheckFragment newInstance() {
        CheckFragment checkfragment = new CheckFragment();
        return checkfragment;
    }

    //Oncreate of the fragment
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCategories = FirebaseDatabase.getInstance().getReference().child("checks");
    }

    //Oncreate of the fragment view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //The orderview_layout the fragment use
        View view = inflater.inflate(R.layout.fragment_check, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerviewcheck);

        mOptions = new FirebaseRecyclerOptions.Builder<CheckModel>()
                .setQuery(mCategories, CheckModel.class)
                .build();

        loadCategories();

        return view;
    }

    //Load and create the views in the recyclerview
    private void loadCategories() {
        mAdapter = new FirebaseRecyclerAdapter<CheckModel, CheckViewHolder>(mOptions) {
            @NonNull
            @Override
            public CheckViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                //The orderview_layout that the viewholer uses
                View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.check_view, parent, false);
                return new CheckViewHolder(mView);
            }

            @Override
            protected void onBindViewHolder(@NonNull CheckViewHolder holder, int position, @NonNull CheckModel model) {
                //Set all the values inside the view
                holder.ammount.setText(model.getAmount() + "x");
                holder.tableNumber.setText("tafel: " +model.getTable());
                holder.timeNumber.setText("tijd: " +model.getTime());
                holder.dateNumber.setText("reservering: " +model.getDate());
                holder.foodName.setText(model.getName());
                holder.price.setText("€ " + model.getPrice());
                holder.totalprice.setText("€ " + model.getTotalprice());
                holder.moneyPayed.setText("Betaald contant " + model.getMoneypayed());
                holder.totalPayed.setText("totaal " + model.getTotalpayed());
                holder.moneyBack.setText("terug " + model.getBack());
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
