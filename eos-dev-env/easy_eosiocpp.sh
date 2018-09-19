#!/bin/sh

if [ "$#" -ne 2 ]; then
    echo "Usage: easy_eosiocpp cpp_file_name owner_account_name"
    exit 1
fi

alias cleos='docker exec -it nodeos /opt/eosio/bin/cleos -u http://nodeosd:8888 --wallet-url http://keosd:8899'

declare contract_name=$1
declare account_name=$2

# This script makes the following assumptions that a user with access
# to the `signature-provider` private key has been created in the format
# of {contract_name}.code
eosiocpp -o $contract_name.wast $contract_name.cpp
eosiocpp -g $contract_name.abi $contract_name.cpp

cleos system newaccount \
  --stake-net "5 SYS" \
  --stake-cpu "5 SYS" \
  --buy-ram "5 SYS" \
  eosio $account_name $(<$EOS_RUN_DIR/session/public_key) $(<$EOS_RUN_DIR/session/public_key)

cleos set contract $account_name contracts/dev/$contract_name -p $account_name@active
