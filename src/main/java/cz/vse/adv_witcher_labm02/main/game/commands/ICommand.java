package cz.vse.adv_witcher_labm02.main.game.commands;

/**
 * Rozhraní {@code ICommand} definující základní strukturu pro herní příkazy.
 * Každý příkaz implementující toto rozhraní musí poskytnout metody pro získání
 * názvu příkazu a jeho vykonání.
 *
 * @author Mariia Labazina
 * @version ZS-2024, 2025-01-12
 */
public interface ICommand{

    /**
     * Metoda vrací název příkazu.
     * Název je textová reprezentace, kterou hráč zadává k vykonání příkazu.
     *
     * @return název příkazu
     */
    String getName();

    /**
     * Metoda vykoná příkaz na základě poskytnutých parametrů.
     * Implementace definuje konkrétní logiku příkazu. Parametry příkazu se
     * předávají jako pole řetězců.
     *
     * @param argumentsCommand parametry příkazu <i>(mohou obsahovat např. názvy předmětů, lokací, postav apod.)</i>
     * @return textová zpráva s výsledkem vykonání příkazu
     */
    String run(String[] argumentsCommand);
}