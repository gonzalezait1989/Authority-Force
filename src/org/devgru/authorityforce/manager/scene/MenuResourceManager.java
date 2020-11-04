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

import java.util.HashMap;

import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.devgru.authorityforce.constant.AssetsConstantsUtility;
import org.devgru.authorityforce.constant.MainParameters;
import org.devgru.authorityforce.constant.MenuConstantsUtility;

/**
 * MenuResourceManager.java
 * 
 * Manages graphic resources for menus
 * 
 * @version 1.0 Apr 19, 2013
 * @author Aitor Gonzalez Martinez
 * @author Xavier Martinez de Francisco
 * @author Toni Trigo Diaz
 */
public class MenuResourceManager extends ASceneResourcesManager {

    // ===========================================================
    // Constants
    // ===========================================================

    /* Instance of this class */
    private static final MenuResourceManager INSTANCE = new MenuResourceManager();

    // ===========================================================
    // Fields
    // ===========================================================

    // ATLAS
    /* Texture Atlas for Logo */
    private BitmapTextureAtlas mLogoTextureAtlas;
    /* Texture Atlas for Background */
    private BitmapTextureAtlas mBackgroundTextureAtlas;
    /* Texture Atlas for Buttons */
    private BitmapTextureAtlas mButtonsTextureAtlas;
    /* Texture Atlas for images of the characters */
    private BitmapTextureAtlas mCharactersTextureAtlas;
    /* Texture Atlas for images of the help menu */
    private BitmapTextureAtlas mHelpImagesTextureAtlas;

    // TEXTURES
    /* Texture for Logo */
    private ITextureRegion mLogoTexture;
    /* Texture for Background */
    private ITextureRegion mBackgroundTexture;
    /* Texture for Buttons */
    private HashMap<Integer, ITextureRegion> mButtonsTexture;
    /* Texture forimages of the characters */
    private HashMap<Integer, ITextureRegion> mCharacterTexture;
    /* Texture for images of the help menu */
    private HashMap<Integer, ITextureRegion> mHelpImagesTexture;

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
    public static MenuResourceManager getInstance() {
	return INSTANCE;
    }

    public ITextureRegion getLogoTexture() {
	return this.mLogoTexture;
    }

    public ITextureRegion getBackgroundTexture() {
	return this.mBackgroundTexture;
    }

    public HashMap<Integer, ITextureRegion> getButtonsTexture() {
	return this.mButtonsTexture;
    }

    public HashMap<Integer, ITextureRegion> getCharacterTexture() {
	return this.mCharacterTexture;
    }

    public HashMap<Integer, ITextureRegion> getHelpImagesTexture() {
	return this.mHelpImagesTexture;
    }

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    /**
     * Prepare this manager to work
     */
    @Override
    public void loadAllResources() {
	this.loadBackgroundTexture();
	this.loadLogoTexture();
	this.loadButtonTextures();
	this.loadCharacterTextures();
	this.loadHelpImagesTexture();
	this.loadFont();
    }

    /**
     * Dispose all resources of the manager
     */
    @Override
    public void disposeAllResources() {
	// Dispose textures
	this.mButtonsTexture = null;
	this.mCharacterTexture = null;
	this.mHelpImagesTexture = null;

	// Dispose texture atlas
	this.mButtonsTextureAtlas.unload();
	this.mCharactersTextureAtlas.unload();
	this.mHelpImagesTextureAtlas.unload();
	this.mButtonsTextureAtlas = null;
	this.mCharactersTextureAtlas = null;
	this.mHelpImagesTextureAtlas = null;
    }

    // ===========================================================
    // Methods
    // ===========================================================

    /**
     * Loads the logo
     */
    private void loadLogoTexture() {
	// Sets directory to load
	BitmapTextureAtlasTextureRegionFactory
		.setAssetBasePath(AssetsConstantsUtility.IMAGES_DIRECTORY);

	// Cretae the Texture Atlas
	this.mLogoTextureAtlas = new BitmapTextureAtlas(
		MainParameters.activity.getTextureManager(), 720, 720,
		TextureOptions.BILINEAR);

	// Createt the Texutre
	this.mLogoTexture = BitmapTextureAtlasTextureRegionFactory
		.createFromAsset(this.mLogoTextureAtlas,
			MainParameters.activity,
			AssetsConstantsUtility.MENU_LOGO, 0, 0);

	// Load the Texture Altas
	this.mLogoTextureAtlas.load();
    }

    /**
     * Loads the background texture
     */
    private void loadBackgroundTexture() {
	BitmapTextureAtlasTextureRegionFactory
		.setAssetBasePath(AssetsConstantsUtility.IMAGES_DIRECTORY);

	this.mBackgroundTextureAtlas = new BitmapTextureAtlas(
		MainParameters.activity.getTextureManager(), 1280, 720,
		TextureOptions.BILINEAR);

	this.mBackgroundTexture = BitmapTextureAtlasTextureRegionFactory
		.createFromAsset(this.mBackgroundTextureAtlas,
			MainParameters.activity,
			AssetsConstantsUtility.MENU_BACKGROUND, 0, 0);

	this.mBackgroundTextureAtlas.load();
    }

    /**
     * Loads the resources buttons
     */
    private void loadButtonTextures() {
	BitmapTextureAtlasTextureRegionFactory
		.setAssetBasePath(AssetsConstantsUtility.IMAGES_DIRECTORY);

	this.mButtonsTextureAtlas = new BitmapTextureAtlas(
		MainParameters.activity.getTextureManager(), 1280, 256,
		TextureOptions.BILINEAR);

	final ITextureRegion btnLeftTexture = BitmapTextureAtlasTextureRegionFactory
		.createFromAsset(this.mButtonsTextureAtlas,
			MainParameters.activity,
			AssetsConstantsUtility.MENU_BUTTON_LEFT, 0, 0);

	final ITextureRegion btnMiddleLeftTexture = BitmapTextureAtlasTextureRegionFactory
		.createFromAsset(this.mButtonsTextureAtlas,
			MainParameters.activity,
			AssetsConstantsUtility.MENU_BUTTON_MIDDLE_LEFT, 300, 0);

	final ITextureRegion btnMiddleRihtTexture = BitmapTextureAtlasTextureRegionFactory
		.createFromAsset(this.mButtonsTextureAtlas,
			MainParameters.activity,
			AssetsConstantsUtility.MENU_BUTTON_MIDDLE_RIGHT, 570, 0);

	final ITextureRegion btnRightTexture = BitmapTextureAtlasTextureRegionFactory
		.createFromAsset(this.mButtonsTextureAtlas,
			MainParameters.activity,
			AssetsConstantsUtility.MENU_BUTTON_RIGHT, 840, 0);

	final ITextureRegion btnHelpTexture = BitmapTextureAtlasTextureRegionFactory
		.createFromAsset(this.mButtonsTextureAtlas,
			MainParameters.activity,
			AssetsConstantsUtility.MENU_BUTTON_HELP, 0, 60);

	final ITextureRegion btnExitTexture = BitmapTextureAtlasTextureRegionFactory
		.createFromAsset(this.mButtonsTextureAtlas,
			MainParameters.activity,
			AssetsConstantsUtility.MENU_BUTTON_EXIT, 180, 60);

	final ITextureRegion btnLoadGameTexture = BitmapTextureAtlasTextureRegionFactory
		.createFromAsset(this.mButtonsTextureAtlas,
			MainParameters.activity,
			AssetsConstantsUtility.MENU_SAVED_GAME_BOX, 0, 120);

	// Add all texture to Map
	this.mButtonsTexture = new HashMap<Integer, ITextureRegion>();
	this.mButtonsTexture.put(MenuConstantsUtility.MENU_BUTTON_LEFT,
		btnLeftTexture);
	this.mButtonsTexture.put(MenuConstantsUtility.MENU_BUTTON_MIDDLE_LEFT,
		btnMiddleLeftTexture);
	this.mButtonsTexture.put(MenuConstantsUtility.MENU_BUTTON_MIDDLE_RIGHT,
		btnMiddleRihtTexture);
	this.mButtonsTexture.put(MenuConstantsUtility.MENU_BUTTON_RIGHT,
		btnRightTexture);
	this.mButtonsTexture.put(MenuConstantsUtility.MENU_BUTTON_HELP,
		btnHelpTexture);
	this.mButtonsTexture.put(MenuConstantsUtility.MENU_BUTTON_EXIT,
		btnExitTexture);
	this.mButtonsTexture.put(MenuConstantsUtility.MENU_BOX_LOAD_GAME,
		btnLoadGameTexture);

	this.mButtonsTextureAtlas.load();
    }

    /**
     * Loads the characters list
     */
    private void loadCharacterTextures() {
	BitmapTextureAtlasTextureRegionFactory
		.setAssetBasePath(AssetsConstantsUtility.IMAGES_DIRECTORY);

	this.mCharactersTextureAtlas = new BitmapTextureAtlas(
		MainParameters.activity.getTextureManager(), 1024, 768,
		TextureOptions.BILINEAR);

	final ITextureRegion greyMosesRegion = BitmapTextureAtlasTextureRegionFactory
		.createFromAsset(this.mCharactersTextureAtlas,
			MainParameters.activity,
			AssetsConstantsUtility.MENU_CHARACTER_GREY_MOSES, 0, 0);

	final ITextureRegion strikerRegion = BitmapTextureAtlasTextureRegionFactory
		.createFromAsset(this.mCharactersTextureAtlas,
			MainParameters.activity,
			AssetsConstantsUtility.MENU_CHARACTER_STRIKER, 331, 0);

	this.mCharacterTexture = new HashMap<Integer, ITextureRegion>();
	this.mCharacterTexture
		.put(MenuConstantsUtility.MENU_CHARACTER_GREY_MOSES,
			greyMosesRegion);
	this.mCharacterTexture.put(MenuConstantsUtility.MENU_CHARACTER_STRIKER,
		strikerRegion);

	this.mCharactersTextureAtlas.load();
    }

    /**
     * Loads the images for Help menu
     */
    private void loadHelpImagesTexture() {
	BitmapTextureAtlasTextureRegionFactory
		.setAssetBasePath(AssetsConstantsUtility.IMAGES_DIRECTORY);

	this.mHelpImagesTextureAtlas = new BitmapTextureAtlas(
		MainParameters.activity.getTextureManager(), 256, 512,
		TextureOptions.BILINEAR);

	final ITextureRegion weaponsTexture = BitmapTextureAtlasTextureRegionFactory
		.createFromAsset(this.mHelpImagesTextureAtlas,
			MainParameters.activity,
			AssetsConstantsUtility.HELP_IMAGE_WEAPONS, 0, 0);

	final ITextureRegion armorsTexture = BitmapTextureAtlasTextureRegionFactory
		.createFromAsset(this.mHelpImagesTextureAtlas,
			MainParameters.activity,
			AssetsConstantsUtility.HELP_IMAGE_ARMORS, 128, 0);

	final ITextureRegion itemsTexture = BitmapTextureAtlasTextureRegionFactory
		.createFromAsset(this.mHelpImagesTextureAtlas,
			MainParameters.activity,
			AssetsConstantsUtility.HELP_IMAGE_ITEMS, 192, 0);

	this.mHelpImagesTexture = new HashMap<Integer, ITextureRegion>();
	this.mHelpImagesTexture.put(
		MenuConstantsUtility.MENU_HELP_IMAGE_WEAPONS, weaponsTexture);
	this.mHelpImagesTexture.put(
		MenuConstantsUtility.MENU_HELP_IMAGE_ARMORS, armorsTexture);
	this.mHelpImagesTexture.put(MenuConstantsUtility.MENU_HELP_IMAGE_ITEMS,
		itemsTexture);

	this.mHelpImagesTextureAtlas.load();
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}
