## Subsequent runs
Follow the README.md first, these steps are used to restart the environment.

### 1. kill any running nodeos or keosd
```
./kill_nodeos.sh
```

### 2. start nodeos docker
```
docker run --name nodeos \
 -p 8888:8888 \
 -p 8899:8899 \
 -p 9876:9876 \
 -v $EOS_DEVELOPER_CONTRACT_DIR:/contracts/dev \
 -t eosio/eos nodeosd.sh \
 -e --http-alias=nodeos:8888 \
 --http-alias=127.0.0.1:8888 \
 --http-alias=localhost:8888 > ./nodeos.log &
```

### 3. start keosd
```
docker exec -it nodeos /opt/eosio/bin/keosd \
 --http-server-address=localhost:8899 > ./keosd.log &
```

### 4. setup system contracts
```
./setup_system_contracts.sh
```
