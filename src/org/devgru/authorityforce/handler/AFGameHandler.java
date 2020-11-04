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

package org.devgru.authorityforce.handler;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.primitive.Rectangle;
import org.devgru.authorityforce.constant.GeneralConstantsUtility;
import org.devgru.authorityforce.constant.GeneralConstantsUtility.MAP_ENTITY_TYPE;
import org.devgru.authorityforce.constant.MainParameters;
import org.devgru.authorityforce.controller.SceneController;
import org.devgru.authorityforce.entity.ACharacter;
import org.devgru.authorityforce.entity.AFTrigger;
import org.devgru.authorityforce.entity.AMapObject;
import org.devgru.authorityforce.entity.Armor;
import org.devgru.authorityforce.entity.BonusItem;
import org.devgru.authorityforce.entity.Bullet;
import org.devgru.authorityforce.entity.Enemy;
import org.devgru.authorityforce.entity.Player;
import org.devgru.authorityforce.entity.Weapon;
import org.devgru.authorityforce.manager.GameManager;
import org.devgru.authorityforce.scene.game.GameScene;

import android.util.Log;

/**
 * AFGameHandler.java
 * 
 * This class manages the events on every game update. Mainly controls
 * collisions and all the operations done depending on the collided object.
 * 
 * @version 1.0 May 16, 2013
 * @author Aitor Gonzalez Martinez
 * @author Xavier Martinez de Francisco
 * @author Toni Trigo Diaz
 */
public class AFGameHandler implements IUpdateHandler {

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    private GameScene mGameScene;

    // ===========================================================
    // Constructors
    // ===========================================================

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    public void setGameScene(GameScene pScene) {
	this.mGameScene = pScene;
    }

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    @Override
    public void onUpdate(float pSecondsElapsed) {
	// Check player collision with map objects.
	this.checkPlayerCollidesObjects();
	// Check bullet impacts.
	this.checkBulletImpacts();
	// Make the characters do their actions.
	this.enemyBehavior();
    }

    @Override
    public void reset() {
    }

    // ===========================================================
    // Methods
    // ===========================================================

    /**
     * Check the collision os the player with map ibjects in the game.
     */
    private synchronized void checkPlayerCollidesObjects() {
	// Iterate game scene objects list.
	for (AMapObject mapObject : GameScene.mObjects) {
	    // Checks if player collides with the object being checked.
	    if (GameScene.mPlayer.collidesWith(mapObject)) {
		if (mapObject instanceof Weapon) {
		    this.playerCollidesWeapon((Weapon) mapObject);
		} else if (mapObject instanceof Armor) {
		    this.playerCollidesArmor((Armor) mapObject);
		} else {
		    this.playerCollidesBonusItem((BonusItem) mapObject);
		}
	    }
	}

	for (AFTrigger trigger : GameScene.mTriggers) {

	    if (GameScene.mPlayer.collidesWith(trigger)
		    && !trigger.isIgnoreUpdate()) {
		trigger.setIgnoreUpdate(true);
		Log.e("TRIGGER POSITIONS", "Pos_X: " + trigger.getX()
			+ "| Pos_Y: " + trigger.getY());
		if (trigger.isMapEnd()) {
		    MainParameters.engine
			    .registerUpdateHandler(new TimerHandler(2,
				    new ITimerCallback() {

					@Override
					public void onTimePassed(
						TimerHandler pTimerHandler) {
					    SceneController.getInstance()
						    .loadNextScene();
					}
				    }));
		} else {
		    this.mGameScene.loadEntitiesIntoMap(trigger.getX() + "x"
			    + trigger.getY());
		}
		break;
	    }
	}
    }

    /**
     * Check Game Scene current bullet impacts.
     */
    private synchronized void checkBulletImpacts() {
	boolean hasCollided = false;
	// Iterate bullets attached to the Game Scene.
	for (Bullet bullet : GameScene.mBullets) {
	    hasCollided = false;
	    // For each bullet iterate characters attached to the Game Scene.
	    for (ACharacter character : GameScene.mCharacters) {
		// If the bullet collides with a character...
		if (bullet.collidesWith(character)) {
		    hasCollided = true;
		    // Get character's current health.
		    int characterCurrentHealth = character.getHp();
		    // Get colliding bullet damage.
		    int bulletDamage = bullet.getDamage();
		    // If the collided character is the player...
		    if (character instanceof Player) {
			// Update the player's hp.
			GameScene.mPlayer.updateLife(bullet.getDamage());
		    }
		    // If the character collided enemy is an Enemy...
		    else if (character instanceof Enemy) {
			// Updates enemy hp.
			character.setHp(characterCurrentHealth - bulletDamage);
			// Checks if the character is an enemy and is dead.
			if (character.getHp() <= 0) {
			    // Make the enemy die.
			    character.die();
			    // Remove the enemy from GameScene objects.
			    GameScene.mCharacters.remove(character);
			}
		    }
		}
	    }

	    /*
	     * Bullet hasn't collided with a character, check rectangle
	     * collisions.
	     */
	    if (!hasCollided) {
		// Checks if the bullet collides a collision rectangle
		for (Rectangle rectangle : GameScene.mCollisions) {
		    /*
		     * If the bullet collides with a collision point change flag
		     * value and break the iteration.
		     */
		    if (bullet.collidesWith(rectangle)) {
			hasCollided = true;
			break;
		    }
		}
	    }

	    /*
	     * If the bullet has collided with anything, remove it from the
	     * GameScene objects list and recycle it.
	     */
	    if (hasCollided) {
		GameScene.mBullets.remove(bullet);
		GameScene.mMultiPool
			.recycleItem(MAP_ENTITY_TYPE.BULLET, bullet);
	    }
	}
    }

    /**
     * Make every enemy move and attack.
     */
    private synchronized void enemyBehavior() {
	// Iterate characters in the GaeScene.
	for (ACharacter character : GameScene.mCharacters) {
	    // If the character is an enemy make it move or attack.
	    if (character instanceof Enemy) {
		((Enemy) character).behavior();
	    }
	}
    }

    /**
     * Perform needed operations when the player collides with a weapon in the
     * GameScene. If this happens, sets the picked weapon to the player and
     * recycles the old player weapon. Also makes the picked weapon to disappear
     * from the scene.
     * 
     * @param pWeapon
     *            Weapon object picked up from the ground.
     */
    private void playerCollidesWeapon(Weapon pWeapon) {
	// Get the player weapon.
	Weapon weapon = GameScene.mPlayer
		.getWeapon(GeneralConstantsUtility.CHARACTER_PRIMARY_WEAPON);
	// Add the collided weapon to the player as primary weapon.
	GameScene.mPlayer.addWeapon(pWeapon,
		GeneralConstantsUtility.CHARACTER_PRIMARY_WEAPON);
	// Recycle the player weapon.
	GameScene.mMultiPool.recycleItem(MAP_ENTITY_TYPE.WEAPON, weapon);
	/*
	 * Update the hud bullets left text with the new weapon bullets left
	 * value.
	 */
	GameScene.mTextMainWeaponBulletsLeft.setText(String.valueOf(pWeapon
		.getAmmoLeft()));
	// Remove the weapon from the game scene objects.
	GameScene.mObjects.remove(pWeapon);
	// Detach the new weapon from the scene.
	pWeapon.detachSelf();
	// Set the new position to the weapon to be attached to the
	// hud.
	pWeapon.setPosition(100, 590);
	// Rescale the new weapon to fit the hud.
	pWeapon.setScale(0.8f);
	// Attach the new weapon to the hud.
	GameScene.mHud.attachChild(pWeapon);
    }

    /**
     * Perform needed operations when the player collides with an armor in the
     * GameScene. If this happens, sets the picked armor to the player and
     * recycles the old player armor if it already had one. Also makes the
     * picked armor to disappear from the scene.
     * 
     * @param pArmor
     *            Armor object picked up from the ground.
     */
    private void playerCollidesArmor(Armor pArmor) {
	// If the player has an armor...
	if (GameScene.mPlayer.getArmor() != null) {
	    // Recycle player's armor.
	    GameScene.mMultiPool.recycleItem(MAP_ENTITY_TYPE.ARMOR,
		    GameScene.mPlayer.getArmor());
	}
	// Remove the armor from the list containing the game
	// scene objects.
	GameScene.mObjects.remove(pArmor);
	// Sets the new armor to the player.
	GameScene.mPlayer.setArmor(pArmor);
	// Updates the player life bar.
	GameScene.mPlayer.updateLifeBar();
	// Sets the map object invisible.
	pArmor.setVisible(false);
	pArmor.setIgnoreUpdate(true);
    }

    /**
     * Perform needed operations when the player collides with a bonus item in
     * the GameScene. If this happens, modify the player attributes and recycles
     * the bonus item.
     * 
     * @param pBonusItem
     *            BonusItem picked up from the ground.
     */
    private void playerCollidesBonusItem(BonusItem pBonusItem) {

	// ///////////// HP BONUS ///////////////

	// Add the bonus item hp bonus to player's hp.
	if (GameScene.mPlayer.getHp() + pBonusItem.getHpBonus() >= GameScene.mPlayer
		.getInitialHP()) {
	    GameScene.mPlayer.setHp(GameScene.mPlayer.getInitialHP());
	} else {
	    GameScene.mPlayer.setHp(GameScene.mPlayer.getHp()
		    + pBonusItem.getHpBonus());
	}
	// Updates player life bar.
	GameScene.mPlayer.updateLifeBar();

	// ///////////// AMMO BONUS ///////////////

	// Gets the player main weapon ammo left and the maximum weapon ammo.
	int currentAmmo = GameScene.mPlayer.getWeapon(
		GeneralConstantsUtility.CHARACTER_PRIMARY_WEAPON).getAmmoLeft();
	int maxAmmo = GameScene.mPlayer.getWeapon(
		GeneralConstantsUtility.CHARACTER_PRIMARY_WEAPON)
		.getDefaultAmmoLeft();
	// Sum bonus item ammo bonus to player's weapon.
	if (currentAmmo + pBonusItem.getExtraAmmo() > maxAmmo) {
	    GameScene.mPlayer.getWeapon(
		    GeneralConstantsUtility.CHARACTER_PRIMARY_WEAPON)
		    .setAmmoLeft(maxAmmo);
	} else {
	    GameScene.mPlayer.getWeapon(
		    GeneralConstantsUtility.CHARACTER_PRIMARY_WEAPON)
		    .setAmmoLeft(currentAmmo + pBonusItem.getExtraAmmo());
	}

	/*
	 * If player's primary weapon is not it's default main weapon...
	 */
	if (!GameScene.mPlayer
		.getWeapon(GeneralConstantsUtility.CHARACTER_PRIMARY_WEAPON)
		.getId().equals(GameScene.mPlayer.getMainWeaponID())) {
	    /*
	     * Sets the bullets left text in the hud with the new ammo left
	     * value.
	     */
	    GameScene.mTextMainWeaponBulletsLeft.setText(String
		    .valueOf(GameScene.mPlayer.getWeapon(
			    GeneralConstantsUtility.CHARACTER_PRIMARY_WEAPON)
			    .getAmmoLeft()));
	}

	// Gets the current game score.
	int currentScore = GameManager.getInstance().getmScore();
	// Sets the new score applying the bonus item modifier.
	GameManager.getInstance().setmScore(
		currentScore * pBonusItem.getScoreMultiplier());

	// ///////////// SCORE MULTIPLIER BONUS ///////////////

	// Remove the bonus item from game scene objects list.
	GameScene.mObjects.remove(pBonusItem);
	// Recycle the bonus item.
	GameScene.mMultiPool
		.recycleItem(MAP_ENTITY_TYPE.BONUS_ITEM, pBonusItem);
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}
