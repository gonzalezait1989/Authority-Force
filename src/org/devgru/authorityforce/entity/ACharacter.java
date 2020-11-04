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
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.devgru.authorityforce.manager.GameManager;

import com.badlogic.gdx.physics.box2d.Body;

/**
 * ACharacter.java
 * 
 * This class is an Entity representing any in game character.
 * 
 * @version 1.0 Apr 19, 2013
 * @author Aitor Gonzalez Martinez
 * @author Xavier Martinez de Francisco
 * @author Toni Trigo Diaz
 */
public abstract class ACharacter extends AnimatedSprite {

    // ===========================================================
    // Constants
    // ===========================================================

    /* The movement animation duration. */
    protected static final long[] ANIMATE_DURATION = new long[] { 200, 200,
	    200, 200 };
    /* The Enemy attack animation duration. */
    protected static final long[] ENEMY_ATTACK_DURATION = new long[] { 200,
	    200, 200 };
    /* The Enemy death animation duration. */
    protected static final long[] ENEMY_DEATH_DURATION = new long[] { 200, 200,
	    200, 200, 200, 200 };
    /* The Player death animation duration. */
    protected static final long[] PLAYER_DEATH_DURATION = new long[] { 200,
	    200, 200, 200 };

    // ===========================================================
    // Fields
    // ===========================================================

    /* The Game Manager. */
    protected GameManager mGameManager;

    /* The ID of the Character. */
    protected String mId;
    /* The Health Points of the Character. */
    protected int mHp;
    /* The movement speed of the Character. */
    protected float mMovementSpeed;
    /* The character body, used by the physics. */
    protected Body mCharacterBody;
    /* The time between a physical attack. */
    protected long mTimePhysicalAttack;

    /* The character direction of the Character. */
    protected CharacterDirection mCharacterDirection = CharacterDirection.GROUND;
    /* The shooting direction of the Character. */
    protected CharacterDirection mShootingDirection = CharacterDirection.UP;
    /* The status of the Character. */
    protected CharacterStatus mCharacterStatus = CharacterStatus.DEAD;

    /* A enum that defines the direction of the Character. */
    public enum CharacterDirection {
	GROUND(0), UP(-90), DOWN(90), LEFT(180), RIGHT(0), UP_LEFT(-135), UP_RIGHT(
		-45), DOWN_LEFT(135), DOWN_RIGHT(45);

	private float rotation;

	private CharacterDirection(float rotation) {
	    this.rotation = rotation;
	}

	public float getRotation() {
	    return rotation;
	}
    }

    /* A enum that defines the status of the Character. */
    public enum CharacterStatus {
	MOVE, ATTACK, DEAD, STOP;
    }

    // ===========================================================
    // Constructors
    // ===========================================================

    /**
     * The default constructor of ACharacter.
     * 
     * @param pId
     *            the ID of the Character.
     * @param pX
     *            the position of the Character on the X axis.
     * @param pY
     *            the position of the Character on the Y axis.
     * @param pTiledTextureRegion
     *            the texture used by the Character.
     * @param pVbom
     *            the VertexBuffer.
     */
    public ACharacter(String pId, float pX, float pY,
	    ITiledTextureRegion pTiledTextureRegion,
	    VertexBufferObjectManager pVbom) {

	super(pX, pY, pTiledTextureRegion, pVbom);

	this.mId = pId;
	this.mGameManager = GameManager.getInstance();
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

    public int getHp() {
	return this.mHp;
    }

    public void setHp(int pHp) {
	this.mHp = pHp;
    }

    public float getMovementSpeed() {
	return this.mMovementSpeed;
    }

    public void setMovementSpeed(float pMovementSpeed) {
	this.mMovementSpeed = pMovementSpeed;
    }

    public CharacterDirection getCharacterDirection() {
	return this.mCharacterDirection;
    }

    public CharacterDirection getShootingDirection() {
	return this.mShootingDirection;
    }

    public CharacterStatus getCharacterStatus() {
	return this.mCharacterStatus;
    }

    public void setCharacterStatus(CharacterStatus pCharacterStatus) {
	this.mCharacterStatus = pCharacterStatus;
    }

    public void setTimePhysicalAttack(final long pTimePhysicalAttack) {
	this.mTimePhysicalAttack = pTimePhysicalAttack;
    }

    public Body getCharacterBody() {
	return mCharacterBody;
    }

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    /**
     * Determines when a texture collides with the texture of this entity
     */
    @Override
    public boolean collidesWith(IEntity pOtherEntity) {
	return this.mY <= pOtherEntity.getY() + pOtherEntity.getHeight() / 2
		&& this.mY >= pOtherEntity.getY() - pOtherEntity.getHeight()
			/ 2
		&& this.mX >= pOtherEntity.getX() - pOtherEntity.getWidth() / 2
		&& this.mX <= pOtherEntity.getX() + pOtherEntity.getWidth() / 2;
    }

    /**
     * Performs the action of the Character dying.
     */
    public abstract void die();

    /**
     * Creates the body Physics of the Character.
     * 
     * @param pPhysicsWorld
     */
    public abstract void createPhysics(PhysicsWorld pPhysicsWorld);

    // ===========================================================
    // Methods
    // ===========================================================

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}
