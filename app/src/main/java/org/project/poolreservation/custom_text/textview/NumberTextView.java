package org.project.poolreservation.custom_text.textview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import org.project.poolreservation.custom_text.FontCache;


public class NumberTextView extends androidx.appcompat.widget.AppCompatTextView{

    public NumberTextView(Context context) {
        super(context);

        applyCustomFont(context);
    }

    public NumberTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context);
    }

    public NumberTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("yekanNumber.ttf", context);
        setTypeface(customFont);
    }
}
