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

package org.devgru.authorityforce.constant;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.devgru.authorityforce.activity.AuthorityForceActivity;

/**
 * MainParameters.java
 * 
 * This class contains references to MainActivity, camera, engine and Vertex
 * Buffer Object Manager used by the application.
 * 
 * @version 1.0 May 24, 2013
 * @author Aitor Gonzalez Martinez
 * @author Xavier Martinez de Francisco
 * @author Toni Trigo Diaz
 */
public final class MainParameters {

    /** The game main activity */
    public static AuthorityForceActivity activity;
    /** The game Vertex Buffer Object Manager. */
    public static VertexBufferObjectManager vbom;
    /** The game camera. */
    public static BoundCamera camera;
    /** The game engine. */
    public static Engine engine;

    /**
     * This method acts as a setter for this class fields, making them reference
     * application main objects.
     * 
     * @param pAuthorityForceActivity
     *            The game main activity.
     */
    public final static void setParams(
	    AuthorityForceActivity pAuthorityForceActivity) {
	// If to check that fields are only setted one time.
	if (activity == null) {
	    activity = pAuthorityForceActivity;
	    vbom = activity.getVertexBufferObjectManager();
	    camera = activity.getBoundCamera();
	    engine = activity.getEngine();
	}
    }

}
