package ua.com.spasetv.testintuitions;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragExerciseOne extends Fragment
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
        View view = inflater.inflate(R.layout.fragment_exercise_one, null);


        if(getActivity()!=null){
            mainActivity = (MainActivity) getActivity();
        }

        restoreActionBar();

        return view;
    }

    private void restoreActionBar() {
        mainActivity.refreshActionBar(FRAGMENT_EXERCISE_ONE);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);  //for the control ActionBar from Fragment !!!!!!!
    }

    @Override
    public void onDetach(){
        super.onDetach();
    }

}