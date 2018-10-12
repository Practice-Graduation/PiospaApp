package com.ptit.baobang.piospaapp.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.ui.listener.CallBackChoosePhoto;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

public class CommonUtils {

    private static final String TAG = "CommonUtils";
    private static final Locale locale = new Locale("vi", "VN");
    private static final Currency currency = Currency.getInstance(locale);
    private static final NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);

    private CommonUtils() {
        // This utility class is not publicly instantiable
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

    public static String formatDateAndMonth(int number) {
        return String.format("%02d", number);
    }

    public static String formatToCurrency(int number) {
        return numberFormat.format(number) + currency.getSymbol();
    }

    public static String formatToCurrency(BigDecimal number) {
        return numberFormat.format(number) + currency.getSymbol();
    }

    public static String formatToCurrency(String number) {
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

    public static void setUpStackMainScreen(Context context) {

        int current = android.os.Process.myPid();

        if (current == SharedPreferenceUtils.getProcessID(context)) {
            int count = SharedPreferenceUtils.getCount(context);
            SharedPreferenceUtils.saveCount(context, count + 1);
        } else {
            SharedPreferenceUtils.saveCurrentProcessID(context);
            SharedPreferenceUtils.saveCount(context, 1);
        }

    }

    public static void loadImage(ImageView imageView, String url) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.paceholder)
                .error(R.drawable.error);
        Glide.with(imageView.getContext())
                .load(url)
                .apply(options)
                .into(imageView);
    }
}
