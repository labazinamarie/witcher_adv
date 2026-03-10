package cz.vse.adv_witcher_labm02.main.start;
/**
 * Interface for implementing the Subject in the Observer design pattern.
 * Classes that implement this interface allow observers to register
 * for specific types of game state changes.
 *
 * This helps decouple UI updates from game logic by notifying
 * registered observers when important events occur (like inventory change, location change, etc.).
 *
 * @author Mariia Labazina
 * @version ZS-2024, 2025-01-12
 */
public interface Subject {
    /**
     * Registers an observer to be notified when a specific game change occurs.
     *
     * @param gameChange the type of game event to observe (e.g., INVENTORY_CHANGE, LOCATION_CHANGE)
     * @param observer the observer that will be notified of the change
     */
    void register(GameChange gameChange, Observer observer);
}
