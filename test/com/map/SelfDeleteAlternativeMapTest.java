package com.map;


import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class SelfDeleteAlternativeMapTest {

    @Test
    public void testSelfExpiredScenerio () {
        SelfDeleteMap selfDeleteMap = new SelfDeleteMapImplAlternative();



        selfDeleteMap.put("one", 100, 7000);
        selfDeleteMap.put("two", 200, 10);

        Assert.assertTrue((Integer)selfDeleteMap.get("one") == 100);
        try {
            TimeUnit.MILLISECONDS.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // key already expired
        Assert.assertNull(selfDeleteMap.get("two"));


     //  check with updated value Key
        selfDeleteMap.put("two", 250, 200);
        Assert.assertTrue((Integer)selfDeleteMap.get("two") == 250);


        selfDeleteMap.put("one", 750, 2500);
        Assert.assertTrue((Integer)selfDeleteMap.get("one") == 750);

         //test for key removal
        selfDeleteMap.remove("one");
        Assert.assertNull(selfDeleteMap.get("one"));

    }
}
