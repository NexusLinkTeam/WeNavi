package com.nexuslink.wenavi.ui;

import android.content.Intent;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nexuslink.wenavi.R;
import com.nexuslink.wenavi.base.BaseActivity;
import com.nexuslink.wenavi.ui.login.AuthActivity;
import com.nexuslink.wenavi.util.ActivityCollector;

public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: 17-9-3 改成Fragment
        addPreferencesFromResource(R.xml.preference);
        findPreference("logout").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                ActivityCollector.finishAll();
                Intent intent = new Intent(SettingsActivity.this, AuthActivity.class);
                startActivity(intent);
                finish();
                return true;
            }
        });
    }
}
