package com.obrekht.nmedia.posts

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.obrekht.nmedia.databinding.ActivityNewPostBinding

class AddEditPostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewPostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent?.let {
            binding.inputField.setText(it.getStringExtra(Intent.EXTRA_TEXT))
        }

        binding.inputField.requestFocus()
        binding.buttonSave.setOnClickListener {
            val text = binding.inputField.text.toString()
            if (text.isBlank()) {
                setResult(Activity.RESULT_CANCELED)
            } else {
                val intent = Intent().apply {
                    putExtra(Intent.EXTRA_TEXT, text)
                }
                setResult(Activity.RESULT_OK, intent)
            }
            finish()
        }
    }
}