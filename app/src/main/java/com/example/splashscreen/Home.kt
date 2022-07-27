package com.example.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import retrofit2.Call
import android.widget.Toast
import com.example.splashscreen.databinding.ActivityHomeBinding
import com.google.zxing.integration.android.IntentIntegrator



class Home : AppCompatActivity() {
    private var _binding: ActivityHomeBinding? = null
    private val binding get() = _binding!!

    private var nim = ""
    companion object {
        const val NIM = "nim"
        const val NAMA = "nama"
        const val PASSWORD = "password"
        const val SCAN_MEMBER = 1
        const val RESULT_SCAN = "result_scan"

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        _binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        nim = intent.getStringExtra(NIM) ?: ""

        val nama = intent.getStringExtra(NAMA)
        val password = intent.getStringExtra(PASSWORD)

        Toast.makeText(this, "NIM : $nim\nnama : $nama\npassword : $password", Toast.LENGTH_LONG).show()
        Toast.makeText(
            this,
            "NIM : $nim\nnama : $nama\npassword : $password",
            Toast.LENGTH_LONG
        ).show()

        binding.btnAbsen.setOnClickListener {
            val integrator = IntentIntegrator(this)
            integrator.captureActivity = ScanViewActivity::class.java
            integrator.setOrientationLocked(false)
            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES)
            integrator.setPrompt("Scanning...")
            integrator.initiateScan()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (requestCode == IntentIntegrator.REQUEST_CODE) {
            if (result != null) {
                if (result.contents == null) {
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show()

                    val client =
                        ApiConfig.getApiService().postScan(nim = nim, token = result.contents)
                    client.enqueue(object : retrofit2.Callback<Response> {
                        override fun onResponse(
                            call: Call<Response>,
                            response: retrofit2.Response<Response>
                        ) {
                            Toast.makeText(
                                this@Home,
                                "Absen Berhasil",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        override fun onFailure(call: retrofit2.Call<Response>, t: Throwable) {
                            Toast.makeText(
                                this@Home,
                                "Absen Gagal",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}


