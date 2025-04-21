-- AI GENERATED DML!!!
-- AI GENERATED DML!!!
-- AI GENERATED DML!!!
-- AI GENERATED DML!!!
-- REFACTOR BEFORE ACTUAL USE!!!


-- ===== Populate Core Definition Tables =====

-- == ability_score_type ==
INSERT INTO ability_score_type (name, abbreviation) VALUES
                                                        ('Strength', 'STR'),
                                                        ('Dexterity', 'DEX'),
                                                        ('Constitution', 'CON'),
                                                        ('Intelligence', 'INT'),
                                                        ('Wisdom', 'WIS'),
                                                        ('Charisma', 'CHA');

-- == alignment ==
INSERT INTO alignment (alignment_name, abbreviation) VALUES
                                                         ('Lawful Good', 'LG'),
                                                         ('Neutral Good', 'NG'),
                                                         ('Chaotic Good', 'CG'),
                                                         ('Lawful Neutral', 'LN'),
                                                         ('Neutral', 'N'),
                                                         ('Chaotic Neutral', 'CN'),
                                                         ('Lawful Evil', 'LE'),
                                                         ('Neutral Evil', 'NE'),
                                                         ('Chaotic Evil', 'CE');

-- == race ==
-- Note: Descriptions are brief placeholders based on SRD concepts.
INSERT INTO race (race_name, description, base_speed, size) VALUES
                                                                ('Dwarf', 'Bold and hardy, known for their skill in warfare, mining, and craftsmanship.', 25, 'Medium'),
                                                                ('Elf', 'Magical people of otherworldly grace, living in the world but not entirely part of it.', 30, 'Medium'),
                                                                ('Halfling', 'Practical and down-to-earth, cherish home and hearth, lead unassuming lives.', 25, 'Small'),
                                                                ('Human', 'Youngest of the common races, short-lived, adaptable, and ambitious.', 30, 'Medium'),
                                                                ('Dragonborn', 'Descendants of dragons, proud and honorable.', 30, 'Medium'),
                                                                ('Gnome', 'Small and enthusiastic inventors, explorers, and tricksters.', 25, 'Small'),
                                                                ('Half-Elf', 'Inheriting aspects of both human and elven parentage, charismatic and adaptable.', 30, 'Medium'),
                                                                ('Half-Orc', 'Combining the physical power of orcs with human adaptability, often facing prejudice.', 30, 'Medium'),
                                                                ('Tiefling', 'Descendants of humans who made infernal pacts, bearing physical marks of their heritage.', 30, 'Medium');

-- == class ==
-- Note: Descriptions are brief placeholders. Primary abilities link to IDs from ability_score_type (assuming STR=1, DEX=2, CON=3, INT=4, WIS=5, CHA=6).
INSERT INTO "class" (class_name, description, hit_die, primary_ability_ids) VALUES
                                                                                ('Cleric', 'A priestly champion who wields divine magic in service of a higher power.', 8, ARRAY[5]), -- Wisdom
                                                                                ('Fighter', 'A master of martial combat, skilled with a variety of weapons and armor.', 10, ARRAY[1, 2]), -- Strength or Dexterity
                                                                                ('Rogue', 'A scoundrel who uses stealth and trickery to overcome obstacles and enemies.', 8, ARRAY[2]), -- Dexterity
                                                                                ('Wizard', 'A scholarly magic-user capable of manipulating the structures of reality.', 6, ARRAY[4]); -- Intelligence

-- == background ==
-- Note: Descriptions are brief placeholders.
INSERT INTO background (background_name, description, feature_name, feature_description) VALUES
                                                                                             ('Acolyte', 'You have spent your life in the service of a temple.', 'Shelter of the Faithful', 'Temples of your faith provide you and your companions with healing and care.'),
                                                                                             ('Criminal', 'You are an experienced criminal with a history of breaking the law.', 'Criminal Contact', 'You have a reliable and trustworthy contact who acts as your liaison to a network of other criminals.'),
                                                                                             ('Sage', 'You spent years learning the lore of the multiverse.', 'Researcher', 'When you attempt to learn or recall a piece of lore, if you do not know that information, you often know where and from whom you can obtain it.'),
                                                                                             ('Soldier', 'You were trained for battle and warfare.', 'Military Rank', 'Soldiers loyal to your former military organization still recognize your authority and influence.');

-- == skill_type ==
-- Assumes ability_score_type IDs: STR=1, DEX=2, CON=3, INT=4, WIS=5, CHA=6
INSERT INTO skill_type (name, governing_ability_score_type_id) VALUES
                                                                   ('Acrobatics', 2), ('Animal Handling', 5), ('Arcana', 4), ('Athletics', 1), ('Deception', 6),
                                                                   ('History', 4), ('Insight', 5), ('Intimidation', 6), ('Investigation', 4), ('Medicine', 5),
                                                                   ('Nature', 4), ('Perception', 5), ('Performance', 6), ('Persuasion', 6), ('Religion', 4),
                                                                   ('Sleight of Hand', 2), ('Stealth', 2), ('Survival', 5);

-- == saving_throw_type ==
-- Assumes ability_score_type IDs: STR=1, DEX=2, CON=3, INT=4, WIS=5, CHA=6
INSERT INTO saving_throw_type (name, governing_ability_score_type_id) VALUES
                                                                          ('Strength Save', 1), ('Dexterity Save', 2), ('Constitution Save', 3),
                                                                          ('Intelligence Save', 4), ('Wisdom Save', 5), ('Charisma Save', 6);

-- == language ==
INSERT INTO language (name, script) VALUES
                                        ('Common', 'Common'),
                                        ('Dwarvish', 'Dwarvish'),
                                        ('Elvish', 'Elvish'),
                                        ('Giant', 'Dwarvish'),
                                        ('Gnomish', 'Dwarvish'),
                                        ('Goblin', 'Dwarvish'),
                                        ('Halfling', 'Common'),
                                        ('Orc', 'Dwarvish');

-- == tool ==
INSERT INTO tool (name, tool_type) VALUES
                                       ('Thieves'' Tools', 'other'), -- Often considered its own type or artisan by some tables
                                       ('Brewer''s Supplies', 'artisan'),
                                       ('Lute', 'musical'),
                                       ('Dice Set', 'gaming'),
                                       ('Navigator''s Tools', 'other'),
                                       ('Smith''s Tools', 'artisan');

-- == armor_proficiency_category ==
INSERT INTO armor_proficiency_category (name) VALUES
                                                  ('Light'), ('Medium'), ('Heavy'), ('Shields');

-- == weapon_proficiency_category ==
INSERT INTO weapon_proficiency_category (name) VALUES
                                                   ('Simple'), ('Martial');

-- == feat ==
-- Note: Descriptions are brief placeholders. Prerequisites are simplified text.
INSERT INTO feat (name, description, prerequisites) VALUES
                                                        ('Alert', 'You gain initiative bonuses and cannot be surprised.', NULL),
                                                        ('Grappler', 'You gain advantages when grappling foes.', 'Strength 13 or higher'),
                                                        ('Great Weapon Master', 'Before making a melee attack with a heavy weapon you are proficient with, you can choose to take a -5 penalty to the attack roll. If the attack hits, you add +10 to the attack''s damage.', 'Proficiency with a heavy melee weapon'),
                                                        ('Sharpshooter', 'Attacking at long range doesn''t impose disadvantage on your ranged weapon attack rolls. Your ranged weapon attacks ignore half cover and three-quarters cover. Before you make an attack with a ranged weapon that you are proficient with, you can choose to take a -5 penalty to the attack roll. If the attack hits, you add +10 to the attack''s damage.', 'Proficiency with a ranged weapon');

-- == item ==
-- Costs are in copper pieces (cp). 1gp = 100cp, 1sp = 10cp.
-- Weapons
INSERT INTO item (item_name, description, weight, cost_cp, item_type, weapon_proficiency, weapon_damage, weapon_damage_type, weapon_properties) VALUES
                                                                                                                                                    ('Dagger', 'A standard dagger.', 1, 200, 'weapon', 'simple melee', '1d4', 'piercing', 'Finesse, light, thrown (range 20/60)'),
                                                                                                                                                    ('Shortbow', 'A standard shortbow.', 2, 2500, 'weapon', 'simple ranged', '1d6', 'piercing', 'Ammunition (range 80/320), two-handed'),
                                                                                                                                                    ('Longsword', 'A standard longsword.', 3, 1500, 'weapon', 'martial melee', '1d8', 'slashing', 'Versatile (1d10)'),
                                                                                                                                                    ('Heavy Crossbow', 'A powerful crossbow.', 18, 5000, 'weapon', 'martial ranged', '1d10', 'piercing', 'Ammunition (range 100/400), heavy, loading, two-handed');

-- Armor & Shield
INSERT INTO item (item_name, description, weight, cost_cp, item_type, armor_proficiency, armor_class, armor_strength_requirement, armor_stealth) VALUES
                                                                                                                                                     ('Leather', 'Basic leather armor.', 10, 1000, 'armor', 'light', '11 + Dex modifier', NULL, NULL),
                                                                                                                                                     ('Scale Mail', 'Armor made of interlocking metal scales.', 45, 5000, 'armor', 'medium', '14 + Dex modifier (max 2)', NULL, 'disadvantage'),
                                                                                                                                                     ('Plate', 'Full plate armor.', 65, 150000, 'armor', 'heavy', '18', 'Str 15', 'disadvantage'),
                                                                                                                                                     ('Shield', 'A standard shield.', 6, 1000, 'shield', 'shield', '+2', NULL, NULL);

-- Potion
INSERT INTO item (item_name, description, weight, cost_cp, item_type) VALUES
    ('Potion of Healing', 'Restores 2d4 + 2 hit points.', 0.5, 5000, 'potion'); -- Basic version

-- Scroll (Generic Example)
INSERT INTO item (item_name, description, weight, cost_cp, item_type) VALUES
    ('Spell Scroll (1st level)', 'A scroll containing a single 1st-level spell.', 0.1, 2500, 'scroll'); -- Cost varies greatly

-- Wondrous Item Example
INSERT INTO item (item_name, description, weight, cost_cp, item_type, requires_attunement) VALUES
    ('Amulet of Proof against Detection and Location', 'While wearing this amulet, you are hidden from divination magic.', 1, 0, 'wondrous', TRUE); -- Cost typically determined by DM/rarity

-- Gear
INSERT INTO item (item_name, description, weight, cost_cp, item_type, capacity) VALUES
                                                                                    ('Backpack', 'A simple backpack.', 5, 200, 'gear', '1 cubic foot/30 pounds of gear'),
                                                                                    ('Rope, Hempen (50 feet)', '50 feet of hempen rope.', 10, 100, 'gear', NULL),
                                                                                    ('Torch', 'Provides light.', 1, 1, 'gear', NULL);

-- Tool
INSERT INTO item (item_name, description, weight, cost_cp, item_type) VALUES
    ('Thieves'' Tools Set', 'Includes a small file, lock picks, mirror, pliers, scissors.', 1, 2500, 'tool'); -- Re-inserting as 'item' of type 'tool'

-- == spell ==
-- Note: Descriptions are brief placeholders.
INSERT INTO spell (spell_name, description, higher_level_description, level, school, casting_time, range, components, material_component_description, duration, is_ritual, requires_concentration) VALUES
-- Level 0 (Cantrips)
('Light', 'Object shines with light.', NULL, 0, 'Evocation', '1 action', 'Touch', 'V, M', 'A firefly or phosphorescent moss', '1 hour', FALSE, FALSE),
('Mage Hand', 'Create a spectral, floating hand.', NULL, 0, 'Conjuration', '1 action', '30 feet', 'V, S', NULL, '1 minute', FALSE, FALSE),
-- Level 1
('Magic Missile', 'Projectiles of magical force unerringly strike targets.', 'Creates one additional dart for each slot level above 1st.', 1, 'Evocation', '1 action', '120 feet', 'V, S', NULL, 'Instantaneous', FALSE, FALSE),
('Cure Wounds', 'Heal a creature you touch.', 'Heals an additional 1d8 for each slot level above 1st.', 1, 'Evocation', '1 action', 'Touch', 'V, S', NULL, 'Instantaneous', FALSE, FALSE),
('Shield of Faith', 'Protective field grants +2 AC to a creature.', NULL, 1, 'Abjuration', '1 bonus action', '60 feet', 'V, S, M', 'A small parchment with a holy text written on it', 'Concentration, up to 10 minutes', FALSE, TRUE),
-- Level 2
('Hold Person', 'Paralyze a humanoid.', 'Targets one additional humanoid for each slot level above 2nd.', 2, 'Enchantment', '1 action', '60 feet', 'V, S, M', 'A small, straight piece of iron', 'Concentration, up to 1 minute', FALSE, TRUE),
('Misty Step', 'Teleport up to 30 feet.', NULL, 2, 'Conjuration', '1 bonus action', 'Self', 'V', NULL, 'Instantaneous', FALSE, FALSE),
-- Level 3
('Fireball', 'Explosion of fire damages creatures in a radius.', 'Damage increases by 1d6 for each slot level above 3rd.', 3, 'Evocation', '1 action', '150 feet', 'V, S, M', 'A tiny ball of bat guano and sulfur', 'Instantaneous', FALSE, FALSE),
('Animate Dead', 'Create undead servants from bones or corpses.', 'Targets one additional corpse or pile of bones for each slot level above 3rd.', 3, 'Necromancy', '1 minute', '10 feet', 'V, S, M', 'A drop of blood, a piece of flesh, and a pinch of bone dust', 'Instantaneous', FALSE, FALSE),
-- Level 4
('Dimension Door', 'Teleport yourself and potentially one other creature.', NULL, 4, 'Conjuration', '1 action', '500 feet', 'V', NULL, 'Instantaneous', FALSE, FALSE),
('Stoneskin', 'Target gains resistance to nonmagical physical damage.', NULL, 4, 'Abjuration', '1 action', 'Touch', 'V, S, M', 'Diamond dust worth 100 gp, which the spell consumes', 'Concentration, up to 1 hour', FALSE, TRUE),
-- Level 5
('Cone of Cold', 'Blast of cold damages creatures in a cone.', 'Damage increases by 1d8 for each slot level above 5th.', 5, 'Evocation', '1 action', 'Self (60-foot cone)', 'V, S, M', 'A small crystal or glass cone', 'Instantaneous', FALSE, FALSE),
('Flame Strike', 'Column of divine fire damages creatures.', 'Damage increases by 1d6 for each slot level above 5th.', 5, 'Evocation', '1 action', '60 feet', 'V, S, M', 'Pinch of sulfur', 'Instantaneous', FALSE, FALSE),
-- Level 6
('Chain Lightning', 'Lightning leaps between targets.', 'One additional bolt targets one additional creature for each slot level above 6th.', 6, 'Evocation', '1 action', '150 feet', 'V, S, M', 'A bit of fur; a piece of amber, glass, or crystal rod; and three silver pins', 'Instantaneous', FALSE, FALSE),
('Heal', 'Restore 70 HP and end certain conditions.', 'Healing increases by 10 for each slot level above 6th.', 6, 'Evocation', '1 action', '60 feet', 'V, S', NULL, 'Instantaneous', FALSE, FALSE),
-- Level 7
('Finger of Death', 'Slay one creature with necrotic energy.', NULL, 7, 'Necromancy', '1 action', '60 feet', 'V, S', NULL, 'Instantaneous', FALSE, FALSE),
('Plane Shift', 'Travel to another plane of existence.', NULL, 7, 'Conjuration', '1 action', 'Touch', 'V, S, M', 'A forked, metal rod worth at least 250 gp, attuned to a particular plane', 'Instantaneous', FALSE, FALSE),
-- Level 8
('Dominate Monster', 'Control the actions of any creature.', 'Duration increases with slot level.', 8, 'Enchantment', '1 action', '60 feet', 'V, S', NULL, 'Concentration, up to 1 hour', FALSE, TRUE),
('Sunburst', 'Blinding sphere of radiant light damages and blinds.', NULL, 8, 'Evocation', '1 action', '150 feet', 'V, S, M', 'Fire and a piece of sunstone', 'Instantaneous', FALSE, FALSE),
-- Level 9
('Time Stop', 'Stop the flow of time for yourself.', NULL, 9, 'Transmutation', '1 action', 'Self', 'V', NULL, 'Instantaneous', FALSE, FALSE),
('True Resurrection', 'Restore a creature dead for up to 200 years to life, fully healed.', NULL, 9, 'Necromancy', '1 hour', 'Touch', 'V, S, M', 'Diamonds worth at least 25,000 gp, which the spell consumes', 'Instantaneous', FALSE, FALSE);


-- == player ==
-- Example players
-- INSERT INTO player (player_name, email) VALUES
--                                             ('AliceTheAdventurer', 'alice@example.com'),
--                                             ('BobTheBrave', 'bob@sample.net'),
--                                             ('CharlieTheClever', NULL);