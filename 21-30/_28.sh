# #!/bin/bash

# # Simple User Display Script

# echo "Currently logged in users (using who):"
# echo "-------------------------------------"
# who

# echo -e "\nAll users with login shells (using finger):"
# echo "-------------------------------------------"
# if command -v finger &> /dev/null; then
#     finger | grep -v "Login" | grep -v "Never logged in"
# else
#     echo "Note: 'finger' command not found, using alternative:"
#     grep -v "nologin" /etc/passwd | cut -d: -f1 | sort
# fi

# echo -e "\nSystem users summary:"
# echo "-------------------"
# echo "Total users: $(wc -l < /etc/passwd)"
# echo "Currently logged in: $(who | wc -l)"

# Write a shell script to display the users on the system . (Using finger or who command).


#!/bin/bash

# Display the users on the system using the who command
echo "Users currently logged in to the system:"
who
