package org.example;

import com.mongodb.client.*;
import org.bson.Document;

import java.util.Scanner;

public class Main {
    private static MongoClient mongoClient;
    private static MongoDatabase database;
    private static MongoCollection<Document> collection;

    public static void main(String[] args) {
        mongoClient = MongoClients.create("mongodb://localhost:27017");
        database = mongoClient.getDatabase("college");
        collection = database.getCollection("students");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 1) {
                register(scanner);
            } else if (choice == 2) {
                login(scanner);
            } else if (choice == 3) {
                System.out.println("Exiting...");
                break;
            } else {
                System.out.println("Invalid choice, try again.");
            }
        }

        scanner.close();
        mongoClient.close();
    }

    private static void register(Scanner scanner) {
        System.out.print("Enter student ID: ");
        String studentId = scanner.nextLine();

        if (collection.find(new Document("studentId", studentId)).first() != null) {
            System.out.println("Student ID already exists. Try another one.");
            return;
        }

        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
            System.out.println("Invalid email format.");
            return;
        }

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        Document student = new Document("studentId", studentId)
                .append("name", name)
                .append("email", email)
                .append("password", password);

        collection.insertOne(student);
        System.out.println("Registration successful!");
    }

    private static void login(Scanner scanner) {
        System.out.print("Enter student ID: ");
        String studentId = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        .
        Document student = collection.find(new Document("studentId", studentId)).first();

        if (student != null && student.getString("password").equals(password)) {
            System.out.println("Login successful! Welcome, " + student.getString("name"));
        } else {
            System.out.println("Invalid student ID or password.");
        }
    }
}