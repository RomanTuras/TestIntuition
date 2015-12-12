package ua.com.spasetv.testintuitions;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ua.com.spasetv.testintuitions.tools.DisplayMetrics;
import ua.com.spasetv.testintuitions.tools.ExTextView;
import ua.com.spasetv.testintuitions.tools.StaticFields;

public class FragExerciseOne extends Fragment
        implements StaticFields {

    private Activity activity;
    private MainActivity mainActivity;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        this.activity = activity;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
                             Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.fragment_exercise_one, null);
        ExTextView textExOneHead = (ExTextView) view.findViewById(R.id.textExOneHead);
        ExTextView textExOneOr = (ExTextView) view.findViewById(R.id.textExOneOr);

        float textSize = new DisplayMetrics(getActivity().getWindowManager()).getSizeTitle();

        textExOneHead.setTextSize(textSize);
        textExOneOr.setTextSize(textSize);

        if(getActivity()!=null){
            mainActivity = (MainActivity) getActivity();
        }

        restoreActionBar();

        return view;
    }

    private void restoreActionBar() {
        mainActivity.overrideActionBar(FRAGMENT_EXERCISE_ONE);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDetach(){
        super.onDetach();
        mainActivity.overrideActionBar(MAIN_ACTIVITY);
    }

}
