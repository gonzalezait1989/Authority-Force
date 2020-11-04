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

package org.devgru.authorityforce.activity;

import java.io.IOException;

import org.andengine.engine.Engine;
import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.BaseGameActivity;
import org.devgru.authorityforce.audio.AudioPlayer;
import org.devgru.authorityforce.constant.GeneralConstantsUtility;
import org.devgru.authorityforce.constant.MainParameters;
import org.devgru.authorityforce.controller.SceneController;
import org.devgru.authorityforce.manager.audio.MFXResourceManager;
import org.devgru.authorityforce.manager.scene.MenuResourceManager;
import org.devgru.authorityforce.preferences.PreferencesManager;
import org.devgru.authorityforce.settings.SettingsManager;

import android.view.KeyEvent;

/**
 * AuthorityForceActivity.java
 * 
 * This is the game main activity.
 * 
 * @version 1.0 Apr 19, 2013
 * @author Aitor Gonzalez Martinez
 * @author Xavier Martinez de Francisco
 * @author Toni Trigo Diaz
 */
public class AuthorityForceActivity extends BaseGameActivity {

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    /* The game camera */
    private BoundCamera mBoundCamera;
    /* boolean to check if the activity is active or not */
    private static boolean mIsActive;

    // ===========================================================
    // Constructors
    // ===========================================================

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    /**
     * Determines if the Authority Force Activity is running.
     * 
     * @return true if it's running, false otherwise.
     */
    public static boolean isActive() {
	return AuthorityForceActivity.mIsActive;
    }

    /**
     * Gets the Camera of the game.
     * 
     * @return a BoundCamera.
     */
    public BoundCamera getBoundCamera() {
	return this.mBoundCamera;
    }

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    @Override
    public EngineOptions onCreateEngineOptions() {

	// Creates the game camera.
	this.mBoundCamera = new BoundCamera(0, 0,
		GeneralConstantsUtility.SCREEN_WIDTH_RESOLUTION,
		GeneralConstantsUtility.SCREEN_HEIGHT_RESOLUTION);

	// Creates the options for the engine.
	final EngineOptions engineOptions = new EngineOptions(true,
		ScreenOrientation.LANDSCAPE_FIXED, new FillResolutionPolicy(),
		this.mBoundCamera);

	// Enables multitouch.
	engineOptions.getTouchOptions().setNeedsMultiTouch(true);

	// Enables dithering on image rendering.
	engineOptions.getRenderOptions().setDithering(true);

	// Enables music and sound.
	engineOptions.getAudioOptions().setNeedsMusic(true).setNeedsSound(true);

	// Forces the device to not lock the screen while the game is running.
	engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);

	return engineOptions;
    }

    @Override
    public Engine onCreateEngine(EngineOptions pEngineOptions) {
	// Creates the engine with a limited max fps.
	return new LimitedFPSEngine(pEngineOptions,
		GeneralConstantsUtility.GAME_MAX_ALLOWED_FPS);
    }

    @Override
    public void onCreateResources(
	    OnCreateResourcesCallback pOnCreateResourcesCallback)
	    throws IOException {

	// Assign references for main objects in the activity to MainParameters
	// fields.
	MainParameters.setParams(this);

	// Set the settings from the shared preferences.
	this.setDefaultSettings();

	// Initializes menu resources.
	this.startMenuManager();

	// Loads audio resources.
	this.startMusicManager();

	AuthorityForceActivity.mIsActive = true;

	pOnCreateResourcesCallback.onCreateResourcesFinished();
    }

    @Override
    public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
	    throws IOException {
	// Creates the main menu scene.
	SceneController.getInstance().createMainMenuScene(
		pOnCreateSceneCallback);
    }

    @Override
    public void onPopulateScene(Scene pScene,
	    OnPopulateSceneCallback pOnPopulateSceneCallback)
	    throws IOException {
	MainParameters.engine.registerUpdateHandler(new TimerHandler(2f,
		new ITimerCallback() {
		    public void onTimePassed(final TimerHandler pTimerHandler) {
			MainParameters.engine
				.unregisterUpdateHandler(pTimerHandler);
		    }
		}));

	pOnPopulateSceneCallback.onPopulateSceneFinished();
    }

    @Override
    public boolean onKeyDown(int pKeyCode, KeyEvent pKevent) {
	// Calls the event launched on the back key pressed for the current
	// scene.
	if (pKeyCode == KeyEvent.KEYCODE_BACK) {
	    SceneController.getInstance().getCurrentScene().onBackKeyPressed();
	}
	return false;
    }

    @Override
    protected void onDestroy() {
	super.onDestroy();
	if (this.isGameLoaded()) {
	    System.exit(0);
	}
    }

    @Override
    protected void onResume() {
	super.onResume();
	if (AuthorityForceActivity.mIsActive) {
	    AudioPlayer.getInstance().setVolumeMaster();
	}
    }

    @Override
    protected void onPause() {
	super.onPause();
	AudioPlayer.getInstance().stopMusic();
    }

    // ===========================================================
    // Methods
    // ===========================================================

    /**
     * Loads the settings from the shared preferences file.
     */
    private void setDefaultSettings() {
	PreferencesManager.getInstance().loadAllDataFromSharedPreferences();
	SettingsManager.changeBrightness();
	SettingsManager.changePlaneMode();
    }

    /**
     * Loads all resources needed by the menus.
     */
    private void startMenuManager() {
	MenuResourceManager.getInstance().loadAllResources();
    }

    /**
     * Loads the needed audio resources and starts playing the menu music.
     */
    private void startMusicManager() {
	MFXResourceManager mfxManager = MFXResourceManager.getInstance();
	mfxManager.prepareManager();
	mfxManager.loadMenuAudio();
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}
