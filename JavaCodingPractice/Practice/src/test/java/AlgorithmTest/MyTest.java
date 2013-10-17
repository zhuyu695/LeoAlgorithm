package AlgorithmTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class MyTest {
	1. Answer:
	public void printList(LNode node) {
		while (node != null) {
			System.out.print(node.getName());
			node = node.getNext();
		}
	}

	2. Answer:
	public void printListReverse(LNode node) {
		if (node == null)
			return;
		printListReverse(node.getNext());
		System.out.print(node.getName());
	}

	3. Answer:
	public ArrayList walkGraph(GNode node) {
		if (node == null)
			return null;
		ArrayList result = new ArrayList();
		result.add(node);
		Set<GNode> trackSet = new HashSet<GNode>();
		trackSet.add(node);
		Queue<GNode> queue = new LinkedList<GNode>();
		queue.add(node);
		while (queue.size() > 0) {
			GNode tmp = queue.poll();
			for (GNode child : tmp.getChildren()) {
				if (trackSet.contains(child))
					continue;
				result.add(child);
				trackSet.add(child);
				queue.add(child);
			}
		}
		return result;
	}

	4. Answer:
	public ArrayList paths(GNode node) {
		if (node == null)
			return null;
		ArrayList<ArrayList<GNode>> finalResult = new ArrayList<ArrayList<GNode>>();
		getPath(node, new ArrayList<GNode>(), finalResult);
		return finalResult;
	}

	public void getPath(GNode node, ArrayList<GNode> path, ArrayList<ArrayList<GNode> result) {
		if (node == null)
			result.add(path.clone());
		path.add(node);
		for (GNode child : node.getChildren()) {
			getPath(node, path, result);
			path.remove(child);
		}
	}

	5. Answer:
	public ArrayList paths(GNode node) {
		if (node == null)
			return null;
		ArrayList<ArrayList<GNode>> finalResult = new ArrayList<ArrayList<GNode>>();
		Set<GNode> trackSet = new HashSet<GNode>();
		getPath(node, new ArrayList<GNode>(), finalResult, trackSet);
		return finalResult;
	}

	public void getPath(GNode node, ArrayList<GNode> path, ArrayList<ArrayList<GNode> result, HashSet<GNode> trackSet) {
		if (node == null)
			result.add(path.clone());
		path.add(node);
		trackSet.add(node);
		for (GNode child : node.getChildren()) {
			if (trackSet.contains(node))
				continue;
			getPath(node, path, result);
			path.remove(child);
		}
	}

	6. Answer:
	public void countWords(InputStream input) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		while (input.hasNext()) {
			String wd = input.next();
			if (map.contains(wd)) {
				map.put(wd, ++map.get(wd));
			} else {
				map.add(wd, 0);
			}
		}
		for (Map.Entry entry : map) {
			System.out.println(entry.getValue() + " " + entry.getValue());
		}
	}

}
