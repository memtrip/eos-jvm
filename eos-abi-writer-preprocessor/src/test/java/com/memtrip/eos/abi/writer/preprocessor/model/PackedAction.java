package com.memtrip.eos.abi.writer.preprocessor.model;

import com.memtrip.eos.abi.writer.Abi;
import com.memtrip.eos.abi.writer.AccountNameCompress;
import com.memtrip.eos.abi.writer.CollectionCompress;
import com.memtrip.eos.abi.writer.DataCompress;
import com.memtrip.eos.abi.writer.NameCompress;

import java.util.List;

@Abi
public class PackedAction {

    private final String account;
    private final String name;
    private final List<PackedTransactionAuthorization> authorization;
    private final String data;

    @AccountNameCompress
    public String account() {
        return account;
    }

    @NameCompress
    public String name() {
        return name;
    }

    @CollectionCompress
    public List<PackedTransactionAuthorization> authorization() {
        return authorization;
    }

    @DataCompress
    public String data() {
        return data;
    }

    public PackedAction(
        String account,
        String name,
        List<PackedTransactionAuthorization> authorization,
        String data
    ) {
        this.account = account;
        this.name = name;
        this.authorization = authorization;
        this.data = data;
    }
}
