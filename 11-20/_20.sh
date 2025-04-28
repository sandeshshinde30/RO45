#!/bin/bash

# Simple Network Connection Checker
ping -c 1 vogueprism.com  && echo "Network: Connected" || echo "Network: Disconnected"