module com.example.project1_algo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.project1_algo to javafx.fxml;
    exports com.example.project1_algo;
    exports com.example.project1_algo.Test;
    opens com.example.project1_algo.Test to javafx.fxml;
}