package com.memtrip.eos.core.crypto

import com.memtrip.eos.core.crypto.signature.PrivateKeySigning
import com.memtrip.eos.core.hex.DefaultHexWriter
import com.memtrip.eos.core.hex.HexWriter
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class EosKeyTests {

    @Test
    fun testPublicKeyIsResolvedFromPrivateKey() {
        val privateKey = EosPrivateKey("5KQwrPbwdL6PhXujxW37FSSQZ1JiwsST4cqQzDeyXtP79zkvFD3")
        assertEquals("EOS6MRyAjQq8ud7hVNYcfnVPJqcVpscN5So8BhtHuGYqET5GDW5CV", privateKey.publicKey.toString())
    }

    @Test
    fun testGenerateEosPrivateKey() {
        val privateKey = EosPrivateKey()
        val publicKey = privateKey.publicKey
        assertNotNull(privateKey.toString())
        assertNotNull(publicKey.toString())
    }

    @Test
    fun testCreateEosPrivateKeyFromBytes() {
        val existingPrivateKey = EosPrivateKey("5KQwrPbwdL6PhXujxW37FSSQZ1JiwsST4cqQzDeyXtP79zkvFD3")
        val bytes = existingPrivateKey.bytes
        val privateKey = EosPrivateKey(bytes)
        assertNotNull(privateKey.toString())
        assertEquals(privateKey.toString(), "5KQwrPbwdL6PhXujxW37FSSQZ1JiwsST4cqQzDeyXtP79zkvFD3")
    }

    @Test
    fun testPublicKeyFormat() {
        val publicKey = EosPublicKey("EOS6MRyAjQq8ud7hVNYcfnVPJqcVpscN5So8BhtHuGYqET5GDW5CV")
        assertEquals("EOS6MRyAjQq8ud7hVNYcfnVPJqcVpscN5So8BhtHuGYqET5GDW5CV", publicKey.toString())
        val publicKeyFromBytes = EosPublicKey(publicKey.bytes)
        assertEquals(publicKeyFromBytes.toString(), publicKey.toString())
    }

    @Test
    fun testSignature() {
        val privateKey = EosPrivateKey("5KQwrPbwdL6PhXujxW37FSSQZ1JiwsST4cqQzDeyXtP79zkvFD3")

        val privateKeySigning = PrivateKeySigning()

        val hexWriter = DefaultHexWriter()

        val signature = privateKeySigning.sign(
            hexWriter.hexToBytes("a034c6aeba9ba592e03bbdb5ba9ba5920010000000000000"),
            privateKey)

        assertEquals("SIG_K1_KRGhdMZURkr1gE7TFuabMKwbNjLixgR5Q6F3CyVw1dMtJwrXRGZ5WweYXyyZJcmDkcWAQAaswssd5US9WVYMSD2TVQkUNW", signature)
    }
}