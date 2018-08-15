package com.memtrip.eosio.template;

import com.memtrip.eosio.Transaction;
import com.memtrip.eosio.abi.binary.ByteWriter;
import com.memtrip.eosio.abi.binary.Squishable;

public class TransactionSquishable implements Squishable<Transaction> {

    private final AbiBinaryGen abiBinaryGen;

    TransactionSquishable(AbiBinaryGen abiBinaryGen) {
        this.abiBinaryGen = abiBinaryGen;
    }

    @Override
    public void squish(Transaction transaction, ByteWriter byteWriter) {
        byteWriter.putInt((int)(transaction.getExpiration() / 1000));
        byteWriter.putBlockNum(transaction.getRefBlockNum());
        byteWriter.putBlockPrefix(transaction.getRefBlockPrefix());
        byteWriter.putVariableUInt(transaction.getMaxNetUsageWords());
        byteWriter.putVariableUInt(transaction.getMaxCpuUsageMs());
        byteWriter.putVariableUInt(transaction.getDelaySec());

        abiBinaryGen.compressCollectionPackedAction(
            transaction.getContextFreeActions(),
            byteWriter);

        abiBinaryGen.compressCollectionPackedAction(
            transaction.getActions(),
            byteWriter);

        byteWriter.putStringCollection(transaction.getTransactionExtensions());
    }
}
