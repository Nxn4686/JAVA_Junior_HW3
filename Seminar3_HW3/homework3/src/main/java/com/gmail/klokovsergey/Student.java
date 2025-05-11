package com.gmail.klokovsergey;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

// * Разработайте класс Student с полями String name, int age7 transient double GPA (средний балл).

public class Student implements Externalizable {

    //region Сериализация студента (Externalizable implementation)
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(name);
        out.writeObject(age);
        out.writeObject(gPA);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        name = (String)in.readObject();
        age = (int)in.readObject();
        gPA = (double)in.readObject();
    }

    //endregion

    //region Методы

    /**
     * Получить имя студента
     * @return имя студента
     */
    public String getName() {
        return name;
    }

    /**
     * Получить возраст студента
     * @return возраст студента
     */
    public int getAge() {
        return age;
    }

    /**
     * Получить средний балл студента
     * @return gPA (средний балл)
     */
    public double getGPA() {
        return gPA;
    }

    /**
     * Установить стунденту средний балл (GPA)
     * @param gPA - средний балл
     */
    public void setGPA(double gPA) {
        this.gPA = gPA;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", gPA=" + gPA +
                '}';
    }
    //endregion

    //region Конструкторы

    /**
     * Создание объекта Студент
     * @param name String имя студента
     * @param age int возраст студента
     * @param gPA double средний балл студента
     */
    public Student(String name, int age, double gPA) {
        this.name = name;
        this.age = age;
        this.gPA = gPA;
    }

    public Student() {
    }

    public Student(String name, int age) {
        this(name, age, Double.valueOf(null));
    }
    //endregion

    //region Поля
    private String name;
    private int age;
    private transient double gPA;
    //endregion
}
