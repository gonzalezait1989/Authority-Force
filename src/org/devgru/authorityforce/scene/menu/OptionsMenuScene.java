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

package org.devgru.authorityforce.scene.menu;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.AutoWrap;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.opengl.font.Font;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.color.Color;
import org.andengine.util.modifier.IModifier;
import org.devgru.authorityforce.R;
import org.devgru.authorityforce.constant.GeneralConstantsUtility;
import org.devgru.authorityforce.constant.GeneralConstantsUtility.SceneType;
import org.devgru.authorityforce.constant.MainParameters;
import org.devgru.authorityforce.constant.MenuConstantsUtility;
import org.devgru.authorityforce.scene.ABaseMenuScene;
import org.devgru.authorityforce.settings.SettingsManager;

/**
 * OptionsMenuScene.java
 * 
 * This class manages all the application settings. To modify the difficulty,
 * brightness, music and other.
 * 
 * @version 1.0 Apr 19, 2013
 * @author Aitor Gonzalez Martinez
 * @author Xavier Martinez de Francisco
 * @author Toni Trigo Diaz
 */
public class OptionsMenuScene extends ABaseMenuScene {

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    // BUTTONS ID
    /* Id to dificulty button */
    private final int difficultyOption = 0;
    /* Id to handed button */
    private final int handedOption = 1;
    /* Id to audio master button */
    private final int masterOption = 2;
    /* Id to music button */
    private final int musicOption = 3;
    /* Id to sound fx button */
    private final int fxOption = 4;
    /* Id to monitor fps button */
    private final int fpsOption = 5;
    /* Id to monitor cpu button */
    private final int cpuOption = 6;
    /* Id to plane mode button */
    private final int planeModeOption = 7;
    /* Id to brightness button */
    private final int brighrnessOption = 8;
    /* Id to ok button */
    private final int okOption = 9;

    // POSITIONS
    /* The horizontal of the left Texts */
    private final float posTextsLeftX = MenuConstantsUtility.SCREEN_POS_CENTER_X - 350;
    /* The horizontal of the right Texts */
    private final float posTextsRightX = MenuConstantsUtility.SCREEN_POS_CENTER_X + 300;
    // The vertical position for 1 line headers*/
    private final float posTextGameSoundY = GeneralConstantsUtility.SCREEN_HEIGHT_RESOLUTION - 100;
    // The verticla position for 2 line headers*/
    private final float posTextMonitorSystemY = GeneralConstantsUtility.SCREEN_HEIGHT_RESOLUTION - 400;
    /* Button OK position on the Scene */
    private final float posOkBtnX = MenuConstantsUtility.SCREEN_POS_CENTER_X;

    // ITEMS TO BASESCENE

    /* The Sprite Background */
    private Sprite mSpriteBackground;
    // The Sprite Logo*/
    private Sprite mSpriteLogo;

    // TEXT
    /* Header Text */
    private Text mTextHeader;
    /* Game Header Text */
    private Text mTextGameHeader;
    /* Difficulty Text */
    private Text mTextDificulty;
    /* Handed Text */
    private Text mTextHanded;
    /* Sound Header Text */
    private Text mTextSoundHeader;
    /* Audio master Text */
    private Text mTextMaster;
    /* Music Text */
    private Text mTextMusic;
    /* Sound FX Text */
    private Text mTextFX;
    /* Monitor Header Text */
    private Text mTextMonitorHeader;
    /* Monitor FPS Text */
    private Text mTextFPS;
    /* Monitor CPU Text */
    private Text mTextCPU;
    /* System Header Text */
    private Text mTextSystemHeader;
    /* Plane mode Text */
    private Text mTextPlaneMode;
    /* Brightness Text */
    private Text mTextBrightness;
    /* The Font */
    private Font mFont;

    // ITEMS TO CHILDMENUSCENE
    /* The scene containing all the buttons. */
    private MenuScene mChildMenuScene;

    // BUTTONS
    /* Difficulty Button */
    private IMenuItem mButtonStateDificulty;
    /* Handed Button */
    private IMenuItem mButtonStateHanded;
    /* Audio master Button */
    private IMenuItem mButtonStateMaster;
    /* Music Button */
    private IMenuItem mButtonStateMusic;
    /* Sound FX Button */
    private IMenuItem mButtonStateFX;
    /* Monitor FPS Button */
    private IMenuItem mButtonStateFPS;
    /* Monitor CPU Button */
    private IMenuItem mButtonStateCPU;
    /* Plane mode Button */
    private IMenuItem mButtonStatePlaneMode;
    /* Brightness Button */
    private IMenuItem mButtonStateBrightness;
    /* Ok Button */
    private IMenuItem mButtonOk;

    // TEXT
    /* Difficulty Text to difficulty button */
    private TextMenuItem mTextButtonStateDificulty;
    /* Handed Text to handed button */
    private TextMenuItem mTextButtonStateHanded;
    /* Audio master Text to audio master button */
    private TextMenuItem mTextButtonStateMaster;
    /* Music Text to music button */
    private TextMenuItem mTextButtonStateMusic;
    /* Sound FX Text to sound fx button */
    private TextMenuItem mTextButtonStateFX;
    /* Monitor FPS Text to monitor fps button */
    private TextMenuItem mTextButtonStateFPS;
    /* Monitor CPU Text to monitor cpu button */
    private TextMenuItem mTextButtonStateCPU;
    /* Plane mode Text to plane mode button */
    private TextMenuItem mTextButtonStatePlaneMode;
    /* Brightness Text to brightness button */
    private TextMenuItem mTextButtonStateBrightness;
    /* Ok Text to ok button */
    private TextMenuItem mTextButtonOk;

    // ===========================================================
    // Constructors
    // ===========================================================

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    /**
     * This is called when back button is pressed
     */
    @Override
    public void onBackKeyPressed() {
	this.applyScreenOutActions();
    }

    /**
     * Returns the type of scene
     * 
     * @return returns the type of scene
     */
    @Override
    public SceneType getSceneType() {
	return SceneType.OPTIONS_MENU_SCENE;
    }

    /**
     * Loads all the screen elements the user can not interact with.
     */
    @Override
    public void createBaseScene() {
	// RESOURCES
	// Initializes the font
	this.mFont = this.mMenuResourceManager.getFont();
	this.mFont.prepareLetters("abcdefghijklmnopqrstuvwxyz".toCharArray());

	// CREATE ITEMS
	// Load Background and Logo
	this.mSpriteBackground = new Sprite(0, 0,
		this.mMenuResourceManager.getBackgroundTexture(),
		MainParameters.vbom);
	this.mSpriteLogo = new Sprite(0, 0,
		this.mMenuResourceManager.getLogoTexture(), MainParameters.vbom);

	// Loads the options text
	this.mTextHeader = new Text(0, 0, this.mFont,
		MainParameters.activity.getString(R.string.options_header),
		new TextOptions(AutoWrap.WORDS, 500, HorizontalAlign.CENTER,
			Text.LEADING_DEFAULT), MainParameters.vbom);

	this.mTextGameHeader = new Text(0, 0, this.mFont,
		MainParameters.activity.getString(R.string.options_game),
		new TextOptions(AutoWrap.WORDS, 500, HorizontalAlign.CENTER,
			Text.LEADING_DEFAULT), MainParameters.vbom);

	this.mTextDificulty = new Text(0, 0, this.mFont,
		MainParameters.activity.getString(R.string.options_dificulty),
		new TextOptions(AutoWrap.WORDS, 500, HorizontalAlign.LEFT,
			Text.LEADING_DEFAULT), MainParameters.vbom);

	this.mTextHanded = new Text(0, 0, this.mFont,
		MainParameters.activity
			.getString(R.string.options_left_right_handed),
		new TextOptions(AutoWrap.WORDS, 500, HorizontalAlign.LEFT,
			Text.LEADING_DEFAULT), MainParameters.vbom);

	this.mTextSoundHeader = new Text(0, 0, this.mFont,
		MainParameters.activity.getString(R.string.options_sound),
		new TextOptions(AutoWrap.WORDS, 500, HorizontalAlign.CENTER,
			Text.LEADING_DEFAULT), MainParameters.vbom);

	this.mTextMaster = new Text(
		0,
		0,
		this.mFont,
		MainParameters.activity.getString(R.string.options_main_volume),
		new TextOptions(AutoWrap.WORDS, 500, HorizontalAlign.LEFT,
			Text.LEADING_DEFAULT), MainParameters.vbom);

	this.mTextMusic = new Text(0, 0, this.mFont,
		MainParameters.activity
			.getString(R.string.options_music_volume),
		new TextOptions(AutoWrap.WORDS, 500, HorizontalAlign.LEFT,
			Text.LEADING_DEFAULT), MainParameters.vbom);

	this.mTextFX = new Text(0, 0, this.mFont,
		MainParameters.activity.getString(R.string.options_fx_volume),
		new TextOptions(AutoWrap.WORDS, 500, HorizontalAlign.LEFT,
			Text.LEADING_DEFAULT), MainParameters.vbom);

	this.mTextMonitorHeader = new Text(0, 0, this.mFont,
		MainParameters.activity.getString(R.string.options_monitor),
		new TextOptions(AutoWrap.WORDS, 500, HorizontalAlign.CENTER,
			Text.LEADING_DEFAULT), MainParameters.vbom);

	this.mTextFPS = new Text(0, 0, this.mFont,
		MainParameters.activity.getString(R.string.options_show_fps),
		new TextOptions(AutoWrap.WORDS, 500, HorizontalAlign.LEFT,
			Text.LEADING_DEFAULT), MainParameters.vbom);

	this.mTextCPU = new Text(0, 0, this.mFont,
		MainParameters.activity.getString(R.string.options_show_cpu),
		new TextOptions(AutoWrap.WORDS, 500, HorizontalAlign.LEFT,
			Text.LEADING_DEFAULT), MainParameters.vbom);

	this.mTextSystemHeader = new Text(0, 0, this.mFont,
		MainParameters.activity.getString(R.string.options_system),
		new TextOptions(AutoWrap.WORDS, 500, HorizontalAlign.CENTER,
			Text.LEADING_DEFAULT), MainParameters.vbom);

	this.mTextPlaneMode = new Text(
		0,
		0,
		this.mFont,
		MainParameters.activity.getString(R.string.options_flight_mode),
		new TextOptions(AutoWrap.WORDS, 500, HorizontalAlign.LEFT,
			Text.LEADING_DEFAULT), MainParameters.vbom);

	this.mTextBrightness = new Text(0, 0, this.mFont,
		MainParameters.activity.getString(R.string.options_brightness),
		new TextOptions(AutoWrap.WORDS, 500, HorizontalAlign.LEFT,
			Text.LEADING_DEFAULT), MainParameters.vbom);

	// INITIAL ITEM POSISTIONS
	this.mSpriteBackground.setPosition(
		MenuConstantsUtility.SCREEN_POS_CENTER_X,
		MenuConstantsUtility.SCREEN_POS_CENTER_Y);
	this.mSpriteLogo.setPosition(MenuConstantsUtility.SCREEN_POS_CENTER_X,
		MenuConstantsUtility.MENU_LOGO_POS_Y);

	// Position of the options text
	this.mTextHeader.setPosition(MenuConstantsUtility.SCREEN_POS_CENTER_X,
		MenuConstantsUtility.MENU_HEADER_POS_Y);
	this.mTextGameHeader.setPosition(this.posTextsLeftX,
		this.posTextGameSoundY);
	this.mTextDificulty.setPosition(this.posTextsLeftX,
		this.posTextGameSoundY - 80);
	this.mTextHanded.setPosition(this.posTextsLeftX,
		this.posTextGameSoundY - 160);
	this.mTextMonitorHeader.setPosition(this.posTextsLeftX,
		this.posTextMonitorSystemY);
	this.mTextFPS.setPosition(this.posTextsLeftX,
		this.posTextMonitorSystemY - 80);
	this.mTextCPU.setPosition(this.posTextsLeftX,
		this.posTextMonitorSystemY - 160);
	this.mTextSoundHeader.setPosition(this.posTextsRightX,
		this.posTextGameSoundY);
	this.mTextMaster.setPosition(this.posTextsRightX,
		this.posTextGameSoundY - 80);
	this.mTextMusic.setPosition(this.posTextsRightX,
		this.posTextGameSoundY - 160);
	this.mTextFX.setPosition(this.posTextsRightX,
		this.posTextGameSoundY - 240);
	this.mTextSystemHeader.setPosition(this.posTextsRightX,
		this.posTextMonitorSystemY);
	this.mTextPlaneMode.setPosition(this.posTextsRightX,
		this.posTextMonitorSystemY - 80);
	this.mTextBrightness.setPosition(this.posTextsRightX,
		this.posTextMonitorSystemY - 160);

	// INITIAL ITEM SCALES
	// Scale the text headers
	this.mTextHeader.setScale(1.5f);
	this.mTextGameHeader.setScale(1.3f);
	this.mTextSoundHeader.setScale(1.3f);
	this.mTextMonitorHeader.setScale(1.3f);
	this.mTextSystemHeader.setScale(1.3f);

	// INITIAL ITEM ALPHAS
	this.mSpriteLogo.setAlpha(0.4f);

	// OTHER INITIAL CONFIG
	this.mTextHeader.setColor(MenuConstantsUtility.MENU_HEADER_COLOR);
	this.mTextGameHeader
		.setColor(MenuConstantsUtility.MENU_SUB_HEADER_COLOR);
	this.mTextSoundHeader
		.setColor(MenuConstantsUtility.MENU_SUB_HEADER_COLOR);
	this.mTextMonitorHeader
		.setColor(MenuConstantsUtility.MENU_SUB_HEADER_COLOR);
	this.mTextSystemHeader
		.setColor(MenuConstantsUtility.MENU_SUB_HEADER_COLOR);

	// INITIAL MODIFIER

	// ATTACH ITEMS
	// Attach options text in scene
	this.attachChild(this.mSpriteBackground);
	this.attachChild(this.mSpriteLogo);
	this.attachChild(this.mTextHeader);
	this.attachChild(this.mTextGameHeader);
	this.attachChild(this.mTextDificulty);
	this.attachChild(this.mTextHanded);
	this.attachChild(this.mTextSoundHeader);
	this.attachChild(this.mTextMaster);
	this.attachChild(this.mTextMusic);
	this.attachChild(this.mTextFX);
	this.attachChild(this.mTextMonitorHeader);
	this.attachChild(this.mTextFPS);
	this.attachChild(this.mTextCPU);
	this.attachChild(this.mTextSystemHeader);
	this.attachChild(this.mTextPlaneMode);
	this.attachChild(this.mTextBrightness);
    }

    /**
     * Loads all clickable items on the screen.
     */
    @Override
    public void createChildMenuScene() {
	// RESOURCES

	// CREATE ITEMS
	// Creates the child Menu Scene
	this.mChildMenuScene = new MenuScene(MainParameters.camera);

	// Load the states Buttons
	this.mButtonStateDificulty = new ScaleMenuItemDecorator(
		new SpriteMenuItem(difficultyOption, this.mMenuResourceManager
			.getButtonsTexture().get(
				MenuConstantsUtility.MENU_BUTTON_LEFT),
			MainParameters.vbom),
		MenuConstantsUtility.MENU_UNSELECTED_BUTTON_SIZE,
		MenuConstantsUtility.MENU_SMALL_BUTTON_SIZE);

	this.mButtonStateHanded = new ScaleMenuItemDecorator(
		new SpriteMenuItem(handedOption, this.mMenuResourceManager
			.getButtonsTexture().get(
				MenuConstantsUtility.MENU_BUTTON_LEFT),
			MainParameters.vbom),
		MenuConstantsUtility.MENU_UNSELECTED_BUTTON_SIZE,
		MenuConstantsUtility.MENU_SMALL_BUTTON_SIZE);

	this.mButtonStateMaster = new ScaleMenuItemDecorator(
		new SpriteMenuItem(masterOption, this.mMenuResourceManager
			.getButtonsTexture().get(
				MenuConstantsUtility.MENU_BUTTON_LEFT),
			MainParameters.vbom),
		MenuConstantsUtility.MENU_UNSELECTED_BUTTON_SIZE,
		MenuConstantsUtility.MENU_SMALL_BUTTON_SIZE);

	this.mButtonStateMusic = new ScaleMenuItemDecorator(new SpriteMenuItem(
		musicOption, this.mMenuResourceManager.getButtonsTexture().get(
			MenuConstantsUtility.MENU_BUTTON_LEFT),
		MainParameters.vbom),
		MenuConstantsUtility.MENU_UNSELECTED_BUTTON_SIZE,
		MenuConstantsUtility.MENU_SMALL_BUTTON_SIZE);

	this.mButtonStateFX = new ScaleMenuItemDecorator(new SpriteMenuItem(
		fxOption, this.mMenuResourceManager.getButtonsTexture().get(
			MenuConstantsUtility.MENU_BUTTON_LEFT),
		MainParameters.vbom),
		MenuConstantsUtility.MENU_UNSELECTED_BUTTON_SIZE,
		MenuConstantsUtility.MENU_SMALL_BUTTON_SIZE);

	this.mButtonStateFPS = new ScaleMenuItemDecorator(new SpriteMenuItem(
		fpsOption, this.mMenuResourceManager.getButtonsTexture().get(
			MenuConstantsUtility.MENU_BUTTON_LEFT),
		MainParameters.vbom),
		MenuConstantsUtility.MENU_UNSELECTED_BUTTON_SIZE,
		MenuConstantsUtility.MENU_SMALL_BUTTON_SIZE);

	this.mButtonStateCPU = new ScaleMenuItemDecorator(new SpriteMenuItem(
		cpuOption, this.mMenuResourceManager.getButtonsTexture().get(
			MenuConstantsUtility.MENU_BUTTON_LEFT),
		MainParameters.vbom),
		MenuConstantsUtility.MENU_UNSELECTED_BUTTON_SIZE,
		MenuConstantsUtility.MENU_SMALL_BUTTON_SIZE);

	this.mButtonStatePlaneMode = new ScaleMenuItemDecorator(
		new SpriteMenuItem(planeModeOption, this.mMenuResourceManager
			.getButtonsTexture().get(
				MenuConstantsUtility.MENU_BUTTON_LEFT),
			MainParameters.vbom),
		MenuConstantsUtility.MENU_UNSELECTED_BUTTON_SIZE,
		MenuConstantsUtility.MENU_SMALL_BUTTON_SIZE);

	this.mButtonStateBrightness = new ScaleMenuItemDecorator(
		new SpriteMenuItem(brighrnessOption, this.mMenuResourceManager
			.getButtonsTexture().get(
				MenuConstantsUtility.MENU_BUTTON_LEFT),
			MainParameters.vbom),
		MenuConstantsUtility.MENU_UNSELECTED_BUTTON_SIZE,
		MenuConstantsUtility.MENU_SMALL_BUTTON_SIZE);

	this.mButtonOk = new ScaleMenuItemDecorator(new SpriteMenuItem(
		okOption, this.mMenuResourceManager.getButtonsTexture().get(
			MenuConstantsUtility.MENU_BUTTON_RIGHT),
		MainParameters.vbom),
		MenuConstantsUtility.MENU_SELECTED_BUTTON_SIZE,
		MenuConstantsUtility.MENU_UNSELECTED_BUTTON_SIZE);

	// Load States Buttons text
	this.mTextButtonStateDificulty = new TextMenuItem(difficultyOption,
		this.mFont, "", 20, MainParameters.vbom);
	this.mTextButtonStateHanded = new TextMenuItem(handedOption,
		this.mFont, "", 20, MainParameters.vbom);
	this.mTextButtonStateMaster = new TextMenuItem(masterOption,
		this.mFont, "", 20, MainParameters.vbom);
	this.mTextButtonStateMusic = new TextMenuItem(musicOption, this.mFont,
		"", 20, MainParameters.vbom);
	this.mTextButtonStateFX = new TextMenuItem(fxOption, this.mFont, "",
		20, MainParameters.vbom);
	this.mTextButtonStateFPS = new TextMenuItem(fpsOption, this.mFont, "",
		20, MainParameters.vbom);
	this.mTextButtonStateCPU = new TextMenuItem(cpuOption, this.mFont, "",
		20, MainParameters.vbom);
	this.mTextButtonStatePlaneMode = new TextMenuItem(planeModeOption,
		this.mFont, "", 20, MainParameters.vbom);
	this.mTextButtonStateBrightness = new TextMenuItem(brighrnessOption,
		this.mFont, "", 20, MainParameters.vbom);

	// Ok button text
	this.mTextButtonOk = new TextMenuItem(okOption, this.mFont,
		MainParameters.activity.getString(R.string.options_ok),
		MainParameters.vbom);

	// INITIAL ITEM POSISTION
	// Position of the States Buttons
	this.mButtonStateDificulty.setPosition(
		this.mTextDificulty.getX() + 150, this.mTextDificulty.getY());
	this.mButtonStateHanded.setPosition(this.mTextHanded.getX() + 150,
		this.mTextHanded.getY());
	this.mButtonStateMaster.setPosition(this.mTextMaster.getX() + 150,
		this.mTextMaster.getY());
	this.mButtonStateMusic.setPosition(this.mTextMusic.getX() + 150,
		this.mTextMusic.getY());
	this.mButtonStateFX.setPosition(this.mTextFX.getX() + 150,
		this.mTextFX.getY());
	this.mButtonStateFPS.setPosition(this.mTextFPS.getX() + 150,
		this.mTextFPS.getY());
	this.mButtonStateCPU.setPosition(this.mTextCPU.getX() + 150,
		this.mTextCPU.getY());
	this.mButtonStatePlaneMode.setPosition(
		this.mTextPlaneMode.getX() + 150, this.mTextPlaneMode.getY());
	this.mButtonStateBrightness.setPosition(
		this.mTextBrightness.getX() + 150, this.mTextBrightness.getY());
	// Load Back and Ok Button
	this.mButtonOk.setPosition(this.posOkBtnX,
		MenuConstantsUtility.MENU_BUTTONS_POS_Y_HIDDEN);

	// Position of the States Buttons Text
	this.mTextButtonStateDificulty.setPosition(
		this.mButtonStateDificulty.getWidth() / 2,
		this.mButtonStateDificulty.getHeight() / 2);
	this.mTextButtonStateHanded.setPosition(
		this.mButtonStateHanded.getWidth() / 2,
		this.mButtonStateHanded.getHeight() / 2);
	this.mTextButtonStateMaster.setPosition(
		this.mButtonStateMaster.getWidth() / 2,
		this.mButtonStateMaster.getHeight() / 2);
	this.mTextButtonStateMusic.setPosition(
		this.mButtonStateMusic.getWidth() / 2,
		this.mButtonStateMusic.getHeight() / 2);
	this.mTextButtonStateFX.setPosition(this.mButtonStateFX.getWidth() / 2,
		this.mButtonStateFX.getHeight() / 2);
	this.mTextButtonStateFPS.setPosition(
		this.mButtonStateFPS.getWidth() / 2,
		this.mButtonStateFPS.getHeight() / 2);
	this.mTextButtonStateCPU.setPosition(
		this.mButtonStateCPU.getWidth() / 2,
		this.mButtonStateCPU.getHeight() / 2);
	this.mTextButtonStatePlaneMode.setPosition(
		this.mButtonStatePlaneMode.getWidth() / 2,
		this.mButtonStatePlaneMode.getHeight() / 2);
	this.mTextButtonStateBrightness.setPosition(
		this.mButtonStateBrightness.getWidth() / 2,
		this.mButtonStateBrightness.getHeight() / 2);
	// Load Back and Ok Button Text
	this.mTextButtonOk.setPosition(this.mButtonOk.getWidth() / 2,
		this.mButtonOk.getHeight() / 2);

	// INITIAL ITEM SCALE

	// INITIAL ITEM ALPHA

	// OTHER INITIAL CONFIG
	// Sets the MenuScene properties
	this.mChildMenuScene.setBackgroundEnabled(false);
	this.mChildMenuScene.setOnMenuItemClickListener(this);

	// INITIAL MODIFIER

	// ATTACH ITEMS
	// Attach the text in Buttons
	this.mButtonStateDificulty.attachChild(this.mTextButtonStateDificulty);
	this.mButtonStateHanded.attachChild(this.mTextButtonStateHanded);
	this.mButtonStateMaster.attachChild(this.mTextButtonStateMaster);
	this.mButtonStateMusic.attachChild(this.mTextButtonStateMusic);
	this.mButtonStateFX.attachChild(this.mTextButtonStateFX);
	this.mButtonStateFPS.attachChild(this.mTextButtonStateFPS);
	this.mButtonStateCPU.attachChild(this.mTextButtonStateCPU);
	this.mButtonStatePlaneMode.attachChild(this.mTextButtonStatePlaneMode);
	this.mButtonStateBrightness
		.attachChild(this.mTextButtonStateBrightness);

	this.mButtonOk.attachChild(this.mTextButtonOk);

	// Adds the buttons in ChildMenuScene
	this.mChildMenuScene.addMenuItem(this.mButtonStateDificulty);
	this.mChildMenuScene.addMenuItem(this.mButtonStateHanded);
	this.mChildMenuScene.addMenuItem(this.mButtonStateMaster);
	this.mChildMenuScene.addMenuItem(this.mButtonStateMusic);
	this.mChildMenuScene.addMenuItem(this.mButtonStateFX);
	this.mChildMenuScene.addMenuItem(this.mButtonStateFPS);
	this.mChildMenuScene.addMenuItem(this.mButtonStateCPU);
	this.mChildMenuScene.addMenuItem(this.mButtonStatePlaneMode);
	this.mChildMenuScene.addMenuItem(this.mButtonStateBrightness);
	this.mChildMenuScene.addMenuItem(this.mButtonOk);

	// Sets the childMenuScene in this Scene
	this.setChildScene(this.mChildMenuScene);
    }

    /**
     * Apply the effects when scene is attached
     */
    @Override
    public void applyScreenInActions() {
	// Set initial text in state buttons
	this.mTextButtonStateDificulty.setText(this.mPreferencesManager
		.getDifficultyLevel());
	this.mTextButtonStateHanded.setText(this.mPreferencesManager
		.getHandedSelected());
	this.mTextButtonStateMaster.setText(this.mPreferencesManager
		.getMainSoundEnabled());
	this.mTextButtonStateMusic.setText(this.mPreferencesManager
		.getMusicSoundLevel());
	this.mTextButtonStateFX.setText(this.mPreferencesManager
		.getFXSoundLevel());
	this.mTextButtonStateFPS.setText(this.mPreferencesManager
		.getFPSMonitorEnabled());
	this.mTextButtonStateCPU.setText(this.mPreferencesManager
		.getCPUMonitorEnabled());
	this.mTextButtonStatePlaneMode.setText(this.mPreferencesManager
		.getPlaneModeEnabled());
	this.mTextButtonStateBrightness.setText(this.mPreferencesManager
		.getBrightnessLevel());

	// Sets initial color
	if (this.mTextButtonStateDificulty.getText().equals(
		MenuConstantsUtility.OPTIONS_STATE_EASY_DIFFICULTY)) {
	    this.mTextButtonStateDificulty.setColor(Color.GREEN);
	} else if (this.mTextButtonStateDificulty.getText().equals(
		MenuConstantsUtility.OPTIONS_STATE_MEDDIUM_LEVEL)) {
	    this.mTextButtonStateDificulty.setColor(Color.YELLOW);
	} else if (this.mTextButtonStateDificulty.getText().equals(
		MenuConstantsUtility.OPTIONS_STATE_HARD_DIFFICULTY)) {
	    this.mTextButtonStateDificulty.setColor(Color.RED);
	}

	if (this.mTextButtonStateMaster.getText().equals(
		MenuConstantsUtility.OPTIONS_STATE_ON)) {
	    this.mTextButtonStateMaster.setColor(Color.GREEN);
	} else {
	    this.mTextButtonStateMaster.setColor(Color.RED);
	}

	if (this.mTextButtonStateFPS.getText().equals(
		MenuConstantsUtility.OPTIONS_STATE_ON)) {
	    this.mTextButtonStateFPS.setColor(Color.GREEN);
	} else {
	    this.mTextButtonStateFPS.setColor(Color.RED);
	}

	if (this.mTextButtonStateCPU.getText().equals(
		MenuConstantsUtility.OPTIONS_STATE_ON)) {
	    this.mTextButtonStateCPU.setColor(Color.GREEN);
	} else {
	    this.mTextButtonStateCPU.setColor(Color.RED);
	}

	if (this.mTextButtonStatePlaneMode.getText().equals(
		MenuConstantsUtility.OPTIONS_STATE_ON)) {
	    this.mTextButtonStatePlaneMode.setColor(Color.GREEN);
	} else {
	    this.mTextButtonStatePlaneMode.setColor(Color.RED);
	}

	// Set Alphas in Option Text
	this.mTextHeader.setAlpha(0);
	this.mTextGameHeader.setAlpha(0);
	this.mTextDificulty.setAlpha(0);
	this.mTextHanded.setAlpha(0);
	this.mTextMonitorHeader.setAlpha(0);
	this.mTextFPS.setAlpha(0);
	this.mTextCPU.setAlpha(0);
	this.mTextSoundHeader.setAlpha(0);
	this.mTextMaster.setAlpha(0);
	this.mTextMusic.setAlpha(0);
	this.mTextFX.setAlpha(0);
	this.mTextSystemHeader.setAlpha(0);
	this.mTextPlaneMode.setAlpha(0);
	this.mTextBrightness.setAlpha(0);

	// Set Alphas to States Buttons
	this.mButtonStateDificulty.setAlpha(0);
	this.mButtonStateHanded.setAlpha(0);
	this.mButtonStateMaster.setAlpha(0);
	this.mButtonStateMusic.setAlpha(0);
	this.mButtonStateFX.setAlpha(0);
	this.mButtonStateFPS.setAlpha(0);
	this.mButtonStateCPU.setAlpha(0);
	this.mButtonStatePlaneMode.setAlpha(0);
	this.mButtonStateBrightness.setAlpha(0);

	// Sets Alphas to States Buttons Text
	this.mTextButtonStateDificulty.setAlpha(0);
	this.mTextButtonStateHanded.setAlpha(0);
	this.mTextButtonStateMaster.setAlpha(0);
	this.mTextButtonStateMusic.setAlpha(0);
	this.mTextButtonStateFX.setAlpha(0);
	this.mTextButtonStateFPS.setAlpha(0);
	this.mTextButtonStateCPU.setAlpha(0);
	this.mTextButtonStatePlaneMode.setAlpha(0);
	this.mTextButtonStateBrightness.setAlpha(0);

	// Fade IN in Option Text
	this.regFadeInEntityModifier(this.mTextHeader,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeInEntityModifier(this.mTextGameHeader,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeInEntityModifier(this.mTextDificulty,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeInEntityModifier(this.mTextHanded,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeInEntityModifier(this.mTextSoundHeader,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeInEntityModifier(this.mTextMaster,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeInEntityModifier(this.mTextMusic,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeInEntityModifier(this.mTextFX,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeInEntityModifier(this.mTextMonitorHeader,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeInEntityModifier(this.mTextFPS,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeInEntityModifier(this.mTextCPU,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeInEntityModifier(this.mTextSystemHeader,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeInEntityModifier(this.mTextPlaneMode,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeInEntityModifier(this.mTextBrightness,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);

	// Fade IN in States Buttons
	this.regFadeInEntityModifier(this.mButtonStateDificulty,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeInEntityModifier(this.mButtonStateHanded,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeInEntityModifier(this.mButtonStateMaster,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeInEntityModifier(this.mButtonStateMusic,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeInEntityModifier(this.mButtonStateFX,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeInEntityModifier(this.mButtonStateFPS,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeInEntityModifier(this.mButtonStateCPU,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeInEntityModifier(this.mButtonStatePlaneMode,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeInEntityModifier(this.mButtonStateBrightness,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);

	// Fade IN in States Buttons Text
	this.regFadeInEntityModifier(this.mTextButtonStateDificulty,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeInEntityModifier(this.mTextButtonStateHanded,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeInEntityModifier(this.mTextButtonStateMaster,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeInEntityModifier(this.mTextButtonStateMusic,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeInEntityModifier(this.mTextButtonStateFX,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeInEntityModifier(this.mTextButtonStateFPS,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeInEntityModifier(this.mTextButtonStateCPU,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeInEntityModifier(this.mTextButtonStatePlaneMode,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeInEntityModifier(this.mTextButtonStateBrightness,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);

	// Move OK Button
	this.regMoveEntityModifier(this.mButtonOk,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, this.posOkBtnX,
		MenuConstantsUtility.MENU_BUTTONS_POS_Y_HIDDEN, this.posOkBtnX,
		MenuConstantsUtility.MENU_BUTTONS_POS_Y_SHOWN, false);
    }

    /**
     * Apply the effects when scene is detached
     */
    @Override
    public void applyScreenOutActions() {
	// Creates a Modifier and a Modifier Listener for the Back Button
	MoveModifier moveModifierButtonOk = new MoveModifier(
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, this.posOkBtnX,
		MenuConstantsUtility.MENU_BUTTONS_POS_Y_SHOWN, this.posOkBtnX,
		MenuConstantsUtility.MENU_BUTTONS_POS_Y_HIDDEN);
	moveModifierButtonOk.addModifierListener(new IEntityModifierListener() {
	    @Override
	    public void onModifierStarted(IModifier<IEntity> pModifier,
		    IEntity pItem) {
	    }

	    @Override
	    public void onModifierFinished(final IModifier<IEntity> pModifier,
		    final IEntity pItem) {
		// When the animation is finished, changes to Main Menu Scene
		OptionsMenuScene.this.mSceneController
			.createMainMenuScene(null);
	    }
	});

	// Fade OUT in Option Text
	this.regFadeOutEntityModifier(this.mTextHeader,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeOutEntityModifier(this.mTextGameHeader,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeOutEntityModifier(this.mTextDificulty,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeOutEntityModifier(this.mTextHanded,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeOutEntityModifier(this.mTextSoundHeader,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeOutEntityModifier(this.mTextMaster,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeOutEntityModifier(this.mTextMusic,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeOutEntityModifier(this.mTextFX,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeOutEntityModifier(this.mTextMonitorHeader,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeOutEntityModifier(this.mTextFPS,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeOutEntityModifier(this.mTextCPU,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeOutEntityModifier(this.mTextSystemHeader,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeOutEntityModifier(this.mTextPlaneMode,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeOutEntityModifier(this.mTextBrightness,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);

	// Fade OUT in State Options
	this.regFadeOutEntityModifier(this.mButtonStateDificulty,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeOutEntityModifier(this.mButtonStateHanded,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeOutEntityModifier(this.mButtonStateMaster,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeOutEntityModifier(this.mButtonStateMusic,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeOutEntityModifier(this.mButtonStateFX,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeOutEntityModifier(this.mButtonStateFPS,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeOutEntityModifier(this.mButtonStateCPU,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeOutEntityModifier(this.mButtonStatePlaneMode,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeOutEntityModifier(this.mButtonStateBrightness,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);

	// Fade OUT in State Options Text
	this.regFadeOutEntityModifier(this.mTextButtonStateDificulty,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeOutEntityModifier(this.mTextButtonStateHanded,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeOutEntityModifier(this.mTextButtonStateMaster,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeOutEntityModifier(this.mTextButtonStateMusic,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeOutEntityModifier(this.mTextButtonStateFX,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeOutEntityModifier(this.mTextButtonStateFPS,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeOutEntityModifier(this.mTextButtonStateCPU,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeOutEntityModifier(this.mTextButtonStatePlaneMode,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeOutEntityModifier(this.mTextButtonStateBrightness,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);

	// Move to hidde buttons Back and OK
	this.regModifyEntity(this.mButtonOk, moveModifierButtonOk, true);
    }

    /**
     * Listen when press a button on the Option Menu
     */
    @Override
    public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
	    float pMenuItemLocalX, float pMenuItemLocalY) {

	// Filtered pressed button
	switch (pMenuItem.getID()) {

	case difficultyOption:
	    // Save the change to shared preferences
	    this.mPreferencesManager.setDifficultyLevel(this
		    .newState(this.mTextButtonStateDificulty));
	    return true;

	case handedOption:
	    this.mPreferencesManager.setHandedSelected(this
		    .newState(this.mTextButtonStateHanded));
	    return true;

	case masterOption:
	    // Save the change to shared preferences
	    this.mPreferencesManager.setMainSoundEnabled(this
		    .newState(this.mTextButtonStateMaster));
	    // Apply the state of volumen maste in real time
	    this.mAudioPlayer.setVolumeMaster();
	    return true;

	case musicOption:
	    this.mPreferencesManager.setMusicSoundLevel(this
		    .newState(this.mTextButtonStateMusic));
	    this.mAudioPlayer.setVolumeMusic();
	    return true;

	case fxOption:
	    this.mPreferencesManager.setFXSoundLevel(this
		    .newState(this.mTextButtonStateFX));
	    return true;

	case fpsOption:
	    this.mPreferencesManager.setFPSMonitorEnabled(this
		    .newState(this.mTextButtonStateFPS));
	    return true;

	case cpuOption:
	    this.mPreferencesManager.setCPUMonitorEnabled(this
		    .newState(this.mTextButtonStateCPU));
	    return true;

	case planeModeOption:
	    this.mPreferencesManager.setPlaneModeEnabled(this
		    .newState(this.mTextButtonStatePlaneMode));
	    // Apply the new plane mode state in real time
	    SettingsManager.changePlaneMode();
	    return true;

	case brighrnessOption:
	    this.mPreferencesManager.setBrightnessLevel(this
		    .newState(this.mTextButtonStateBrightness));
	    SettingsManager.changeBrightness();
	    return true;

	    // Save all changes in Shared Preferences and back to Main Menu
	case okOption:
	    mPreferencesManager.saveAllDataToSharedPreferences();
	    this.applyScreenOutActions();
	    return true;

	default:
	    return false;
	}
    }

    /**
     * Dispose all resources for the scene
     */
    @Override
    public void disposeScene() {
	// Runs the detach and dispose operations on a different Thread
	MainParameters.engine.runOnUpdateThread(new Runnable() {
	    @Override
	    public void run() {
		// DETACH ENTITIES
		// Detaches the elements on the background
		mSpriteBackground.detachSelf();
		mSpriteLogo.detachSelf();
		// Detaches the texts on the background
		mTextHeader.detachSelf();
		mTextGameHeader.detachSelf();
		mTextDificulty.detachSelf();
		mTextHanded.detachSelf();
		mTextSoundHeader.detachSelf();
		mTextMaster.detachSelf();
		mTextMusic.detachSelf();
		mTextFX.detachSelf();
		mTextMonitorHeader.detachSelf();
		mTextFPS.detachSelf();
		mTextCPU.detachSelf();
		mTextSystemHeader.detachSelf();
		mTextPlaneMode.detachSelf();
		mTextBrightness.detachSelf();
		// Detaches the texts from the menu
		mTextButtonStateDificulty.detachSelf();
		mTextButtonStateHanded.detachSelf();
		mTextButtonStateMaster.detachSelf();
		mTextButtonStateMusic.detachSelf();
		mTextButtonStateFX.detachSelf();
		mTextButtonStateFPS.detachSelf();
		mTextButtonStateCPU.detachSelf();
		mTextButtonStatePlaneMode.detachSelf();
		mTextButtonStateBrightness.detachSelf();
		mTextButtonOk.detachSelf();
		// Detaches the buttons from the menu
		mButtonStateDificulty.detachSelf();
		mButtonStateHanded.detachSelf();
		mButtonStateMaster.detachSelf();
		mButtonStateMusic.detachSelf();
		mButtonStateFX.detachSelf();
		mButtonStateFPS.detachSelf();
		mButtonStateCPU.detachSelf();
		mButtonStatePlaneMode.detachSelf();
		mButtonStateBrightness.detachSelf();
		mButtonOk.detachSelf();
		// Detaches the MenuScene
		mChildMenuScene.detachSelf();

		// DISPOSE
		// Disposes the elements on the background
		mSpriteBackground.dispose();
		mSpriteLogo.dispose();
		// Disposes the texts on the background
		mTextHeader.dispose();
		mTextGameHeader.dispose();
		mTextDificulty.dispose();
		mTextHanded.dispose();
		mTextSoundHeader.dispose();
		mTextMaster.dispose();
		mTextMusic.dispose();
		mTextFX.dispose();
		mTextMonitorHeader.dispose();
		mTextFPS.dispose();
		mTextCPU.dispose();
		mTextSystemHeader.dispose();
		mTextPlaneMode.dispose();
		mTextBrightness.dispose();
		// Disposes the texts from the menu
		mTextButtonStateDificulty.dispose();
		mTextButtonStateHanded.dispose();
		mTextButtonStateMaster.dispose();
		mTextButtonStateMusic.dispose();
		mTextButtonStateFX.dispose();
		mTextButtonStateFPS.dispose();
		mTextButtonStateCPU.dispose();
		mTextButtonStatePlaneMode.dispose();
		mTextButtonStateBrightness.dispose();
		mTextButtonOk.dispose();
		// Disposes the buttons from the menu
		mButtonStateDificulty.dispose();
		mButtonStateHanded.dispose();
		mButtonStateMaster.dispose();
		mButtonStateMusic.dispose();
		mButtonStateFX.dispose();
		mButtonStateFPS.dispose();
		mButtonStateCPU.dispose();
		mButtonStatePlaneMode.dispose();
		mButtonStateBrightness.dispose();
		mButtonOk.dispose();
		// Disposes the MenuScene
		mChildMenuScene.dispose();

		// SET NULL
		// Sets other objects to null
		mFont = null;

		// DISPOSE THIS SCENE
		detachSelf();
		clearChildScene();
		clearEntityModifiers();
		clearTouchAreas();
		clearUpdateHandlers();
		reset();
		dispose();
	    }
	});
    }

    // ===========================================================
    // Methods
    // ===========================================================

    /**
     * Change the button text pressed, applying the features of the new state
     * 
     * @param pOptionToChange
     *            the text of the button pressed
     * @return a String that contains the name of the new state to save in
     *         shared preferences
     */
    private String newState(Text pOptionToChange) {
	// Compare the button name it's like your state
	if (pOptionToChange.getText().equals(
		MenuConstantsUtility.OPTIONS_STATE_ON)) {
	    // Change to new name, new state
	    pOptionToChange.setText(MenuConstantsUtility.OPTIONS_STATE_OFF);
	    // Change the color text
	    pOptionToChange.setColor(Color.RED);
	    // Return the new name of the text
	    return MenuConstantsUtility.OPTIONS_STATE_OFF;

	} else if (pOptionToChange.getText().equals(
		MenuConstantsUtility.OPTIONS_STATE_OFF)) {
	    pOptionToChange.setText(MenuConstantsUtility.OPTIONS_STATE_ON);
	    pOptionToChange.setColor(Color.GREEN);
	    return MenuConstantsUtility.OPTIONS_STATE_ON;

	} else if (pOptionToChange.getText().equals(
		MenuConstantsUtility.OPTIONS_STATE_EASY_DIFFICULTY)) {
	    pOptionToChange
		    .setText(MenuConstantsUtility.OPTIONS_STATE_MEDIUM_DIFFICULTY);
	    pOptionToChange.setColor(Color.YELLOW);
	    return MenuConstantsUtility.OPTIONS_STATE_MEDIUM_DIFFICULTY;

	} else if (pOptionToChange.getText().equals(
		MenuConstantsUtility.OPTIONS_STATE_MEDIUM_DIFFICULTY)) {
	    pOptionToChange
		    .setText(MenuConstantsUtility.OPTIONS_STATE_HARD_DIFFICULTY);
	    pOptionToChange.setColor(Color.RED);
	    return MenuConstantsUtility.OPTIONS_STATE_HARD_DIFFICULTY;

	} else if (pOptionToChange.getText().equals(
		MenuConstantsUtility.OPTIONS_STATE_HARD_DIFFICULTY)) {
	    pOptionToChange
		    .setText(MenuConstantsUtility.OPTIONS_STATE_EASY_DIFFICULTY);
	    pOptionToChange.setColor(Color.GREEN);
	    return MenuConstantsUtility.OPTIONS_STATE_EASY_DIFFICULTY;

	} else if (pOptionToChange.getText().equals(
		MenuConstantsUtility.OPTIONS_STATE_RIGHT_HANDED)) {
	    pOptionToChange
		    .setText(MenuConstantsUtility.OPTIONS_STATE_LEFT_HANDED);
	    return MenuConstantsUtility.OPTIONS_STATE_LEFT_HANDED;

	} else if (pOptionToChange.getText().equals(
		MenuConstantsUtility.OPTIONS_STATE_LEFT_HANDED)) {
	    pOptionToChange
		    .setText(MenuConstantsUtility.OPTIONS_STATE_RIGHT_HANDED);
	    return MenuConstantsUtility.OPTIONS_STATE_RIGHT_HANDED;

	} else if (pOptionToChange.getText().equals(
		MenuConstantsUtility.OPTIONS_STATE_LOW_LEVEL)) {
	    pOptionToChange
		    .setText(MenuConstantsUtility.OPTIONS_STATE_MEDDIUM_LEVEL);
	    return MenuConstantsUtility.OPTIONS_STATE_MEDDIUM_LEVEL;

	} else if (pOptionToChange.getText().equals(
		MenuConstantsUtility.OPTIONS_STATE_MEDDIUM_LEVEL)) {
	    pOptionToChange
		    .setText(MenuConstantsUtility.OPTIONS_STATE_HIGH_LEVEL);
	    return MenuConstantsUtility.OPTIONS_STATE_HIGH_LEVEL;

	} else if (pOptionToChange.getText().equals(
		MenuConstantsUtility.OPTIONS_STATE_HIGH_LEVEL)) {
	    pOptionToChange
		    .setText(MenuConstantsUtility.OPTIONS_STATE_LOW_LEVEL);
	    return MenuConstantsUtility.OPTIONS_STATE_LOW_LEVEL;
	}

	// For safety if there is no state returns OFF
	return MenuConstantsUtility.OPTIONS_STATE_OFF;
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}
