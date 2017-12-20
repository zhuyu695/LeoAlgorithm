package Algorithm;

//This is the text editor interface. 
//Anything you type or change here will be seen by the other person in real time.

/*
a1, a2, a3, ..... an is an array of integers 1...n . If ai > aj but i < j, we call (ai, aj) an inversion pair.

Please design an algorithm to count the inversion pairs

For example, 2,3,8,6,1 has 5 inversion pairs: (2, 1), (3, 1), (8, 6), (8, 1), (6, 1)
*/

public class UberCountInversion {
 public int countInversion(int[] num) {
     return mergeSort(num, 0, num.length-1);
 }
 
 public int mergeSort(int[] num, int start, int end) {
     if (start < end) {
         int mid = (start + end)/ 2;
         int left = mergeSort(num, start, mid);
         int right = mergeSort(num, mid+1, end);
         int merged = merge(num, start, mid, end);
         return left + right + merged;
     }
     return 0;
 }
 
 // Case : num {6, 1}. start 0, end 1, mid 0
 public int merge(int[] num, int start, int mid, int end) {
		int inverse = 0;
		int[] helper = new int[num.length];
		for (int i=start; i<=end; ++i) {
			helper[i] = num[i];
		}
		int i=start, j=mid+1, k=i;
		while(i<=mid && j<=end) {
			if (helper[i] < helper[j]) {
				num[k] = helper[i];
				++i;
			} else {
				num[k] = helper[j];
				++j;
				inverse += (mid + 1 -i);

			}
			++k;
		}
		int remaining = mid-i;
		for (int n=0; n<=remaining; ++n) {
			num[k+n] = helper[i+n];
			//++inverse;
		}

		return inverse;
	}
 
	 public static void main(String[] args) {
	     //System.out.println(new Interview().merge(new int[]{6,1}, 0,0,1));
	     System.out.println(new UberCountInversion().countInversion(new int[]{2, 3, 8, 6, 1, 5}));
	 }  
}
