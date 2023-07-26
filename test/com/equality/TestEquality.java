package com.equality;

import org.junit.Assert;
import org.junit.Test;

public class TestEquality {

    @Test
    public void testpersonEquality() {
        Person p1 = new Person("Jimmy", 50);
        Person p2 = new Person("Jimmy", 47);
        Person p3 = new Person("Jimmy", 50);

        Assert.assertTrue(p1.equals(p3));
        Assert.assertFalse(p1.equals(p2));
        Assert.assertFalse(p3.equals(p2));
    }


    @Test
    public void testemployeeEquality() {
        Employee e1 = new Employee("Rakesh", 50, "VP");
        Employee e2 = new Employee("Rakesh", 47, "VP");
        Employee e3 = new Employee("Rakesh", 50, "VP");
        Employee e4 = new Employee("Rakesh", 50, "PL");

        Assert.assertTrue(e1.equals(e3));
        Assert.assertFalse(e1.equals(e2));
        Assert.assertFalse(e3.equals(e4));
    }

    @Test
    public void testforPersonEmployeeEquality() {
        Employee e1 = new Employee("Rakesh", 50, "VP");
        Person p1 = new Person("Rakesh", 50);
        Person p2 = new Employee("Rakesh", 50, "VP");
        Person p3 = new Employee("Rakesh", 50, "VP");
        Person p4 = new Employee("Rakesh", 44, "VP");


        Assert.assertFalse(e1.equals(p1));
        Assert.assertTrue(e1.equals(p2)); //Parent reference, instance of Employee
        Assert.assertTrue(p2.equals(p3));
        Assert.assertFalse(p3.equals(p4));
    }

}
