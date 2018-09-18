# eos-dev-env
NOTE FROM DEVELOPER: These are the initial rough steps I took to setup a consistent
development environment for interacting with the nodeos api.
`setup_system_contracts.sh` and `populate_action_list_data.sh` are required by some
of the integration tests in eos-http-rpc and eos-chain-actions. I included this project
with the assumption that it might help some people, but this is by no means a complete solution.

## clone eos
```
git clone https://github.com/EOSIO/eos.git --recursive  --depth 1
cd eos/Docker
docker build . -t eosio/eos
```

## set the `EOS_DEVELOPER_CONTRACT_DIR` env variable
```
export EOS_DEVELOPER_CONTRACT_DIR=/path/to/custom/smart/contracts
export EOS_RUN_DIR=/path/to/where/setup_system_contracts.sh/was/ran
```

## alias cleos
```
alias cleos='docker exec -it nodeos /opt/eosio/bin/cleos -u http://nodeosd:8888 --wallet-url http://keosd:8899'
```

## start nodeos and keosd with docker-compose
```
docker-compose up -d
```

## setup system contracts
```
./setup_system_contracts.sh
```
The following will be executed:
- Create the default wallet with the eosio `signature-provider` private key
- Install the required system contracts
- The `public_key` in the output should be used to create contract accounts
- The `wallet_password` is used to unlock the default wallet that contains the `signature-provider` private key

## set EOS_CONTRACT_PUBLIC_KEY env variable
Set an environment variable for the public_key and wallet_password output from the previous step.
If you lose these details you will need to clean the docker container with `./kill_nodeos.sh` and start the setup again.
```
export EOS_CONTRACT_PUBLIC_KEY=$(<session/public_key)
export EOS_CONTRACT_WALLET_PASSWORD=$(<session/wallet_password)
```

## Tips & tricks
### shell into docker container
```
docker exec -it nodeos bash
```

### kill docker container
```
./kill_nodeos.sh
```

### Use the `-d` and `-j` flags to indicate "don't broadcast" and "return transaction as json"
```
cleos push action eosio.token issue '["user", "100.0000 SYS", "memo"]' -p eosio@active -d -j
```

### Use the `--print-request` flag to show the http requests the command is made up of
```
cleos --print-request create account eosio hello.code $(<session/public_key) $(<session/public_key)
```

### easy_eosiocpp.sh
```
sudo cp easy_eosiocpp.sh /usr/local/eosio/bin/
alias easy_eosiocpp=/usr/local/eosio/bin/easy_eosiocpp.sh
```

Usage inside contract source folder
```
easy_eosiocpp contract_file_name_without_extension
```

### ~/.bash_profile
Add the following to `~/.bash_profile` to make your life easier
```
alias cleos='docker exec -it nodeos /opt/eosio/bin/cleos -u http://nodeosd:8888 --wallet-url http://keosd:8899'
alias easy_eosiocpp=/usr/local/eosio/bin/easy_eosiocpp.sh
export EOSIO_SOURCE=/path/to/eos/ # your local EOS source directory
export EOS_SYSTEM_CONTRACT_DIR=/path/to/eos/build/contracts # adjust this match your local EOS source directory
export EOS_DEVELOPER_CONTRACT_DIR=/path/to/custom/smart/contracts # the path to your dev custom contracts defined earlier
export PATH=${PATH}:/usr/local/eosio/bin/
export EOS_RUN_DIR=/path/to/where/setup_system_contracts.sh/was/ran
```

## nodeos container issue resolution
https://github.com/EOSIO/eos/issues/5054
exited with code 137
```
nodeos --hard-replay
```
https://github.com/EOSIO/eos/issues/4902
rethrow database dirty flag set: {"what":"database dirty flag set"}
```
nodeos --delete-all-blocks
```
