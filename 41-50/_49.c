// Implement the program for IPC using MPI library (“Hello world” program).
//  mpicc -o 49 _49.c
// mpirun -np 4 ./49


#include <stdio.h>
#include <mpi.h>

int main(int argc, char** argv) {
    // Initialize MPI environment
    MPI_Init(&argc, &argv);

    int rank, size;
    
    // Get current process rank
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);
    
    // Get total number of processes
    MPI_Comm_size(MPI_COMM_WORLD, &size);

    // Print message from each process
    printf("Hello World from process %d of %d\n", rank, size);

    // Clean up MPI environment
    MPI_Finalize();
    return 0;
}