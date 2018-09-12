package com.memtrip.eos.core.crypto

import org.junit.Assert.assertEquals
import org.junit.Test

class EosKeyTests {

    @Test
    fun testPublicKeyIsResolvedFromPrivateKey() {
        val privateKey = EosPrivateKey("5KQwrPbwdL6PhXujxW37FSSQZ1JiwsST4cqQzDeyXtP79zkvFD3")
        assertEquals("EOS6MRyAjQq8ud7hVNYcfnVPJqcVpscN5So8BhtHuGYqET5GDW5CV", privateKey.publicKey.toString())
    }

    @Test
    fun testPublicKeyFormat() {
        val publicKey = EosPublicKey("EOS6MRyAjQq8ud7hVNYcfnVPJqcVpscN5So8BhtHuGYqET5GDW5CV")
        assertEquals("EOS6MRyAjQq8ud7hVNYcfnVPJqcVpscN5So8BhtHuGYqET5GDW5CV", publicKey.toString())
    }
}