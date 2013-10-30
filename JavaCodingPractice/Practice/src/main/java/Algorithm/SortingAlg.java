package Algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SortingAlg {
	/*-------------------------------1. Merge Sort----------------------------*/
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

	/*-----------------------------2. Quick Sort------------------------------*/
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

	/*---------------------3. insertion sort---------------------*/
	public void insertionSort(int[] a) {
		for (int i = 1; i < a.length; ++i) {
			int temp = a[i];
			int j;
			for (j = i - 1; j >= 0 && a[j] > temp; --j) {
				a[j + 1] = a[j];
			}
			a[j + 1] = temp;
		}
	}

	/*4. Given an array with n objects colored red, white or blue,
	 * sort them so that objects of the same color are adjacent, with the colors in the order red, white and blue.
	 * Here, we will use the integers 0, 1, and 2 to represent the color red, white, and blue respectively.*/
	public void sortColors(int a[]) {
		int red = 0;
		int blue = a.length - 1;
		int i = red;
		while (i <= blue) {
			if (a[i] == 0 && i != red) {
				swap(a, i, red);
				++red;
			} else if (a[i] == 2 && i != blue) {
				swap(a, i, blue);
				--blue;
			} else {
				++i;
			}
		}
	}

	private void swap(int a[], int i, int j) {
		int tmp = a[i];
		a[i] = a[j];
		a[j] = tmp;
	}

	/*5.Given a collection of intervals, merge all overlapping intervals.
	 * For example,
	 * Given [1,3],[2,6],[8,10],[15,18],
	 * return [1,6],[8,10],[15,18]. */
	public class Interval {
		int start;
		int end;
		Interval() { start = 0; end = 0; }
		Interval(int s, int e) { start = s; end = e; }
	}

	public ArrayList<Interval> merge(ArrayList<Interval> intervals) {
		Collections.sort(intervals, IntervalComparator);
		ArrayList<Interval> result = new ArrayList<Interval>();
		for (int i = 1; i < intervals.size(); ++i) {
			if (result.get(result.size() - 1).end >= intervals.get(i).start) {
				result.get(result.size() - 1).end = Math.max(result.get(result.size() - 1).end, intervals.get(i).end);
			} else {
				result.add(intervals.get(i));
			}
		}
		return result;
	}

	public static Comparator<Interval> IntervalComparator = new Comparator<Interval>() {
		public int compare(Interval interval1, Interval interval2) {

			if (interval1.start > interval2.start)
				return 1;
			else if (interval1.start == interval2.start && interval1.end > interval2.end)
				return 1;
			return -1;
		}
	};
}
