// Write a program to print “Hello World” message in bold, blink effect, and in different colors like red, blue etc.

#!/bin/bash

# Reset all attributes
RESET='\033[0m'

# Text effects
BOLD='\033[1m'
BLINK='\033[5m'

# Colors
RED='\033[31m'
GREEN='\033[32m'
BLUE='\033[34m'
YELLOW='\033[33m'
MAGENTA='\033[35m'
CYAN='\033[36m'

echo -e "${BOLD}Bold Hello World${RESET}"
echo -e "${BLINK}Blinking Hello World${RESET}"
echo -e "${RED}Red Hello World${RESET}"
echo -e "${GREEN}Green Hello World${RESET}"
echo -e "${BLUE}Blue Hello World${RESET}"
echo -e "${YELLOW}Yellow Hello World${RESET}"
echo -e "${MAGENTA}Magenta Hello World${RESET}"
echo -e "${CYAN}Cyan Hello World${RESET}"

# Combined effects
echo -e "${BOLD}${BLINK}${RED}Bold Blinking Red Hello World${RESET}"
echo -e "${BOLD}${BLUE}Bold Blue Hello World${RESET}"
echo -e "${BLINK}${GREEN}Blinking Green Hello World${RESET}"