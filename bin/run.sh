#!/bin/bash
BIN=$(cd $(dirname $0);pwd)
. $BIN/head.sh
var_die ROOT

main="com.uc.qiujw.BuildData"
CLASSPATH="$ROOT/target/*.jar"
java -cp $CLASSPATH $main $@
