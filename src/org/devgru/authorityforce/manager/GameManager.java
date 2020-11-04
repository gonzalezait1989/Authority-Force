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

package org.devgru.authorityforce.manager;

import org.devgru.authorityforce.constant.MenuConstantsUtility;
import org.devgru.authorityforce.preferences.PreferencesManager;

/**
 * SplashScene.java
 * 
 * 
 * @version 1.0 Apr 19, 2013
 * @author Aitor Gonzalez Martinez
 * @author Xavier Martinez de Francisco
 * @author Toni Trigo Diaz
 */
public class GameManager {

    // ===========================================================
    // Constants
    // ===========================================================

    /* Multiplier for easy difficulty */
    private static final float EASY_DIFFICULTY_MULTIPLIER = 0.33f;
    /* Multiplier for medium difficulty */
    private static final float MEDIUM_DIFFICULTY_MULTIPLIER = 0.66f;
    /* Multiplier for hard difficulty */
    private static final float HARD_DIFFICULTY_MULTIPLIER = 1;

    // ===========================================================
    // Fields
    // ===========================================================

    /* A instance of AudioPlayer */
    private static GameManager INSTANCE;
    /* Map name of the current game */
    private String mCurrentMapName;
    /* Difficulty of the current game */
    private String mDifficulty;
    /* Difficulty multiplier of the current game */
    private float mDifficultyMultiplier;
    /* Name of the player in current game */
    private String mPlayerSelected;
    /* Score of the player in current game */
    private int mGameScore;

    // ===========================================================
    // Constructors
    // ===========================================================

    /**
     * Constructor by default
     */
    private GameManager() {
	// Default values
	this.mCurrentMapName = "map_tutorial";
	this.mDifficulty = PreferencesManager.getInstance()
		.getDifficultyLevel();
	this.mPlayerSelected = "grey_moses";
	this.mGameScore = 0;
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    /**
     * Singleton Pattern. Gets a instance of AudioPlayer.
     * 
     * @return a instance of AudioPlayer.
     */
    public static GameManager getInstance() {
	if (INSTANCE == null) {
	    INSTANCE = new GameManager();
	}
	return INSTANCE;
    }

    public String getLevelMap() {
	return this.mCurrentMapName;
    }

    public void setLevelMap(String pLevelMap) {
	this.mCurrentMapName = pLevelMap;
    }

    public String getDifficulty() {
	return this.mDifficulty;
    }

    public void setDifficulty(String pDifficulty) {
	this.mDifficulty = pDifficulty;

	// Obtains the multiplier for this difficulty
	this.applyDifficultyMultiplier(pDifficulty);
    }

    public float getDifficultyMultiplier() {
	return this.mDifficultyMultiplier;
    }

    public float setDifficultyMultiplier(float pDifficultyMultiplier) {
	return this.mDifficultyMultiplier = pDifficultyMultiplier;
    }

    public String getPlayerSelected() {
	return this.mPlayerSelected;
    }

    public void setPlayerSelected(String pPlayerSelected) {
	this.mPlayerSelected = pPlayerSelected;
    }

    public int getmScore() {
	return this.mGameScore;
    }

    public void setmScore(int pScore) {
	this.mGameScore = pScore;
    }

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    // ===========================================================
    // Methods
    // ===========================================================

    /**
     * Sets the default values
     */
    public void resetDefaults() {
	this.mCurrentMapName = "map_tutorial";
	this.mDifficulty = PreferencesManager.getInstance()
		.getDifficultyLevel();
	this.mPlayerSelected = "grey_moses";
	this.mGameScore = 0;
    }

    /**
     * Obtain the multiplier difficulty for parameter
     * 
     * @param pDifficulty
     *            the new difficulty
     */
    public void applyDifficultyMultiplier(String pDifficulty) {
	if (pDifficulty
		.equals(MenuConstantsUtility.OPTIONS_STATE_EASY_DIFFICULTY)) {
	    this.mDifficultyMultiplier = GameManager.EASY_DIFFICULTY_MULTIPLIER;

	} else if (pDifficulty
		.equals(MenuConstantsUtility.OPTIONS_STATE_MEDIUM_DIFFICULTY)) {
	    this.mDifficultyMultiplier = GameManager.MEDIUM_DIFFICULTY_MULTIPLIER;

	} else {
	    this.mDifficultyMultiplier = GameManager.HARD_DIFFICULTY_MULTIPLIER;
	}
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}
