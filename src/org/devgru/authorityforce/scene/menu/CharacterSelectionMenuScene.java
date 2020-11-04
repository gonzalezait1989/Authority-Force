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

import java.util.HashMap;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
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
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.modifier.IModifier;
import org.devgru.authorityforce.R;
import org.devgru.authorityforce.constant.GeneralConstantsUtility;
import org.devgru.authorityforce.constant.GeneralConstantsUtility.SceneType;
import org.devgru.authorityforce.constant.MainParameters;
import org.devgru.authorityforce.constant.MenuConstantsUtility;
import org.devgru.authorityforce.scene.ABaseMenuScene;

/**
 * CharacterSelectionMenuScene.java
 * 
 * The Character Selection screen, shown after clicking the play button in the
 * main menu. This screen lets the player select the character which he wants to
 * play with.
 * 
 * @version 1.0 Apr 19, 2013
 * @author Aitor Gonzalez Martinez
 * @author Xavier Martinez de Francisco
 * @author Toni Trigo Diaz
 */
public class CharacterSelectionMenuScene extends ABaseMenuScene {

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    // BUTTON IDs
    /* Option ID for the back button */
    private final int back_option = 0;
    /* Option ID for the first player selection */
    private final int greyMosesSelected = 1;
    /* Option ID for the second player selection */
    private final int strikerSelected = 2;
    /* Option ID for the new game button */
    private final int newGameOption = 3;

    // POSITIONS
    // Images
    /* Position X to Grey Mosses Show */
    private final float mPosGreyMosesXShown = GeneralConstantsUtility.SCREEN_WIDTH_RESOLUTION - 1050;
    /* Position X to Grey Mosses Hidden */
    private final float mPosGreyMosesXHidden = GeneralConstantsUtility.SCREEN_WIDTH_RESOLUTION - 1500;
    /* Position Y to Grey Mosses */
    private final float mPosGreyMosesY = MenuConstantsUtility.SCREEN_POS_CENTER_Y;
    /* Position X to Striker Show */
    private final float mPosStrikerXShown = GeneralConstantsUtility.SCREEN_WIDTH_RESOLUTION - 200;
    /* Position X to Striker Hidden */
    private final float mPosStrikerXHidden = GeneralConstantsUtility.SCREEN_WIDTH_RESOLUTION + 300;
    /* Position Y to Striker */
    private final float mPosStrikerY = MenuConstantsUtility.SCREEN_POS_CENTER_Y;

    // Buttons
    /* Position X to New Game Button */
    private final float mPosNewGameBtnX = MenuConstantsUtility.SCREEN_POS_CENTER_X;
    /* Position X to Back Button Center */
    private final float mPosBackBtnCenterX = MenuConstantsUtility.SCREEN_POS_CENTER_X;
    /* Position X to Back Button left */
    private final float mPosBackBtnLeftX = MenuConstantsUtility.SCREEN_POS_CENTER_X
	    - this.mButtonBack.getWidth();
    /* Position X to Back Button Center */
    private final float mPosBackBtnRightX = MenuConstantsUtility.SCREEN_POS_CENTER_X
	    + this.mButtonBack.getWidth();

    // History header
    /* Position X to Grey Moses History header */
    private final float mPosHistoryHeaderXGreyMoses = MenuConstantsUtility.SCREEN_POS_CENTER_X - 85;
    /* Position X to Striker History header */
    private final float mPosHistoryHeaderXStriker = MenuConstantsUtility.SCREEN_POS_CENTER_X - 488;
    /* Position Y to History header */
    private final float mPosHistoryHeaderY = GeneralConstantsUtility.SCREEN_HEIGHT_RESOLUTION - 100;

    // Stats header
    /* Position X to Grey Moses Stats header */
    private final float mPosStatsHeaderXGreyMoses = MenuConstantsUtility.SCREEN_POS_CENTER_X - 20;
    /* Position Y to Grey Moses Stats headerr */
    private final float mPosStatsHeaderYGreyMoses = GeneralConstantsUtility.SCREEN_HEIGHT_RESOLUTION - 515;
    /* Position X to Striker Stats header */
    private final float mPosStatsHeaderXStriker = MenuConstantsUtility.SCREEN_POS_CENTER_X - 420;
    /* Position Y to Striker Stats header */
    private final float mPosStatsHeaderYStriker = GeneralConstantsUtility.SCREEN_HEIGHT_RESOLUTION - 520;

    // Stats
    /* Position X to Grey Moses Stats */
    private final float mPosStatsXGreyMoses = MenuConstantsUtility.SCREEN_POS_CENTER_X + 5;
    /* Position Y to Grey Moses Stats */
    private final float mPosStatsYGreyMoses = GeneralConstantsUtility.SCREEN_HEIGHT_RESOLUTION - 550;
    /* Position X to Striker Stats */
    private final float mPosStatsXStriker = MenuConstantsUtility.SCREEN_POS_CENTER_X - 400;
    /* Position Y to Striker Stats */
    private final float mPosStatsYStriker = GeneralConstantsUtility.SCREEN_HEIGHT_RESOLUTION - 555;

    // Text
    /* Position X to Grey Mosses Text. */
    private final float mPosGreyMosesXText = MenuConstantsUtility.SCREEN_POS_CENTER_X + 200;
    /* Position Y to Grey Mosses Text. */
    private final float mPosGreyMosesYText = GeneralConstantsUtility.SCREEN_HEIGHT_RESOLUTION - 300;
    /* Position X to Striker Text. */
    private final float mPosStrikerXText = MenuConstantsUtility.SCREEN_POS_CENTER_X - 200;
    /* Position Y to Striker Text. */
    private final float mPosStrikerYText = GeneralConstantsUtility.SCREEN_HEIGHT_RESOLUTION - 300;

    // ITEMS TO BASESCENE
    /* The background. */
    private Sprite mSpriteBackground;
    /* The logo. */
    private Sprite mSpriteLogo;

    // ITEMS TO CHILDMENUSCENE
    /* The MenuScene */
    private MenuScene mChildMenuScene;
    /* The Sprite of the Grey Moses soldier */
    private SpriteMenuItem mSpriteGreyMoses;
    /* The Sprite of the Striker soldier */
    private SpriteMenuItem mSpriteStriker;
    /* The Sprite currently selected. */
    private SpriteMenuItem mSpriteSelectedCharacter = null;
    /* The Sprite of the Back Button */
    private IMenuItem mButtonBack;
    /* The Sprite of the New Game Button */
    private IMenuItem mButtonNewGame;
    /* The Back Button Text */
    private TextMenuItem mTextButtonBack;
    /* The New Game Button Text */
    private TextMenuItem mTextButtonNewGame;
    /* Font used by the Menu text. */
    private Font mFont;
    /* The Text for the Striker soldier */
    private Text mTextStriker;
    /* The Text for the Grey Moses soldier */
    private Text mTextGreyMoses;
    /* The Select character Text. */
    private Text mTextHeader;
    /* History header Text. */
    private Text mTextHistoryHeader;
    /* The Stats header text. */
    private Text mTextStatsHeader;
    /* The Stats text. */
    private Text mTextStats;

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
	// Determines the action when go back
	this.performBackAction();
    }

    /**
     * Returns the type of scene
     * 
     * @return returns the type of scene
     */
    @Override
    public SceneType getSceneType() {
	return SceneType.CHARACTER_SELECTION_MENU_SCENE;
    }

    /**
     * Loads all the screen elements the user can not interact with.
     */
    @Override
    public void createBaseScene() {
	// RESOURCES
	this.mFont = (Font) this.mMenuResourceManager.getFont();
	ITextureRegion background = this.mMenuResourceManager
		.getBackgroundTexture();
	ITextureRegion logo = this.mMenuResourceManager.getLogoTexture();
	final String textStriker = (String) MainParameters.activity
		.getText(R.string.striker_history);
	final String textGreyMoses = (String) MainParameters.activity
		.getText(R.string.grey_moses_history);
	final String textSelectCharacter = (String) MainParameters.activity
		.getText(R.string.select_character);
	final String textHistoryHeader = (String) MainParameters.activity
		.getText(R.string.history_header);
	final String textStatsHeader = (String) MainParameters.activity
		.getText(R.string.stats_header);

	// CREATE ITEMS
	// Background.
	this.mSpriteBackground = new Sprite(
		MenuConstantsUtility.SCREEN_POS_CENTER_X,
		MenuConstantsUtility.SCREEN_POS_CENTER_Y, background,
		MainParameters.vbom);
	// Logo.
	this.mSpriteLogo = new Sprite(MenuConstantsUtility.SCREEN_POS_CENTER_X,
		MenuConstantsUtility.MENU_LOGO_POS_Y, logo, MainParameters.vbom);
	// Striker Text
	this.mTextStriker = new Text(0, 0, this.mFont, textStriker, 1000,
		new TextOptions(AutoWrap.WORDS, 1000, HorizontalAlign.LEFT,
			Text.LEADING_DEFAULT), MainParameters.vbom);
	// Grey Moses Text
	this.mTextGreyMoses = new Text(0, 0, this.mFont, textGreyMoses, 1000,
		new TextOptions(AutoWrap.WORDS, 1000, HorizontalAlign.LEFT,
			Text.LEADING_DEFAULT), MainParameters.vbom);
	// Select character
	this.mTextHeader = new Text(0, 0, this.mFont, textSelectCharacter, 500,
		new TextOptions(AutoWrap.WORDS, 500, HorizontalAlign.CENTER,
			Text.LEADING_DEFAULT), MainParameters.vbom);
	// History header
	this.mTextHistoryHeader = new Text(0, 0, this.mFont, textHistoryHeader,
		500, new TextOptions(AutoWrap.WORDS, 500,
			HorizontalAlign.CENTER, Text.LEADING_DEFAULT),
		MainParameters.vbom);
	// History header
	this.mTextStatsHeader = new Text(0, 0, this.mFont, textStatsHeader,
		500, new TextOptions(AutoWrap.WORDS, 500,
			HorizontalAlign.CENTER, Text.LEADING_DEFAULT),
		MainParameters.vbom);
	// Stats
	this.mTextStats = new Text(0, 0, this.mFont, "", 500, new TextOptions(
		AutoWrap.WORDS, 500, HorizontalAlign.CENTER,
		Text.LEADING_DEFAULT), MainParameters.vbom);

	// Sets positions.
	this.mTextStriker.setPosition(this.mPosStrikerXText,
		this.mPosStrikerYText);
	this.mTextGreyMoses.setPosition(this.mPosGreyMosesXText,
		this.mPosGreyMosesYText);
	this.mTextHeader.setPosition(MenuConstantsUtility.SCREEN_POS_CENTER_X,
		MenuConstantsUtility.MENU_HEADER_POS_Y);
	this.mTextHistoryHeader.setPosition(
		MenuConstantsUtility.SCREEN_POS_CENTER_X,
		MenuConstantsUtility.MENU_HEADER_POS_Y);
	this.mTextStatsHeader.setPosition(
		MenuConstantsUtility.SCREEN_POS_CENTER_X,
		MenuConstantsUtility.MENU_HEADER_POS_Y);
	this.mTextStats.setPosition(MenuConstantsUtility.SCREEN_POS_CENTER_X,
		MenuConstantsUtility.MENU_HEADER_POS_Y);

	// INITIAL ITEM SCALES
	this.mTextStriker.setScale(0.7f);
	this.mTextGreyMoses.setScale(0.7f);
	this.mTextHeader.setScale(1.5f);
	this.mTextStats.setScale(0.7f);

	// INITIAL ITEM ALPHAS
	this.mSpriteLogo.setAlpha(0.4f);
	this.mTextHeader.setAlpha(0);
	this.mTextHistoryHeader.setAlpha(0);
	this.mTextStatsHeader.setAlpha(0);
	this.mTextStats.setAlpha(0);

	// OTHER INITIAL CONFIG
	this.mTextHeader.setColor(MenuConstantsUtility.MENU_HEADER_COLOR);
	this.mTextHistoryHeader
		.setColor(MenuConstantsUtility.MENU_SUB_HEADER_COLOR);
	this.mTextStatsHeader
		.setColor(MenuConstantsUtility.MENU_SUB_HEADER_COLOR);

	// ATTACH ITEMS
	this.attachChild(this.mSpriteBackground);
	this.attachChild(this.mSpriteLogo);
	this.attachChild(this.mTextHeader);
    }

    /**
     * Loads all clickable items on the screen.
     */
    @Override
    public void createChildMenuScene() {
	// RESOURCES
	HashMap<Integer, ITextureRegion> characters = this.mMenuResourceManager
		.getCharacterTexture();
	HashMap<Integer, ITextureRegion> buttonResources = this.mMenuResourceManager
		.getButtonsTexture();
	String newGameText = MainParameters.activity
		.getString(R.string.btn_new_game);
	String backButtonText = MainParameters.activity
		.getString(R.string.btn_back);

	// CREATE ITEMS
	// Child Menu Scene
	this.mChildMenuScene = new MenuScene(MainParameters.camera);
	// Grey Mosses
	this.mSpriteGreyMoses = new SpriteMenuItem(greyMosesSelected,
		characters.get(MenuConstantsUtility.MENU_CHARACTER_GREY_MOSES),
		MainParameters.vbom);
	this.mSpriteGreyMoses.setPosition(this.mPosGreyMosesXHidden,
		this.mPosGreyMosesY);
	// Striker
	this.mSpriteStriker = new SpriteMenuItem(strikerSelected,
		characters.get(MenuConstantsUtility.MENU_CHARACTER_STRIKER),
		MainParameters.vbom);
	// Back button
	this.mButtonBack = new ScaleMenuItemDecorator(new SpriteMenuItem(
		back_option,
		buttonResources.get(MenuConstantsUtility.MENU_BUTTON_LEFT),
		MainParameters.vbom),
		MenuConstantsUtility.MENU_SELECTED_BUTTON_SIZE,
		MenuConstantsUtility.MENU_UNSELECTED_BUTTON_SIZE);
	// New game button
	this.mButtonNewGame = new ScaleMenuItemDecorator(new SpriteMenuItem(
		newGameOption,
		buttonResources.get(MenuConstantsUtility.MENU_BUTTON_RIGHT),
		MainParameters.vbom),
		MenuConstantsUtility.MENU_SELECTED_BUTTON_SIZE,
		MenuConstantsUtility.MENU_UNSELECTED_BUTTON_SIZE);
	// Back button Text
	this.mTextButtonBack = new TextMenuItem(back_option, mFont,
		backButtonText, MainParameters.vbom);
	// New game button text
	this.mTextButtonNewGame = new TextMenuItem(newGameOption, mFont,
		newGameText, MainParameters.vbom);

	// INITIAL ITEM POSISTIONS
	this.mSpriteStriker.setPosition(this.mPosStrikerXHidden,
		this.mPosStrikerY);
	this.mButtonBack.setPosition(this.mPosBackBtnCenterX,
		MenuConstantsUtility.MENU_BUTTONS_POS_Y_HIDDEN);
	this.mButtonNewGame.setPosition(this.mPosNewGameBtnX,
		MenuConstantsUtility.MENU_BUTTONS_POS_Y_HIDDEN);
	mTextButtonBack.setPosition(this.mButtonBack.getWidth() / 2,
		this.mButtonBack.getHeight() / 2);
	mTextButtonNewGame.setPosition(this.mButtonNewGame.getWidth() / 2,
		this.mButtonNewGame.getHeight() / 2);

	// OTHER INITIAL CONFIG
	this.mChildMenuScene.setBackgroundEnabled(false);
	this.mChildMenuScene.setOnMenuItemClickListener(this);

	// ATTACH ITEMS
	this.mButtonBack.attachChild(mTextButtonBack);
	this.mButtonNewGame.attachChild(mTextButtonNewGame);
	this.mChildMenuScene.addMenuItem(this.mSpriteStriker);
	this.mChildMenuScene.addMenuItem(this.mSpriteGreyMoses);
	this.mChildMenuScene.addMenuItem(this.mButtonBack);
	this.mChildMenuScene.addMenuItem(this.mButtonNewGame);

	// Sets the child MenuScene
	this.setChildScene(this.mChildMenuScene);
    }

    /**
     * Apply the effects when scene is attached
     */
    @Override
    public void applyScreenInActions() {
	this.regFadeInEntityModifier(this.mTextHeader,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);

	this.regMoveEntityModifier(this.mSpriteGreyMoses,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED,
		this.mPosGreyMosesXHidden, this.mPosGreyMosesY,
		this.mPosGreyMosesXShown, this.mPosGreyMosesY, true);

	this.regMoveEntityModifier(this.mSpriteStriker,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED,
		this.mPosStrikerXHidden, this.mPosStrikerY,
		this.mPosStrikerXShown, this.mPosStrikerY, true);

	this.regMoveEntityModifier(this.mButtonBack,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED,
		this.mPosBackBtnCenterX,
		MenuConstantsUtility.MENU_BUTTONS_POS_Y_HIDDEN,
		this.mPosBackBtnCenterX,
		MenuConstantsUtility.MENU_BUTTONS_POS_Y_SHOWN, true);
    }

    /**
     * Apply the effects when scene is detached
     */
    @Override
    public void applyScreenOutActions() {
	// Create a Modifier and a Modifier Listener for the Back Button.
	MoveModifier moveOut = new MoveModifier(
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED,
		this.mPosBackBtnCenterX,
		MenuConstantsUtility.MENU_BUTTONS_POS_Y_SHOWN,
		this.mPosBackBtnCenterX,
		MenuConstantsUtility.MENU_BUTTONS_POS_Y_HIDDEN);

	moveOut.addModifierListener(new IEntityModifierListener() {
	    @Override
	    public void onModifierStarted(IModifier<IEntity> pModifier,
		    IEntity pItem) {
	    }

	    @Override
	    public void onModifierFinished(final IModifier<IEntity> pModifier,
		    final IEntity pItem) {
		CharacterSelectionMenuScene.this.mSceneController
			.createMainMenuScene(null);
		pModifier.removeModifierListener(this);
	    }
	});

	// Register the new modifiers for all entities which are going to be
	// animated.
	this.regMoveEntityModifier(this.mSpriteGreyMoses,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED,
		this.mPosGreyMosesXShown, this.mPosGreyMosesY,
		mPosGreyMosesXHidden, this.mPosGreyMosesY, true);

	this.regMoveEntityModifier(this.mSpriteStriker,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED,
		this.mPosStrikerXShown, this.mPosStrikerY,
		this.mPosStrikerXHidden, this.mPosStrikerY, true);

	this.regFadeOutEntityModifier(this.mTextHeader,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);

	this.regModifyEntity(this.mButtonBack, moveOut, true);
    }

    /**
     * Listen when press a button on this scene
     */
    @Override
    public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
	    float pMenuItemLocalX, float pMenuItemLocalY) {

	switch (pMenuItem.getID()) {

	// If any back action occurs.
	case back_option:
	    this.performBackAction();
	    return true;

	    // If Grey Moses is clicked.
	case greyMosesSelected:
	    if (this.mSpriteSelectedCharacter == null) {
		this.mGameManager.setPlayerSelected("grey_moses");
		this.performGreyMossesClick();
	    } else {
		this.performBackAction();
	    }
	    return true;

	    // If Striker is clicked.
	case strikerSelected:
	    if (this.mSpriteSelectedCharacter == null) {
		this.mGameManager.setPlayerSelected("striker");
		this.performStrikerClick();
	    } else {
		this.performBackAction();
	    }
	    return true;

	    // If Play button is clicked.
	case newGameOption:
	    this.mSceneController.loadGameScene(true);
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
	// Runs the detatch operations on a diferent Thread
	MainParameters.engine.runOnUpdateThread(new Runnable() {
	    @Override
	    public void run() {
		// DETACH ENTITIES
		// Detaches the elements of the background
		mSpriteBackground.detachSelf();
		mSpriteLogo.detachSelf();
		// Detaches the text from the background
		mTextStriker.detachSelf();
		mTextGreyMoses.detachSelf();
		mTextHeader.detachSelf();
		mTextStatsHeader.detachSelf();
		mTextStats.detachSelf();
		// Detaches the text of the buttons of the menu
		mTextButtonBack.detachSelf();
		mTextButtonNewGame.detachSelf();
		// Detaches the buttons of the menu
		mSpriteGreyMoses.detachSelf();
		mSpriteStriker.detachSelf();
		mButtonBack.detachSelf();
		mButtonNewGame.detachSelf();

		// DISPOSE
		// Disposes the elements of the background
		mSpriteBackground.dispose();
		mSpriteLogo.dispose();
		// Disposes the text from the background
		mTextStriker.dispose();
		mTextGreyMoses.dispose();
		mTextHeader.dispose();
		mTextStatsHeader.dispose();
		mTextStats.dispose();
		// Disposes the text of the buttons of the menu
		mTextButtonBack.dispose();
		mTextButtonNewGame.dispose();
		// Disposes the buttons of the menu
		mSpriteGreyMoses.dispose();
		mSpriteStriker.dispose();
		mButtonBack.dispose();
		mButtonNewGame.dispose();
		// Detaches the MenuScene
		mChildMenuScene.detachSelf();
		// Disposes the MenuScene
		mChildMenuScene.dispose();

		// SET NULL
		// Sets other objects to null
		mSpriteSelectedCharacter = null;
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
     * Defines the actions done when clicking the Grey Mosses character.
     */
    private void performGreyMossesClick() {
	// Set Grey Moses as selected character.
	this.mSpriteSelectedCharacter = this.mSpriteGreyMoses;

	// Create a Modifier and a Modifier Listener for the Header Text.
	AlphaModifier fadeOut = new AlphaModifier(
		MenuConstantsUtility.ANIMATION_FAST_SPEED, 1, 0);

	fadeOut.addModifierListener(new IEntityModifierListener() {
	    @Override
	    public void onModifierStarted(IModifier<IEntity> pModifier,
		    IEntity pItem) {
	    }

	    @Override
	    public void onModifierFinished(final IModifier<IEntity> pModifier,
		    final IEntity pItem) {
		CharacterSelectionMenuScene.this.mTextHeader
			.setText((String) MainParameters.activity
				.getText(R.string.grey_moses_name));
		CharacterSelectionMenuScene.this.regFadeInEntityModifier(pItem,
			MenuConstantsUtility.ANIMATION_FAST_SPEED, false);
		pModifier.removeModifierListener(this);
	    }
	});

	this.mTextHistoryHeader.setPosition(this.mPosHistoryHeaderXGreyMoses,
		this.mPosHistoryHeaderY);
	this.mTextStatsHeader.setPosition(this.mPosStatsHeaderXGreyMoses,
		this.mPosStatsHeaderYGreyMoses);
	this.mTextStats.setPosition(this.mPosStatsXGreyMoses,
		this.mPosStatsYGreyMoses);
	this.mTextStats.setText((String) MainParameters.activity
		.getText(R.string.grey_moses_stats));

	// Register the new modifiers for all entities which are going to be
	// animated.
	this.regFadeInEntityModifier(this.mTextHistoryHeader,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeInEntityModifier(this.mTextStats,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeInEntityModifier(this.mTextStatsHeader,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);

	this.regMoveEntityModifier(this.mSpriteStriker,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED,
		this.mPosStrikerXShown, this.mPosStrikerY,
		this.mPosStrikerXHidden, this.mPosStrikerY, true);

	this.regFadeInEntityModifier(this.mTextGreyMoses,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);

	this.regMoveEntityModifier(this.mButtonNewGame,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED,
		this.mPosNewGameBtnX,
		MenuConstantsUtility.MENU_BUTTONS_POS_Y_HIDDEN,
		this.mPosNewGameBtnX,
		MenuConstantsUtility.MENU_BUTTONS_POS_Y_SHOWN, true);

	this.regMoveEntityModifier(this.mButtonBack,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED,
		this.mPosBackBtnCenterX,
		MenuConstantsUtility.MENU_BUTTONS_POS_Y_SHOWN,
		this.mPosBackBtnRightX,
		MenuConstantsUtility.MENU_BUTTONS_POS_Y_SHOWN, true);

	this.regModifyEntity(this.mTextHeader, fadeOut, true);

	if (!this.mTextGreyMoses.hasParent()) {
	    this.attachChild(this.mTextGreyMoses);
	}

	if (!this.mTextHistoryHeader.hasParent()) {
	    this.attachChild(this.mTextHistoryHeader);
	}

	if (!this.mTextStatsHeader.hasParent()) {
	    this.attachChild(this.mTextStatsHeader);
	}

	if (!this.mTextStats.hasParent()) {
	    this.attachChild(this.mTextStats);
	}
    }

    /**
     * Defines the actions done when clicking the Striker character.
     */
    private void performStrikerClick() {
	// Set Striker as selected character.
	this.mSpriteSelectedCharacter = this.mSpriteStriker;

	// Create a Modifier and a Modifier Listener for the Header Text.
	AlphaModifier fadeOut = new AlphaModifier(
		MenuConstantsUtility.ANIMATION_FAST_SPEED, 1, 0);

	fadeOut.addModifierListener(new IEntityModifierListener() {
	    @Override
	    public void onModifierStarted(IModifier<IEntity> pModifier,
		    IEntity pItem) {
	    }

	    @Override
	    public void onModifierFinished(final IModifier<IEntity> pModifier,
		    final IEntity pItem) {
		CharacterSelectionMenuScene.this.mTextHeader
			.setText((String) MainParameters.activity
				.getText(R.string.striker_name));
		pItem.registerEntityModifier(new SequenceEntityModifier(
			new AlphaModifier(
				MenuConstantsUtility.ANIMATION_FAST_SPEED, 0, 1)));
		pModifier.removeModifierListener(this);
	    }
	});

	this.mTextHistoryHeader.setPosition(this.mPosHistoryHeaderXStriker,
		this.mPosHistoryHeaderY);
	this.mTextStatsHeader.setPosition(this.mPosStatsHeaderXStriker,
		this.mPosStatsHeaderYStriker);
	this.mTextStats.setPosition(this.mPosStatsXStriker,
		this.mPosStatsYStriker);
	this.mTextStats.setText((String) MainParameters.activity
		.getText(R.string.striker_stats));

	// Register the new modifiers for all entities which are going to be
	// animated.
	this.regFadeInEntityModifier(this.mTextHistoryHeader,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeInEntityModifier(this.mTextStats,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeInEntityModifier(this.mTextStatsHeader,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);

	this.regMoveEntityModifier(this.mSpriteGreyMoses,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED,
		this.mPosGreyMosesXShown, this.mPosGreyMosesY,
		this.mPosGreyMosesXHidden, this.mPosGreyMosesY, true);

	this.regFadeInEntityModifier(this.mTextStriker,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);

	this.regMoveEntityModifier(this.mButtonNewGame,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED,
		this.mPosNewGameBtnX,
		MenuConstantsUtility.MENU_BUTTONS_POS_Y_HIDDEN,
		this.mPosNewGameBtnX,
		MenuConstantsUtility.MENU_BUTTONS_POS_Y_SHOWN, true);

	this.regMoveEntityModifier(this.mButtonBack,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED,
		this.mPosBackBtnCenterX,
		MenuConstantsUtility.MENU_BUTTONS_POS_Y_SHOWN,
		this.mPosBackBtnLeftX,
		MenuConstantsUtility.MENU_BUTTONS_POS_Y_SHOWN, true);

	this.regModifyEntity(this.mTextHeader, fadeOut, true);

	if (!this.mTextStriker.hasParent()) {
	    this.attachChild(this.mTextStriker);
	}

	if (!this.mTextHistoryHeader.hasParent()) {
	    this.attachChild(this.mTextHistoryHeader);
	}

	if (!this.mTextStatsHeader.hasParent()) {
	    this.attachChild(this.mTextStatsHeader);
	}

	if (!this.mTextStats.hasParent()) {
	    this.attachChild(this.mTextStats);
	}
    }

    /**
     * Defines the actions done on a back action.
     */
    private void performBackAction() {
	// Unselect Grey Moses.
	if (this.mSpriteSelectedCharacter == this.mSpriteGreyMoses) {

	    // Create a Modifier and a Modifier Listener for the Header Text.
	    AlphaModifier fadeOut = new AlphaModifier(
		    MenuConstantsUtility.ANIMATION_FAST_SPEED, 1, 0);

	    fadeOut.addModifierListener(new IEntityModifierListener() {
		@Override
		public void onModifierStarted(IModifier<IEntity> pModifier,
			IEntity pItem) {
		}

		@Override
		public void onModifierFinished(
			final IModifier<IEntity> pModifier, final IEntity pItem) {
		    CharacterSelectionMenuScene.this.mTextHeader
			    .setText((String) MainParameters.activity
				    .getText(R.string.select_character));
		    CharacterSelectionMenuScene.this.regFadeInEntityModifier(
			    pItem, MenuConstantsUtility.ANIMATION_FAST_SPEED,
			    false);
		    pModifier.removeModifierListener(this);
		}
	    });

	    // Register the new modifiers for all entities which are going to be
	    // animated.
	    this.regFadeOutEntityModifier(this.mTextHistoryHeader,
		    MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);

	    this.regFadeOutEntityModifier(this.mTextStatsHeader,
		    MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);

	    this.regFadeOutEntityModifier(this.mTextStats,
		    MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);

	    this.regMoveEntityModifier(this.mSpriteStriker,
		    MenuConstantsUtility.ANIMATION_NORMAL_SPEED,
		    this.mPosStrikerXHidden, this.mPosStrikerY,
		    this.mPosStrikerXShown, this.mPosStrikerY, true);

	    this.regFadeOutEntityModifier(this.mTextGreyMoses,
		    MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);

	    this.regMoveEntityModifier(this.mButtonBack,
		    MenuConstantsUtility.ANIMATION_NORMAL_SPEED,
		    this.mPosBackBtnRightX,
		    MenuConstantsUtility.MENU_BUTTONS_POS_Y_SHOWN,
		    this.mPosBackBtnCenterX,
		    MenuConstantsUtility.MENU_BUTTONS_POS_Y_SHOWN, true);

	    this.regMoveEntityModifier(this.mButtonNewGame,
		    MenuConstantsUtility.ANIMATION_NORMAL_SPEED,
		    this.mPosNewGameBtnX,
		    MenuConstantsUtility.MENU_BUTTONS_POS_Y_SHOWN,
		    this.mPosNewGameBtnX,
		    MenuConstantsUtility.MENU_BUTTONS_POS_Y_HIDDEN, true);

	    this.mTextHeader.registerEntityModifier(new SequenceEntityModifier(
		    fadeOut));

	    // Unselect Striker.
	} else if (this.mSpriteSelectedCharacter == this.mSpriteStriker) {
	    // Create a Modifier and a Modifier Listener for the Header Text.
	    AlphaModifier fadeOut = new AlphaModifier(
		    MenuConstantsUtility.ANIMATION_FAST_SPEED, 1, 0);

	    fadeOut.addModifierListener(new IEntityModifierListener() {
		@Override
		public void onModifierStarted(IModifier<IEntity> pModifier,
			IEntity pItem) {
		}

		@Override
		public void onModifierFinished(
			final IModifier<IEntity> pModifier, final IEntity pItem) {
		    CharacterSelectionMenuScene.this.mTextHeader
			    .setText((String) MainParameters.activity
				    .getText(R.string.select_character));
		    CharacterSelectionMenuScene.this.regFadeInEntityModifier(
			    pItem, MenuConstantsUtility.ANIMATION_FAST_SPEED,
			    false);
		    pModifier.removeModifierListener(this);
		}
	    });

	    // Register the new modifiers for all entities which are going to be
	    // animated.
	    this.regFadeOutEntityModifier(this.mTextHistoryHeader,
		    MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);

	    this.regFadeOutEntityModifier(this.mTextStatsHeader,
		    MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);

	    this.regFadeOutEntityModifier(this.mTextStats,
		    MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);

	    this.regMoveEntityModifier(this.mSpriteGreyMoses,
		    MenuConstantsUtility.ANIMATION_NORMAL_SPEED,
		    this.mPosGreyMosesXHidden, this.mPosGreyMosesY,
		    mPosGreyMosesXShown, this.mPosGreyMosesY, true);

	    this.regFadeOutEntityModifier(this.mTextStriker,
		    MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);

	    this.regMoveEntityModifier(this.mButtonBack,
		    MenuConstantsUtility.ANIMATION_NORMAL_SPEED,
		    this.mPosBackBtnLeftX,
		    MenuConstantsUtility.MENU_BUTTONS_POS_Y_SHOWN,
		    this.mPosBackBtnCenterX,
		    MenuConstantsUtility.MENU_BUTTONS_POS_Y_SHOWN, true);

	    this.regMoveEntityModifier(this.mButtonNewGame,
		    MenuConstantsUtility.ANIMATION_NORMAL_SPEED,
		    this.mPosNewGameBtnX,
		    MenuConstantsUtility.MENU_BUTTONS_POS_Y_SHOWN,
		    this.mPosNewGameBtnX,
		    MenuConstantsUtility.MENU_BUTTONS_POS_Y_HIDDEN, true);

	    this.mTextHeader.registerEntityModifier(new SequenceEntityModifier(
		    fadeOut));

	    // Go back to main menu.
	} else {
	    // Apply screen out effects.
	    this.applyScreenOutActions();
	}
	// Set the selected character to null, which means none is selected.
	this.mSpriteSelectedCharacter = null;
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}
