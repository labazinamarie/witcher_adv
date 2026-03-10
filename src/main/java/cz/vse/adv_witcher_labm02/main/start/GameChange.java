package cz.vse.adv_witcher_labm02.main.start;
/**
 * Enum representing game state changes that observers can listen to.
 * Used in the observer pattern to trigger updates to the UI or game state.
 *
 * @author Mariia Labazina
 * @version LS-2025, 2025-04-11
 */
public enum GameChange {
    LOCATION_CHANGE,
    INVENTORY_CHANGE,
    END_OF_GAME
}
