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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.andengine.extension.tmx.TMXLoader;
import org.andengine.extension.tmx.TMXTiledMap;
import org.andengine.extension.tmx.util.exception.TMXLoadException;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.devgru.authorityforce.constant.AssetsConstantsUtility;
import org.devgru.authorityforce.constant.GeneralConstantsUtility;
import org.devgru.authorityforce.constant.JsonFieldsConstantsUtility;
import org.devgru.authorityforce.constant.MainParameters;
import org.devgru.authorityforce.dao.EntityDAO;
import org.devgru.authorityforce.dao.TextureDAO;
import org.devgru.authorityforce.manager.GameManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * GameResourceManager.java
 * 
 * Manages graphic resources for game. All charges made ​​in a dynamic way. Each
 * charging access to Dao for only load the resources need for that scene
 * 
 * @version 1.0 Apr 19, 2013
 * @author Aitor Gonzalez Martinez
 * @author Xavier Martinez de Francisco
 * @author Toni Trigo Diaz
 */
public class GameResourceManager extends ASceneResourcesManager {

    // ===========================================================
    // Constants
    // ===========================================================

    /* Instance of this class */
    private static final GameResourceManager INSTANCE = new GameResourceManager();

    // ===========================================================
    // Fields
    // ===========================================================

    /* A instance of TextureDAO */
    private TextureDAO mTextureDao;
    /* A instance of GameManager */
    private GameManager mGameManager;
    /* A instance of EntityDAO */
    private EntityDAO mEntityDAO;

    // ATLAS
    /* TMX Map */
    private TMXTiledMap mTMXTiledMap;
    /* Texture Atlas for HUD */
    private BitmapTextureAtlas mHUDTextureAtlas;
    /* Texture Atlas for Characters */
    private BitmapTextureAtlas mCharacterTextureAtlas;
    /* Texture Atlas for Faces */
    private BitmapTextureAtlas mCharacterFaceAtlas;
    /* Texture Atlas for images of the help menu */
    private ArrayList<BuildableBitmapTextureAtlas> mEnemiesTextureAtlas;
    /* Texture Atlas for images of the help menu */
    private BuildableBitmapTextureAtlas mMapObjectTextureAtlas;

    // TEXTURES
    /* Texture for HUD */
    private HashMap<Integer, ITextureRegion> mHUDTexture;
    /* Texture for Characters */
    private TiledTextureRegion mCharacterTexture;
    /* Texture for Faces */
    private TiledTextureRegion mCharacterFace;
    /* Texture for Enemies */
    private HashMap<String, TiledTextureRegion> mEnemiesTexture;
    /* Texture for MapObjects */
    private HashMap<String, TextureRegion> mMapObjectTexture;

    // ===========================================================
    // Constructors
    // ===========================================================

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    /**
     * Singleton Pattern
     * 
     * @return a unique instance of this class
     */
    public static GameResourceManager getInstance() {
	return INSTANCE;
    }

    public TMXTiledMap getTMXTiledMap() {
	return this.mTMXTiledMap;
    }

    public Map<Integer, ITextureRegion> getHUDTexture() {
	return this.mHUDTexture;
    }

    public Map<String, TextureRegion> getCharactersObjectTexture() {
	return this.mMapObjectTexture;
    }

    public TiledTextureRegion getCharacterTexture() {
	return this.mCharacterTexture;
    }

    public Map<String, TiledTextureRegion> getEnemiesTexture() {
	return this.mEnemiesTexture;
    }

    public TiledTextureRegion getCharacterFace() {
	return this.mCharacterFace;
    }

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    /**
     * Prepare this manager to work
     */
    @Override
    public void loadAllResources() {
	this.mGameManager = GameManager.getInstance();
	this.mEntityDAO = EntityDAO.getInstance();
	this.mTextureDao = TextureDAO.getInstance();

	this.mTextureDao.readTexturesFromJSON();
	this.mEntityDAO.readPlayersData();

	this.loadMap();
	this.loadHUD();
	this.loadCharacterTexture();
	this.loadFaceTexture();
	this.loadCharacterObjectTextures();
	this.loadEnemiesTextures();
	this.loadFont();
    }

    /**
     * Dispose all resources of the manager
     */
    @Override
    public void disposeAllResources() {
	this.mHUDTextureAtlas.unload();
	this.mMapObjectTextureAtlas.unload();
	this.mCharacterTextureAtlas.unload();
	this.mCharacterFaceAtlas.unload();
	for (BuildableBitmapTextureAtlas atlas : this.mEnemiesTextureAtlas) {
	    atlas.unload();
	}

	this.mTMXTiledMap = null;
	this.mHUDTextureAtlas = null;
	this.mHUDTexture = null;
	this.mMapObjectTexture = null;
	this.mCharacterTexture = null;
	this.mCharacterFaceAtlas = null;
	this.mEnemiesTexture = null;
    }

    // ===========================================================
    // Methods
    // ===========================================================

    /**
     * Load the TMX Map
     */
    private void loadMap() {
	try {
	    final TMXLoader tmxLoader = new TMXLoader(
		    MainParameters.activity.getAssets(),
		    MainParameters.engine.getTextureManager(),
		    TextureOptions.BILINEAR, MainParameters.vbom);

	    this.mTMXTiledMap = tmxLoader.loadFromAsset(this.mTextureDao
		    .getTMXFileName());
	} catch (final TMXLoadException tmxle) {
	    tmxle.printStackTrace();
	}
    }

    /**
     * Load all textures for HUD
     */
    private void loadHUD() {
	BitmapTextureAtlasTextureRegionFactory
		.setAssetBasePath(AssetsConstantsUtility.IMAGES_DIRECTORY);

	this.mHUDTextureAtlas = new BitmapTextureAtlas(
		MainParameters.activity.getTextureManager(), 512, 720,
		TextureOptions.BILINEAR);

	TextureRegion barLifeTexture = BitmapTextureAtlasTextureRegionFactory
		.createFromAsset(this.mHUDTextureAtlas,
			MainParameters.activity,
			AssetsConstantsUtility.HUD_STATUS_BAR, 0, 0);

	TextureRegion controlTexture = BitmapTextureAtlasTextureRegionFactory
		.createFromAsset(this.mHUDTextureAtlas,
			MainParameters.activity,
			AssetsConstantsUtility.HUD_CONTROL, 0, 200);

	ITextureRegion padTexture = BitmapTextureAtlasTextureRegionFactory
		.createFromAsset(this.mHUDTextureAtlas,
			MainParameters.activity,
			AssetsConstantsUtility.HUD_PAD, 256, 200);

	ITextureRegion btnActionButtonTexture = BitmapTextureAtlasTextureRegionFactory
		.createFromAsset(this.mHUDTextureAtlas,
			MainParameters.activity,
			AssetsConstantsUtility.HUD_ACTION_BUTTON, 384, 200);

	ITextureRegion btnActionButtonPressedTexture = BitmapTextureAtlasTextureRegionFactory
		.createFromAsset(this.mHUDTextureAtlas,
			MainParameters.activity,
			AssetsConstantsUtility.HUD_ACTION_BUTTON_PRESSED, 0,
			456);

	this.mHUDTexture = new HashMap<Integer, ITextureRegion>();
	this.mHUDTexture.put(GeneralConstantsUtility.HUD_LIFE_BAR_ONLIST,
		barLifeTexture);
	this.mHUDTexture.put(GeneralConstantsUtility.HUD_CONTROL_ONLIST,
		controlTexture);
	this.mHUDTexture
		.put(GeneralConstantsUtility.HUD_PAD_ONLIST, padTexture);
	this.mHUDTexture.put(GeneralConstantsUtility.HUD_ACTION_BUTTON_ONLIST,
		btnActionButtonTexture);
	this.mHUDTexture.put(
		GeneralConstantsUtility.HUD_ACTION_BUTTON_PRESSED_ONLIST,
		btnActionButtonPressedTexture);

	this.mHUDTextureAtlas.load();
    }

    /**
     * Load texture for player
     */
    public void loadCharacterTexture() {
	JSONObject mPlayerData = this.mEntityDAO
		.getPlayerData(this.mGameManager.getPlayerSelected());

	try {
	    String texturePath = mPlayerData
		    .getString(JsonFieldsConstantsUtility.PLAYER_TEXTURE);

	    BitmapTextureAtlasTextureRegionFactory
		    .setAssetBasePath(AssetsConstantsUtility.IMAGES_DIRECTORY);

	    this.mCharacterTextureAtlas = new BitmapTextureAtlas(
		    MainParameters.activity.getTextureManager(), 512, 1280,
		    TextureOptions.BILINEAR);
	    this.mCharacterTexture = BitmapTextureAtlasTextureRegionFactory
		    .createTiledFromAsset(this.mCharacterTextureAtlas,
			    MainParameters.activity, texturePath, 0, 0, 5, 9);

	    this.mCharacterTextureAtlas.load();

	} catch (JSONException e) {
	    e.printStackTrace();
	}
    }

    /**
     * Load textures for player face
     */
    public void loadFaceTexture() {
	JSONObject mPlayerData = this.mEntityDAO
		.getPlayerData(this.mGameManager.getPlayerSelected());

	try {
	    String texturePath = mPlayerData
		    .getString(JsonFieldsConstantsUtility.PLAYER_FACE_TEXTURE);

	    BitmapTextureAtlasTextureRegionFactory
		    .setAssetBasePath(AssetsConstantsUtility.IMAGES_DIRECTORY);

	    this.mCharacterFaceAtlas = this.mCharacterTextureAtlas = new BitmapTextureAtlas(
		    MainParameters.activity.getTextureManager(), 256, 128,
		    TextureOptions.BILINEAR);
	    this.mCharacterFace = BitmapTextureAtlasTextureRegionFactory
		    .createTiledFromAsset(this.mCharacterTextureAtlas,
			    MainParameters.activity, texturePath, 0, 0, 4, 2);

	    this.mCharacterTextureAtlas.load();

	} catch (JSONException e) {
	    e.printStackTrace();
	}

    }

    /**
     * Load textures for character objects
     */
    private void loadCharacterObjectTextures() {
	try {
	    BitmapTextureAtlasTextureRegionFactory
		    .setAssetBasePath(AssetsConstantsUtility.IMAGES_DIRECTORY);
	    // Gets the object with the data
	    JSONObject textures = this.mTextureDao
		    .getTextureByType(JsonFieldsConstantsUtility.TEXTURE_CHARACTER_OBJECTS);

	    // Gets the size of the Bitmap
	    int bitmapXSize = textures
		    .getInt(JsonFieldsConstantsUtility.TEXTURE_BITMAP_SIZE_X);
	    int bitmapYSize = textures
		    .getInt(JsonFieldsConstantsUtility.TEXTURE_BITMAP_SIZE_Y);

	    // Initializes the BitmapTextureAtlas
	    this.mMapObjectTextureAtlas = new BuildableBitmapTextureAtlas(
		    MainParameters.activity.getTextureManager(), bitmapXSize,
		    bitmapYSize);
	    // Initializes the Map that will contain the Textures
	    this.mMapObjectTexture = null;
	    this.mMapObjectTexture = new HashMap<String, TextureRegion>();

	    // Gets a JSON Array Object to get all the textures definitions
	    JSONArray texturesArray = textures
		    .getJSONArray(JsonFieldsConstantsUtility.TEXTURES);

	    // Traverses the Array to get the texture properties
	    for (int i = 0; i < texturesArray.length(); i++) {
		JSONObject texture = texturesArray.getJSONObject(i);

		// Gets the data about the texture
		String textureID = texture
			.getString(JsonFieldsConstantsUtility.ID);
		String path = texture
			.getString(JsonFieldsConstantsUtility.TEXTURE_PATH);
		TextureRegion region = BitmapTextureAtlasTextureRegionFactory
			.createFromAsset(this.mMapObjectTextureAtlas,
				MainParameters.activity, path);
		this.mMapObjectTexture.put(textureID, region);
	    }

	    // Loads the atlas
	    try {
		this.mMapObjectTextureAtlas
			.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
				0, 1, 0));

		this.mMapObjectTextureAtlas.load();

	    } catch (TextureAtlasBuilderException e) {
		e.printStackTrace();
	    }

	} catch (JSONException e) {
	    e.printStackTrace();
	}
    }

    /**
     * Load textures for enemies
     */
    private void loadEnemiesTextures() {
	try {
	    BitmapTextureAtlasTextureRegionFactory
		    .setAssetBasePath(AssetsConstantsUtility.IMAGES_DIRECTORY);

	    // Gets the object with the data
	    JSONObject textures = this.mTextureDao
		    .getTextureByType(JsonFieldsConstantsUtility.TEXTURE_DEFINITION_ENEMIES);

	    // Gets the size of the BitmapAtlas
	    int sizeX = textures
		    .getInt(JsonFieldsConstantsUtility.TEXTURE_BITMAP_SIZE_X);
	    int sizeY = textures
		    .getInt(JsonFieldsConstantsUtility.TEXTURE_BITMAP_SIZE_Y);

	    // Initializes the HashMap that will contain the bosses textures
	    this.mEnemiesTexture = new HashMap<String, TiledTextureRegion>();

	    // Initializes the ArrayList which contains the Atlases
	    this.mEnemiesTextureAtlas = new ArrayList<BuildableBitmapTextureAtlas>();

	    // Gets the JSONArray with the data of the textures
	    JSONArray array = textures
		    .getJSONArray(JsonFieldsConstantsUtility.TEXTURES);

	    // Creates the BitmapAtlases and the textures
	    for (int i = 0; i < array.length(); i++) {
		JSONObject texture = array.getJSONObject(i);

		int columns = texture
			.getInt(JsonFieldsConstantsUtility.TEXTURE_COLUMNS);
		int rows = texture
			.getInt(JsonFieldsConstantsUtility.TEXTURE_ROWS);
		String id = texture.getString(JsonFieldsConstantsUtility.ID);
		String imagePath = texture
			.getString(JsonFieldsConstantsUtility.TEXTURE_PATH);

		BuildableBitmapTextureAtlas atlas = new BuildableBitmapTextureAtlas(
			MainParameters.activity.getTextureManager(), sizeX,
			sizeY);

		TiledTextureRegion region = BitmapTextureAtlasTextureRegionFactory
			.createTiledFromAsset(atlas, MainParameters.activity,
				imagePath, columns, rows);

		atlas.load();

		try {
		    atlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
			    0, 1, 0));
		    atlas.load();

		} catch (TextureAtlasBuilderException e) {
		    e.printStackTrace();
		}

		this.mEnemiesTextureAtlas.add(atlas);
		this.mEnemiesTexture.put(id, region);
	    }

	} catch (JSONException e) {
	    e.printStackTrace();
	}
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}
