#!/bin/bash
#
#
#所有的运行脚本都存放在bin目录下，开头加入以下代码
#BIN=$(cd $(dirname $0);pwd)
#. $BIN/head.sh
#var_die $ROOT
#运行脚本的初始化脚本

if [ "$HEAD_DEF" != "HEAD_DEF" ]; then
#***********************include once*************
echo "head def";
source ~/.bash_profile

if [ -z $BIN ] ;then 
    echo "BIN is null"
    exit;
fi;


ROOT=$(dirname $BIN);


#以下是函数的预定义
die() { [ $# -gt 0 ] && echo "$@"; if [ "X$OLD_DIR" != "X" ]; then cd $OLD_DIR; fi; exit -1; }
var() { eval echo \$"$1"; }

var_die() { [ "`var $1`" == "" ] && die "var $1 is not definded" ||:; }
file_die() { if [ -e "$1" ]; then msg=${2:-"file $1 is already exists"}; die "$msg"; fi }
notfile_die() { if [ ! -e "$1" ]; then msg=${2:-"file $1 is not exists"}; die "$msg"; fi }

fi;
