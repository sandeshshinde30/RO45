# Write a python recursive function for prime number input limit in as parameter to it

def is_prime(n, i=2):
    """Recursive function to check if a number is prime"""
    if n <= 2:
        return n == 2
    if n % i == 0:
        return False
    if i * i > n:
        return True
    return is_prime(n, i + 1)

def print_primes(limit, current=2):
    """Recursive function to print primes up to limit"""
    if current > limit:
        return
    if is_prime(current):
        print(current, end=' ')
    print_primes(limit, current + 1)

# Get input from user
limit = int(input("Enter a number to find primes up to: "))
print(f"Prime numbers up to {limit}:")
print_primes(limit)