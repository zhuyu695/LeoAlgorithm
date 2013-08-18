package leo.proj.cache;

public class KeyValueGenerator {
	public String generateValue(int key) {
		return "ValueStr=" + String.valueOf(key);
	}

	public int generateKey(String value) {
		int finalOut = 0;
		for(char c : value.toCharArray()) {
			finalOut += (int)c;
		}
		return finalOut;
	}
}
