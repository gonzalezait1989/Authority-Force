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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.SortedMap;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.ScaleModifier;
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
import org.andengine.util.modifier.IModifier;
import org.devgru.authorityforce.R;
import org.devgru.authorityforce.constant.GeneralConstantsUtility.SceneType;
import org.devgru.authorityforce.constant.MainParameters;
import org.devgru.authorityforce.constant.MenuConstantsUtility;
import org.devgru.authorityforce.preferences.SavedGamesManager;
import org.devgru.authorityforce.scene.ABaseMenuScene;

/**
 * LoadGameScene.java
 * 
 * Manage saved games from the game to load and remove previously saved games
 * 
 * @version 1.0 Apr 19, 2013
 * @author Aitor Gonzalez Martinez
 * @author Xavier Martinez de Francisco
 * @author Toni Trigo Diaz
 */
public class LoadGameScene extends ABaseMenuScene {

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    // BUTTON IDs
    /* Id to back option button */
    private final int backOption = 0;
    /* Id to load option button */
    private final int loadOption = 1;
    /* Id to delete option button */
    private final int deleteOption = 2;

    // POSITIONS
    /* Position X to Back Button */
    private final float mPositionButtonBackX = MenuConstantsUtility.SCREEN_POS_CENTER_X - 300;
    /* Position X to Load Button */
    private final float mPositionButtonLoadX = MenuConstantsUtility.SCREEN_POS_CENTER_X;
    /* Position X to Delete Button */
    private final float mPositionButtonDeleteX = MenuConstantsUtility.SCREEN_POS_CENTER_X + 300;

    // ITEMS TO BASESCENE
    /* Logo Sprite. */
    private Sprite mSpriteLogo;
    /* Background Sprite. */
    private Sprite mSpriteBackground;
    /* Text Header */
    private Text mTextHeader;
    /* Text to feedback No saved games */
    private Text mTextNoSavedGames;

    // ITEMS TO CHILDMENUSCENE
    /* The scene containing all the buttons. */
    private MenuScene mChildMenuScene;
    /* The back Button. */
    private IMenuItem mButtonBack;
    /* The Load Button */
    private IMenuItem mButtonLoad;
    /* The Delete ButtonF */
    private IMenuItem mButtonDelete;
    /* List with buttons saved games */
    private ArrayList<IMenuItem> mButtonSavedGames;
    /* Text Back Button */
    private TextMenuItem mTextButtonBack;
    /* Text Load Button */
    private TextMenuItem mTextButtonLoad;
    /* Text Delete Button */
    private TextMenuItem mtextButtonDelete;
    /* List with text saved games */
    private ArrayList<Text> mTextSavedGames;
    /* The Font */
    private Font mFont;
    /* Determines if a loaded game is selected */
    private boolean mIsLoadGameSelected = false;
    /* Determines the selected game */
    private IMenuItem mButtonSelectedLoadGame;
    /* Map with Saved Games */
    private SortedMap<String, String> mSavedGames;
    /* ArrayList with the saved game keys */
    private ArrayList<String> mLoadedGamesKeys;

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
	return SceneType.LOAD_GAME_MENU_SCENE;
    }

    /**
     * Loads all the screen elements the user can not interact with.
     */
    @Override
    public void createBaseScene() {
	// RESOURCES
	// Initializes the font
	this.mFont = this.mMenuResourceManager.getFont();

	// CREATE ITEMS
	// Creates the Header Text
	this.mTextHeader = new Text(MenuConstantsUtility.SCREEN_POS_CENTER_X,
		MenuConstantsUtility.MENU_HEADER_POS_Y, this.mFont,
		MainParameters.activity.getString(R.string.load_game_header),
		500, new TextOptions(AutoWrap.WORDS, 500,
			HorizontalAlign.CENTER, Text.LEADING_DEFAULT),
		MainParameters.vbom);

	// Creates the text that shows that there are no saved games
	this.mTextNoSavedGames = new Text(
		MenuConstantsUtility.SCREEN_POS_CENTER_X,
		MenuConstantsUtility.SCREEN_POS_CENTER_Y, this.mFont,
		MainParameters.activity.getString(R.string.no_saved_games),
		500, new TextOptions(AutoWrap.WORDS, 500,
			HorizontalAlign.CENTER, Text.LEADING_DEFAULT),
		MainParameters.vbom);

	// Creates the sprite with background texture
	this.mSpriteBackground = new Sprite(
		MenuConstantsUtility.SCREEN_POS_CENTER_X,
		MenuConstantsUtility.SCREEN_POS_CENTER_Y,
		this.mMenuResourceManager.getBackgroundTexture(),
		MainParameters.vbom);

	// Logo
	this.mSpriteLogo = new Sprite(MenuConstantsUtility.SCREEN_POS_CENTER_X,
		MenuConstantsUtility.MENU_LOGO_POS_Y,
		this.mMenuResourceManager.getLogoTexture(), MainParameters.vbom);

	// INITIAL ITEM POSISTION

	// INITIAL ITEM SCALE
	this.mTextHeader.setScale(1.5f);
	this.mTextNoSavedGames.setScale(1.5f);

	// INITIAL ITEM ALPHA
	this.mTextNoSavedGames.setAlpha(0.0f);
	this.mSpriteLogo.setAlpha(0.4f);
	this.mTextHeader.setAlpha(0);

	// OTHER INITIAL CONFIG
	this.mTextHeader.setColor(MenuConstantsUtility.MENU_HEADER_COLOR);
	this.mTextNoSavedGames
		.setColor(MenuConstantsUtility.MENU_SUB_HEADER_COLOR);

	// INITIAL MODIFIER

	// ATACH ITEMS
	// Attach the background texture and the logo
	this.attachChild(this.mSpriteBackground);
	this.attachChild(this.mSpriteLogo);
	this.attachChild(this.mTextHeader);
	this.attachChild(this.mTextNoSavedGames);
    }

    /**
     * Loads all clickable items on the screen.
     */
    @Override
    public void createChildMenuScene() {
	// RESOURCES

	// CREATE ITEMS
	// Creates the Buttons
	this.mChildMenuScene = new MenuScene(MainParameters.camera);

	this.mButtonBack = new ScaleMenuItemDecorator(new SpriteMenuItem(
		backOption, this.mMenuResourceManager.getButtonsTexture().get(
			MenuConstantsUtility.MENU_BUTTON_LEFT),
		MainParameters.vbom),
		MenuConstantsUtility.MENU_SELECTED_BUTTON_SIZE,
		MenuConstantsUtility.MENU_UNSELECTED_BUTTON_SIZE);

	this.mButtonLoad = new ScaleMenuItemDecorator(new SpriteMenuItem(
		loadOption, this.mMenuResourceManager.getButtonsTexture().get(
			MenuConstantsUtility.MENU_BUTTON_MIDDLE_LEFT),
		MainParameters.vbom),
		MenuConstantsUtility.MENU_SELECTED_BUTTON_SIZE,
		MenuConstantsUtility.MENU_UNSELECTED_BUTTON_SIZE);

	this.mButtonDelete = new ScaleMenuItemDecorator(new SpriteMenuItem(
		deleteOption, this.mMenuResourceManager.getButtonsTexture()
			.get(MenuConstantsUtility.MENU_BUTTON_MIDDLE_RIGHT),
		MainParameters.vbom),
		MenuConstantsUtility.MENU_SELECTED_BUTTON_SIZE,
		MenuConstantsUtility.MENU_UNSELECTED_BUTTON_SIZE);

	// Creates the Texts
	// Load the Back Button Text
	this.mTextButtonBack = new TextMenuItem(0, this.mFont,
		MainParameters.activity.getString(R.string.btn_back),
		MainParameters.vbom);

	this.mTextButtonLoad = new TextMenuItem(0, this.mFont,
		MainParameters.activity.getString(R.string.btn_load_game),
		MainParameters.vbom);

	this.mtextButtonDelete = new TextMenuItem(0, this.mFont,
		MainParameters.activity.getString(R.string.btn_delete_game),
		MainParameters.vbom);

	// INITIAL ITEM POSISTION
	// Positions the Texts
	this.mTextButtonBack.setPosition(this.mButtonBack.getWidth() / 2,
		this.mButtonBack.getHeight() / 2);
	this.mTextButtonLoad.setPosition(this.mButtonLoad.getWidth() / 2,
		this.mButtonLoad.getHeight() / 2);
	this.mtextButtonDelete.setPosition(this.mButtonDelete.getWidth() / 2,
		this.mButtonDelete.getHeight() / 2);

	// Positions the Buttons
	this.mButtonBack.setPosition(this.mPositionButtonBackX,
		MenuConstantsUtility.MENU_BUTTONS_POS_Y_HIDDEN);
	this.mButtonLoad.setPosition(this.mPositionButtonLoadX,
		MenuConstantsUtility.MENU_BUTTONS_POS_Y_HIDDEN);
	this.mButtonDelete.setPosition(this.mPositionButtonDeleteX,
		MenuConstantsUtility.MENU_BUTTONS_POS_Y_HIDDEN);

	// INITIAL ITEM SCALE

	// INITIAL ITEM ALPHA

	// OTHER INITIAL CONFIG
	// Disable the backgound
	this.mChildMenuScene.setBackgroundEnabled(false);
	// Listener
	this.mChildMenuScene.setOnMenuItemClickListener(this);

	// INITIAL MODIFIER

	// ATACH ITEMS
	// Attach Texts to Buttons
	this.mButtonBack.attachChild(this.mTextButtonBack);
	this.mButtonDelete.attachChild(this.mtextButtonDelete);
	this.mButtonLoad.attachChild(this.mTextButtonLoad);

	// Attach Buttons to MenuScene
	this.mChildMenuScene.addMenuItem(this.mButtonBack);
	this.mChildMenuScene.addMenuItem(this.mButtonDelete);
	this.mChildMenuScene.addMenuItem(this.mButtonLoad);

	// Add menuButtonsScene in Main Menu Scene
	this.setChildScene(this.mChildMenuScene);
    }

    /**
     * Apply the effects when scene is attached
     */
    @Override
    public void applyScreenInActions() {
	this.loadGamesFromSharedPreferences();

	// Displays the Header Text
	this.regFadeInEntityModifier(this.mTextHeader,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);

	// Displays the Back Button
	this.regMoveEntityModifier(this.mButtonBack,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED,
		this.mPositionButtonBackX,
		MenuConstantsUtility.MENU_BUTTONS_POS_Y_HIDDEN,
		this.mPositionButtonBackX,
		MenuConstantsUtility.MENU_BUTTONS_POS_Y_SHOWN, true);
    }

    /**
     * Apply the effects when scene is detached
     */
    @Override
    public void applyScreenOutActions() {
	// If the text that tells that there are no loaded games is shown,
	// undisplays it
	if (this.mTextNoSavedGames.getAlpha() != 0) {
	    this.regFadeOutEntityModifier(this.mTextNoSavedGames,
		    MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	}
	// Shows an animation for each Load Game Item Text and Button
	for (Text textEntity : mTextSavedGames) {
	    this.regFadeOutEntityModifier(textEntity,
		    MenuConstantsUtility.ANIMATION_FAST_SPEED, true);
	}
	for (IMenuItem button : mButtonSavedGames) {
	    if (!button.isDisposed()) {
		this.regFadeOutEntityModifier(button,
			MenuConstantsUtility.ANIMATION_FAST_SPEED, true);
	    }
	}
	// Registers a Modifier to the Header Text
	this.regFadeOutEntityModifier(this.mTextHeader,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);

	// Creates a Modifier to hide the Back Button.
	MoveModifier modifier = new MoveModifier(
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED,
		this.mPositionButtonBackX,
		MenuConstantsUtility.MENU_BUTTONS_POS_Y_SHOWN,
		this.mPositionButtonBackX,
		MenuConstantsUtility.MENU_BUTTONS_POS_Y_HIDDEN);

	// Registers a Listener to the Modifier which loads the Main Menu Scene
	modifier.addModifierListener(new IEntityModifierListener() {
	    @Override
	    public void onModifierStarted(IModifier<IEntity> pModifier,
		    IEntity pItem) {
	    }

	    @Override
	    public void onModifierFinished(final IModifier<IEntity> pModifier,
		    final IEntity pItem) {

		// Opens a new Thread which is the one thread of the engines
		// which checks the status of the entities.
		MainParameters.activity.getEngine().runOnUpdateThread(
			new Runnable() {
			    public void run() {
				// On a separate Thread from this one which
				// modelizes
				// the view, traverses the array with the texts
				// and
				// detach them
				for (Text textEntity : mTextSavedGames) {
				    textEntity.detachSelf();
				    textEntity.dispose();
				    textEntity = null;
				}
				mTextSavedGames = null;

				// Does the same with the Buttons
				for (IMenuItem button : mButtonSavedGames) {
				    if (!button.isDisposed()) {
					mChildMenuScene.detachChild(button);
					button.detachSelf();
					button.dispose();
				    }
				    button = null;
				}
				mButtonSavedGames = null;
			    }
			});
		mSceneController.createMainMenuScene(null);
		pModifier.removeModifierListener(this);
	    }
	});
	this.regModifyEntity(this.mButtonBack, modifier, true);
    }

    /**
     * Controls which MenuItem is beign clicked
     */
    @Override
    public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
	    float pMenuItemLocalX, float pMenuItemLocalY) {
	if (pMenuItem.getID() == backOption) {
	    // If there's any Load Game Item selected, returns all to the
	    // original state
	    if (this.mIsLoadGameSelected) {
		this.renderToDefaultState();
	    }
	    // If not, returns to Main Menu
	    else {
		this.applyScreenOutActions();
	    }
	    return true;
	}

	// If the clicked MenuItem is Load Option and it's visible (used as
	// isEnabled(), there's no such method for MenuItems) loads a game
	else if (pMenuItem.getID() == loadOption
		&& this.mButtonLoad.isVisible()) {
	    this.loadGame(this.mButtonSelectedLoadGame);
	    return true;
	}

	// Same as before, but with Delete Option
	else if (pMenuItem.getID() == deleteOption
		&& this.mButtonDelete.isVisible()) {
	    this.removeLoadedGame(this.mButtonSelectedLoadGame);
	    return true;
	}

	// Here, determines which Load Game Item is selected
	// Because the Back, Load and Delete buttons has ID's from 0 to 2,
	// it checks if the item selected is between the range of the valid
	// Load Items
	else if (pMenuItem.getID() >= 3
		&& pMenuItem.getID() <= this.mButtonSavedGames.size() + 3
		&& !pMenuItem.isDisposed()) {
	    this.applyEffectsWhenSelectedItem(pMenuItem);
	    return true;
	} else {
	    return false;
	}
    }

    @Override
    public void disposeScene() {
	// Runs the detach and dispose operations on a different Thread
	MainParameters.engine.runOnUpdateThread(new Runnable() {
	    @Override
	    public void run() {
		// DETACH ENTITIES
		// Detaches the elements of the Background
		mSpriteLogo.detachSelf();
		mSpriteBackground.detachSelf();
		mTextHeader.detachSelf();
		mTextNoSavedGames.detachSelf();
		// Detaches the buttons of the menu
		mTextButtonBack.detachSelf();
		mtextButtonDelete.detachSelf();
		mTextButtonLoad.detachSelf();
		mButtonBack.detachSelf();
		mButtonDelete.detachSelf();
		mButtonLoad.detachSelf();
		// Detaches the text of the loaded games of the menu
		if (mTextSavedGames != null && mButtonSavedGames != null) {
		    for (Text text : mTextSavedGames) {
			text.detachSelf();
		    }
		    // Detaches the buttons of the loaded games of the menu
		    for (IMenuItem item : mButtonSavedGames) {
			item.detachSelf();
		    }
		}
		// Detaches the selected load game if not null
		if (mButtonSelectedLoadGame != null) {
		    mButtonSelectedLoadGame.detachSelf();
		}

		// DISPOSE
		// Disposes the elements of the Background
		mSpriteLogo.dispose();
		mSpriteBackground.dispose();
		mTextHeader.dispose();
		mTextNoSavedGames.dispose();
		// Disposes the buttons of the menu
		mTextButtonBack.dispose();
		mtextButtonDelete.dispose();
		mTextButtonLoad.dispose();
		mButtonBack.dispose();
		mButtonDelete.dispose();
		mButtonLoad.dispose();
		// Disposes the text of the loaded games of the menu
		if (mTextSavedGames != null && mButtonSavedGames != null) {
		    for (Text text : mTextSavedGames) {
			text.dispose();
		    }
		    // Dispose the buttons of the loaded games of the menu
		    for (IMenuItem item : mButtonSavedGames) {
			item.detachSelf();
		    }
		    // Disposes the selected load game if not null
		    if (mButtonSelectedLoadGame != null) {
			mButtonSelectedLoadGame.dispose();
		    }
		}

		// SET NULL
		// Sets the Font to null
		mFont = null;
		// Sets the saved game to null
		mSavedGames = null;
		// Sets the loaded keys to null
		mLoadedGamesKeys = null;
		// Detaches the MenuScene
		mChildMenuScene.detachSelf();
		// Disposes the MenuScene
		mChildMenuScene.dispose();

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
     * Loads the games to be shown on the Scene
     */
    private void loadGamesFromSharedPreferences() {
	// Loads the saved games
	SavedGamesManager.getInstance().loadAllDataFromSharedPreferences();
	this.mSavedGames = SavedGamesManager.getInstance().getLastFive();

	// Initiates new arrays to store the entities which will modelize the
	// MenuItems
	this.mTextSavedGames = new ArrayList<Text>();
	this.mButtonSavedGames = new ArrayList<IMenuItem>();
	this.mLoadedGamesKeys = new ArrayList<String>();

	// A counter of elements
	int cntElements = 3;

	// Checks if the size of the map is 0 which means that there are no
	// saved games
	if (this.mSavedGames.size() == 0) {
	    this.regFadeInEntityModifier(this.mTextNoSavedGames,
		    MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);

	} else {
	    // Gets the data of the loaded games
	    for (Map.Entry<String, String> entry : this.mSavedGames.entrySet()) {
		String key = entry.getKey();
		this.mLoadedGamesKeys.add(key);
		String text = entry.getValue();

		// Gets the key on date format
		Locale locale = new Locale("ES");
		Long millisKeyValue = Long.parseLong(key);
		Date date = new Date(millisKeyValue);
		SimpleDateFormat dateFormatter = new SimpleDateFormat(
			"dd.MM.yyyy '-' HH:mm", locale);
		String formattedDate = dateFormatter.format(date);
		String[] splittedText = text.split(",");
		// Modelizes the text that will be shown for the user
		text = formattedDate + "  " + splittedText[0] + "  "
			+ splittedText[1] + "  " + splittedText[2] + "  "
			+ splittedText[3];

		// Gets the Text and the Button
		ScaleMenuItemDecorator button = new ScaleMenuItemDecorator(
			new SpriteMenuItem(
				cntElements,
				this.mMenuResourceManager
					.getButtonsTexture()
					.get(MenuConstantsUtility.MENU_BOX_LOAD_GAME),
				MainParameters.vbom), 1.2f, 1);
		Text textEntity = new TextMenuItem(cntElements, this.mFont,
			text, MainParameters.vbom);

		// Sets the position of the Text and the Button
		textEntity.setPosition(button.getWidth() / 2,
			button.getHeight() / 2);
		button.setPosition(MenuConstantsUtility.SCREEN_POS_CENTER_X,
			cntElements * 70);

		// Sets the Alpha of the Button and the Text
		button.setAlpha(0);
		textEntity.setAlpha(0);

		// Attatches the Text to the Button
		button.attachChild(textEntity);

		// Sets Fade Modifiers to the Button and the Text
		this.regFadeInEntityModifier(button,
			MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
		this.regFadeInEntityModifier(textEntity,
			MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);

		this.mTextSavedGames.add(textEntity);
		this.mButtonSavedGames.add(button);

		// Adds the Button to the child as Menu Item
		this.mChildMenuScene.addMenuItem(button);

		cntElements++;
	    }
	}

	this.mChildMenuScene.setOnMenuItemClickListener(this);
    }

    /**
     * Loads a saved game
     * 
     * @param selectedGame
     *            the selected Item on the Menu
     */
    private void loadGame(final IMenuItem selectedGame) {
	// Gets the text of the button
	final String savedGameID = (String) this.mLoadedGamesKeys
		.get(selectedGame.getID() - 3);
	// Splits it to get the key of the Loaded Game
	final String[] savedGameData = SavedGamesManager.getInstance()
		.loadDataFromSharedPreferences(savedGameID).split(",");

	final String mapID = savedGameData[0];
	final String playerSelected = savedGameData[1];
	final String difficulty = savedGameData[2];
	final String score = savedGameData[3];

	this.mGameManager.setLevelMap(mapID);
	this.mGameManager.setPlayerSelected(playerSelected);
	this.mGameManager.setDifficulty(difficulty);
	this.mGameManager.setmScore(Integer.parseInt(score));
	this.mSceneController.loadGameScene(false);
    }

    /**
     * Modifies the elements and the behavior of the elements when a Load Game
     * Item is selected
     * 
     * @param menuItem
     *            the selected Load Game Item
     */
    private void applyEffectsWhenSelectedItem(IMenuItem menuItem) {
	// If there's no item selected, displays an animation on the selected
	// item and displays the Delete and Load buttons
	if (!this.mIsLoadGameSelected) {
	    menuItem.registerEntityModifier(new ScaleModifier(
		    MenuConstantsUtility.ANIMATION_FAST_SPEED, 1, 1.2f));
	    this.mIsLoadGameSelected = true;
	    this.mButtonSelectedLoadGame = menuItem;

	    this.regMoveEntityModifier(this.mButtonDelete,
		    MenuConstantsUtility.ANIMATION_NORMAL_SPEED,
		    this.mPositionButtonDeleteX,
		    MenuConstantsUtility.MENU_BUTTONS_POS_Y_HIDDEN,
		    this.mPositionButtonDeleteX,
		    MenuConstantsUtility.MENU_BUTTONS_POS_Y_SHOWN, false);

	    this.regMoveEntityModifier(this.mButtonLoad,
		    MenuConstantsUtility.ANIMATION_NORMAL_SPEED,
		    this.mPositionButtonLoadX,
		    MenuConstantsUtility.MENU_BUTTONS_POS_Y_HIDDEN,
		    this.mPositionButtonLoadX,
		    MenuConstantsUtility.MENU_BUTTONS_POS_Y_SHOWN, false);

	    this.mButtonDelete.setVisible(true);
	    this.mButtonLoad.setVisible(true);
	}

	// It the selected Item is the same as the current selected, deselects
	// it
	else if (this.mButtonSelectedLoadGame == menuItem) {
	    this.renderToDefaultState();
	}

	// If the new selected item is other than the current selected item,
	// deselects the item and selects the new one
	else {
	    this.mButtonSelectedLoadGame
		    .registerEntityModifier(new ScaleModifier(
			    MenuConstantsUtility.ANIMATION_FAST_SPEED, 1.2f, 1));
	    menuItem.registerEntityModifier(new ScaleModifier(
		    MenuConstantsUtility.ANIMATION_FAST_SPEED, 1, 1.2f));
	    this.mButtonSelectedLoadGame = menuItem;
	}
    }

    /**
     * Removes a loaded game
     * 
     * @param selectedGame
     *            the selected Item on the Menu
     */
    private void removeLoadedGame(IMenuItem selectedGame) {
	// Gets the text of the button
	String savedGameKey = (String) this.mLoadedGamesKeys.get(selectedGame
		.getID() - 3);
	// Deletes the game using the loaded game manager
	SavedGamesManager.getInstance().removeDataFromSharedPreferences(
		savedGameKey);

	// Detachs the selected Load Game Item from the Menu
	this.mChildMenuScene.detachChild(selectedGame);
	selectedGame.detachSelf();

	// Reallocates the elements traversing all the elements on the menu
	for (IMenuItem menuItem : this.mChildMenuScene.getMenuItems()) {

	    // Checks if the items has to be affected by the change, which would
	    // be all that are Load Game Items
	    if (menuItem.getID() >= 3
		    && menuItem.getID() < selectedGame.getID()) {
		this.regMoveEntityModifier(menuItem,
			MenuConstantsUtility.ANIMATION_NORMAL_SPEED,
			menuItem.getX(), menuItem.getY(), menuItem.getX(),
			menuItem.getY() + 60, true);
		menuItem.setPosition(menuItem.getX(), menuItem.getY());
	    }
	}

	// Disposes the selected item and renders the other MenuItems to it's
	// default state
	selectedGame.dispose();
	selectedGame = null;
	this.mButtonSelectedLoadGame = null;
	this.mIsLoadGameSelected = false;
	this.renderToDefaultState();
    }

    /**
     * Positions all the elements to it's initial state
     */
    private void renderToDefaultState() {
	// Moves the load and delete button to it's default location
	// Creates a Modifier and a Modifier Listener for the the Load Button.
	MoveModifier moveModifierButtonLoad = new MoveModifier(
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED,
		this.mPositionButtonLoadX,
		MenuConstantsUtility.MENU_BUTTONS_POS_Y_SHOWN,
		this.mPositionButtonLoadX,
		MenuConstantsUtility.MENU_BUTTONS_POS_Y_HIDDEN);

	moveModifierButtonLoad
		.addModifierListener(new IEntityModifierListener() {
		    @Override
		    public void onModifierStarted(IModifier<IEntity> pModifier,
			    IEntity pItem) {
		    }

		    @Override
		    public void onModifierFinished(
			    final IModifier<IEntity> pModifier,
			    final IEntity pItem) {
			mButtonLoad.setVisible(false);
		    }
		});

	// Creates a Modifier and a Modifier Listener for the Delete Button.
	MoveModifier moveModifierButtonDelete = new MoveModifier(
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED,
		this.mPositionButtonDeleteX,
		MenuConstantsUtility.MENU_BUTTONS_POS_Y_SHOWN,
		this.mPositionButtonDeleteX,
		MenuConstantsUtility.MENU_BUTTONS_POS_Y_HIDDEN);

	moveModifierButtonDelete
		.addModifierListener(new IEntityModifierListener() {
		    @Override
		    public void onModifierStarted(IModifier<IEntity> pModifier,
			    IEntity pItem) {
		    }

		    @Override
		    public void onModifierFinished(
			    final IModifier<IEntity> pModifier,
			    final IEntity pItem) {
			mButtonDelete.setVisible(false);
		    }
		});

	// Registers the modifiers on the Load Button and Delete Button
	this.regModifyEntity(this.mButtonLoad, moveModifierButtonLoad, true);
	this.regModifyEntity(this.mButtonDelete, moveModifierButtonDelete, true);

	// If there's a selected game, deselects it
	if (this.mButtonSelectedLoadGame != null) {
	    ScaleModifier modifier = new ScaleModifier(
		    MenuConstantsUtility.ANIMATION_FAST_SPEED, 1.2f, 1);
	    modifier.addModifierListener(new IEntityModifierListener() {
		@Override
		public void onModifierStarted(IModifier<IEntity> pModifier,
			IEntity pItem) {
		}

		@Override
		public void onModifierFinished(
			final IModifier<IEntity> pModifier, final IEntity pItem) {
		    // mButtonSelectedLoadGame.clearEntityModifiers();
		    mButtonSelectedLoadGame = null;
		}
	    });
	    this.regModifyEntity(this.mButtonSelectedLoadGame, modifier, true);
	}
	this.mIsLoadGameSelected = false;
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}
