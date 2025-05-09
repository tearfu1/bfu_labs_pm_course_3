module com.example.diaryapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.slf4j;


    opens com.example.diaryapp.controller to javafx.fxml;
    opens com.example.diaryapp.model to javafx.fxml, javafx.base;
    opens com.example.diaryapp to javafx.fxml, javafx.graphics;
    exports com.example.diaryapp;
}