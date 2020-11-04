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
 * AssetsConstantsUtility.java
 * 
 * This class contains constants needed for the resources located in the assets
 * folder.
 * 
 * @version 1.0 Apr 22, 2013
 * @author Aitor Gonzalez Martinez
 * @author Xavier Martinez de Francisco
 * @author Toni Trigo Diaz
 */
public class AssetsConstantsUtility {

    // ===========================================================
    // Constants
    // ===========================================================

    // ********** ASSETS DIRECTORIES **********

    /** Main folder for image resources. */
    public static final String IMAGES_DIRECTORY = "gfx/";
    /** Main folder for fonts resources. */
    public static final String FONTS_DIRECTORY = "fonts/";
    /** Main folder for audio resources. */
    public static final String MEDIA_DIRECTORY = "mfx/";

    // ********** JSON DAO **********

    /** Path to JSON file which manages the game scenes flux. */
    public static final String MAP_FLUX_FILE = "json/maps/flux.json";

    // ********** MENU RESOURCES PATHS **********
    // Background
    /** Path for logo image inside assets/gfx folder. */
    public static final String MENU_LOGO = "menu/background/logo.png";
    /** Path for background image inside assets/gfx folder. */
    public static final String MENU_BACKGROUND = "menu/background/background.png";

    // Character selection screen characters.
    /** Path for Grey Mosses image inside assets/gfx folder. */
    public static final String MENU_CHARACTER_GREY_MOSES = "menu/characters/grey_moses.png";
    /** Path for Striker image inside assets/gfx folder. */
    public static final String MENU_CHARACTER_STRIKER = "menu/characters/striker.png";

    // Buttons and boxes.
    /** Path for left button image inside assets/gfx folder. */
    public static final String MENU_BUTTON_LEFT = "menu/buttons/btn_left.png";
    /** Path for middle left button image inside assets/gfx folder. */
    public static final String MENU_BUTTON_MIDDLE_LEFT = "menu/buttons/btn_middle_left.png";
    /** Path for middle right button image inside assets/gfx folder. */
    public static final String MENU_BUTTON_MIDDLE_RIGHT = "menu/buttons/btn_middle_right.png";
    /** Path for right button image inside assets/gfx folder. */
    public static final String MENU_BUTTON_RIGHT = "menu/buttons/btn_right.png";
    /** Path for help button image inside assets/gfx folder. */
    public static final String MENU_BUTTON_HELP = "menu/buttons/btn_help.png";
    /** Path for exit button image inside assets/gfx folder. */
    public static final String MENU_BUTTON_EXIT = "menu/buttons/btn_exit.png";
    /** Path for saved game box image inside assets/gfx folder. */
    public static final String MENU_SAVED_GAME_BOX = "menu/buttons/btn_load_game.png";

    // Help menu images.
    /** Path for weapons help image inside assets/gfx folder. */
    public static final String HELP_IMAGE_WEAPONS = "menu/help_images/weapons.png";
    /** Path for armors help image inside assets/gfx folder. */
    public static final String HELP_IMAGE_ARMORS = "menu/help_images/armors.png";
    /** Path for items help image inside assets/gfx folder. */
    public static final String HELP_IMAGE_ITEMS = "menu/help_images/items.png";

    // ********** HUD ELEMENTS PATHS **********

    /** Path for hud status bar image inside assets/gfx folder. */
    public static final String HUD_STATUS_BAR = "game/hud/status_bar.png";
    /** Path for hud control image inside assets/gfx folder */
    public static final String HUD_CONTROL = "game/hud/control.png";
    /** Path for hud pad image inside assets/gfx folder */
    public static final String HUD_PAD = "game/hud/pad.png";
    /** Path for hud action button image inside assets/gfx folder */
    public static final String HUD_ACTION_BUTTON = "game/hud/btn_action.png";
    /** Path for hud action button pressed image inside assets/gfx folder */
    public static final String HUD_ACTION_BUTTON_PRESSED = "game/hud/btn_action_pressed.png";

    // Audio paths.
    /** Path for music menu inside assets/mfx folder */
    public static final String MUSIC_MENU = "music/music_menu.ogg";
    /** Path for city map music inside assets/mfx folder */
    public static final String MUSIC_CITY = "music/music_city.ogg";

    // ********** FONTS PATHS **********
    /** Path to the main font inside assets/fonts folder */
    public static final String MAIN_FONT = "airborne.ttf";
}
