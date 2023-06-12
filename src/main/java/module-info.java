module com.example.movidle {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens com.example.movidle to javafx.fxml;
    exports com.example.movidle;
}