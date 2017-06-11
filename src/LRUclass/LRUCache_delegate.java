package LRUclass;

import java.util.LinkedHashMap;

public class LRUCache_delegate<K, V> {
	private LinkedHashMap<K, V> lRUCache;
	private final int cacheSize;
	private static final float load_factor=0.75f;
	
	public LRUCache_delegate(int size) {
		this.cacheSize=size;
		lRUCache=new LinkedHashMap<K, V>((int)(cacheSize/load_factor)+1,load_factor,true){
			@Override
			protected boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {
				synchronized (this) {return size()>cacheSize;}
			}
		};
	}
	
	public void put(K key,V value){
		synchronized (lRUCache) {lRUCache.put(key, value);}
	}
	
	public V get(K key){
		synchronized (lRUCache) {return lRUCache.get(key);}
	}
	
	public void remove(K key){
		synchronized (lRUCache) {lRUCache.remove(key);}
	}
	
	public int size(){
		synchronized (lRUCache) {return lRUCache.size();}
	}

}
