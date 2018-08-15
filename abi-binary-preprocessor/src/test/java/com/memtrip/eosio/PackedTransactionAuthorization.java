package com.memtrip.eosio;

import com.memtrip.eosio.abi.binary.Abi;
import com.memtrip.eosio.abi.binary.AccountNameCompress;

@Abi
public class PackedTransactionAuthorization {

    private final String actor;
    private final String permission;

    @AccountNameCompress
    public String actor() {
        return actor;
    }

    @AccountNameCompress
    public String permission() {
        return permission;
    }

    public PackedTransactionAuthorization(String actor, String permission) {
        this.actor = actor;
        this.permission = permission;
    }
}
