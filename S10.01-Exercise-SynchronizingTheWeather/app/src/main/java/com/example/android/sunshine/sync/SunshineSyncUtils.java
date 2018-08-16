package com.example.android.sunshine.sync;

import android.content.Context;
import android.content.Intent;

import com.example.android.sunshine.utilities.NetworkUtils;

import java.net.URL;

public class SunshineSyncUtils {

    public static void immediateSync(Context context) {
        context.startService(new Intent(context, SunshineSyncIntentService.class));
    }
}
