package Algorithm;

import java.util.Random;

public class Randomization {
	/*---------1. Select random number from stream---------*/
	// A function to randomly select a item from stream[0], stream[1], .. stream[i-1]
	int res = 0;    // The resultant random number
    int count = 0;  //Count of numbers visited so far in stream

	int selectRandom(int x)
	{
	    count++;  // increment count of numbers seen so far
	    // If this is the first element from stream, return it
	    Random rand = new Random();
	    if (count == 1)
	        res = x;
	    else
	    {
	        // Generate a random number from 0 to count - 1
	        int i = rand.nextInt() % count;

	        // Replace the prev random number with new number with 1/count probability
	        if (i == count - 1)
	            res  = x;
	    }
	    return res;
	}

	/*-----------2. reservior sampling---------*/
	// A function to randomly select k items from stream[0..n-1].
	int[] selectKItems(int stream[], int n, int k)
	{
	    int i;  // index for elements in stream[]
	    // reservoir[] is the output array. Initialize it with
	    // first k elements from stream[]
	    int reservoir[] = new int[k];
	    for (i = 0; i < k; i++)
	        reservoir[i] = stream[i];

	    // Use a different seed value so that we don't get
	    // same result each time we run this program
	    Random rand = new Random();
	    // Iterate from the (k+1)th element to nth element
	    for (; i < n; i++)
	    {
	        // Pick a random index from 0 to i.
	        int j = rand.nextInt() % (i+1);

	        // If the randomly  picked index is smaller than k, then replace
	        // the element present at the index with new element from stream
	        if (j < k)
	          reservoir[j] = stream[i];
	    }
	    return reservoir;
	}
}
