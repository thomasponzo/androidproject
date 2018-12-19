package thomasponzo.examenvoorbereiding.viewholder;

//Imports
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import thomasponzo.examenvoorbereiding.R;

public class ReservationsViewHolder extends RecyclerView.ViewHolder {

    //Global variables

    public TextView tableNumber, personNumber, timeNumber, dateNumber, phoneNumber, reservationName;
    public Button buttonOrder;

    //The item views in the viewholder
    public ReservationsViewHolder(View itemView) {
        super(itemView);

        tableNumber = itemView.findViewById(R.id.tablenumber);
        personNumber =  itemView.findViewById(R.id.personnumber);
        timeNumber = itemView.findViewById(R.id.clocktime);
        dateNumber = itemView.findViewById(R.id.date);
        phoneNumber = itemView.findViewById(R.id.phonenumbertext);
        reservationName = itemView.findViewById(R.id.reservationname);
        buttonOrder = itemView.findViewById(R.id.buttonreservationorder);
    }
}
