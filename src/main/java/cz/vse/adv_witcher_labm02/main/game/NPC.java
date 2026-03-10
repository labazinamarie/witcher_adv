package cz.vse.adv_witcher_labm02.main.game;
import java.util.*;

/**
 * Třída {@code NPC} představuje nehratelnou postavu (NPC) ve hře.
 * Každé NPC má jméno, lokaci, seznam frází, které může říkat,
 * a index sledující aktuální frázi v dialogu.
 *
 * @author Mariia Labazina
 * @version ZS-2024, 2025-01-12
 */
public class NPC {
    private String name;
    private Location location;
    private List<String> phrases;
    private int currentPhraseIndex;

    /**
     * Konstruktor třídy {@code NPC}.
     * Inicializuje NPC se zadaným jménem, lokací a seznamem frází.
     * Pokud seznam frází není poskytnut, inicializuje prázdný seznam.
     *
     * @param name jméno NPC
     * @param location počáteční lokace NPC
     * @param phrases seznam frází, které NPC může říkat
     * @param currentPhraseIndex index aktuální fráze (výchozí hodnota je 0)
     */
    public NPC(String name, Location location, List<String> phrases, int currentPhraseIndex) {
        this.name = name;
        this.location = location;
        this.phrases = (phrases != null) ? new ArrayList<>(phrases) : new ArrayList<>();
        this.currentPhraseIndex = 0;
    }

    /**
     * Vrací jméno NPC.
     *
     * @return jméno NPC
     */
    public String getName() {
        return name;
    }


    /**
     * Vrací aktuální lokaci NPC.
     *
     * @return lokace, ve které se NPC nachází
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Nastaví novou lokaci pro NPC.
     *
     * @param location nová lokace NPC
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Přidá novou frázi do seznamu frází NPC.
     *
     * @param phrase nová fráze, kterou NPC může říkat
     */
    public void addPhrase(String phrase) {
        phrases.add(phrase);
    }

    /**
     * Vrací další frázi, kterou NPC říká.
     * Pokud NPC nemá žádné fráze, vrací výchozí zprávu.
     * Při každém zavolání metody se přechází na další frázi.
     *
     * @return fráze, kterou NPC říká
     */
    public String speak() {
        if (phrases.isEmpty()) {
            return name + " doesn't have anything to say right now.";
        }
        String response = name + ": " + phrases.get(currentPhraseIndex);
        currentPhraseIndex = (currentPhraseIndex + 1) % phrases.size();
        return response;
    }

    /**
     * Vrací seznam všech frází, které NPC může říkat.
     *
     * @return seznam frází NPC
     */
    public List<String> getPhrases() {
        return phrases;
    }

    /**
     * Resetuje index frází na začátek, aby NPC opět začínalo svou konverzaci od první fráze.
     */
    public void resetPhrases() {
        currentPhraseIndex = 0;
    }
}