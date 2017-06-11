package LRUclass;

import java.util.HashMap;

public class LRUCache_Manual<K, V> {
	private HashMap<K, Entry> cacheMap;
	private Entry headEntry, tailEntry, currentEntry;
	private int cacheSize;
	private static float load_factor = 0.75f;

	public class Entry<K, V> {
		private K key;
		private V value;
		public Entry before, after;

		public Entry(K key, V value) {
			this.key = key;
			this.value = value;
		}

		public K Key() {
			return key;
		};

		public void Value(V value) {
			this.value=value;
		};
	}

	public LRUCache_Manual(int cacheSize) {
		cacheMap = new HashMap<K, Entry>((int) (cacheSize / load_factor) + 1);
		headEntry = new Entry<K, V>(null, null);
		headEntry.before = null;
		headEntry.after = headEntry;
		tailEntry = headEntry;
	}

	public synchronized void put(K key, V value) {
		if (cacheMap.get(key) != null) {
			cacheMap.get(key).Value(value);
			moveToHead(cacheMap.get(key));
			return;
		}
		if (size() == cacheSize) {
			if (cacheMap.get(tailEntry.key) == null)
				return;
			cacheMap.remove(tailEntry.key);
			removeLast();
		}
		Entry nowEntry = new Entry<K, V>(key, value);
		cacheMap.put(key, nowEntry);
		moveToHead(nowEntry);
	}

	public void moveToHead(Entry entry) {
		synchronized (this) {
			if (headEntry.after != null) {
				headEntry.after.before = entry;
				entry.after = headEntry.after;
			} else {
				tailEntry = entry;
			}
			headEntry.after = entry;
			entry.before = headEntry;
		}
	}

	public synchronized int size() {
		return cacheMap.size();
	}

	public synchronized void remove(K key) {
		Entry entry = cacheMap.get(key);
		if (entry == null)
			return;
		if (entry == tailEntry) {
			tailEntry = entry.before;
		} else {
			entry.after.before = entry.before;
		}
		entry.before.after = entry.after;
		cacheMap.remove(key);

	}

	public synchronized void removeLast() {
		if (tailEntry==headEntry) return;
		cacheMap.remove(tailEntry.key);
		tailEntry.before.after=null;
	}
	
	public synchronized Entry get(K key){
		Entry entry=cacheMap.get(key);
		if (entry==null) return null;
		
		moveToHead(entry);
		return entry;
	}
}
