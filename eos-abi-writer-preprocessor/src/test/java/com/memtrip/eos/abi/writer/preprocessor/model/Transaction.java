package com.memtrip.eos.abi.writer.preprocessor.model;

import com.memtrip.eos.abi.writer.Abi;
import com.memtrip.eos.abi.writer.AssetCompress;
import com.memtrip.eos.abi.writer.BlockNumCompress;
import com.memtrip.eos.abi.writer.BlockPrefixCompress;
import com.memtrip.eos.abi.writer.ChainIdCompress;
import com.memtrip.eos.abi.writer.ChildCompress;
import com.memtrip.eos.abi.writer.CollectionCompress;
import com.memtrip.eos.abi.writer.HexCollectionCompress;
import com.memtrip.eos.abi.writer.PublicKeyCompress;
import com.memtrip.eos.abi.writer.StringCollectionCompress;
import com.memtrip.eos.abi.writer.TimestampCompress;
import com.memtrip.eos.abi.writer.VariableUIntCompress;

import java.util.List;

@Abi
public class Transaction {

    private final long expiration;
    private final int refBlockNum;
    private final long refBlockPrefix;
    private final long maxNetUsageWords;
    private final long maxCpuUsageMs;
    private final long delaySec;
    private final String publicKey;
    private final String asset;
    private final String chainId;
    private final PackedAction singleAction;
    private final List<String> hexCollection;
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

    @PublicKeyCompress
    public String getPublicKey() {
        return publicKey;
    }

    @AssetCompress
    public String getAsset() {
        return asset;
    }

    @ChainIdCompress
    public String getChainId() {
        return chainId;
    }

    @ChildCompress
    public PackedAction getSingleAction() {
        return singleAction;
    }

    @HexCollectionCompress
    public List<String> getHexCollection() {
        return hexCollection;
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
        String publicKey,
        String asset,
        String chainId,
        PackedAction singleAction,
        List<String> hexCollection,
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
        this.publicKey = publicKey;
        this.asset = asset;
        this.chainId = chainId;
        this.singleAction = singleAction;
        this.hexCollection = hexCollection;
        this.contextFreeActions = contextFreeActions;
        this.actions = actions;
        this.transactionExtensions = transactionExtensions;
        this.signatures = signatures;
        this.contextFreeData = contextFreeData;
    }
}
