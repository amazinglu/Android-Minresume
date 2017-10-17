package com.example.guojing.my_minresume.util;

/**
 * Created by AmazingLu on 8/26/17.
 */

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * user class uses for check and request permission
 * */

public class PermissionUtil {

    public static final int REQ_CODE_WRITE_EXTERNAL_STORAGE = 200;

    public static boolean checkPermission(@NonNull Context context,
                                          @NonNull String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M
                || ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestPermissions(@NonNull Activity activity,
                                          @NonNull String[] permissions,
                                          int reqCode) {
        ActivityCompat.requestPermissions(activity, permissions, reqCode);
    }

    public static void requestReadExternalStoragePermission(@NonNull Activity activity) {
        requestPermissions(activity,
                new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                REQ_CODE_WRITE_EXTERNAL_STORAGE);
    }
}