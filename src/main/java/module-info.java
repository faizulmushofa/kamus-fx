open module io.github.kamusfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires static lombok;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires org.reflections;
    requires jbcrypt;

    exports io.github.kamusfx;
    exports io.github.kamusfx.controller;
}