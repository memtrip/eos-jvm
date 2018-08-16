package com.memtrip.eos.abi.writer.preprocessor.template;

import com.memtrip.eos.abi.writer.ByteWriter;
import com.memtrip.eos.abi.writer.Squishable;
import com.memtrip.eos.abi.writer.preprocessor.model.PackedTransactionAuthorization;

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
