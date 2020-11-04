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
package org.devgru.authorityforce.util;

import org.devgru.authorityforce.constant.GeneralConstantsUtility.MAP_ENTITY_TYPE;

/**
 * EntityMessenger.java
 * 
 * This class modelizes a coordinate with X and Y axis and the type of the
 * entity.
 * 
 * @version 1.0 May 18, 2013
 * @author Aitor Gonzalez Martinez
 * @author Xavier Martinez de Francisco
 * @author Toni Trigo Diaz
 */
public class EntityMessenger {

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    /* The entity type */
    private MAP_ENTITY_TYPE mEntityType;
    /* The id of the entity */
    private String mId;
    /* Position x of the entity on the map */
    private int mPosX;
    /* Position y of the entity on the map */
    private int mPosY;

    // ===========================================================
    // Constructors
    // ===========================================================

    /**
     * Constructor
     * 
     * @param pEntityType
     *            type of the entity
     * @param pId
     *            id of the entity
     * @param pPosX
     *            x position of the entity on the map
     * @param pPosY
     *            y position of the entity on the map
     */
    public EntityMessenger(final MAP_ENTITY_TYPE pEntityType, final String pId,
	    final int pPosX, final int pPosY) {
	this.mEntityType = pEntityType;
	this.mId = pId;
	this.mPosX = pPosX;
	this.mPosY = pPosY;
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    public int getPosX() {
	return this.mPosX;
    }

    public int getPosY() {
	return this.mPosY;
    }

    public MAP_ENTITY_TYPE getEntityType() {
	return this.mEntityType;
    }

    public String getId() {
	return this.mId;
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
