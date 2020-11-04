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

import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.devgru.authorityforce.constant.GeneralConstantsUtility.MAP_ENTITY_TYPE;
import org.devgru.authorityforce.entity.Armor;
import org.devgru.authorityforce.entity.BonusItem;
import org.devgru.authorityforce.entity.Bullet;
import org.devgru.authorityforce.entity.Enemy;
import org.devgru.authorityforce.entity.Weapon;

/**
 * AFMultiPool.java
 * 
 * AFMultipool is able to return any instance of any of the existing pools,
 * casted to Sprite.
 * 
 * @version 1.0 May 18, 2013
 * @author Aitor Gonzalez Martinez
 * @author Xavier Martinez de Francisco
 * @author Toni Trigo Diaz
 */
public class AFMultiPool {

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    private ArmorPool armorPool;
    private WeaponPool weaponPool;
    private BonusItemPool bonusItemPool;
    private EnemyPool enemyPool;
    private BulletPool bulletPool;

    // ===========================================================
    // Constructors
    // ===========================================================

    public AFMultiPool(PhysicsWorld pPhysicsWorld) {
	this.armorPool = new ArmorPool();
	this.weaponPool = new WeaponPool();
	this.bonusItemPool = new BonusItemPool();
	this.enemyPool = new EnemyPool();
	this.bulletPool = new BulletPool();

	this.armorPool.batchAllocatePoolItems(10);
	this.weaponPool.batchAllocatePoolItems(20);
	this.bonusItemPool.batchAllocatePoolItems(10);
	this.enemyPool.batchAllocatePoolItems(20);
	this.bulletPool.batchAllocatePoolItems(60);
    }

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
     * Obtains a Sprite of a specific type, and position from it's specific
     * pool.
     * 
     * @param pType
     *            MAP_ENTITY_TYPE type of the entity.
     * @param pId
     *            entity id.
     * @param pX
     *            entity X position in the map.
     * @param pY
     *            entity Y position in the map.
     * @return a Sprite from it's pool.
     */
    public synchronized Sprite obtainItem(MAP_ENTITY_TYPE pType, String pId,
	    float pX, float pY) {

	switch (pType) {
	case ARMOR:
	    return this.armorPool.obtainPoolItem(pId, pX, pY);
	case WEAPON:
	    return this.weaponPool.obtainPoolItem(pId, pX, pY, this.bulletPool);
	case BONUS_ITEM:
	    return this.bonusItemPool.obtainPoolItem(pId, pX, pY);
	case ENEMY:
	    return this.enemyPool.obtainPoolItem(pId, pX, pY);
	case BULLET:
	    return this.bulletPool.obtainPoolItem(pId, pX, pY);
	default:
	    return null;
	}
    }

    /**
     * Recycle an entity which already done it's function to it's specific pool.
     * 
     * @param pID
     *            MAP_ENTITY_TYPE the entity type.
     * @param pItem
     *            the entity to recycle.
     */
    public synchronized void recycleItem(final MAP_ENTITY_TYPE pID,
	    final Sprite pItem) {

	switch (pID) {
	case ARMOR:
	    this.armorPool.recyclePoolItem((Armor) pItem);
	    break;
	case WEAPON:
	    this.weaponPool.recyclePoolItem((Weapon) pItem);
	    break;
	case BONUS_ITEM:
	    this.bonusItemPool.recyclePoolItem((BonusItem) pItem);
	    break;
	case ENEMY:
	    this.enemyPool.recyclePoolItem((Enemy) pItem);
	    break;
	case BULLET:
	    this.bulletPool.recyclePoolItem((Bullet) pItem);
	    break;
	}
    }

    public void freeResources() {
	this.armorPool = null;
	this.weaponPool = null;
	this.bonusItemPool = null;
	this.enemyPool = null;
	this.bulletPool = null;
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}
