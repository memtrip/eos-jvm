##
## Populate the memtripissue account with 201 transactions to
## test the getActions pagination.
alias cleos='docker exec -it nodeos /opt/eosio/bin/cleos -u http://nodeosd:8888 --wallet-url http://keosd:8899'

MEMTRIP_ISSUE_PRIVATE_KEY="`cat session/memtripissue/private_key 2>&1`"
WALLET_PASSWORD="`cat session/wallet_password 2>&1`"

cleos wallet unlock --password $WALLET_PASSWORD
cleos wallet import --private-key $MEMTRIP_ISSUE_PRIVATE_KEY

outgoing=200
for i in `seq 2 $outgoing`
do
  if [ `expr $i % 2` -eq 0 ]
    cleos push action eosio.token transfer \
            '[ "memtripissue", "memtripproxy", "0.0100 SYS", "'$i'" ]' -p memtripissue@active -f
  then
    cleos push action eosio.token transfer \
            '[ "memtripproxy", "memtripissue", "0.0100 SYS", "'$i'" ]' -p memtripproxy@active -f
  fi
done
