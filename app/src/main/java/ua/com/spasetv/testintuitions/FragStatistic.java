package ua.com.spasetv.testintuitions;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ua.com.spasetv.testintuitions.adapters.ViewPageAdapter;
import ua.com.spasetv.testintuitions.tools.StaticFields;

public class FragStatistic extends Fragment
        implements StaticFields {

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

//        LayoutInflater inflater = LayoutInflater.from(this);
        List<View> pages = new ArrayList<>();

        View page = inflater.inflate(R.layout.model_statistic_card_view, null);
        pages.add(page);
        page = inflater.inflate(R.layout.model_statistic_card_view, null);
        pages.add(page);
        page = inflater.inflate(R.layout.model_statistic_card_view, null);
        pages.add(page);

        ViewPageAdapter pagerAdapter = new ViewPageAdapter(pages);
        ViewPager viewPager = new ViewPager(getActivity().getBaseContext());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(1);

//        getActivity().setContentView(viewPager);

        return view;
    }

    private void restoreActionBar() {
        mainActivity.overrideActionBar(FRAGMENT_STATISTIC);
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
