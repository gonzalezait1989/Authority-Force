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

package org.devgru.authorityforce.scene.menu;

import java.util.HashMap;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.FadeOutModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.AutoWrap;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.modifier.IModifier;
import org.devgru.authorityforce.R;
import org.devgru.authorityforce.constant.GeneralConstantsUtility;
import org.devgru.authorityforce.constant.GeneralConstantsUtility.SceneType;
import org.devgru.authorityforce.constant.MainParameters;
import org.devgru.authorityforce.constant.MenuConstantsUtility;
import org.devgru.authorityforce.scene.ABaseMenuScene;

/**
 * HelpMenuScene.java
 * 
 * This scene provides information of this applicacion to the user
 * 
 * @version 1.0 Apr 19, 2013
 * @author Aitor Gonzalez Martinez
 * @author Xavier Martinez de Francisco
 * @author Toni Trigo Diaz
 */
public class HelpMenuScene extends ABaseMenuScene {

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    // Buttons ID
    /* Id to back button */
    private final int backOption = 0;
    /* Id to weapons button */
    private final int weaponsOption = 1;
    /* Id to armors button */
    private final int armorsOption = 2;
    /* Id to items button */
    private final int itemsOption = 3;

    // POSITIONS
    /* Position X to Images */
    private final int mPosImageX = MenuConstantsUtility.SCREEN_POS_CENTER_X - 375;
    /* Position YF to Images */
    private final int mPosImageY = MenuConstantsUtility.SCREEN_POS_CENTER_Y;
    /* Position X to Sub Header Text */
    private final int mPosSubHeaderTextX = MenuConstantsUtility.SCREEN_POS_CENTER_X / 2;
    /* Position Y to Sub Header Text */
    private final int mPosSubHeaderTextY = MenuConstantsUtility.SCREEN_POS_CENTER_Y + 230;
    /* Position X to Description Text */
    private final int mPosDescriptionTextX = MenuConstantsUtility.SCREEN_POS_CENTER_X + 150;
    /* Position Y to Description Text */
    private final int mPosDescriptionTextY = MenuConstantsUtility.SCREEN_POS_CENTER_Y;

    /* Position X to Back Button */
    private final float mPosBackBtnX = GeneralConstantsUtility.SCREEN_WIDTH_RESOLUTION - 1050;
    /* Position X to Weapons Button */
    private final float mPosWeaponsBtnX = GeneralConstantsUtility.SCREEN_WIDTH_RESOLUTION - 785;
    /* Position X to Armors Button */
    private final float mPosArmorsBtnX = GeneralConstantsUtility.SCREEN_WIDTH_RESOLUTION - 495;
    /* Position X to Items Button */
    private final float mPosItemsBtnX = GeneralConstantsUtility.SCREEN_WIDTH_RESOLUTION - 230;

    // ITEMS TO BASESCENE
    /* Background Sprite */
    private Sprite mSpriteBackground;
    /* Logo Sprite */
    private Sprite mSpriteLogo;
    /* Weapons Sprite */
    private Sprite mSpriteWeapons;
    /* Armors Sprite */
    private Sprite mSpriteArmors;
    /* Items Sprite */
    private Sprite mSpriteItems;
    /* The Font */
    private Font mFont;
    /* Header Text */
    private Text mTextHeader;
    /* SubHeader Text */
    private Text mTextSubHeader;
    /* Description Text */
    private Text mTextDescription;
    /* Option selected by user */
    private int mSelectedOption = 1;

    // ITEMS TO CHILDMENUSCENE
    /* The scene which contains all the buttons */
    private MenuScene mChildMenuScene;
    /* The Back Button */
    private IMenuItem mButtonBack;
    /* The Weapons Button */
    private IMenuItem mButtonWeapons;
    /* The Armors Button */
    private IMenuItem mButtonArmors;
    /* The Items Button */
    private IMenuItem mButtonItems;

    // ===========================================================
    // Constructors
    // ===========================================================

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    /**
     * This is called when back button is pressed
     */
    @Override
    public void onBackKeyPressed() {
	this.performBackAction();
    }

    /**
     * Returns the type of scene
     * 
     * @return returns the type of scene
     */
    @Override
    public SceneType getSceneType() {
	return SceneType.HELP_MENU_SCENE;
    }

    /**
     * Loads all the screen elements the user can not interact with.
     */
    @Override
    public void createBaseScene() {
	// RESOURCES
	HashMap<Integer, ITextureRegion> helpImages = this.mMenuResourceManager
		.getHelpImagesTexture();
	ITextureRegion background = this.mMenuResourceManager
		.getBackgroundTexture();
	ITextureRegion logo = this.mMenuResourceManager.getLogoTexture();
	this.mFont = (Font) this.mMenuResourceManager.getFont();
	String menuHeaderLiteral = MainParameters.activity
		.getString(R.string.help_title);
	String menuHeaderSubTitle = MainParameters.activity
		.getString(R.string.help_subtitle_weapons);
	String menuDescriptionWeapons = MainParameters.activity
		.getString(R.string.help_weapons_description);

	// CREATE ITEMS
	// Background
	this.mSpriteBackground = new Sprite(
		MenuConstantsUtility.SCREEN_POS_CENTER_X,
		MenuConstantsUtility.SCREEN_POS_CENTER_Y, background,
		MainParameters.vbom);
	// Logo
	this.mSpriteLogo = new Sprite(MenuConstantsUtility.SCREEN_POS_CENTER_X,
		MenuConstantsUtility.MENU_LOGO_POS_Y, logo, MainParameters.vbom);
	// Weapons Image
	this.mSpriteWeapons = new Sprite(this.mPosImageX, this.mPosImageY,
		helpImages.get(MenuConstantsUtility.MENU_HELP_IMAGE_WEAPONS),
		MainParameters.vbom);
	// Armors Image
	this.mSpriteArmors = new Sprite(this.mPosImageX, this.mPosImageY,
		helpImages.get(MenuConstantsUtility.MENU_HELP_IMAGE_ARMORS),
		MainParameters.vbom);
	// Items Image
	this.mSpriteItems = new Sprite(this.mPosImageX, this.mPosImageY,
		helpImages.get(MenuConstantsUtility.MENU_HELP_IMAGE_ITEMS),
		MainParameters.vbom);
	// Header Text
	this.mTextHeader = new Text(MenuConstantsUtility.SCREEN_POS_CENTER_X,
		MenuConstantsUtility.MENU_HEADER_POS_Y, this.mFont,
		menuHeaderLiteral, 400, new TextOptions(AutoWrap.WORDS, 400,
			HorizontalAlign.CENTER, Text.LEADING_DEFAULT),
		MainParameters.vbom);
	// SubHeader Text
	this.mTextSubHeader = new Text(this.mPosSubHeaderTextX,
		this.mPosSubHeaderTextY, this.mFont, menuHeaderSubTitle, 400,
		new TextOptions(AutoWrap.WORDS, 400, HorizontalAlign.CENTER,
			Text.LEADING_DEFAULT), MainParameters.vbom);
	// Description Text
	this.mTextDescription = new Text(this.mPosDescriptionTextX,
		this.mPosDescriptionTextY, this.mFont, menuDescriptionWeapons,
		800, new TextOptions(AutoWrap.WORDS, 800, HorizontalAlign.LEFT,
			Text.LEADING_DEFAULT), MainParameters.vbom);

	// INITIAL ITEM POSISTIONS

	// INITIAL ITEM SCALES
	this.mSpriteWeapons.setScale(1.5f);
	this.mSpriteArmors.setScale(1.5f);
	this.mSpriteItems.setScale(1.5f);
	this.mTextHeader.setScale(1.5f);
	this.mTextSubHeader.setScale(1.2f);

	// INITIAL ITEM ALPHAS
	this.mSpriteLogo.setAlpha(0.4f);
	this.mSpriteWeapons.setAlpha(0);
	this.mSpriteArmors.setAlpha(0);
	this.mSpriteItems.setAlpha(0);
	this.mTextHeader.setAlpha(0);
	this.mTextSubHeader.setAlpha(0);
	this.mTextDescription.setAlpha(0);

	// OTHER INITIAL CONFIG
	this.mTextHeader.setColor(MenuConstantsUtility.MENU_HEADER_COLOR);
	this.mTextSubHeader
		.setColor(MenuConstantsUtility.MENU_SUB_HEADER_COLOR);

	// ATTACH ITEMS
	this.attachChild(this.mSpriteBackground);
	this.attachChild(this.mSpriteLogo);
	this.attachChild(this.mSpriteWeapons);
	this.attachChild(this.mSpriteArmors);
	this.attachChild(this.mSpriteItems);
	this.attachChild(this.mTextHeader);
	this.attachChild(this.mTextSubHeader);
	this.attachChild(this.mTextDescription);
    }

    /**
     * Loads all clickable items on the screen.
     */
    @Override
    public void createChildMenuScene() {
	// RESOURCES
	HashMap<Integer, ITextureRegion> listButtons = this.mMenuResourceManager
		.getButtonsTexture();
	String buttonBackLiteral = MainParameters.activity
		.getString(R.string.btn_back);
	String buttonWeaponsLiteral = MainParameters.activity
		.getString(R.string.btn_weapons);
	String buttonArmorsLiteral = MainParameters.activity
		.getString(R.string.btn_armors);
	String buttonItemsLiteral = MainParameters.activity
		.getString(R.string.btn_items);

	// CREATE ITEMS
	// ChildMenuScene
	this.mChildMenuScene = new MenuScene(MainParameters.camera);
	// Back button
	this.mButtonBack = new ScaleMenuItemDecorator(new SpriteMenuItem(
		backOption,
		listButtons.get(MenuConstantsUtility.MENU_BUTTON_LEFT),
		MainParameters.vbom),
		MenuConstantsUtility.MENU_SELECTED_BUTTON_SIZE,
		MenuConstantsUtility.MENU_UNSELECTED_BUTTON_SIZE);
	// Weapons button
	this.mButtonWeapons = new ScaleMenuItemDecorator(new SpriteMenuItem(
		weaponsOption,
		listButtons.get(MenuConstantsUtility.MENU_BUTTON_MIDDLE_LEFT),
		MainParameters.vbom),
		MenuConstantsUtility.MENU_SELECTED_BUTTON_SIZE,
		MenuConstantsUtility.MENU_UNSELECTED_BUTTON_SIZE);
	// Armors button
	this.mButtonArmors = new ScaleMenuItemDecorator(new SpriteMenuItem(
		armorsOption,
		listButtons.get(MenuConstantsUtility.MENU_BUTTON_MIDDLE_RIGHT),
		MainParameters.vbom),
		MenuConstantsUtility.MENU_SELECTED_BUTTON_SIZE,
		MenuConstantsUtility.MENU_UNSELECTED_BUTTON_SIZE);
	// Items button
	this.mButtonItems = new ScaleMenuItemDecorator(new SpriteMenuItem(
		itemsOption,
		listButtons.get(MenuConstantsUtility.MENU_BUTTON_RIGHT),
		MainParameters.vbom),
		MenuConstantsUtility.MENU_SELECTED_BUTTON_SIZE,
		MenuConstantsUtility.MENU_UNSELECTED_BUTTON_SIZE);
	// Back button text.
	final TextMenuItem buttonBackText = new TextMenuItem(backOption,
		this.mFont, buttonBackLiteral, MainParameters.vbom);
	// General button text.
	final TextMenuItem butttonWeaponsText = new TextMenuItem(weaponsOption,
		this.mFont, buttonWeaponsLiteral, MainParameters.vbom);
	// General button text.
	final TextMenuItem buttontArmorsText = new TextMenuItem(armorsOption,
		this.mFont, buttonArmorsLiteral, MainParameters.vbom);
	// General button text.
	final TextMenuItem buttonItemsText = new TextMenuItem(itemsOption,
		this.mFont, buttonItemsLiteral, MainParameters.vbom);

	// INITIAL ITEM POSISTIONS
	buttonBackText.setPosition(this.mButtonBack.getWidth() / 2,
		this.mButtonBack.getHeight() / 2);
	butttonWeaponsText.setPosition(this.mButtonWeapons.getWidth() / 2,
		this.mButtonWeapons.getHeight() / 2);
	buttontArmorsText.setPosition(this.mButtonArmors.getWidth() / 2,
		this.mButtonArmors.getHeight() / 2);
	buttonItemsText.setPosition(this.mButtonItems.getWidth() / 2,
		this.mButtonItems.getHeight() / 2);
	this.mButtonWeapons.setPosition(this.mPosWeaponsBtnX,
		MenuConstantsUtility.MENU_BUTTONS_POS_Y_HIDDEN);
	this.mButtonArmors.setPosition(this.mPosArmorsBtnX,
		MenuConstantsUtility.MENU_BUTTONS_POS_Y_HIDDEN);
	this.mButtonItems.setPosition(this.mPosItemsBtnX,
		MenuConstantsUtility.MENU_BUTTONS_POS_Y_HIDDEN);
	this.mButtonBack.setPosition(this.mPosBackBtnX,
		MenuConstantsUtility.MENU_BUTTONS_POS_Y_HIDDEN);

	// OTHER INITIAL CONFIG
	this.mChildMenuScene.setBackgroundEnabled(false);
	this.mChildMenuScene.setOnMenuItemClickListener(this);

	// ATTACH ITEMS
	this.mButtonBack.attachChild(buttonBackText);
	this.mButtonWeapons.attachChild(butttonWeaponsText);
	this.mButtonArmors.attachChild(buttontArmorsText);
	this.mButtonItems.attachChild(buttonItemsText);
	this.mChildMenuScene.addMenuItem(this.mButtonBack);
	this.mChildMenuScene.addMenuItem(this.mButtonWeapons);
	this.mChildMenuScene.addMenuItem(this.mButtonArmors);
	this.mChildMenuScene.addMenuItem(this.mButtonItems);
	this.setChildScene(this.mChildMenuScene);
    }

    /**
     * Apply the effects when scene is attached
     */
    @Override
    public void applyScreenInActions() {
	this.mTextSubHeader.setText(MainParameters.activity
		.getString(R.string.help_subtitle_weapons));
	this.mTextDescription.setText(MainParameters.activity
		.getString(R.string.help_weapons_description));

	this.regMoveEntityModifier(this.mButtonBack,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, this.mPosBackBtnX,
		MenuConstantsUtility.MENU_BUTTONS_POS_Y_HIDDEN,
		this.mPosBackBtnX,
		MenuConstantsUtility.MENU_BUTTONS_POS_Y_SHOWN, true);

	this.regMoveEntityModifier(this.mButtonWeapons,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED,
		this.mPosWeaponsBtnX,
		MenuConstantsUtility.MENU_BUTTONS_POS_Y_HIDDEN,
		this.mPosWeaponsBtnX,
		MenuConstantsUtility.MENU_BUTTONS_POS_Y_SHOWN, true);

	this.regMoveEntityModifier(this.mButtonArmors,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED,
		this.mPosArmorsBtnX,
		MenuConstantsUtility.MENU_BUTTONS_POS_Y_HIDDEN,
		this.mPosArmorsBtnX,
		MenuConstantsUtility.MENU_BUTTONS_POS_Y_SHOWN, true);

	this.regMoveEntityModifier(this.mButtonItems,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED,
		this.mPosItemsBtnX,
		MenuConstantsUtility.MENU_BUTTONS_POS_Y_HIDDEN,
		this.mPosItemsBtnX,
		MenuConstantsUtility.MENU_BUTTONS_POS_Y_SHOWN, true);

	this.regFadeInEntityModifier(this.mTextHeader,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeInEntityModifier(this.mTextSubHeader,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeInEntityModifier(this.mTextDescription,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeInEntityModifier(this.mSpriteWeapons,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
    }

    /**
     * Apply the effects when scene is detached
     */
    @Override
    public void applyScreenOutActions() {
	MoveModifier moveOut = new MoveModifier(
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED,
		this.mPosItemsBtnX,
		MenuConstantsUtility.MENU_BUTTONS_POS_Y_SHOWN,
		this.mPosItemsBtnX,
		MenuConstantsUtility.MENU_BUTTONS_POS_Y_HIDDEN);

	moveOut.addModifierListener(new IEntityModifierListener() {
	    @Override
	    public void onModifierStarted(IModifier<IEntity> pModifier,
		    IEntity pItem) {
	    }

	    @Override
	    public void onModifierFinished(final IModifier<IEntity> pModifier,
		    final IEntity pItem) {
		HelpMenuScene.this.mSceneController.createMainMenuScene(null);
		pModifier.removeModifierListener(this);
	    }
	});

	switch (this.mSelectedOption) {

	case weaponsOption:
	    this.regFadeOutEntityModifier(this.mSpriteWeapons,
		    MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	    break;

	case armorsOption:
	    this.regFadeOutEntityModifier(this.mSpriteArmors,
		    MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	    break;

	case itemsOption:
	    this.regFadeOutEntityModifier(this.mSpriteItems,
		    MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	    break;
	}

	this.regFadeOutEntityModifier(this.mTextHeader,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeOutEntityModifier(this.mTextSubHeader,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);
	this.regFadeOutEntityModifier(this.mTextDescription,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, true);

	this.regMoveEntityModifier(this.mButtonBack,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED, this.mPosBackBtnX,
		MenuConstantsUtility.MENU_BUTTONS_POS_Y_SHOWN,
		this.mPosBackBtnX,
		MenuConstantsUtility.MENU_BUTTONS_POS_Y_HIDDEN, true);

	this.regMoveEntityModifier(this.mButtonWeapons,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED,
		this.mPosWeaponsBtnX,
		MenuConstantsUtility.MENU_BUTTONS_POS_Y_SHOWN,
		this.mPosWeaponsBtnX,
		MenuConstantsUtility.MENU_BUTTONS_POS_Y_HIDDEN, true);

	this.regMoveEntityModifier(this.mButtonArmors,
		MenuConstantsUtility.ANIMATION_NORMAL_SPEED,
		this.mPosArmorsBtnX,
		MenuConstantsUtility.MENU_BUTTONS_POS_Y_SHOWN,
		this.mPosArmorsBtnX,
		MenuConstantsUtility.MENU_BUTTONS_POS_Y_HIDDEN, true);

	this.regModifyEntity(this.mButtonItems, moveOut, true);

	// Save the buttons selected
	this.mSelectedOption = weaponsOption;
    }

    /**
     * Listen when press a button on this scene
     */
    @Override
    public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
	    float pMenuItemLocalX, float pMenuItemLocalY) {

	switch (pMenuItem.getID()) {

	case backOption:
	    this.performBackAction();
	    return true;

	case weaponsOption:
	    this.clickWeapons();
	    return true;

	case armorsOption:
	    this.clickArmors();
	    return true;

	case itemsOption:
	    this.clickItems();
	    return true;

	default:
	    return false;
	}
    }

    /**
     * Dispose all resources for the scene
     */
    @Override
    public void disposeScene() {
	// Runs the detach and dispose operations on a different Thread
	MainParameters.engine.runOnUpdateThread(new Runnable() {
	    @Override
	    public void run() {
		// DETACH ENTITIES
		// Detaches the elements from the background
		mSpriteBackground.detachSelf();
		mSpriteLogo.detachSelf();
		mSpriteWeapons.detachSelf();
		mSpriteArmors.detachSelf();
		mSpriteItems.detachSelf();
		// Detaches the buttons from the menu
		mTextHeader.detachSelf();
		mTextSubHeader.detachSelf();
		mTextDescription.detachSelf();
		mButtonBack.detachSelf();
		mButtonWeapons.detachSelf();
		mButtonArmors.detachSelf();
		mButtonItems.detachSelf();

		// DISPOSE
		// Disposes the elements from the background
		mSpriteBackground.dispose();
		mSpriteLogo.dispose();
		mSpriteWeapons.dispose();
		mSpriteArmors.dispose();
		mSpriteItems.dispose();
		// Detaches the buttons from the menu
		mTextHeader.dispose();
		mTextSubHeader.dispose();
		mTextDescription.dispose();
		mButtonBack.dispose();
		mButtonWeapons.dispose();
		mButtonArmors.dispose();
		mButtonItems.dispose();
		// Detaches the MenuScene
		mChildMenuScene.detachSelf();
		// Disposes the MenuScene
		mChildMenuScene.dispose();

		// SET NULL
		// Sets other objects to null
		mFont = null;

		// DISPOSE THIS SCENE
		// Disposes the Scene
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

    // ===========================================================
    // Methods
    // ===========================================================

    /**
     * Defines the actions done on a back action.
     */
    private void performBackAction() {
	this.applyScreenOutActions();
    }

    /**
     * Defines actions when press the weapons button
     */
    private void clickWeapons() {
	if (this.mSelectedOption != weaponsOption) {
	    // Apply modifiers
	    FadeOutModifier changeImage = new FadeOutModifier(
		    MenuConstantsUtility.ANIMATION_FAST_SPEED);
	    changeImage.addModifierListener(new IEntityModifierListener() {
		@Override
		public void onModifierStarted(IModifier<IEntity> pModifier,
			IEntity pItem) {
		}

		@Override
		public void onModifierFinished(
			final IModifier<IEntity> pModifier, final IEntity pItem) {
		    // Apply fade in to new image
		    HelpMenuScene.this.regFadeInEntityModifier(mSpriteWeapons,
			    MenuConstantsUtility.ANIMATION_FAST_SPEED, false);
		    // Remove last modifiers
		    pModifier.removeModifierListener(this);
		}
	    });

	    FadeOutModifier changeSubHeader = new FadeOutModifier(
		    MenuConstantsUtility.ANIMATION_FAST_SPEED);
	    changeSubHeader.addModifierListener(new IEntityModifierListener() {
		@Override
		public void onModifierStarted(IModifier<IEntity> pModifier,
			IEntity pItem) {
		}

		@Override
		public void onModifierFinished(
			final IModifier<IEntity> pModifier, final IEntity pItem) {
		    // Change the subtitle text
		    ((Text) pItem).setText(MainParameters.activity
			    .getString(R.string.help_subtitle_weapons));
		    // Apply fade in to new subtitle
		    HelpMenuScene.this.regFadeInEntityModifier(pItem,
			    MenuConstantsUtility.ANIMATION_FAST_SPEED, false);
		    pModifier.removeModifierListener(this);
		}
	    });

	    FadeOutModifier changeDescription = new FadeOutModifier(
		    MenuConstantsUtility.ANIMATION_FAST_SPEED);
	    changeDescription
		    .addModifierListener(new IEntityModifierListener() {
			@Override
			public void onModifierStarted(
				IModifier<IEntity> pModifier, IEntity pItem) {
			}

			@Override
			public void onModifierFinished(
				final IModifier<IEntity> pModifier,
				final IEntity pItem) {
			    // Change the description text
			    ((Text) pItem).setText(MainParameters.activity
				    .getString(R.string.help_weapons_description));
			    // Apply fade in to new descriptionF
			    HelpMenuScene.this.regFadeInEntityModifier(pItem,
				    MenuConstantsUtility.ANIMATION_FAST_SPEED,
				    false);
			    pModifier.removeModifierListener(this);
			}
		    });

	    // Hidden the last image displayed
	    switch (this.mSelectedOption) {

	    case armorsOption:
		this.regModifyEntity(mSpriteArmors, changeImage, true);
		break;

	    case itemsOption:
		this.regModifyEntity(mSpriteItems, changeImage, true);
		break;
	    }

	    // Hidden the last text displayed
	    this.regModifyEntity(mTextSubHeader, changeSubHeader, true);
	    this.regModifyEntity(mTextDescription, changeDescription, true);

	    // Save the new option selected
	    this.mSelectedOption = this.weaponsOption;
	}
    }

    /**
     * Defines actions when press the armors button
     */
    private void clickArmors() {
	if (this.mSelectedOption != armorsOption) {

	    FadeOutModifier changeImage = new FadeOutModifier(
		    MenuConstantsUtility.ANIMATION_FAST_SPEED);
	    changeImage.addModifierListener(new IEntityModifierListener() {
		@Override
		public void onModifierStarted(IModifier<IEntity> pModifier,
			IEntity pItem) {
		}

		@Override
		public void onModifierFinished(
			final IModifier<IEntity> pModifier, final IEntity pItem) {
		    HelpMenuScene.this.regFadeInEntityModifier(mSpriteArmors,
			    MenuConstantsUtility.ANIMATION_FAST_SPEED, false);
		    pModifier.removeModifierListener(this);
		}
	    });

	    FadeOutModifier changeSubHeader = new FadeOutModifier(
		    MenuConstantsUtility.ANIMATION_FAST_SPEED);
	    changeSubHeader.addModifierListener(new IEntityModifierListener() {
		@Override
		public void onModifierStarted(IModifier<IEntity> pModifier,
			IEntity pItem) {
		}

		@Override
		public void onModifierFinished(
			final IModifier<IEntity> pModifier, final IEntity pItem) {
		    ((Text) pItem).setText(MainParameters.activity
			    .getString(R.string.help_subtitle_armors));
		    HelpMenuScene.this.regFadeInEntityModifier(pItem,
			    MenuConstantsUtility.ANIMATION_FAST_SPEED, false);
		    pModifier.removeModifierListener(this);
		}
	    });

	    FadeOutModifier changeDescription = new FadeOutModifier(
		    MenuConstantsUtility.ANIMATION_FAST_SPEED);
	    changeDescription
		    .addModifierListener(new IEntityModifierListener() {
			@Override
			public void onModifierStarted(
				IModifier<IEntity> pModifier, IEntity pItem) {
			}

			@Override
			public void onModifierFinished(
				final IModifier<IEntity> pModifier,
				final IEntity pItem) {
			    ((Text) pItem).setText(MainParameters.activity
				    .getString(R.string.help_armors_description));
			    HelpMenuScene.this.regFadeInEntityModifier(pItem,
				    MenuConstantsUtility.ANIMATION_FAST_SPEED,
				    false);
			    pModifier.removeModifierListener(this);
			}
		    });

	    switch (this.mSelectedOption) {

	    case weaponsOption:
		this.regModifyEntity(mSpriteWeapons, changeImage, true);
		break;

	    case itemsOption:
		this.regModifyEntity(mSpriteItems, changeImage, true);
		break;
	    }

	    this.regModifyEntity(mTextSubHeader, changeSubHeader, true);
	    this.regModifyEntity(mTextDescription, changeDescription, true);

	    this.mSelectedOption = armorsOption;
	}
    }

    /**
     * Defines actions when press the items button
     */
    private void clickItems() {
	if (this.mSelectedOption != itemsOption) {

	    FadeOutModifier changeImage = new FadeOutModifier(
		    MenuConstantsUtility.ANIMATION_FAST_SPEED);
	    changeImage.addModifierListener(new IEntityModifierListener() {
		@Override
		public void onModifierStarted(IModifier<IEntity> pModifier,
			IEntity pItem) {
		}

		@Override
		public void onModifierFinished(
			final IModifier<IEntity> pModifier, final IEntity pItem) {
		    HelpMenuScene.this.regFadeInEntityModifier(mSpriteItems,
			    MenuConstantsUtility.ANIMATION_FAST_SPEED, false);
		    pModifier.removeModifierListener(this);
		}
	    });

	    FadeOutModifier changeSubHeader = new FadeOutModifier(
		    MenuConstantsUtility.ANIMATION_FAST_SPEED);
	    changeSubHeader.addModifierListener(new IEntityModifierListener() {
		@Override
		public void onModifierStarted(IModifier<IEntity> pModifier,
			IEntity pItem) {
		}

		@Override
		public void onModifierFinished(
			final IModifier<IEntity> pModifier, final IEntity pItem) {
		    ((Text) pItem).setText(MainParameters.activity
			    .getString(R.string.help_subtitle_items));
		    HelpMenuScene.this.regFadeInEntityModifier(pItem,
			    MenuConstantsUtility.ANIMATION_FAST_SPEED, false);
		    pModifier.removeModifierListener(this);
		}
	    });

	    FadeOutModifier changeDescription = new FadeOutModifier(
		    MenuConstantsUtility.ANIMATION_FAST_SPEED);
	    changeDescription
		    .addModifierListener(new IEntityModifierListener() {
			@Override
			public void onModifierStarted(
				IModifier<IEntity> pModifier, IEntity pItem) {
			}

			@Override
			public void onModifierFinished(
				final IModifier<IEntity> pModifier,
				final IEntity pItem) {
			    ((Text) pItem).setText(MainParameters.activity
				    .getString(R.string.help_items_description));
			    HelpMenuScene.this.regFadeInEntityModifier(pItem,
				    MenuConstantsUtility.ANIMATION_FAST_SPEED,
				    false);
			    pModifier.removeModifierListener(this);
			}
		    });

	    switch (this.mSelectedOption) {

	    case armorsOption:
		this.regModifyEntity(mSpriteArmors, changeImage, true);
		break;

	    case weaponsOption:
		this.regModifyEntity(mSpriteWeapons, changeImage, true);
		break;
	    }

	    this.regModifyEntity(mTextSubHeader, changeSubHeader, true);
	    this.regModifyEntity(mTextDescription, changeDescription, true);

	    this.mSelectedOption = itemsOption;
	}
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}
