/*
 * GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007
 *
 * Copyright (C) 2017 drakeet https://github.com/drakeet
 */

package com.drakeet.weibo.fixer;

import android.content.ClipData;
import android.content.Intent;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import java.lang.reflect.Method;

import static de.robv.android.xposed.XposedBridge.log;

/**
 * @author drakeet
 */
public class Fixer implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam param) throws Throwable {
        if (!param.packageName.equals("com.sina.weibo")) {
            return;
        }
        log("Fixer handleLoadPackage: " + param.packageName);

        Class<?> instrumentation = XposedHelpers.findClass("android.app.Instrumentation", param.classLoader);
        Method method = instrumentation.getMethod("newActivity", ClassLoader.class, String.class, Intent.class);
        XposedBridge.hookMethod(method, new ActivityHook());

        XposedHelpers.findAndHookMethod("android.content.ClipboardManager",
            param.classLoader, "setPrimaryClip", ClipData.class, new ClipboardHook());
    }
}
