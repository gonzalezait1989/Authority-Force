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

import java.util.HashMap;

import org.andengine.audio.music.Music;
import org.devgru.authorityforce.dao.MFXDAO;
import org.devgru.authorityforce.manager.GameManager;
import org.devgru.authorityforce.manager.IResourceManager;

import android.media.SoundPool;

/**
 * AAudioResourceManager.java
 * 
 * Abstract class for audio managers
 * 
 * @version 1.0 March 7, 2013
 * @author Aitor Gonzalez Martinez
 * @author Xavier Martinez de Francisco
 * @author Toni Trigo Diaz
 */

public abstract class AAudioResourceManager implements IResourceManager {

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    // A instance of MFXDAO
    protected MFXDAO mMFXDAO;
    // A instance of GameManager
    protected GameManager mGameManager;
    // To load the music that will be played
    protected Music mMusic;
    // To load the sound that will be played
    protected SoundPool mSoundPool;
    // Contains the path and id for all sound loaded
    protected HashMap<String, Integer> mSoundPoolMapping;

    // ===========================================================
    // Constructors
    // ===========================================================

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    public Music getMusic() {
	return this.mMusic;
    }

    public SoundPool getGameSound() {
	return this.mSoundPool;
    }

    public HashMap<String, Integer> getSoundPoolMapping() {
	return this.mSoundPoolMapping;
    }

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    /**
     * Prepare the music manager to run. They perform all the previous operation
     * to ensure correct operation
     */
    public abstract void prepareManager();

    /**
     * Load the music using by the menus
     */
    public abstract void loadMenuAudio();

    /**
     * Load the music using by the game
     */
    public abstract void loadGameAudio();

    /**
     * Load the sounds using by the game
     */
    public abstract void loadGameSoundsFX();

    // ===========================================================
    // Methods
    // ===========================================================

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}
