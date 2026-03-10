package cz.vse.adv_witcher_labm02.main.start;

import cz.vse.adv_witcher_labm02.main.game.Game;
import cz.vse.adv_witcher_labm02.main.game.IGame;
import cz.vse.adv_witcher_labm02.main.game.Item;
import cz.vse.adv_witcher_labm02.main.game.Location;
import cz.vse.adv_witcher_labm02.main.game.commands.CommandGo;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.util.*;

/**
 * JavaFX controller class for the main game interface.
 * Handles UI interactions, updates inventory, location, and map dynamically via the observer pattern.
 * Also provides actions for sending commands, showing help, and handling game state.
 *
 * @author Mariia Labazina
 * @version LS-2025, 2025-04-11
 */
public class HomeController {

    @FXML
    private ImageView player;
    @FXML
    private ListView<Location> exitsPanel;
    @FXML
    private Button buttonSend;
    @FXML
    private TextField input;
    @FXML
    private TextArea output;
    @FXML
    private VBox inventoryPanel;
    @FXML
    private Pane mapPane;

    private IGame game = new Game();

    private ObservableList<Location> exitList = FXCollections.observableArrayList();

    private Map<String, Point2D> locationCoordinates = new HashMap<>();

    /**
     * Initializes the controller. Sets up event listeners, observer pattern,
     * and binds UI components to the game model.
     */
    @FXML
    private void initialize() {
        output.appendText(game.getPrologue()+ "\n\n");
        Platform.runLater(()-> input.requestFocus());
        exitsPanel.setItems(exitList);
        game.getGameWorld().register(GameChange.LOCATION_CHANGE,() -> {
            updateExitList();
            updatePlayerPosition();
            updateLocationItems();
        });
        game.register(GameChange.END_OF_GAME, () -> updateGameEnd());
        getCoordinates();
        updateExitList();
        updateLocationItems();
        exitsPanel.setCellFactory(param -> new ListCellLocation());
        game.register(GameChange.INVENTORY_CHANGE, ()->{
            updateLocationItems();
            updateInventoryPanel();

        });
        updateInventoryPanel();
    }
    /**
     * Returns the path to an image based on the item's name.
     *
     * @param name name of the item
     * @return image path
     */
    private String getImagePathForItem(String name) {
        return "items/" + name.trim() + ".png";
    }
    /**
     * Sets the coordinate mapping for each location on the map.
     */
    private void getCoordinates() {
        locationCoordinates.put("Blaviken's Market Square", new Point2D(324, -13));
        locationCoordinates.put("Haunted Hut", new Point2D(324, 57));
        locationCoordinates.put("Chapel of Eternal Fire", new Point2D(616, 57));
        locationCoordinates.put("Alchemist's House", new Point2D(145, 57));
        locationCoordinates.put("Hindar Woods", new Point2D(324, 112));
        locationCoordinates.put("Dantan Glade", new Point2D(145, 170));
        locationCoordinates.put("Tulasens’ Cave", new Point2D(324, 170));
        locationCoordinates.put("Old Stone Bridge", new Point2D(616, 170));
        locationCoordinates.put("Vulkodlak’s Den", new Point2D(451, 238));
        locationCoordinates.put("Rozkos River", new Point2D(616, 238));
    }

    /**
     * Updates the list of exits based on the current location.
     */
    @FXML
    private void updateExitList(){
        exitList.clear();
        exitList.addAll(game.getGameWorld().getActualLocation().getExits());
    }
    /**
     * Updates the player's marker position on the map according to current location.
     */
    @FXML
    private void updatePlayerPosition() {
        String location = game.getGameWorld().getActualLocation().getName();
        Point2D coords = locationCoordinates.get(location);

        if (coords != null) {
            player.setLayoutX(coords.getX());
            player.setLayoutY(coords.getY());
        } else {
            System.err.println("[WARN] No coordinates mapped for: " + location);
        }
    }
    /**
     * Updates the visible items on the map based on the current location.
     * Creates icons with options to inspect or take the item.
     */
    @FXML
    private void updateLocationItems() {
        mapPane.getChildren().removeIf(node -> node.getStyleClass().contains("map-item"));

        List<Item> items = new ArrayList<>(game.getGameWorld().getActualLocation().getItems());

        String locationName = game.getGameWorld().getActualLocation().getName();
        Point2D baseCoord = locationCoordinates.get(locationName);

        if (baseCoord == null) return;

        double x = baseCoord.getX();
        double y = baseCoord.getY() + 40;
        double offset = 45;

        for (Item item : items) {
            try {
                ImageView itemIcon = new ImageView(new Image(
                        getClass().getResourceAsStream("items/" + item.getName().toLowerCase() + ".png")
                ));
                itemIcon.setFitHeight(40);
                itemIcon.setFitWidth(40);
                itemIcon.setLayoutX(x);
                itemIcon.setLayoutY(y);
                itemIcon.getStyleClass().add("map-item");

                itemIcon.setOnMouseClicked(e -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle(item.getName());
                    alert.setHeaderText("Inspect or Take Item");
                    alert.setContentText(item.getDetailedDescription());

                    ButtonType takeButton = new ButtonType("Take");
                    ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                    alert.getButtonTypes().setAll(takeButton, cancelButton);

                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.isPresent() && result.get() == takeButton) {
                        String command = "take " + item.getName();
                        String response = game.processAction(command);
                        output.appendText("\n> " + command + "\n" + response + "\n");

                        updateInventoryPanel();
                        updateLocationItems();
                    }
                });

                mapPane.getChildren().add(itemIcon);
                x += offset;
            } catch (Exception ex) {
                System.err.println("Could not load image for item: " + item.getName());
            }
        }
    }
    /**
     * Updates the player's inventory panel by displaying all current items with their images
     * and adding context menu options based on item properties.
     */
    @FXML
    private void updateInventoryPanel() {
        inventoryPanel.getChildren().clear();

        for (Item item : game.getInventory().getItems().values()) {
            HBox itemBox = new HBox(10);
            itemBox.setAlignment(Pos.CENTER_LEFT);

            ImageView imageView;
            try {
                imageView = new ImageView(new Image(getClass().getResourceAsStream(getImagePathForItem(item.getName()))));
            } catch (Exception e) {
                imageView = new ImageView();
            }
            imageView.setFitWidth(32);
            imageView.setFitHeight(32);

            Label label = new Label(item.getName());

            ContextMenu menu = new ContextMenu();

            if (item.isReadable()) {
                MenuItem read = new MenuItem("Read");
                read.setOnAction(e -> handleCommand("read " + item.getName()));
                menu.getItems().add(read);
            }

            if (item.canBeLit() && !item.isLit()) {
                MenuItem light = new MenuItem("Light");
                light.setOnAction(e -> handleCommand("light " + item.getName()));
                menu.getItems().add(light);
            }

            if (item.isConsumable()) {
                MenuItem use = new MenuItem("Drink");
                use.setOnAction(e -> handleCommand("Drink " + item.getName()));
                menu.getItems().add(use);
            }

            if (item.getName().equalsIgnoreCase("dog_tallow") || item.getName().equalsIgnoreCase("wolfsbane")) {
                MenuItem combine = new MenuItem("Combine");
                combine.setOnAction(e -> handleCommand("combine " + item.getName()));
                menu.getItems().add(combine);
            }

            if (item.isMovable()) {
                MenuItem drop = new MenuItem("Drop");
                drop.setOnAction(e -> handleCommand("drop " + item.getName()));
                menu.getItems().add(drop);
            }

            itemBox.setOnContextMenuRequested(e -> menu.show(itemBox, e.getScreenX(), e.getScreenY()));

            itemBox.getChildren().addAll(imageView, label);
            inventoryPanel.getChildren().add(itemBox);
        }
    }
    /**
     * Disables game input when game is over and shows epilogue.
     */
    private void updateGameEnd() {
        if(game.isGameOver()){
            output.appendText(game.getEpilogue());
            input.setDisable(true);
            buttonSend.setDisable(true);
            exitsPanel.setDisable(true);
        }
    }
    /**
     * Reads user input from the text field and processes it.
     *
     * @param actionEvent triggered by pressing Enter or clicking Send
     */
    @FXML
    private void enterInput(ActionEvent actionEvent) {
        String command = input.getText();
        input.clear();
        processAction(command);
    }

    /**
     * Processes a command and prints the response to the output text area.
     *
     * @param command the text command from user
     */
    @FXML
    private void processAction(String command) {
        output.appendText("> " + command+ "\n");
        String result = game.processAction(command);
        output.appendText(result + "\n\n");
    }
    /**
     * Handles command input from the UI, processes it via the game logic,
     * and updates the output area with the result.
     *
     * @param command the user-entered command string
     */
    @FXML
    private void handleCommand(String command) {
        String result = game.processAction(command);
        output.appendText("> " + command + "\n" + result + "\n\n");

        //updateInventoryPanel();

        if (game.isGameOver()) {
            output.appendText(game.getEpilogue());
            input.setDisable(true);
            buttonSend.setDisable(true);
        }
    }
    /**
     * Handles clicking an exit in the exitsPanel. Moves the player to the selected location.
     */
    @FXML
    private void clickExitPanel(MouseEvent mouseEvent) {
        Location dest = exitsPanel.getSelectionModel().getSelectedItem();
        if (dest == null) return;
        String command = CommandGo.NAME + " " + dest.getName();
        processAction(command);
    }

    /**
     * Ends the game after confirming with the player.
     */
    @FXML
    private void endGame(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to end this game?");

        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            Platform.exit();
        }
    }
    /**
     * Starts a new game instance after confirmation. Resets UI and listeners.
     */
    @FXML
    private void startNewGame(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to start a new game?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            game = new Game();

            output.clear();
            input.setDisable(false);
            buttonSend.setDisable(false);
            exitsPanel.setDisable(false);

            output.appendText(game.getPrologue() + "\n\n");
            updateExitList();
            updateInventoryPanel();
            updateLocationItems();
            updatePlayerPosition();

            game.getGameWorld().register(GameChange.LOCATION_CHANGE, () -> {
                updateExitList();
                updatePlayerPosition();
            });
            game.register(GameChange.END_OF_GAME, this::updateGameEnd);
            game.register(GameChange.INVENTORY_CHANGE, () -> {
                updateInventoryPanel();
                updateLocationItems();
            });
        }
    }
    /**
     * Displays the help window in a new WebView stage.
     */
    @FXML
    private void helpClick(ActionEvent actionEvent) {
        Stage helpStage = new Stage();
        WebView wv = new WebView();
        Scene helpScene = new Scene(wv);
        helpStage.setScene(helpScene);
        helpStage.show();
        wv.getEngine().load(getClass().getResource("help.html").toExternalForm());
    }
}