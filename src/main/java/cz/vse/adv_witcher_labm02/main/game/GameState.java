package cz.vse.adv_witcher_labm02.main.game;

/**
 * Enumerace představuje možné stavy hry <i>(běžící hra, výhra, prohra apod.)</i>.
 *
 * @author Mariia Labazina
 * @version ZS-2024, 2025-01-12
 */
public enum GameState
{
    /** Hra stále běží. */
    PLAYING,

    /** Hráč vyhrál. */
    WON,

    /** Hráč prohrál. */
    LOST;
}