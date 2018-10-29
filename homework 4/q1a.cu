
#include "cuda_runtime.h"
#include "device_launch_parameters.h"

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define bzero(b,len) (memset((b), '\0', (len)), (void) 0)


__global__ void findMin(int *d_in, int size) 
{
	int tId = threadIdx.x;
	if (tId < size) {
		if (d_in[tId] < d_in[0]) {
			d_in[0] = d_in[tId];
		}
	}
	__syncthreads();
}

void reduce(int *d_in, int size) {
	const int maxThreadsPerBlock = 512;
	int threads = maxThreadsPerBlock;
	int blocks;
	if (size > maxThreadsPerBlock) {
		int blocks = size / maxThreadsPerBlock;
	}
	else {
		blocks = 1;
	}
	findMin<<<blocks, threads >>>(d_in, size);
}

int main()
{
	const int Array_Size = 1000;
	const int Array_Bytes = Array_Size * sizeof(int);

	int num[Array_Size];
	FILE *ifp, *ofp;
	char ch, buffer[32];
	int i = 0, j = 0;

	//Open the file
	ifp = fopen("inp.txt", "r");
	if (ifp == NULL) {
		fprintf(stderr, "Can't open input file inp.txt");
		return 0;
	}

	//Change this!
	ofp = fopen("q1a.txt", "w");

	//Take number and put into num array
	while (1) {
		ch = fgetc(ifp);
		if (ch == EOF) {
			if (ch == ',') {
				break;
			}
			else {
				int x = atoi(buffer);
				if (x < 1000 && x >= 0) {
					num[j] = atoi(buffer);
					j++;
				}
				bzero(buffer, 32);
				i = 0;
				break;
			}
		}
		else if (ch == ',') {
			int x = atoi(buffer);
			if (x < 1000 && x >= 0) {
				num[j] = atoi(buffer);
				j++;
			}
			bzero(buffer, 32);
			i = 0;
			continue;
		}
		else {
			buffer[i] = ch;
			i++;
		}
	}

	int *d_in;//, *d_out;

	cudaMalloc((void**)&d_in, Array_Bytes);
	//cudaMalloc((void**)&d_out, Array_Bytes);

	cudaMemcpy(d_in, num, Array_Bytes, cudaMemcpyHostToDevice);

	//reduce(d_out, d_in, j);
	reduce(d_in, j);

	int min;
	cudaMemcpy(&min, d_in, sizeof(int), cudaMemcpyDeviceToHost);
	//int first[Array_Size];
	//cudaMemcpy(first, d_out, Array_Bytes, cudaMemcpyDeviceToHost);

	//Output min to text file
	fprintf(ofp, "%d\n", min);

	//Output first digit of each number
	//for (int i = 0; i < j; i++) {
	//	fprintf(ofp, "%d,", first[i]);
	//}

	//Close files
	fclose(ifp);
	fclose(ofp);

	//Free memory
	cudaFree(d_in);
	//cudaFree(d_out);

    return 0;
}

