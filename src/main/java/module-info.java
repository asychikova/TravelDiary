module com.example.finalproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    // Export packages
    exports com.example.finalproject.Controller;
    exports com.example.finalproject.Model;
    exports com.example.finalproject.View;

    // Open packages for JavaFX FXML (if needed)
    opens com.example.finalproject.Controller to javafx.fxml;
    opens com.example.finalproject.Model to javafx.fxml;
    opens com.example.finalproject.View to javafx.fxml;
}
