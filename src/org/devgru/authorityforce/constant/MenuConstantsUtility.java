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

import org.andengine.util.adt.color.Color;

/**
 * MenuConstantsUtility.java
 * 
 * This class contains constants needed by the menu applications.
 * 
 * @version 1.0 May 25, 2013
 * @author Aitor Gonzalez Martinez
 * @author Xavier Martinez de Francisco
 * @author Toni Trigo Diaz
 */
public class MenuConstantsUtility {

    // ===========================================================
    // Constants
    // ===========================================================

    // ********** GENERAL **********

    // Positions
    /** Menu header Y position. */
    public static final int MENU_HEADER_POS_Y = GeneralConstantsUtility.SCREEN_HEIGHT_RESOLUTION - 45;
    /** Logo Y position. */
    public static final int MENU_LOGO_POS_Y = MenuConstantsUtility.SCREEN_POS_CENTER_Y + 50;
    /** Screen center X position. */
    public static final int SCREEN_POS_CENTER_X = GeneralConstantsUtility.SCREEN_WIDTH_RESOLUTION / 2;
    /** Screen center Y position. */
    public static final int SCREEN_POS_CENTER_Y = GeneralConstantsUtility.SCREEN_HEIGHT_RESOLUTION / 2;

    // Animation Times.
    /** First logo animation duration time. */
    public static final float ANIMATION_FIRST_LOGO_SPEED = 2.5f;
    /** Standard animation velocity. */
    public static final float ANIMATION_NORMAL_SPEED = 0.6f;
    /** Fast animation velocity. */
    public static final float ANIMATION_FAST_SPEED = 0.3f;

    // Colors
    /** Color for the menu headers. */
    public static final Color MENU_HEADER_COLOR = new Color(0.70703125f,
	    0.6484375f, 0.34765625f, 0);
    /** Color for the menu subheaders. */
    public static final Color MENU_SUB_HEADER_COLOR = new Color(0.70703125f,
	    0.62890625f, 0.21484375f, 0);

    // ********** BUTTONS AND BOXES **********

    // ID's
    /** Left menu button ID. */
    public static final int MENU_BUTTON_LEFT = 0;
    /** Middle left menu button ID. */
    public static final int MENU_BUTTON_MIDDLE_LEFT = 1;
    /** Middle right menu button ID. */
    public static final int MENU_BUTTON_MIDDLE_RIGHT = 2;
    /** Right menu button ID. */
    public static final int MENU_BUTTON_RIGHT = 3;
    /** Help menu button ID. */
    public static final int MENU_BUTTON_HELP = 4;
    /** Exit menu button ID. */
    public static final int MENU_BUTTON_EXIT = 5;
    /** Load game menu box ID. */
    public static final int MENU_BOX_LOAD_GAME = 6;

    // Scales
    /** Button scale modifier for selected buttons. */
    public static final float MENU_SELECTED_BUTTON_SIZE = 1.2f;
    /** Button scale modifier for unselected buttons. */
    public static final float MENU_UNSELECTED_BUTTON_SIZE = 1f;
    /** Buttons scale modifier for small buttons. */
    public static final float MENU_SMALL_BUTTON_SIZE = 0.8f;

    // Positions
    /** Y position for hidden buttons. */
    public static final int MENU_BUTTONS_POS_Y_HIDDEN = GeneralConstantsUtility.SCREEN_HEIGHT_RESOLUTION - 750;
    /** Y position for shown buttons. */
    public static final int MENU_BUTTONS_POS_Y_SHOWN = GeneralConstantsUtility.SCREEN_HEIGHT_RESOLUTION - 650;

    // ********** HELP MENU **********

    /** ID for weapon's help menu image. */
    public static final int MENU_HELP_IMAGE_WEAPONS = 0;
    /** ID for armor's help menu image. */
    public static final int MENU_HELP_IMAGE_ARMORS = 1;
    /** ID for item's help menu image. */
    public static final int MENU_HELP_IMAGE_ITEMS = 2;

    // ********** CHARACTER SELECTION SCREEN **********

    // List Character's Names
    /** ID for Grey Moses's character selection screen image. */
    public static final int MENU_CHARACTER_GREY_MOSES = 0;
    /** ID for Striker's character selection screen image. */
    public static final int MENU_CHARACTER_STRIKER = 1;

    // ********** OPTIONS MENU **********

    // Option Button States
    /** On state for options menu options. */
    public static final String OPTIONS_STATE_ON = "On";
    /** Off state for options menu options. */
    public static final String OPTIONS_STATE_OFF = "Off";
    /** State for left handed in options menu. */
    public static final String OPTIONS_STATE_LEFT_HANDED = "Zurdo";
    /** State for right handed in options menu. */
    public static final String OPTIONS_STATE_RIGHT_HANDED = "Diestro";
    /** State for low level in options menu. */
    public static final String OPTIONS_STATE_LOW_LEVEL = "Bajo";
    /** State for medium level in options menu. */
    public static final String OPTIONS_STATE_MEDDIUM_LEVEL = "Medio";
    /** State for high level in options menu. */
    public static final String OPTIONS_STATE_HIGH_LEVEL = "Alto";
    /** State for easy difficulty in options menu. */
    public static final String OPTIONS_STATE_EASY_DIFFICULTY = "Army";
    /** State for medium difficulty in options menu. */
    public static final String OPTIONS_STATE_MEDIUM_DIFFICULTY = "Marines";
    /** State for hard difficulty in options menu. */
    public static final String OPTIONS_STATE_HARD_DIFFICULTY = "Seal";
}
