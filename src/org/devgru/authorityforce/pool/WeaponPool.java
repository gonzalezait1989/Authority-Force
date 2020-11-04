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
import org.devgru.authorityforce.entity.Bullet;
import org.devgru.authorityforce.entity.Weapon;
import org.devgru.authorityforce.scene.game.GameScene;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/**
 * EnemyPool.java
 * 
 * This class is a Pool of weapons, which can store already used weapons and
 * recycle them.
 * 
 * @version 1.0 May 10, 2013
 * @author Aitor Gonzalez Martinez
 * @author Xavier Martinez de Francisco
 * @author Toni Trigo Diaz
 */
public class WeaponPool extends AFGenericPool<Weapon> {

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    private Map<String, TextureRegion> mWeaponTextureRegions;

    // ===========================================================
    // Constructors
    // ===========================================================

    WeaponPool() {
	super();
	this.mWeaponTextureRegions = this.mGameResourceManager
		.getCharactersObjectTexture();
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    @Override
    protected Weapon onAllocatePoolItem() {
	return new Weapon("m4a2", 0, 0, mWeaponTextureRegions.get("m4a2"),
		MainParameters.vbom);
    }

    @Override
    protected synchronized void onHandleRecycleItem(Weapon pItem) {

	Bullet bullet = pItem.getBullet();
	GameScene.mMultiPool.recycleItem(MAP_ENTITY_TYPE.BULLET, bullet);
	pItem.setBullet(null);

	super.onHandleRecycleItem(pItem);
    }

    // ===========================================================
    // Methods
    // ===========================================================

    public synchronized Weapon obtainPoolItem(String pId, float pX, float pY,
	    BulletPool pBulletPool) {
	Weapon weapon = super.obtainPoolItem();

	try {

	    JSONObject weaponData = this.mEntityDao.getEntityData(
		    MAP_ENTITY_TYPE.WEAPON, pId);
	    weapon.setTextureRegion(mWeaponTextureRegions.get(pId));
	    weapon.setPosition(pX, pY);
	    weapon.setId(pId);
	    int ammo = weaponData
		    .getInt(JsonFieldsConstantsUtility.WEAPON_BULLETS_LEFT);
	    weapon.setAmmoLeft(ammo);
	    weapon.setDefaultAmmoLeft(ammo);
	    String bulletID = weaponData
		    .getString(JsonFieldsConstantsUtility.WEAPON_BULLET_ID);
	    weapon.setBullet((Bullet) pBulletPool
		    .obtainPoolItem(bulletID, 0, 0));

	    if (pX != 0 && pY != 0) {
		weapon.setVisible(true);
		weapon.setIgnoreUpdate(false);
	    }

	} catch (JSONException e) {
	    Log.e("WeaponPool", e.getMessage());
	}

	return weapon;
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}
