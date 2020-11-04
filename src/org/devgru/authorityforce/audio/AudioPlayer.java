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

package org.devgru.authorityforce.audio;

import java.util.HashMap;

import org.andengine.audio.music.Music;
import org.devgru.authorityforce.constant.MenuConstantsUtility;
import org.devgru.authorityforce.manager.audio.MFXResourceManager;
import org.devgru.authorityforce.preferences.PreferencesManager;

import android.media.SoundPool;

/**
 * AudioPlayer.java
 * 
 * Play all sound of the aplication
 * 
 * @version 1.0 Apr 19, 2013
 * @author Aitor Gonzalez Martinez
 * @author Xavier Martinez de Francisco
 * @author Toni Trigo Diaz
 */
public class AudioPlayer {

    // ===========================================================
    // Constants
    // ===========================================================

    /* A instance of AudioPlayer */
    private static final AudioPlayer INSTANCE = new AudioPlayer();

    // ===========================================================
    // Fields
    // ===========================================================

    /* A instance of PreferencesManager gets the volume options */
    private PreferencesManager mPreferencesManager = PreferencesManager
	    .getInstance();

    /* A Music object to play the music */
    private Music mMusic;
    /* The volume of the music */
    private float mVolumeMusic;
    /* The volume of the sounds effects */
    private float mVolumeFX;
    /* The Sound Pool mapping of sounds */
    private HashMap<String, Integer> mSoundPoolMapping;
    /* The SoundPool */
    private SoundPool mSoundPool;

    // ===========================================================
    // Constructors
    // ===========================================================

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    /**
     * Gets a instance of AudioPlayer.
     * 
     * @return a instance of AudioPlayer.
     */
    public static AudioPlayer getInstance() {
	return INSTANCE;
    }

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    // ===========================================================
    // Methods
    // ===========================================================

    /**
     * Gets the saved state of the music in the options and if this ON the play
     */
    public void setVolumeMaster() {
	// Gets the saved state of the music
	String mainSoundEnabled = this.mPreferencesManager
		.getMainSoundEnabled();

	// State is ON
	if (mainSoundEnabled.equals(MenuConstantsUtility.OPTIONS_STATE_ON)) {
	    this.startMusic();

	    // State is OFF
	} else {
	    this.stopMusic();
	}
    }

    /**
     * Prepare the music to sound
     */
    private void startMusic() {
	// For safety
	if (this.mMusic == null) {
	    // Gets the music of the Dao
	    this.mMusic = MFXResourceManager.getInstance().getMusic();

	    this.mMusic.setLooping(true);

	    // Volume applies
	    this.setVolumeMusic();
	}

	// Now play the music
	this.playMusic();
    }

    /**
     * Plays the music.
     */
    private void playMusic() {
	// This checks that not playing
	if (!this.mMusic.isPlaying()) {
	    // Restarts the song
	    this.mMusic.seekTo(0);
	    this.mMusic.play();
	}
    }

    /**
     * Plays a determinate Sound Effect.
     * 
     * @param pSoundID
     *            the ID of the Sound Effect.
     */
    public void playSound(final String pSoundID) {
	this.mSoundPool.play(this.mSoundPoolMapping.get(pSoundID),
		this.mVolumeFX, this.mVolumeFX, 1, 0, 1.0f);
    }

    /**
     * Changes the volume of the music
     */
    public void setVolumeMusic() {
	// Gets the new volume of the music
	String musicSoundLevel = this.mPreferencesManager.getMusicSoundLevel();

	// Check what is the new volume
	if (musicSoundLevel
		.equals(MenuConstantsUtility.OPTIONS_STATE_LOW_LEVEL)) {
	    // Saves the volume
	    this.mVolumeMusic = 0.25f;

	} else if (musicSoundLevel
		.equals(MenuConstantsUtility.OPTIONS_STATE_MEDDIUM_LEVEL)) {
	    this.mVolumeMusic = 0.5f;

	} else {
	    this.mVolumeMusic = 1;
	}

	// If the music is playing is applied to the new volume
	if (this.mMusic != null) {
	    this.mMusic.setVolume(this.mVolumeMusic);
	}
    }

    /**
     * Changes the volume of the sounds
     */
    public void setVolumeFX() {
	// Gets the new volume of the sound
	String FXSoundLevel = this.mPreferencesManager.getFXSoundLevel();

	// Check what is the new volume
	if (FXSoundLevel.equals(MenuConstantsUtility.OPTIONS_STATE_LOW_LEVEL)) {
	    // Saves the volume
	    this.mVolumeFX = 0.25f;

	} else if (FXSoundLevel
		.equals(MenuConstantsUtility.OPTIONS_STATE_MEDDIUM_LEVEL)) {
	    this.mVolumeFX = 0.5f;

	} else {
	    this.mVolumeFX = 1;
	}
    }

    public void prepareAudioFXPool() {
	this.setVolumeFX();
	this.mSoundPoolMapping = MFXResourceManager.getInstance()
		.getSoundPoolMapping();
	this.mSoundPool = MFXResourceManager.getInstance().getGameSound();
    }

    /**
     * Stop the music
     */
    public void stopMusic() {
	// Verifies that the music is playing
	if (this.mMusic != null && this.mMusic.isPlaying()) {
	    this.mMusic.pause();
	}
    }

    /**
     * Dispose the resource of music
     */
    public void disposeAudio() {
	this.mMusic = null;
	this.mSoundPoolMapping = null;
	this.mSoundPool = null;
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}
