package cz.vse.adv_witcher_labm02.main.start;
/**
 * Interface for implementing the Observer design pattern.
 * Classes that want to be notified about game state changes
 * should implement this interface.
 *
 * @author Mariia Labazina
 * @version LS-2025, 2025-04-11
 */
public interface Observer {
    void update();
}
