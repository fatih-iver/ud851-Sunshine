package com.example.android.sunshine.sync;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.icu.text.TimeZoneNames;
import android.net.Uri;
import android.util.Log;

import com.example.android.sunshine.MainActivity;
import com.example.android.sunshine.data.WeatherContract;
import com.example.android.sunshine.utilities.NetworkUtils;
import com.example.android.sunshine.utilities.OpenWeatherJsonUtils;
import java.net.URL;

public class SunshineSyncTask {

    synchronized public static void syncWeather(Context context) {

        try {
            URL weatherRequestUrl = NetworkUtils.getUrl(context);
            String jsonWeatherResponse = NetworkUtils.getResponseFromHttpUrl(weatherRequestUrl);
            ContentValues[] contentValues = OpenWeatherJsonUtils.getWeatherContentValuesFromJson(context, jsonWeatherResponse);
            ContentResolver contentResolver = context.getContentResolver();
            Uri CONTENT_URI = WeatherContract.WeatherEntry.CONTENT_URI;
            contentResolver.delete(CONTENT_URI, null,null);
            contentResolver.bulkInsert(CONTENT_URI, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
