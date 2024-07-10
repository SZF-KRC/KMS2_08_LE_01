package manager.model;

import java.util.Date;

public abstract class Employee {
    private int id;
    private String name;
    private String surname;
    private int age;
    private String phone;
    private Date startDate;
    private double currentHoliday;

    public abstract double calculateSalary();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public double getCurrentHoliday() {
        return currentHoliday;
    }

    public void setCurrentHoliday(double currentHoliday) {
        this.currentHoliday = currentHoliday;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", phone='" + phone + '\'' +
                ", startDate=" + startDate +
                ", currentHoliday=" + currentHoliday +
                '}';
    }
}
