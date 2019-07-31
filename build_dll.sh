#!/bin/bash
if [ ! -f "build/libs/xenobyte-1.0.0.jar" ]; then
	exit 1
fi
cp build/libs/xenobyte-1.0.0.jar loader/xenobyte-1.0.0.jar
cd loader
java -jar packer.jar xenobyte-1.0.0.jar
