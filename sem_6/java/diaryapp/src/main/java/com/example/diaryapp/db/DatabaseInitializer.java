package com.example.diaryapp.db;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class DatabaseInitializer {

    public static void createTablesIfNotExists() {
        String createCategoriesTableSQL = "CREATE TABLE IF NOT EXISTS categories ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "name TEXT NOT NULL UNIQUE"
                + ");";

        String createEntriesTableSQL = "CREATE TABLE IF NOT EXISTS entries ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "title TEXT NOT NULL,"
                + "content TEXT,"
                + "entry_date TEXT NOT NULL,"
                + "category_id INTEGER,"
                + "FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE SET NULL"
                + ");";

        String insertDefaultCategoriesSQL = "INSERT OR IGNORE INTO categories (name) VALUES " +
                "('Работа'), ('Личное'), ('Учеба'), ('Другое');";


        try (Connection conn = DatabaseHandler.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createCategoriesTableSQL);
            System.out.println("Таблица 'categories' проверена/создана.");
            stmt.execute(createEntriesTableSQL);
            System.out.println("Таблица 'entries' проверена/создана.");
            stmt.executeUpdate(insertDefaultCategoriesSQL);
            System.out.println("Категории по умолчанию проверены/добавлены.");

        } catch (SQLException e) {
            System.err.println("Ошибка при инициализации таблиц: " + e.getMessage());
            e.printStackTrace();
        }
    }
}