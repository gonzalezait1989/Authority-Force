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

/**
 * ASharedPreferencesManager.java
 * 
 * Abstarct class for all Preferences Manager
 * 
 * @version 1.0 March 7, 2013
 * @author Aitor Gonzalez Martinez
 * @author Xavier Martinez de Francisco
 * @author Toni Trigo Diaz
 */
public abstract class ASharedPreferencesManager {

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

    /**
     * Read all data from Shared Preferences
     */
    public abstract void loadAllDataFromSharedPreferences();

    /**
     * Write all data to Shared Preferences
     */
    public abstract void saveAllDataToSharedPreferences();

    /**
     * Read data comes as a parameter from Shared Preferences
     */
    public abstract String loadDataFromSharedPreferences(final String pKey);

    /**
     * Wirte data comes as a parameter to Shared Preferences
     */
    public abstract void saveDataToSharedPreferences(final String pKey,
	    final String pNewvalue);

    // ===========================================================
    // Methods
    // ===========================================================

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}
