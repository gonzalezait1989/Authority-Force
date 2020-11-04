package org.devgru.authorityforce.pool;

import java.util.Map;

import org.andengine.opengl.texture.region.TextureRegion;
import org.devgru.authorityforce.constant.JsonFieldsConstantsUtility;
import org.devgru.authorityforce.constant.MainParameters;
import org.devgru.authorityforce.entity.Bullet;
import org.json.JSONException;
import org.json.JSONObject;

public class BulletPool extends AFGenericPool<Bullet> {

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    private Map<String, TextureRegion> mBulletTextureRegions;

    // ===========================================================
    // Constructors
    // ===========================================================

    BulletPool() {
	super();
	this.mBulletTextureRegions = this.mGameResourceManager
		.getCharactersObjectTexture();
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    @Override
    protected Bullet onAllocatePoolItem() {
	return new Bullet("standard_bullet", 0, 0,
		mBulletTextureRegions.get("standard_bullet"),
		MainParameters.vbom);
    }

    // ===========================================================
    // Methods
    // ===========================================================

    public synchronized Bullet obtainPoolItem(String pId, float pX, float pY) {

	Bullet bullet = obtainPoolItem();

	try {

	    JSONObject bulletData = this.mEntityDao.getBulletData(pId);
	    bullet.setTextureRegion(mBulletTextureRegions.get(pId));
	    bullet.setId(pId);
	    bullet.setPosition(pX, pY);
	    bullet.setSpeed((float) bulletData
		    .getDouble(JsonFieldsConstantsUtility.BULLET_SPEED));
	    bullet.setDamage(bulletData
		    .getInt(JsonFieldsConstantsUtility.BULLET_DAMAGE));
	    bullet.setRange((float) bulletData
		    .getDouble(JsonFieldsConstantsUtility.BULLET_RANGE));

	    if (pX != 0 && pY != 0) {
		bullet.setVisible(true);
		bullet.setIgnoreUpdate(false);
	    }

	} catch (JSONException e) {
	    e.printStackTrace();
	}

	return bullet;
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}
