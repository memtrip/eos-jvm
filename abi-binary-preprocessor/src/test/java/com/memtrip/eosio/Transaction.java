package com.memtrip.eosio;

import com.memtrip.eosio.abi.binary.*;

import java.util.List;

@Abi
public class Transaction {

    private final long expiration;
    private final int refBlockNum;
    private final long refBlockPrefix;
    private final long maxNetUsageWords;
    private final long maxCpuUsageMs;
    private final long delaySec;
    private final List<PackedAction> contextFreeActions;
    private final List<PackedAction> actions;
    private final List<String> transactionExtensions;
    private final List<String> signatures;
    private final List<String> contextFreeData;

    @TimestampCompress
    public long getExpiration() {
        return expiration;
    }

    @BlockNumCompress
    public int getRefBlockNum() {
        return refBlockNum;
    }

    @BlockPrefixCompress
    public long getRefBlockPrefix() {
        return refBlockPrefix;
    }

    @VariableUIntCompress
    public long getMaxNetUsageWords() {
        return maxNetUsageWords;
    }

    @VariableUIntCompress
    public long getMaxCpuUsageMs() {
        return maxCpuUsageMs;
    }

    @VariableUIntCompress
    public long getDelaySec() {
        return delaySec;
    }

    @CollectionCompress
    public List<PackedAction> getContextFreeActions() {
        return contextFreeActions;
    }

    @CollectionCompress
    public List<PackedAction> getActions() {
        return actions;
    }

    @StringCollectionCompress
    public List<String> getTransactionExtensions() {
        return transactionExtensions;
    }

    public List<String> getSignatures() {
        return signatures;
    }

    public List<String> getContextFreeData() {
        return contextFreeData;
    }

    public Transaction(
        long expiration,
        int refBlockNum,
        long refBlockPrefix,
        long maxNetUsageWords,
        long maxCpuUsageMs,
        long delaySec,
        List<PackedAction> contextFreeActions,
        List<PackedAction> actions,
        List<String> transactionExtensions,
        List<String> signatures,
        List<String> contextFreeData
    ) {
        this.expiration = expiration;
        this.refBlockNum = refBlockNum;
        this.refBlockPrefix = refBlockPrefix;
        this.maxNetUsageWords = maxNetUsageWords;
        this.maxCpuUsageMs = maxCpuUsageMs;
        this.delaySec = delaySec;
        this.contextFreeActions = contextFreeActions;
        this.actions = actions;
        this.transactionExtensions = transactionExtensions;
        this.signatures = signatures;
        this.contextFreeData = contextFreeData;
    }
}
