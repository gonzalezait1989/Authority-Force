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
 * Armor.java
 * 
 * This class is an Entity representing an in game armor, usable either players
 * or enemies.
 * 
 * @version 1.0 Apr 19, 2013
 * @author Aitor Gonzalez Martinez
 * @author Xavier Martinez de Francisco
 * @author Toni Trigo Diaz
 */
public class Armor extends AMapObject {

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    /* The hits left to the Armor to be unusable. */
    private int mHitsLeft;
    /* The default hits left. */
    private int mDefaultHitsLeft;
    /* The damage reduction bonus. */
    private float mDamageReductionBonus;

    // ===========================================================
    // Constructors
    // ===========================================================

    /**
     * Default Constructor of Armor.
     * 
     * @param pId
     *            the ID of the Armor.
     * @param pX
     *            the position of the Armor on the X axis.
     * @param pY
     *            the position of the Armor on the Y axis.
     * @param pTextureRegion
     *            the texture used by the Armor.
     * @param pVbom
     *            the VertexBuffer.
     */
    public Armor(String pId, float pX, float pY, ITextureRegion pTextureRegion,
	    VertexBufferObjectManager pVbom) {
	super(pId, pX, pY, pTextureRegion, pVbom);

	this.setZIndex(1);
    }

    /**
     * Constructor of Armor with all parameters.
     * 
     * @param pId
     *            the ID of the Armor.
     * @param pX
     *            the position of the Armor on the X axis.
     * @param pY
     *            the position of the Armor on the Y axis.
     * @param pTextureRegion
     *            the texture used by the Armor.
     * @param pVbom
     *            the VertexBuffer.
     * @param pHitsLeft
     *            the default hits left of the Armor.
     * @param pDamageReductionBonus
     *            the damage reduction bonus of the Armor.
     */
    public Armor(String pId, float pX, float pY, ITextureRegion pTextureRegion,
	    VertexBufferObjectManager pVbom, int pHitsLeft,
	    float pDamageReductionBonus) {

	super(pId, pX, pY, pTextureRegion, pVbom);

	this.setZIndex(1);
	this.mHitsLeft = pHitsLeft;
	this.mDamageReductionBonus = pDamageReductionBonus;
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    public int getHitsLeft() {
	return this.mHitsLeft;
    }

    public void setHitsLeft(int pHitsLeft) {
	this.mHitsLeft = pHitsLeft;
    }

    public float getDamageReductionBonus() {
	return this.mDamageReductionBonus;
    }

    public void setDamageReductionBonus(float pDamageReductionBonus) {
	this.mDamageReductionBonus = pDamageReductionBonus;
    }

    public int getDefaultHitsLeft() {
	return this.mDefaultHitsLeft;
    }

    public void setDefaultHitsLeft(int pDefaultHitsLeft) {
	this.mDefaultHitsLeft = pDefaultHitsLeft;
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
