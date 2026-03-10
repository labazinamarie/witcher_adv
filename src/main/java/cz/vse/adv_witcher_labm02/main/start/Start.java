package cz.vse.adv_witcher_labm02.main.start;

import cz.vse.adv_witcher_labm02.main.game.Game;
import cz.vse.adv_witcher_labm02.main.ui.TextUI;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Spouštěcí třída aplikace.
 *
 * @author Mariia Labazina
 * @version ZS-2024, 2025-01-10
 */
public class Start extends Application
{
    /**
     * Spouštěcí metoda aplikace.
     *
     * @param args parametry aplikace z příkazové řádky, aktuálně se nijak nevyužívají
     */
    public static void main(String[] args)
    {
        if(args.length > 0 && args[0].equals("text")){
            Game game = new Game();
            TextUI ui = new TextUI(game);

            ui.play();

            Platform.exit();
        } else{
            launch();
        }


    }
    /**
     * Main launcher class for the Witcher Adventure game.
     * Switches between the text-based and JavaFX-based UI based on command-line arguments.
     *  Initializes the JavaFX GUI, loads FXML, and sets the application scene.
     *
     * @param stage the primary JavaFX stage
     * @throws Exception if the FXML file or resources cannot be loaded
     * @author Mariia Labazina
     * @version LS-2025, 2025-04-11
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("home.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("styles/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Witcher Adventure");
    }
}

