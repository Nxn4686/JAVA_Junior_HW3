package com.gmail.klokovsergey;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Задание 1:
 * Разработайте класс Student с полями String name, int age, transient double GPA (средний балл).
 * Обеспечьте поддержку сериализации для этого класса.
 * Создайте объект класса Student и инициализируйте его данными.
 * Сериализируйте этот объект в файл.
 * Десериализируйте объект обратно в программу из файла.
 * Выведите все поля объекта, включая GPA, и обсудите почему значение GPA не было сохранено/восстановлено.
 *
 * Задание 2:
 * ** Выполнить задачу 1 используя другие типы сериализаторов (в xml и json документов).
 */

public class Main {
    private static final String FILE_JSON = "tasks.json";
    private static final String FILE_BIN = "tasks.bin";
    private static final String FILE_XML = "tasks.xml";
    private static final Scanner scanner = new Scanner(System.in);
    static Gson gson = new Gson();
    private static final XmlMapper xmlMapper = new XmlMapper();

    private static List<Student> students = new ArrayList<>();

    public static void main(String[] args) throws Exception{

        File file = new File(FILE_JSON);
        if (file.exists() && !file.isDirectory())
            students = loadStudentsFromFile(FILE_JSON);
        else
            fillStudentsList();



        while (true) {
            System.out.println("Выберите действие:");
            System.out.println("1. Прочитать список студентов в БД");
            System.out.println("2. Добавить нового студента");
            System.out.println("3. Выйти");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> students.forEach(System.out::println);
                case "2" -> {
                    try {
                        System.out.println("Введите имя студента:");
                        String name = scanner.nextLine();
                        System.out.println("Введите возраст студента (целое число):");
                        int age = scanner.nextInt();
                        System.out.println("Средний балл студента (дробное, через запятую):");
                        double gPA = scanner.nextDouble();
                        scanner.nextLine();
                        students.add(new Student(name, age, gPA));
                    } catch (Exception e) {
                        System.out.println("Некорректные значения, попробуйте еще раз.");
                    }
                }
                case "3" -> {
                    saveStudentsFromFile(FILE_JSON, students);
                    saveStudentsFromFile(FILE_BIN, students);
                    saveStudentsFromFile(FILE_XML, students);
                    System.out.println("Список задач сохранен.");
                    scanner.close();
                    System.exit(0);
                }
                default -> System.out.println("Некорректный выбор. Попробуйте снова.");
            }

        }

    }

    private static List<Student> loadStudentsFromFile(String fileName) {
        List<Student> list = new ArrayList<>();
        File file = new File(fileName);

        if (file.exists()) {
            try {
                if (fileName.endsWith(".json")) {
                    Type type = new TypeToken<List<Student>>(){}.getType();
                    try (FileReader fileReader = new FileReader(fileName);
                         BufferedReader reader = new BufferedReader(fileReader)) {
                        StringBuilder txt = new StringBuilder();
                        while (reader.ready())
                            txt.append(reader.readLine());
                        list = gson.fromJson(txt.toString(), type);
                    }
                } else if (fileName.endsWith(".bin")) {
                    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                        list = (List<Student>)ois.readObject();
                    }
                } else if (fileName.endsWith(".xml")) {
                    list = xmlMapper.readValue(file, xmlMapper.getTypeFactory().constructCollectionType(List.class, Student.class));
                }
            } catch (Exception e) { e.printStackTrace(); }
        }

        return list;
    }

    private static void saveStudentsFromFile(String nameFile, List<Student> list) throws Exception{
            if (nameFile.endsWith(".json")) {
                try (FileWriter writer = new FileWriter(nameFile)) {
                    writer.write(gson.toJson(list));
                } catch (IOException e) { e.printStackTrace(); }
            } else if (nameFile.endsWith(".bin")) {
                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nameFile))) {
                    oos.writeObject(list);
                } catch (IOException e) { e.printStackTrace(); }
            } else if (nameFile.endsWith(".xml")) {
                xmlMapper.writeValue(new File(nameFile), list);
            }
    }

    private static void fillStudentsList() {
        students.add(new Student("Ivan", 20, 7.4));
        students.add(new Student("Maria", 19, 7));
    }
}