package com.memtrip.eosio.template;

import com.memtrip.eosio.PackedAction;
import com.memtrip.eosio.PackedTransactionAuthorization;
import com.memtrip.eosio.Transaction;
import com.memtrip.eosio.abi.binary.ByteWriter;
import com.memtrip.eosio.abi.binary.compression.CompressionFactory;
import com.memtrip.eosio.abi.binary.compression.CompressionType;

import com.memtrip.eosio.abi.binary.HexWriter;
import com.memtrip.eosio.abi.binary.writer.AccountNameWriter;
import com.memtrip.eosio.abi.binary.writer.DefaultByteWriter;

import com.memtrip.eosio.abi.binary.writer.DefaultHexWriter;
import com.memtrip.eosio.abi.binary.writer.NameWriter;

import java.util.List;

public class AbiBinaryGen {

    private final ByteWriter byteWriter;
    private final HexWriter hexWriter;
    private final CompressionType compressionType;

    private final TransactionSquishable transactionSquishable;
    private final PackedActionSquishable packedActionSquishable;
    private final PackedTransactionAuthorizationSquishable packedTransactionAuthorizationSquishable;

    public AbiBinaryGen(CompressionType compressionType) {
        this(
            new DefaultByteWriter(
                512,
                new NameWriter(),
                new AccountNameWriter(),
                new DefaultHexWriter()),
            new DefaultHexWriter(),
            compressionType
        );
    }

    public AbiBinaryGen(ByteWriter byteWriter, HexWriter hexWriter, CompressionType compressionType) {
        this.byteWriter = byteWriter;
        this.hexWriter = hexWriter;
        this.compressionType = compressionType;

        transactionSquishable = new TransactionSquishable(this);
        packedActionSquishable = new PackedActionSquishable(this);
        packedTransactionAuthorizationSquishable = new PackedTransactionAuthorizationSquishable(this);
    }

    public String pack() {
        byte[] compressedBytes = new CompressionFactory(compressionType)
            .create()
            .compress(byteWriter.toBytes());
        return hexWriter.bytesToHex(compressedBytes, 0, compressedBytes.length, null);
    }

    public AbiBinaryGen compressTransaction(Transaction transaction) {
        transactionSquishable.squish(transaction, byteWriter);
        return this;
    }

    public AbiBinaryGen compressCollectionTransaction(
        List<Transaction> transactionList, ByteWriter byteWriter) {
        byteWriter.putVariableUInt(transactionList.size());
        for (Transaction transaction : transactionList) {
            transactionSquishable.squish(transaction, byteWriter);
        }
        return this;
    }

    public AbiBinaryGen compressPackedAction(PackedAction packedaction) {
        packedActionSquishable.squish(packedaction, byteWriter);
        return this;
    }

    public AbiBinaryGen compressCollectionPackedAction(
        List<PackedAction> packedactionList, ByteWriter byteWriter) {
        byteWriter.putVariableUInt(packedactionList.size());
        for (PackedAction packedaction : packedactionList) {
            packedActionSquishable.squish(packedaction, byteWriter);
        }
        return this;
    }

    public AbiBinaryGen compressPackedTransactionAuthorization(
        PackedTransactionAuthorization packedtransactionauthorization) {
        packedTransactionAuthorizationSquishable
            .squish(packedtransactionauthorization, byteWriter);
        return this;
    }

    public AbiBinaryGen compressCollectionPackedTransactionAuthorization(
        List<PackedTransactionAuthorization> packedtransactionauthorizationList,
        ByteWriter byteWriter) {
        byteWriter.putVariableUInt(packedtransactionauthorizationList.size());
        for (PackedTransactionAuthorization packedtransactionauthorization :
            packedtransactionauthorizationList) {
            packedTransactionAuthorizationSquishable
                .squish(packedtransactionauthorization, byteWriter);
        }
        return this;
    }
}
