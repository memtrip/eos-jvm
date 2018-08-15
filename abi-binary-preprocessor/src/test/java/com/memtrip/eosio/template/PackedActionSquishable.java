package com.memtrip.eosio.template;

import com.memtrip.eosio.PackedAction;

import com.memtrip.eosio.abi.binary.ByteWriter;
import com.memtrip.eosio.abi.binary.Squishable;

public class PackedActionSquishable implements Squishable<PackedAction> {

    private final AbiBinaryGen abiBinaryGen;

    PackedActionSquishable(AbiBinaryGen abiBinaryGen) {
        this.abiBinaryGen = abiBinaryGen;
    }

    @Override
    public void squish(PackedAction packedAction, ByteWriter byteWriter) {
        byteWriter.putAccountName(packedAction.account());
        byteWriter.putName(packedAction.name());

        abiBinaryGen.compressCollectionPackedTransactionAuthorization(packedAction.authorization(), byteWriter);

        byteWriter.putData(packedAction.data());
    }
}
