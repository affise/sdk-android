package com.affise.attribution.utils

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

class EncryptedSharedPreferences(context: Context, preferencesName: String) : SharedPreferences {

    private val preferences: SharedPreferences = context.getSharedPreferences(
        preferencesName, Context.MODE_PRIVATE
    )

    private val secretKey: SecretKey = getOrCreateSecretKey()

    /**
     * Get SecretKey or generate new if aliases in KeyStore not contains affise alias
     */
    private fun getOrCreateSecretKey(): SecretKey {
        //Get KeyStore
        val keyStore = KeyStore.getInstance("AndroidKeyStore").apply {
            load(null)
        }

        //Check version
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //Get all aliases in KeyStore
            val alias = keyStore.aliases().toList()

            //Check affise alias in KeyStore aliases
            if (!alias.contains(ALIAS_NAME)) {
                //Create KeyGenerator
                val keyGenerator = KeyGenerator.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES,
                    "AndroidKeyStore"
                )

                //Init KeyGenerator parameters
                keyGenerator.init(
                    KeyGenParameterSpec.Builder(
                        ALIAS_NAME,
                        KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                    )
                        .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                        .setRandomizedEncryptionRequired(false)
                        .build()
                )

                //Generate new key
                keyGenerator.generateKey()
            }
        } else {
            throw RuntimeException("EncryptedSharedPreferences support only Android 6.0+")
        }

        return keyStore.getKey(ALIAS_NAME, null) as SecretKey
    }

    override fun getAll(): MutableMap<String, *> {
        //Get all preferences values
        val encryptedMap = preferences.all

        //Create map of decrypted values
        val decryptedMap = HashMap<String, Any?>(encryptedMap.size)

        //For all data in preferences values
        for ((key, cipherText) in encryptedMap) {
            try {
                //Get decrypted StringSet or null if value is not StringSet
                val stringSet = getDecryptedStringSet(cipherText)

                if (stringSet != null) {
                    //Add StringSet to map of decrypted values
                    decryptedMap[key] = stringSet
                } else {
                    //Add string to map of decrypted values
                    decryptedMap[key] = decrypt(cipherText.toString())
                }
            } catch (e: Exception) {
                //Add value to map of decrypted values
                decryptedMap[key] = cipherText.toString()
            }
        }

        return decryptedMap
    }

    /**
     * Generate decrypted StringSet from [cipherText]
     */
    private fun getDecryptedStringSet(cipherText: Any?): Set<String?>? {
        //Check for null
        cipherText ?: return null

        //Check for Set
        val encryptedSet = cipherText as? Set<*>? ?: return null

        //Create decrypted StringSet
        val decryptedSet = java.util.HashSet<String?>()

        //Check and decrypt all values
        for (value in encryptedSet) {
            (value as? String)
                ?.let {
                    decryptedSet.add(decrypt(it))
                }
                ?: return null
        }

        return decryptedSet
    }

    override fun getString(
        key: String?, defValue: String?
    ): String? = decrypt(preferences.getString(key, defValue))

    override fun getStringSet(
        key: String?, defValues: MutableSet<String>?
    ): MutableSet<String>? = preferences.getStringSet(key, defValues)
        ?.let { encryptSet ->
            //Decrypt value from preferences
            HashSet<String>(encryptSet.size).apply {
                for (value in encryptSet) {
                    decrypt(value)?.let {
                        add(it)
                    }
                }
            }
        }

    override fun getInt(key: String?, defValue: Int): Int = decrypt(
        //Decrypt value from preferences
        preferences.getString(key, null)
    )?.toIntOrNull() ?: defValue

    override fun getLong(key: String?, defValue: Long): Long = decrypt(
        //Decrypt value from preferences
        preferences.getString(key, null)
    )?.toLongOrNull() ?: defValue

    override fun getFloat(key: String?, defValue: Float): Float = decrypt(
        //Decrypt value from preferences
        preferences.getString(key, null)
    )?.toFloatOrNull() ?: defValue

    override fun getBoolean(key: String?, defValue: Boolean): Boolean = decrypt(
        //Decrypt value from preferences
        preferences.getString(key, null)
    )?.toBooleanStrictOrNull() ?: defValue

    override fun contains(key: String?): Boolean = false

    override fun edit(): SharedPreferences.Editor = Editor()

    override fun registerOnSharedPreferenceChangeListener(
        listener: SharedPreferences.OnSharedPreferenceChangeListener?
    ) {
    }

    override fun unregisterOnSharedPreferenceChangeListener(
        listener: SharedPreferences.OnSharedPreferenceChangeListener?
    ) {
    }

    /**
     * Generate Cipher object with [encryptMode]
     */
    private fun getCipher(
        encryptMode: Int
    ) = Cipher.getInstance("AES/CBC/PKCS7Padding").apply {
        init(encryptMode, secretKey, IvParameterSpec(ByteArray(16)))
    }

    /**
     * Encrypted [value]
     */
    private fun encrypt(value: String?): String? = try {
        Base64.encodeToString(
            getCipher(Cipher.ENCRYPT_MODE).doFinal(value?.toByteArray()),
            Base64.NO_WRAP
        )
    } catch (throwable: Throwable) {
        null
    }

    /**
     * Decrypted [value]
     */
    private fun decrypt(value: String?): String? = try {
        String(
            getCipher(Cipher.DECRYPT_MODE).doFinal(Base64.decode(value, Base64.NO_WRAP)),
            charset("UTF-8")
        )
    } catch (throwable: Throwable) {
        null
    }

    inner class Editor internal constructor() : SharedPreferences.Editor {

        private var editor: SharedPreferences.Editor = preferences.edit()

        override fun putString(s: String, s1: String?): SharedPreferences.Editor = this.apply {
            //Encrypted and put value to preferences
            editor.putString(s, encrypt(s1)).apply()
        }

        override fun putStringSet(
            s: String, set: Set<String>?
        ): SharedPreferences.Editor = this.apply {
            //Encrypted and put value to preferences
            set?.map { encrypt(it) }
                ?.toSet()
                ?.let {
                    editor.putStringSet(s, it)
                }
        }

        override fun putInt(s: String, i: Int): SharedPreferences.Editor = this.apply {
            //Encrypted and put value to preferences
            editor.putString(s, encrypt(i.toString())).apply()
        }

        override fun putLong(s: String, l: Long): SharedPreferences.Editor = this.apply {
            //Encrypted and put value to preferences
            editor.putString(s, encrypt(l.toString())).apply()
        }

        override fun putFloat(s: String, v: Float): SharedPreferences.Editor = this.apply {
            //Encrypted and put value to preferences
            editor.putString(s, encrypt(v.toString())).apply()
        }

        override fun putBoolean(s: String, b: Boolean): SharedPreferences.Editor = this.apply {
            //Encrypted and put value to preferences
            val encryptedData = encrypt(b.toString())
            editor.putString(s, encryptedData).apply()
        }

        override fun remove(s: String): SharedPreferences.Editor = this.apply {
            editor.remove(s)
        }

        override fun clear(): SharedPreferences.Editor = this.apply {
            editor.clear()
        }

        override fun commit(): Boolean = editor.commit()

        override fun apply() {
            editor.apply()
        }
    }

    companion object {
        private const val ALIAS_NAME = "_affise_preferences_key_name_"
    }
}