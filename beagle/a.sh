#!/bin/bash

#function execute_button
#{
	windowID=`xdotool search --pid 1420 | tail -1`
	xdotool windowfocus --sync $windowID key ctrl+Tab
#}

#execute_button
