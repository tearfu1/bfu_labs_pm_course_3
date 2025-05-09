package com.example.diaryapp.controller;

import com.example.diaryapp.db.DatabaseHandler;
import com.example.diaryapp.model.Category;
import com.example.diaryapp.model.DiaryEntry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;

public class EntryFormController {

    @FXML
    private TextField titleField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<Category> categoryComboBox;
    @FXML
    private TextArea contentArea;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    private Stage dialogStage;
    private DiaryEntry currentEntry;
    private DatabaseHandler dbHandler;
    private boolean isEditMode = false;

    @FXML
    private void initialize() {
        dbHandler = new DatabaseHandler();
        loadCategories();
        datePicker.setValue(LocalDate.now());
    }

    private void loadCategories() {
        ObservableList<Category> categories = FXCollections.observableArrayList(dbHandler.getAllCategories());
        categoryComboBox.setItems(categories);
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setDiaryEntry(DiaryEntry entry) {
        this.currentEntry = entry;
        if (entry != null) {
            isEditMode = true;
            titleField.setText(entry.getTitle());
            datePicker.setValue(entry.getEntryDate());
            contentArea.setText(entry.getContent());
            if (entry.getCategory() != null) {
                for (Category cat : categoryComboBox.getItems()) {
                    if (cat.getId() == entry.getCategory().getId()) {
                        categoryComboBox.setValue(cat);
                        break;
                    }
                }
            } else {
                categoryComboBox.getSelectionModel().clearSelection();
            }
        } else {
            isEditMode = false;
            categoryComboBox.getSelectionModel().clearSelection();
        }
    }

    @FXML
    private void handleSaveButtonAction() {
        if (isInputValid()) {
            String title = titleField.getText();
            LocalDate date = datePicker.getValue();
            String content = contentArea.getText();
            Category selectedCategory = categoryComboBox.getSelectionModel().getSelectedItem();

            if (isEditMode && currentEntry != null) {
                currentEntry.setTitle(title);
                currentEntry.setEntryDate(date);
                currentEntry.setContent(content);
                currentEntry.setCategory(selectedCategory);
                if (dbHandler.updateEntry(currentEntry)) {
                    showAlert(Alert.AlertType.INFORMATION, "Успех", "Запись успешно обновлена.");
                    dialogStage.close();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Ошибка обновления", "Не удалось обновить запись.");
                }
            } else {
                DiaryEntry newEntry = new DiaryEntry(title, content, date, selectedCategory);
                if (dbHandler.addEntry(newEntry)) {
                    showAlert(Alert.AlertType.INFORMATION, "Успех", "Запись успешно добавлена.");
                    dialogStage.close();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Ошибка добавления", "Не удалось добавить запись.");
                }
            }
        }
    }

    @FXML
    private void handleCancelButtonAction() {
        dialogStage.close();
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (titleField.getText() == null || titleField.getText().trim().isEmpty()) {
            errorMessage += "Некорректный заголовок!\n";
        }
        if (datePicker.getValue() == null) {
            errorMessage += "Не выбрана дата!\n";
        }

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            showAlert(Alert.AlertType.ERROR, "Некорректный ввод", errorMessage);
            return false;
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}