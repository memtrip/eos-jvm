package com.memtrip.eosio.template;

import com.memtrip.eosio.PackedTransactionAuthorization;
import com.memtrip.eosio.abi.binary.ByteWriter;
import com.memtrip.eosio.abi.binary.Squishable;

public class PackedTransactionAuthorizationSquishable implements Squishable<PackedTransactionAuthorization> {

    private final AbiBinaryGen abiBinaryGen;

    PackedTransactionAuthorizationSquishable(AbiBinaryGen abiBinaryGen) {
        this.abiBinaryGen = abiBinaryGen;
    }

    @Override
    public void squish(PackedTransactionAuthorization packedTransactionAuthorization, ByteWriter byteWriter) {
        byteWriter.putAccountName(packedTransactionAuthorization.actor());
        byteWriter.putName(packedTransactionAuthorization.permission());
    }
}
