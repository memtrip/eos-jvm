package com.memtrip.eos.http.aggregation.account.actions.delegatebw

import com.memtrip.eos.abi.writer.Abi
import com.memtrip.eos.abi.writer.AccountNameCompress
import com.memtrip.eos.abi.writer.AssetCompress
import com.memtrip.eos.abi.writer.IntCompress

@Abi
data class DelegateBandwidthArgs(
    val from: String,
    val receiver: String,
    val stake_net_quantity: String,
    val stake_cpu_quantity: String,
    val transfer: Int) {

    val getFrom: String
        @AccountNameCompress get() = from

    val getReceiver: String
        @AccountNameCompress get() = receiver

    val getStakeNetQuantity: String
        @AssetCompress get() = stake_net_quantity

    val getStakeCpuQuantity: String
        @AssetCompress get() = stake_cpu_quantity

    val getTransfer: Int
        @IntCompress get() = transfer
}