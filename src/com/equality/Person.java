package com.equality;

public class Person {
    private final int age;
    private final String name;
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if(this.getClass() != obj.getClass()) {
            return false;
        }

        Person person = (Person) obj;
        return this.name == person.name && this.age == person.age;
    }

    @Override
    public int hashCode() {
      int hash=7;
      hash = 37 * hash + (this.name != null ? this.name.hashCode() :0);
      hash = 37 * hash + age;
      return hash;
    }
}
