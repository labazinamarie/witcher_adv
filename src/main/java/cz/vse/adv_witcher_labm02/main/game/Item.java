package cz.vse.adv_witcher_labm02.main.game;

/**
 * Třída {@code Item} představuje herní předmět s různými vlastnostmi, jako je
 * možnost pohybu, konzumovatelnost, svítivost, zamčení, čtení atd.
 *
 * Předměty mohou být přenosné, uzamčené, konzumovatelné nebo čitelné.
 *
 * @author Mariia Labazina
 * @version ZS-2024, 2025-01-12
 */
public class Item {
    private String name;
    private String description;
    private boolean movable;
    private String purpose;
    private int quantity;
    private boolean locked;
    private String keyRequired;
    private boolean consumable;
    private int charges;
    private boolean lightable;
    private boolean isLit;
    private boolean readable;
    private String textContent;
    private int weight;


    /**
     * Hlavní konstruktor vytváří předmět s plnou konfigurací vlastností.
     *
     * @param name        název předmětu
     * @param description popis předmětu
     * @param movable     zda lze předmět přenášet
     * @param purpose     účel předmětu
     * @param quantity    množství předmětu
     * @param locked      zda je předmět zamčený
     * @param keyRequired klíč potřebný k odemčení (nebo {@code null})
     * @param consumable  zda je předmět konzumovatelný
     * @param lightable   zda lze předmět zapálit
     * @param isLit       zda je předmět právě zapálený
     * @param readable    zda lze předmět přečíst
     * @param textContent obsah čitelného předmětu
     */
    public Item(String name, String description, boolean movable, String purpose, int quantity,
                boolean locked, String keyRequired, boolean consumable, boolean lightable,
                boolean isLit, boolean readable, String textContent, int weight) {
        this.name = name;
        this.description = description;
        this.movable = movable;
        this.purpose = purpose;
        this.quantity = Math.max(quantity, 1);
        this.locked = locked;
        this.keyRequired = keyRequired;
        this.consumable = consumable;
        this.charges = consumable ? 3 : 0;
        this.lightable = lightable;
        this.isLit = isLit;
        this.readable = readable;
        this.textContent = textContent;
        this.weight = weight;
    }

    /**
     * Konstruktor pro běžné předměty, které lze přenášet a nejsou zamčené.
     */
    public Item(String name, String description, boolean movable, String purpose, int quantity, int weight ) {
        this(name, description, movable, purpose, quantity, false, null, false, false, false, false, null, weight);
    }

    /**
     * Konstruktor pro nepohyblivé předměty.
     */
    public Item(String name, String description, String purpose, int quantity) {
        this(name, description, false, purpose, quantity,
                false, null, false, false, false, false, null, 100);
    }

    /**
     * Konstruktor pro zamčené předměty.
     */
    public Item(String name, String description, boolean movable, String purpose, int quantity, String keyRequired, int weight) {
        this(name, description, movable, purpose, quantity,
                true, keyRequired, false, false, false, false, null, weight );
    }

    /**
     * Konstruktor pro konzumovatelné předměty (lektvary, oleje).
     */
    public Item(String name, String description, boolean movable, String purpose, int quantity, boolean consumable, int weight) {
        this(name, description, movable, purpose, quantity,
                false, null, consumable, false, false, false, null, weight);
    }

    /**
     * Konstruktor pro zapalovatelné předměty (pochodně, lampy).
     */
    public Item(String name, String description, boolean movable, String purpose, int quantity, boolean lightable, boolean isLit, int weight) {
        this(name, description, movable, purpose, quantity,
                false, null, false, lightable, isLit, false, null, weight);
    }

    /**
     * Konstruktor pro předměty, které mohou být jak konzumovatelné, tak zapalovatelné.
     */
    public Item(String name, String description, boolean lightable, boolean consumable, int weight) {
        this(name, description, true, "No specific purpose defined.", 1,
                false, null, consumable, lightable, false, false, null, weight);
    }

    /**
     * Konstruktor s výchozím množstvím (1).
     */
    public Item(String name, String description) {
        this(name, description, true, "No specific purpose defined.", 1,
                false, null, false, false, false, false, null, 1);
    }

    /**
     * Konstruktor pro čitelné předměty s textovým obsahem.
     */
    public Item(String name, String description, boolean movable, String purpose, int quantity, boolean lightable, String textContent, int weight) {
        this(name, description, movable, purpose, quantity,
                false, null, lightable,  false, false, true, textContent, weight);
    }


    /**
     * Vrací jmeno předmětu.
     *
     * @return jmeno předmětu
     */
    public String getName() {
        return name;
    }
    /**
     * Vrací popis předmětu.
     *
     * @return popis předmětu
     */
    public String getDescription() {
        return description;
    }

    /**
     * Kontroluje, zda je předmět přenositelný.
     *
     * @return {@code true}, pokud je předmět přenositelný; jinak {@code false}
     */
    public boolean isMovable() {
        return movable;
    }

    /**
     * Vrací účel předmětu.
     *
     * @return účel předmětu
     */
    public String getPurpose() {
        return purpose;
    }

    /**
     * Vrací množství daného předmětu.
     *
     * @return množství předmětu
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Kontroluje, zda je předmět zamčený.
     *
     * @return {@code true}, pokud je předmět zamčený; jinak {@code false}
     */
    public boolean isLocked() {
        return locked;
    }

    /**
     * Vrací klíč potřebný k odemčení předmětu.
     *
     * @return název klíče, pokud je potřeba; jinak {@code null}
     */
    public String getKeyRequired() {
        return keyRequired;
    }

    /**
     * Kontroluje, zda je předmět konzumovatelný.
     *
     * @return {@code true}, pokud je předmět konzumovatelný; jinak {@code false}
     */
    public boolean isConsumable() {
        return consumable;
    }

    /**
     * Kontroluje, zda lze předmět zapálit.
     *
     * @return {@code true}, pokud lze předmět zapálit; jinak {@code false}
     */
    public boolean canBeLit() {
        return lightable;
    }

    /**
     * Kontroluje, zda je předmět zapálený.
     *
     * @return {@code true}, pokud je předmět zapálený; jinak {@code false}
     */
    public boolean isLit() {
        return isLit;
    }

    /**
     * Vrací počet zbývajících použití předmětu.
     *
     * @return počet zbývajících použití
     */
    public int getCharges() {
        return charges;
    }

    /**
     * Kontroluje, zda je předmět čitelný.
     *
     * @return {@code true}, pokud je předmět čitelný; jinak {@code false}
     */
    public boolean isReadable() {
        return readable;
    }

    /**
     * Vrací obsah čitelného předmětu.
     *
     * @return obsah předmětu (text)
     */
    public String getTextContent() {
        return textContent;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    /**
     * Nastaví obsah čitelného předmětu.
     *
     * @param textContent nový text, který má být nastaven
     */
    public void setTextContent(String textContent) {
        if (readable) {
            this.textContent = textContent;
        }
    }

    /**
     * Nastaví množství předmětu. Zabraňuje nastavení záporných hodnot.
     *
     * @param quantity nové množství předmětu
     */
    public void setQuantity(int quantity) {
        this.quantity = Math.max(quantity, 0);
    }

    /**
     * Uzamkne předmět a nastaví potřebný klíč.
     *
     * @param key klíč potřebný k odemčení předmětu
     */
    public void lock(String key) {
        this.locked = true;
        this.keyRequired = key;
    }

    /**
     * Pokusí se odemknout předmět pomocí zadaného klíče.
     *
     * @param key klíč, který má být použit k odemčení
     * @return {@code true}, pokud byl předmět úspěšně odemčen; jinak {@code false}
     */
    public boolean unlock(String key) {
        if (!locked) {
            return false;
        }
        if (key != null && key.equalsIgnoreCase(keyRequired)) {
            locked = false;
            keyRequired = null;
            return true;
        }
        return false;
    }

    /**
     * Zapálí předmět, pokud je zapalovatelný a není již zapálený.
     */
    public void light() {
        if (lightable&&!isLit) {
            isLit = true;
        }
    }

    /**
     * Uhasí předmět, pokud je zapálený.
     */
    public void extinguish() {
        if (lightable&&isLit) {
            isLit = false;
        }
    }

    /**
     * Použije předmět, pokud je konzumovatelný a má zbývající použití.
     */
    public void use() {
        if (consumable && charges > 0) {
            charges--;
        }
    }

    /**
     * Obnoví počet použití konzumovatelného předmětu na maximální hodnotu.
     */
    public void refill() {
        if (consumable) {
            charges = 3;
        }
    }

    /**
     * Vrací detailní popis předmětu včetně jeho vlastností.
     *
     * @return detailní popis předmětu
     */
    public String getDetailedDescription() {
        return name + " (" + description + ") - " + quantity + "x"
                + (locked ? " [Locked]" : "")
                + (lightable ? (isLit ? " [Lit]" : " [Not Lit]") : "")
                + (consumable ? " [Uses Left: " + charges + "]" : "")
                + (readable ? " [Readable]" : "")
                + weight;
    }
}
