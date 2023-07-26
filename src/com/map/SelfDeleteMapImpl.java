package com.map;
/*
 consideration :
 1. Concurrent hashmap - to achieve concurrency and performance
 2. We need a separate process for cleanup activity which will clean expired elements
 3. We con consider a Timer Task but this will ignore some element which period is shorter and lead to inaccuracy
 4.A separate thread processing - this will keep cleaning the expired elements
 5. A Queue - can help to connect two processes
 6. DelayQueue - Specifically used for delay(Timer purpose).The getDelay method states how much time is left for the object to be kept in the DelayQueue.
 7. Less additional Space
 8. Higher CPU processing due to additional thread processing ( can be used threadpool for higher concurrent load)
 */


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class SelfDeleteMapImpl<K, V> implements SelfDeleteMap<K, V> {

    private final Map<K, V> entryMap;

    private final DelayQueue<Delaykey> delayQueue;

    public SelfDeleteMapImpl() {
        entryMap = new ConcurrentHashMap<>();
        delayQueue = new DelayQueue<Delaykey>();
        initiateCleanup();

    }

    @Override
    public void put(K key, V value, long timeout) {
        entryMap.put(key, value);
        delayQueue.offer(new Delaykey(key, timeout));

    }

    private void initiateCleanup() {
        Thread t = new Thread(new SelfExipiringScheduler());
        t.start();
    }

    @Override
    public V get(K key) {
        if (entryMap.containsKey(key)) {
            return entryMap.get(key);
        }
        return null;
    }

    @Override
    public void remove(K key) {
        entryMap.remove(key);
    }

    private class Delaykey<K> implements Delayed {

        K key;
        long startTime;


        public Delaykey(K key, long timeout) {
            this.key = key;
            this.startTime = System.currentTimeMillis() + timeout;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            long diff = System.currentTimeMillis() - startTime;
            return unit.convert(diff, TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed delayed) {
            return Long.compare(this.startTime, ((Delaykey) delayed).startTime);
        }
    }

    private class SelfExipiringScheduler implements Runnable {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                synchronized (this) {
                    System.out.println("EntryMap initial size:" + entryMap.size());

                    Delaykey<K> expiredKey = null;
                    try {
                        expiredKey = delayQueue.take();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    System.out.println("Expired Key:" + expiredKey.key);
                    entryMap.remove(expiredKey.key);
                    System.out.println("EntryMap after size:" + entryMap.size());

                }
            }
        }
    }
}


