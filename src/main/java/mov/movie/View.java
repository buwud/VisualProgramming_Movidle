package mov.movie;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;


public class View extends Application {
    private Game game;
    private Label nameLabel;
    private TextField guessTextField;
    private Button guessButton;
    private VBox feedbackBox;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException
    {
        game = new Game();

        nameLabel = new Label("Guess the movie:");
        guessTextField = new TextField();
        guessButton = new Button("Make Guess");
        guessButton.setOnAction(e -> makeGuess());

        HBox inputBox = new HBox(guessTextField, guessButton);
        inputBox.setSpacing(10);
        feedbackBox = new VBox();
        feedbackBox.setSpacing(10);
        feedbackBox.setPadding(new Insets(10));

        VBox mainBox = new VBox(nameLabel, inputBox, feedbackBox);
        mainBox.setAlignment(Pos.CENTER);
        mainBox.setSpacing(20);

        Scene scene = new Scene(mainBox, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Movidle Game");
        primaryStage.show();

        updateView();
    }

    private void makeGuess() {
        String guess = guessTextField.getText();
        game.makeGuess(guess);
        updateView();

        if (game.isGameOver()) {
            showGameOverAlert();
        }
    }

    private void updateView() {
        feedbackBox.getChildren().clear();

        Movie movie = game.getCurrentMovie();
        for (Boolean result : game.getGuessResults()) {
            Label feedbackLabel = new Label(result ? "✓" : "✗");
            feedbackLabel.setStyle("-fx-text-fill: " + (result ? "green" : "red"));
            feedbackBox.getChildren().add(feedbackLabel);
        }

        //nameLabel.setText("Guess the movie: " + movie.getHint());
        guessTextField.clear();
        guessTextField.requestFocus();
    }

    private void showGameOverAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText("You have reached the maximum number of guesses." +
                                     " The movie was: " + game.getCurrentMovie().getTitle());
        alert.showAndWait();
        System.exit(0);
    }
}
