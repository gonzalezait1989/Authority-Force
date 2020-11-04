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

package org.devgru.authorityforce.preferences;

import org.devgru.authorityforce.constant.MainParameters;
import org.devgru.authorityforce.constant.MenuConstantsUtility;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * PreferencesManager.java
 * 
 * Manages to read the application options
 * 
 * @version 1.0 March 7, 2013
 * @author Aitor Gonzalez Martinez
 * @author Xavier Martinez de Francisco
 * @author Toni Trigo Diaz
 */
public class PreferencesManager extends ASharedPreferencesManager {

    // ===========================================================
    // Constants
    // ===========================================================

    /* An Instance of the class */
    private static final PreferencesManager INSTANCE = new PreferencesManager();

    /* The name of the file preferences */
    private static final String PREFERENCES_FILE_NAME = "GamePreferences";

    // ===========================================================
    // Fields
    // ===========================================================

    /* Constants to retrieve difficulty preference */
    private final String mDifficultyPreference = "DifficultyLevel";
    /* Constants to retrieve handed preference */
    private final String mHandedPreference = "LeftHandedSelected";
    /* Constants to retrieve main sound preference */
    private final String mMainSoundPreference = "MainSoundEnabled";
    /* Constants to retrieve music sound preference */
    private final String mMusicSoundPreference = "MusicSoundLevel";
    /* Constants to retrieve fx sound preference */
    private final String mFxSoundPreference = "FXSoundLevel";
    /* Constants to retrieve fps monitor preference */
    private final String mFpsMonitorPreference = "FPSMonitorEnabled";
    /* Constants to retrieve cpu monitor preference */
    private final String mCpuMonitorPreference = "CPUMonitorEnabled";
    /* Constants to retrieve plane mode preference */
    private final String mPlaneModePreference = "PlaneModeEnabled";
    /* Constants to retrieve brightness preference */
    private final String mBrightnessPreference = "BrightnessLevel";

    /* Determine the state of the difficulty of the application */
    private String mDifficultyLevel;
    /* Determine the state of the handed of the application */
    private String mHandedSelected;
    /* Determine the state of the main sound of the application */
    private String mIsMainSoundEnabled;
    /* Determine the state of the music sound of the application */
    private String mIsMusicSoundLevel;
    /* Determine the state of the fx sound of the application */
    private String mIsFXSoundLevel;
    /* Determine the state of the fps monitor of the application */
    private String mIsFPSMonitorEnabled;
    /* Determine the state of the cpu monitor of the application */
    private String mIsCPUMonitorEnabled;
    /* Determine the state of the plane mode of the application */
    private String mIsPlaneModeEnabled;
    /* Determine the state of the brightness of the application */
    private String mIsBrightnessLevel;

    // ===========================================================
    // Constructors
    // ===========================================================

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    /**
     * Singleton Pattern
     * 
     * @return a unique instance of this class
     */
    public static PreferencesManager getInstance() {
	return INSTANCE;
    }

    public String getDifficultyLevel() {
	return this.mDifficultyLevel;
    }

    public void setDifficultyLevel(String pDifficultyLevel) {
	this.mDifficultyLevel = pDifficultyLevel;
    }

    public String getHandedSelected() {
	return this.mHandedSelected;
    }

    public void setHandedSelected(String pHandedSelected) {
	this.mHandedSelected = pHandedSelected;
    }

    public String getMainSoundEnabled() {
	return this.mIsMainSoundEnabled;
    }

    public void setMainSoundEnabled(String pIsMainSoundEnabled) {
	this.mIsMainSoundEnabled = pIsMainSoundEnabled;
    }

    public String getMusicSoundLevel() {
	return this.mIsMusicSoundLevel;
    }

    public void setMusicSoundLevel(String pIsMusicSoundLevel) {
	this.mIsMusicSoundLevel = pIsMusicSoundLevel;
    }

    public String getFXSoundLevel() {
	return this.mIsFXSoundLevel;
    }

    public void setFXSoundLevel(String pIsFXSoundLevel) {
	this.mIsFXSoundLevel = pIsFXSoundLevel;
    }

    public String getFPSMonitorEnabled() {
	return this.mIsFPSMonitorEnabled;
    }

    public void setFPSMonitorEnabled(String pIsFPSMonitorEnabled) {
	this.mIsFPSMonitorEnabled = pIsFPSMonitorEnabled;
    }

    public String getCPUMonitorEnabled() {
	return this.mIsCPUMonitorEnabled;
    }

    public void setCPUMonitorEnabled(String pIsCPUMonitorEnabled) {
	this.mIsCPUMonitorEnabled = pIsCPUMonitorEnabled;
    }

    public String getPlaneModeEnabled() {
	return this.mIsPlaneModeEnabled;
    }

    public void setPlaneModeEnabled(String pIsPlaneModeEnabled) {
	this.mIsPlaneModeEnabled = pIsPlaneModeEnabled;
    }

    public String getBrightnessLevel() {
	return this.mIsBrightnessLevel;
    }

    public void setBrightnessLevel(String pIsBrightnessLevel) {
	this.mIsBrightnessLevel = pIsBrightnessLevel;
    }

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    /**
     * Read all data from Shared Preferences
     */
    @Override
    public void loadAllDataFromSharedPreferences() {
	// Obtain data from Shared Preferences
	SharedPreferences sharedPreferencesFile = MainParameters.activity
		.getSharedPreferences("GamePreferences", Context.MODE_PRIVATE);

	// Receives the preference and a default value if the preference does
	// not exist
	this.setDifficultyLevel(sharedPreferencesFile.getString(
		mDifficultyPreference,
		MenuConstantsUtility.OPTIONS_STATE_EASY_DIFFICULTY));

	this.setHandedSelected(sharedPreferencesFile.getString(
		mHandedPreference,
		MenuConstantsUtility.OPTIONS_STATE_RIGHT_HANDED));

	this.setMainSoundEnabled(sharedPreferencesFile.getString(
		mMainSoundPreference, MenuConstantsUtility.OPTIONS_STATE_ON));

	this.setMusicSoundLevel(sharedPreferencesFile.getString(
		mMusicSoundPreference,
		MenuConstantsUtility.OPTIONS_STATE_MEDDIUM_LEVEL));

	this.setFXSoundLevel(sharedPreferencesFile.getString(
		mFxSoundPreference,
		MenuConstantsUtility.OPTIONS_STATE_MEDDIUM_LEVEL));

	this.setFPSMonitorEnabled(sharedPreferencesFile.getString(
		mFpsMonitorPreference, MenuConstantsUtility.OPTIONS_STATE_OFF));

	this.setCPUMonitorEnabled(sharedPreferencesFile.getString(
		mCpuMonitorPreference, MenuConstantsUtility.OPTIONS_STATE_OFF));

	this.setPlaneModeEnabled(sharedPreferencesFile.getString(
		mPlaneModePreference, MenuConstantsUtility.OPTIONS_STATE_OFF));

	this.setBrightnessLevel(sharedPreferencesFile.getString(
		mBrightnessPreference,
		MenuConstantsUtility.OPTIONS_STATE_MEDDIUM_LEVEL));
    }

    /**
     * Write all data to Shared Preferences
     */
    @Override
    public void saveAllDataToSharedPreferences() {
	SharedPreferences sharedPreferencesFile = MainParameters.activity
		.getSharedPreferences(PREFERENCES_FILE_NAME,
			Context.MODE_PRIVATE);

	// To write in Shared Preferences
	SharedPreferences.Editor editor = sharedPreferencesFile.edit();

	editor.putString(mDifficultyPreference, this.mDifficultyLevel);
	editor.putString(mHandedPreference, this.mHandedSelected);
	editor.putString(mMainSoundPreference, this.mIsMainSoundEnabled);
	editor.putString(mMusicSoundPreference, this.mIsMusicSoundLevel);
	editor.putString(mFxSoundPreference, this.mIsFXSoundLevel);
	editor.putString(mFpsMonitorPreference, this.mIsFPSMonitorEnabled);
	editor.putString(mCpuMonitorPreference, this.mIsCPUMonitorEnabled);
	editor.putString(mPlaneModePreference, this.mIsPlaneModeEnabled);
	editor.putString(mBrightnessPreference, this.mIsBrightnessLevel);

	// Write file
	editor.commit();
    }

    /**
     * Read data comes as a parameter from Shared Preferences
     */
    @Override
    public String loadDataFromSharedPreferences(String pKey) {
	SharedPreferences sharedPreferencesFile = MainParameters.activity
		.getSharedPreferences(PREFERENCES_FILE_NAME,
			Context.MODE_PRIVATE);

	return sharedPreferencesFile.getString(pKey,
		MenuConstantsUtility.OPTIONS_STATE_OFF);
    }

    /**
     * Wirte data comes as a parameter to Shared Preferences
     */
    @Override
    public void saveDataToSharedPreferences(String pKey, String pValue) {
	SharedPreferences sharedPreferencesFile = MainParameters.activity
		.getSharedPreferences(PREFERENCES_FILE_NAME,
			Context.MODE_PRIVATE);

	SharedPreferences.Editor editor = sharedPreferencesFile.edit();

	editor.putString(pKey, pValue);

	editor.commit();
    }

    // ===========================================================
    // Methods
    // ===========================================================

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}
