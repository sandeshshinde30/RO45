// Write a program using OpenMP library to parallelize the for loop in sequential program
// of finding prime numbers in given range.

#include <stdio.h>
#include <omp.h>

int is_prime(int n) {
    if (n <= 1) return 0;
    if (n <= 3) return 1;
    if (n % 2 == 0 || n % 3 == 0) return 0;

    for (int i = 5; i * i <= n; i += 6) {
        if (n % i == 0 || n % (i + 2) == 0) return 0;
    }

    return 1;
}

int main() {
    int lower = 1, upper = 100000;
    int num_primes = 0;

    #pragma omp parallel for reduction(+:num_primes)
    for (int i = lower; i <= upper; i++) {
        if (is_prime(i)) {
            num_primes++;
        }
    }

    printf("Number of prime numbers between %d and %d: %d\n", lower, upper, num_primes);

    return 0;
}
