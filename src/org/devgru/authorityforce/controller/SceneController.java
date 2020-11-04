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

package org.devgru.authorityforce.controller;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;
import org.devgru.authorityforce.constant.GeneralConstantsUtility;
import org.devgru.authorityforce.constant.GeneralConstantsUtility.SceneType;
import org.devgru.authorityforce.constant.JsonFieldsConstantsUtility;
import org.devgru.authorityforce.constant.MainParameters;
import org.devgru.authorityforce.dao.MapFluxDAO;
import org.devgru.authorityforce.manager.GameManager;
import org.devgru.authorityforce.manager.audio.MFXResourceManager;
import org.devgru.authorityforce.manager.scene.GameResourceManager;
import org.devgru.authorityforce.manager.scene.MenuResourceManager;
import org.devgru.authorityforce.preferences.PreferencesManager;
import org.devgru.authorityforce.preferences.SavedGamesManager;
import org.devgru.authorityforce.scene.ABaseMenuScene;
import org.devgru.authorityforce.scene.ABaseScene;
import org.devgru.authorityforce.scene.game.GameScene;
import org.devgru.authorityforce.scene.menu.CharacterSelectionMenuScene;
import org.devgru.authorityforce.scene.menu.HelpMenuScene;
import org.devgru.authorityforce.scene.menu.LoadGameScene;
import org.devgru.authorityforce.scene.menu.LoadingScene;
import org.devgru.authorityforce.scene.menu.MainMenuScene;
import org.devgru.authorityforce.scene.menu.OptionsMenuScene;

/**
 * SceneController.java
 * 
 * 
 * @version 1.0 Apr 19, 2013
 * @author Aitor Gonzalez Martinez
 * @author Xavier Martinez de Francisco
 * @author Toni Trigo Diaz
 */
public class SceneController {

    // ===========================================================
    // Constants
    // ===========================================================

    /* A Instance of SceneController */
    private static final SceneController INSTANCE = new SceneController();

    // ===========================================================
    // Fields
    // ===========================================================

    /* The Main Menu Scene */
    private ABaseScene mMainMenuScene;
    /* The Character Selection Menu Scene */
    private ABaseScene mCharacterSelectionMenuScene;
    /* The Load Game Menu Scene */
    private ABaseScene mLoadGameMenuScene;
    /* The Option Menu Scene */
    private ABaseScene mOptionMenuScene;
    /* The Help Menu Scene */
    private ABaseScene mHelpMenuScene;
    /* The Loading Scene */
    private ABaseScene mLoadingScene;
    /* The Game Scene */
    private ABaseScene mGameScene;

    /* The Current Scene */
    private ABaseScene mCurrentScene;
    /* The Current Scene Type */
    private SceneType mCurrentSceneType;

    /* The Menus Resource Manager */
    private MenuResourceManager mMenuResourceManger = MenuResourceManager
	    .getInstance();
    /* The Game Scenes Resource Manager */
    private GameResourceManager mGameResourceManger = GameResourceManager
	    .getInstance();
    /* The FX Resource Manager */
    private MFXResourceManager mMFXResourceManager = MFXResourceManager
	    .getInstance();

    // ===========================================================
    // Constructors
    // ===========================================================

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    /**
     * Gets a Instance of the SceneController.
     * 
     * @return a Instance of the SceneController.
     */
    public static SceneController getInstance() {
	return INSTANCE;
    }

    /**
     * Gets the current Scene type.
     * 
     * @return the current Scene type.
     */
    public SceneType getCurrentSceneType() {
	return this.mCurrentSceneType;
    }

    /**
     * Gets the current Scene.
     * 
     * @return the current Scene.
     */
    public ABaseScene getCurrentScene() {
	return this.mCurrentScene;
    }

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    // ===========================================================
    // Methods
    // ===========================================================

    /**
     * Sets a Scene.
     * 
     * @param pScene
     *            a Scene.
     */
    public void setScene(ABaseScene pScene) {
	// Sets the new Scene
	MainParameters.engine.setScene(pScene);
	// Save the new current scene
	this.mCurrentScene = pScene;
	// Save the new Scene Type
	this.mCurrentSceneType = pScene.getSceneType();
	// If the scene is a menu applies in effects
	if (pScene instanceof ABaseMenuScene) {
	    ((ABaseMenuScene) pScene).applyScreenInActions();
	}
    }

    /**
     * Sets a scene according to the type of this scene
     */
    public void setScene(SceneType pSceneType) {
	switch (pSceneType) {

	case MAIN_MENU_SCENE:
	    this.setScene(this.mMainMenuScene);
	    break;

	case CHARACTER_SELECTION_MENU_SCENE:
	    this.setScene(this.mCharacterSelectionMenuScene);
	    break;

	case LOAD_GAME_MENU_SCENE:
	    this.setScene(this.mLoadGameMenuScene);
	    break;

	case OPTIONS_MENU_SCENE:
	    this.setScene(this.mOptionMenuScene);
	    break;
	case HELP_MENU_SCENE:
	    this.setScene(this.mHelpMenuScene);
	    break;

	case LOADING_SCENE:
	    this.setScene(this.mLoadingScene);
	    break;

	case GAME_SCENE:
	    this.setScene(this.mGameScene);
	    break;

	default:
	    break;
	}
    }

    /**
     * Creates and sets the Main Menu Scene.
     * 
     * @param pOnCreateSceneCallback
     *            a AndEngine Callback.
     */
    public void createMainMenuScene(OnCreateSceneCallback pOnCreateSceneCallback) {
	// Not to create the same scene double
	if (this.mMainMenuScene == null) {
	    this.mMainMenuScene = new MainMenuScene();
	}
	// Sets the new Scene
	this.setScene(this.mMainMenuScene);

	// AndEngine Callback only call first time
	if (pOnCreateSceneCallback != null) {
	    pOnCreateSceneCallback.onCreateSceneFinished(this.mMainMenuScene);
	}
    }

    /**
     * Creates and sets the Character Selection Menu Scene.
     */
    public void createCharacterSelectionMenuScene() {
	if (this.mCharacterSelectionMenuScene == null) {
	    this.mCharacterSelectionMenuScene = new CharacterSelectionMenuScene();
	}
	this.setScene(this.mCharacterSelectionMenuScene);
    }

    /**
     * Creates and sets the Load Game Menu Scene.
     */
    public void createLoadGameMenuScene() {
	if (this.mLoadGameMenuScene == null) {
	    this.mLoadGameMenuScene = new LoadGameScene();
	}
	this.setScene(this.mLoadGameMenuScene);
    }

    /**
     * Creates and sets the Options Menu Scene.
     */
    public void createOptionMenuScene() {
	if (this.mOptionMenuScene == null) {
	    this.mOptionMenuScene = new OptionsMenuScene();
	}
	this.setScene(this.mOptionMenuScene);
    }

    /**
     * Creates and sets the Help Menu Scene.
     */
    public void createHelpMenuScene() {
	if (this.mHelpMenuScene == null) {
	    this.mHelpMenuScene = new HelpMenuScene();
	}
	this.setScene(this.mHelpMenuScene);
    }

    /**
     * Creates and sets the Load Scene.
     */
    public void createLoadingScene() {
	if (this.mLoadingScene == null) {
	    this.mLoadingScene = new LoadingScene();
	}
	// Generate new random sentence
	((LoadingScene) this.mLoadingScene).setRandomSentence();

	this.setScene(this.mLoadingScene);
    }

    /**
     * Creates and sets the Game Scene.
     */
    public void createGameScene() {
	if (this.mGameScene == null) {
	    this.mGameScene = new GameScene();
	}
	this.setScene(this.mGameScene);
    }

    /**
     * Dispose an loads the necessary resources to load the Main Menu Scene.
     */
    public void loadMainMenuScene() {
	// Disposes the Game Resources
	this.disposeGameResources();

	// Shows the Loading Scene
	this.createLoadingScene();

	// Restart the parameters to the camera, center on the display
	this.restartCamera();

	MainParameters.engine.registerUpdateHandler(new TimerHandler(1,
		new ITimerCallback() {
		    @Override
		    public void onTimePassed(final TimerHandler pTimerHandler) {
			// First unregister the UpdateHandler
			MainParameters.engine
				.unregisterUpdateHandler(pTimerHandler);

			// Loads the necessary resources for all menus
			SceneController.this.mMenuResourceManger
				.loadAllResources();
			SceneController.this.mMFXResourceManager
				.loadMenuAudio();

			// Resets the Game settings to it's default values
			GameManager.getInstance().resetDefaults();

			// Creates the Main Menu Scene
			SceneController.this.createMainMenuScene(null);
		    }
		}));
    }

    /**
     * Loads and sets the necessary resources to load the first Game Scene.
     * 
     * @param pIsNewGame
     *            determine if the Game Scene is a new game
     */
    public void loadGameScene(final boolean pIsNewGame) {
	// Shows a Loading Scene
	this.createLoadingScene();

	MainParameters.engine.registerUpdateHandler(new TimerHandler(1,
		new ITimerCallback() {
		    @Override
		    public void onTimePassed(final TimerHandler pTimerHandler) {
			// First unregister the UpdateHandler
			MainParameters.engine
				.unregisterUpdateHandler(pTimerHandler);

			// Disposes the menus resources
			SceneController.this.disposeMenusResources();

			// Gets all the necessary data to load entities on the
			// Scene
			MapFluxDAO.getInstance().loadMapsData();
			MapFluxDAO.getInstance().loadMapData(
				GameManager.getInstance().getLevelMap());

			// Loads all necessary resources for the game
			SceneController.this.mGameResourceManger
				.loadAllResources();
			SceneController.this.mMFXResourceManager
				.loadGameAudio();
			SceneController.this.mMFXResourceManager
				.loadGameSoundsFX();

			// If it's a new game, gets the difficulty from the
			// Preferences
			if (pIsNewGame) {
			    GameManager.getInstance().setDifficulty(
				    PreferencesManager.getInstance()
					    .getDifficultyLevel());
			}

			// Creates a new Game Scene
			SceneController.this.createGameScene();
		    }
		}));
    }

    /**
     * Loads and sets the necessary resources to load the next Scene.
     */
    public void loadNextScene() {
	// Gets the name of the next scene to be loaded.
	final String nextMap = MapFluxDAO.getInstance().getMapData(
		JsonFieldsConstantsUtility.FLUX_NEXT_MAP);

	// If it's not the last scene to load, loads the resources to create a
	// new scene
	if (!nextMap.equals(JsonFieldsConstantsUtility.FLUX_MAIN_MENU)) {
	    // Disposes all the resources from the last Game Scene
	    this.disposeGameResources();

	    // Creates a Loading Scene
	    this.createLoadingScene();

	    // Restart the parameters to the camera, center on the display
	    this.restartCamera();

	    MainParameters.engine.registerUpdateHandler(new TimerHandler(1,
		    new ITimerCallback() {
			@Override
			public void onTimePassed(
				final TimerHandler pTimerHandler) {

			    // First unregister the UpdateHandler
			    MainParameters.engine
				    .unregisterUpdateHandler(pTimerHandler);

			    // Saves the last game to be loaded for the user
			    // later
			    SavedGamesManager.getInstance().saveData(nextMap);

			    // Loads the necessary data to load all the entities
			    MapFluxDAO.getInstance().loadMapData(nextMap);

			    // Loads all necessary resources
			    SceneController.this.mGameResourceManger
				    .loadAllResources();
			    SceneController.this.mMFXResourceManager
				    .loadGameAudio();
			    SceneController.this.mMFXResourceManager
				    .loadGameSoundsFX();

			    // Creates a new Game Scene
			    SceneController.this.createGameScene();
			}
		    }));

	    // If it's the last Scene to be loaded, loads the Main Menu
	} else {
	    this.loadMainMenuScene();
	}
    }

    /**
     * Restart position of the camera
     */
    private void restartCamera() {
	MainParameters.camera.setChaseEntity(null);
	MainParameters.camera.setCenter(
		GeneralConstantsUtility.SCREEN_WIDTH_RESOLUTION / 2,
		GeneralConstantsUtility.SCREEN_HEIGHT_RESOLUTION / 2);
	MainParameters.camera.updateChaseEntity();
    }

    /**
     * Disposes all the resources of menus
     */
    private void disposeMenusResources() {
	// Dispose all menus scenes
	this.disposeMenuScenes();
	// Dispse all menu resources
	this.mMenuResourceManger.disposeAllResources();
	// Dispose all audio resources
	this.mMFXResourceManager.disposeAllResources();

	// Calls garbage collector
	System.gc();
    }

    /**
     * Disposes the Menu Scenes
     */
    private void disposeMenuScenes() {
	// Make sure the scene is created
	if (this.mMainMenuScene != null)
	    // Dispose all resources of the scene
	    this.mMainMenuScene.disposeScene();

	if (this.mCharacterSelectionMenuScene != null)
	    this.mCharacterSelectionMenuScene.disposeScene();

	if (this.mLoadGameMenuScene != null)
	    this.mLoadGameMenuScene.disposeScene();

	if (this.mOptionMenuScene != null)
	    this.mOptionMenuScene.disposeScene();

	if (this.mHelpMenuScene != null)
	    this.mHelpMenuScene.disposeScene();

	this.mMainMenuScene = null;
	this.mCharacterSelectionMenuScene = null;
	this.mLoadGameMenuScene = null;
	this.mOptionMenuScene = null;
	this.mHelpMenuScene = null;
    }

    /**
     * Disposes all the resources of menus
     */
    private void disposeGameResources() {
	this.disposeGameScene();
	this.mGameResourceManger.disposeAllResources();
	this.mMFXResourceManager.disposeAllResources();
	System.gc();
    }

    /**
     * Disposes a Game Scene.
     */
    private void disposeGameScene() {
	if (this.mGameScene != null)
	    this.mGameScene.disposeScene();

	this.mGameScene = null;
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}
