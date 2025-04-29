# Write a program to display the following pyramid. The number of lines in the
# pyramid should not be hard-coded. It should be obtained from the user. The
# pyramid should appear as close to the center of the screen as possible.
# (Hint: Basics n loops)

def print_pyramid(rows):
    """Function to print a centered pyramid"""
    max_width = 2 * rows - 1
    for i in range(1, rows + 1):
        # Calculate number of stars and spaces
        stars = '*' * (2 * i - 1)
        spaces = ' ' * ((max_width - len(stars)) // 2)
        print(spaces + stars)

# Get input from user
rows = int(input("Enter number of rows for the pyramid: "))
print_pyramid(rows)