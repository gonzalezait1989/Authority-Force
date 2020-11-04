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

package org.devgru.authorityforce.manager.audio;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.SoundFactory;
import org.devgru.authorityforce.audio.AudioPlayer;
import org.devgru.authorityforce.constant.AssetsConstantsUtility;
import org.devgru.authorityforce.constant.MainParameters;
import org.devgru.authorityforce.dao.MFXDAO;
import org.devgru.authorityforce.manager.GameManager;

import android.media.AudioManager;
import android.media.SoundPool;

/**
 * MFXResourceManager.java
 * 
 * Manage sound load all application
 * 
 * @version 1.0 March 7, 2013
 * @author Aitor Gonzalez Martinez
 * @author Xavier Martinez de Francisco
 * @author Toni Trigo Diaz
 */

public class MFXResourceManager extends AAudioResourceManager {

    // ===========================================================
    // Constants
    // ===========================================================

    /* Instance of this class */
    private static final MFXResourceManager INSTANCE = new MFXResourceManager();

    // ===========================================================
    // Fields
    // ===========================================================

    // ===========================================================
    // Constructors
    // ===========================================================

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    /**
     * Singleton Pattern. Gets a instance of AudioPlayer.
     * 
     * @return a instance of AudioPlayer.
     */
    public static MFXResourceManager getInstance() {
	return INSTANCE;
    }

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    /**
     * Prepare the music manager to run. They perform all the previous operation
     * to ensure correct operation
     */
    @Override
    public void prepareManager() {
	// Gets all resources to work
	this.mGameManager = GameManager.getInstance();
	this.mMFXDAO = MFXDAO.getInstance();
    }

    /**
     * Load the music using by the menus
     */
    @Override
    public void loadMenuAudio() {
	// Sets the directory from which the audio load
	MusicFactory.setAssetBasePath(AssetsConstantsUtility.MEDIA_DIRECTORY);
	// Load the main menu music
	try {
	    this.mMusic = MusicFactory.createMusicFromAsset(
		    MainParameters.engine.getMusicManager(),
		    MainParameters.activity, AssetsConstantsUtility.MUSIC_MENU);
	} catch (final IOException e) {
	    e.printStackTrace();
	}
    }

    /**
     * Load the music using by the game
     */
    @Override
    public void loadGameAudio() {
	MusicFactory.setAssetBasePath(AssetsConstantsUtility.MEDIA_DIRECTORY);
	try {
	    this.mMusic = MusicFactory.createMusicFromAsset(
		    MainParameters.engine.getMusicManager(),
		    MainParameters.activity, this.mMFXDAO.getMusicByLevel());
	} catch (final IOException e) {
	    e.printStackTrace();
	}
    }

    /**
     * Dispose all resources of the manager
     */
    @Override
    public void loadGameSoundsFX() {
	SoundFactory.setAssetBasePath(AssetsConstantsUtility.MEDIA_DIRECTORY);

	this.mMFXDAO.loadSounds();

	this.mSoundPoolMapping = new HashMap<String, Integer>();
	this.mSoundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
	HashMap<String, String> soundPaths = (HashMap<String, String>) this.mMFXDAO
		.getSoundPaths();

	Set<String> soundIDs = soundPaths.keySet();
	for (String key : soundIDs) {
	    String path = soundPaths.get(key);
	    try {
		Integer soundID = this.mSoundPool.load(
			MainParameters.activity.getAssets().openFd(
				AssetsConstantsUtility.MEDIA_DIRECTORY + path),
			1);
		this.mSoundPoolMapping.put(key, soundID);
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
    }

    /**
     * Dispose all resources of the manager
     */
    @Override
    public void disposeAllResources() {
	// First stop the music
	AudioPlayer.getInstance().stopMusic();
	// Dispose the musicS
	AudioPlayer.getInstance().disposeAudio();
	this.mMusic = null;

	// Dispose Sounds
	if (this.mSoundPoolMapping != null && this.mSoundPool != null) {
	    Set<String> soundsIDs = this.mSoundPoolMapping.keySet();
	    for (String soundID : soundsIDs) {
		int soundPoolID = this.mSoundPoolMapping.get(soundID);
		this.mSoundPool.unload(soundPoolID);
	    }
	    this.mSoundPool = null;
	    this.mSoundPoolMapping = null;
	}
    }

    // ===========================================================
    // Methods
    // ===========================================================

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}