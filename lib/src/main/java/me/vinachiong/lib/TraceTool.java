package me.vinachiong.lib;

import android.os.Build;
import android.os.Debug;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author changweiliang@kungeek.com
 * @version v1.2.0
 */
public final class TraceTool {

    private static final int SAMPLE_INTERVAL = 30_000;

    public static void startTrace() {
        DateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss", Locale.getDefault());
        String logDate = dateFormat.format(new Date());
        // Applies the date and time to the name of the trace log.
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            Debug.startMethodTracingSampling("sample-" + logDate, 0, SAMPLE_INTERVAL);
        } else {
            Debug.startMethodTracing("sample-$logDate");
        }

    }

    public static void stopTrace() {
        Debug.stopMethodTracing();
    }
}