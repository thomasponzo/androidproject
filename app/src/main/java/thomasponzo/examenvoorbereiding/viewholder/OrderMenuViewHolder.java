package thomasponzo.examenvoorbereiding.viewholder;

//Imports

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import thomasponzo.examenvoorbereiding.R;
import thomasponzo.examenvoorbereiding.interpface.ItemClickListener;

public class OrderMenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    //Global variables

    public TextView menuName;
    public ImageView menuPicture;

    private ItemClickListener itemClickListener;

    //The item views in the viewholder
    public OrderMenuViewHolder(View itemView) {
        super(itemView);

        menuName = itemView.findViewById(R.id.menutextorder);
        menuPicture = itemView.findViewById(R.id.menupictureorder);

        itemView.setOnClickListener(this);
    }


    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);
    }
    //When the user clicks on the view
}
