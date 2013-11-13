package Algorithm;

public class BitManipulation {
	public int swapBit(int num, int src, int dest) {
		int srcBit = (num >> src) & 1;
		int destBit = (num >> dest) & 1;
		if ((srcBit ^ destBit) > 0) {
			num ^= ((1 << src | 1 << dest));
		}
		return num;
	}

	/*-----------------reverse bits of an integer-----------------------*/
	/* numberOfBits = bit length of elem - 1*/
	public int reverseBit(int elem, int numberOfBits) {
		int result = elem;
		for (int i=0; i<numberOfBits/2; ++i) {
			result = swapBit(result, i, numberOfBits - i);
		}
		return result;
	}
}
