package mov.movie;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class View extends Application {
    private Game game;
    private Label titleLabel, g;
    private TextField guessTextField;
    private Button guessButton;
    private GridPane tileGrid;
    private int guesses=0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        game = new Game();

        g=new Label(game.getCurrentMovie().getTitle());

        titleLabel = new Label("Guess the movie:");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        guessTextField = new TextField();
        guessTextField.setPrefWidth(200);
        guessTextField.setStyle("-fx-font-size: 14px;");

        guessButton = new Button("Guess");
        guessButton.setStyle("-fx-font-size: 14px;");
        guessButton.setOnAction(e -> makeGuess());

        tileGrid = new GridPane();
        tileGrid.setHgap(10);
        tileGrid.setVgap(10);
        tileGrid.setAlignment(Pos.CENTER);

        VBox mainBox = new VBox(20);
        mainBox.setPadding(new Insets(20));
        mainBox.getChildren().addAll(g,titleLabel, guessTextField, guessButton
                , tileGrid);
        mainBox.setAlignment(Pos.CENTER);
        mainBox.setStyle("-fx-background-color: #f4f4f4;");

        Scene scene = new Scene(mainBox, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Movidle Game");
        primaryStage.show();

        displayMovieTiles();
    }

    private void makeGuess()
    {
        String guess = guessTextField.getText();
        Movie movieInput= game.getByTitle(guess,game.movies);
        Movie movie = game.getCurrentMovie();

        boolean titleCorrect = movieInput.getTitle().equalsIgnoreCase(movie.getTitle());
        boolean yearCorrect = String.valueOf(movieInput.getYear()).equalsIgnoreCase(String.valueOf(movie.getYear()));
        boolean genreCorrect = movieInput.getGenre().equalsIgnoreCase(movie.getGenre());
        boolean originCorrect = movieInput.getOrigin().equalsIgnoreCase(movie.getOrigin());
        boolean directorCorrect = movieInput.getDirector().equalsIgnoreCase(movie.getDirector());
        boolean starCorrect = movieInput.getStar().equalsIgnoreCase(movie.getStar());

        if(titleCorrect)
            showWinPopup();
        else
        {
            guesses++;

            if(guesses==5)
                showLosePopup();
            else
                generateFeedbackTiles(guess,yearCorrect,genreCorrect,
                                      originCorrect,directorCorrect,starCorrect);
        }

        guessTextField.clear();
        guessTextField.requestFocus();
    }

    private void displayMovieTiles()
    {
        Movie movie =  game.getCurrentMovie();

        addTile("Year", movie.getYear() + "");
        addTile("Genre", movie.getGenre());
        addTile("Origin", movie.getOrigin());
        addTile("Director", movie.getDirector());
        addTile("Star", movie.getStar());
    }

    private void addTile(String label, String value)
    {
        Label titleLabel = new Label(label);
        titleLabel.setStyle("-fx-font-weight: bold;");

        Label valueLabel = new Label(value);
        valueLabel.setStyle("-fx-font-weight: bold;");

        HBox tileBox = new HBox(5);
        tileBox.setPadding(new Insets(5));
        tileBox.setAlignment(Pos.CENTER);
        tileBox.getChildren().addAll(titleLabel, valueLabel);
        tileBox.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cccccc; -fx-border-width: 1px;");

        tileGrid.addRow(tileGrid.getRowCount(), tileBox);
    }

    private void generateFeedbackTiles(String guess, Boolean yearCorrect,
                                       Boolean genreCorrect,
                                       Boolean originCorrect,
                                       Boolean directorCorrect, boolean starCorrect)
    {
        Movie movie= game.getByTitle(guess,game.movies);
        //boollara göre renkleri göstercek

        addTileWithColor("Year", movie.getYear() + "",
                          yearCorrect ? Color.GREEN : Color.RED);
        addTileWithColor("Genre", movie.getGenre(), genreCorrect ? Color.GREEN :
                Color.RED);
        addTileWithColor("Origin", movie.getOrigin(), originCorrect ?
                Color.GREEN : Color.RED);
        addTileWithColor("Director", movie.getDirector(), directorCorrect ?
                Color.GREEN : Color.RED);
        addTileWithColor("Star", movie.getStar(), starCorrect ? Color.GREEN :
                Color.RED);
    }

    private void addTileWithColor(String label, String value, Color color)
    {
        Label titleLabel = new Label(label);
        titleLabel.setStyle("-fx-font-weight: bold;");

        Label valueLabel = new Label(value);
        valueLabel.setStyle("-fx-font-weight: bold;");

        VBox tileBox = new VBox(5);
        tileBox.setPadding(new Insets(5));
        tileBox.setAlignment(Pos.CENTER);
        tileBox.getChildren().addAll(titleLabel, valueLabel);
        tileBox.setStyle("-fx-background-color: " + toRgbCode(color) + "; -fx-border-color: #cccccc; -fx-border-width: 1px;");

        tileGrid.addRow(tileGrid.getRowCount(), tileBox);
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
