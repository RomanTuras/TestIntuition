package ua.com.spasetv.testintuitions;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragStatistic extends Fragment
        implements StaticFields{

    Activity activity;
    TextView textViewAbout;
    MainActivity mainActivity;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        this.activity = activity;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
                             Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.fragment_statistic, null);

        if(getActivity()!=null){
            mainActivity = (MainActivity) getActivity();
        }

        restoreActionBar();

        return view;
    }

    private void restoreActionBar() {
        mainActivity.refreshActionBar(FRAGMENT_STATISTIC);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDetach(){
        super.onDetach();
    }

}
