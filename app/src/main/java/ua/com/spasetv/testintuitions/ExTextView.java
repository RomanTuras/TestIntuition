package ua.com.spasetv.testintuitions;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by salden on 07/12/2015.
 * Overriding class TextView for adding TypeFace in each TextView in this activity
 */

public class ExTextView extends AppCompatTextView {

    String droid_font = "fonts/DroidSans.ttf";
    Typeface CF;

    public ExTextView(Context context) {
        super(context);
        CF = Typeface.createFromAsset(context.getAssets(), droid_font);
        setTypeface(CF);
    }

    public ExTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        CF = Typeface.createFromAsset(context.getAssets(), droid_font);
        setTypeface(CF);
    }

    public ExTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        CF = Typeface.createFromAsset(context.getAssets(), droid_font);
        setTypeface(CF);
    }

    @Override
    public void setTypeface(android.graphics.Typeface tf, int style) {
        super.setTypeface(tf);
    }
}