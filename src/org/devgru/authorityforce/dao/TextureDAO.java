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

import org.devgru.authorityforce.constant.JsonFieldsConstantsUtility;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * TextureDAO.java
 * 
 * This class returns distinct data of Map.
 * 
 * @version 1.0 Apr 25, 2013
 * @author Aitor Gonzalez Martinez
 * @author Xavier Martinez de Francisco
 * @author Toni Trigo Diaz
 */

public class TextureDAO extends AFileJSONDAO {

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    /* A Instance of TextureDAO. */
    private static TextureDAO INSTANCE = null;

    /* The root JSONObject of the map textures definitions file. */
    private JSONObject mMapTexturesJSONroot;

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
     * Returns a unique instance of MapDAO.
     * 
     * @return a instance of MapDAO.
     */
    public static TextureDAO getInstance() {
	if (INSTANCE == null) {
	    INSTANCE = new TextureDAO();
	}
	return INSTANCE;
    }

    /**
     * Gets the name of textures from JSON file
     */
    public void readTexturesFromJSON() {
	this.mMapTexturesJSONroot = this.readJSONFile(MapFluxDAO.getInstance()
		.getMapData(JsonFieldsConstantsUtility.FLUX_TEXTURES));
    }

    /**
     * Gets the file name of the TMX file.
     * 
     * @return the file name of the TMX file.
     */
    public String getTMXFileName() {
	try {
	    return this.mMapTexturesJSONroot
		    .getString(JsonFieldsConstantsUtility.TEXTURE_TMX_ID);

	} catch (JSONException e) {
	    e.printStackTrace();
	    return null;
	}
    }

    /**
     * Returns a JSONObject with all the data needed to retrieve all textures
     * from one type of Entity.
     * 
     * @param pType
     *            the type of Entity.
     * @return a JSONObject with all the data needed to retrieve all textures
     *         from one type of Entity.
     */
    public JSONObject getTextureByType(final String pType) {
	JSONArray texture;

	try {
	    texture = this.mMapTexturesJSONroot.getJSONArray(pType);
	    return texture.getJSONObject(0);

	} catch (JSONException e) {
	    e.printStackTrace();
	}

	return null;
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}
