package ua.com.spasetv.testintuitions;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by salden on 25/11/2015.
 * Class MyAdapter for adding content List View
 * It working with container ListData
 */
public class MyAdapter extends BaseAdapter{

    Context context;
    ArrayList<ListData> objects;
    LayoutInflater inflater;

    MyAdapter(Context context, ArrayList<ListData> arrayList){
        this.context = context;
        this.objects = arrayList;
        inflater = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Log.d("TG", "MyAdapter - constructor");
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        View view = inflater.inflate(R.layout.format_list_view, viewGroup, false);
        ListData listData = (ListData) getItem(position);

        Log.d("TG", "listData = "+listData.toString());

        ((TextView) view.findViewById(R.id.textTitle))
                .setText(listData.title);

        ((TextView) view.findViewById(R.id.textAmountTimes))
                .setText(listData.amountTimes);

        ((TextView) view.findViewById(R.id.textBestResult))
                .setText(listData.bestResult);

        (view.findViewById(R.id.img))
                .setBackgroundResource(listData.idImg);

        return view;
    }
}
