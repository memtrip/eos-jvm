package com.memtrip.eos.core.crypto

import com.memtrip.eos.core.crypto.signature.PrivateKeySigning
import com.memtrip.eos.core.hex.DefaultHexWriter
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

        assertEquals("SIG_K1_Jxxwow19uNizFcqGAGoh6C528BvebUiMxoHsUEJWTSu3rUBGukWExUDh3G8hmw2rZwEJBECVCXaVSMEgkMNsCtQkfj36CY", signature)
    }

    @Test
    fun testSignature2() {
        val privateKey = EosPrivateKey("5Hq6wrxmXdwiSkjXF8aPGqJP4T1MwpcNendYtih5TKwZB9XabWQ")

        val privateKeySigning = PrivateKeySigning()

        val hexWriter = DefaultHexWriter()

        val signature = privateKeySigning.sign(
                hexWriter.hexToBytes("a034c6aeba9ba592e03bbdb5ba9ba59259d107000000000004454f5300000000d9201a000000000004454f530000000001000000"),
                privateKey)

        assertEquals("SIG_K1_K9icWBYwPP2Axh661xUxWkUmBTv7xnWcF9L9jwXf2bf5GpyKuNNAQG9nSGdTy9CgxiYNfJxvtB53ViTdPv12sXqMsm4Jrn", signature)
    }

    @Test
    fun testSignature3() {
        val privateKey = EosPrivateKey("5JsAhYTiPXRRShhgL4eRXMakxEdWACPF1JAFdKizeeJFX5qfL8q")

        val privateKeySigning = PrivateKeySigning()

        val hexWriter = DefaultHexWriter()

        val signature = privateKeySigning.sign(
                hexWriter.hexToBytes("5034c6aeba9ba59200118da7ba9ba592a0e001000000000004454f53000000000e746869732069732061206d656d6f"),
                privateKey)

        assertEquals("SIG_K1_K813x8ALwV1tvtmgTw8yhkHxxTYcXW98Fn8SS9K811UiyGMgJtsdVc22VEhCodtn5rpLba6NPkYetFjReKbpatggGxP7Nz", signature)
    }
}