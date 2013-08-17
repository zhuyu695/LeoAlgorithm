package Algorithm;

public class SortingAlg {
	/*-------------------------------Merge Sort----------------------------*/
	public void MergeSort(int[] arr, int start, int end) {
		if (start < end) {
			int mid = (start + end) / 2;
			MergeSort(arr, start, mid);
			MergeSort(arr, mid + 1, end);
			Merge(arr, start, mid, end);
		}
	}
	
	public void Merge(int[] arr, int left, int mid, int right) {
		int[] helper = new int[arr.length];
		for (int i = left; i <= right; ++i) {
			helper[i] = arr[i];
		}
		int i = left, j = mid + 1, k = left;
		while (i <= mid && j <= right) {
			if (arr[i] < arr[j]) {
				arr[k] = helper[i];
				++i;
			} else {
				arr[k] = helper[j];
				++j;
			}
			++k;
		}
		int remaining = mid - i;
		for (int ii = 0; ii <= remaining; ++ii) {
			arr[k + ii] = helper[i + ii];
		}
	}
	
	/*-----------------------------Quick Sort------------------------------*/
	public void QuickSort(int[] arr, int left, int right) {
		if (left < right) {
			int pivot = Partition(arr, left, right);
			if (left < pivot - 1)
				QuickSort(arr, left, pivot - 1);
			if (pivot < right)
				QuickSort(arr, pivot, right);
		}
	}
	
	public int Partition(int [] arr, int left, int right) {
		int pivot = arr[(left + right) >> 1];
		int i = left;
		int j = right;
		while (i <= j) {
			while (arr[i] < pivot) {
				++i;
			}
			while (arr[j] > pivot) {
				--j;
			}
			if (i <= j) {
				int tmp = arr[i];
				arr[i] = arr[j];
				arr[j] = tmp;
				++i;
				--j;
			}
		}
		return i;
	}
}
