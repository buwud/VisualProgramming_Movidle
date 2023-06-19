package mov.movie;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import mov.movie.Game;
import mov.movie.Movie;

public class View extends Application {
    private Game game;
    private Label titleLabel, g;
    private TextField guessTextField;
    private Button guessButton;
    private Button exitButton;
    private GridPane tileGrid;
    private int guesses = 0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        game = new Game();

        g = new Label(game.getCurrentMovie().getTitle());
        g.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #FFFFFF;");

        titleLabel = new Label("Guess the movie:");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #FFFFFF;");

        guessTextField = new TextField();
        guessTextField.setMaxWidth(400);
        guessTextField.setStyle("-fx-font-size: 14px; -fx-pref-height: 40px; -fx-background-color: #444444; -fx-text-fill: #FFFFFF; -fx-prompt-text-fill: #999999;");

        guessButton = new Button("Guess");
        guessButton.setStyle("-fx-font-size: 14px; -fx-pref-height: 40px; " +
                                     "-fx-background-color: #FFD700; " +
                                     "-fx-text-fill: #222222;");
        guessButton.setDefaultButton(true);
        guessButton.setOnAction(e -> makeGuess());

        tileGrid = new GridPane();
        tileGrid.setHgap(10);
        tileGrid.setVgap(10);
        tileGrid.setAlignment(Pos.CENTER);

        VBox mainBox = new VBox(20);
        mainBox.setPadding(new Insets(20));
        mainBox.getChildren().addAll(g, titleLabel, guessTextField, guessButton, tileGrid);
        mainBox.setAlignment(Pos.CENTER);
        mainBox.setStyle("-fx-background-color: #222222; -fx-background-radius: 10px;");

        Scene scene = new Scene(mainBox, 600, 500);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Movidle Game");
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();

        displayMovieTiles();
    }


    private void makeGuess() {
        String guess = guessTextField.getText();
        Movie movieInput = game.getByTitle(guess, game.movies);
        Movie movie = game.getCurrentMovie();

        boolean titleCorrect = movieInput.getTitle().equalsIgnoreCase(movie.getTitle());
        boolean yearCorrect = String.valueOf(movieInput.getYear()).equalsIgnoreCase(String.valueOf(movie.getYear()));
        boolean genreCorrect = movieInput.getGenre().equalsIgnoreCase(movie.getGenre());
        boolean originCorrect = movieInput.getOrigin().equalsIgnoreCase(movie.getOrigin());
        boolean directorCorrect = movieInput.getDirector().equalsIgnoreCase(movie.getDirector());
        boolean starCorrect = movieInput.getStar().equalsIgnoreCase(movie.getStar());

        if (titleCorrect)
            showWinPopup();
        else {
            guesses++;
            generateFeedbackTiles(guess, yearCorrect, genreCorrect,
                                  originCorrect, directorCorrect, starCorrect);
            if (guesses == 5)
                showLosePopup();
        }

        guessTextField.clear();
        guessTextField.requestFocus();
    }

    private void displayMovieTiles() {
        Movie movie = game.getCurrentMovie();

        addTile(movie.getYear() + "", 0);
        addTile(movie.getGenre(), 1);
        addTile(movie.getOrigin(), 2);
        addTile(movie.getDirector(), 3);
        addTile(movie.getStar(), 4);
    }

    private void addTile(String value, int columnIndex) {
        Label valueLabel = new Label(value);
        valueLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        StackPane tilePane = new StackPane();
        tilePane.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cccccc; -fx-border-width: 1px; -fx-padding: 10px;");
        tilePane.setEffect(new DropShadow(10, Color.GRAY));

        VBox contentBox = new VBox(5);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.getChildren().addAll(valueLabel);

        tilePane.getChildren().add(contentBox);
        StackPane.setAlignment(contentBox, Pos.CENTER);

        tileGrid.add(tilePane, columnIndex, 0);
    }

    private void generateFeedbackTiles(String guess, boolean yearCorrect,
                                       boolean genreCorrect, boolean originCorrect,
                                       boolean directorCorrect, boolean starCorrect) {
        Movie movie = game.getByTitle(guess, game.movies);

        int rowIndex = tileGrid.getRowCount();

        addTileWithColor(movie.getYear() + "",
                         yearCorrect ? Color.OLIVEDRAB : Color.GRAY, 0, rowIndex);
        addTileWithColor( movie.getGenre(),
                         genreCorrect ? Color.OLIVEDRAB : Color.GRAY, 1,
                         rowIndex);
        addTileWithColor( movie.getOrigin(),
                         originCorrect ? Color.OLIVEDRAB : Color.GRAY, 2, rowIndex);
        addTileWithColor( movie.getDirector(),
                         directorCorrect ? Color.OLIVEDRAB : Color.GRAY, 3, rowIndex);
        addTileWithColor( movie.getStar(),
                         starCorrect ? Color.OLIVEDRAB : Color.GRAY, 4, rowIndex);
    }

    private void addTileWithColor(String value, Color color, int columnIndex, int rowIndex) {

        Label valueLabel = new Label(value);
        valueLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        StackPane tilePane = new StackPane();
        tilePane.setStyle("-fx-background-color: " + toRgbCode(color) + "; " +
                                  "-fx-border-color: #cccccc; " +
                                  "-fx-border-width: 1px; -fx-padding: 10px");
        tilePane.setEffect(new DropShadow(10, Color.GRAY));

        VBox contentBox = new VBox(5);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.getChildren().addAll(valueLabel);

        tilePane.getChildren().add(contentBox);
        StackPane.setAlignment(contentBox, Pos.CENTER);

        tileGrid.add(tilePane, columnIndex, rowIndex);
    }

    private String toRgbCode(Color color) {
        return String.format("#%02X%02X%02X",
                             (int) (color.getRed() * 255),
                             (int) (color.getGreen() * 255),
                             (int) (color.getBlue() * 255));
    }

    private void showWinPopup() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Congratulations");
        alert.setHeaderText(null);
        alert.setContentText("You guessed the movie correctly! You win!");
        alert.showAndWait();
    }

    private void showLosePopup() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText("You have reached the maximum number of guesses. Game over!");
        alert.showAndWait();
    }
}
