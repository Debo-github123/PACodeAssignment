package com.map;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/*
 consideration :
 1. Alternative solution without multithreading
 2. Concurrent hashmap - to achieve concurrency and performance
 3. Two concurrent Map - one for key/Value pair and one for checking  timeout for each key
 4.
 7. More additional Space due to additional Map (but frequent cleanup can help in clean up additional space)
 8. Lower CPU processing as there is no separate thread for cleanup activity
 */

public class SelfDeleteMapImplAlternative<K, V> implements SelfDeleteMap<K, V> {

    private final Map<K, V> entryMap = new ConcurrentHashMap<>();
    private Map<K, Long> expiryMap = new ConcurrentHashMap<>();

    @Override
    public void put(K key, V value, long timeout) {
        cleanExpiredkeys();
        entryMap.put(key, value);
        expiryMap.put(key, System.currentTimeMillis() + timeout);
    }

    @Override
    public V get(K key) {
        cleanExpiredkeys();
        if (entryMap.containsKey(key)) {
            return entryMap.get(key);
        }
        return null;
    }

    @Override
    public void remove(K key) {
        entryMap.remove(key);
    }

    private void cleanExpiredkeys() {
        for (Map.Entry<K, Long> entry : expiryMap.entrySet()) {
            long currentTime = System.currentTimeMillis();
            if (currentTime > entry.getValue()) {
                System.out.println("removing key:" + entry.getKey());
                expiryMap.remove(entry.getKey());
                entryMap.remove(entry.getKey());
            }
        }
    }
}
