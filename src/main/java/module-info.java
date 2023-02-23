module com.example.rp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.datatransfer;
    requires java.desktop;
    requires opencv;


    opens com.example.rp to javafx.fxml;
    exports com.example.rp;
}