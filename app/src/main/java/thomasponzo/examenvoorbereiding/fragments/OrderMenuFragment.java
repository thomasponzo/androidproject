package thomasponzo.examenvoorbereiding.fragments;

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
import com.squareup.picasso.Picasso;

import thomasponzo.examenvoorbereiding.Model.OrderMenuModel;
import thomasponzo.examenvoorbereiding.R;
import thomasponzo.examenvoorbereiding.viewholder.OrderMenuViewHolder;

public class OrderMenuFragment extends Fragment {

    //Global Declaration

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;

    FirebaseRecyclerAdapter<OrderMenuModel, OrderMenuViewHolder> mAdapter;
    FirebaseRecyclerOptions<OrderMenuModel> mOptions;

    DatabaseReference mCategories;
    DatabaseReference mDatabase;


    //Initialize new fragment
    public static OrderMenuFragment newInstance() {
        OrderMenuFragment orderMenuFragment = new OrderMenuFragment();
        return orderMenuFragment;
    }

    //Oncreate of the fragment
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCategories = FirebaseDatabase.getInstance().getReference().child("categories");
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    //Oncreate of the fragment view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //The orderview_layout the fragment use
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerviewordermenu);

        mOptions = new FirebaseRecyclerOptions.Builder<OrderMenuModel>()
                .setQuery(mCategories, OrderMenuModel.class)
                .build();

        loadCategories();

        return view;
    }

    //Load and create the views in the recyclerview
    private void loadCategories() {
        mAdapter = new FirebaseRecyclerAdapter<OrderMenuModel, OrderMenuViewHolder>(mOptions) {
            @NonNull
            @Override
            public OrderMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                //The orderview_layout that the viewholer uses
                View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ordermenu_view, parent, false);
                return new OrderMenuViewHolder(mView);
            }

            @Override
            protected void onBindViewHolder(@NonNull OrderMenuViewHolder holder, int position, @NonNull OrderMenuModel model) {
                //Set all the values inside the view
                holder.menuName.setText(model.getName());
                Picasso.with(getActivity())
                        .load(model.getPicture())
                        .fit()
                        .into(holder.menuPicture);
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

