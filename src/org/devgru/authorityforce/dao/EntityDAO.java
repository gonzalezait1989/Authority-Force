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
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.devgru.authorityforce.constant.GeneralConstantsUtility.MAP_ENTITY_TYPE;
import org.devgru.authorityforce.constant.JsonFieldsConstantsUtility;
import org.devgru.authorityforce.util.EntityMessenger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * EntityDAO.java
 * 
 * This class returns distinct data of Entities.
 * 
 * @version 1.0 Apr 25, 2013
 * @author Aitor Gonzalez Martinez
 * @author Xavier Martinez de Francisco
 * @author Toni Trigo Diaz
 */

public class EntityDAO extends AFileJSONDAO {

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    /* An instance of this class */
    private static EntityDAO INSTANCE = null;

    /* Fields for index for positions of the Entities on the map. */
    private JSONObject mEntitiesIndexMap;
    /* Fields for the ID's of distict type of Entities */
    private Map<String, JSONObject> mEntitiesData;
    /* Fields for the coordinates of distinct Entities */
    private List<EntityMessenger> mEntitiesPosition;
    /* Fields of the bullets */
    private JSONObject mBulletsData;
    /* Fields of the player */
    private JSONObject mPlayerData;

    // ===========================================================
    // Constructors
    // ===========================================================

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    /**
     * Returns a unique instance of EntityDAO.
     * 
     * @return a instance of EntityDAO.
     */
    public static EntityDAO getInstance() {
	if (INSTANCE == null) {
	    INSTANCE = new EntityDAO();
	}
	return INSTANCE;
    }

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    // ===========================================================
    // Methods
    // ===========================================================

    /**
     * Reads all the Bullets data.
     */
    public void readBulletsData() {
	this.mBulletsData = this.readJSONFile(MAP_ENTITY_TYPE.BULLET
		.getFilePath());
    }

    /**
     * Gets the data of a determinate Bullet.
     * 
     * @param pID
     *            the ID of the Bullet.
     * @return a JSON Object with all the data fields the Bullet corresponding
     *         to the ID.
     */
    public JSONObject getBulletData(final String pID) {
	try {
	    return this.mBulletsData.getJSONObject(pID);

	} catch (JSONException e) {
	    e.printStackTrace();
	    return null;
	}
    }

    /**
     * Reads all the data corresponding to the index of positions of the
     * Entities on the map.
     */
    public void readIndexMapFile() {
	this.mEntitiesIndexMap = this.readJSONFile(MapFluxDAO.getInstance()
		.getMapData(
			JsonFieldsConstantsUtility.FLUX_ENTITIES_DEFINITIONS));
    }

    /**
     * Reads the Player data.
     */
    public void readPlayersData() {
	this.mPlayerData = this.readJSONFile(MAP_ENTITY_TYPE.PLAYER
		.getFilePath());
    }

    /**
     * Gets the data fields of a determinate Player corresponding to the ID.
     * 
     * @param pID
     *            the ID of the Player.
     * @return a JSON object with the data of the Player.f
     */
    public JSONObject getPlayerData(final String pID) {
	try {
	    return this.mPlayerData.getJSONObject(pID);

	} catch (JSONException e) {
	    e.printStackTrace();
	    return null;
	}
    }

    /**
     * Reads the file which contains the Entities that will be on a map with
     * it's positions.
     */
    public void readEntitiesFromJSON() {
	this.mEntitiesData = new HashMap<String, JSONObject>();
	this.loadEntitiesData(MAP_ENTITY_TYPE.ARMOR);
	this.loadEntitiesData(MAP_ENTITY_TYPE.BONUS_ITEM);
	this.loadEntitiesData(MAP_ENTITY_TYPE.ENEMY);
	this.loadEntitiesData(MAP_ENTITY_TYPE.WEAPON);
    }

    /**
     * Loads the data of positions of a determinate type of Entity.
     * 
     * @param entityType
     *            the type of the Entity.
     */
    private void loadEntitiesData(final MAP_ENTITY_TYPE entityType) {
	// Reads the JSON file with the attributes of the Entities corresponding
	// to the type
	JSONObject entitiesData = this.readJSONFile(entityType.getFilePath());
	this.mEntitiesData.put(entityType.getTypeName(), entitiesData);
    }

    /**
     * Gives a list of all the entities that will be loaded on map when passed a
     * position trigger.
     * 
     * @param pMapPosition
     *            the position of the trigger and also, the ID on the JSON file.
     * @return a list of all the entities that will be loaded on map when passed
     *         a position trigger.
     */
    public List<EntityMessenger> loadEntitiesPositionFromJSON(
	    final String pMapPosition) {
	this.mEntitiesPosition = new CopyOnWriteArrayList<EntityMessenger>();
	try {
	    JSONArray position = this.mEntitiesIndexMap
		    .getJSONArray(pMapPosition);
	    JSONObject elements = (JSONObject) position.get(0);

	    loadEntitiesIndexMap(MAP_ENTITY_TYPE.ARMOR, elements);
	    loadEntitiesIndexMap(MAP_ENTITY_TYPE.BONUS_ITEM, elements);
	    loadEntitiesIndexMap(MAP_ENTITY_TYPE.ENEMY, elements);
	    loadEntitiesIndexMap(MAP_ENTITY_TYPE.WEAPON, elements);

	    return this.mEntitiesPosition;
	} catch (JSONException e) {
	    e.printStackTrace();
	    return null;
	}
    }

    /**
     * Adds certain type of Entities to a list with the positions which has to
     * appear on the map.
     * 
     * @param pEntityType
     *            the type of the entity.
     * @param pEntities
     *            a JSONObject containing the data about the Entities and it's
     *            positions.
     */
    private void loadEntitiesIndexMap(final MAP_ENTITY_TYPE pEntityType,
	    JSONObject pEntities) {
	try {
	    JSONArray concreteEntitiesPositions = pEntities
		    .getJSONArray(pEntityType.getTypeName());

	    for (int i = 0; i < concreteEntitiesPositions.length(); i++) {
		JSONObject entityData = concreteEntitiesPositions
			.getJSONObject(i);
		String id = entityData.getString(JsonFieldsConstantsUtility.ID);
		JSONArray entityPositions = entityData
			.getJSONArray(JsonFieldsConstantsUtility.DEFINITIONS_POSITIONS);

		for (int j = 0; j < entityPositions.length(); j++) {
		    JSONObject position = entityPositions.getJSONObject(j);
		    int posX = position
			    .getInt(JsonFieldsConstantsUtility.DEFINITIONS_POSITION_X);
		    int posY = position
			    .getInt(JsonFieldsConstantsUtility.DEFINITIONS_POSITION_Y);
		    this.mEntitiesPosition.add(new EntityMessenger(pEntityType,
			    id, posX, posY));
		}
	    }
	} catch (JSONException e) {
	    e.printStackTrace();
	}
    }

    /**
     * Gets the starting X position of the Player on the map.
     * 
     * @return the starting X position of the player on the map.
     */
    public float getPlayerPositionX() {
	try {
	    return this.mEntitiesIndexMap
		    .getInt(JsonFieldsConstantsUtility.DEFINITIONS_INITIAL_PLAYER_POSITION_X);

	} catch (JSONException e) {
	    e.printStackTrace();
	    return 0;
	}
    }

    /**
     * Gets the starting Y position of the Player on the map.
     * 
     * @return the starting Y position of the player on the map.
     */
    public float getPlayerPositionY() {
	try {
	    return this.mEntitiesIndexMap
		    .getInt(JsonFieldsConstantsUtility.DEFINITIONS_INITIAL_PLAYER_POSITION_Y);

	} catch (JSONException e) {
	    e.printStackTrace();
	    return 0;
	}
    }

    /**
     * Gets the data of a Entity by it's type and ID.
     * 
     * @param pEntityType
     *            the type of the Entity.
     * @param pId
     *            the ID of the Entity.
     * @return a JSONObject with the data of that Entity.
     */
    public JSONObject getEntityData(MAP_ENTITY_TYPE pEntityType, String pId) {
	try {
	    return this.mEntitiesData.get(pEntityType.getTypeName())
		    .getJSONObject(pId);
	} catch (JSONException e) {
	    e.printStackTrace();
	    return null;
	}
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}
