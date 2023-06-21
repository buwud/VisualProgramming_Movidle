package mov.movie;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.util.ArrayList;
import java.util.List;

public class View extends Application {
    private Game game;
    private Label titleLabel, g;
    private TextField guessTextField;
    private Button guessButton;
    private Button exitButton;
    private GridPane tileGrid;
    private int guesses = 0;
    private Label hint;
    private ListView<String> suggestionListView;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        game = new Game();

        g = new Label(game.getCurrentMovie().getTitle());
        g.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #FFFFFF;");

        hint = new Label("hint: " + (game.getCurrentMovie().getYear()));
        hint.setTextFill(Color.GRAY);


        titleLabel = new Label("Guess the movie:");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #FFFFFF;");

        guessTextField = new TextField();
        guessTextField.setMaxWidth(800);
        guessTextField.setPrefWidth(600);
        guessTextField.setStyle("-fx-font-size: 14px; -fx-pref-height: 40px; -fx-background-color: #444444; -fx-text-fill: #FFFFFF; -fx-prompt-text-fill: #999999;");
        guessTextField.setOnKeyPressed(e -> {
            int index = suggestionListView.getSelectionModel().getSelectedIndex();
            switch (e.getCode()){
                case UP:
                    // int selected =
                    suggestionListView.getSelectionModel().getSelectedIndex();
                    if(index>0)
                    {
                        suggestionListView.getSelectionModel().select(index-1);
                        suggestionListView.scrollTo(index-1);
                    }
                    break;
                case DOWN:

                    if (index < suggestionListView.getItems().size() - 1) {
                        suggestionListView.getSelectionModel().select(index + 1);
                        suggestionListView.scrollTo(index + 1);
                    }
                    break;
                case ENTER:
                    String selected = suggestionListView.getSelectionModel().getSelectedItem();
                    if(selected != null){
                        guessTextField.setText(selected);
                        suggestionListView.setVisible(false);
                        guessTextField.requestFocus();
                    }
            }
        });

        guessButton = new Button("Guess");
        guessButton.setStyle("-fx-font-size: 14px; -fx-pref-height: 40px; " +
                                     "-fx-background-color: #FFD700; " +
                                     "-fx-text-fill: #222222;");
        guessButton.setDefaultButton(true);
        guessButton.setOnAction(e -> makeGuess());

        HBox guessBox= new HBox(guessTextField,guessButton);
        guessBox.setSpacing(10);
        guessBox.setAlignment(Pos.TOP_CENTER);

        //AUTO-COMPLETE
        suggestionListView = new ListView<>();
        suggestionListView.setPrefHeight(50);
        suggestionListView.setVisible(false);
        suggestionListView.setOnMouseClicked(e -> {
            String selectedSuggestion = suggestionListView.getSelectionModel().getSelectedItem();
            if(selectedSuggestion != null)
                guessTextField.setText(selectedSuggestion);
            suggestionListView.setVisible(false);
        });

        guessTextField.setOnKeyReleased(e -> handleAutoComplete());

        exitButton = new Button("Exit");
        exitButton.setStyle("-fx-font-size: 12px; -fx-background-color:#DD7F77 ; " +
                                    "-fx-text-fill: #222222;");
        exitButton.setOnAction(e -> primaryStage.close());

        VBox exitButtonContainer = new VBox(exitButton);
        exitButtonContainer.setAlignment(Pos.TOP_RIGHT);
        exitButtonContainer.setPadding(new Insets(10));

        tileGrid = new GridPane();
        tileGrid.setHgap(10);
        tileGrid.setVgap(10);
        tileGrid.setAlignment(Pos.CENTER);


        VBox mainContainer = new VBox(20);
        mainContainer.setPadding(new Insets(20));
        mainContainer.getChildren().addAll(exitButtonContainer,g, titleLabel,
                                           guessBox, hint, suggestionListView,
                                           tileGrid);
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.setStyle("-fx-background-color: #222222; " +
                                 "-fx-background-radius: 10px;");

        Scene scene = new Scene(mainContainer, 1000, 800);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Movidle Game");
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setIconified(true);
        primaryStage.show();
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

        if (titleCorrect){
            generateFeedbackTiles(guess,titleCorrect, yearCorrect, genreCorrect,
                                  originCorrect, directorCorrect, starCorrect);
            showWinPopup();
        }
        else {
            guesses++;
            generateFeedbackTiles(guess,titleCorrect, yearCorrect, genreCorrect,
                                  originCorrect, directorCorrect, starCorrect);
            if (guesses == 5)
                showLosePopup();
        }

        guessTextField.clear();
        guessTextField.requestFocus();
    }
    private void generateFeedbackTiles(String guess,boolean titleCorrect,
                                       boolean yearCorrect,
                                       boolean genreCorrect, boolean originCorrect,
                                       boolean directorCorrect, boolean starCorrect) {
        Movie movie = game.getByTitle(guess, game.movies);

        int rowIndex = tileGrid.getRowCount();

        addTile(movie.getTitle(),titleCorrect ? Color.OLIVEDRAB:Color.valueOf("#BC310F"),0,
                rowIndex);
        addTile(movie.getYear() + "",
                         yearCorrect ? Color.OLIVEDRAB : Color.valueOf("#BC310F"), 1,
                rowIndex);
        addTile( movie.getGenre(),
                         genreCorrect ? Color.OLIVEDRAB : Color.valueOf("#BC310F"), 2,
                         rowIndex);
        addTile( movie.getOrigin(),
                         originCorrect ? Color.OLIVEDRAB : Color.valueOf("#BC310F"), 3,
                 rowIndex);
        addTile( movie.getDirector(),
                         directorCorrect ? Color.OLIVEDRAB : Color.valueOf("#BC310F"), 4,
                 rowIndex);
        addTile( movie.getStar(),
                         starCorrect ? Color.OLIVEDRAB : Color.valueOf("#BC310F"), 5,
                 rowIndex);
    }
    private void addTile(String value, Color color, int columnIndex, int rowIndex) {

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
    private void handleAutoComplete() {
        String input = guessTextField.getText();
        if (input.isEmpty()) {
            suggestionListView.setVisible(false);
            return;
        }
        List<String> suggestions = getMatchingMovieNames(input);
        if (suggestions.isEmpty()) {
            suggestionListView.setVisible(false);
        } else {
            suggestionListView.setItems(FXCollections.observableArrayList(suggestions));
            suggestionListView.setVisible(true);
        }
    }
    private List<String> getMatchingMovieNames(String input) {
        List<String> movieNames = game.getAllMovieTitles();
        List<String> matchingNames = new ArrayList<>();

        for (String name : movieNames) {
            if (name.toLowerCase().startsWith(input.toLowerCase())) {
                matchingNames.add(name);
            }
        }
        return matchingNames;
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