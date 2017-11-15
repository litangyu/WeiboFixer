/*
 * GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007
 *
 * Copyright (C) 2017 drakeet https://github.com/drakeet
 */

package com.drakeet.weibo.fixer;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;
import io.fabric.sdk.android.Fabric;

import static com.drakeet.weibo.fixer.Androids.openMarket;

/**
 * @author drakeet
 */
public class MainActivity extends Activity {

    private LinearLayout contentLayout;
    private LinearLayout actionLayout;
    private LinearLayout adLayout;
    private TextView messageView;
    private int contentLayoutPaddingBottomNormal;
    private int contentLayoutPaddingBottomWithActions;
    private String nice;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        ((View) (findViewById(R.id.card).getParent())).setOnClickListener(view -> onBackPressed());
        messageView = findViewById(R.id.message);
        contentLayout = findViewById(R.id.contentLayout);
        actionLayout = findViewById(R.id.actionLayout);
        adLayout = findViewById(R.id.adLayout);
        contentLayoutPaddingBottomNormal = getResources().getDimensionPixelSize(R.dimen.dialogPaddingBottom);
        contentLayoutPaddingBottomWithActions = getResources().getDimensionPixelSize(R.dimen.dialogPaddingBottomWithActions);
        nice = getIntent().getStringExtra("nice");
        if (nice != null) {
            messageView.setText(R.string.note_with_action);
            setActions(true);
        } else {
            messageView.setText(R.string.note);
            setActions(false);
        }
    }


    private void setActions(boolean show) {
        if (show) {
            contentLayout.setPadding(contentLayout.getPaddingLeft(), contentLayout.getPaddingTop(), contentLayout.getPaddingRight(), contentLayoutPaddingBottomWithActions);
            actionLayout.setVisibility(View.VISIBLE);
            adLayout.setVisibility(View.GONE);
            Answers.getInstance().logCustom(new CustomEvent("Fix"));
        } else {
            contentLayout.setPadding(contentLayout.getPaddingLeft(), contentLayout.getPaddingTop(), contentLayout.getPaddingRight(), contentLayoutPaddingBottomNormal);
            actionLayout.setVisibility(View.GONE);
            adLayout.setVisibility(View.VISIBLE);
            Answers.getInstance().logCustom(new CustomEvent("Open"));
        }
    }


    public void onNegativeButtonClicked(View view) {
        onBackPressed();
    }


    public void onPositiveButtonClicked(View view) {
        Androids.copyToClipBoard(this, nice);
        onBackPressed();
    }


    public void onWriterClicked(View view) {
        openMarket(this, "com.drakeet.purewriter");
        Answers.getInstance().logCustom(new CustomEvent("Writer"));
    }


    public void onMosaicClicked(View view) {
        openMarket(this, "me.drakeet.puremosaic");
        Answers.getInstance().logCustom(new CustomEvent("Mosaic"));
    }
}
