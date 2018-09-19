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
package com.memtrip.eos.chain.actions.transaction.abi

import com.memtrip.eos.abi.writer.Abi
import com.memtrip.eos.abi.writer.AccountNameCompress
import com.memtrip.eos.abi.writer.CollectionCompress
import com.memtrip.eos.abi.writer.DataCompress
import com.memtrip.eos.abi.writer.NameCompress

@Abi
data class ActionAbi(
    val account: String,
    val name: String,
    val authorization: List<TransactionAuthorizationAbi>,
    val data: String?
) {

    val getAccount: String
        @AccountNameCompress get() = account

    val getName: String
        @NameCompress get() = name

    val getAuthorization: List<TransactionAuthorizationAbi>
        @CollectionCompress get() = authorization

    val getData: String?
        @DataCompress get() = data
}
