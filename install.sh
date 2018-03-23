#!/bin/bash
apt install libopencv-dev --yes

cd camera
make
cd ..

mkdir /root/fcvision/
mkdir /root/fcvision/camera
cp -r files /root/fcvision/
cp -r html /root/fcvision/
cp bin/fcvision.jar /root/fcvision/
cp fcvision.conf /etc/
cp camera/camera /root/fcvision/camera
