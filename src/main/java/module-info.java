/**
 * Module definition for the Witcher Adventure game.
 *
 * Declares required JavaFX modules and opens necessary packages
 * for FXML-based reflection at runtime.
 *
 * - Requires JavaFX libraries for GUI (controls, FXML, web).
 * - Opens `main.start` and `main.game` for FXML controller injection.
 * - Exports logic and UI packages to be accessible by JavaFX.
 *
 * This configuration allows the game to function both as a GUI application and
 * to integrate scene layouts and event handling.
 *
 * Author: Mariia Labazina
 * Version: ZS-2024, 2025-01-12
 */
module cz.vse.adv_witcher_labm02 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;


    opens cz.vse.adv_witcher_labm02.main.start to javafx.fxml;
    exports cz.vse.adv_witcher_labm02.main.start;
    exports cz.vse.adv_witcher_labm02.main.game;
    opens cz.vse.adv_witcher_labm02.main.game to javafx.fxml;
}