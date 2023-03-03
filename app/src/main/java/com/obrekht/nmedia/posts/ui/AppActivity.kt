package com.obrekht.nmedia.posts.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.obrekht.nmedia.R

class AppActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)

        intent?.let {
            if (it.action == Intent.ACTION_SEND) {
                val text = it.getStringExtra(Intent.EXTRA_TEXT)
                if (text.isNullOrBlank()) {
                    return@let
                }

                val action = FeedFragmentDirections.actionOpenPostEditor().apply {
                    this.text = text
                }

                findNavController(R.id.nav_host_fragment).navigate(action)
            }
        }
    }
}
