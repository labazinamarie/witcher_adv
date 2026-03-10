package cz.vse.adv_witcher_labm02.main.game;

import cz.vse.adv_witcher_labm02.main.start.GameChange;
import cz.vse.adv_witcher_labm02.main.start.Observer;
import cz.vse.adv_witcher_labm02.main.start.Subject;

import java.util.*;

/**
 * Třída {@code GameWorld} reprezentuje herní svět, obsahující lokace, NPC, bytosti a předměty.
 * Spravuje herní prostředí, včetně aktuálního umístění hráče a seznamu dostupných lokací.
 *
 * @author Mariia Labazina
 * @version ZS-2024, 2025-01-12
 */

public class GameWorld implements Subject {
    private Location actualLocation;
    private final List<Location> locations;
    private List<NPC> characters;
    private Set<Recipe> recipes;
    private Inventory inventory;
    private List<Riddle> riddles;
    private int currentRiddleIndex = 0;
    private Map<GameChange, Set<Observer>> listOfObservers = new HashMap<>();

    /**
     * Konstruktor třídy {@code GameWorld}. Inicializuje seznam lokací, postav, inventáře a receptů.
     * Nastavuje výchozí lokaci hráče a přidává předdefinované postavy a bytosti do herního světa.
     */
    public GameWorld(Inventory inv, Set<Recipe> rec) {
        locations = new ArrayList<>();
        characters = new ArrayList<>();
        recipes = rec;
        inventory = inv;
        for (GameChange change : GameChange.values()) {
            listOfObservers.put(change, new HashSet<>());
        }

        initRiddles();

        Location market = new Location("Blaviken's Market Square", "The heart of Blaviken." + "\n" + "The air is thick with the smell of smoked meats and sweat.\n" + "Merchants barter, villagers gossip, \n and behind the cheerful facade, " + "whispers of a lurking beast spread like wildfire.", false);
        Location woods = new Location("Hindar Woods", "A dense, ancient forest where even the trees seem to whisper. " + "\n" + "The villagers speak of a Leshy that stalks its depths, " + "a guardian twisted by rage. Best tread carefully.", true);
        Location glade = new Location("Dantan Glade", "A vast clearing where the wind carries the scent of wild herbs." + "\n" + "A perfect place to gather ingredients—if you don’t mind the eerie silence.", false);
        Location house = new Location("Alchemist's House", "A dimly lit cottage filled with the scent of brewing potions and burning herbs. " + "\n" + "The alchemist here has knowledge few possess, but his help comes at a cost.", false);
        Location hut = new Location("Haunted Hut", "A decrepit ruin on the village outskirts. No one dares enter. " + "\n" + "Shadows move inside even when there’s no wind, and the ground feels cursed beneath your feet.", true);
        Location cave = new Location("Tulasens’ Cave", "A cavern hidden beneath the roots of the land. The air is damp,and ancient mosaics stare at you from the darkness."+"\n" + " Some say forbidden knowledge lingers here.", true);
        Location river = new Location("Rozkos River", "A wide, churning river with waters as dark as a sorcerer’s soul. " + "\n" +  "A Djinn is rumored to be bound beneath its depths—disturbing it would be a grave mistake.", false);
        Location bridge = new Location("Old Stone Bridge", "A crumbling bridge worn by time and battle. " + "\n" +  "It once connected villages, now it merely stands as a gateway to danger.", false);
        Location chapel = new Location("Chapel of Eternal Fire", "Once a place of worship, now abandoned to the elements. "+ "\n" + "The walls whisper forgotten prayers, and the few who enter rarely return.", false);
        Location den = new Location("Vulkodlak's Den", "A foul-smelling lair deep in the wilderness. The remains of animals—and perhaps men—litter the ground. " + "\n" + "Something monstrous lurks inside, and it does not take kindly to intruders.", true);

        NPC alchemist = new NPC("Alchemist", house, List.of("Ah, a Witcher! You seek knowledge, or perhaps something more... potent?"), 1);
        NPC merchant = new NPC("Merchant", market, List.of("I've heard all problems started with the Haunted Hut. It was full of life, you know, Butcher." +  "\n" + " But after owner's wife died, people talk, that he tried to bring her back with some dark magic, but he only lost his mind and went across the bridge."), 1);
        NPC villager = new NPC("Villager", market, List.of("We've heard you never go away, until the job is done."+ "\n" + " Geralt, please, our village needs your help there is a monster, that lurks around at night and lives in the woods, where we used to hunt our food."), 1);
        NPC priest = new NPC("Priest", chapel, List.of("I see you've found out what creature it is. Well, well, well, Geralt of Rivia. " + "\n" + " Only I need something from you first. I've heard about miracles that the Swallow potion could make and i know for a fact, that your kind crafts" + "that potion quickly and proficient"), 1);

        characters.addAll(Arrays.asList(alchemist, merchant, villager, priest));

        Creature leshy = new Creature("Leshy", "An ancient guardian of the woods, twisted by time. \n" + "Its bark-like skin is scarred, its eyes burning with unnatural fire. \n " + "It will not let you pass without a fight.", getLocationByName("Hindar Woods"), 150, 50);
        Creature djiin = new Creature("Djinn", "A being of raw, unrestrained power. Bound for centuries, its rage is endless. " + "You hear whispers in the wind—its voice, warning you to leave.", getLocationByName("Rozkos River"), 10000, 1000);
        Creature vulkodlak = new Creature("Vulkodlak", " ", getLocationByName("Vulkodlak's Den"), 150, 70);

        actualLocation = market;

        locations.addAll(Arrays.asList(market, woods, glade, house, hut, cave, river, bridge, chapel, den));

        setupTracks();

        market.addExit(house, false);
        market.addExit(hut, false);
        market.addExit(chapel, true);
        market.addExit(woods, false);
        market.addCharacter(merchant);
        market.addCharacter(villager);

        woods.addExit(house, false);
        woods.addExit(hut, false);
        woods.addExit(chapel, false);
        woods.addExit(glade, false);
        woods.addExit(cave, false);
        woods.addExit(bridge, false);
        woods.addExit(market, false);
        woods.addCreature(leshy);

        house.addExit(woods, false);
        house.addExit(hut, false);
        house.addExit(market, false);
        house.addCharacter(alchemist);

        hut.addExit(woods, false);
        hut.addExit(chapel, false);
        hut.addExit(house, false);
        hut.addExit(market, false);

        chapel.addExit(woods, false);
        chapel.addExit(hut, false);
        chapel.addExit(market, false);
        chapel.addCharacter(priest);

        glade.addExit(woods, false);
        glade.addExit(cave, false);

        cave.addExit(glade, false);
        cave.addExit(woods, false);
        cave.addExit(bridge, true);

        bridge.addExit(cave, false);
        bridge.addExit(woods, false);
        bridge.addExit(river, false);
        bridge.addExit(den, true);
        bridge.setRiddle(riddles.getFirst());

        den.addExit(bridge, false);
        den.addCreature(vulkodlak);

        river.addExit(bridge, false);
        river.addCreature(djiin);

        Item sword = new Item("Sword", "Your old good sword, it has never let you down in countless battles", true, false, 1);

        Item tree = new Item("Ancient Pine", "A towering pine tree, its bark etched with deep claw marks. " + "The scent of sap and decay fills the air.", false, "a landmark", 1,100);
        Item seaweed = new Item("Vitalizing Seaweed", "Thick strands of riverweed, slick and unnaturally vibrant. " + "It is said to enhance the potency of alchemical oils. An ingredient for crafting Witcher oils.", true, true, 1);
        Item runeKey = new Item("Ancient Rune Key", "A jagged, blackened key covered in unknown glyphs. " + "It hums faintly in your grasp, reacting to unseen forces.\"Unlocks the sealed cave entrance.\"", true, false,1);
        Item bestiary = new Item("Witcher's Bestiary", "A worn leather-bound tome filled with descriptions of monsters and their weaknesses.", true, "Provides insight into creatures of the world.", 1, true,
                "Leshy: A forest spirit, capable of manipulating nature itself. "
                + "Weak to fire and relic oil.\n\n"
                + "Djinn: A powerful air elemental, bound to objects. "
                + "Unleashing one without preparation means certain death.\n\n"
                + "Vulkodlak: terrible cursed beast, some mistakes it for wolf."
                + "Weak to cursed oil, silver and hunger.", 1);

        Item alchemyBook = new Item("Alchemical Compendium",
                "A collection of potions and their effects, used by alchemists and Witchers alike.",
                true,
                "Helps in potion crafting.", 1, true, "Recipe: Swallow Potion - Restores health over time. " +
                "You need Dwarven Spirit, Celandine and Drowner Brain\n\n"
                + "Recipe: Enhanced Cat - Grants night vision for an extended period.", 1);

        Item cursedScroll = new Item("Cursed Scroll", "A decayed parchment covered in cryptic symbols. Holding it makes your skin crawl.", true, "Might contain forbidden knowledge.", 1, true, "The price of the power is blood... \n\n        Every moon thy power should be restored and turned into one true form - animal's..."
                + "... the Vulkodlak's curse becomes irreversible with time, but reveals one's true potential.", 1);
        Item oilRecipe = new Item("Recipe for the Cursed oil", "old recipe for the oil that will help in the fight with Vulkodlak", true, "Priest gave it to you in exchange for the Swallow", 1, true, "Mix Dog Tallow and Wolfsbane", 1);

        Item wolfsbane = new Item("Wolfsbane", "also known as the" +
                " \"queen of poisons\" and \"monk's hood\","
                + " is used in many witcher potions and alchemical brews.",
                true, "use it for brewing the cursed oil", 10,
                true,1);

        Item dogTallow = new Item("Dog_tallow", "an alchemy ingredient that is needed to craft many blade oils",
                true, "use it for crafting the cursed oil", 3, true,1);

        Item dwarvenSpirit = new Item("Dwarven spirit", "an alchemy ingredient that is needed to craft all mutagen decoctions and base-level potions", true, "use it to brew the Swallow or Thunderbolt potions", 5, true, 1);
        Item cortinarius = new Item("Cortinarius", "Alchemy ingredient. Contains hydragenum", true, "use it to brew the Thunderbolt potion", 2, true,1);
        Item celandine = new Item("Celandine", "an alchemy ingredient that is needed to craft the half of the known potions", true, "use it to brew the Swallow potion", 7, true,1);
        Item drownerBrain = new Item("Drowner Brain", "an alchemy ingredient that is needed to craft potions", true, "use it to brew the Swallow potion", 7, true,1);
        Item torch = new Item("Torch", "old rusty torch that can come in handy sometimes", true, "it will help you light the way through the darkness", 2, true, false,1);
        Item key = new Item("Key", "A small rusty key.", true, "Unlocks doors", 1,1);
        inventory.addItem(sword);

        woods.addItem(tree);
        woods.addItem(cortinarius);
        river.addItem(seaweed);
        river.addItem(drownerBrain);
        cave.addItem(runeKey);
        market.addItem(bestiary);
        market.addItem(key);
        market.addItem(dwarvenSpirit);
        house.addItem(alchemyBook);
        house.addItem(dogTallow);
        hut.addItem(cursedScroll);
        glade.addItem(wolfsbane);
        glade.addItem(celandine);
        chapel.addItem(torch);
        chapel.addItem(oilRecipe);

        Recipe thunderbolt = new Recipe("Thunderbolt", List.of("Dwarven Spirit", "Cortinarius", "Seaweed"));
        Recipe swallow = new Recipe("Swallow", List.of("Dwarven Spirit", "Celandine", "Drowner Brain"));
        Recipe cursedOil = new Recipe("Cursed Oil", List.of("Dog_Tallow", "Wolfsbane"));
        recipes.addAll(Arrays.asList(thunderbolt, swallow, cursedOil));

    }

    /**
     * Vrací aktuální lokaci hráče.
     *
     * @return aktuální lokace
     */
    public Location getActualLocation() {
        return actualLocation;
    }

    /**
     * Nastaví novou aktuální lokaci hráče.
     *
     * @param newLocation nová lokace
     */
    public void setActualLocation(Location newLocation) {
        this.actualLocation = newLocation;
        notifyObservers(GameChange.LOCATION_CHANGE);
    }

    /**
     * Nastaví stopy (tracks) mezi lokacemi, které mohou hráče vést k dalším cílům.
     */
    public void setupTracks() {
        Location woods = getLocationByName("Hindar Woods");
        Location cave = getLocationByName("Tulasens’ Cave");

        woods.addTrack("Footprints, deep and heavy, disappear into the thick foliage.", "Tulasens’ Cave");
        cave.addTrack("Scratch marks along the rocky walls lead further into darkness.", "Vulkodlak's Den");
    }

    /**
     * Vyhledá lokaci podle jejího názvu.
     *
     * @param name název lokace
     * @return objekt {@code Location}, pokud je nalezen; jinak {@code null}
     */
    public Location getLocationByName(String name) {
        for (Location location : locations) {
            if (location.getName().equalsIgnoreCase(name)) {
                return location;
            }
        }
        return null;
    }

    /**
     * Nastaví hádanky ve hře.
     */
    public void initRiddles() {
        riddles = List.of(new Riddle("What has to be broken before you can use it?", "egg"), new Riddle("I speak without a mouth and hear without ears. " + "I have no body, but I come alive with the wind. What am I?", "echo"));
    }

    /**
     * Vrací následující hádanku k vyřešení.
     *
     * @return další hádanka
     */
    public Riddle getNextRiddle() {
        if (riddles.isEmpty() || currentRiddleIndex >= riddles.size() - 1) {
            return null;
        }
        currentRiddleIndex++;
        return riddles.get(currentRiddleIndex);
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
}
