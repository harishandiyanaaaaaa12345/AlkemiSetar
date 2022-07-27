package com.example.splashscreen
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope

import com.example.splashscreen.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivIcon.alpha = 0f

        binding.ivIcon.animate()?.setDuration(1500)?.alpha(1f)?.withEndAction {
            lifecycleScope.launch(Dispatchers.Main) {
                delay(1000) // wait for 1 second
                val intent = Intent(this@MainActivity, Login::class.java)
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                finish()
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}