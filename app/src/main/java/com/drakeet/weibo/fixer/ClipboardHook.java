/*
 * GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007
 *
 * Copyright (C) 2017 drakeet https://github.com/drakeet
 */

package com.drakeet.weibo.fixer;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import de.robv.android.xposed.XC_MethodHook;

/**
 * @author drakeet
 */
public class ClipboardHook extends XC_MethodHook {

    @Override
    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
        Context context = ActivityHook.getCurrentActivity();
        ClipData clip = (ClipData) param.args[0];
        String dirty = clip.getItemAt(0).coerceToText(context).toString();
        if (dirty.startsWith("[cp]") && dirty.endsWith("[/cp]")) {
            String nice = dirty.substring(4, dirty.length() - 5);
            final String scheme = BuildConfig.DEBUG ? "drakeet-debug" : "drakeet";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(scheme + "://weibo.fixer"));
            intent.putExtra("nice", nice);
            context.startActivity(intent);
        }
    }
}