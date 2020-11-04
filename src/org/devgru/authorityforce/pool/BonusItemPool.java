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
import org.devgru.authorityforce.entity.BonusItem;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/**
 * BonusItemPool.java
 * 
 * This class is a Pool of bonus items, which can store already used bonus items
 * and recycle them.
 * 
 * @version 1.0 May 10, 2013
 * @author Aitor Gonzalez Martinez
 * @author Xavier Martinez de Francisco
 * @author Toni Trigo Diaz
 */
public class BonusItemPool extends AFGenericPool<BonusItem> {

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    private Map<String, TextureRegion> mBonusItemTextureRegions;

    // ===========================================================
    // Constructors
    // ===========================================================

    BonusItemPool() {
	super();
	this.mBonusItemTextureRegions = this.mGameResourceManager
		.getCharactersObjectTexture();
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    @Override
    protected BonusItem onAllocatePoolItem() {
	return new BonusItem("small_healthpack", 0, 0,
		mBonusItemTextureRegions.get("ammo"), MainParameters.vbom);
    }

    // ===========================================================
    // Methods
    // ===========================================================

    public synchronized BonusItem obtainPoolItem(String pId, float pX, float pY) {
	BonusItem bonusItem = obtainPoolItem();

	try {

	    JSONObject bonusItemData = this.mEntityDao.getEntityData(
		    MAP_ENTITY_TYPE.BONUS_ITEM, pId);
	    bonusItem.setTextureRegion(mBonusItemTextureRegions.get(pId));
	    bonusItem.setPosition(pX, pY);
	    bonusItem.setId(pId);
	    bonusItem.setHpBonus(bonusItemData
		    .getInt(JsonFieldsConstantsUtility.BONUS_ITEM_HP_BONUS));
	    bonusItem
		    .setScoreMultiplier(bonusItemData
			    .getInt(JsonFieldsConstantsUtility.BONUS_ITEM_SCORE_MULTIPLIER));
	    bonusItem.setExtraAmmo(bonusItemData
		    .getInt(JsonFieldsConstantsUtility.BONUS_ITEM_EXTRA_AMMO));
	    bonusItem.setVisible(true);
	    bonusItem.setIgnoreUpdate(false);

	} catch (JSONException e) {
	    Log.e("BonusItemPool", e.getMessage());
	}
	return bonusItem;
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}
