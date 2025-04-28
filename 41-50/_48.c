// Using OpemnMP library write a program in which master thread count the total no. of
// threads created, and others will print their thread numbers.

#include <stdio.h>
#include <omp.h>

int main() {
    int total_threads = 0;

    // Parallel region begins
    #pragma omp parallel
    {
        // Only master thread executes this
        #pragma omp master
        {
            total_threads = omp_get_num_threads();
            printf("Total threads created: %d\n", total_threads);
        }

        // All threads (including master) execute this
        #pragma omp barrier  // Wait for master to finish counting
        
        printf("Hello from thread %d\n", omp_get_thread_num());
    }

    return 0;
}