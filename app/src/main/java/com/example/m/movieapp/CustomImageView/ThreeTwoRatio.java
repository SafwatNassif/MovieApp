package com.example.m.movieapp.CustomImageView;

import android.content.Context;
import android.media.Image;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by bishoy on 9/11/16.
 */
public class ThreeTwoRatio extends ImageView {
    public ThreeTwoRatio(Context context) {
        super(context);
    }

    public ThreeTwoRatio(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ThreeTwoRatio(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int ThreeTwoHeight= MeasureSpec.getSize(widthMeasureSpec)*2/3;
        int ThreeTwoHeigthSpec= MeasureSpec.makeMeasureSpec(ThreeTwoHeight,MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec,ThreeTwoHeigthSpec);
    }
}
