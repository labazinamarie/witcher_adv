package cz.vse.adv_witcher_labm02.main.game.commands;
import java.util.*;
import cz.vse.adv_witcher_labm02.main.game.*;

/**
 * Třída implementující příkaz pro kombinování předmětů z inventáře.
 * Hráč může použít tento příkaz k vytvoření nového předmětu spojením více existujících
 * předmětů podle zadaných receptů.
 *
 * @author Mariia Labazina
 * @version ZS-2024, 2025-01-12
 */
public class CommandCombineItems implements ICommand {
    private Game game;
    private Inventory inventory;
    private Set<Recipe> recipes;

    /**
     * Konstruktor třídy.
     *
     * @param game hra, ve které se bude příkaz používat
     */
    public CommandCombineItems(Game game) {
        this.game = game;
        this.inventory = game.getInventory();
        this.recipes = game.getRecipes();
    }

    /**
     * Metoda vrací název příkazu, tj.&nbsp;slovo <b>combine</b>.
     *
     * @return název příkazu
     */
    @Override
    public String getName() {
        return "combine";
    }

    /**
     * Metoda provádí kombinování více předmětů z inventáře podle dostupných receptů.
     * Nejprve zkontroluje, zda byly zadány názvy alespoň dvou předmětů. Pokud ne,
     * vrátí chybové hlášení. Poté zkontroluje, zda hráč má všechny zadané předměty
     * v inventáři. Pokud ano, pokusí se najít odpovídající kombinaci v receptech.
     * Pokud je kombinace nalezena, vytvoří nový předmět, přidá jej do inventáře
     * a odebere použité předměty.
     *
     * @param argumentsCommand parametry příkazu <i>(očekávají se názvy kombinovaných předmětů)</i>
     * @return zpráva potvrzující vytvoření nového předmětu nebo chybové hlášení
     */
    @Override
    public String run(String[] argumentsCommand) {
        if (argumentsCommand.length < 2) {
            return "Please specify at least two items to combine.";
        }
        Set<String> providedIngredients = new HashSet<>(Arrays.asList(argumentsCommand));

        for (String ingredient : providedIngredients) {
            if (!inventory.hasItem(ingredient)) {
                return "Item '" + ingredient + "' not found in inventory.";
            }
        }

        Recipe matchingRecipe = findMatchingRecipe(providedIngredients);
        if (matchingRecipe == null) {
            return "No valid combination found for " + String.join(", ", argumentsCommand) + ".";
        }

        String resultItemName = matchingRecipe.getResult();
        String newItemDescription = "A combination of " + String.join(", ", providedIngredients);
        String newItemPurpose = "Used for advanced crafting.";
        Item newItem = new Item(resultItemName, newItemDescription, true, newItemPurpose, 1, 1);

        inventory.addItem(newItem);
        for (String ingredient : providedIngredients) {
            Item item = inventory.getItemByName(ingredient);
            reduceItemQuantity(item);
        }

        game.notifyInventoryChanged();
        return "You combined " + String.join(", ", providedIngredients) + " to create " + newItem.getName() + ".";
    }

    /**
     * Finds a matching recipe for the given ingredients.
     *
     * @param providedIngredients the set of ingredients provided by the player
     * @return the matching recipe, or null if no match is found
     */
    private Recipe findMatchingRecipe(Set<String> providedIngredients) {
        for (Recipe recipe : recipes) {
            if (recipe.matches(providedIngredients)) {
                return recipe;
            }
        }
        return null;
    }

    /**
     * Pomocná metoda pro snížení množství použitého předmětu v inventáři.
     * Pokud má předmět množství větší než 1, sníží jeho počet. Pokud má pouze jednu
     * jednotku, odstraní jej z inventáře.
     *
     * @param item předmět, jehož množství se má snížit
     */
    private void reduceItemQuantity(Item item) {
        int currentQuantity = item.getQuantity();
        if (currentQuantity > 1) {
            item.setQuantity(currentQuantity - 1);
        } else {
            inventory.removeItem(item.getName());
        }
    }
}