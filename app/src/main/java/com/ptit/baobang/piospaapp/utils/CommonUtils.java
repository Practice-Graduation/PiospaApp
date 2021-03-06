package com.ptit.baobang.piospaapp.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.provider.Settings;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.ui.listener.CallBackChoosePhoto;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtils {

    private static final String TAG = "CommonUtils";
    private static final Locale locale = new Locale("vi", "VN");
    private static final Currency currency = Currency.getInstance(locale);
    private static final NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);

    private CommonUtils() {
        // This utility class is not publicly instantiable
    }
    public static String convertDateToString(Date date, String outputFormat) {
        String convertString = "";
        SimpleDateFormat df = new SimpleDateFormat(outputFormat);
        try {
            convertString = df.format(date);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return convertString;
    }
    public static void openDialogChooseImage(Activity activity, CallBackChoosePhoto callBack) {
        final CharSequence[] items = {activity.getString(R.string.camera), activity.getString(R.string.album)};
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.choose_image);
        builder.setItems(items, (dialogInterface, i) -> {
            if (items[i].equals(activity.getString(R.string.camera))) {
                callBack.onCamera();

            } else if (items[i].equals(activity.getString(R.string.album))) {
                callBack.onGallery();

            }
        });

        builder.show();
    }


    public static ProgressDialog showLoadingDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }

    @SuppressLint("all")
    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    @SuppressLint("RestrictedApi")
    public static void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e(TAG, "Unable to get shift mode field");
        } catch (IllegalAccessException e) {
            Log.e(TAG, "Unable to change value of shift mode");
        }
    }

    public static Date getDateTime(Date date, String time){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String []item = time.split(":");
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(item[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(item[1]));
        if(item.length > 2){
            calendar.set(Calendar.SECOND, Integer.parseInt(item[2]));
        }

        return calendar.getTime();
    }

    public static boolean isEmailValid(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static String formatDateAndMonth(int number){
        return String.format("%02d", number);
    }

    public static String formatToCurrency(int number){
        return numberFormat.format(number) + currency.getSymbol();
    }
    public static String formatToCurrency(BigDecimal number){
        return numberFormat.format(number) + currency.getSymbol();
    }
    public static String formatToCurrency(String number){
        Long aLong = Long.valueOf(number);
        return numberFormat.format(aLong) + currency.getSymbol();
    }

    public static Bitmap resizeImage(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float scaleWidth = ((float) 640) / width;
        float scaleHeight = ((float) 480) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        return Bitmap.createBitmap(
                bitmap, 0, 0, width, height, matrix, false);
    }
}
