#!/bin/bash


#add that script to autostart -> root privelages
ifconfig $1 up 192.168.2.1 netmask 255.255.255.0
sleep 2

if [ -z "$(ps -e | grep dnsmasq)" ]
then
 dnsmasq
fi

hostapd -B /etc/hostapd/hostapd.conf

#killall dnsmasq
