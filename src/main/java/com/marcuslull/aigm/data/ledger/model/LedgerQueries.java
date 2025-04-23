package com.marcuslull.aigm.data.ledger.model;

public class LedgerQueries {

    public static final String armor =
            """
            SELECT * FROM rules.dnd5e.item WHERE item_type = 'armor';
            """;

    public static String armorByName =
            """
            SELECT * FROM rules.dnd5e.item WHERE item_name = ?;
            """;

    public static final String character =
            """
            SELECT * FROM rules.dnd5e.character;
            """;

    public static String characterByName =
            """
            SELECT * FROM rules.dnd5e.character WHERE character_name = ?;
            """;

    public static final String feat =
            """
            SELECT * FROM rules.dnd5e.feat;
            """;

    public static String featByName =
            """
            SELECT * FROM rules.dnd5e.feat WHERE name = ?;
            """;

    public static final String item =
            """
            SELECT * FROM rules.dnd5e.item;
            """;

    public static String itemByName =
            """
            SELECT * FROM rules.dnd5e.item WHERE name = ?;
            """;

    public static final String spell =
            """
            SELECT * FROM rules.dnd5e.spell;
            """;

    public static String spellByName =
            """
            SELECT * FROM rules.dnd5e.spell WHERE spell_name = ?;
            """;

    public static final String weapon =
            """
            SELECT * FROM rules.dnd5e.item WHERE item_type = 'weapon';
            """;

    public static String weaponByName =
            """
            SELECT * FROM rules.dnd5e.item WHERE item_name = ?;
            """;
}
