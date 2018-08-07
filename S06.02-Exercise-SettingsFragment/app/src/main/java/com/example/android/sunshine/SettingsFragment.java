package com.example.android.sunshine;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;
import android.util.Log;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    /**
     * Called during {@link #onCreate(Bundle)} to supply the preferences for this fragment.
     * Subclasses are expected to call {@link #setPreferenceScreen(PreferenceScreen)} either
     * directly or via helper methods such as {@link #addPreferencesFromResource(int)}.
     *
     * @param savedInstanceState If the fragment is being re-created from
     *                           a previous saved state, this is the state.
     * @param rootKey            If non-null, this preference fragment should be rooted at the
     *                           {@link PreferenceScreen} with this key.
     */
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_general);

        PreferenceScreen preferenceScreen = getPreferenceScreen();
        SharedPreferences sharedPreferences = preferenceScreen.getSharedPreferences();

        int preferenceCount = preferenceScreen.getPreferenceCount();

        for(int index = 0; index < preferenceCount; index++) {
            Preference preference = preferenceScreen.getPreference(index);
            String key = preference.getKey();

            if (preference instanceof EditTextPreference) {
                EditTextPreference editTextPreference = (EditTextPreference) preference;
                String value = sharedPreferences.getString(key, getString(R.string.pref_location_default));
                editTextPreference.setSummary(value);


            } else if (preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                String value = sharedPreferences.getString(key, getString(R.string.pref_units_label_metric));
                int indexOfValue = listPreference.findIndexOfValue(value);
                CharSequence[] entries = listPreference.getEntries();
                CharSequence entry = entries[indexOfValue];
                listPreference.setSummary(entry);

            }
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {

        Preference preference = findPreference(s);
        if (s.equals(getString(R.string.pref_location_key))) {
            String value = sharedPreferences.getString(s, getString(R.string.pref_location_default));
            EditTextPreference editTextPreference = (EditTextPreference) preference;
            editTextPreference.setSummary(value);
        } else if (s.equals(getString(R.string.pref_units_key))) {
            String value = sharedPreferences.getString(s, getString(R.string.pref_units_metric));
            ListPreference listPreference = (ListPreference) preference;
            int indexOfValue = listPreference.findIndexOfValue(value);
            CharSequence[] entries = listPreference.getEntries();
            CharSequence entry = entries[indexOfValue];
            preference.setSummary(entry);
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
}
