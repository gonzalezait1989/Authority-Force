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

import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.devgru.authorityforce.constant.GeneralConstantsUtility.MAP_ENTITY_TYPE;
import org.devgru.authorityforce.constant.JsonFieldsConstantsUtility;
import org.devgru.authorityforce.constant.MainParameters;
import org.devgru.authorityforce.entity.Enemy;
import org.devgru.authorityforce.entity.Weapon;
import org.devgru.authorityforce.manager.GameManager;
import org.devgru.authorityforce.scene.game.GameScene;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * EnemyPool.java
 * 
 * This class is a Pool of enemies, which can store already used enemies and
 * recycle them.
 * 
 * @version 1.0 May 10, 2013
 * @author Aitor Gonzalez Martinez
 * @author Xavier Martinez de Francisco
 * @author Toni Trigo Diaz
 */
public class EnemyPool extends AFGenericPool<Enemy> {

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    private Map<String, TiledTextureRegion> mEnemyTextureRegions;

    // ===========================================================
    // Constructors
    // ===========================================================

    EnemyPool() {
	super();
	this.mEnemyTextureRegions = this.mGameResourceManager
		.getEnemiesTexture();
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    @Override
    protected Enemy onAllocatePoolItem() {
	return new Enemy("infected", 0, 0,
		this.mEnemyTextureRegions.get("infected"), MainParameters.vbom);
    }

    @Override
    protected synchronized void onHandleRecycleItem(Enemy pItem) {

	Weapon weapon = pItem.getWeapon();
	GameScene.mMultiPool.recycleItem(MAP_ENTITY_TYPE.WEAPON, weapon);
	pItem.setWeapon(null);

	if (pItem.getCharacterBody() != null) {
	    GameScene.mPhysicsWorld.destroyBody(pItem.getCharacterBody());
	}

	super.onHandleRecycleItem(pItem);
    }

    // ===========================================================
    // Methods
    // ===========================================================

    public synchronized Enemy obtainPoolItem(final String pId, float pX,
	    float pY) {
	Enemy enemy = super.obtainPoolItem();

	try {
	    float difficultyMultiplier = GameManager.getInstance()
		    .getDifficultyMultiplier();
	    JSONObject enemyData = this.mEntityDao.getEntityData(
		    MAP_ENTITY_TYPE.ENEMY, pId);
	    enemy.setTextureRegion(this.mEnemyTextureRegions.get(pId));
	    enemy.setPosition(pX, pY);
	    enemy.setId(pId);
	    enemy.setHp((int) (enemyData
		    .getInt(JsonFieldsConstantsUtility.CHARACTER_HP) * difficultyMultiplier));
	    // Log.e("EnemyPool", String.valueOf(enemy.getHp()));
	    enemy.setMovementSpeed((float) enemyData
		    .getDouble(JsonFieldsConstantsUtility.CHARACTER_MOVEMENT_SPEED));
	    enemy.setTimePhysicalAttack((long) enemyData
		    .getDouble(JsonFieldsConstantsUtility.CHARACTER_PHYSICAL_ATTACK_TIME));
	    Weapon weapon = (Weapon) GameScene.mMultiPool
		    .obtainItem(
			    MAP_ENTITY_TYPE.WEAPON,
			    enemyData
				    .getString(JsonFieldsConstantsUtility.ENEMY_WEAPON),
			    0, 0);
	    weapon.getBullet()
		    .setDamage(
			    (int) (weapon.getBullet().getDamage() * difficultyMultiplier));
	    // Log.e("ENEMYPOOL","BULLET DAMAGE: " +
	    // weapon.getBullet().getDamage());
	    enemy.setWeapon(weapon);
	    enemy.setGivenScore(enemyData
		    .getInt(JsonFieldsConstantsUtility.ENEMY_GIVEN_SCORE));
	    enemy.setVisible(true);
	    enemy.setIgnoreUpdate(false);
	    enemy.createPhysics(GameScene.mPhysicsWorld);

	} catch (JSONException e) {
	    e.printStackTrace();
	}

	return enemy;
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}
