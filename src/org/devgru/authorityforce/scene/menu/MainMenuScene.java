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
import org.andengine.entity.modifier.FadeOutModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.font.Font;
import org.andengine.util.modifier.IModifier;
import org.devgru.authorityforce.R;
import org.devgru.authorityforce.activity.VideoActivity;
import org.devgru.authorityforce.constant.GeneralConstantsUtility;
import org.devgru.authorityforce.constant.GeneralConstantsUtility.SceneType;
import org.devgru.authorityforce.constant.MainParameters;
import org.devgru.authorityforce.constant.MenuConstantsUtility;
import org.devgru.authorityforce.scene.ABaseMenuScene;

import android.content.Intent;

/**
 * MainMenuScene.java
 * 
 * It is the main menu of the application allows access to all game options
 * 
 * @version 1.0 Apr 19, 2013
 * @author Aitor Gonzalez Martinez
 * @author Xavier Martinez de Francisco
 * @author Toni Trigo Diaz
 */
public class MainMenuScene extends ABaseMenuScene {

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    // Buttons ID
    /* Id to character selection button */
    private final int characterSelectionMenuScene = 0;
    /* Id to load game button */
    private final int loadGameMenuScene = 1;
    /* Id to options button */
    private final int optionsMenuScene = 2;
    /* Id to credits button */
    private final int credits = 3;
    /* Id to help button */
    private final int helpMenuScene = 4;
    /* Id to exit button */
    private final int exit = 5;

    // POSITIONS
    /* Position X to Button Play */
    private final float mPosNewGameBtnX = GeneralConstantsUtility.SCREEN_WIDTH_RESOLUTION - 1050;
    /* Position X to Button Load */
    private final float mPosLoadGameBtnX = GeneralConstantsUtility.SCREEN_WIDTH_RESOLUTION - 785;
    /* Position X to Button Options */
    private final float mPosOptionsBtnX = GeneralConstantsUtility.SCREEN_WIDTH_RESOLUTION - 495;
    /* Position X to Button Credits */
    private final float mPosCreditsBtnX = GeneralConstantsUtility.SCREEN_WIDTH_RESOLUTION - 230;
    /* Position X to Button Help */
    private final float mPosHelpBtnX = GeneralConstantsUtility.SCREEN_WIDTH_RESOLUTION - 100;
    /* Position X to Button Exit */
    private final float mPosExitBtnX = GeneralConstantsUtility.SCREEN_WIDTH_RESOLUTION - 1200;
    /* Position Y to Button Exit */
    private final float mPosHelpExitBtnY = GeneralConstantsUtility.SCREEN_HEIGHT_RESOLUTION - 40;

    // ITEMS TO BASESCENE
    /* Background Sprite */
    private Sprite mSpriteBackground;
    /* Logo Sprite */
    private Sprite mSpriteLogo;

    // ITEMS TO CHILDMENUSCENE
    /* The scene which contains all the buttons */
    private MenuScene mChildMenuScene;
    /* The New Game Button */
    private IMenuItem mButtonNewGame;
    /* The Load Game Button */
    private IMenuItem mButtonLoadGame;
    /* The Options Button */
    private IMenuItem mButtonOptions;
    /* The Credits Button */
    private IMenuItem mButtonCredits;
    /* The Help Button */
    private IMenuItem mButtonHelp;
    /* The Exit Button */
    private IMenuItem mButtonExit;
    /* The text for the New Game Button. */
    private TextMenuItem mTextButtonNewGame;
    /* The text for Load Game Button. */
    private TextMenuItem mTextButtonLoadGame;
    /* The text for Options Button. */
    private TextMenuItem mTextButtonOptions;
    /* The text for Credits Button. */
    private TextMenuItem mTextButtonCredits;
    /* The text for Help Button. */
    private TextMenuItem mTextButtonHelp;
    /* The text for Exit Button. */
    private TextMenuItem mTextButtonExit;
    /* The Font */
    private Font mFont;
    /* Option chosen by the user. */
    private int mMenuSelectedOption;

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
     * Create Main Menu scene
     */
    @Override
    public void createScene() {
	// Call superclass method to get all resources and start creating items
	super.createScene();

	// Start music for menus
	this.mAudioPlayer.setVolumeMaster();
    }

    /**
     * This is called when back button is pressed
     */
    @Override
    public void onBackKeyPressed() {
	// Close the aplication
	System.exit(0);
    }

    /**
     * Returns the type of scene
     * 
     * @return returns the type of scene
     */
    @Override
    public SceneType getSceneType() {
	return SceneType.MAIN_MENU_SCENE;
    }

    /**
     * Loads all the screen elements the user can not interact with.
     */
    @Override
    public void createBaseScene() {
	// RESOURCES

	// CREATE ITEMS
	// Cretae the sprite with background texture
	this.mSpriteBackground = new Sprite(0, 0,
		this.mMenuResourceManager.getBackgroundTexture(),
		MainParameters.vbom);
	// Create the sprite with logo texture
	this.mSpriteLogo = new Sprite(0, 0,
		this.mMenuResourceManager.getLogoTexture(), MainParameters.vbom);

	// INITIAL ITEM POSISTIONS
	this.mSpriteBackground.setPosition(
		MenuConstantsUtility.SCREEN_POS_CENTER_X,
		MenuConstantsUtility.SCREEN_POS_CENTER_Y);
	this.mSpriteLogo.setPosition(MenuConstantsUtility.SCREEN_POS_CENTER_X,
		MenuConstantsUtility.MENU_LOGO_POS_Y);

	// INITIAL ITEM SCALES

	// INITIAL ITEM ALPHAS
	this.mSpriteLogo.setAlpha(0);
	this.mSpriteBackground.setAlpha(0);

	// OTHER INITIAL CONFIG

	// INITIAL MODIFIER
	this.regFadeInEntityModifier(this.mSpriteBackground,
		MenuConstantsUtility.ANIMATION_FIRST_LOGO_SPEED, false);
	this.regFadeInEntityModifier(this.mSpriteLogo,
		MenuConstantsUtility.ANIMATION_FIRST_LOGO_SPEED, false);

	// ATTACH ITEMS
	this.attachChild(this.mSpriteBackground);
	this.attachChild(this.mSpriteLogo);
    }

    /**
     * Loads all clickable items on the screen.
     */
    @Override
    public void createChildMenuScene() {
	// RESOURCES
	// Get the font
	this.mFont = (Font) this.mMenuResourceManager.getFont();

	// CREATE ITEMS
	this.mChildMenuScene = new MenuScene(MainParameters.camera);
	// Create the buttons
	this.mButtonNewGame = new ScaleMenuItemDecorator(new SpriteMenuItem(
		characterSelectionMenuScene, this.mMenuResourceManager
			.getButtonsTexture().get(
				MenuConstantsUtility.MENU_BUTTON_LEFT),
		MainParameters.vbom),
		MenuConstantsUtility.MENU_SELECTED_BUTTON_SIZE,
		MenuConstantsUtility.MENU_UNSELECTED_BUTTON_SIZE);

	this.mButtonLoadGame = new ScaleMenuItemDecorator(new SpriteMenuItem(
		loadGameMenuScene, this.mMenuResourceManager
			.getButtonsTexture().get(
				MenuConstantsUtility.MENU_BUTTON_MIDDLE_LEFT),
		MainParameters.vbom),
		MenuConstantsUtility.MENU_SELECTED_BUTTON_SIZE,
		MenuConstantsUtility.MENU_UNSELECTED_BUTTON_SIZE);

	this.mButtonOptions = new ScaleMenuItemDecorator(new SpriteMenuItem(
		optionsMenuScene, this.mMenuResourceManager.getButtonsTexture()
			.get(MenuConstantsUtility.MENU_BUTTON_MIDDLE_RIGHT),
		MainParameters.vbom),
		MenuConstantsUtility.MENU_SELECTED_BUTTON_SIZE,
		MenuConstantsUtility.MENU_UNSELECTED_BUTTON_SIZE);

	this.mButtonCredits = new ScaleMenuItemDecorator(new SpriteMenuItem(
		credits, this.mMenuResourceManager.getButtonsTexture().get(
			MenuConstantsUtility.MENU_BUTTON_RIGHT),
		MainParameters.vbom),
		MenuConstantsUtility.MENU_SELECTED_BUTTON_SIZE,
		MenuConstantsUtility.MENU_UNSELECTED_BUTTON_SIZE);

	this.mButtonHelp = new ScaleMenuItemDecorator(new SpriteMenuItem(
		helpMenuScene, this.mMenuResourceManager.getButtonsTexture()
			.get(MenuConstantsUtility.MENU_BUTTON_HELP),
		MainParameters.vbom),
		MenuConstantsUtility.MENU_SELECTED_BUTTON_SIZE,
		MenuConstantsUtility.MENU_UNSELECTED_BUTTON_SIZE);

	this.mButtonExit = new ScaleMenuItemDecorator(new SpriteMenuItem(exit,
		this.mMenuResourceManager.getButtonsTexture().get(
			MenuConstantsUtility.MENU_BUTTON_EXIT),
		MainParameters.vbom),
		MenuConstantsUtility.MENU_SELECTED_BUTTON_SIZE,
		MenuConstantsUtility.MENU_UNSELECTED_BUTTON_SIZE);

	// Load text for the buttons
	this.mTextButtonNewGame = new TextMenuItem(characterSelectionMenuScene,
		this.mFont,
		MainParameters.activity.getString(R.string.btn_new_game),
		MainParameters.vbom);

	this.mTextButtonLoadGame = new TextMenuItem(loadGameMenuScene,
		this.mFont,
		MainParameters.activity.getString(R.string.btn_load_game),
		MainParameters.vbom);

	this.mTextButtonOptions = new TextMenuItem(optionsMenuScene,
		this.mFont,
		MainParameters.activity.getString(R.string.btn_options),
		MainParameters.vbom);

	this.mTextButtonCredits = new TextMenuItem(credits, this.mFont,
		MainParameters.activity.getString(R.string.btn_credits),
		MainParameters.vbom);

	this.mTextButtonHelp = new TextMenuItem(helpMenuScene, this.mFont,
		MainParameters.activity.getString(R.string.btn_help),
		MainParameters.vbom);

	this.mTextButtonExit = new TextMenuItem(exit, this.mFont,
		MainParameters.activity.getString(R.string.btn_exit),
		MainParameters.vbom);

	// INITIAL ITEM POSISTIONS
	// Positioning the buttons
	this.mButtonNewGame.setPosition(this.mPosNewGameBtnX,
		MenuConstantsUtility.MENU_BUTTONS_POS_Y_SHOWN);
	this.mButtonLoadGame.setPosition(this.mPosLoadGameBtnX,
		MenuConstantsUtility.MENU_BUTTONS_POS_Y_SHOWN);
	this.mButtonOptions.setPosition(this.mPosOptionsBtnX,
		MenuConstantsUtility.MENU_BUTTONS_POS_Y_SHOWN);
	this.mButtonCredits.setPosition(this.mPosCreditsBtnX,
		MenuConstantsUtility.MENU_BUTTONS_POS_Y_SHOWN);
	this.mButtonHelp.setPosition(this.mPosHelpBtnX, this.mPosHelpExitBtnY);
	this.mButtonExit.setPosition(this.mPosExitBtnX, this.mPosHelpExitBtnY);

	// Positioning the text
	this.mTextButtonNewGame.setPosition(this.mButtonNewGame.getWidth() / 2,
		this.mButtonNewGame.getHeight() / 2);
	this.mTextButtonLoadGame.setPosition(
		this.mButtonLoadGame.getWidth() / 2,
		this.mButtonLoadGame.getHeight() / 2);
	this.mTextButtonOptions.setPosition(this.mButtonOptions.getWidth() / 2,
		this.mButtonOptions.getHeight() / 2);
	this.mTextButtonCredits.setPosition(this.mButtonCredits.getWidth() / 2,
		this.mButtonCredits.getHeight() / 2);
	this.mTextButtonHelp.setPosition(this.mButtonHelp.getWidth() / 2 + 20,
		this.mButtonHelp.getHeight() / 2 + 10);
	this.mTextButtonExit.setPosition(this.mButtonExit.getWidth() / 2 - 20,
		this.mButtonExit.getHeight() / 2 + 10);

	// INITIAL ITEM SCALES

	// INITIAL ITEM ALPHAS
	this.mButtonNewGame.setAlpha(0);
	this.mButtonLoadGame.setAlpha(0);
	this.mButtonOptions.setAlpha(0);
	this.mButtonCredits.setAlpha(0);
	this.mButtonHelp.setAlpha(0);
	this.mButtonExit.setAlpha(0);

	this.mTextButtonNewGame.setAlpha(0);
	this.mTextButtonLoadGame.setAlpha(0);
	this.mTextButtonOptions.setAlpha(0);
	this.mTextButtonCredits.setAlpha(0);
	this.mTextButtonHelp.setAlpha(0);
	this.mTextButtonExit.setAlpha(0);

	// OTHER INITIAL CONFIG
	// Enable the backgound
	this.mChildMenuScene.setBackgroundEnabled(false);
	// Listener
	this.mChildMenuScene.setOnMenuItemClickListener(this);

	// INITIAL MODIFIER
	this.regFadeInEntityModifier(this.mButtonNewGame,
		MenuConstantsUtility.ANIMATION_FIRST_LOGO_SPEED, false);
	this.regFadeInEntityModifier(this.mButtonLoadGame,
		MenuConstantsUtility.ANIMATION_FIRST_LOGO_SPEED, false);
	this.regFadeInEntityModifier(this.mButtonOptions,
		MenuConstantsUtility.ANIMATION_FIRST_LOGO_SPEED, false);
	this.regFadeInEntityModifier(this.mButtonCredits,
		MenuConstantsUtility.ANIMATION_FIRST_LOGO_SPEED, false);
	this.regFadeInEntityModifier(this.mButtonHelp,
		MenuConstantsUtility.ANIMATION_FIRST_LOGO_SPEED, false);
	this.regFadeInEntityModifier(this.mButtonExit,
		MenuConstantsUtility.ANIMATION_FIRST_LOGO_SPEED, false);
	this.regFadeInEntityModifier(this.mTextButtonNewGame,
		MenuConstantsUtility.ANIMATION_FIRST_LOGO_SPEED, false);
	this.regFadeInEntityModifier(this.mTextButtonLoadGame,
		MenuConstantsUtility.ANIMATION_FIRST_LOGO_SPEED, false);
	this.regFadeInEntityModifier(this.mTextButtonOptions,
		MenuConstantsUtility.ANIMATION_FIRST_LOGO_SPEED, false);
	this.regFadeInEntityModifier(this.mTextButtonCredits,
		MenuConstantsUtility.ANIMATION_FIRST_LOGO_SPEED, false);
	this.regFadeInEntityModifier(this.mTextButtonHelp,
		MenuConstantsUtility.ANIMATION_FIRST_LOGO_SPEED, false);
	this.regFadeInEntityModifier(this.mTextButtonExit,
		MenuConstantsUtility.ANIMATION_FIRST_LOGO_SPEED, false);

	// ATTACH ITEMS
	// Attach text in buttons
	this.mButtonNewGame.attachChild(this.mTextButtonNewGame);
	this.mButtonLoadGame.attachChild(this.mTextButtonLoadGame);
	this.mButtonOptions.attachChild(this.mTextButtonOptions);
	this.mButtonCredits.attachChild(this.mTextButtonCredits);
	this.mButtonHelp.attachChild(this.mTextButtonHelp);
	this.mButtonExit.attachChild(this.mTextButtonExit);

	// Add the buttons in menuButtonsScene
	this.mChildMenuScene.addMenuItem(this.mButtonNewGame);
	this.mChildMenuScene.addMenuItem(this.mButtonLoadGame);
	this.mChildMenuScene.addMenuItem(this.mButtonOptions);
	this.mChildMenuScene.addMenuItem(this.mButtonCredits);
	this.mChildMenuScene.addMenuItem(this.mButtonHelp);
	this.mChildMenuScene.addMenuItem(this.mButtonExit);

	// Add menuButtonsScene in Main Menu Scene
	this.setChildScene(this.mChildMenuScene);
    }

    /**
     * Apply the effects when scene is attached
     */
    @Override
    public void applyScreenInActions() {
	this.regAlphaEntityModifier(this.mSpriteLogo,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, 0.4f, 1, false);
	this.regFadeInEntityModifier(this.mButtonNewGame,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, false);
	this.regFadeInEntityModifier(this.mTextButtonNewGame,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, false);
	this.regFadeInEntityModifier(this.mButtonLoadGame,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, false);
	this.regFadeInEntityModifier(this.mTextButtonLoadGame,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, false);
	this.regFadeInEntityModifier(this.mButtonOptions,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, false);
	this.regFadeInEntityModifier(this.mTextButtonOptions,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, false);
	this.regFadeInEntityModifier(this.mButtonCredits,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, false);
	this.regFadeInEntityModifier(this.mTextButtonCredits,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, false);
	this.regFadeInEntityModifier(this.mButtonHelp,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, false);
	this.regFadeInEntityModifier(this.mTextButtonHelp,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, false);
	this.regFadeInEntityModifier(this.mButtonExit,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, false);
	this.regFadeInEntityModifier(this.mTextButtonExit,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, false);
    }

    /**
     * Apply the effects when scene is detached
     */
    @Override
    public void applyScreenOutActions() {
	// Create a Modifier for wait hidden buttons
	FadeOutModifier alphaOut = new FadeOutModifier(
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED);
	alphaOut.addModifierListener(new IEntityModifierListener() {
	    @Override
	    public void onModifierStarted(IModifier<IEntity> pModifier,
		    IEntity pItem) {
	    }

	    // When the modifier finalize
	    @Override
	    public void onModifierFinished(final IModifier<IEntity> pModifier,
		    final IEntity pItem) {
		// Remove this modifier
		pModifier.removeModifierListener(this);

		// Chante to selected scene
		switch (mMenuSelectedOption) {

		case characterSelectionMenuScene:
		    MainMenuScene.this.mSceneController
			    .createCharacterSelectionMenuScene();
		    break;

		case loadGameMenuScene:
		    MainMenuScene.this.mSceneController
			    .createLoadGameMenuScene();
		    break;

		case optionsMenuScene:
		    MainMenuScene.this.mSceneController.createOptionMenuScene();
		    break;

		case helpMenuScene:
		    MainMenuScene.this.mSceneController.createHelpMenuScene();
		    break;

		case exit:
		    System.exit(0);
		    break;
		}
	    }
	});

	// Hide the buttons in Main Menu
	this.regAlphaEntityModifier(this.mSpriteLogo,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, 1, 0.4f, true);
	this.regFadeOutEntityModifier(this.mButtonNewGame,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeOutEntityModifier(this.mTextButtonNewGame,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeOutEntityModifier(this.mButtonLoadGame,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeOutEntityModifier(this.mTextButtonLoadGame,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeOutEntityModifier(this.mButtonOptions,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeOutEntityModifier(this.mTextButtonOptions,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeOutEntityModifier(this.mButtonCredits,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeOutEntityModifier(this.mTextButtonCredits,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeOutEntityModifier(this.mButtonHelp,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeOutEntityModifier(this.mTextButtonHelp,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeOutEntityModifier(this.mButtonExit,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeOutEntityModifier(this.mTextButtonExit,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regModifyEntity(this.mTextButtonHelp, alphaOut, true);
    }

    /**
     * Listen when press a button on this scene
     */
    @Override
    public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
	    float pMenuItemLocalX, float pMenuItemLocalY) {

	// Launch Video Activity
	if (pMenuItem.getID() == credits) {
	    MainParameters.activity.startActivity(new Intent(
		    MainParameters.activity, VideoActivity.class));

	    // Set another Scene
	} else {
	    // Save the option selected by the user
	    this.mMenuSelectedOption = pMenuItem.getID();
	    // Apply the effects out
	    this.applyScreenOutActions();
	}

	return true;
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
		// Detaches the elements from the background
		mSpriteBackground.detachSelf();
		mSpriteLogo.detachSelf();
		// Detaches the text of the buttons the menu
		mTextButtonNewGame.detachSelf();
		mTextButtonLoadGame.detachSelf();
		mTextButtonOptions.detachSelf();
		mTextButtonHelp.detachSelf();
		// Detaches the buttons from the menu
		mButtonNewGame.detachSelf();
		mButtonLoadGame.detachSelf();
		mButtonOptions.detachSelf();
		mButtonCredits.detachSelf();
		mButtonHelp.detachSelf();

		// DISPOSE
		// Disposes the elements from the background
		mSpriteBackground.dispose();
		mSpriteLogo.dispose();
		// Disposes the text of the buttons the menu
		mTextButtonNewGame.dispose();
		mTextButtonLoadGame.dispose();
		mTextButtonOptions.dispose();
		mTextButtonHelp.dispose();
		// Disposes the buttons of the menu
		mButtonNewGame.dispose();
		mButtonLoadGame.dispose();
		mButtonOptions.dispose();
		mButtonCredits.dispose();
		mButtonHelp.dispose();
		// Detaches the MenuScene
		mChildMenuScene.detachSelf();
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

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}
