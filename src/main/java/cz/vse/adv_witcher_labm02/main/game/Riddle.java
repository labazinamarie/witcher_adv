package cz.vse.adv_witcher_labm02.main.game;

/**
 * Třída představující hádanku, která obsahuje otázku a správnou odpověď.
 * Hráč může použít hádanku k řešení úkolů nebo odemykání lokací.
 *
 * @author Mariia Labazina
 * @version ZS-2024, 2025-01-12
 */
public class Riddle {
    private final String question;
    private final String answer;

    /**
     * Konstruktor vytváří novou instanci hádanky se zadanou otázkou a odpovědí.
     *
     * @param question otázka hádanky
     * @param answer správná odpověď na hádanku
     */
    public Riddle(String question, String answer) {
        this.question = question;
        this.answer = answer.toLowerCase();
    }

    /**
     * Metoda vrací otázku hádanky.
     *
     * @return otázka hádanky
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Metoda ověřuje správnost odpovědi hráče.
     * Porovnává hráčovu odpověď se správnou odpovědí bez ohledu na velikost písmen
     * a ořezává případné mezery na začátku a na konci odpovědi.
     *
     * @param playerAnswer odpověď hráče
     * @return {@code true}, pokud odpověď odpovídá správné odpovědi; jinak {@code false}
     */
    public boolean checkAnswer(String playerAnswer) {
        return this.answer.equalsIgnoreCase(playerAnswer.trim());
    }
}