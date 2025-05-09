package com.example.diaryapp.db;

import com.example.diaryapp.model.Category;
import com.example.diaryapp.model.DiaryEntry;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler {
    private static final String DB_URL = "jdbc:sqlite:diary.db";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT id, name FROM categories ORDER BY name";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                categories.add(new Category(rs.getInt("id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            System.err.println("Ошибка загрузки категорий: " + e.getMessage());
        }
        return categories;
    }

    public Category getCategoryById(int id) {
        String sql = "SELECT id, name FROM categories WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Category(rs.getInt("id"), rs.getString("name"));
            }
        } catch (SQLException e) {
            System.err.println("Ошибка получения категории по ID: " + e.getMessage());
        }
        return null;
    }

    public List<DiaryEntry> getAllEntries() {
        List<DiaryEntry> entries = new ArrayList<>();
        String sql = "SELECT e.id, e.title, e.content, e.entry_date, e.category_id, c.name as category_name " +
                "FROM entries e LEFT JOIN categories c ON e.category_id = c.id " +
                "ORDER BY e.entry_date DESC, e.id DESC";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Category category = null;
                int categoryId = rs.getInt("category_id");
                if (!rs.wasNull()) {
                    category = new Category(categoryId, rs.getString("category_name"));
                }

                LocalDate entryDate = LocalDate.parse(rs.getString("entry_date"), DATE_FORMATTER);
                entries.add(new DiaryEntry(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        entryDate,
                        category
                ));
            }
        } catch (SQLException e) {
            System.err.println("Ошибка загрузки записей: " + e.getMessage());
            e.printStackTrace();
        }
        return entries;
    }

    public boolean addEntry(DiaryEntry entry) {
        String sql = "INSERT INTO entries(title, content, entry_date, category_id) VALUES(?,?,?,?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, entry.getTitle());
            pstmt.setString(2, entry.getContent());
            pstmt.setString(3, entry.getEntryDate().format(DATE_FORMATTER));
            if (entry.getCategory() != null) {
                pstmt.setInt(4, entry.getCategory().getId());
            } else {
                pstmt.setNull(4, Types.INTEGER);
            }
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Ошибка добавления записи: " + e.getMessage());
            return false;
        }
    }

    public boolean updateEntry(DiaryEntry entry) {
        String sql = "UPDATE entries SET title = ?, content = ?, entry_date = ?, category_id = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, entry.getTitle());
            pstmt.setString(2, entry.getContent());
            pstmt.setString(3, entry.getEntryDate().format(DATE_FORMATTER));
            if (entry.getCategory() != null) {
                pstmt.setInt(4, entry.getCategory().getId());
            } else {
                pstmt.setNull(4, Types.INTEGER);
            }
            pstmt.setInt(5, entry.getId());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Ошибка обновления записи: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteEntry(int entryId) {
        String sql = "DELETE FROM entries WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, entryId);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Ошибка удаления записи: " + e.getMessage());
            return false;
        }
    }
}