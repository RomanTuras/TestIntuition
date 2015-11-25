package ua.com.spasetv.testintuitions;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragAbout extends Fragment {

    Activity activity;
    TextView textViewAbout;
    MainActivity mainActivity;
    public static final int FRAGMENT_ABOUT=1;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        this.activity = activity;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
                             Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.fragment_about, null);

        textViewAbout = (TextView) view.findViewById(R.id.textViewAbout);

        if(getActivity()!=null){
            mainActivity = (MainActivity) getActivity();
        }
        showTextHelp();
        restoreActionBar();

        return view;
    }

    private void restoreActionBar() {
        mainActivity.refreshActionBar(FRAGMENT_ABOUT);
    }

    private void showTextHelp() {
        String[] arrayTextAbout = getResources().getStringArray(R.array.textAbout);
        String tmpString="";
        for(String string: arrayTextAbout){
            tmpString+=string;
        }
        textViewAbout.setText(tmpString);
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
