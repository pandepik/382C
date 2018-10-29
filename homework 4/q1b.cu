
#include "cuda_runtime.h"
#include "device_launch_parameters.h"

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define bzero(b,len) (memset((b), '\0', (len)), (void) 0)


__global__ void firstDigit(int *d_out, int *d_in, int size)
{
	//int myId = threadIdx.x + blockIdx.x*blockDim.x;
	int tId = threadIdx.x % size;
//	if (tId < size) {
	d_out[tId] = d_in[tId] % 10;
	d_in[tId] = d_in[tId] % 10;
			//d_out[tId] = d_in[tId] - ((d_in[tId]/10)*10);
			
	
		//else {
		//	d_out[tId] = d_in[tId];
		//}
//	}
}

void reduce(int *d_out, int *d_in, int size) {
	const int maxThreadsPerBlock = 512;
	int threads = maxThreadsPerBlock;
	int blocks = size / maxThreadsPerBlock;

	firstDigit<<<blocks, threads >>>(d_out, d_in, size);
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
	ofp = fopen("q1b.txt", "w");

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

	int *d_in, *d_out;

	cudaMalloc((void**)&d_in, Array_Bytes);
	cudaMalloc((void**)&d_out, Array_Bytes);

	cudaMemcpy(d_in, num, Array_Bytes, cudaMemcpyHostToDevice);

	reduce(d_out, d_in, j);
	
	int first1[Array_Size];
	int first2[Array_Size];
	cudaMemcpy(first1, d_in, Array_Size, cudaMemcpyDeviceToHost);
	cudaMemcpy(first2, d_out, Array_Size, cudaMemcpyDeviceToHost);

	//Output to txt file
	for (int i = 0; i < j; i++) {
		fprintf(ofp, "%d,", first1[i]);
	}
	fprintf(ofp, "\n");
	for (int i = 0; i < j; i++) {
		fprintf(ofp, "%d,", first2[i]);
	}

	//Close files
	fclose(ifp);
	fclose(ofp);

	//Free memory
	cudaFree(d_in);
	cudaFree(d_out);

	return 0;
}

