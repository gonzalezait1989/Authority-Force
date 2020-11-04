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
import org.devgru.authorityforce.constant.GeneralConstantsUtility;
import org.devgru.authorityforce.constant.GeneralConstantsUtility.MAP_ENTITY_TYPE;
import org.devgru.authorityforce.constant.MainParameters;
import org.devgru.authorityforce.entity.entityaction.IEnemyAction;
import org.devgru.authorityforce.scene.game.GameScene;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

/**
 * Enemy.java
 * 
 * This class is an Entity representing an in game common enemy.
 * 
 * @version 1.0 Apr 19, 2013
 * @author Aitor Gonzalez Martinez
 * @author Xavier Martinez de Francisco
 * @author Toni Trigo Diaz
 */
public class Enemy extends ACharacter implements IEnemyAction {

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    /* Score given when the enemy is dead. */
    private int mGivenScore;
    /* The Enemy weapon. */
    private Weapon mWeapon;
    /* The last attack time done by the enemy. */
    private long mLastAttackTime = 0;

    // ===========================================================
    // Constructors
    // ===========================================================

    /**
     * The default constructor of Enemy.
     * 
     * @param pId
     *            the ID of the Enemy.
     * @param pX
     *            the position of the Enemy on the X axis.
     * @param pY
     *            the position of the Enemy on the Y axis.
     * @param pTiledTextureRegion
     *            the texture used by the Enemy.
     * @param pVbom
     *            the VertexBuffer.
     */
    public Enemy(String pId, float pX, float pY,
	    ITiledTextureRegion pTiledTextureRegion,
	    VertexBufferObjectManager pVbom) {

	super(pId, pX, pY, pTiledTextureRegion, pVbom);

	this.setZIndex(2);
    }

    /**
     * The default constructor of Enemy.
     * 
     * @param pId
     *            the ID of the Enemy.
     * @param pX
     *            the position of the Enemy on the X axis.
     * @param pY
     *            the position of the Enemy on the Y axis.
     * @param pTiledTextureRegion
     *            the texture used by the Enemy.
     * @param pVbom
     *            the VertexBuffer.
     * @param pGivenScore
     *            the score that the enemy gives to the player when dies.
     */
    public Enemy(String pId, float pX, float pY,
	    ITiledTextureRegion pTiledTextureRegion,
	    VertexBufferObjectManager pVbom, int pGivenScore) {

	super(pId, pX, pY, pTiledTextureRegion, pVbom);

	this.setZIndex(2);
	this.mGivenScore = pGivenScore;
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    public int getGivenScore() {
	return this.mGivenScore;
    }

    public void setGivenScore(int pGivenScore) {
	this.mGivenScore = pGivenScore;
    }

    public Weapon getWeapon() {
	return this.mWeapon;
    }

    public void setWeapon(Weapon pWeapon) {
	this.mWeapon = pWeapon;
    }

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    @Override
    public void createPhysics(PhysicsWorld pPhysicsWorld) {

	this.mCharacterBody = PhysicsFactory.createBoxBody(pPhysicsWorld,
		this.getX(), this.getY(), this.getWidth(), 5,
		BodyType.DynamicBody, PhysicsFactory.createFixtureDef(0, 0, 0));

	this.mCharacterBody.setUserData("enemy");

	pPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(this,
		this.mCharacterBody, true, false) {
	    @Override
	    public void onUpdate(float pSecondsElapsed) {
		if (this.mUpdatePosition) {
		    final Vector2 position = Enemy.this.mCharacterBody
			    .getPosition();
		    final float pixelToMeterRatio = this.mPixelToMeterRatio;

		    Enemy.this.setPosition(position.x * pixelToMeterRatio,
			    position.y * pixelToMeterRatio + 64);
		}
	    }
	});
	this.mCharacterStatus = CharacterStatus.MOVE;
	// Log.e("Enemy: ", "Physics ID: " + this.mId);
    }

    @Override
    public void attack() {
	long now = System.currentTimeMillis();
	// Log.e("Enemy", "attack");
	if (now >= this.mLastAttackTime + this.mTimePhysicalAttack) {
	    if (this.mWeapon.getBullet().getRange() <= 70) {
		GameScene.mPlayer.updateLife(this.mWeapon.getBullet()
			.getDamage());

	    } else {
		String bulletId = this.mWeapon.getBullet().getId();
		Bullet bullet = (Bullet) GameScene.mMultiPool.obtainItem(
			MAP_ENTITY_TYPE.BULLET, bulletId, this.mX, this.mY);

		bullet.setRotation(this.mShootingDirection.getRotation());
		bullet.setCullingEnabled(true);

		bullet.setShooter(GeneralConstantsUtility.ENEMY_SHOOT);

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
		GameScene scene = (GameScene) this.getParent();
		scene.attachBullet(bullet);

	    }
	    this.mLastAttackTime = now;
	}
    }

    @Override
    public void behavior() {
	// Log.e("Enemy", "Move ID: " + this.mId);
	if (this.mCharacterStatus != CharacterStatus.DEAD
		&& this.mCharacterStatus != CharacterStatus.STOP) {
	    int x = 0;
	    int y = 0;

	    int enemyX = Math.round(this.getX());
	    int enemyY = Math.round(this.getY());
	    int playerX = Math.round(GameScene.mPlayer.getX());
	    int playerY = Math.round(GameScene.mPlayer.getY());
	    float rangeAttack = this.mWeapon.getBullet().getRange();

	    if ((enemyX <= playerX + 5 && enemyX >= playerX - 5)
		    && enemyY < playerY) {
		if (this.mCharacterStatus == CharacterStatus.MOVE) {
		    if (this.mCharacterDirection != CharacterDirection.UP) {
			this.animate(ANIMATE_DURATION, 0, 3, true);
			this.mCharacterDirection = CharacterDirection.UP;
			this.mShootingDirection = CharacterDirection.UP;
		    }
		    x = 0;
		    y = 4;
		    this.mCharacterBody.setLinearVelocity(x, y);

		}
		if (playerY - enemyY >= 0 && playerY - enemyY <= rangeAttack) {
		    if (this.mCharacterStatus != CharacterStatus.ATTACK) {
			this.mCharacterBody.setLinearVelocity(0, 0);
			this.animate(ENEMY_ATTACK_DURATION, 4, 6, true);
			this.mCharacterStatus = CharacterStatus.ATTACK;
		    }
		    this.attack();
		} else {
		    this.mCharacterStatus = CharacterStatus.MOVE;
		}

	    } else if ((enemyX <= playerX + 5 && enemyX >= playerX - 5)
		    && enemyY > playerY) {
		if (this.mCharacterStatus == CharacterStatus.MOVE) {
		    if (this.mCharacterDirection != CharacterDirection.DOWN) {
			this.animate(ANIMATE_DURATION, 28, 31, true);
			this.mCharacterDirection = CharacterDirection.DOWN;
			this.mShootingDirection = CharacterDirection.DOWN;
		    }
		    x = 0;
		    y = -4;
		    this.mCharacterBody.setLinearVelocity(x, y);
		}
		if (enemyY - playerY >= 0 && enemyY - playerY <= rangeAttack) {
		    if (this.mCharacterStatus != CharacterStatus.ATTACK) {
			this.mCharacterBody.setLinearVelocity(0, 0);
			this.animate(ENEMY_ATTACK_DURATION, 32, 34, true);
			this.mCharacterStatus = CharacterStatus.ATTACK;
		    }
		    this.attack();
		} else {
		    this.mCharacterStatus = CharacterStatus.MOVE;
		}

	    } else if ((enemyY <= playerY + 5 && enemyY >= playerY - 5)
		    && enemyX > playerX) {
		if (this.mCharacterStatus == CharacterStatus.MOVE) {
		    if (this.mCharacterDirection != CharacterDirection.LEFT) {
			this.animate(ANIMATE_DURATION, 42, 45, true);
			this.mCharacterDirection = CharacterDirection.LEFT;
			this.mShootingDirection = CharacterDirection.LEFT;
		    }
		    x = -4;
		    y = 0;
		    this.mCharacterBody.setLinearVelocity(x, y);
		}
		if (enemyX - playerX >= 0 && enemyX - playerX <= rangeAttack) {
		    if (this.mCharacterStatus != CharacterStatus.ATTACK) {
			this.mCharacterBody.setLinearVelocity(0, 0);
			this.animate(ENEMY_ATTACK_DURATION, 46, 48, true);
			this.mCharacterStatus = CharacterStatus.ATTACK;
		    }
		    this.attack();
		} else {
		    this.mCharacterStatus = CharacterStatus.MOVE;
		}

	    } else if ((enemyY <= playerY + 5 && enemyY >= playerY - 5)
		    && enemyX < playerX) {
		if (this.mCharacterStatus == CharacterStatus.MOVE) {
		    if (this.mCharacterDirection != CharacterDirection.RIGHT) {
			this.animate(ANIMATE_DURATION, 14, 17, true);
			this.mCharacterDirection = CharacterDirection.RIGHT;
			this.mShootingDirection = CharacterDirection.RIGHT;
		    }
		    x = 4;
		    y = 0;
		    this.mCharacterBody.setLinearVelocity(x, y);
		}
		if (playerX - enemyX >= 0 && playerX - enemyX <= rangeAttack) {
		    if (this.mCharacterStatus != CharacterStatus.ATTACK) {
			this.mCharacterBody.setLinearVelocity(0, 0);
			this.animate(ENEMY_ATTACK_DURATION, 18, 20, true);
			this.mCharacterStatus = CharacterStatus.ATTACK;
		    }
		    this.attack();
		} else {
		    this.mCharacterStatus = CharacterStatus.MOVE;
		}

	    } else if (enemyX < playerX && enemyY < playerY) {
		if (this.mCharacterStatus == CharacterStatus.MOVE) {
		    if (this.mCharacterDirection != CharacterDirection.UP_RIGHT) {
			this.animate(ANIMATE_DURATION, 7, 10, true);
			this.mCharacterDirection = CharacterDirection.UP_RIGHT;
			this.mShootingDirection = CharacterDirection.UP_RIGHT;
		    }
		    x = 4;
		    y = 4;
		    this.mCharacterBody.setLinearVelocity(x, y);
		}
		if (playerY - enemyY <= rangeAttack
			&& playerX - enemyX <= rangeAttack) {
		    if (this.mCharacterStatus != CharacterStatus.ATTACK) {
			this.mCharacterBody.setLinearVelocity(0, 0);
			this.animate(ENEMY_ATTACK_DURATION, 11, 13, true);
			this.mCharacterStatus = CharacterStatus.ATTACK;
		    }
		    this.attack();
		} else {
		    this.mCharacterStatus = CharacterStatus.MOVE;
		}

	    } else if (enemyX < playerX && enemyY > playerY) {
		if (this.mCharacterStatus == CharacterStatus.MOVE) {
		    if (this.mCharacterDirection != CharacterDirection.DOWN_RIGHT) {
			this.animate(ANIMATE_DURATION, 21, 24, true);
			this.mCharacterDirection = CharacterDirection.DOWN_RIGHT;
			this.mShootingDirection = CharacterDirection.DOWN_RIGHT;
		    }
		    x = 4;
		    y = -4;
		    this.mCharacterBody.setLinearVelocity(x, y);
		}
		if (enemyY - playerY <= rangeAttack
			&& playerX - enemyX <= rangeAttack) {
		    if (this.mCharacterStatus != CharacterStatus.ATTACK) {
			this.mCharacterBody.setLinearVelocity(0, 0);
			this.animate(ENEMY_ATTACK_DURATION, 25, 27, true);
			this.mCharacterStatus = CharacterStatus.ATTACK;
		    }
		    this.attack();
		} else {
		    this.mCharacterStatus = CharacterStatus.MOVE;
		}

	    } else if (enemyX > playerX && enemyY < playerY) {
		if (this.mCharacterStatus == CharacterStatus.MOVE) {
		    if (this.mCharacterDirection != CharacterDirection.UP_LEFT) {
			this.animate(ANIMATE_DURATION, 49, 52, true);
			this.mCharacterDirection = CharacterDirection.UP_LEFT;
			this.mShootingDirection = CharacterDirection.UP_LEFT;
		    }
		    x = -4;
		    y = 4;
		    this.mCharacterBody.setLinearVelocity(x, y);
		}
		if (playerY - enemyY <= rangeAttack
			&& enemyX - playerX <= rangeAttack) {
		    if (this.mCharacterStatus != CharacterStatus.ATTACK) {
			this.mCharacterBody.setLinearVelocity(0, 0);
			this.animate(ENEMY_ATTACK_DURATION, 52, 54, true);
			this.mCharacterStatus = CharacterStatus.ATTACK;
		    }
		    this.attack();
		} else {
		    this.mCharacterStatus = CharacterStatus.MOVE;
		}

	    } else if (enemyX > playerX && enemyY > playerY) {
		if (this.mCharacterStatus == CharacterStatus.MOVE) {
		    if (this.mCharacterDirection != CharacterDirection.DOWN_LEFT) {
			this.animate(ANIMATE_DURATION, 35, 38, true);
			this.mCharacterDirection = CharacterDirection.DOWN_LEFT;
			this.mShootingDirection = CharacterDirection.DOWN_LEFT;
		    }
		    x = -4;
		    y = -4;
		    this.mCharacterBody.setLinearVelocity(x, y);
		}
		if (enemyY - playerY <= rangeAttack
			&& enemyX - playerX <= rangeAttack) {
		    if (this.mCharacterStatus != CharacterStatus.ATTACK) {
			this.mCharacterBody.setLinearVelocity(0, 0);
			this.animate(ENEMY_ATTACK_DURATION, 39, 41, true);
			this.mCharacterStatus = CharacterStatus.ATTACK;
		    }
		    this.attack();
		} else {
		    this.mCharacterStatus = CharacterStatus.MOVE;
		}
	    }
	}
	if (GameScene.mPlayer.getCharacterStatus() == CharacterStatus.DEAD) {
	    if (this.isAnimationRunning()) {
		this.stopAnimation();
		this.mCharacterBody.setLinearVelocity(0, 0);
		this.mCharacterStatus = CharacterStatus.STOP;
	    }
	}
    }

    @Override
    public void die() {
	if (this.mCharacterStatus != CharacterStatus.DEAD) {
	    GameScene.mCharacters.remove(this);
	    // Sets new Score
	    int givenScore = this.getGivenScore();
	    int currentScore = this.mGameManager.getmScore();
	    this.mGameManager.setmScore(currentScore + givenScore);
	    GameScene.mTextScore.setText(String.valueOf(this.mGameManager
		    .getmScore()));
	    // Animates enemy with it's death
	    this.stopAnimation();
	    this.mCharacterDirection = CharacterDirection.GROUND;
	    this.mCharacterStatus = CharacterStatus.DEAD;
	    this.animate(ENEMY_DEATH_DURATION, 56, 61, false);
	    this.mCharacterBody.setLinearVelocity(0, 0);
	    this.mCharacterBody.setType(BodyType.StaticBody);
	    this.mCharacterBody.setActive(false);
	    MainParameters.engine.registerUpdateHandler(new TimerHandler(2,
		    new ITimerCallback() {
			@Override
			public void onTimePassed(
				final TimerHandler pTimerHandler) {

			    // First unregister the UpdateHandler
			    MainParameters.engine
				    .unregisterUpdateHandler(pTimerHandler);
			    GameScene.mMultiPool.recycleItem(
				    MAP_ENTITY_TYPE.ENEMY, Enemy.this);
			}
		    }));

	}
    }

    // ===========================================================
    // Methods
    // ===========================================================

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}
