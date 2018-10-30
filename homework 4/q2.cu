
#include "cuda_runtime.h"
#include "device_launch_parameters.h"

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define bzero(b,len) (memset((b), '\0', (len)), (void) 0)


__global__ void globalCount(int *count, int *d_in, int size)
{	
	int myId = threadIdx.x + blockDim.x * blockIdx.x;
	int tId = threadIdx.x;
	if (tId < size) {
		count[d_in[tId] / 10]++;
	}
}

__global__ void sharedCount(int *count, int *d_in, int size)
{	
	etern __shared__ int sCount[10];

	int myId = threadIdx.x + blockDim.x * blockIdx.x;
	int tId = threadIdx.x;

	sCount[tId] = count[tId];
	__syncthreads();

	//counts in shared memory
	if (tId < size) {
		sCount[d_in[tId]/10]++;
	}
	__syncthreads();
	//writes back to global memory
	if (tId < size) {
		count[tId] += sCount[d_in[tId]/10];
	}
}

__global__ void prefixCount(int *count, int *d_in, int size) {
	int myId = threadIdx.x + blockDim.x * blockIdx.x;
	int tId = threadIdx.x;
	if (tId < size) {
		for (int i = 0; i < tId; i++) {
			count[tId] += d_in[i];
		}
		__syncthreads();
	}
}

void reduce(int *count3, int *count2, int *count1, int *d_in, int size) {
	const int maxThreadsPerBlock = 512;
	int threads = maxThreadsPerBlock;
	int blocks;
	if (size > maxThreadsPerBlock) {
		int blocks = size / maxThreadsPerBlock;
	}
	else {
		blocks = 1;
	}
	
	globalCount(count1, d_in, size);
	sharedCount(count2, d_in, size);
	prefixCount(count3, count1, 10);
}

int main()
{
	const int Array_Size = 1000;
	const int Array_Bytes = Array_Size * sizeof(int);
	const int Count_Size = 10;
	const int Count_Bytes = Count_Size * sizeof(int);

	int num[Array_Size];
	FILE *ifp, *ofp1, *ofp2, *ofp3;
	char ch, buffer[32];
	int i = 0, j = 0;

	//Open the file
	ifp = fopen("inp.txt", "r");
	if (ifp == NULL) {
		fprintf(stderr, "Can't open input file inp.txt");
		return 0;
	}

	//Change this!
	ofp1 = fopen("q2a.txt", "w");
	ofp2 = fopen("q2b.txt", "w");
	ofp3 = fopen("q2c.txt", "w");

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

	int zeros[] = { 0,0,0,0,0,0,0,0,0,0 };
	int *d_in, *count1, *count2, *count3;

	cudaMalloc((void**)&d_in, Array_Bytes);
	cudaMalloc((void**)&count1, Array_Bytes);
	cudaMalloc((void**)&count2, Array_Bytes);
	cudaMalloc((void**)&count3, Array_Bytes);

	//Allocate Memory
	cudaMemcpy(d_in, num, Array_Bytes, cudaMemcpyHostToDevice);
	cudaMemcpy(count1, zeros, Count_Bytes, cudaMemcpyHostToDevice);
	cudaMemcpy(count2, zeros, Count_Bytes, cudaMemcpyHostToDevice);
	cudaMemcpy(count3, zeros, Count_Bytes, cudaMemcpyHostToDevice);

	reduce(count3, count2, count1, d_in, j);

	//Copy from Device to Host
	int globalCount[Count_Size];
	cudaMemcpy(globalCount, count1, Count_Bytes, cudaMemcpyDeviceToHost);
	int sharedCount[Count_Size];
	cudaMemcpy(sharedCount, count2, Count_Bytes, cudaMemcpyDeviceToHost);
	int prefixCount[Count_Size];
	cudaMemcpy(prefixCount, count3, Count_Bytes, cudaMemcpyDeviceToHost);

	//Output to text file
	for (int i = 0; i < 10; i++) {
		fprintf(ofp1, "%d,", count1[i]);
		fprintf(ofp2, "%d,", count2[i]);
		fprintf(ofp3, "%d,", count3[i]);
	}

	//Close files
	fclose(ifp);
	fclose(ofp1);
	fclose(ofp2);
	fclose(ofp3);

	//Free memory
	cudaFree(d_in);
	cudaFree(count1);
	cudaFree(count2);
	cudaFree(count3);

	return 0;
}

