package top.davidon.ironware.utils

import com.google.common.hash.Hashing
import org.apache.commons.codec.binary.Hex
import org.apache.commons.lang3.RandomStringUtils
import sun.misc.BASE64Decoder
import sun.misc.BASE64Encoder
import java.nio.charset.StandardCharsets
import java.security.Key
import java.security.spec.KeySpec
import java.util.regex.Pattern
import javax.crypto.Cipher
import javax.crypto.Mac
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

class CryptUtil {
    companion object {
        private const val ALGORITHM = "AES"

        @Throws(Exception::class)
        fun encrypt(valueToEnc: String, keyV: String, saltV: String): String? {
            val key = getKeyFromPasswd(keyV, saltV)
            val c = Cipher.getInstance(ALGORITHM)
            c.init(Cipher.ENCRYPT_MODE, key)
            val encValue = c.doFinal(valueToEnc.toByteArray())
            return BASE64Encoder().encode(encValue)
        }

        @Throws(Exception::class)
        fun decrypt(encryptedValue: String, keyV: String, saltV: String): String {
            val key = getKeyFromPasswd(keyV, saltV)
            val c = Cipher.getInstance(ALGORITHM)
            c.init(Cipher.DECRYPT_MODE, key)
            val decoderValue = BASE64Decoder().decodeBuffer(encryptedValue)
            val decValue = c.doFinal(decoderValue)
            return decValue.toString()
        }

        @Throws(Exception::class)
        private fun getKeyFromPasswd(passwd: String, salt: String): Key {
            val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
            val spec: KeySpec = PBEKeySpec(passwd.toCharArray(), salt.toByteArray(), 65536, 256)
            return SecretKeySpec(factory.generateSecret(spec).encoded, ALGORITHM)
        }

        fun encode(key: String, data: String): String? {
            return try {
                val sha256_HMAC = Mac.getInstance("HmacSHA256")
                val secretKey = SecretKeySpec(key.toByteArray(charset("UTF-8")), "HmacSHA256")
                sha256_HMAC.init(secretKey)
                Hex.encodeHexString(sha256_HMAC.doFinal(data.toByteArray(charset("UTF-8"))))
            } catch (e: Exception) {
                e.printStackTrace()
                Utils.logger.error("Failed to hash hmac.")
                System.exit(-1)
                null
            }
        }

        fun isValideHmac(key: String, data: String, hmac: String): Boolean {
            return if (hmac.equals(encode(key, data), ignoreCase = true)) {
                true
            } else {
                System.exit(-169)
                false
            }
        }

        fun getSHA512Hash(input: String?): String {
            return Hashing.sha512().hashString(input, StandardCharsets.UTF_8).toString()
        }

        fun genSalt(): String {
            val characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@!#$%&"
            val password = RandomStringUtils.random(50, characters)
            val regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@!#$%&])(?=\\S+$).{8,}$"
            val pattern = Pattern.compile(regex)
            val matcher = pattern.matcher(password)
            return if (matcher.matches()) {
                password
            } else {
                genSalt()
            }
        }
    }
}