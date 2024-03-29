module org.projet.cypath {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires javafx.media;

    opens org.projet.cypath to javafx.fxml;
    exports org.projet.cypath;
    exports org.projet.cypath.exceptions;
    exports org.projet.cypath.players;
    exports org.projet.cypath.tools;
}