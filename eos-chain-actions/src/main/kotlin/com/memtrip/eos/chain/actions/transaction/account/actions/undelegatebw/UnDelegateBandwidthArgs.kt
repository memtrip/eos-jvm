/**
 * Copyright 2013-present memtrip LTD.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.memtrip.eos.chain.actions.transaction.account.actions.undelegatebw

import com.memtrip.eos.abi.writer.Abi
import com.memtrip.eos.abi.writer.AccountNameCompress
import com.memtrip.eos.abi.writer.AssetCompress

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