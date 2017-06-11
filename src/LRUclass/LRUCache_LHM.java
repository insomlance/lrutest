package LRUclass;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache_LHM<K, V> extends LinkedHashMap<K, V>{
	private static final float load_factor=0.75f;
	private int cacheSize;
	
	public LRUCache_LHM(int cacheSize){
		super((int)(cacheSize/load_factor)+1, load_factor,true);
		this.cacheSize=cacheSize;
	}
	
	@Override
	protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
		return size()>cacheSize;
	}
		
}
