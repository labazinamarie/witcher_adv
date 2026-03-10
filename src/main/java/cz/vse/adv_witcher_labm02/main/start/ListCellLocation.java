package cz.vse.adv_witcher_labm02.main.start;

import cz.vse.adv_witcher_labm02.main.game.Location;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;

/**
 * Custom list cell to display location names with associated images
 * in the exits panel (ListView).
 *
 * @author Mariia Labazina
 * @version LS-2025, 2025-04-11
 */
public class ListCellLocation extends ListCell<Location> {
    @Override
    protected void updateItem(Location location, boolean empty) {
        super.updateItem(location, empty);
        if (empty || location == null) {
            setText(null);
            setGraphic(null);
        } else {
            setText(location.getName());
            ImageView iw = new ImageView(getClass().getResource("locations/" + location.getName() + ".jpg").toExternalForm());
            iw.setFitWidth(100);
            iw.setPreserveRatio(true);
            setGraphic(iw);
        }

    }
}
