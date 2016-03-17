package com.itjh.doushi.UI.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.itjh.doushi.R;
import com.itjh.doushi.Utils.ColorUtils;

public class ProgressFloatingActionButton extends FrameLayout implements ProgressView.OnCompletedListener {

    private FloatingActionButton mFab;
    private ProgressView mProgressView;

    private Drawable mFinalIcon;
    private OnClickListener mListener;

    private int mPrimaryColor;
    private int mAccentColor;
    private int mAccentColorLight;

    public ProgressFloatingActionButton(Context context) {
        this(context, null, 0);
    }

    public ProgressFloatingActionButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressFloatingActionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View view = inflate(getContext(), R.layout.view_progress_fab, this);

        // View injection (ButterKnife has issues with libraries apparently)
        mFab = (FloatingActionButton) view.findViewById(R.id.pfFab);
        mProgressView = (ProgressView) view.findViewById(R.id.pfProgress);

        // Initializing color values
        mPrimaryColor = ColorUtils.getThemePrimaryColor(context);
        mAccentColor = ColorUtils.getThemeAccentColor(context);
        mAccentColorLight = ColorUtils.lighten(mAccentColor, 0.5f);

        // Loading attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.ProgressFloatingActionButton, defStyleAttr, 0);

        if (a.hasValue(R.styleable.ProgressFloatingActionButton_pFabProgressIcon))
            setIcon(a.getDrawable(R.styleable.ProgressFloatingActionButton_pFabProgressIcon));

        if (a.hasValue(R.styleable.ProgressFloatingActionButton_pFabFinalIcon))
            setFinalIcon(a.getDrawable(R.styleable.ProgressFloatingActionButton_pFabFinalIcon));

        // Numeric values

        setStartingProgress(
                a.getInt(R.styleable.ProgressFloatingActionButton_pFabStartingProgress, 0)
        );

        setCurrentProgress(
                a.getInt(R.styleable.ProgressFloatingActionButton_pFabCurrentProgress,
                        mProgressView.mStartingProgress),
                false
        );

        setTotalProgress(
                a.getInt(R.styleable.ProgressFloatingActionButton_pFabTotalProgress, 100)
        );

        setStepSize(
                a.getInt(R.styleable.ProgressFloatingActionButton_pFabStepSize, 10)
        );

        // Colors

        setProgressColor(
                a.getColor(R.styleable.ProgressFloatingActionButton_pFabCircleColor,
                        mPrimaryColor
                )
        );

        setFabColor(
                a.getColor(
                        R.styleable.ProgressFloatingActionButton_pFabRippleColor,
                        mAccentColor
                )
        );

        setRippleColor(
                a.getColor(
                        R.styleable.ProgressFloatingActionButton_pFabRippleColor,
                        mAccentColorLight
                )
        );

        a.recycle();

        // Listeners
        mProgressView.setListener(this);
        mFab.setOnClickListener(v -> mProgressView.next(true));
    }

    /*
    * ({@link ProgressView}) will notify the ({@link ProgressFloatingActionButton}) once the
    * progress has been completed through the ({@link ProgressView.OnCompletedListener}) interface.
    * */
    @Override
    public void onProgressCompleted() {

        // If the progress is completed the FAB will change
        if (mFinalIcon != null)
            mFab.setImageDrawable(mFinalIcon);

        mFab.setOnClickListener(mListener);
    }

    /*
    * Getters and Setters
    * */

    public FloatingActionButton getFab() {
        return mFab;
    }

    public void setStartingProgress(int start) {
        mProgressView.mStartingProgress = start;
    }

    public void setCurrentProgress(int current, boolean animate) {
        mProgressView.setCurrentProgress(current, animate);
    }

    public void setTotalProgress(int total) {
        mProgressView.mTotalProgress = total;
    }

    public void setStepSize(int size) {
        mProgressView.mStepSize = size;
    }

    public void setIcon(Drawable icon) {
        if (icon != null)
            mFab.setImageDrawable(icon);
    }

    public void setFinalIcon(Drawable mFinalIcon) {
        this.mFinalIcon = mFinalIcon;
    }

    public void setProgressColor(int color) {
        mProgressView.mColor = color;
    }

    public void setFabColor(int color) {
        mFab.setBackgroundTintList(ColorStateList.valueOf(color));
    }

    public void setRippleColor(int color) {
        mFab.setRippleColor(color);
    }

    public void setCompletedListener(OnClickListener mListener) {
        this.mListener = mListener;
    }
}