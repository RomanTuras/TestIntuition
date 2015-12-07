package ua.com.spasetv.testintuitions;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by salden on 25/11/2015.
 * Class CardsAdapter for adding content to Main Activity
 * It working with CardView
 */
public class CardsAdapter implements StaticFields {

    Context context;
    ArrayList<ListData> objects;
    ArrayList<CardView> cardHolders;
    MainActivity mainActivity;
    int padding10;
    int padding5;
    float elevation;
    CardHolder cardHolder;

    CardsAdapter(Context context, ArrayList<ListData> arrayList){
        this.context = context;
        this.objects = arrayList;
        cardHolders = new ArrayList<>();
        mainActivity = new MainActivity();
    }


    public ArrayList setCardsOnLayout(LinearLayout cardsContainer){

        LayoutInflater inflater1 = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        padding10 = (int)mainActivity.padding10;
        padding5 = (int)mainActivity.padding5;
        elevation = mainActivity.elevation;
        CardView cardView;
        int id = 0;
        for(ListData listData: objects) {
            View view = inflater1.inflate(R.layout.card_view, cardsContainer, false);

            LinearLayout layout_images = (LinearLayout) view.findViewById(R.id.layout_images);
            layout_images.setPadding(0, padding10*2, 0, padding10*2);

            FrameLayout fr = (FrameLayout) view.findViewById(R.id.frame_card);
            fr.setPadding(padding10, padding5, padding10, padding5);

            cardView = (CardView) view.findViewById(R.id.card);
            cardView.setContentPadding(padding10, padding10, padding10, padding10);

            cardView.setId(id++);
//            cardView.setOnClickListener(mainActivity);


            if(Build.VERSION.SDK_INT > 10) {
//                cardView.setElevation(elevation);
                cardView.setUseCompatPadding(true);
                cardView.setCardElevation(elevation);
//                cardView.setTranslationZ(elevation);
                Log.d("TG", "Build.VERSION.SDK_INT = " + Build.VERSION.SDK_INT);
            }

            int color = 0;
                switch (id){
                    case 1: color = Color.parseColor("#B2FF59");
                        break;
                    case 2: color = Color.parseColor("#69F0AE");
                        break;
                    case 3: color = Color.parseColor("#EEFF41");
                        break;
                    case 4: color = Color.parseColor("#FFAB40");
                        break;
                    case 5: color = Color.parseColor("#FF4081");
                        break;
                }
            cardView.setCardBackgroundColor(color);

            TextView textTitle = (TextView) view.findViewById(R.id.textTitle);
            textTitle.setText(listData.title);
            textTitle.setTextSize(mainActivity.sizeTitle);

            TextView textAmountTimes = (TextView) view.findViewById(R.id.textAmountTimes);
            textAmountTimes.setText(listData.amountTimes);
            textAmountTimes.setTextSize(mainActivity.sizeSubTitle);

            TextView textBestResult = (TextView) view.findViewById(R.id.textBestResult);
            if(listData.bestResult != null) {
//                textBestResult.setText(listData.bestResult);
                textBestResult.setText("Width: " + mainActivity.width +
                        "  Hight: " + mainActivity.high + "  Dpi: "+mainActivity.dpi);
                textBestResult.setTextSize(mainActivity.sizeSubTitle);
            }else textBestResult.setVisibility(View.GONE);

            ImageView imageView = (ImageView) view.findViewById(R.id.img);
            imageView.setImageResource(listData.idImg);

            imageView.getLayoutParams().width = mainActivity.widthImage;
            imageView.getLayoutParams().height = mainActivity.widthImage;

            ImageView imageViewArrow = (ImageView) view.findViewById(R.id.img_arrow);
            imageViewArrow.getLayoutParams().width = mainActivity.widthImageArrow;
            imageViewArrow.getLayoutParams().height = mainActivity.widthImageArrow;


            cardsContainer.addView(fr);
            cardHolders.add(cardView);
        }
        return cardHolders;
    }

}