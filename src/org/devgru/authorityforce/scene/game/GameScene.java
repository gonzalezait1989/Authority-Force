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

package org.devgru.authorityforce.scene.game;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.microedition.khronos.opengles.GL10;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl.IOnScreenControlListener;
import org.andengine.engine.camera.hud.controls.DigitalOnScreenControl;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.util.FPSCounter;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.tmx.TMXLayer;
import org.andengine.extension.tmx.TMXObject;
import org.andengine.extension.tmx.TMXObjectGroup;
import org.andengine.extension.tmx.TMXTiledMap;
import org.andengine.util.adt.color.Color;
import org.devgru.authorityforce.constant.GeneralConstantsUtility;
import org.devgru.authorityforce.constant.GeneralConstantsUtility.MAP_ENTITY_TYPE;
import org.devgru.authorityforce.constant.GeneralConstantsUtility.SceneType;
import org.devgru.authorityforce.constant.JsonFieldsConstantsUtility;
import org.devgru.authorityforce.constant.MainParameters;
import org.devgru.authorityforce.constant.MenuConstantsUtility;
import org.devgru.authorityforce.dao.EntityDAO;
import org.devgru.authorityforce.entity.ACharacter;
import org.devgru.authorityforce.entity.AFTrigger;
import org.devgru.authorityforce.entity.AMapObject;
import org.devgru.authorityforce.entity.Armor;
import org.devgru.authorityforce.entity.Bullet;
import org.devgru.authorityforce.entity.Enemy;
import org.devgru.authorityforce.entity.Player;
import org.devgru.authorityforce.entity.Weapon;
import org.devgru.authorityforce.handler.AFGameHandler;
import org.devgru.authorityforce.manager.scene.GameResourceManager;
import org.devgru.authorityforce.pool.AFMultiPool;
import org.devgru.authorityforce.scene.ABaseScene;
import org.devgru.authorityforce.util.EntityMessenger;
import org.json.JSONException;
import org.json.JSONObject;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

/**
 * GameScene.java
 * 
 * This class is the scene where the player can play.
 * 
 * @version 1.0 May 1, 2013
 * @author Aitor Gonzalez Martinez
 * @author Xavier Martinez de Francisco
 * @author Toni Trigo Diaz
 */
public class GameScene extends ABaseScene implements OnClickListener {

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    /* GameResourceManager Instance to get the needed resources. */
    private GameResourceManager mGameResourceManger;
    /* EntityDAO Instance to get the needed data. */
    private EntityDAO mEntityDao;
    /* Multipool used to get the needed map entities. */
    public static AFMultiPool mMultiPool;
    /* The Status Monitor handler. */
    private TimerHandler mMonitorTimeHandler;
    /* The game Map. */
    private TMXTiledMap mTMXTiledMap;
    /* The game physics. */
    public static PhysicsWorld mPhysicsWorld;
    /* The game Hud. */
    public static HUD mHud;
    /* The hud pad. */
    private DigitalOnScreenControl mDigitalOnScreenControl;
    /* Button to shoot the player's primary weapon. */
    private ButtonSprite mButtonFirstAction;
    /* Button to shoot the player's secondary weapon. */
    private ButtonSprite mButtonSecondaryAction;
    /* Hud's life bar. */
    public static Rectangle mLifeBar;
    /* The FPS text. */
    private Text mTextFPS;
    /* The CPU usage text. */
    private Text mTextCPU;
    /* The Hud character's face. */
    private TiledSprite mCharacterFace;
    /** The score text. */
    public static Text mTextScore;
    /** The main weapon bullets left text. */
    public static Text mTextMainWeaponBulletsLeft;
    /** The secondary weapon bullets left text. */
    public static Text mTextSecondaryWeaponBulletsLeft;
    /** The player object */
    public static Player mPlayer;
    /** A List containing all the characters attached to the scene. */
    public static List<ACharacter> mCharacters;
    /**
     * A List containing all Armors, Weapons and Bonus items attached to the
     * scene.
     */
    public static List<AMapObject> mObjects;
    /** A List containing all the bullets attached to the scene. */
    public static List<Bullet> mBullets;
    /** A List containing all the collisions attached to the scene. */
    public static List<Rectangle> mCollisions;
    /** A List containing all Triggers attached to the scene. */
    public static List<AFTrigger> mTriggers;

    // ===========================================================
    // Constructors
    // ===========================================================

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    // LAYERS
    // MAP = 0
    // COLLISION = 0
    // TRIGGER = 0
    // MAPOBJECT = 1
    // PLAYER = 2
    // ENEMIES = 2
    // BULLETS = 2
    // CROSS = 3

    @Override
    public void createScene() {

	/*
	 * Gets a GameResourceManager instance, used to get the needed resources
	 * by the game.
	 */
	this.mGameResourceManger = GameResourceManager.getInstance();
	/*
	 * Gets an EntityDao instance, used to get the needed map entities data.
	 */
	this.mEntityDao = EntityDAO.getInstance();
	// Loads into EntityDao the needed resources data.
	this.mEntityDao.readIndexMapFile();
	// Load into EntityDao the player's data.
	this.mEntityDao.readPlayersData();
	// Load into EntityDao the bullet's's data.
	this.mEntityDao.readBulletsData();
	// Loads the data of the Entities
	this.mEntityDao.readEntitiesFromJSON();
	// Initializes the needed lists.
	GameScene.mCharacters = new CopyOnWriteArrayList<ACharacter>();
	GameScene.mBullets = new CopyOnWriteArrayList<Bullet>();
	GameScene.mObjects = new CopyOnWriteArrayList<AMapObject>();
	GameScene.mCollisions = new ArrayList<Rectangle>();
	GameScene.mTriggers = new ArrayList<AFTrigger>();

	// Initializes the multipool to get entities.
	GameScene.mMultiPool = new AFMultiPool(GameScene.mPhysicsWorld);

	// Creates the world physics.
	this.createWorldPhysics();
	// Creates the game map.
	this.createMap();
	// Creates the player.
	this.createPlayer();
	// Creates the Hud.
	this.createHUD();
	// Creates the monitor logger.
	this.createMonitorLogger();

	// Sorts game scene children to make them display in their correct
	// index.
	this.sortChildren();

	// Setss the master volume.
	this.mAudioPlayer.setVolumeMaster();
	this.mAudioPlayer.prepareAudioFXPool();

	// Starts the GameHandler and register it to the scene.
	AFGameHandler gameHandler = new AFGameHandler();
	gameHandler.setGameScene(this);
	this.registerUpdateHandler(gameHandler);
    }

    @Override
    public void onBackKeyPressed() {
	// If the device back key is pressed go back to main menu.
	this.mSceneController.loadMainMenuScene();
    }

    @Override
    public SceneType getSceneType() {
	return SceneType.GAME_SCENE;
    }

    @Override
    public void disposeScene() {
	// Runs the detach and dispose operations on a different Thread
	MainParameters.engine.runOnUpdateThread(new Runnable() {
	    @Override
	    public void run() {
		unregisterUpdateHandler(mMonitorTimeHandler);

		// Clear collisions.
		for (Rectangle rectangle : mCollisions) {
		    rectangle.detachSelf();
		    rectangle = null;
		}

		mCollisions.clear();
		mCollisions = null;

		// Clear triggeers.
		for (Rectangle rectangle : mTriggers) {
		    rectangle.detachSelf();
		    rectangle = null;
		}

		mTriggers.clear();
		mTriggers = null;

		// Clear charcters.
		for (ACharacter character : GameScene.mCharacters) {
		    if (character instanceof Enemy) {
			GameScene.mMultiPool.recycleItem(MAP_ENTITY_TYPE.ENEMY,
				character);
		    }
		}

		GameScene.mCharacters.clear();
		GameScene.mCharacters = null;

		// Clear map objects (Armors, Weapons and BonusItems).
		for (AMapObject object : GameScene.mObjects) {
		    if (object instanceof Armor) {
			GameScene.mMultiPool.recycleItem(MAP_ENTITY_TYPE.ARMOR,
				object);
		    } else if (object instanceof Weapon) {
			GameScene.mMultiPool.recycleItem(
				MAP_ENTITY_TYPE.WEAPON, object);
		    } else {
			GameScene.mMultiPool.recycleItem(
				MAP_ENTITY_TYPE.BONUS_ITEM, object);
		    }
		}

		GameScene.mObjects.clear();
		GameScene.mObjects = null;

		// Clear Bullets.
		for (Bullet bullet : GameScene.mBullets) {
		    GameScene.mMultiPool.recycleItem(MAP_ENTITY_TYPE.BULLET,
			    bullet);
		}

		GameScene.mBullets.clear();
		GameScene.mBullets = null;

		// Detach and clear the game scene resources.
		MainParameters.camera.setHUD(null);

		mTMXTiledMap.detachSelf();
		mDigitalOnScreenControl.detachSelf();
		mTextFPS.detachSelf();
		mTextCPU.detachSelf();
		mTextMainWeaponBulletsLeft.detachSelf();
		mTextSecondaryWeaponBulletsLeft.detachSelf();
		mTextScore.detachSelf();
		mCharacterFace.detachSelf();
		mHud.detachChildren();
		mHud.detachSelf();

		mPhysicsWorld.dispose();
		mTMXTiledMap.dispose();
		mDigitalOnScreenControl.dispose();
		mTextFPS.dispose();
		mTextCPU.dispose();
		mTextMainWeaponBulletsLeft.dispose();
		mTextSecondaryWeaponBulletsLeft.dispose();
		mTextScore.dispose();
		mCharacterFace.dispose();
		mHud.dispose();

		mMonitorTimeHandler = null;
		mTMXTiledMap = null;
		mPhysicsWorld = null;
		mDigitalOnScreenControl = null;
		mTextMainWeaponBulletsLeft = null;
		mTextSecondaryWeaponBulletsLeft = null;
		mTextScore = null;
		mCharacterFace = null;
		mTextFPS = null;
		mTextCPU = null;
		mHud = null;

		// Disposes the player.
		disposePlayer();
		// Clean the multipool.
		mMultiPool.freeResources();
		mMultiPool = null;
		// Disposes the Scene and clean it's handlers and modifiers.
		detachSelf();
		clearChildScene();
		clearEntityModifiers();
		clearTouchAreas();
		clearUpdateHandlers();
		reset();
		dispose();
	    }
	});
    }

    @Override
    public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX,
	    float pTouchAreaLocalY) {
	// Checks which weapon the player is shooting with.
	int weaponUsed = 0;

	if (pButtonSprite.equals(this.mButtonFirstAction)) {
	    weaponUsed = GeneralConstantsUtility.CHARACTER_PRIMARY_WEAPON;
	} else if (pButtonSprite.equals(this.mButtonSecondaryAction)) {
	    weaponUsed = GeneralConstantsUtility.CHARACTER_SECONDARY_WEAPON;
	}
	// Attach the bullet to the scene and perform the shot.
	this.attachBullet(GameScene.mPlayer.attack(weaponUsed));
    }

    // ===========================================================
    // Methods
    // ===========================================================

    /**
     * Creates the physics used by the game.
     */
    private void createWorldPhysics() {
	GameScene.mPhysicsWorld = new FixedStepPhysicsWorld(30, new Vector2(0,
		0), false, 8, 1);
	this.registerUpdateHandler(GameScene.mPhysicsWorld);
    }

    /**
     * Disposes the player.
     */
    private void disposePlayer() {

	Weapon weapon1 = GameScene.mPlayer
		.getWeapon(GeneralConstantsUtility.CHARACTER_PRIMARY_WEAPON);
	Weapon weapon2 = GameScene.mPlayer
		.getWeapon(GeneralConstantsUtility.CHARACTER_SECONDARY_WEAPON);

	GameScene.mMultiPool.recycleItem(MAP_ENTITY_TYPE.WEAPON, weapon1);
	GameScene.mMultiPool.recycleItem(MAP_ENTITY_TYPE.WEAPON, weapon2);

	GameScene.mPlayer.addWeapon(null,
		GeneralConstantsUtility.CHARACTER_PRIMARY_WEAPON);
	GameScene.mPlayer.addWeapon(null,
		GeneralConstantsUtility.CHARACTER_SECONDARY_WEAPON);

	// Clear player entity modifiers.
	GameScene.mPlayer.clearEntityModifiers();
	// Detach the player.
	GameScene.mPlayer.detachSelf();
	// Disposes the player.
	GameScene.mPlayer.dispose();
    }

    /**
     * Creates the player.
     */
    private void createPlayer() {
	try {

	    // Read the level start entities.
	    this.mEntityDao.loadEntitiesPositionFromJSON("level_start");

	    float playerInitialPositionX = this.mEntityDao.getPlayerPositionX();
	    float playerInitialPositionY = this.mEntityDao.getPlayerPositionY();
	    JSONObject playerData = this.mEntityDao
		    .getPlayerData(this.mGameManager.getPlayerSelected());
	    GameScene.mPlayer = new Player(
		    this.mGameManager.getPlayerSelected(),
		    playerInitialPositionX, playerInitialPositionY,
		    this.mGameResourceManger.getCharacterTexture(),
		    MainParameters.vbom);
	    GameScene.mPlayer.createPhysics(GameScene.mPhysicsWorld);
	    GameScene.mPlayer.setHp(playerData
		    .getInt(JsonFieldsConstantsUtility.CHARACTER_HP));
	    GameScene.mPlayer.setInitialHP(playerData
		    .getInt(JsonFieldsConstantsUtility.CHARACTER_HP));

	    Sprite weapon1 = GameScene.mMultiPool
		    .obtainItem(
			    MAP_ENTITY_TYPE.WEAPON,

			    playerData
				    .getString(JsonFieldsConstantsUtility.PLAYER_PRIMARY_WEAPON_JSON),
			    0, 0);
	    Sprite weapon2 = GameScene.mMultiPool
		    .obtainItem(
			    MAP_ENTITY_TYPE.WEAPON,
			    playerData
				    .getString(JsonFieldsConstantsUtility.PLAYER_SECONDARY_WEAPON_JSON),
			    0, 0);

	    GameScene.mPlayer.addWeapon((Weapon) weapon1,
		    GeneralConstantsUtility.CHARACTER_PRIMARY_WEAPON);
	    GameScene.mPlayer.addWeapon((Weapon) weapon2,
		    GeneralConstantsUtility.CHARACTER_SECONDARY_WEAPON);
	    GameScene.mPlayer.setMainWeaponID(((Weapon) weapon1).getId());

	    GameScene.mPlayer
		    .setMovementSpeed(playerData
			    .getInt(JsonFieldsConstantsUtility.CHARACTER_MOVEMENT_SPEED));
	    GameScene.mPlayer
		    .setTimePhysicalAttack(playerData
			    .getInt(JsonFieldsConstantsUtility.CHARACTER_PHYSICAL_ATTACK_TIME));

	    this.attachChild(GameScene.mPlayer);
	    GameScene.mCharacters.add(GameScene.mPlayer);

	} catch (JSONException e) {
	    e.printStackTrace();
	}

    }

    /**
     * Creates the game scene map.
     */
    private void createMap() {
	this.mTMXTiledMap = this.mGameResourceManger.getTMXTiledMap();

	// Create Map
	for (TMXLayer layer : this.mTMXTiledMap.getTMXLayers()) {
	    layer.detachSelf();
	    if (layer.getTMXLayerProperties()
		    .containsTMXProperty("map", "true")) {
		layer.setZIndex(0);

	    } else if (layer.getTMXLayerProperties().containsTMXProperty(
		    "cross", "true")) {
		layer.setZIndex(3);
	    }
	    this.attachChild(layer);
	}

	// Create Object layers
	// Offset to correct the Y position
	float yOffset = mTMXTiledMap.getTMXLayers().get(0).getHeight();

	// Read all Object layers from TMX
	for (final TMXObjectGroup group : this.mTMXTiledMap
		.getTMXObjectGroups()) {

	    // Create Collisions
	    if (group.getTMXObjectGroupProperties().containsTMXProperty(
		    "collision", "true")) {
		final FixtureDef boxFixtureDef = PhysicsFactory
			.createFixtureDef(0, 0, 1f);

		// Read all collision objects from TMX
		for (final TMXObject object : group.getTMXObjects()) {
		    // Create rectangle
		    final Rectangle rect = new Rectangle(object.getX()
			    + object.getWidth() / 2, yOffset - object.getY()
			    - object.getHeight() / 2, object.getWidth(),
			    object.getHeight(), MainParameters.vbom);

		    // Add Physics to rectangle
		    PhysicsFactory.createBoxBody(GameScene.mPhysicsWorld, rect,
			    BodyType.StaticBody, boxFixtureDef);

		    GameScene.mCollisions.add(rect);

		    rect.setVisible(false);

		    rect.setZIndex(0);
		    this.attachChild(rect);
		}

		// Create Triggers
	    } else if (group.getTMXObjectGroupProperties().containsTMXProperty(
		    "trigger", "true")) {

		// Read all triggers ojects from TMX
		for (final TMXObject object : group.getTMXObjects()) {
		    // Create rectangle
		    final AFTrigger rect = new AFTrigger(object.getX()
			    + object.getWidth() / 2, yOffset - object.getY()
			    - object.getHeight() / 2, object.getWidth(),
			    object.getHeight(), MainParameters.vbom);

		    if (object.getTMXObjectProperties().get(0).getName()
			    .equals("end")) {
			rect.setMapEnd(true);
			// mSceneController.loadNextScene();
		    }

		    GameScene.mTriggers.add(rect);

		    rect.setVisible(false);

		    rect.setZIndex(0);
		    this.attachChild(rect);
		}
	    }
	}
    }

    /**
     * Creates the player controls and all the elements over the game scene map.
     */
    private void createHUD() {
	// STATUS BAR
	final Sprite statusBar = new Sprite(
		GeneralConstantsUtility.SCREEN_WIDTH_RESOLUTION - 1000,
		GeneralConstantsUtility.SCREEN_HEIGHT_RESOLUTION - 120,
		this.mGameResourceManger.getHUDTexture().get(
			GeneralConstantsUtility.HUD_LIFE_BAR_ONLIST),
		MainParameters.vbom);

	// LIFE BAR
	GameScene.mLifeBar = new Rectangle(350, 650,
		GeneralConstantsUtility.HUD_LIFE_BAR_WIDTH, 25,
		MainParameters.vbom);
	GameScene.mLifeBar.setColor(Color.GREEN);

	// TEXT SCORE
	GameScene.mTextScore = new Text(
		GeneralConstantsUtility.SCREEN_WIDTH_RESOLUTION / 2,
		GeneralConstantsUtility.SCREEN_HEIGHT_RESOLUTION - 60,
		this.mGameResourceManger.getFont(),
		String.valueOf(this.mGameManager.getmScore()), 20,
		MainParameters.vbom);
	GameScene.mTextScore.setScale(2f);

	// BUTTONS AND JOYSTICK
	// Positions for Buttons and Joystick
	float posJoystickX = 0;
	float posJoystickY = GeneralConstantsUtility.SCREEN_HEIGHT_RESOLUTION - 600;
	float posButtonFirstX = 0;
	float posButtonFirstY = GeneralConstantsUtility.SCREEN_HEIGHT_RESOLUTION - 650;
	float posButtonSecondX = 0;
	float posButtonSecondY = GeneralConstantsUtility.SCREEN_HEIGHT_RESOLUTION - 580;

	// Position of hand
	String hand = this.mPreferencesManager.getHandedSelected();

	// Set Left position
	if (hand.equals(MenuConstantsUtility.OPTIONS_STATE_RIGHT_HANDED)) {
	    posJoystickX = GeneralConstantsUtility.SCREEN_WIDTH_RESOLUTION - 1150;
	    posButtonFirstX = GeneralConstantsUtility.SCREEN_WIDTH_RESOLUTION - 230;
	    posButtonSecondX = GeneralConstantsUtility.SCREEN_WIDTH_RESOLUTION - 100;

	    // Set Right position
	} else {
	    posJoystickX = GeneralConstantsUtility.SCREEN_WIDTH_RESOLUTION - 150;
	    posButtonFirstX = GeneralConstantsUtility.SCREEN_WIDTH_RESOLUTION - 1050;
	    posButtonSecondX = GeneralConstantsUtility.SCREEN_WIDTH_RESOLUTION - 1180;
	}

	// Create Joystick
	this.mDigitalOnScreenControl = new DigitalOnScreenControl(posJoystickX,
		posJoystickY, MainParameters.camera, this.mGameResourceManger
			.getHUDTexture().get(
				GeneralConstantsUtility.HUD_CONTROL_ONLIST),
		this.mGameResourceManger.getHUDTexture().get(
			GeneralConstantsUtility.HUD_PAD_ONLIST), 0.1f,
		MainParameters.vbom, new IOnScreenControlListener() {
		    @Override
		    public void onControlChange(
			    final BaseOnScreenControl pBaseOnScreenControl,
			    final float pValueX, final float pValueY) {
			GameScene.mPlayer.move(pValueX, pValueY);
		    }
		});

	this.mDigitalOnScreenControl.getControlBase().setBlendFunction(
		GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
	this.mDigitalOnScreenControl.setAllowDiagonal(true);

	// Create Buttons
	this.mButtonFirstAction = new ButtonSprite(
		0,
		0,
		this.mGameResourceManger.getHUDTexture().get(
			GeneralConstantsUtility.HUD_ACTION_BUTTON_ONLIST),
		this.mGameResourceManger
			.getHUDTexture()
			.get(GeneralConstantsUtility.HUD_ACTION_BUTTON_PRESSED_ONLIST),
		MainParameters.vbom);

	this.mButtonSecondaryAction = new ButtonSprite(
		0,
		0,
		this.mGameResourceManger.getHUDTexture().get(
			GeneralConstantsUtility.HUD_ACTION_BUTTON_ONLIST),
		this.mGameResourceManger
			.getHUDTexture()
			.get(GeneralConstantsUtility.HUD_ACTION_BUTTON_PRESSED_ONLIST),
		MainParameters.vbom);

	// SET POSITION
	this.mButtonFirstAction.setPosition(posButtonFirstX, posButtonFirstY);
	this.mButtonSecondaryAction.setPosition(posButtonSecondX,
		posButtonSecondY);

	// SET ALPHA
	this.mDigitalOnScreenControl.getControlBase().setAlpha(0.7f);
	this.mButtonFirstAction.setAlpha(0.7f);
	this.mButtonSecondaryAction.setAlpha(0.7f);

	// LISTENERS
	this.mButtonFirstAction.setOnClickListener(this);
	this.mButtonSecondaryAction.setOnClickListener(this);

	// BULLETS LEFT PRIMARY TEXT
	GameScene.mTextMainWeaponBulletsLeft = new Text(60, 540,
		this.mGameResourceManger.getFont(), "∞", 20,
		MainParameters.vbom);
	// BULLETS LEFT SECONDARY TEXT
	GameScene.mTextSecondaryWeaponBulletsLeft = new Text(115, 540,
		this.mGameResourceManger.getFont(),
		String.valueOf(GameScene.mPlayer.getWeapon(
			GeneralConstantsUtility.CHARACTER_SECONDARY_WEAPON)
			.getAmmoLeft()), 20, MainParameters.vbom);

	// WEAPONS
	GameScene.mPlayer.getWeapon(
		GeneralConstantsUtility.CHARACTER_PRIMARY_WEAPON).setPosition(
		100, 590);
	// this.mPlayer.getWeapon(EntitiesConstantsUtility.SPECIAL_WEAPON).setPosition(400,
	// 700);
	GameScene.mPlayer.getWeapon(
		GeneralConstantsUtility.CHARACTER_PRIMARY_WEAPON)
		.setScale(0.8f);

	// FACE
	this.mCharacterFace = new TiledSprite(100, 660,
		this.mGameResourceManger.getCharacterFace(),
		MainParameters.vbom);

	// HUD
	GameScene.mHud = new HUD();

	// ATTACH
	this.setChildScene(this.mDigitalOnScreenControl);
	GameScene.mHud.attachChild(GameScene.mLifeBar);
	GameScene.mHud.attachChild(statusBar);
	GameScene.mHud.attachChild(GameScene.mTextScore);
	GameScene.mHud.attachChild(this.mButtonFirstAction);
	GameScene.mHud.attachChild(this.mButtonSecondaryAction);
	GameScene.mHud.attachChild(GameScene.mPlayer
		.getWeapon(GeneralConstantsUtility.CHARACTER_PRIMARY_WEAPON));
	// this.mHud.attachChild(this.mPlayer.getWeapon(EntitiesConstantsUtility.SPECIAL_WEAPON));
	GameScene.mHud.attachChild(mTextMainWeaponBulletsLeft);
	GameScene.mHud.attachChild(GameScene.mTextSecondaryWeaponBulletsLeft);
	GameScene.mHud.attachChild(this.mCharacterFace);
	GameScene.mHud.registerTouchArea(this.mButtonFirstAction);
	GameScene.mHud.registerTouchArea(this.mButtonSecondaryAction);
	MainParameters.camera.setHUD(GameScene.mHud);
    }

    /**
     * Load JSON predefined entities in the game scene.
     * 
     * @param rectangleTriggerPosition
     *            the position of the trigger.
     */
    public void loadEntitiesIntoMap(final String rectangleTriggerPosition) {
	for (EntityMessenger entityMessenger : GameScene.this.mEntityDao
		.loadEntitiesPositionFromJSON(rectangleTriggerPosition)) {
	    final String id = entityMessenger.getId();
	    final int posX = entityMessenger.getPosX();
	    final int posY = entityMessenger.getPosY();
	    if (posX != 0 && posY != 0) {
		final MAP_ENTITY_TYPE entityTYPE = entityMessenger
			.getEntityType();
		final Sprite entity = mMultiPool.obtainItem(entityTYPE, id,
			posX, posY);

		switch (entityTYPE) {
		case ENEMY:
		    GameScene.mCharacters.add((Enemy) entity);
		    break;
		default:
		    GameScene.mObjects.add((AMapObject) entity);
		    break;
		}

		if (!entity.hasParent()) {
		    GameScene.this.attachChild(entity);
		    GameScene.this.sortChildren(false);
		}
	    }
	}
    }

    /**
     * Creates the monitor logger to show fps and cpu information.
     */
    private void createMonitorLogger() {
	// Create FPS Text
	this.mTextFPS = new Text(
		GeneralConstantsUtility.SCREEN_WIDTH_RESOLUTION - 100,
		GeneralConstantsUtility.SCREEN_HEIGHT_RESOLUTION - 25,
		this.mGameResourceManger.getFont(), "", 20, MainParameters.vbom);

	// Create CPU Text
	this.mTextCPU = new Text(
		GeneralConstantsUtility.SCREEN_WIDTH_RESOLUTION - 300,
		GeneralConstantsUtility.SCREEN_HEIGHT_RESOLUTION - 25,
		this.mGameResourceManger.getFont(), "", 20, MainParameters.vbom);

	GameScene.mHud.attachChild(this.mTextFPS);
	GameScene.mHud.attachChild(this.mTextCPU);

	// Start the Monitor Logger
	this.startMonitorLogger();
    }

    /**
     * Starts the monitor logger.
     */
    private void startMonitorLogger() {
	final String isFPSEnabled = this.mPreferencesManager
		.getFPSMonitorEnabled();
	final String isCPUEnabled = this.mPreferencesManager
		.getCPUMonitorEnabled();

	final DecimalFormat decimalFormat = new DecimalFormat("00.00");

	final FPSCounter fpsCounter = new FPSCounter();
	MainParameters.engine.registerUpdateHandler(fpsCounter);

	this.mMonitorTimeHandler = new TimerHandler(2f, true,
		new ITimerCallback() {
		    @Override
		    public void onTimePassed(final TimerHandler pTimerHandler) {
			// Calculate CPU usage
			if (isCPUEnabled
				.equals(MenuConstantsUtility.OPTIONS_STATE_ON)) {
			    Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
				    try {
					RandomAccessFile reader = new RandomAccessFile(
						"/proc/stat", "r");
					String load = reader.readLine();

					String[] toks = load.split(" ");

					long idle1 = Long.parseLong(toks[5]);
					long cpu1 = Long.parseLong(toks[2])
						+ Long.parseLong(toks[3])
						+ Long.parseLong(toks[4])
						+ Long.parseLong(toks[6])
						+ Long.parseLong(toks[7])
						+ Long.parseLong(toks[8]);

					try {
					    Thread.sleep(360);
					} catch (Exception e) {
					    e.printStackTrace();
					}

					reader.seek(0);
					load = reader.readLine();
					reader.close();

					toks = load.split(" ");

					long idle2 = Long.parseLong(toks[5]);
					long cpu2 = Long.parseLong(toks[2])
						+ Long.parseLong(toks[3])
						+ Long.parseLong(toks[4])
						+ Long.parseLong(toks[6])
						+ Long.parseLong(toks[7])
						+ Long.parseLong(toks[8]);

					// Show CPU
					GameScene.this.mTextCPU.setText("CPU: "
						+ decimalFormat
							.format((float) (cpu2 - cpu1)
								/ ((cpu2 + idle2) - (cpu1 + idle1))));

				    } catch (IOException ex) {
					ex.printStackTrace();
				    }
				}
			    });
			    thread.start();
			}

			// Show FPS
			if (isFPSEnabled.equals("On")) {
			    GameScene.this.mTextFPS.setText("FPS: "
				    + decimalFormat.format(fpsCounter.getFPS()));
			    fpsCounter.reset();
			}

			pTimerHandler.reset();

		    }
		});
	this.registerUpdateHandler(this.mMonitorTimeHandler);
    }

    /**
     * Attaches a bullet to the scene.
     * 
     * @param pBullet
     *            the bullet to be attached to the scene.
     */
    public void attachBullet(final Bullet pBullet) {
	if (pBullet != null) {
	    GameScene.mBullets.add(pBullet);
	    pBullet.setZIndex(2);
	    if (pBullet.hasParent()) {
		pBullet.detachSelf();
	    }
	    this.attachChild(pBullet);
	    this.sortChildren(false);
	}
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}
