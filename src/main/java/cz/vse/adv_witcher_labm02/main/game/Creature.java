package cz.vse.adv_witcher_labm02.main.game;
import java.util.*;
/**
 * Třída {@code Creature} představuje tvor ve hře, který může mít sílu, zdraví,
 * lokaci a určité akce jako útok a obranu.
 *
 * @author Mariia Labazina
 * @version ZS-2024, 2025-01-12
 */
public class Creature {
    private String name;
    private String description;
    private Location location;
    private int strength;
    private int health;

    /**
     * Konstruktor pro vytvoření tvora s danými parametry.
     *
     * @param name jméno tvora
     * @param description popis tvora
     * @param location počáteční lokace tvora
     * @param strength útočná síla tvora
     * @param health počáteční zdraví tvora
     */
    public Creature(String name, String description, Location location, int strength, int health) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.strength = strength;
        this.health = health;
    }

    /**
     * Vrací jméno tvora.
     *
     * @return jméno tvora
     */
    public String getName() {
        return name;
    }


    /**
     * Vrací popis tvora.
     *
     * @return popis tvora
     */
    public String getDescription() {
        return description;
    }

    /**
     * Vrací aktuální lokaci tvora.
     *
     * @return lokace, kde se tvor nachází
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Nastaví novou lokaci pro tvor.
     *
     * @param location nová lokace tvora
     */public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Nastaví nový popis tvora.
     *
     * @param description nový popis tvora
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Vrací útočnou sílu tvora.
     *
     * @return úroveň síly tvora
     */
    public int getStrength() {
        return strength;
    }
    /**
     * Vrací aktuální zdraví tvora.
     *
     * @return hodnota zdraví tvora
     */
    public int getHealth() {
        return health;
    }

    /**
     * Nastaví úroveň síly tvora.
     *
     * @param strength nová hodnota tvora
     */
    public void setStrength(int strength) {
        this.strength = strength;
    }

    /**
     * Nastaví hodnotu zdraví tvora.
     *
     * @param health nová hodnota zdraví
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Snižuje zdraví tvora o určité množství zranění.
     *
     * @param damage hodnota zranění, které tvor obdrží
     */
    public void takeDamage(int damage) {
        health -= damage;
    }


    /**
     * Kontroluje, zda je tvor stále naživu.
     *
     * @return {@code true}, pokud je zdraví tvora vyšší než 0, jinak {@code false}
     */
    public boolean isAlive() {
        return health > 0;
    }

    /**
     * Vrací úroveň útoku tvora.
     *
     * @return síla útoku tvora
     */
    public int attack() {
        return strength;
    }

    /**
     * Vrací textovou zprávu o tom, že se tvor brání útoku.
     *
     * @return zpráva o obraně tvora
     */
    public String defend() {
        return name + " defends against the attack!";
    }
}