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

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Weapon.java
 * 
 * This class is an Entity representing an in game Weapon, usable either by
 * enemies or the player.
 * 
 * @version 1.0 Apr 19, 2013
 * @author Aitor Gonzalez Martinez
 * @author Xavier Martinez de Francisco
 * @author Toni Trigo Diaz
 */
public class Weapon extends AMapObject {

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    /* The number of bullets left. */
    private int mAmmoLeft;
    /* The default maximum ammo of the Weapon. */
    private int mDefaultAmmoLeft;
    /* The Bullet used by the Weapon. */
    private Bullet mBullet;

    // ===========================================================
    // Constructors
    // ===========================================================

    /**
     * Default Constructor for Weapon.
     * 
     * @param pId
     *            the ID of the Weapon.
     * @param pX
     *            the position of the Weapon on the X axis.
     * @param pY
     *            the position of the Weapon on the Y axis.
     * @param pTextureRegion
     *            the texture used by the Weapon.
     * @param pVbom
     *            the VertexBuffer.
     */
    public Weapon(String pId, float pX, float pY,
	    ITextureRegion pTextureRegion, VertexBufferObjectManager pVbom) {
	super(pId, pX, pY, pTextureRegion, pVbom);
    }

    /**
     * Constructor for Weapon with more parameters.
     * 
     * @param pId
     *            the ID of the Weapon.
     * @param pX
     *            the position of the Weapon on the X axis.
     * @param pY
     *            the position of the Weapon on the Y axis.
     * @param pTextureRegion
     *            the texture used by the Weapon.
     * @param pVbom
     *            the VertexBuffer.
     * @param pAmmoLeft
     *            the ammo left on the Weapon.
     * @param pBullet
     *            the Bullet used by the Weapon.
     */
    public Weapon(String pId, float pX, float pY,
	    ITextureRegion pTextureRegion, VertexBufferObjectManager pVbom,
	    int pAmmoLeft, Bullet pBullet) {

	super(pId, pX, pY, pTextureRegion, pVbom);

	this.setZIndex(1);
	this.mAmmoLeft = pAmmoLeft;
	this.mBullet = pBullet;
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    public int getAmmoLeft() {
	return this.mAmmoLeft;
    }

    public void setAmmoLeft(int pAmmoLeft) {
	this.mAmmoLeft = pAmmoLeft;
    }

    public Bullet getBullet() {
	return this.mBullet;
    }

    public void setBullet(Bullet pBullet) {
	this.mBullet = pBullet;
    }

    public int getDefaultAmmoLeft() {
	return mDefaultAmmoLeft;
    }

    public void setDefaultAmmoLeft(int mDefaultAmmoLeft) {
	this.mDefaultAmmoLeft = mDefaultAmmoLeft;
    }

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    // ===========================================================
    // Methods
    // ===========================================================

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}
