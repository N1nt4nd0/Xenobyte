@echo off
IF NOT EXIST build\libs\xenobyte-1.0.0.jar exit 1
del /Q loader\xenobyte-1.0.0.jar
copy build\libs\xenobyte-1.0.0.jar loader\xenobyte-1.0.0.jar
cd loader
del /Q classes.h
java -jar packer.jar xenobyte-1.0.0.jar
del /Q eloader\eloader\classes.h
copy classes.h eloader\eloader\classes.h
cd eloader
msbuild eloader.sln /p:Configuration=Release /p:Platform=x86
msbuild eloader.sln /p:Configuration=Release /p:Platform=x64
cd ../..
del /F /S /Q output-total
mkdir output-total
copy build\libs\xenobyte-1.0.0.jar output-total\xenobyte-1.0.0.jar
copy loader\eloader\output\Release\xenobyte.x32.dll output-total\xenobyte.x32.dll
copy loader\eloader\output\Release\xenobyte.x64.dll output-total\xenobyte.x64.dll