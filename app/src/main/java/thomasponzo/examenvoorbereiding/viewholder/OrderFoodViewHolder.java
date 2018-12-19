package thomasponzo.examenvoorbereiding.viewholder;

//Imports

import android.support.v7.widget.RecyclerView;
import android.view.View;

import thomasponzo.examenvoorbereiding.Database.Database;
import thomasponzo.examenvoorbereiding.Model.OrderModel;
import thomasponzo.examenvoorbereiding.R;

public class OrderFoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

    public OrderFoodViewHolder(View itemView) {
        super(itemView);



        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        new Database(view.getContext()).addToCard(new OrderModel(

        ));
    }
}
