package com.memtrip.eos.chain.actions.transaction.account.actions.undelegatebw

import com.memtrip.eos.abi.writer.Abi
import com.memtrip.eos.abi.writer.AccountNameCompress
import com.memtrip.eos.abi.writer.AssetCompress
import com.memtrip.eos.abi.writer.IntCompress

@Abi
data class UnDelegateBandwidthArgs(
    val from: String,
    val receiver: String,
    val stake_net_quantity: String,
    val stake_cpu_quantity: String
) {

    val getFrom: String
        @AccountNameCompress get() = from

    val getReceiver: String
        @AccountNameCompress get() = receiver

    val getStakeNetQuantity: String
        @AssetCompress get() = stake_net_quantity

    val getStakeCpuQuantity: String
        @AssetCompress get() = stake_cpu_quantity
}