package cz.vse.adv_witcher_labm02.main.game;
import cz.vse.adv_witcher_labm02.main.start.Subject;

public interface IGame extends Subject {

        public String getPrologue();

        /**
         *  Vrátí závěrečnou zprávu pro hráče.
         */
        public String getEpilogue();

        /**
         * Vrací true, pokud hra skončila.
         */
        public boolean isGameOver();

        /**
         *  Metoda zpracuje řetězec uvedený jako parametr, rozdělí ho na slovo příkazu a další parametry.
         *  Pak otestuje zda příkaz je klíčovým slovem  např. jdi.
         *  Pokud ano spustí samotné provádění příkazu.
         *
         *@param  line  text, který zadal uživatel jako příkaz do hry.
         *@return          vrací se řetězec, který se má vypsat na obrazovku
         */
        public String processAction(String line);


        /**
         *  Metoda vrátí odkaz na herní plán, je využita hlavně v testech,
         *  kde se jejím prostřednictvím získává aktualní místnost hry.
         *
         *  @return     odkaz na herní plán
         */
        public GameWorld getGameWorld();

        Inventory getInventory();
        void notifyInventoryChanged();


    }

