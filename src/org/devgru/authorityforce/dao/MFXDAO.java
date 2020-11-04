package org.devgru.authorityforce.dao;

import java.util.HashMap;
import java.util.Map;

import org.devgru.authorityforce.constant.JsonFieldsConstantsUtility;
import org.devgru.authorityforce.constant.JsonFieldsConstantsUtility.SOUND_TYPE;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MFXDAO extends AFileJSONDAO {

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    /* A Instance of MFXDAO. */
    private static MFXDAO INSTANCE = null;

    /* The JSON Object with the definitions of the sounds in the map. */
    private JSONObject mAudioData;
    /* A HashMap with the id of the sounds and the sounds path. */
    private Map<String, String> mSoundPaths;

    // ===========================================================
    // Constructors
    // ===========================================================

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    /**
     * Gets a Instance of MFXDAO.
     * 
     * @return a Instance of MFXDAO.
     */
    public static MFXDAO getInstance() {
	if (INSTANCE == null) {
	    INSTANCE = new MFXDAO();
	}
	return INSTANCE;
    }

    public Map<String, String> getSoundPaths() {
	return this.mSoundPaths;
    }

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    // ===========================================================
    // Methods
    // ===========================================================

    /**
     * Gets the path of the Music file of a determinate level.
     * 
     * @return the path of the Music file of a determinate level.
     */
    public String getMusicByLevel() {
	this.mAudioData = this.readJSONFile(MapFluxDAO.getInstance()
		.getMapData(JsonFieldsConstantsUtility.FLUX_SOUNDS));

	try {
	    return this.mAudioData
		    .getString(JsonFieldsConstantsUtility.SOUND_LEVEL_SONG_ID);

	} catch (JSONException e) {
	    e.printStackTrace();
	    return null;
	}
    }

    /**
     * Loads the paths and data of the sounds of a determinate type of Entity.
     * 
     * @param pType
     *            the type of the Entity.
     */
    private void loadSoundsByType(final SOUND_TYPE pType) {
	if (this.mAudioData == null) {
	    this.mAudioData = this.readJSONFile(MapFluxDAO.getInstance()
		    .getMapData(JsonFieldsConstantsUtility.FLUX_SOUNDS));
	}

	if (this.mSoundPaths == null) {
	    this.mSoundPaths = new HashMap<String, String>();
	}

	try {
	    JSONArray soundsData = this.mAudioData.getJSONArray(pType
		    .getTypeName());
	    for (int i = 0; i < soundsData.length(); i++) {
		JSONObject sound;
		sound = soundsData.getJSONObject(i);
		final String id = sound
			.getString(JsonFieldsConstantsUtility.SOUND_ID);
		final String path = sound
			.getString(JsonFieldsConstantsUtility.SOUND_PATH);
		this.mSoundPaths.put(id, path);
	    }

	} catch (JSONException e) {
	    e.printStackTrace();
	}
    }

    /**
     * Loads all the paths and data of the sounds of all Entities.
     */
    public void loadSounds() {
	this.loadSoundsByType(SOUND_TYPE.WEAPONS);
    }

    /**
     * Disposes the resources of data.
     */
    public void disposeResources() {
	this.mSoundPaths = null;
	this.mAudioData = null;
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}
