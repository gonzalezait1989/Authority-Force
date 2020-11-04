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
package org.devgru.authorityforce.manager.scene;

import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.util.adt.color.Color;
import org.devgru.authorityforce.constant.AssetsConstantsUtility;
import org.devgru.authorityforce.constant.MainParameters;
import org.devgru.authorityforce.manager.IResourceManager;

/**
 * ASceneResourcesManager.java
 * 
 * Abstract class for scenes managers
 * 
 * @version 1.0 March 7, 2013
 * @author Aitor Gonzalez Martinez
 * @author Xavier Martinez de Francisco
 * @author Toni Trigo Diaz
 */

public abstract class ASceneResourcesManager implements IResourceManager {

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    /* The Font used by the game */
    protected Font mFont;

    // ===========================================================
    // Constructors
    // ===========================================================

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    public Font getFont() {
	return this.mFont;
    }

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    /**
     * Load all resources needed for the scenes
     */
    public abstract void loadAllResources();

    // ===========================================================
    // Methods
    // ===========================================================

    /**
     * Loads the desired font with a size and a color
     */
    public void loadFont() {
	// Loads the Font
	FontFactory.setAssetBasePath(AssetsConstantsUtility.FONTS_DIRECTORY);

	this.mFont = FontFactory.createFromAsset(
		MainParameters.engine.getFontManager(),
		MainParameters.engine.getTextureManager(), 256, 256,
		MainParameters.activity.getAssets(),
		AssetsConstantsUtility.MAIN_FONT, 30f, true,
		Color.WHITE_ARGB_PACKED_INT);
	this.mFont.load();
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}
