package ua.com.spasetv.testintuitions;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import ua.com.spasetv.testintuitions.tools.DisplayMetrics;
import ua.com.spasetv.testintuitions.tools.ExTextView;
import ua.com.spasetv.testintuitions.tools.StaticFields;

public class FragExerciseOne extends Fragment
        implements StaticFields {

    private Activity activity;
    private MainActivity mainActivity;
    CardView cardExOne;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        this.activity = activity;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
                             Bundle saveInstanceState){
        
        float textSize = new DisplayMetrics(getActivity().getWindowManager()).getSizeTitle();
        int widthImage = new DisplayMetrics(getActivity().getWindowManager()).getWidthImage();

        View view = inflater.inflate(R.layout.fragment_exercise_one, null);

        ExTextView textExOneHead = (ExTextView) view.findViewById(R.id.textExOneHead);
        ExTextView textExOneOr = (ExTextView) view.findViewById(R.id.textExOneOr);
        textExOneHead.setTextSize(textSize);
        textExOneOr.setTextSize(textSize);

        ImageView imgExOneQuestion = (ImageView) view.findViewById(R.id.imgExOneQuestion);
        imgExOneQuestion.getLayoutParams().width = widthImage;
        imgExOneQuestion.getLayoutParams().height = widthImage;

        ImageView imgExOneMoon = (ImageView) view.findViewById(R.id.imgExOneMoon);
        imgExOneMoon.getLayoutParams().height = widthImage + widthImage/2;
        imgExOneMoon.getLayoutParams().height = widthImage + widthImage/2;

        ImageView imgExOneSun = (ImageView) view.findViewById(R.id.imgExOneSun);
        imgExOneSun.getLayoutParams().height = widthImage + widthImage/2;
        imgExOneSun.getLayoutParams().height = widthImage + widthImage/2;

        ProgressBar progressBarExOne = (ProgressBar) view.findViewById(R.id.progressBarExOne);
        progressBarExOne.getLayoutParams().width = widthImage;
        progressBarExOne.getLayoutParams().height = widthImage;

        mainActivity = (MainActivity) getActivity();

        restoreActionBar();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

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
