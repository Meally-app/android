package com.meally.meally.deeplinkHandler

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.meally.domain.thirdParty.ThirdPartyRepository
import com.meally.meally.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get

class DeeplinkHandlerActivity : AppCompatActivity() {

    private val thirdPartyRepository: ThirdPartyRepository = get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val uri: Uri? = intent?.data
        Log.d("Deeplink", "Handling deeplink $uri")
        if (uri != null && uri.scheme == "meally") {
            when (uri.host) {
                "strava-auth" -> handleStravaAuth(uri)
                else -> { Log.d("Deeplink", "Unregister deeplink for host: ${uri.host}") }
            }
        }
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun handleStravaAuth(uri: Uri) {
        val code = uri.getQueryParameter("code")
        val error = uri.getQueryParameter("error")

        if (code != null) {
            Log.d("StravaOAuth", "Authorization code: $code")
            CoroutineScope(Dispatchers.IO).launch {
                thirdPartyRepository.stravaCode(code)
            }
        } else if (error != null) {
            Toast.makeText(this, "Strava auth failed: $error", Toast.LENGTH_LONG).show()
        }
    }
}