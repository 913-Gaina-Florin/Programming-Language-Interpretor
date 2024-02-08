module com.example.javafxinterpretor {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.javafxinterpretor to javafx.fxml;
    opens com.example.javafxinterpretor.utilities to javafx.base;
    exports com.example.javafxinterpretor;
}