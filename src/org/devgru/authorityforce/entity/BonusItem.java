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
 * BonusItem.java
 * 
 * This class is an Entity representing an in game bonus item.
 * 
 * @version 1.0 Apr 19, 2013
 * @author Aitor Gonzalez Martinez
 * @author Xavier Martinez de Francisco
 * @author Toni Trigo Diaz
 */
public class BonusItem extends AMapObject {

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    /* The amount of Health this Item gives to the Player. */
    private int mHpBonus;
    /* The amount of ammo this Item gives to the Player. */
    private int mExtraAmmo;
    /* The score multiplier this Item gives to the Player. */
    private int mScoreMultiplier;

    // ===========================================================
    // Constructors
    // ===========================================================

    /**
     * Default Constructor of Bonus Item.
     * 
     * @param pId
     *            the ID of the Bonus Item.
     * @param pX
     *            the position of the Bonus Item on the X axis.
     * @param pY
     *            the position of the Bonus Item on the Y axis.
     * @param pTextureRegion
     *            the texture used by the Bonus Item.
     * @param pVbom
     *            the VertexBuffer.
     */
    public BonusItem(String pId, float pX, float pY,
	    ITextureRegion pTextureRegion, VertexBufferObjectManager pVbom) {
	super(pId, pX, pY, pTextureRegion, pVbom);

	this.setZIndex(1);
    }

    /**
     * Constructor of Bonus Item with more parameters.
     * 
     * @param pId
     *            the ID of the Bonus Item.
     * @param pX
     *            the position of the Bonus Item on the X axis.
     * @param pY
     *            the position of the Bonus Item on the Y axis.
     * @param pTextureRegion
     *            the texture used by the Bonus Item.
     * @param pVbom
     *            the VertexBuffer.
     * @param pHpBonus
     *            the Health Points Bonus of the Bonus Item.
     * @param pExtraAmmo
     *            the extra ammo given by the Bonus Item.
     * @param pScoreMultiplier
     *            the score multiplier given by the Bonus Item.
     */
    public BonusItem(String pId, float pX, float pY,
	    ITextureRegion pTextureRegion, VertexBufferObjectManager pVbom,
	    int pHpBonus, int pExtraAmmo, int pScoreMultiplier) {

	super(pId, pX, pY, pTextureRegion, pVbom);

	this.setZIndex(1);
	this.mHpBonus = pHpBonus;
	this.mExtraAmmo = pExtraAmmo;
	this.mScoreMultiplier = pScoreMultiplier;
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    public int getHpBonus() {
	return this.mHpBonus;
    }

    public void setHpBonus(int pHpBonus) {
	this.mHpBonus = pHpBonus;
    }

    public int getExtraAmmo() {
	return this.mExtraAmmo;
    }

    public void setExtraAmmo(int pExtraAmmo) {
	this.mExtraAmmo = pExtraAmmo;
    }

    public int getScoreMultiplier() {
	return this.mScoreMultiplier;
    }

    public void setScoreMultiplier(int pScoreMultiplier) {
	this.mScoreMultiplier = pScoreMultiplier;
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
