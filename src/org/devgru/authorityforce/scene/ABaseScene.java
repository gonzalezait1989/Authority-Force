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

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.FadeInModifier;
import org.andengine.entity.modifier.FadeOutModifier;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.Scene;
import org.devgru.authorityforce.audio.AudioPlayer;
import org.devgru.authorityforce.constant.GeneralConstantsUtility.SceneType;
import org.devgru.authorityforce.controller.SceneController;
import org.devgru.authorityforce.manager.GameManager;
import org.devgru.authorityforce.preferences.PreferencesManager;

/**
 * ABaseScene.java
 * 
 * This the parent class of all the scenes of the application
 * 
 * @version 1.0 Apr 19, 2013
 * @author Aitor Gonzalez Martinez
 * @author Xavier Martinez de Francisco
 * @author Toni Trigo Diaz
 */
public abstract class ABaseScene extends Scene {

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    // Instance of the SceneController
    protected SceneController mSceneController;
    // / Instance of the PreferencesManager
    protected PreferencesManager mPreferencesManager;
    // Instance of the GameManager
    protected GameManager mGameManager;
    // Instance of the AudioPlayer
    protected AudioPlayer mAudioPlayer;

    // ===========================================================
    // Constructors
    // ===========================================================

    /**
     * This constructor is not called directly. When call the constructor of a
     * scene that goes up to get here
     */
    public ABaseScene() {
	// Gets all instances that will use
	this.mSceneController = SceneController.getInstance();
	this.mPreferencesManager = PreferencesManager.getInstance();
	this.mGameManager = GameManager.getInstance();
	this.mAudioPlayer = AudioPlayer.getInstance();

	// Call the method that creates the scene
	createScene();
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    /**
     * Main method of creation of the scene
     */
    public abstract void createScene();

    /**
     * This is called when back button is pressed
     */
    public abstract void onBackKeyPressed();

    /**
     * Returns the type of scene
     * 
     * @return returns the type of scene
     */
    public abstract SceneType getSceneType();

    /**
     * Dispose all resources for the scene
     */
    public abstract void disposeScene();

    // ===========================================================
    // Methods
    // ===========================================================

    /**
     * Apply a custom alpha modifier to an entity
     * 
     * @param pEntity
     *            the entity
     * @param pDuration
     *            the duration of the modifier
     * @param pFromAlpha
     *            the initial alpha
     * @param pToAlpha
     *            the final alpha
     * @param pClearModifiers
     *            determines if cleaned previous entity modifiers
     */
    protected void regAlphaEntityModifier(IEntity pEntity, float pDuration,
	    float pFromAlpha, float pToAlpha, boolean pClearModifiers) {
	// Check if cleaned previous entity modifiers
	if (pClearModifiers) {
	    pEntity.clearEntityModifiers();
	}
	// Apply the modifier
	pEntity.registerEntityModifier(new SequenceEntityModifier(
		new AlphaModifier(pDuration, pFromAlpha, pToAlpha)));
    }

    /**
     * Apply a fade in alpha modifier to an entity
     * 
     * @param pEntity
     *            the entity
     * @param pDuration
     *            the duration of the modifier
     * @param pClearModifiers
     *            determines if cleaned previous entity modifiers
     */
    protected void regFadeInEntityModifier(IEntity pEntity, float pDuration,
	    boolean pClearModifiers) {
	if (pClearModifiers) {
	    pEntity.clearEntityModifiers();
	}
	pEntity.registerEntityModifier(new SequenceEntityModifier(
		new FadeInModifier(pDuration)));
    }

    /**
     * Apply a fade out alpha modifier to an entity
     * 
     * @param pEntity
     *            the entity
     * @param pDuration
     *            the duration of the modifier
     * @param pClearModifiers
     *            determines if cleaned previous entity modifiers
     */
    protected void regFadeOutEntityModifier(IEntity pEntity, float pDuration,
	    boolean pClearModifiers) {
	if (pClearModifiers) {
	    pEntity.clearEntityModifiers();
	}
	pEntity.registerEntityModifier(new SequenceEntityModifier(
		new FadeOutModifier(pDuration)));
    }

    /**
     * Apply a move modifier to an entity
     * 
     * @param pEntity
     *            the entity
     * @param pDuration
     *            the duration of the modifier
     * @param pFromPosX
     *            the initial x position
     * @param pFromPosY
     *            the initial y position
     * @param pToPosX
     *            the final x position
     * @param pToPosY
     *            the final y position
     * @param pClearModifiers
     *            determines if cleaned previous entity modifiers
     */
    protected void regMoveEntityModifier(IEntity pEntity, float pDuration,
	    float pFromPosX, float pFromPosY, float pToPosX, float pToPosY,
	    boolean pClearModifiers) {
	if (pClearModifiers) {
	    pEntity.clearEntityModifiers();
	}
	pEntity.registerEntityModifier(new SequenceEntityModifier(
		new MoveModifier(pDuration, pFromPosX, pFromPosY, pToPosX,
			pToPosY)));
    }

    /**
     * Apply the modifier that comes as a parameter to the entity
     * 
     * @param pEntity
     *            the entity
     * @param pDuration
     *            the duration of the modifier
     * @param pClearModifiers
     *            determines if cleaned previous entity modifiers
     */
    protected void regModifyEntity(IEntity pEntity, IEntityModifier pModifier,
	    boolean pClearModifiers) {
	if (pClearModifiers) {
	    pEntity.clearEntityModifiers();
	}
	pEntity.registerEntityModifier(new SequenceEntityModifier(pModifier));
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}
