/*
 * GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007
 *
 * Copyright (C) 2017 drakeet https://github.com/drakeet
 */

package com.drakeet.weibo.fixer;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import java.util.Locale;

/**
 * @author drakeet
 */
@SuppressWarnings("WeakerAccess")
public class Androids {

    public static void openMarket(@NonNull Context context, @NonNull String targetPackage) {
        if (Locale.getDefault().equals(Locale.CHINA)) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.coolapk.com/apk/" + targetPackage)));
        } else {
            try {
                Intent googlePlayIntent = context.getPackageManager().getLaunchIntentForPackage("com.android.vending");
                ComponentName comp = new ComponentName("com.android.vending", "com.google.android.finsky.activities.LaunchUrlHandlerActivity");
                // noinspection ConstantConditions
                googlePlayIntent.setComponent(comp);
                googlePlayIntent.setData(Uri.parse("market://details?id=" + targetPackage));
                context.startActivity(googlePlayIntent);
            } catch (Throwable e) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.coolapk.com/apk/" + targetPackage)));
                e.printStackTrace();
            }
        }
    }


    public static void copyToClipBoard(@NonNull Context context, @NonNull CharSequence text) {
        ClipData clipData = ClipData.newPlainText(context.getPackageName(), text);
        ClipboardManager manager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (manager != null) {
            manager.setPrimaryClip(clipData);
        }
    }
}
