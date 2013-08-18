package leo.proj.cache;

import org.junit.Test;

public class LRUcacheTest {
	private static LRUcache lc = new LRUcache(5);
	private static String testInfo[] = {"Hello", "Foo", "Good", "Interest", "Hi", "New"};
	private static int testKey[] = {500, 292, 393, 298};

	private static class MultiThreadingPut implements Runnable {
		public void run() {
			for (String str : testInfo) {
				lc.put(new Block<String>(str));
			}
		}
	}

	private static class MultiThreadingGet implements Runnable {
		public void run() {
			for (int key : testKey) {
				lc.get(key);
			}
		}
	}

	@Test
	public void testLRUcacheWithMultiThreading() throws InterruptedException {
		Thread tp = new Thread(new MultiThreadingPut());
		Thread tg = new Thread(new MultiThreadingGet());
		tp.start();
		tg.start();
		tp.join();
		tg.join();
		lc.printList();
	}
}
