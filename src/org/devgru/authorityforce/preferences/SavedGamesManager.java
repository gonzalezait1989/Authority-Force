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

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.devgru.authorityforce.constant.MainParameters;
import org.devgru.authorityforce.manager.GameManager;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SavedGamesManager.java
 * 
 * Manages to read the application saved games
 * 
 * @version 1.0 March 7, 2013
 * @author Aitor Gonzalez Martinez
 * @author Xavier Martinez de Francisco
 * @author Toni Trigo Diaz
 */
public class SavedGamesManager extends ASharedPreferencesManager {

    // ===========================================================
    // Constants
    // ===========================================================

    /* An Instance of the class */
    private static final SavedGamesManager INSTANCE = new SavedGamesManager();

    /* String with the name of the Shared Preference */
    private static final String PREFERENCES_FILE_NAME = "LoadGames";
    /* String field that is a property */
    private static final String NOT_FOUND = "NOTFOUND";

    // ===========================================================
    // Fields
    // ===========================================================

    /* The loaded games */
    private HashMap<String, String> mLoadedGames;

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
    public static SavedGamesManager getInstance() {
	return INSTANCE;
    }

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    /**
     * Read all data from Shared Preferences
     */
    @Override
    public void loadAllDataFromSharedPreferences() {
	SharedPreferences sharedPreferencesFile = MainParameters.activity
		.getSharedPreferences(PREFERENCES_FILE_NAME,
			Context.MODE_PRIVATE);

	this.mLoadedGames = (HashMap<String, String>) sharedPreferencesFile
		.getAll();
    }

    /**
     * Write all data to Shared Preferences
     */
    @Override
    public void saveAllDataToSharedPreferences() {
    }

    /**
     * Read data comes as a parameter from Shared Preferences
     */
    @Override
    public String loadDataFromSharedPreferences(String pKey) {
	SharedPreferences sharedPreferencesFile = MainParameters.activity
		.getSharedPreferences(PREFERENCES_FILE_NAME,
			Context.MODE_PRIVATE);

	return sharedPreferencesFile.getString(pKey, NOT_FOUND);
    }

    /**
     * Wirte data comes as a parameter to Shared Preferences
     */
    @Override
    public void saveDataToSharedPreferences(final String pKey,
	    final String pNewvalue) {
	SharedPreferences sharedPreferencesFile = MainParameters.activity
		.getSharedPreferences(PREFERENCES_FILE_NAME,
			Context.MODE_PRIVATE);

	SharedPreferences.Editor editor = sharedPreferencesFile.edit();
	editor.putString(pKey, pNewvalue);
	editor.commit();
    }

    // ===========================================================
    // Methods
    // ===========================================================

    /**
     * Remove the save arrive as parameter
     * 
     * @param pKey
     *            the key of the item to be deleted
     */
    public void removeDataFromSharedPreferences(final String pKey) {
	SharedPreferences sharedPreferencesFile = MainParameters.activity
		.getSharedPreferences(PREFERENCES_FILE_NAME,
			Context.MODE_PRIVATE);

	SharedPreferences.Editor editor = sharedPreferencesFile.edit();
	editor.remove(pKey);
	editor.commit();
    }

    /**
     * Elmina all saved games
     */
    public void removeAllDataFromSharedPreferences() {
	SharedPreferences sharedPreferencesFile = MainParameters.activity
		.getSharedPreferences(PREFERENCES_FILE_NAME,
			Context.MODE_PRIVATE);

	SharedPreferences.Editor editor = sharedPreferencesFile.edit();
	editor.clear();
	editor.commit();
    }

    /**
     * Get last 5 game saves
     * 
     * @return a list with the guarded items
     */
    public SortedMap<String, String> getLastFive() {
	// Creates a comparator to sort the treeMap
	Comparator<String> comparator = new Comparator<String>() {

	    @Override
	    public int compare(String lhs, String rhs) {
		Long lhrsLong = Long.parseLong(lhs);
		Long rhsLong = Long.parseLong(rhs);
		return lhrsLong.compareTo(rhsLong);
	    }

	};
	// Creates a new TreeMap to store all the results sorted
	TreeMap<String, String> sortedLoadGames = new TreeMap<String, String>(
		comparator);
	sortedLoadGames.putAll(this.mLoadedGames);

	// Gets the first five values of the Map
	TreeMap<String, String> target = new TreeMap<String, String>();
	int count = 0;
	for (Map.Entry<String, String> entry : sortedLoadGames.entrySet()) {
	    if (count >= 5)
		break;
	    target.put(entry.getKey(), entry.getValue());
	    count++;
	}

	// Returns them
	return target;
    }

    /**
     * When it finishes a game guards the game
     * 
     * @param pNextMap
     */
    public void saveData(final String pNextMap) {
	String key = String.valueOf(System.currentTimeMillis());

	GameManager gameManager = GameManager.getInstance();

	// Constructs the line of preferences
	String value = pNextMap + "," + gameManager.getPlayerSelected() + ","
		+ gameManager.getDifficulty() + "," + gameManager.getmScore();

	this.saveDataToSharedPreferences(key, value);
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}
