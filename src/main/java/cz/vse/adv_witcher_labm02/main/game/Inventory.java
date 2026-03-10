package cz.vse.adv_witcher_labm02.main.game;

import java.util.*;
/**
 * Třída {@code Inventory} spravuje herní inventář, umožňuje přidávání, odebírání
 * a manipulaci s předměty, včetně interakcí jako je osvětlení nebo čtení předmětů.
 * Používá {@code HashMap} pro rychlé vyhledávání položek.
 *
 * @author Mariia Labazina
 * @version ZS-2024, 2025-01-12
 */
public class Inventory {
    private Map<String, Item> items;
    private int weightCount;

    /**
     * Konstruktor třídy {@code Inventory}. Inicializuje prázdnou mapu předmětů.
     */
    public Inventory() {
        items = new HashMap<>();
        weightCount = 0;
    }


    /**
     * Přidá předmět do inventáře. Pokud už předmět existuje, zvětší jeho množství.
     *
     */
    public boolean isFull(){
        if (weightCount < 10){
            return false;
        }
        return true;
    }
    public void addItem(Item item) {
        if (!isFull()) {
            String itemName = item.getName().toLowerCase();

            if (items.containsKey(itemName)) {
                Item existingItem = items.get(itemName);
                existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
                weightCount += item.getWeight();

            } else {
                items.put(itemName, item);
                weightCount += item.getWeight();
            }
        } else {
            System.out.println("Spare the Roach, it's heavy enough already.Your inventory is full.");
        }
    }

    /**
     * Odebere předmět z inventáře. Pokud má více kusů, sníží množství, jinak jej odstraní.
     *
     * @param itemName název předmětu, který má být odstraněn
     */
    public void removeItem(String itemName) {
        String lowerName = itemName.toLowerCase();

        if (!items.containsKey(lowerName)) {
            System.out.println("Item not found in inventory.");
            return;
        }

        Item item = items.get(lowerName);
        if (item.getQuantity() > 1) {
            item.setQuantity(item.getQuantity() - 1);
            weightCount -= item.getWeight();
            System.out.println(item.getName() + " quantity decreased to " + item.getQuantity() + ". And you have freed up some space.");
        } else {
            items.remove(lowerName);
            weightCount -= item.getWeight();
        }
    }

    /**
     * Vrací obsah inventáře jako řetězec.
     *
     * @return řetězcová reprezentace obsahu inventáře
     */
    public String viewInventory() {
        if (items.isEmpty()) {
            return "Your inventory is empty.";
        }

        StringBuilder inventoryContents = new StringBuilder("Inventory:\n");
        for (Item item : items.values()) {
            inventoryContents.append(" - ").append(item.getName())
                    .append(": ").append(item.getDescription()).append("\n");
        }

        return inventoryContents.toString();
    }

    /**
     * Vyhledá předmět v inventáři podle názvu.
     *
     * @param itemName název předmětu
     * @return objekt {@code Item}, pokud existuje; jinak {@code null}
     */
    public Item getItemByName(String itemName) {
        return items.get(itemName.toLowerCase());
    }

    /**
     * Kontroluje, zda inventář obsahuje daný předmět.
     *
     * @param itemName název předmětu
     * @return {@code true}, pokud je předmět v inventáři, jinak {@code false}
     */
    public boolean hasItem(String itemName) {
        return items.containsKey(itemName.toLowerCase());
    }

    /**
     * Vymaže veškerý obsah inventáře.
     */
    public void clearInventory() {
        items.clear();
    }

    /**
     * Vrací seznam všech předmětů v inventáři.
     *
     * @return mapa obsahující předměty v inventáři
     */
    public Map<String, Item> getItems() {
        return items;
    }
}


