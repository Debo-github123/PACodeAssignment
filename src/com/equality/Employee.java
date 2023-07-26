package com.equality;

public class Employee extends Person{
    private final String role;
    public Employee(String name, int age, String role) {
        super(name, age);
        this.role = role;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
         if (this.getClass() != obj.getClass())
             return false;
         if (!super.equals(obj))
             return false;

         Employee employee = (Employee) obj;
         return this.role == employee.role;


    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + super.hashCode();
        hash = 37 * hash + (this.role != null ? this.role.hashCode() : 0);
        return hash;
    }
}
