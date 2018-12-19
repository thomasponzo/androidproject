package thomasponzo.examenvoorbereiding.activities;

//imports
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import nl.psdcompany.duonavigationdrawer.views.DuoOptionView;
import thomasponzo.examenvoorbereiding.R;

class MenuAdapter extends BaseAdapter {

    //Global Declaration

    private ArrayList<String> mOptions = new ArrayList<>();
    private ArrayList<DuoOptionView> mOptionViews = new ArrayList<>();

    MenuAdapter(ArrayList<String> options) {
        mOptions = options;
    }

    //Get amount of navigationmenu_layout options
    @Override
    public int getCount() {
        return mOptions.size();
    }

    //Get position of navigationmenu_layout items
    @Override
    public Object getItem(int position) {
        return mOptions.get(position);
    }

    void setViewSelected(int position, boolean selected) {

        // Looping through the mOptions in the navigationmenu_layout
        // Selecting the chosen option
        for (int i = 0; i < mOptionViews.size(); i++) {
            if (i == position) {
                mOptionViews.get(i).setSelected(selected);
            } else {
                mOptionViews.get(i).setSelected(!selected);
            }
        }
    }

    //Getting item id
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final String option = mOptions.get(position);

        final DuoOptionView mOptionView;
        if (convertView == null) {
            mOptionView = new DuoOptionView(parent.getContext());
        } else {
            mOptionView = (DuoOptionView) convertView;
        }

        // Using the DuoOptionView's default selectors
        mOptionView.bind(option, ResourcesCompat.getDrawable(parent.getResources(), R.drawable.menuright, null), null);

        // Adding the views to an array list to handle view selection
        mOptionViews.add(mOptionView);

        return mOptionView;
    }
}
