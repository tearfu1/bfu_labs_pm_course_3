package com.example.diaryapp.controller;

import com.example.diaryapp.MainApp;
import com.example.diaryapp.db.DatabaseHandler;
import com.example.diaryapp.model.DiaryEntry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

public class MainViewController {

    @FXML
    private TableView<DiaryEntry> entriesTableView;
    @FXML
    private TableColumn<DiaryEntry, LocalDate> dateColumn;
    @FXML
    private TableColumn<DiaryEntry, String> titleColumn;
    @FXML
    private TableColumn<DiaryEntry, String> categoryColumn;

    @FXML
    private Button addButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;

    private DatabaseHandler dbHandler;
    private ObservableList<DiaryEntry> entryList;

    @FXML
    private void initialize() {
        dbHandler = new DatabaseHandler();
        entryList = FXCollections.observableArrayList();

        dateColumn.setCellValueFactory(new PropertyValueFactory<>("entryDate"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("categoryName"));

        dateColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.format(java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                }
            }
        });

        loadEntries();

        editButton.setDisable(true);
        deleteButton.setDisable(true);

        entriesTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    editButton.setDisable(newValue == null);
                    deleteButton.setDisable(newValue == null);
                }
        );
    }

    private void loadEntries() {
        entryList.setAll(dbHandler.getAllEntries());
        entriesTableView.setItems(entryList);
        entriesTableView.refresh();
    }

    @FXML
    private void handleAddButtonAction() {
        showEntryFormDialog(null);
    }

    @FXML
    private void handleEditButtonAction() {
        DiaryEntry selectedEntry = entriesTableView.getSelectionModel().getSelectedItem();
        if (selectedEntry != null) {
            showEntryFormDialog(selectedEntry);
        } else {
            showAlert(Alert.AlertType.WARNING, "Ничего не выбрано", "Пожалуйста, выберите запись для редактирования.");
        }
    }

    @FXML
    private void handleDeleteButtonAction() {
        DiaryEntry selectedEntry = entriesTableView.getSelectionModel().getSelectedItem();
        if (selectedEntry != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Подтверждение удаления");
            alert.setHeaderText("Удалить запись: " + selectedEntry.getTitle() + "?");
            alert.setContentText("Вы уверены, что хотите удалить эту запись?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (dbHandler.deleteEntry(selectedEntry.getId())) {
                    loadEntries();
                    showAlert(Alert.AlertType.INFORMATION, "Успех", "Запись успешно удалена.");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Ошибка удаления", "Не удалось удалить запись из базы данных.");
                }
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Ничего не выбрано", "Пожалуйста, выберите запись для удаления.");
        }
    }

    private void showEntryFormDialog(DiaryEntry entry) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/EntryFormView.fxml"));
            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle(entry == null ? "Добавить запись" : "Редактировать запись");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            EntryFormController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setDiaryEntry(entry);

            dialogStage.showAndWait();

            loadEntries();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Ошибка", "Не удалось открыть форму редактирования.");
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