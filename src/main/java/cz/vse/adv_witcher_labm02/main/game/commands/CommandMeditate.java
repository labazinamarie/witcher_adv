package cz.vse.adv_witcher_labm02.main.game.commands;
import java.util.*;
import cz.vse.adv_witcher_labm02.main.game.*;
/**
 * Třída implementující příkaz pro meditaci.
 * Hráč může použít tento příkaz k obnovení zdraví, výdrže a doplnění lektvarů,
 * pokud se nachází v bezpečné zóně.
 *
 * @author Mariia Labazina
 * @version ZS-2024, 2025-01-12
 */
public class CommandMeditate implements ICommand {
    private Game game;

    /**
     * Konstruktor třídy.
     *
     * @param game hra, ve které se bude příkaz používat
     */
    public CommandMeditate(Game game) {
        this.game = game;
    }

    /**
     * Metoda vrací název příkazu, tj.&nbsp;slovo <b>meditate</b>.
     *
     * @return název příkazu
     */
    @Override
    public String getName() {
        return "meditate";
    }

    /**
     * Metoda umožňuje hráči meditovat, což obnoví zdraví. Nejprve zkontroluje, zda se hráč nachází v bezpečné zóně.
     * Pokud není, vrátí zprávu, že meditace není možná. Pokud je hráč v bezpečné zóně,
     * provede obnovu hp a vrátí potvrzující zprávu.
     *
     * @param argumentsCommand parametry příkazu <i>(nejsou očekávány žádné parametry)</i>
     * @return zpráva potvrzující meditaci nebo upozornění, že meditace není možná
     */
    @Override
    public String run(String[] argumentsCommand) {
        if (!game.isInSafeZone()) {
            return "You cannot meditate in dangerous areas!";
        }
        game.restoreHealth();

        return "You meditate and restored your health.";
    }
}