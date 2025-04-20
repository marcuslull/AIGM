package com.marcuslull.aigm.data.ledger.model;

public class LedgerQueries {

    public static final String armor =
            """
            SELECT * FROM item WHERE item_type = 'armor';
            """;

    public static String armorByName =
            """
            SELECT * FROM item WHERE item_name = ?;
            """;

    public static final String character =
            """
            SELECT * FROM character;
            """;

    public static String characterByName =
            """
            SELECT * FROM character WHERE character_name = ?;
            """;

    public static final String feat =
            """
            SELECT * FROM feat;
            """;

    public static String featByName =
            """
            SELECT * FROM feat WHERE name = ?;
            """;

    public static final String item =
            """
            SELECT * FROM item;
            """;

    public static String itemByName =
            """
            SELECT * FROM item WHERE name = ?;
            """;

    public static final String spell =
            """
            SELECT * FROM spell;
            """;

    public static String spellByName =
            """
            SELECT * FROM spell WHERE spell_name = ?;
            """;

    public static final String weapon =
            """
            SELECT * FROM item WHERE item_type = 'Weapon';
            """;

    public static String weaponByName =
            """
            SELECT * FROM item WHERE item_name = ?;
            """;
}
