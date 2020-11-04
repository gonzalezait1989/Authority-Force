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

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * ACharacterObject.java
 * 
 * This class is an Entity representing any in game object usable by a player or
 * an enemy.
 * 
 * @version 1.0 Apr 19, 2013
 * @author Aitor Gonzalez Martinez
 * @author Xavier Martinez de Francisco
 * @author Toni Trigo Diaz
 */
public abstract class AMapObject extends Sprite {

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    /* The MapObject ID. */
    private String mId;

    // ===========================================================
    // Constructors
    // ===========================================================

    /**
     * Default Constructor for AMapObject.
     * 
     * @param pId
     *            the ID of the Map Object.
     * @param pX
     *            the position of the Map Object on the X axis.
     * @param pY
     *            the position of the Map Object on the Y axis.
     * @param pTextureRegion
     *            the texture used by the Map Object.
     * @param pVbom
     *            the VertexBuffer.
     */
    public AMapObject(String pId, float pX, float pY,
	    ITextureRegion pTextureRegion, VertexBufferObjectManager pVbom) {
	super(pX, pY, pTextureRegion, pVbom);

	this.mId = pId;
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    public String getId() {
	return this.mId;
    }

    public void setId(String pId) {
	this.mId = pId;
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
