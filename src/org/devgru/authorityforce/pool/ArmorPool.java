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

package org.devgru.authorityforce.pool;

import java.util.Map;

import org.andengine.opengl.texture.region.TextureRegion;
import org.devgru.authorityforce.constant.GeneralConstantsUtility.MAP_ENTITY_TYPE;
import org.devgru.authorityforce.constant.JsonFieldsConstantsUtility;
import org.devgru.authorityforce.constant.MainParameters;
import org.devgru.authorityforce.entity.Armor;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/**
 * ArmorPool.java
 * 
 * This class is a Pool of armors, which can store already used armors and
 * recycle them.
 * 
 * @version 1.0 May 10, 2013
 * @author Aitor Gonzalez Martinez
 * @author Xavier Martinez de Francisco
 * @author Toni Trigo Diaz
 */
public class ArmorPool extends AFGenericPool<Armor> {

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    private Map<String, TextureRegion> mArmorTextureRegions;

    // ===========================================================
    // Constructors
    // ===========================================================

    ArmorPool() {
	super();
	this.mArmorTextureRegions = this.mGameResourceManager
		.getCharactersObjectTexture();
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    @Override
    protected Armor onAllocatePoolItem() {
	return new Armor("vest_light", 0, 0,
		mArmorTextureRegions.get("vest_light"), MainParameters.vbom);
    }

    // ===========================================================
    // Methods
    // ===========================================================

    public synchronized Armor obtainPoolItem(final String pId, float pX,
	    float pY) {
	Armor armor = super.obtainPoolItem();
	JSONObject armorData = this.mEntityDao.getEntityData(
		MAP_ENTITY_TYPE.ARMOR, pId);
	try {
	    armor.setTextureRegion(mArmorTextureRegions.get(pId));
	    armor.setPosition(pX, pY);
	    armor.setHitsLeft(armorData
		    .getInt(JsonFieldsConstantsUtility.ARMOR_HITS_LEFT));
	    armor.setDefaultHitsLeft(armorData
		    .getInt(JsonFieldsConstantsUtility.ARMOR_HITS_LEFT));
	    armor.setDamageReductionBonus((float) armorData
		    .getDouble(JsonFieldsConstantsUtility.ARMOR_DAMAGE_REDUCTION_BONUS));
	    armor.setId(pId);
	    armor.setVisible(true);
	    armor.setIgnoreUpdate(false);
	} catch (JSONException e) {
	    Log.e("ArmorPool", e.getMessage());
	}

	return armor;
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}
