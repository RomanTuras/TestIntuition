package ua.com.spasetv.testintuitions;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragAbout extends Fragment
        implements StaticFields{

    Activity activity;
    ExTextView textViewAbout;
    MainActivity mainActivity;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        this.activity = activity;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
                             Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.fragment_about, null);

        textViewAbout = (ExTextView) view.findViewById(R.id.textViewAbout);

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
    }

    @Override
    public void onDetach(){
        super.onDetach();
    }

}
