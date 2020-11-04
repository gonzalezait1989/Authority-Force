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

package org.devgru.authorityforce.settings;

import org.devgru.authorityforce.constant.MainParameters;
import org.devgru.authorityforce.constant.MenuConstantsUtility;
import org.devgru.authorityforce.preferences.PreferencesManager;

import android.content.Intent;
import android.provider.Settings;
import android.view.WindowManager;

/**
 * SettingsManager.java
 * 
 * Controls the terminal settings
 * 
 * @version 1.0 Apr 19, 2013
 * @author Aitor Gonzalez Martinez
 * @author Xavier Martinez de Francisco
 * @author Toni Trigo Diaz
 */
public class SettingsManager {

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    // ===========================================================
    // Constructors
    // ===========================================================

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    // ===========================================================
    // Methods
    // ===========================================================

    /**
     * Switch the flight mode of the terminal
     */
    public static void changePlaneMode() {
	// Read the actual state of the plane mode
	String newState = PreferencesManager.getInstance()
		.getPlaneModeEnabled();

	final boolean planeModeIsEnabled;

	// Compare the new state
	if (newState.equals(MenuConstantsUtility.OPTIONS_STATE_ON)) {
	    planeModeIsEnabled = true;

	} else {
	    planeModeIsEnabled = false;
	}

	// Apply the new state
	Settings.System.putInt(MainParameters.activity.getContentResolver(),
		Settings.System.AIRPLANE_MODE_ON, planeModeIsEnabled ? 1 : 0);
	Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
	intent.putExtra("state", planeModeIsEnabled);
	MainParameters.activity.sendBroadcast(intent);
    }

    /**
     * 
     */
    public static void changeBrightness() {
	// Read the actual state of the brightness
	String newState = PreferencesManager.getInstance().getBrightnessLevel();

	final float brightnessLevel;
	// Compare the new state
	if (newState.equals(MenuConstantsUtility.OPTIONS_STATE_LOW_LEVEL)) {
	    brightnessLevel = 0.25f;

	} else if (newState
		.equals(MenuConstantsUtility.OPTIONS_STATE_MEDDIUM_LEVEL)) {
	    brightnessLevel = 0.5f;

	} else {
	    brightnessLevel = 1f;
	}

	// Apply the new state in other thread
	MainParameters.activity.runOnUiThread(new Runnable() {
	    @Override
	    public void run() {
		WindowManager.LayoutParams layout = MainParameters.activity
			.getWindow().getAttributes();
		layout.screenBrightness = brightnessLevel;
		MainParameters.activity.getWindow().setAttributes(layout);
	    }
	});
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}
