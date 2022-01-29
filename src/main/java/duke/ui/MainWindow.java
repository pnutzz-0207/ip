package duke.ui;
import duke.Duke;
import duke.sonautil.Ui;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;


/**
 * Controller for MainWindow. Provides the layout for the other controls.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Duke duke;

    private final Image user = new Image(this.getClass().getResourceAsStream("/images/user.png"));
    private final Image sona = new Image(this.getClass().getResourceAsStream("/images/Sona.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        String welcome = Ui.welcomeMessage();
        dialogContainer.getChildren().add(DialogBox.getDukeDialog(welcome, sona));
    }

    public void setDuke(Duke d) {
        duke = d;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = duke.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, user),
                DialogBox.getDukeDialog(response, sona)
        );
        userInput.clear();

        String[] command = input.split(" ");
        if (command.length > 0 && command[0].equals("bye")) {
            PauseTransition wait = new PauseTransition(Duration.seconds(2));
            wait.setOnFinished(event -> Platform.exit());
            wait.play();
        }
    }

}
