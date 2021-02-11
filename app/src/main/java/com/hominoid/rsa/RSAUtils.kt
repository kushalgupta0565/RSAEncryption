package com.hominoid.rsa

import android.util.Base64
import java.security.KeyFactory
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher

class RSAUtils {

    val ALGORITHM = "RSA"
    val PUBLIC_KEY = "" // Enter Public key here
    val PRIVATE_KEY = "" // Enter Private key here

    fun encrypt(text: String): String? {
        return encryptRSA(text)?.let { byteArrayToString(it) }
    }

    fun decrypt(text: String): String {
        return decryptRSA(stringToByteArray(text))
    }

    private fun encryptRSA(text: String): ByteArray? {
        var cipherText: ByteArray? = null
        try {
            // generate publicKey instance
            val byteKey = stringToByteArray(PUBLIC_KEY)
            val X509publicKey = X509EncodedKeySpec(byteKey)
            val kf: KeyFactory = KeyFactory.getInstance(ALGORITHM)
            val publicKey =  kf.generatePublic(X509publicKey)
            // get an RSA cipher object and print the provider
            val cipher: Cipher = Cipher.getInstance(ALGORITHM)
            // encrypt the plain text using the public key
            cipher.init(Cipher.ENCRYPT_MODE, publicKey)
            cipherText = cipher.doFinal(text.toByteArray())
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return cipherText
    }

    private fun decryptRSA(text: ByteArray): String {
        var dectyptedText: ByteArray? = null
        try {
            // generate privateKey instance
            val byteKey = stringToByteArray(PRIVATE_KEY)
            val keySpec = PKCS8EncodedKeySpec(byteKey)
            val kf: KeyFactory = KeyFactory.getInstance(ALGORITHM)
            val privateKey = kf.generatePrivate(keySpec)
            // get an RSA cipher object and print the provider
            val cipher = Cipher.getInstance(ALGORITHM)
            // decrypt the text using the private key
            cipher.init(Cipher.DECRYPT_MODE, privateKey)
            dectyptedText = cipher.doFinal(text)
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
        }
        return String(dectyptedText!!)
    }

    private fun stringToByteArray(text: String): ByteArray {
        return Base64.decode(text, Base64.DEFAULT)
    }

    private fun byteArrayToString(byteArray: ByteArray): String {
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }
}