package cz.vse.adv_witcher_labm02.main.game;

import cz.vse.adv_witcher_labm02.main.game.commands.*;
import cz.vse.adv_witcher_labm02.main.start.*;
import cz.vse.adv_witcher_labm02.main.start.Observer;

import java.util.*;

/**
 * The main logic class for the Witcher Adventure game.
 * Coordinates the GameWorld, inventory, command execution, and game state transitions.
 * Acts as the bridge between UI and backend logic.
 *
 * Implements observer pattern for game state updates.
 *
 * @author Mariia Labazina
 * @version LS-2025, 2025-04-11
 */


public class Game implements IGame{
    private boolean gameOver;
    private final GameWorld gameWorld;
    private final Set<ICommand> commands;
    private final Inventory inventory;
    private final Set<Recipe> recipes;
    private List<Creature> creatures;
    private List<NPC> characters;
    private int playerHealth;
    private final int maxHealth = 100;
    private int stamina;
    private final int maxStamina = 100;
    private GameState gameState = GameState.PLAYING;
    private Map<GameChange, Set<Observer>>  listOfObservers = new HashMap<>();

    /**
     * Nastaví aktuální stav hry.
     *
     * @param state nový stav hry (např. {@link GameState#PLAYING}, {@link GameState#WON}, {@link GameState#LOST})
     */
    public void setGameState(GameState state) {
        this.gameState = state;
    }

    /**
     * Vrací aktuální hodnotu zdraví hráče.
     *
     * @return zdraví hráče
     */
    public int getPlayerHealth() {
        return playerHealth;
    }

    /**
     * Stanoví hodnotu zdraví hráče.
     *
     * @param health nová hodnota zdraví hráče
     */
    public void setPlayerHealth(int health) {
        playerHealth = health;
    }

    /**
     * Obnoví zdraví hráče na maximální hodnotu.
     */
    public void restoreHealth() {
        playerHealth = maxHealth;
    }

    /**
     * Zjistí, zda se hráč nachází v bezpečné zóně.
     *
     * @return {@code true}, pokud je hráč v bezpečné zóně; jinak {@code false}
     */
    public boolean isInSafeZone() {
        Location currentLocation = gameWorld.getActualLocation();
        return !currentLocation.getName().toLowerCase().contains("den") &&
                !currentLocation.getName().toLowerCase().contains("battlefield") &&
                !currentLocation.getName().toLowerCase().contains("haunted");
    }

    /**
     * Vrací odkaz na inventář hráče.
     *
     * @return inventář hráče
     */
    public Inventory getInventory() {

        return inventory;
    }


    /**
     * Vrací odkaz na seznam platných receptů.
     *
     * @return seznam receptů
     */
    public Set<Recipe> getRecipes() {
        return recipes;
    }

    /**
     * Konstruktor třídy, vytvoří hru a množinu platných příkazů. Hra po
     * vytvoření již běží a je připravená zpracovávat herní příkazy.
     */
    public Game() {
        gameOver = false;
        recipes = new HashSet<>();
        commands = new HashSet<>();
        inventory = new Inventory();
        gameWorld = new GameWorld(inventory, recipes);
        playerHealth = maxHealth;
        stamina = maxStamina;
        creatures = new ArrayList<>();
        characters = new ArrayList<>();

        commands.add(new CommandEnd(this));
        commands.add(new CommandQuit(this));
        commands.add(new CommandGo(this));
        commands.add(new CommandUnlock(this));
        commands.add(new CommandLook(this));
        commands.add(new CommandTake(this));
        commands.add(new CommandHelp(this));
        commands.add(new CommandInventory(this));
        commands.add(new CommandCombineItems(this));
        commands.add(new CommandDrop(this));
        commands.add(new CommandAttack(this));
        commands.add(new CommandTalk(this));
        commands.add(new CommandGive(this));
        commands.add(new CommandTrack(this));
        commands.add(new CommandSolveRiddle(this));
        commands.add(new CommandLight(this));
        commands.add(new CommandMeditate(this));
        commands.add(new CommandRead(this));
        for(GameChange gameChange : GameChange.values()) {
            listOfObservers.put(gameChange, new HashSet<>());
        }
    }

    /**
     * Metoda vrací informaci, zda hra již skončila <i>(je jedno, jestli
     * výhrou, prohrou nebo příkazem 'konec')</i>.
     *
     * @return {@code true}, pokud hra již skončila; jinak {@code false}
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Metoda nastaví příznak indikující, zda hra skončila.
     *
     * @param gameOver příznak indikující, zda hra již skončila
     */
    public void setGameOver(boolean gameOver) {

        this.gameOver = gameOver;
        notifyObservers(GameChange.END_OF_GAME);
    }

    /**
     * Metoda vrací odkaz na mapu prostorů herního světa.
     *
     * @return mapa prostorů herního světa
     */
    public GameWorld getGameWorld() {
        return gameWorld;
    }

    /**
     * Metoda zpracuje herní příkaz <i>(jeden řádek textu zadaný na konzoli)</i>.
     * Řetězec uvedený jako parametr se rozdělí na slova. První slovo je považováno
     * za název příkazu, další slova za jeho parametry.
     * <p>
     * Metoda nejprve ověří, zda první slovo odpovídá hernímu příkazu <i>(např.
     * 'napoveda', 'konec', 'jdi' apod.)</i>. Pokud ano, spustí obsluhu tohoto
     * příkazu a zbývající slova předá jako parametry.
     *
     * @param line text, který hráč zadal na konzoli jako příkaz pro hru
     * @return výsledek zpracování <i>(informace pro hráče, které se vypíšou na konzoli)</i>
     */
    public String processAction(String line) {
        String[] words = line.split("[ \t]+");

        String actionName = words[0];
        String[] actionParameters = new String[words.length - 1];

        for (int i = 0; i < actionParameters.length; i++) {
            actionParameters[i] = words[i + 1];
        }

        String result = "I don't understand that, I don't know this command.";

        for (ICommand executor : commands) {
            if (executor.getName().equalsIgnoreCase(actionName)) {
                result = executor.run(actionParameters);
                break;
            }
        }

        GameState currentState = getGameState();

        if (currentState == GameState.WON) {
            //gameOver = true;
            return result + "\nYou've won. You've defeated the Vulkodlak and now you can return to Blaviken for your coins.\n";
        }

        if (currentState == GameState.LOST) {
           //gameOver = true;
            return result + "\nYou've lost. The Vulkodlak has torn you into peaces.\n";
        }

        return result;
    }
    /**
     * Určuje aktuální stav hry na základě pozice hráče a stavu nepřátel.
     *
     * @return stav hry (např. {@link GameState#PLAYING}, {@link GameState#WON}, {@link GameState#LOST})
     */
    public GameState getGameState() {
        if ((Objects.equals(gameWorld.getActualLocation().getName(), "Vulkodlak's Den"))) {
            if (inventory.hasItem("Cursed Oil")) {
                setGameOver(true);
                return GameState.WON;
            } else {
               setGameOver(true);
                return GameState.LOST;
            }
        }
        return GameState.PLAYING;
    }

    /**
     * Metoda vrací úvodní text pro hráče, který se vypíše na konzoli ihned po
     * zahájení hry.
     *
     * @return úvodní text
     */
    public String getPrologue() {
        return "Welcome to the Continent!\n"
                + "You step into the boots of a Witcher, a monster hunter forged by trials and mutations.\n"
                + "Venture through treacherous lands, uncover hidden secrets, and face formidable foes.\n"
                + "Your choices will shape the journey ahead—be it glory, survival, or tragedy.\n"
                + "If you're uncertain how to proceed, type the command 'help'.\n"
                + "The path awaits, Witcher. Good luck!";
    }

    /**
     * Metoda vrací závěrečný text pro hráče, který se vypíše na konzoli po ukončení
     * hry. Metoda se zavolá pro všechna možná ukončení hry <i>(hráč vyhrál, hráč
     * prohrál, hráč ukončil hru příkazem 'konec')</i>. Tyto stavy je vhodné
     * v metodě rozlišit.
     *
     * @return závěrečný text
     **/
    public String getEpilogue() {
        String epilogue = "Thank you for playing!.";

        if (gameState == GameState.WON) {
            epilogue = "Congratulations! You have slain the Vulkodlak and returned to Blaviken victorious. Your reward awaits.\n\n" + epilogue;
        } else if (gameState == GameState.LOST) {
            Creature vulkodlak = gameWorld.getActualLocation().getCreatureByName("Vulkodlak");
            if (vulkodlak != null && vulkodlak.isAlive()) {
                epilogue = "You were slain by the Vulkodlak, your journey ending in blood and darkness.\n\n" + epilogue;
            } else {
                epilogue = "You have fallen in battle, devoured by the creatures of the night.\n\n" + epilogue;
            }
        }

        return epilogue;
    }

    @Override
    public void register(GameChange gameChange, Observer observer) {
        listOfObservers.get(gameChange).add(observer);
    }

    private void notifyObservers(GameChange gameChange) {
        for (Observer observer : listOfObservers.get(gameChange)) {
            observer.update();
        }
    }
    @Override
    public void notifyInventoryChanged() {
        notifyObservers(GameChange.INVENTORY_CHANGE);
    }

}
