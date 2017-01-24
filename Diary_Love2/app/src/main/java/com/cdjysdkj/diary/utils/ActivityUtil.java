package com.cdjysdkj.diary.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Toast;

import com.cdjysdkj.diary.R;
import com.cdjysdkj.diary.WelcomeActivity;
import com.cdjysdkj.diary.application.MyApplication;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SuppressLint("DefaultLocale")
public final class ActivityUtil {

    public static int[] getScreenSize() {
        int[] screens;
        // if (Constants.screenWidth > 0) {
        // return screens;
        // }
        DisplayMetrics dm=new DisplayMetrics();
        dm=MyApplication.getInstance().getResources().getDisplayMetrics();
        screens=new int[]{dm.widthPixels, dm.heightPixels};
        return screens;
    }

    public static float[] getBitmapConfiguration(Bitmap bitmap, ImageView imageView, float screenRadio) {
        int screenWidth=ActivityUtil.getScreenSize()[0];
        float rawWidth=0;
        float rawHeight=0;
        float width=0;
        float height=0;
        if(bitmap == null) {
            // rawWidth = sourceWidth;
            // rawHeight = sourceHeigth;
            width=(float)(screenWidth / screenRadio);
            height=(float)width;
            imageView.setScaleType(ScaleType.FIT_XY);
        } else {
            rawWidth=bitmap.getWidth();
            rawHeight=bitmap.getHeight();
            if(rawHeight > 10 * rawWidth) {
                imageView.setScaleType(ScaleType.CENTER);
            } else {
                imageView.setScaleType(ScaleType.FIT_XY);
            }
            float radio=rawHeight / rawWidth;

            width=(float)(screenWidth / screenRadio);
            height=(float)(radio * width);
        }
        return new float[]{width, height};
    }

    /**
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        PackageManager pm=context.getPackageManager();
        try {
            PackageInfo info=pm.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch(NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;

    }

    //
    // /**
    // * @param bitmap
    // * @param left
    // * @param top
    // * @param right
    // * @param bottom
    // * @param sourceWidth
    // * @param sourceHeigth
    // * @param screenRadio
    // * @return
    // */
    // public static LayoutParams setImageViewParams(Bitmap bitmap, ImageView imageView, int left, int top, int right, int bottom,
    // float sourceWidth, float sourceHeigth, float screenRadio, boolean fitSource) {
    // float[] cons=getBitmapConfiguration(bitmap, imageView, sourceWidth, sourceHeigth, screenRadio, fitSource);
    // LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams((int)cons[0], (int)cons[1]);
    // layoutParams.gravity=Gravity.CENTER;
    // layoutParams.setMargins(left, top, right, bottom);
    // return layoutParams;
    // }

    public static void openBrowser(Context context, String urlText) {
        Intent intent=new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri url=Uri.parse(urlText);
        intent.setData(url);
        context.startActivity(intent);
    }

    public static void toggleFullScreen(Activity activity, boolean isFull) {
        hideTitleBar(activity);
        Window window=activity.getWindow();
        WindowManager.LayoutParams params=window.getAttributes();
        if(isFull) {
            params.flags|=WindowManager.LayoutParams.FLAG_FULLSCREEN;
            window.setAttributes(params);
            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            params.flags&=(~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            window.setAttributes(params);
            window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    public static void setFullScreen(Activity activity) {
        toggleFullScreen(activity, true);
    }

    public static int getStatusBarHeight(Activity activity) {
        try {
            Class<?> clazz=Class.forName("com.android.internal.R$dimen");
            Object object=clazz.newInstance();
            Field field=clazz.getField("status_bar_height");
            int dpHeight=Integer.parseInt(field.get(object).toString());
            return activity.getResources().getDimensionPixelSize(dpHeight);
        } catch(Exception e1) {
            e1.printStackTrace();
            return 0;
        }
    }
    public static void hideTitleBar(Activity activity) {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    public static void setScreenVertical(Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public static void setScreenHorizontal(Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    public static void hideSoftInput(Activity activity) {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public static void closeSoftInput(Context context, View focusingView) {
        InputMethodManager imm=(InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(focusingView.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }

    public static void adjustSoftInput(Activity activity) {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    public interface MessageFilter {

        String filter(String msg);
    }

    public static MessageFilter msgFilter;

    public static void show(final Activity activity, final String message) {
        @SuppressWarnings("unused")
		final String msg=msgFilter != null ? msgFilter.filter(message) : message;
        activity.runOnUiThread(new Runnable() {

            public void run() {
                // Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                Toast toast=ToastFactory.getToast(activity, message);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
    }
    public static void show(final Context context, final String message) {
        @SuppressWarnings("unused")
		final String msg=msgFilter != null ? msgFilter.filter(message) : message;
        MyApplication.getInstance().getTopActivity().runOnUiThread(new Runnable() {

            public void run() {
                // Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                Toast toast=ToastFactory.getToast(MyApplication.getInstance().getTopActivity(), message);
                toast.setGravity(Gravity.BOTTOM, 0, 0);
                toast.show();
            }
        });
    }
    public static void showL(final Activity activity, final String message) {
        @SuppressWarnings("unused")
		final String msg=msgFilter != null ? msgFilter.filter(message) : message;
        activity.runOnUiThread(new Runnable() {

            public void run() {
                // Toast.makeText(activity, msg, Toast.LENGTH_LONG).show();
                Toast toast=ToastFactory.getToast(activity, message);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale=context.getResources().getDisplayMetrics().density;
        return (int)(dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale=context.getResources().getDisplayMetrics().density;
        return (int)(pxValue / scale + 0.5f);
    }

    @SuppressLint("NewApi")
    public static void runActivityAnim(Activity m, boolean isEnd) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT) {
            if(isEnd) {
                m.overridePendingTransition(R.anim.activity_close_enter, R.anim.activity_close_exit);
            } else {
                m.overridePendingTransition(R.anim.activity_open_enter, R.anim.activity_open_exit);
            }
        }
    }

    public static boolean ifAddShortCut(Context context) {
        boolean isInstallShortCut=false;
        ContentResolver cr=context.getContentResolver();
        String authority="com.android.launcher2.settings";
        Uri uri=Uri.parse("content://" + authority + "/favorites?notify=true");
        Cursor c=
            cr.query(uri, new String[]{"title", "iconResource"}, "title=?", new String[]{context.getString(R.string.app_name)},
                null);
        if(null != c && c.getCount() > 0) {
            isInstallShortCut=true;
        }
        return isInstallShortCut;
    }

    public static void addShortCut(Context context) {
        Intent shortcut=new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, context.getString(R.string.app_name));
        ShortcutIconResource resource= ShortcutIconResource.fromContext(context, R.drawable.ic_launcher);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, resource);
        shortcut.putExtra("duplicate", false);
        Intent intent=new Intent(Intent.ACTION_MAIN);// ��ʶActivityΪһ������Ŀ�ʼ
        intent.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        intent.addFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setClass(context, WelcomeActivity.class);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
        context.sendBroadcast(shortcut);
    }

    /**
     * @param w
     * @param bmw
     * @param bmh
     * @return
     */
    public static int getViewHeight(int w, int bmw, int bmh) {
        return w * bmh / bmw;
    }

    /**
     * @param activity
     * @return
     */
    public static int[] getScreenSize(Activity activity) {
        int[] screens;
        // if (Constants.screenWidth > 0) {
        // return screens;
        // }
        DisplayMetrics dm=new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        screens=new int[]{dm.widthPixels, dm.heightPixels};
        return screens;
    }

    public static String Md5(String str) {
        if(str != null && !str.equals("")) {
            try {
                MessageDigest md5=MessageDigest.getInstance("MD5");
                char[] HEX={'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
                byte[] md5Byte=md5.digest(str.getBytes("UTF8"));
                StringBuffer sb=new StringBuffer();
                for(int i=0; i < md5Byte.length; i++) {
                    sb.append(HEX[(int)(md5Byte[i] & 0xff) / 16]);
                    sb.append(HEX[(int)(md5Byte[i] & 0xff) % 16]);
                }
                str=sb.toString();
            } catch(NoSuchAlgorithmException e) {

            } catch(Exception e) {
            }
        }
        return str;
    }

    public static String getImei(Context context) {
        TelephonyManager tm=(TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    public static boolean hasNetwork(Context context) {
        ConnectivityManager connectivity=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivity != null) {
            NetworkInfo info=connectivity.getActiveNetworkInfo();
            if(info != null && info.isConnected()) {
                if(info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;

    }

    /**
     * @param file
     */
    public static void installApk(Context context, File file) {
        Intent intent=new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    /**
     * @param b
     * @return
     */
    public static String byteToHexString(byte[] b) {
        StringBuffer hexString=new StringBuffer();
        for(int i=0; i < b.length; i++) {
            String hex=Integer.toHexString(b[i] & 0xFF);
            if(hex.length() == 1) {
                hex='0' + hex;
            }
            hexString.append(hex.toUpperCase());
        }
        return hexString.toString();
    }

    /**
     * @param context
     * @param str
     */
    public static void showToast(Context context, String str) {

        Toast toast=ToastFactory.getToast(context, str);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * @param is
     * @return
     * @throws IOException
     */
    public static byte[] load(InputStream is) throws IOException {
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        byte[] buffer=new byte[1024];
        int len;
        while((len=is.read(buffer)) != -1)
            baos.write(buffer, 0, len);
        baos.close();
        is.close();
        return baos.toByteArray();
    }

    /**
     * @param file
     * @return
     */
    public static byte[] getFileByte(File file) {
        if(!file.exists()) {
            return null;
        }
        try {
            FileInputStream fis=new FileInputStream(file);
            int len=fis.available();
            byte[] bytes=new byte[len];
            fis.read(bytes);
            fis.close();
            return bytes;
        } catch(Exception e) {

        }

        return null;
    }

    /**
     * @param value
     * @return
     */
    public static int getSimpleNumber(int value) {
        if(value > 30) {
            return 1 + getSimpleNumber(value / 4);
        } else {
            return 1;
        }
    }

    public static List<String> getAllSameElement2(String[] strArr1, String[] strArr2) {
        if(strArr1 == null || strArr2 == null) {
            return null;
        }
        Arrays.sort(strArr1);
        Arrays.sort(strArr2);
        List<String> list=new ArrayList<String>();
        int k=0;
        int j=0;
        while(k < strArr1.length && j < strArr2.length) {
            if(strArr1[k].compareTo(strArr2[j]) == 0) {
                if(strArr1[k].equals(strArr2[j])) {
                    list.add(strArr1[k]);
                    k++;
                    j++;
                }
                continue;
            } else if(strArr1[k].compareTo(strArr2[j]) < 0) {
                k++;
            } else {
                j++;
            }
        }
        return list;
    }

    /**
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static long getTime() {
        Date d=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String dateNowStr=sdf.format(d);
        Date d2=null;
        try {
            d2=sdf.parse(dateNowStr);
            // System.out.println(d2);
            // System.out.println(d2.getTime());
            return d2.getTime();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    // I added a generic return type to reduce the casting noise in client code
    @SuppressWarnings("unchecked")
    public static <T extends View> T get(View view, int id) {
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        if (viewHolder == null) {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }

}