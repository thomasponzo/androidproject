package thomasponzo.examenvoorbereiding.viewholder;

//Imports

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import thomasponzo.examenvoorbereiding.R;

public class CheckViewHolder extends RecyclerView.ViewHolder {

    //Global variables

    public TextView tableNumber, timeNumber, dateNumber, ammount, foodName, price, totalprice, moneyPayed, totalPayed, moneyBack;

    //The item views in the viewholder
    public CheckViewHolder(View itemView) {
        super(itemView);

        tableNumber = itemView.findViewById(R.id.checktable);
        ammount =  itemView.findViewById(R.id.checkamount);
        timeNumber = itemView.findViewById(R.id.checktime);
        dateNumber = itemView.findViewById(R.id.checkdate);
        foodName = itemView.findViewById(R.id.checkname);
        price = itemView.findViewById(R.id.checkprice);
        totalprice = itemView.findViewById(R.id.checktotalprice);
        moneyPayed = itemView.findViewById(R.id.checkpayed);
        totalPayed = itemView.findViewById(R.id.checktotal);
        moneyBack = itemView.findViewById(R.id.checkback);
    }
}