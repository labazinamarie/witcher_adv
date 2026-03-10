package cz.vse.adv_witcher_labm02.main.game.commands;
import cz.vse.adv_witcher_labm02.main.game.*;
/**
 * Třída implementující příkaz pro sesílání kouzel.
 * Hráč může použít tento příkaz k seslání různých znaků (spells),
 * které mají specifické účinky na cíl nebo samotného hráče.
 *
 * Dostupná kouzla:
 * - Igni: Zapaluje nepřátele nebo překážky.
 * - Aard: Odstrkává cíl.
 * - Axii: Uklidňuje tvory nebo ovlivňuje NPC.
 * - Quen: Vytváří ochranný štít.
 *
 * @author Mariia Labazina
 * @version ZS-2024, 2025-01-12
 */
public class CommandCastSpell implements ICommand {
    private Game game;

    /**
     * Konstruktor třídy.
     *
     * @param game hra, ve které se bude příkaz používat
     */
    public CommandCastSpell(Game game) {
        this.game = game;
    }

    /**
     * Metoda vrací název příkazu, tj.&nbsp;slovo <b>cast</b>.
     *
     * @return název příkazu
     */
    @Override
    public String getName() {
        return "cast";
    }

    /**
     * Metoda umožňuje hráči seslat kouzlo na specifikovaný cíl.
     * Nejprve zkontroluje, zda byl zadán název kouzla. Pokud ne, vrátí chybové
     * hlášení. Pokud je název kouzla zadán, vyhledá odpovídající kouzlo a provede jeho účinek.
     *
     * @param argumentsCommand parametry příkazu <i>(očekává se název kouzla a volitelně cíl)</i>
     * @return textová zpráva s výsledkem seslání kouzla nebo chybové hlášení
     */
    @Override
    public String run(String[] argumentsCommand) {
        if (argumentsCommand.length < 1) {
            return "Please specify a spell to cast.";
        }

        String spellName = argumentsCommand[0];
        String target = argumentsCommand.length > 1 ? argumentsCommand[1] : "nothing";
        switch (spellName.toLowerCase()) {
            case "igni":
                return castIgni(target);
            case "aard":
                return castAard(target);
            case "axii":
                return castAxii(target);
            case "quen":
                return castQuen();
            default:
                return "Unknown spell. Available spells: Igni, Aard, Axii, Quen.";
        }
    }

    /**
     * Sesílá kouzlo Igni na specifikovaný cíl.
     *
     * @param target cíl kouzla
     * @return zpráva s výsledkem seslání kouzla
     */
    private String castIgni(String target) {
        return "You cast Igni on " + target + ", burning your enemy or obstacle!";
    }

    /**
     * Sesílá kouzlo Aard na specifikovaný cíl.
     *
     * @param target cíl kouzla
     * @return zpráva s výsledkem seslání kouzla
     */
    private String castAard(String target) {
        return "You cast Aard on " + target + ", knocking it back!";
    }

    /**
     * Sesílá kouzlo Axii na specifikovaný cíl.
     *
     * @param target cíl kouzla
     * @return zpráva s výsledkem seslání kouzla
     */
    private String castAxii(String target) {
        return "You cast Axii on " + target + ", calming the creature or influencing the NPC.";
    }

    /**
     * Sesílá kouzlo Quen na hráče.
     *
     * @return zpráva o aktivaci ochranného štítu
     */
    private String castQuen() {
        return "You cast Quen, creating a protective shield around yourself.";
    }
}