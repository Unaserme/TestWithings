package com.synaone.testwithings.presentation.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.MultiSelectListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.synaone.testwithings.presentation.R
import com.synaone.testwithings.presentation.common.extensions.setSummaryFromValues
import com.synaone.testwithings.presentation.main.MainActivity

class SettingsFragment: PreferenceFragmentCompat() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as? MainActivity)?.let { mainActivity ->
            mainActivity.supportActionBar?.title = "Settings"
        }

        (findPreference("color") as? MultiSelectListPreference)?.let { columnSelect ->
            columnSelect.onPreferenceChangeListener =
                Preference.OnPreferenceChangeListener { _, newValue ->
                    (newValue as? Set<*>)?.let { setValue ->
                        columnSelect.setSummaryFromValues(setValue)
                    }
                    true
                }
            columnSelect.setSummaryFromValues(columnSelect.values)
        }

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreatePreferences(
        savedInstanceState: Bundle?,
        rootKey: String?
    ) {
        setPreferencesFromResource(R.xml.settings, rootKey)
    }
}