package com.memtrip.eos.abi.writer.preprocessor.template;

import com.memtrip.eos.abi.writer.ByteWriter;
import com.memtrip.eos.abi.writer.Squishable;
import com.memtrip.eos.abi.writer.preprocessor.model.PackedAction;

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
