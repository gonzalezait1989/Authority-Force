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
 * GeneralConstantsUtility.java
 * 
 * This class contains general constants needed by the application.
 * 
 * @version 1.0 Apr 22, 2013
 * @author Aitor Gonzalez Martinez
 * @author Xavier Martinez de Francisco
 * @author Toni Trigo Diaz
 */
public class GeneralConstantsUtility {

    // ===========================================================
    // Constants
    // ===========================================================

    // ********** GAME GENERAL CONFIGS **********

    // Screen resolution.
    /** Screen width size. */
    public final static int SCREEN_WIDTH_RESOLUTION = 1280;
    /** Screen height size. */
    public final static int SCREEN_HEIGHT_RESOLUTION = 720;

    // FPS
    /** Max allowed FPS by the game engine. */
    public static int GAME_MAX_ALLOWED_FPS = 60;

    // ********** ENTITIES **********

    /**
     * Enum type for entities. It contains the root json name and the json file
     * containing the entity data
     */
    public enum MAP_ENTITY_TYPE {
	ARMOR("armors", "json/entities/armors.json"), WEAPON("weapons",
		"json/entities/weapons.json"), BONUS_ITEM("bonus_items",
		"json/entities/bonus_items.json"), ENEMY("enemies",
		"json/entities/enemies.json"), BULLET("bullets",
		"json/entities/bullets.json"), PLAYER("player",
		"json/entities/players.json");

	private String typeName;
	private String filePath;

	private MAP_ENTITY_TYPE(String typeName, String filePath) {
	    this.typeName = typeName;
	    this.filePath = filePath;
	}

	public String getTypeName() {
	    return typeName;
	}

	public String getFilePath() {
	    return filePath;
	}
    }

    // Weapon list positions.
    /** ID for character first weapon. */
    public static final int CHARACTER_PRIMARY_WEAPON = 0;
    /** ID for character second weapon. */
    public static final int CHARACTER_SECONDARY_WEAPON = 1;

    // Bullet Shooter.
    /** Defines if an enemy has shooted the bullet. */
    public static final String ENEMY_SHOOT = "enemy";
    /** Defines if the player has shooted the bullet. */
    public static final String PLAYER_SHOOT = "player";

    // ********** GAME CONSTANTS **********

    // List HUD items
    /** ID for HUD life bar. */
    public static final int HUD_LIFE_BAR_ONLIST = 0;
    /** ID for HUD control. */
    public static final int HUD_CONTROL_ONLIST = 1;
    /** ID for HUD pad. */
    public static final int HUD_PAD_ONLIST = 2;
    /** ID for HUT action button. */
    public static final int HUD_ACTION_BUTTON_ONLIST = 3;
    /** ID for HUD pressed action button. */
    public static final int HUD_ACTION_BUTTON_PRESSED_ONLIST = 5;

    // HUD elements size
    /** Life Bar width. */
    public static final int HUD_LIFE_BAR_WIDTH = 350;

    // ********** SCENE CONSTANTS **********

    /** Enum defining the scene type. */
    public enum SceneType {
	MAIN_MENU_SCENE, CHARACTER_SELECTION_MENU_SCENE, LOAD_GAME_MENU_SCENE, OPTIONS_MENU_SCENE, HELP_MENU_SCENE, LOADING_SCENE, GAME_SCENE;
    }

}
