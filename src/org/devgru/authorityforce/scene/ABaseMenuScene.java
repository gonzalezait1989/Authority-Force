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

package org.devgru.authorityforce.scene;

import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.devgru.authorityforce.manager.scene.MenuResourceManager;

/**
 * ABaseMenuScene.java
 * 
 * This the parent class of all the menu scenes of the application
 * 
 * @version 1.0 Apr 19, 2013
 * @author Aitor Gonzalez Martinez
 * @author Xavier Martinez de Francisco
 * @author Toni Trigo Diaz
 */
public abstract class ABaseMenuScene extends ABaseScene implements
	IOnMenuItemClickListener {

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    // Instance of the MenuResourceManager
    protected MenuResourceManager mMenuResourceManager;

    // ===========================================================
    // Constructors
    // ===========================================================

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    /**
     * Obtains all resources so that the menus can run
     */
    @Override
    public void createScene() {
	// Gets MenuResourceManager instance
	this.mMenuResourceManager = MenuResourceManager.getInstance();

	// First create the background of the scene
	this.createBaseScene();
	// Create the content of the scene
	this.createChildMenuScene();
    }

    /**
     * Loads all the screen elements the user can not interact with.
     */
    public abstract void createBaseScene();

    /**
     * Loads all clickable items on the screen.
     */
    public abstract void createChildMenuScene();

    /**
     * Apply the effects when scene is attached
     */
    public abstract void applyScreenInActions();

    /**
     * Apply the effects when scene is detached
     */
    public abstract void applyScreenOutActions();

    // ===========================================================
    // Methods
    // ===========================================================

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}
