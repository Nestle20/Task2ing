module com.example.task2ing {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.task2ing to javafx.fxml;
    exports com.example.task2ing;
}