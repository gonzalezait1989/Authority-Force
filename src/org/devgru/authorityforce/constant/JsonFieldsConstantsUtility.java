/*
 * Aitor Gonzalez Martinez <aitor.gonzalez.martinez@gmail.com>
 * Xavier Martinez de Francisco <martinez.x0@gmail.com> 
 * Toni Trigo Diaz <solidsnake17@hotmail.com>
 * 
 * Copyleft (c) 2013 
 *
 * This is free software, licensed under the GNU General Public License v3.
 * See http://www.gnu.org/licenses/gpl.html for more information.
 * 
 */

package org.devgru.authorityforce.constant;

/**
 * EntitiesConstantsUtility.java
 * 
 * This class contains constants defining the field names used by JSON files.
 * 
 * @version 1.0 Apr 22, 2013
 * @author Aitor Gonzalez Martinez
 * @author Xavier Martinez de Francisco
 * @author Toni Trigo Diaz
 */
public class JsonFieldsConstantsUtility {

    // ===========================================================
    // Constants
    // ===========================================================

    // ********** JSON Fields names **********

    // General
    /** Root keyword for each JSON file. */
    public static final String JSON_ROOT = "root";
    /** Field name for generic ID, used in many JSON files. */
    public static final String ID = "id";

    // Armor
    /** Field name for armor hits left in armors.json. */
    public static final String ARMOR_HITS_LEFT = "hitsLeft";
    /** Field name for armor damage reduction bonus in armors.json. */
    public static final String ARMOR_DAMAGE_REDUCTION_BONUS = "damageReductionBonus";

    // Bonus Item
    /** Field name for bonus item hp bonus in bonus_items.json. */
    public static final String BONUS_ITEM_HP_BONUS = "hpBonus";
    /** Field name for bonus item score multiplier in bonus_items.json. */
    public static final String BONUS_ITEM_SCORE_MULTIPLIER = "scoreMultiplier";
    /** Field name for bonus item extra ammo bonus in bonus_items.json. */
    public static final String BONUS_ITEM_EXTRA_AMMO = "extraAmmo";

    // Bullet
    /** Field name for bullet range in bullets.json. */
    public static final String BULLET_RANGE = "range";
    /** Field name for bullet damage in bullets.json. */
    public static final String BULLET_DAMAGE = "damage";
    /** Field name for bullet speed in bullets.json. */
    public static final String BULLET_SPEED = "bullet_speed";

    // Weapons
    /** Field name for weapon bullets left in weapons.json. */
    public static final String WEAPON_BULLETS_LEFT = "bulletsLeft";
    /** Field name for weapon bullet ID in weapons.json. */
    public static final String WEAPON_BULLET_ID = "bullet_id";

    // Character
    /** Field name for character hit points in players.json and enemies.json. */
    public static final String CHARACTER_HP = "hp";
    /**
     * Field name for character movement speed in players.json and enemies.json.
     */
    public static final String CHARACTER_MOVEMENT_SPEED = "movementSpeed";
    /** Field name for character attack time in players.json and enemies.json. */
    public static final String CHARACTER_PHYSICAL_ATTACK_TIME = "physical_attack_time";

    // Player
    /** Field name for player armor id in players.json. */
    public static final String PLAYER_ARMOR = "armor";
    /** Field name for player primary weapon id in players.json. */
    public static final String PLAYER_PRIMARY_WEAPON_JSON = "primary_weapon";
    /** Field name for player secondary weapon id in players.json. */
    public static final String PLAYER_SECONDARY_WEAPON_JSON = "secondary_weapon";
    /** Field name for player texture path in players.json. */
    public static final String PLAYER_TEXTURE = "character_texture";
    /** Field name for player face texture path in players.json. */
    public static final String PLAYER_FACE_TEXTURE = "character_face_texture";

    // Enemy
    /** Field name for enemy given score in enemies.json. */
    public static final String ENEMY_GIVEN_SCORE = "givenScore";
    /** Field name for enemy weapon ID in enemies.json. */
    public static final String ENEMY_WEAPON = "weapon";

    // Map entities definitions and texture files.
    /** Root tag for enemies in map entities definitions and map textures files. */
    public static final String TEXTURE_DEFINITION_ENEMIES = "enemies";

    // Map entities definitions files.
    /** Field name for player start X position in each map. */
    public static final String DEFINITIONS_INITIAL_PLAYER_POSITION_X = "player_initial_posX";
    /** Field name for player start Y position in each map. */
    public static final String DEFINITIONS_INITIAL_PLAYER_POSITION_Y = "player_initial_posY";
    /** Field name for each entity positions start root in each map. */
    public static final String DEFINITIONS_POSITIONS = "positions";
    /** Field name for each entity X position in each map. */
    public static final String DEFINITIONS_POSITION_X = "posX";
    /** Field name for each entity Y position in each map. */
    public static final String DEFINITIONS_POSITION_Y = "posY";

    // Map textures files.
    /** Field name for tmx file path for each map. */
    public static final String TEXTURE_TMX_ID = "tmx_file";
    /** Field name for character objects root in each map. */
    public static final String TEXTURE_CHARACTER_OBJECTS = "character_objects";
    /**
     * Field name for texture atlas X size used to store the map textures in
     * each map.
     */
    public static final String TEXTURE_BITMAP_SIZE_X = "bitmap_sizeX";
    /**
     * Field name for texture atlas Y size used to store the map textures in
     * each map.
     */
    public static final String TEXTURE_BITMAP_SIZE_Y = "bitmap_sizeY";
    /** Field name for texture path of each element in each map. */
    public static final String TEXTURE_PATH = "path";
    /** Field name for number of rows of each enemy sprites in each map. */
    public static final String TEXTURE_ROWS = "rows";
    /** Field name for number of columns of each enemy sprites in each map. */
    public static final String TEXTURE_COLUMNS = "columns";
    /** Root name for textures of each map. */
    public static final String TEXTURES = "textures";

    // Game flux.
    /** Field name for map name in flux.json. */
    public static final String FLUX_MAP_NAME = "name";
    /** Field name for next map name in flux.json. */
    public static final String FLUX_NEXT_MAP = "next_map";
    /** Field name for entities definitions in flux.json. */
    public static final String FLUX_ENTITIES_DEFINITIONS = "entities_definitions";
    /** Field name for map sounds field in flux.json. */
    public static final String FLUX_SOUNDS = "map_sounds";
    /** Field name for map textures field in flux.json. */
    public static final String FLUX_TEXTURES = "map_textures";
    /** Field value for next_map field in flux.json to go to main menu. */
    public static final String FLUX_MAIN_MENU = "MainMenu";

    // Sound
    /** Field value for song file path for each map. */
    public static final String SOUND_LEVEL_SONG_ID = "song_path";
    /** Field value for each object ID in each map. */
    public static final String SOUND_ID = "id";
    /** Field value for sound file path for each object in each map. */
    public static final String SOUND_PATH = "path";

    /** Enum containing the root element for each type of object in each map. */
    public enum SOUND_TYPE {
	WEAPONS("weapons");

	private String typeName;

	private SOUND_TYPE(String typeName) {
	    this.typeName = typeName;
	}

	public String getTypeName() {
	    return typeName;
	}
    }
}
