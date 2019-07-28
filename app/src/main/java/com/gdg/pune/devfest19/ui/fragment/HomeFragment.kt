package com.gdg.pune.devfest19.ui.fragment


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.gdg.pune.devfest19.BuildConfig
import com.gdg.pune.devfest19.R
import com.gdg.pune.devfest19.ui.AnnouncementListActivity
import com.gdg.pune.devfest19.ui.discuss.DiscussActivity
import com.google.android.material.button.MaterialButton
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings


class HomeFragment : Fragment(), View.OnClickListener {

    private lateinit var mApplyButton: MaterialButton
    private lateinit var mEventDateView: TextView
    private lateinit var mDiscuss: CardView
    private lateinit var mAnnouncement: CardView
    private lateinit var mRemoteConfig: FirebaseRemoteConfig

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        mApplyButton = view.findViewById(R.id.buttonApply)
        mEventDateView = view.findViewById(R.id.textView_event_date)
        mDiscuss = view.findViewById(R.id.cardDiscuss)
        mAnnouncement = view.findViewById(R.id.cardAnnouncements)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initRemoteConfig()
        fetchRemoteConfig()

        mAnnouncement.setOnClickListener(this)
        mDiscuss.setOnClickListener(this)
        mApplyButton.setOnClickListener(this)
    }

    private fun initRemoteConfig() {
        mRemoteConfig = FirebaseRemoteConfig.getInstance()

        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setDeveloperModeEnabled(BuildConfig.DEBUG)
            .setMinimumFetchIntervalInSeconds(4200)
            .build()
        mRemoteConfig.setConfigSettings(configSettings)
        mRemoteConfig.setDefaults(R.xml.remote_config_defaults)
    }

    private fun fetchRemoteConfig() {
        // [START fetch_config_with_callback]
        mRemoteConfig.fetchAndActivate()
            .addOnCompleteListener {
                updateUi()
            }
        // [END fetch_config_with_callback]
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            mAnnouncement.id -> {
                startActivity(Intent(activity, AnnouncementListActivity::class.java))
            }
            mDiscuss.id -> {
                startActivity(Intent(activity, DiscussActivity::class.java))
            }
            mApplyButton.id -> {
                val url = mRemoteConfig.getString(APPLY_URL)
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(url)
                }
                startActivity(intent)
            }
        }
    }

    private fun updateUi() {
        // [START get_config_values]
        mEventDateView.text = mRemoteConfig.getString(EVENT_DATE_KEY)
        if (mRemoteConfig.getBoolean(IS_APPLY_BUTTON_AVAILABLE_KEY)) {
            mApplyButton.visibility = View.VISIBLE
        } else {
            mApplyButton.visibility = View.GONE
        }
        // [END get_config_values]

    }

    companion object {

        private const val TAG = "MainActivity"

        // Remote Config keys
        private const val EVENT_DATE_KEY = "event_date"
        private const val IS_APPLY_BUTTON_AVAILABLE_KEY = "apply_available"
        private const val APPLY_URL = "apply_url"
    }
}
