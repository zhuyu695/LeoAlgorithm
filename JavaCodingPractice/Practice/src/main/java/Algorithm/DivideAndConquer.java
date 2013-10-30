package Algorithm;

import java.util.ArrayList;

public class DivideAndConquer {
	/*1.Given a set of non-overlapping intervals, insert a new interval into the intervals (merge if necessary).
	 * You may assume that the intervals were initially sorted according to their start times.
	 * Example 1:
	 * Given intervals [1,3],[6,9], insert and merge [2,5] in as [1,5],[6,9].
	 * Example 2:
	 * Given [1,2],[3,5],[6,7],[8,10],[12,16], insert and merge [4,9] in as [1,2],[3,10],[12,16].
	 * This is because the new interval [4,9] overlaps with [3,5],[6,7],[8,10]. */
	public class Interval {
		public int start;
		public int end;
		public Interval(int s, int e) {
			start = s;
			end = e;
		}
	}

	public ArrayList<Interval> insertInterval(ArrayList<Interval> intervals, Interval newInterval) {
		ArrayList<Interval> result = new ArrayList<Interval>();
		boolean isLarge = true;
		int i = 0;
		for (; i < intervals.size(); ++i) {
			if (intervals.get(i).end < newInterval.start) {
				result.add(intervals.get(i));
				continue;
			}
			if (isLarge) {
				if (newInterval.end < intervals.get(i).start) {
					result.add(newInterval);
					isLarge = false;
					continue;
				}
				newInterval.start = newInterval.start > intervals.get(i).start ? intervals.get(i).start : newInterval.start;
				newInterval.end = newInterval.end > intervals.get(i).end ? newInterval.end : intervals.get(0).end;
			} else {
				result.add(intervals.get(i));
			}
		}
		if (isLarge) {
			result.add(intervals.get(i));
		}
		return result;
	}

	/*2.You are given a collection of nuts of different size and corresponding bolts.
	 * You can choose any nut & any bolt together, from which you can determine whether the nut is larger than bolt,
	 * smaller than bolt or matches the bolt exactly.
	 * However there is no way to compare two nuts together or two bolts together.
	 * Suggest an algorithm to match each bolt to its matching nut.*/
	public void sortNut(int nuts[], int bolts[], int low, int high) {
		if (low >= high)
			return;
		int left_end = 0;
		left_end = sortNutHelper(nuts, low, high, bolts[high]);
		left_end = sortNutHelper(bolts, low, high, nuts[high]);

		sortNut(nuts, bolts, low, left_end);
		sortNut(nuts, bolts, left_end + 1, high - 1);
	}

	private int sortNutHelper(int a[], int low, int high, int pivot) {
		if (low >= high)
			return low;
		int store = low;
		boolean found = false;
		for (int i = low; i < high; ++i) {
			if (a[i] == pivot && !found) {
				swap(a, i, high);
				found = true;
				--i;
			}
			if (a[i] < pivot) {
				swap(a, i, store);
				++store;
			}
		}
		return store - 1;
	}

	private void swap(int a[], int n, int m) {
		int tmp = a[n];
		a[n] = a[m];
		a[m] = tmp;
	}

	/*3.Write an efficient algorithm that searches for a value in an m x n matrix. This matrix has the following properties:
	 * Integers in each row are sorted from left to right.
	 * The first integer of each row is greater than the last integer of the previous row.*/
	public boolean checkIn2DArray(int a[][], int tar) {
		int top = 0;
		int bot = a.length - 1;
		int left = 0;
		int right = a[0].length - 1;
		while (top <= bot) {
			int mid = (top + bot) / 2;
			if (a[mid][0] > tar) {
				bot = mid - 1;
			} else if (a[mid][right] < tar) {
				top = mid + 1;
			} else {
				while (left < right) {
					int mid_n = (left + bot) / 2;
					if (a[mid][mid_n] == tar) {
						return true;
					} else if (a[mid][mid_n] > tar) {
						right = mid_n - 1;
					} else {
						left = mid_n + 1;
					}
				}
				break;
			}
		return false;
	}
}
