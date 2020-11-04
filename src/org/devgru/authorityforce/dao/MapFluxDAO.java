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

import java.util.HashMap;
import java.util.Map;

import org.devgru.authorityforce.constant.AssetsConstantsUtility;
import org.devgru.authorityforce.constant.JsonFieldsConstantsUtility;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * MapFluxDAO.java
 * 
 * This class returns data refering to game flux.
 * 
 * @version 1.0 May 23, 2013
 * @author Aitor Gonzalez Martinez
 * @author Xavier Martinez de Francisco
 * @author Toni Trigo Diaz
 */
public class MapFluxDAO extends AFileJSONDAO {

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    /* A instance of MapFluxDAO */
    private static MapFluxDAO INSTANCE = null;

    /*
     * Contains the information of each map. Key is the map id, and value is a
     * JSON object containing all the map data.
     */
    private Map<String, JSONObject> mMapData;
    private JSONObject mActualMapData;

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
     * Gets the data of a determinate Map by a given ID
     * 
     * @param pId
     *            the ID of the Map
     */
    public void loadMapData(String pId) {
	this.mActualMapData = this.mMapData.get(pId);
    }

    /**
     * Returns a unique instance of MapFluxDAO.
     * 
     * @return a instance of MapFluxDAO.
     */
    public static MapFluxDAO getInstance() {
	if (INSTANCE == null) {
	    INSTANCE = new MapFluxDAO();
	}
	return INSTANCE;
    }

    /**
     * Loads the data of a the maps flux.
     */
    public void loadMapsData() {
	JSONObject mapsData = this
		.readJSONFile(AssetsConstantsUtility.MAP_FLUX_FILE);
	this.mMapData = new HashMap<String, JSONObject>();

	try {

	    JSONArray maps = mapsData
		    .getJSONArray(JsonFieldsConstantsUtility.JSON_ROOT);

	    for (int i = 0; i < maps.length(); i++) {
		JSONObject value = maps.getJSONObject(i);
		String key = value
			.getString(JsonFieldsConstantsUtility.FLUX_MAP_NAME);
		this.mMapData.put(key, value);
	    }

	} catch (JSONException e) {
	    e.printStackTrace();
	}
    }

    /**
     * Gets a data field of a map corresponding to the given property name.
     * 
     * @param pMapProperty
     *            the map property.
     * @return a String with the field data.
     */
    public String getMapData(String pMapProperty) {
	try {
	    return this.mActualMapData.getString(pMapProperty);

	} catch (JSONException e) {
	    e.printStackTrace();
	    return null;
	}
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}
