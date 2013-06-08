#!/bin/bash
BIN=$(cd $(dirname $0);pwd)
. $BIN/head.sh
var_die ROOT

cd $ROOT
git pull
mvn install
scp -P 9922 target/make-1.0.jar qiujw1@platform30:~/mulit/
scp -P 9922 target/make-1.0.jar qiujw2@platform30:~/mulit/
