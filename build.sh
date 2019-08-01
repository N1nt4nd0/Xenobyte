#!/bin/bash
if [ ! -f "build/libs/xenobyte-1.0.0.jar" ]; then
	exit 1
fi
rm -f loader/xenobyte-1.0.0.jar
cp build/libs/xenobyte-1.0.0.jar loader/xenobyte-1.0.0.jar
cd loader
rm -f classes.h
java -jar packer.jar xenobyte-1.0.0.jar
rm -f eloader/eloader/classes.h
cp classes.h eloader/eloader/classes.h
cd eloader
msbuild eloader.sln /p:Configuration=Release /p:Platform=x86
msbuild eloader.sln /p:Configuration=Release /p:Platform=x64
cd ../..
rm -d -f output-total
mkdir output-total
cp build/libs/xenobyte-1.0.0.jar output-total/xenobyte-1.0.0.jar
cp loader/eloader/output/Release/xenobyte.x32.dll output-total/xenobyte.x32.dll
cp loader/eloader/output/Release/xenobyte.x64.dll output-total/xenobyte.x64.dll