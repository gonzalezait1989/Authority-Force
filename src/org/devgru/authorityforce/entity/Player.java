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

import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.color.Color;
import org.devgru.authorityforce.audio.AudioPlayer;
import org.devgru.authorityforce.constant.GeneralConstantsUtility;
import org.devgru.authorityforce.constant.GeneralConstantsUtility.MAP_ENTITY_TYPE;
import org.devgru.authorityforce.constant.MainParameters;
import org.devgru.authorityforce.controller.SceneController;
import org.devgru.authorityforce.entity.entityaction.IPlayerAction;
import org.devgru.authorityforce.scene.game.GameScene;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

/**
 * Player.java
 * 
 * This class is an Entity representing the in game player's character.
 * 
 * @version 1.0 Apr 19, 2013
 * @author Aitor Gonzalez Martinez
 * @author Xavier Martinez de Francisco
 * @author Toni Trigo Diaz
 */
public class Player extends ACharacter implements IPlayerAction {

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    /* Armor that Player has. */
    private Armor mArmor;
    /* Weapons that Player has. */
    private Weapon[] mWeaponsEquiped;
    /* The initial HP of the player */
    private int mInitialHP;
    /* The ID of the main Weapon */
    private String mMainWeaponID;

    // ===========================================================
    // Constructors
    // ===========================================================

    /**
     * The default constructor of Player.
     * 
     * @param pId
     *            the ID of the Player.
     * @param pX
     *            the position of the Player on the X axis.
     * @param pY
     *            the position of the Player on the Y axis.
     * @param pTiledTextureRegion
     *            the texture used by the Player.
     * @param pVbom
     *            the VertexBuffer.
     */
    public Player(String pId, float pX, float pY,
	    ITiledTextureRegion pTiledTextureRegion,
	    VertexBufferObjectManager pVbom) {

	super(pId, pX, pY, pTiledTextureRegion, pVbom);

	this.mWeaponsEquiped = new Weapon[2];
	this.setZIndex(2);

	// Camera follow player
	MainParameters.camera.setChaseEntity(this);
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    public Armor getArmor() {
	return this.mArmor;
    }

    public void setArmor(Armor pArmor) {
	this.mArmor = pArmor;
    }

    public Weapon getWeapon(int pPos) {
	return this.mWeaponsEquiped[pPos];
    }

    public void addWeapon(Weapon pWeapon, int pPos) {
	this.mWeaponsEquiped[pPos] = pWeapon;
    }

    public int getInitialHP() {
	return this.mInitialHP;
    }

    public void setInitialHP(int pInitialHP) {
	this.mInitialHP = pInitialHP;
    }

    public String getMainWeaponID() {
	return mMainWeaponID;
    }

    public void setMainWeaponID(String mMainWeaponID) {
	this.mMainWeaponID = mMainWeaponID;
    }

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    @Override
    public void createPhysics(PhysicsWorld pPhysicsWorld) {
	this.mCharacterBody = PhysicsFactory.createBoxBody(pPhysicsWorld,
		this.getX(), this.getY(), this.getWidth(), 5,
		BodyType.DynamicBody, PhysicsFactory.createFixtureDef(0, 0, 0));

	this.mCharacterBody.setUserData("player");

	pPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(this,
		this.mCharacterBody, true, false) {
	    @Override
	    public void onUpdate(float pSecondsElapsed) {
		super.onUpdate(pSecondsElapsed);
		MainParameters.camera.onUpdate(0.1f);

		if (this.mUpdatePosition) {
		    final Vector2 position = Player.this.mCharacterBody
			    .getPosition();
		    final float pixelToMeterRatio = this.mPixelToMeterRatio;

		    Player.this.setPosition(position.x * pixelToMeterRatio,
			    position.y * pixelToMeterRatio + 64);
		}
	    }
	});
	this.mCharacterStatus = CharacterStatus.MOVE;
    }

    @Override
    public void move(float pX, float pY) {
	if (this.mCharacterStatus != CharacterStatus.DEAD) {

	    if (pY == 1) {
		if (this.mCharacterDirection != CharacterDirection.UP) {
		    this.animate(ANIMATE_DURATION, 0, 3, true);
		    this.mCharacterDirection = CharacterDirection.UP;
		    this.mShootingDirection = CharacterDirection.UP;
		}

	    } else if (pY == -1) {
		if (this.mCharacterDirection != CharacterDirection.DOWN) {
		    this.animate(ANIMATE_DURATION, 20, 23, true);
		    this.mCharacterDirection = CharacterDirection.DOWN;
		    this.mShootingDirection = CharacterDirection.DOWN;
		}

	    } else if (pX == -1) {
		if (this.mCharacterDirection != CharacterDirection.LEFT) {
		    this.animate(ANIMATE_DURATION, 30, 33, true);
		    this.mCharacterDirection = CharacterDirection.LEFT;
		    this.mShootingDirection = CharacterDirection.LEFT;
		}

	    } else if (pX == 1) {
		if (this.mCharacterDirection != CharacterDirection.RIGHT) {
		    this.animate(ANIMATE_DURATION, 10, 13, true);
		    this.mCharacterDirection = CharacterDirection.RIGHT;
		    this.mShootingDirection = CharacterDirection.RIGHT;
		}

		// Diagonal
	    } else if (pX != 0 && pX < 0) {
		if (pY < 0) {
		    if (this.mCharacterDirection != CharacterDirection.DOWN_LEFT) {
			this.animate(ANIMATE_DURATION, 25, 28, true);
			this.mCharacterDirection = CharacterDirection.DOWN_LEFT;
			this.mShootingDirection = CharacterDirection.DOWN_LEFT;
		    }
		} else {
		    if (this.mCharacterDirection != CharacterDirection.UP_LEFT) {
			this.animate(ANIMATE_DURATION, 35, 38, true);
			this.mCharacterDirection = CharacterDirection.UP_LEFT;
			this.mShootingDirection = CharacterDirection.UP_LEFT;
		    }
		}

	    } else if (pX != 0 && pX < 1) {
		if (pY < 0) {
		    if (this.mCharacterDirection != CharacterDirection.DOWN_RIGHT) {
			this.animate(ANIMATE_DURATION, 15, 18, true);
			this.mCharacterDirection = CharacterDirection.DOWN_RIGHT;
			this.mShootingDirection = CharacterDirection.DOWN_RIGHT;
		    }
		} else {
		    if (this.mCharacterDirection != CharacterDirection.UP_RIGHT) {
			this.animate(ANIMATE_DURATION, 5, 8, true);
			this.mCharacterDirection = CharacterDirection.UP_RIGHT;
			this.mShootingDirection = CharacterDirection.UP_RIGHT;
		    }
		}

		// Stop
	    } else {
		if (this.isAnimationRunning()) {
		    this.stopAnimation();
		    if (this.mCharacterDirection == CharacterDirection.UP) {
			this.setCurrentTileIndex(4);

		    } else if (this.mCharacterDirection == CharacterDirection.DOWN) {
			this.setCurrentTileIndex(24);

		    } else if (this.mCharacterDirection == CharacterDirection.LEFT) {
			this.setCurrentTileIndex(29);

		    } else if (this.mCharacterDirection == CharacterDirection.RIGHT) {
			this.setCurrentTileIndex(14);

		    } else if (this.mCharacterDirection == CharacterDirection.UP_LEFT) {
			this.setCurrentTileIndex(39);

		    } else if (this.mCharacterDirection == CharacterDirection.UP_RIGHT) {
			this.setCurrentTileIndex(9);

		    } else if (this.mCharacterDirection == CharacterDirection.DOWN_LEFT) {
			this.setCurrentTileIndex(29);

		    } else if (this.mCharacterDirection == CharacterDirection.DOWN_RIGHT) {
			this.setCurrentTileIndex(19);
		    }
		    this.mCharacterDirection = CharacterDirection.GROUND;
		}
	    }

	    // Set the player's velocity
	    mCharacterBody.setLinearVelocity(pX * this.mMovementSpeed, pY
		    * this.mMovementSpeed);
	}
    }

    @Override
    public Bullet attack(int pWeaponUsed) {
	if (this.mCharacterStatus != CharacterStatus.DEAD) {
	    boolean generateBullet = false;

	    // Checks main weapon ammo
	    if (this.getWeapon(pWeaponUsed).getId().equals(this.mMainWeaponID)) {
		generateBullet = true;
	    }
	    // Checks primary weapon ammo
	    else if (this.getWeapon(pWeaponUsed) == this.mWeaponsEquiped[GeneralConstantsUtility.CHARACTER_PRIMARY_WEAPON]) {
		// Checks if the weapon ammo is over
		if (this.getWeapon(pWeaponUsed).getAmmoLeft() == 0) {
		    // Changes the weapon for the default main weapon
		    GameScene.mMultiPool.recycleItem(MAP_ENTITY_TYPE.WEAPON,
			    this.getWeapon(pWeaponUsed));
		    Weapon weapon = (Weapon) GameScene.mMultiPool.obtainItem(
			    MAP_ENTITY_TYPE.WEAPON, this.mMainWeaponID, 100,
			    590);
		    this.addWeapon(weapon,
			    GeneralConstantsUtility.CHARACTER_PRIMARY_WEAPON);
		    weapon.setVisible(true);
		    weapon.setScale(0.8f);
		    GameScene.mHud.attachChild(weapon);
		    GameScene.mTextMainWeaponBulletsLeft.setText("∞");
		} else {
		    this.getWeapon(pWeaponUsed).setAmmoLeft(
			    this.getWeapon(pWeaponUsed).getAmmoLeft() - 1);
		    GameScene.mTextMainWeaponBulletsLeft
			    .setText(String.valueOf(this.getWeapon(pWeaponUsed)
				    .getAmmoLeft()));

		}
		generateBullet = true;
	    }
	    // Checks secondary wepaon ammo
	    else {
		// Checks if the weapon ammo is over
		if (this.getWeapon(pWeaponUsed).getAmmoLeft() == 0) {
		    return null;
		} else {
		    this.getWeapon(pWeaponUsed).setAmmoLeft(
			    this.getWeapon(pWeaponUsed).getAmmoLeft() - 1);
		    GameScene.mTextSecondaryWeaponBulletsLeft
			    .setText(String.valueOf(this.getWeapon(pWeaponUsed)
				    .getAmmoLeft()));
		    generateBullet = true;
		}

	    }

	    if (generateBullet) {
		String bulletId = this.getWeapon(pWeaponUsed).getBullet()
			.getId();

		Bullet bullet = (Bullet) GameScene.mMultiPool.obtainItem(
			MAP_ENTITY_TYPE.BULLET, bulletId, this.mX, this.mY);

		bullet.setShooter(GeneralConstantsUtility.PLAYER_SHOOT);

		bullet.setRotation(this.mShootingDirection.getRotation());
		bullet.setCullingEnabled(true);

		final PhysicsHandler physicsHandler = new PhysicsHandler(bullet);
		bullet.registerUpdateHandler(physicsHandler);
		float speed = bullet.getSpeed();

		switch (this.mShootingDirection) {
		case UP:
		    physicsHandler.setVelocity(0, speed);
		    break;
		case UP_RIGHT:
		    physicsHandler.setVelocity(speed, speed);
		    break;
		case RIGHT:
		    physicsHandler.setVelocity(speed, 0);
		    break;
		case DOWN_RIGHT:
		    physicsHandler.setVelocity(speed, -speed);
		    break;
		case DOWN:
		    physicsHandler.setVelocity(0, -speed);
		    break;
		case DOWN_LEFT:
		    physicsHandler.setVelocity(-speed, -speed);
		    break;
		case LEFT:
		    physicsHandler.setVelocity(-speed, 0);
		    break;
		case UP_LEFT:
		    physicsHandler.setVelocity(-speed, speed);
		    break;
		default:
		    break;
		}
		AudioPlayer.getInstance().playSound(
			this.getWeapon(pWeaponUsed).getId());
		return bullet;
	    } else {
	    }
	}
	return null;
    }

    @Override
    public void die() {
	if (this.mCharacterStatus != CharacterStatus.DEAD) {
	    // Animates player with it's death
	    this.stopAnimation();
	    this.mCharacterDirection = CharacterDirection.GROUND;
	    this.mCharacterStatus = CharacterStatus.DEAD;
	    this.animate(PLAYER_DEATH_DURATION, 40, 43, false);
	    this.mCharacterBody.setLinearVelocity(0, 0);
	    this.mCharacterBody.setType(BodyType.StaticBody);

	    MainParameters.engine.registerUpdateHandler(new TimerHandler(2,
		    new ITimerCallback() {
			@Override
			public void onTimePassed(
				final TimerHandler pTimerHandler) {
			    SceneController.getInstance().loadMainMenuScene();
			}
		    }));
	}
    }

    // ===========================================================
    // Methods
    // ===========================================================

    /**
     * Updates the life bar on the HUD according to the Armor and the Health
     * Points of the Player.
     */
    public void updateLifeBar() {
	float barWidth = GameScene.mLifeBar.getWidth();
	float barX = GameScene.mLifeBar.getX();
	Armor armor = GameScene.mPlayer.getArmor();

	if (armor != null) {
	    float armorAvailable = (float) armor.getHitsLeft()
		    / (float) armor.getDefaultHitsLeft();
	    GameScene.mLifeBar.setColor(Color.BLUE);
	    GameScene.mLifeBar
		    .setWidth(GeneralConstantsUtility.HUD_LIFE_BAR_WIDTH
			    * armorAvailable);

	} else {
	    float healthAvailable = (float) this.mHp / (float) this.mInitialHP;

	    if (healthAvailable > 0.5f) {
		GameScene.mLifeBar.setColor(Color.GREEN);
	    } else if (healthAvailable >= 0.5f && healthAvailable > 0.25f) {
		GameScene.mLifeBar.setColor(Color.YELLOW);
	    } else {
		GameScene.mLifeBar.setColor(Color.RED);
	    }
	    if (healthAvailable > 0) {
		GameScene.mLifeBar
			.setWidth(GeneralConstantsUtility.HUD_LIFE_BAR_WIDTH
				* healthAvailable);
	    } else {
		GameScene.mLifeBar.setWidth(0);
	    }
	}

	GameScene.mLifeBar.setX(barX
		- ((barWidth - GameScene.mLifeBar.getWidth()) / 2));
    }

    /**
     * Manages the damage received by the Player.
     * 
     * @param pDamage
     *            the damage received by the Player.
     */
    public void updateLife(final int pDamage) {
	if (this.mArmor != null) {
	    if (this.mArmor.getHitsLeft() - 1 == 0) {
		GameScene.mMultiPool.recycleItem(MAP_ENTITY_TYPE.ARMOR,
			this.mArmor);
		this.mArmor = null;
	    } else {
		this.mHp -= (pDamage - this.mArmor.getDamageReductionBonus());
		this.mArmor.setHitsLeft(this.mArmor.getHitsLeft() - 1);
	    }
	} else {
	    this.mHp -= pDamage;
	}
	if (this.mHp <= 0) {
	    this.die();
	}
	GameScene.mPlayer.updateLifeBar();
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

    // TO DELETE WHEN UP TO ANDENGINE FORUM
    // private class PhysicsConnectorWithOffset extends PhysicsConnector {
    // private final float mOffsetX;
    // private final float mOffsetY;
    //
    // public PhysicsConnectorWithOffset(final IEntity pEntity, final Body
    // pBody, final boolean pUdatePosition,
    // final boolean pUpdateRotation, final float pPixelToMeterRatio, final
    // float pOffsetX,
    // final float pOffsetY) {
    // super(pEntity, pBody, pUdatePosition, pUpdateRotation,
    // pPixelToMeterRatio);
    //
    // this.mOffsetX = pOffsetX;
    // this.mOffsetY = pOffsetY;
    // }
    //
    // @Override
    // public void onUpdate(final float pSecondsElapsed) {
    // final IEntity entity = Player.this;
    // final Body body = this.mBody;
    //
    // if (this.mUpdatePosition) {
    // final Vector2 position = body.getPosition();
    // final float pixelToMeterRatio = this.mPixelToMeterRatio;
    //
    // entity.setPosition(position.x * pixelToMeterRatio - this.mOffsetX,
    // position.y * pixelToMeterRatio
    // - this.mOffsetY);
    // }
    //
    // if (this.mUpdateRotation) {
    // final float angle = body.getAngle();
    // entity.setRotation(MathUtils.radToDeg(angle));
    // }
    // }
    // }
}
