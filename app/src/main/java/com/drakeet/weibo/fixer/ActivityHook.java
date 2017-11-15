/*
 * GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007
 *
 * Copyright (C) 2017 drakeet https://github.com/drakeet
 */

package com.drakeet.weibo.fixer;

import android.annotation.SuppressLint;
import android.app.Activity;
import de.robv.android.xposed.XC_MethodHook;

/**
 * @author drakeet
 */
public class ActivityHook extends XC_MethodHook {

    @SuppressLint("StaticFieldLeak")
    private static Activity currentActivity;


    public static Activity getCurrentActivity() {
        return currentActivity;
    }


    @Override
    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
        super.afterHookedMethod(param);
        currentActivity = (Activity) param.getResult();
    }
}