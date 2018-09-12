#!/bin/sh

alias cleos='docker exec -it nodeos /opt/eosio/bin/cleos -u http://nodeosd:8888 --wallet-url http://keosd:8899'

# This script makes the following assumptions that a user with access
# to the `signature-provider` private key has been created in the format
# of {contract_name}.code
eosiocpp -o $@.wast $@.cpp
eosiocpp -g $@.abi $@.cpp

cleos system newaccount \
  --stake-net "5 SYS" \
  --stake-cpu "5 SYS" \
  --buy-ram "5 SYS" \
  eosio $@.code $(<$EOS_RUN_DIR/session/public_key) $(<$EOS_RUN_DIR/session/public_key)

cleos set contract $@.code contracts/dev/$@ -p $@.code@active
