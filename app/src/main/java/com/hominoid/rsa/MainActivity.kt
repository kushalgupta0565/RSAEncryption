package com.hominoid.rsa

import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.security.*

class MainActivity : AppCompatActivity() {

    val ENCRYPT_TEXT_INTENT_KEY = "ENCRYPT_TEXT_INTENT_KEY"
    lateinit var btn_encrypt: Button
    lateinit var btn_decrypt: Button
    lateinit var btn_send_encrypt: Button
    lateinit var et_plain_text: EditText
    lateinit var tv_encrypt: TextView
    var isTextEncrypted = false
    var encryptedText: String? = null
    var rsaUtils: RSAUtils = RSAUtils()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeViews()

        btn_encrypt.setOnClickListener {
            tv_encrypt.setText("")
            val textEntered = et_plain_text.text.toString()
            if (TextUtils.isEmpty(textEntered)) {
                Toast.makeText(this, "Enter text to encrypt", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                encryptedText = rsaUtils.encrypt(textEntered)
                if (encryptedText != null) {
                    tv_encrypt.setText(encryptedText)
                    isTextEncrypted = true
                } else
                    tv_encrypt.setText("Encryption failed")
            }
        }

        btn_decrypt.setOnClickListener {
            val textShown = tv_encrypt.text.toString()
            if (TextUtils.isEmpty(textShown)) {
                Toast.makeText(this, "Nothing to decrypt", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                if (isTextEncrypted) {
                    val decryptStr = rsaUtils.decrypt(textShown)
                    tv_encrypt.setText(decryptStr)
                    isTextEncrypted = false
                }
            }
        }

        btn_send_encrypt.setOnClickListener {
            if (isTextEncrypted && encryptedText != null) {
                val launchIntent = packageManager.getLaunchIntentForPackage("com.hominoid.rsa_2")
                launchIntent?.putExtra(ENCRYPT_TEXT_INTENT_KEY, encryptedText)
                launchIntent?.let { startActivity(it) }
            } else {
                Toast.makeText(this, "No encrypted data found", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initializeViews() {
        btn_encrypt = findViewById(R.id.btn_encrypt)
        btn_decrypt = findViewById(R.id.btn_decrypt)
        btn_send_encrypt = findViewById(R.id.btn_send_encrypt)
        et_plain_text = findViewById(R.id.et_plain_text)
        tv_encrypt = findViewById(R.id.tv_encrypt)
    }
}