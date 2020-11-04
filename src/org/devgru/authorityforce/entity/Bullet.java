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
package org.devgru.authorityforce.entity;

import org.andengine.entity.IEntity;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.devgru.authorityforce.constant.GeneralConstantsUtility;

/**
 * Bullet.java
 * 
 * This class is an Entity representing a bullet.
 * 
 * @version 1.0 May 15, 2013
 * @author Aitor Gonzalez Martinez
 * @author Xavier Martinez de Francisco
 * @author Toni Trigo Diaz
 */
public class Bullet extends AMapObject {

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    /* The Bullet speed. */
    private float mSpeed;
    /* The Bullet damage. */
    private int mDamage;
    /* The Bullet range. */
    private float mRange;
    /* The entity shoot the bullet. */
    private String mShooter;

    // ===========================================================
    // Constructors
    // ===========================================================

    /**
     * Default Constructor of Bullet.
     * 
     * @param pId
     *            the ID of the Bullet.
     * @param pX
     *            the position of the Bullet on the X axis.
     * @param pY
     *            the position of the Bullet on the Y axis.
     * @param pTextureRegion
     *            the texture used by the Bullet.
     * @param pVbom
     *            the VertexBuffer.
     */
    public Bullet(String pId, float pX, float pY,
	    ITextureRegion pTextureRegion, VertexBufferObjectManager pVbom) {
	super(pId, pX, pY, pTextureRegion, pVbom);

	this.setZIndex(2);
    }

    /**
     * Default Constructor of Bullet.
     * 
     * @param pId
     *            the ID of the Bullet.
     * @param pX
     *            the position of the Bullet on the X axis.
     * @param pY
     *            the position of the Bullet on the Y axis.
     * @param pTextureRegion
     *            the texture used by the Bullet.
     * @param pVbom
     *            the VertexBuffer.
     * @param pSpeed
     *            the Bullet speed.
     * @param pDamage
     *            the Bullet damage.
     * @param pRange
     *            the Bullet range.
     */
    private Bullet(String mId, float pX, float pY,
	    ITextureRegion pTextureRegion, VertexBufferObjectManager pVbom,
	    float pSpeed, int pDamage, float pRange) {

	super(mId, pX, pY, pTextureRegion, pVbom);

	this.setZIndex(2);
	this.mSpeed = pSpeed;
	this.mDamage = pDamage;
	this.mRange = pRange;
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    public float getSpeed() {
	return this.mSpeed;
    }

    public void setSpeed(float pSpeed) {
	this.mSpeed = pSpeed;
    }

    public int getDamage() {
	return this.mDamage;
    }

    public void setDamage(int pDamage) {
	this.mDamage = pDamage;
    }

    public float getRange() {
	return this.mRange;
    }

    public String getShooter() {
	return this.mShooter;
    }

    public void setRange(float pRange) {
	this.mRange = pRange;
    }

    public void setShooter(String pShooter) {
	this.mShooter = pShooter;
    }

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    @Override
    public boolean collidesWith(IEntity pOtherEntity) {
	if (this.mShooter.equals(GeneralConstantsUtility.ENEMY_SHOOT)
		&& pOtherEntity instanceof Enemy) {
	    return false;
	} else if (this.mShooter.equals(GeneralConstantsUtility.PLAYER_SHOOT)
		&& pOtherEntity instanceof Player) {
	    return false;
	} else {
	    return super.collidesWith(pOtherEntity);
	}
    }

    // ===========================================================
    // Methods
    // ===========================================================

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}
