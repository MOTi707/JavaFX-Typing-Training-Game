module com.example.lab {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires java.desktop;

    exports com.example.lab.View;
    opens com.example.lab.View to javafx.fxml;
    exports com.example.lab.Controller;
    opens com.example.lab.Controller to javafx.fxml;
    exports com.example.lab.Model;
    opens com.example.lab.Model to javafx.fxml;
}