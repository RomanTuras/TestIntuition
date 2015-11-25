package ua.com.spasetv.testintuitions;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragAbout extends Fragment {

    Activity activity;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        this.activity = activity;

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
                             Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.fragment_about, null);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);  //for the control ActionBar from Fragment !!!!!!!
    }
}
