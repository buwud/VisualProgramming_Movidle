module Movidle
{
    requires javafx.graphics;
    requires javafx.controls;
    requires java.base;
    requires javafx.fxml;

    opens mov.movie;
    exports mov.movie;
}