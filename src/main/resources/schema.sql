-- AI GENERATED DDL!!!
-- AI GENERATED DDL!!!
-- AI GENERATED DDL!!!
-- AI GENERATED DDL!!!
-- REFACTOR BEFORE ACTUAL USE!!!


-- Optional: Create a schema to namespace the tables
CREATE SCHEMA IF NOT EXISTS dnd5e;
SET search_path TO dnd5e;

-- Drop tables in reverse order of dependency for easy script re-run
DROP TABLE IF EXISTS character_inventory CASCADE;
DROP TABLE IF EXISTS character_spell CASCADE;
DROP TABLE IF EXISTS character_class_level CASCADE;
DROP TABLE IF EXISTS character_skill_proficiency CASCADE;
DROP TABLE IF EXISTS character_saving_throw_proficiency CASCADE;
DROP TABLE IF EXISTS character_language CASCADE;
DROP TABLE IF EXISTS character_tool_proficiency CASCADE;
DROP TABLE IF EXISTS character_armor_proficiency CASCADE;
DROP TABLE IF EXISTS character_weapon_category_proficiency CASCADE;
DROP TABLE IF EXISTS character_specific_weapon_proficiency CASCADE;
DROP TABLE IF EXISTS character_feat CASCADE;
DROP TABLE IF EXISTS character_ability_score CASCADE;
DROP TABLE IF EXISTS "character" CASCADE; -- Quoted because character can be a reserved word in some contexts
DROP TABLE IF EXISTS player CASCADE;
DROP TABLE IF EXISTS race CASCADE;
DROP TABLE IF EXISTS "class" CASCADE; -- Quoted because class is a reserved word
DROP TABLE IF EXISTS background CASCADE;
DROP TABLE IF EXISTS alignment CASCADE;
DROP TABLE IF EXISTS ability_score_type CASCADE;
DROP TABLE IF EXISTS skill_type CASCADE;
DROP TABLE IF EXISTS saving_throw_type CASCADE;
DROP TABLE IF EXISTS item CASCADE;
DROP TABLE IF EXISTS spell CASCADE;
DROP TABLE IF EXISTS language CASCADE;
DROP TABLE IF EXISTS tool CASCADE;
DROP TABLE IF EXISTS armor_proficiency_category CASCADE;
DROP TABLE IF EXISTS weapon_proficiency_category CASCADE;
DROP TABLE IF EXISTS feat CASCADE;

-- ===== Core Definition Tables (Lookup Tables) =====

CREATE TABLE player (
                        player_id SERIAL PRIMARY KEY,
                        player_name VARCHAR(100) NOT NULL UNIQUE,
                        email VARCHAR(255) UNIQUE, -- Optional
                        created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON TABLE player IS 'Represents the person playing the character.';

CREATE TABLE alignment (
                           alignment_id SERIAL PRIMARY KEY,
                           alignment_name VARCHAR(50) NOT NULL UNIQUE, -- e.g., 'Lawful Good', 'Chaotic Neutral'
                           abbreviation VARCHAR(2) UNIQUE -- e.g., 'LG', 'CN'
);
COMMENT ON TABLE alignment IS 'Possible character alignments (Lawful Good, Neutral Evil, etc.).';

CREATE TABLE ability_score_type (
                                    ability_score_type_id SERIAL PRIMARY KEY,
                                    name VARCHAR(20) NOT NULL UNIQUE, -- Strength, Dexterity, Constitution, Intelligence, Wisdom, Charisma
                                    abbreviation VARCHAR(3) NOT NULL UNIQUE -- STR, DEX, CON, INT, WIS, CHA
);
COMMENT ON TABLE ability_score_type IS 'The six core ability scores in D&D 5e.';

CREATE TABLE race (
                      race_id SERIAL PRIMARY KEY,
                      race_name VARCHAR(100) NOT NULL UNIQUE,
                      description TEXT,
                      base_speed INTEGER NOT NULL CHECK (base_speed >= 0), -- Base walking speed in feet
                      size VARCHAR(20) NOT NULL -- e.g., Small, Medium, Large
    -- Racial traits, ability score increases etc. could be linked via other tables or stored as JSONB/text if simpler
);
COMMENT ON TABLE race IS 'Defines character races like Elf, Dwarf, Human.';

CREATE TABLE "class" (
                         class_id SERIAL PRIMARY KEY,
                         class_name VARCHAR(100) NOT NULL UNIQUE,
                         description TEXT,
                         hit_die INTEGER NOT NULL CHECK (hit_die IN (6, 8, 10, 12)), -- d6, d8, d10, d12
                         primary_ability_ids INTEGER[] -- Array of FKs to ability_score_type, if desired
    -- Class features, spellcasting progression etc. could be linked via other tables
);
COMMENT ON TABLE "class" IS 'Defines character classes like Fighter, Wizard, Rogue.';

CREATE TABLE background (
                            background_id SERIAL PRIMARY KEY,
                            background_name VARCHAR(100) NOT NULL UNIQUE,
                            description TEXT,
                            feature_name VARCHAR(255),
                            feature_description TEXT
    -- Skill proficiencies, tool proficiencies, languages, equipment granted by background are often defined here but linked via separate character proficiency tables.
);
COMMENT ON TABLE background IS 'Defines character backgrounds like Acolyte, Criminal, Sage.';

CREATE TABLE skill_type (
                            skill_type_id SERIAL PRIMARY KEY,
                            name VARCHAR(50) NOT NULL UNIQUE, -- e.g., Acrobatics, History, Stealth
                            governing_ability_score_type_id INTEGER NOT NULL REFERENCES ability_score_type(ability_score_type_id) ON DELETE RESTRICT
);
COMMENT ON TABLE skill_type IS 'Defines the available skills and their governing ability score.';
CREATE INDEX idx_skill_type_governing_ability ON skill_type(governing_ability_score_type_id);

CREATE TABLE saving_throw_type (
                                   saving_throw_type_id SERIAL PRIMARY KEY,
                                   name VARCHAR(50) NOT NULL UNIQUE, -- e.g., Strength Save, Dexterity Save
                                   governing_ability_score_type_id INTEGER NOT NULL REFERENCES ability_score_type(ability_score_type_id) ON DELETE RESTRICT
);
COMMENT ON TABLE saving_throw_type IS 'Defines the saving throws based on ability scores.';
CREATE INDEX idx_saving_throw_type_governing_ability ON saving_throw_type(governing_ability_score_type_id);

CREATE TABLE item (
                      item_id SERIAL PRIMARY KEY,
                      item_name VARCHAR(255) NOT NULL UNIQUE,
                      description TEXT,
                      weight NUMERIC(6, 2) DEFAULT 0 CHECK (weight >= 0), -- Weight in pounds/kg
                      cost_cp INTEGER DEFAULT 0 CHECK (cost_cp >= 0), -- Cost in copper pieces for granularity
                      item_type VARCHAR(50) NOT NULL CHECK (item_type IN ('weapon', 'armor', 'shield', 'potion', 'scroll', 'wondrous', 'gear', 'tool', 'currency')),
                      requires_attunement BOOLEAN DEFAULT FALSE,
                    armor_proficiency VARCHAR(50) CHECK (armor_proficiency IN ('light', 'medium', 'heavy', 'shield')),
                    armor_class VARCHAR(50),
                    armor_strength_requirement VARCHAR(50),
                    armor_stealth VARCHAR(50) CHECK (armor_stealth IN ('disadvantage')),
                    armor_don_doff VARCHAR(50),
                    weapon_proficiency VARCHAR(50) CHECK (weapon_proficiency IN ('simple melee', 'simple ranged', 'martial melee', 'martial ranged')),
                    weapon_damage VARCHAR(50),
                    weapon_damage_type VARCHAR(50) CHECK ( weapon_damage_type IN ('bludgeoning', 'piercing', 'slashing')),
                    weapon_properties VARCHAR(255),
                    capacity VARCHAR(50),
                    movement_speed INTEGER DEFAULT 0 CHECK (movement_speed >= 0)
);
COMMENT ON TABLE item IS 'Catalog of all possible items (weapons, armor, gear, etc.).';
CREATE INDEX idx_item_name ON item(item_name);
CREATE INDEX idx_item_type ON item(item_type);

CREATE TABLE spell (
                       spell_id SERIAL PRIMARY KEY,
                       spell_name VARCHAR(255) NOT NULL UNIQUE,
                       description TEXT NOT NULL,
                       higher_level_description TEXT,
                       level SMALLINT NOT NULL CHECK (level BETWEEN 0 AND 9), -- 0 for cantrips
                       school VARCHAR(50), -- e.g., Evocation, Conjuration
                       casting_time VARCHAR(100),
                       range VARCHAR(100),
                       components VARCHAR(50), -- e.g., V, S, M
                       material_component_description TEXT,
                       duration VARCHAR(100),
                       is_ritual BOOLEAN DEFAULT FALSE,
                       requires_concentration BOOLEAN DEFAULT FALSE
    -- Could add fields for damage, saving throw type, etc.
);
COMMENT ON TABLE spell IS 'Catalog of all available spells.';
CREATE INDEX idx_spell_name ON spell(spell_name);
CREATE INDEX idx_spell_level ON spell(level);
CREATE INDEX idx_spell_school ON spell(school);

CREATE TABLE language (
                          language_id SERIAL PRIMARY KEY,
                          name VARCHAR(50) NOT NULL UNIQUE, -- e.g., Common, Elvish, Dwarvish
                          script VARCHAR(50) -- e.g., Common, Elven, Dwarven
);
COMMENT ON TABLE language IS 'Defines available languages.';

CREATE TABLE tool (
                      tool_id SERIAL PRIMARY KEY,
                      name VARCHAR(100) NOT NULL UNIQUE, -- e.g., Thieves' Tools, Cook's Utensils, Lute
                      tool_type VARCHAR(50) CHECK (tool_type IN ('artisan', 'gaming', 'musical', 'other'))
);
COMMENT ON TABLE tool IS 'Defines available tools characters can be proficient with.';

CREATE TABLE armor_proficiency_category (
                                            armor_prof_cat_id SERIAL PRIMARY KEY,
                                            name VARCHAR(50) NOT NULL UNIQUE -- e.g., Light, Medium, Heavy, Shields
);
COMMENT ON TABLE armor_proficiency_category IS 'Categories of armor proficiency.';

CREATE TABLE weapon_proficiency_category (
                                             weapon_prof_cat_id SERIAL PRIMARY KEY,
                                             name VARCHAR(50) NOT NULL UNIQUE -- e.g., Simple, Martial
);
COMMENT ON TABLE weapon_proficiency_category IS 'Categories of weapon proficiency.';

CREATE TABLE feat (
                      feat_id SERIAL PRIMARY KEY,
                      name VARCHAR(100) NOT NULL UNIQUE,
                      description TEXT NOT NULL,
                      prerequisites TEXT -- Could be structured more formally if needed
);
COMMENT ON TABLE feat IS 'Defines available feats.';

-- ===== Core Character Table =====

CREATE TABLE "character" (
                             character_id BIGSERIAL PRIMARY KEY, -- Use BIGSERIAL if you expect millions+ of characters
                             player_id INTEGER REFERENCES player(player_id) ON DELETE SET NULL, -- Character might exist without a current player
                             character_name VARCHAR(255) NOT NULL,
                             race_id INTEGER NOT NULL REFERENCES race(race_id) ON DELETE RESTRICT,
                             background_id INTEGER NOT NULL REFERENCES background(background_id) ON DELETE RESTRICT,
                             alignment_id INTEGER REFERENCES alignment(alignment_id) ON DELETE RESTRICT, -- Can be null if not set

                             experience_points INTEGER DEFAULT 0 CHECK (experience_points >= 0),
    -- Level is derived from character_class_level sum

                             hit_points_max INTEGER DEFAULT 1 CHECK (hit_points_max >= 0),
                             hit_points_current INTEGER DEFAULT 1,
                             hit_points_temporary INTEGER DEFAULT 0,

    -- Hit dice are tracked per class level in character_class_level

                             armor_class_base INTEGER DEFAULT 10, -- Base AC (10 + Dex mod, or calculated from armor) - Storing base allows easier overrides
    -- Total AC is usually calculated based on equipped armor, dex, shield, spells, etc.

                             speed_current INTEGER DEFAULT 30 CHECK (speed_current >= 0), -- Current speed, potentially modified from race base speed

                             initiative_bonus_override INTEGER, -- Optional: To store explicit bonus/penalty not derived from Dex

                             death_save_successes SMALLINT DEFAULT 0 CHECK (death_save_successes BETWEEN 0 AND 3),
                             death_save_failures SMALLINT DEFAULT 0 CHECK (death_save_failures BETWEEN 0 AND 3),

                             inspiration BOOLEAN DEFAULT FALSE,

                             personality_traits TEXT,
                             ideals TEXT,
                             bonds TEXT,
                             flaws TEXT,
                             backstory TEXT,
                             appearance TEXT,
                             allies_organizations TEXT,

                             copper_pieces INTEGER DEFAULT 0 CHECK (copper_pieces >= 0),
                             silver_pieces INTEGER DEFAULT 0 CHECK (silver_pieces >= 0),
                             electrum_pieces INTEGER DEFAULT 0 CHECK (electrum_pieces >= 0),
                             gold_pieces INTEGER DEFAULT 0 CHECK (gold_pieces >= 0),
                             platinum_pieces INTEGER DEFAULT 0 CHECK (platinum_pieces >= 0),

                             created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
                             updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,

    -- Ensure character names are unique per player if desired
    -- UNIQUE (player_id, character_name),

    -- Check current HP doesn't exceed max HP (can be tricky with temp HP logic)
                             CONSTRAINT check_hp CHECK (hit_points_current <= hit_points_max)
);
COMMENT ON TABLE "character" IS 'The central table representing a single D&D character.';
CREATE INDEX idx_character_player_id ON "character"(player_id);
CREATE INDEX idx_character_name ON "character"(character_name);
CREATE INDEX idx_character_race_id ON "character"(race_id);
CREATE INDEX idx_character_background_id ON "character"(background_id);
CREATE INDEX idx_character_alignment_id ON "character"(alignment_id);

-- Trigger to update 'updated_at' timestamp automatically (Optional but recommended)
-- CREATE OR REPLACE FUNCTION trigger_set_timestamp()
--     RETURNS TRIGGER AS $$
-- BEGIN
--     NEW.updated_at = NOW();
--     RETURN NEW;
-- END;
-- $$ LANGUAGE plpgsql;
--
-- CREATE TRIGGER set_timestamp
--     BEFORE UPDATE ON "character"
--     FOR EACH ROW
-- EXECUTE FUNCTION trigger_set_timestamp();


-- ===== Linking Tables (Many-to-Many Relationships & Character Specific Data) =====

CREATE TABLE character_ability_score (
                                         character_id BIGINT NOT NULL REFERENCES "character"(character_id) ON DELETE CASCADE,
                                         ability_score_type_id INTEGER NOT NULL REFERENCES ability_score_type(ability_score_type_id) ON DELETE RESTRICT,
                                         score_value SMALLINT NOT NULL CHECK (score_value BETWEEN 1 AND 30), -- Base score value
    -- Score modifier is typically calculated: floor((score_value - 10) / 2)
                                         PRIMARY KEY (character_id, ability_score_type_id)
);
COMMENT ON TABLE character_ability_score IS 'Stores the base ability scores for each character.';

CREATE TABLE character_class_level (
                                       character_id BIGINT NOT NULL REFERENCES "character"(character_id) ON DELETE CASCADE,
                                       class_id INTEGER NOT NULL REFERENCES "class"(class_id) ON DELETE RESTRICT,
                                       level SMALLINT NOT NULL CHECK (level > 0),
                                       hit_dice_spent SMALLINT DEFAULT 0 CHECK (hit_dice_spent >= 0),
    -- Ensure hit dice spent does not exceed level for this class
                                       CONSTRAINT check_hit_dice_spent CHECK (hit_dice_spent <= level),
                                       PRIMARY KEY (character_id, class_id)
);
COMMENT ON TABLE character_class_level IS 'Tracks character levels in one or more classes (supports multiclassing). Tracks spent hit dice per class.';
CREATE INDEX idx_character_class_level_class_id ON character_class_level(class_id); -- Also useful for finding all characters of a class

CREATE TABLE character_skill_proficiency (
                                             character_id BIGINT NOT NULL REFERENCES "character"(character_id) ON DELETE CASCADE,
                                             skill_type_id INTEGER NOT NULL REFERENCES skill_type(skill_type_id) ON DELETE RESTRICT,
                                             has_proficiency BOOLEAN DEFAULT TRUE, -- Can be set to FALSE if needed, though usually rows just exist for proficiency
                                             has_expertise BOOLEAN DEFAULT FALSE,
                                             source VARCHAR(100), -- Optional: e.g., 'Race', 'Class', 'Background', 'Feat'
                                             PRIMARY KEY (character_id, skill_type_id)
);
COMMENT ON TABLE character_skill_proficiency IS 'Tracks which skills a character is proficient or has expertise in.';

CREATE TABLE character_saving_throw_proficiency (
                                                    character_id BIGINT NOT NULL REFERENCES "character"(character_id) ON DELETE CASCADE,
                                                    saving_throw_type_id INTEGER NOT NULL REFERENCES saving_throw_type(saving_throw_type_id) ON DELETE RESTRICT,
                                                    has_proficiency BOOLEAN DEFAULT TRUE,
                                                    source VARCHAR(100), -- Optional: e.g., 'Class', 'Feat'
                                                    PRIMARY KEY (character_id, saving_throw_type_id)
);
COMMENT ON TABLE character_saving_throw_proficiency IS 'Tracks which saving throws a character is proficient in.';

CREATE TABLE character_language (
                                    character_id BIGINT NOT NULL REFERENCES "character"(character_id) ON DELETE CASCADE,
                                    language_id INTEGER NOT NULL REFERENCES language(language_id) ON DELETE RESTRICT,
                                    source VARCHAR(100), -- Optional
                                    PRIMARY KEY (character_id, language_id)
);
COMMENT ON TABLE character_language IS 'Languages known by the character.';

CREATE TABLE character_tool_proficiency (
                                            character_id BIGINT NOT NULL REFERENCES "character"(character_id) ON DELETE CASCADE,
                                            tool_id INTEGER NOT NULL REFERENCES tool(tool_id) ON DELETE RESTRICT,
                                            has_expertise BOOLEAN DEFAULT FALSE, -- Some features grant expertise with tools
                                            source VARCHAR(100), -- Optional
                                            PRIMARY KEY (character_id, tool_id)
);
COMMENT ON TABLE character_tool_proficiency IS 'Tool proficiencies for the character.';

CREATE TABLE character_armor_proficiency (
                                             character_id BIGINT NOT NULL REFERENCES "character"(character_id) ON DELETE CASCADE,
                                             armor_prof_cat_id INTEGER NOT NULL REFERENCES armor_proficiency_category(armor_prof_cat_id) ON DELETE RESTRICT,
                                             source VARCHAR(100), -- Optional
                                             PRIMARY KEY (character_id, armor_prof_cat_id)
);
COMMENT ON TABLE character_armor_proficiency IS 'Armor proficiency categories for the character (Light, Medium, Heavy, Shields).';

CREATE TABLE character_weapon_category_proficiency (
                                                       character_id BIGINT NOT NULL REFERENCES "character"(character_id) ON DELETE CASCADE,
                                                       weapon_prof_cat_id INTEGER NOT NULL REFERENCES weapon_proficiency_category(weapon_prof_cat_id) ON DELETE RESTRICT,
                                                       source VARCHAR(100), -- Optional
                                                       PRIMARY KEY (character_id, weapon_prof_cat_id)
);
COMMENT ON TABLE character_weapon_category_proficiency IS 'Weapon proficiency categories for the character (Simple, Martial).';

CREATE TABLE character_specific_weapon_proficiency (
                                                       character_id BIGINT NOT NULL REFERENCES "character"(character_id) ON DELETE CASCADE,
                                                       item_id INTEGER NOT NULL REFERENCES item(item_id) ON DELETE RESTRICT, -- FK to item table (ensure it's a weapon type via check or app logic)
                                                       source VARCHAR(100), -- Optional
                                                       PRIMARY KEY (character_id, item_id)
    -- CONSTRAINT check_item_is_weapon CHECK ( (SELECT item_type FROM item WHERE item.item_id = character_specific_weapon_proficiency.item_id) = 'Weapon' ) -- This check is complex and might be better handled at application level
);
COMMENT ON TABLE character_specific_weapon_proficiency IS 'Specific weapon proficiencies granted (e.g., Elves with Longswords).';

CREATE TABLE character_inventory (
                                     character_inventory_id BIGSERIAL PRIMARY KEY, -- Use a surrogate key for easier referencing/updates if needed
                                     character_id BIGINT NOT NULL REFERENCES "character"(character_id) ON DELETE CASCADE,
                                     item_id INTEGER NOT NULL REFERENCES item(item_id) ON DELETE RESTRICT,
                                     quantity INTEGER DEFAULT 1 CHECK (quantity >= 0),
                                     is_equipped BOOLEAN DEFAULT FALSE,
                                     is_attuned BOOLEAN DEFAULT FALSE,
                                     notes TEXT, -- For item-specific notes like charges left, custom details
    -- Optional: Ensure unique item per character if quantity is 1 and not stackable? App logic might be better.
                                     UNIQUE (character_id, item_id, is_equipped, is_attuned) -- A character likely has an item only once in inventory OR equipped OR attuned state? Maybe too restrictive. Remove if items can appear multiple times.
);
COMMENT ON TABLE character_inventory IS 'Items currently possessed by the character.';
CREATE INDEX idx_character_inventory_character_id ON character_inventory(character_id);
CREATE INDEX idx_character_inventory_item_id ON character_inventory(item_id);

CREATE TABLE character_spell (
                                 character_id BIGINT NOT NULL REFERENCES "character"(character_id) ON DELETE CASCADE,
                                 spell_id INTEGER NOT NULL REFERENCES spell(spell_id) ON DELETE RESTRICT,
                                 source_class_id INTEGER REFERENCES "class"(class_id) ON DELETE SET NULL, -- Which class granted the spell? (for multiclassing)
                                 is_prepared BOOLEAN DEFAULT FALSE, -- For prepared casters (Cleric, Wizard, Druid, Paladin)
                                 is_known BOOLEAN DEFAULT TRUE, -- For known casters (Sorcerer, Bard, Ranger, Warlock). Usually true if row exists.
                                 source VARCHAR(100), -- Optional: 'Class', 'Race', 'Feat', 'Item'
                                 PRIMARY KEY (character_id, spell_id) -- Assuming a character knows a spell only once
);
COMMENT ON TABLE character_spell IS 'Spells known or prepared by the character.';
CREATE INDEX idx_character_spell_spell_id ON character_spell(spell_id);
CREATE INDEX idx_character_spell_source_class_id ON character_spell(source_class_id);

CREATE TABLE character_feat (
                                character_id BIGINT NOT NULL REFERENCES "character"(character_id) ON DELETE CASCADE,
                                feat_id INTEGER NOT NULL REFERENCES feat(feat_id) ON DELETE RESTRICT,
                                source VARCHAR(100), -- Optional: 'Level Up', 'Variant Human'
                                PRIMARY KEY (character_id, feat_id)
);
COMMENT ON TABLE character_feat IS 'Feats acquired by the character.';
CREATE INDEX idx_character_feat_feat_id ON character_feat(feat_id);


-- ===== Example Data Insertion (Optional) =====
/*
-- Sample Ability Score Types
INSERT INTO ability_score_type (name, abbreviation) VALUES
('Strength', 'STR'), ('Dexterity', 'DEX'), ('Constitution', 'CON'),
('Intelligence', 'INT'), ('Wisdom', 'WIS'), ('Charisma', 'CHA');

-- Sample Alignment
INSERT INTO alignment (alignment_name, abbreviation) VALUES ('Lawful Good', 'LG'), ('Neutral Good', 'NG'), ('Chaotic Good', 'CG'), ('Lawful Neutral', 'LN'), ('True Neutral', 'N'), ('Chaotic Neutral', 'CN'), ('Lawful Evil', 'LE'), ('Neutral Evil', 'NE'), ('Chaotic Evil', 'CE');

-- Sample Race
INSERT INTO race (race_name, base_speed, size) VALUES ('Human', 30, 'Medium');

-- Sample Class
INSERT INTO "class" (class_name, hit_die) VALUES ('Fighter', 10);

-- Sample Background
INSERT INTO background (background_name, feature_name, feature_description) VALUES ('Soldier', 'Military Rank', 'You have a military rank from your career as a soldier...');

-- Sample Character
INSERT INTO "character" (character_name, race_id, background_id, alignment_id, experience_points, hit_points_max, hit_points_current, speed_current) VALUES
('Gregor Strongarm', (SELECT race_id FROM race WHERE race_name='Human'), (SELECT background_id FROM background WHERE background_name='Soldier'), (SELECT alignment_id FROM alignment WHERE abbreviation='LG'), 0, 12, 12, 30);

-- Sample Ability Scores for Character
INSERT INTO character_ability_score (character_id, ability_score_type_id, score_value) VALUES
((SELECT character_id FROM "character" WHERE character_name='Gregor Strongarm'), (SELECT ability_score_type_id FROM ability_score_type WHERE abbreviation='STR'), 16),
((SELECT character_id FROM "character" WHERE character_name='Gregor Strongarm'), (SELECT ability_score_type_id FROM ability_score_type WHERE abbreviation='DEX'), 12),
((SELECT character_id FROM "character" WHERE character_name='Gregor Strongarm'), (SELECT ability_score_type_id FROM ability_score_type WHERE abbreviation='CON'), 14),
((SELECT character_id FROM "character" WHERE character_name='Gregor Strongarm'), (SELECT ability_score_type_id FROM ability_score_type WHERE abbreviation='INT'), 10),
((SELECT character_id FROM "character" WHERE character_name='Gregor Strongarm'), (SELECT ability_score_type_id FROM ability_score_type WHERE abbreviation='WIS'), 8),
((SELECT character_id FROM "character" WHERE character_name='Gregor Strongarm'), (SELECT ability_score_type_id FROM ability_score_type WHERE abbreviation='CHA'), 13);

-- Sample Class Level for Character
INSERT INTO character_class_level (character_id, class_id, level) VALUES
((SELECT character_id FROM "character" WHERE character_name='Gregor Strongarm'), (SELECT class_id FROM "class" WHERE class_name='Fighter'), 1);
*/



COMMIT;