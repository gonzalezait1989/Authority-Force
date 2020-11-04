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

package org.devgru.authorityforce.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import org.devgru.authorityforce.constant.JsonFieldsConstantsUtility;
import org.devgru.authorityforce.constant.MainParameters;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * JSONDAO.java
 * 
 * Parses the JSON file and returns its root element.
 * 
 * @version 1.0 Apr 23, 2013
 * @author Aitor Gonzalez Martinez
 * @author Xavier Martinez de Francisco
 * @author Toni Trigo Diaz
 */

public class AFileJSONDAO {

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
     * Reads the JSON file and return a String.
     * 
     * @param pJSONFile
     *            the name of the JSON file to load.
     * 
     * @return a String with the content of the JSON file.
     */
    protected JSONObject readJSONFile(final String pJSONFile) {
	String JSONFileContent = "";
	JSONObject root = null;
	InputStream inputStream;

	try {
	    // Opens a InputScreamReader to a determinate JSON file.
	    inputStream = MainParameters.activity.getAssets().open(pJSONFile);

	    // Reads the content of the JSON file
	    Scanner sc = new Scanner(inputStream);
	    while (sc.hasNextLine()) {
		JSONFileContent += sc.nextLine().trim().replace("\n", "")
			.replace(" ", "");
	    }

	    // Creates a new JSON document and gets it's root element.
	    JSONObject document = new JSONObject(JSONFileContent);
	    root = document.getJSONObject(JsonFieldsConstantsUtility.JSON_ROOT);

	} catch (IOException e) {
	    e.printStackTrace();

	} catch (JSONException e) {
	    e.printStackTrace();
	}

	return root;
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}
