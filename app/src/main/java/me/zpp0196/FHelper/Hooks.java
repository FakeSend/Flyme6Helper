package me.zpp0196.FHelper;

/**
 * Created by zpp0196 on 2017/11/25.
 */

import android.content.Context;
import android.net.Uri;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class Hooks implements IXposedHookLoadPackage {

    private static XC_LoadPackage.LoadPackageParam loadPackageParam;

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        loadPackageParam = lpparam;
        try {
            patchcode();
        } catch (ClassNotFoundException e) {
            XposedBridge.log(e);
        }
    }

    private void patchcode() throws ClassNotFoundException {

        if (loadPackageParam.packageName.equals("com.meizu.customizecenter")) {

            XposedBridge.log("crack by coderstory");
            XposedBridge.log("build by zpp0196");

            findAndHookMethod("com.meizu.customizecenter.h.al", loadPackageParam.classLoader, "h", Context.class, XC_MethodReplacement.returnConstant(0));
            findAndHookMethod("com.meizu.customizecenter.common.theme.common.theme.a", loadPackageParam.classLoader, "e", XC_MethodReplacement.returnConstant(false));
            findAndHookMethod("com.meizu.customizecenter.common.font.c", loadPackageParam.classLoader, "e", XC_MethodReplacement.returnConstant(""));
            findAndHookMethod("com.meizu.customizecenter.common.theme.common.b", loadPackageParam.classLoader, "b", XC_MethodReplacement.returnConstant(false));
            findAndHookMethod("com.meizu.customizecenter.common.theme.common.b", loadPackageParam.classLoader, "b", Boolean.TYPE, XC_MethodReplacement.returnConstant(null));
            findAndHookMethod("com.meizu.customizecenter.font.c", loadPackageParam.classLoader, "b", XC_MethodReplacement.returnConstant(false));
            findAndHookMethod("com.meizu.customizecenter.common.g.f", loadPackageParam.classLoader, "a", String.class, String.class, int.class, int.class, int.class, new XC_MethodReplacement() {
                @Override
                protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                    return null;
                }
            });
            findAndHookMethod("com.meizu.customizecenter.common.g.c", loadPackageParam.classLoader, "a", String.class, String.class, int.class, int.class, int.class, new XC_MethodReplacement() {
                @Override
                protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                    return null;
                }
            });

            //主题混搭 ThemeContentProvider query Unknown URI
            findAndHookMethod("com.meizu.customizecenter.common.dao.ThemeContentProvider", loadPackageParam.classLoader, "query", Uri.class, String[].class, String.class, String[].class, String.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                    Object[] objs = param.args;
                    String Tag = "(ITEMS LIKE";
                    String Tag2 = "%zklockscreen;%";
                    String Tag3 = "%com.meizu.flyme.weather;%";
                    //XposedBridge.log("开始");
                    boolean result = false;
                    for (Object obj : objs) {
                        // XposedBridge.log(obj==null?"":obj.toString());
                        if (obj instanceof String && (((String) obj).contains(Tag) || obj.equals(Tag2) || obj.equals(Tag3))) {
                            result = true;
                        }
                    }
                    // XposedBridge.log("结束");
                    if (result) {
                        for (Object obj : objs) {
                            if (obj instanceof String[]) {
                                for (int j = 0; j < ((String[]) obj).length; j++) {
                                    if (((String[]) obj)[j].contains("/storage/emulated/0/Customize/Themes")) {
                                        ((String[]) obj)[j] = "/storage/emulated/0/Customize%";
                                    } else if (((String[]) obj)[j].contains("/storage/emulated/0/Customize/TrialThemes")) {
                                        ((String[]) obj)[j] = "NONE";
                                    }
                                }
                            }
                        }
                    }
                    super.beforeHookedMethod(param);
                }
            });
        }
    }

    private static void findAndHookMethod(String p1, ClassLoader lpparam, String p2, Object... parameterTypesAndCallback) {
        try {
            XposedHelpers.findAndHookMethod(p1, lpparam, p2, parameterTypesAndCallback);
        } catch (Throwable localString3) {
            XposedBridge.log(localString3);
        }
    }
}