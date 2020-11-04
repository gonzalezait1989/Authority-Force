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
import org.andengine.util.adt.pool.GenericPool;
import org.devgru.authorityforce.dao.EntityDAO;
import org.devgru.authorityforce.manager.scene.GameResourceManager;

/**
 * AFGenericPool.java
 * 
 * This class is a generic pools, which is extended by all the pools in the
 * application.
 * 
 * @version 1.0 May 10, 2013
 * @author Aitor Gonzalez Martinez
 * @author Xavier Martinez de Francisco
 * @author Toni Trigo Diaz
 */
public abstract class AFGenericPool<T> extends GenericPool<T> {

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    protected GameResourceManager mGameResourceManager;
    protected EntityDAO mEntityDao;

    // ===========================================================
    // Constructors
    // ===========================================================

    protected AFGenericPool() {
	super();
	this.mGameResourceManager = GameResourceManager.getInstance();
	this.mEntityDao = EntityDAO.getInstance();
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    @Override
    protected synchronized void onHandleRecycleItem(T pItem) {
	super.onHandleRecycleItem(pItem);
	Sprite item = (Sprite) pItem;
	item.setVisible(false);
	item.setIgnoreUpdate(true);
	item.clearEntityModifiers();
	item.clearUpdateHandlers();
	item.detachSelf();
    }

    // ===========================================================
    // Methods
    // ===========================================================

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}
