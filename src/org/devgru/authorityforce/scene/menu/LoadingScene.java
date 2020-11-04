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

import java.util.Random;

import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.AutoWrap;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.opengl.font.Font;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.color.Color;
import org.devgru.authorityforce.R;
import org.devgru.authorityforce.constant.GeneralConstantsUtility;
import org.devgru.authorityforce.constant.GeneralConstantsUtility.SceneType;
import org.devgru.authorityforce.constant.MainParameters;
import org.devgru.authorityforce.constant.MenuConstantsUtility;
import org.devgru.authorityforce.scene.ABaseMenuScene;

/**
 * LoadScene.java
 * 
 * This class is Game loading screen
 * 
 * @version 1.0 Apr 19, 2013
 * @author Aitor Gonzalez Martinez
 * @author Xavier Martinez de Francisco
 * @author Toni Trigo Diaz
 */
public class LoadingScene extends ABaseMenuScene {

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    // POSITIONS
    /* Position X to Background position. */
    private final int mPosBackgroundX = MenuConstantsUtility.SCREEN_POS_CENTER_X;
    /* Position Y to Background position. */
    private final int mPosBackgroundY = MenuConstantsUtility.SCREEN_POS_CENTER_Y;
    /* Position X to Logo position. */
    private final int mPosLogoX = MenuConstantsUtility.SCREEN_POS_CENTER_X;
    /* Position Y to Logo position. */
    private final int mPosLogoY = MenuConstantsUtility.SCREEN_POS_CENTER_Y + 100;

    /* Position X to Loading map text. */
    private final int mPosLoadingTextX = MenuConstantsUtility.SCREEN_POS_CENTER_X;
    /* Position Y to Loading map text. */
    private final int mPosLoadingTextY = 225;
    /* Position X to Random sentence text position. */
    private final int mPosRandomSentenceTextX = MenuConstantsUtility.SCREEN_POS_CENTER_X;
    /* Position Y to Random sentence text position. */
    private final int mPosRandomSentenceTextY = 150;

    // ITEMS TO BASESCENE
    /* The sprite for the logo. */
    private Sprite mSpriteLogo;
    /* The sprite for the background; */
    private Sprite mSpriteBackground;
    /* The Text for the loading message. */
    private Text mTextLoading;
    /* The Sentence for the loading message. */
    private Text mTextSentence;
    /* The Author of sentence for the loading message. */
    private Text mTextAuthor;

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
    }

    /**
     * Returns the type of scene
     * 
     * @return returns the type of scene
     */
    @Override
    public SceneType getSceneType() {
	return SceneType.LOADING_SCENE;
    }

    /**
     * Loads all the screen elements the user can not interact with.
     */
    @Override
    public void createBaseScene() {
	// RESOURCES
	// Get the font.
	Font font = (Font) this.mMenuResourceManager.getFont();

	// CREATE ITEMS
	// Create background.
	this.mSpriteBackground = new Sprite(this.mPosBackgroundX,
		this.mPosBackgroundY,
		this.mMenuResourceManager.getBackgroundTexture(),
		MainParameters.vbom);
	// Create logo.
	this.mSpriteLogo = new Sprite(this.mPosLogoX, this.mPosLogoY,
		this.mMenuResourceManager.getLogoTexture(), MainParameters.vbom);
	mSpriteLogo.setScale(0.7f);

	// Create the text for the loading message.
	this.mTextLoading = new Text(this.mPosLoadingTextX,
		this.mPosLoadingTextY, font,
		MainParameters.activity.getString(R.string.loading_text),
		new TextOptions(AutoWrap.WORDS, 500, HorizontalAlign.CENTER,
			Text.LEADING_DEFAULT), MainParameters.vbom);
	this.mTextLoading.setColor(Color.GREEN);

	// Famous Sentence
	this.mTextSentence = new Text(this.mPosRandomSentenceTextX,
		this.mPosRandomSentenceTextY, font, "", 400, new TextOptions(
			AutoWrap.WORDS,
			GeneralConstantsUtility.SCREEN_WIDTH_RESOLUTION,
			HorizontalAlign.CENTER, Text.LEADING_DEFAULT),
		MainParameters.vbom);
	// Author of the phrase
	this.mTextAuthor = new Text(this.mPosRandomSentenceTextX,
		this.mPosRandomSentenceTextY - 80, font, "", 400,
		new TextOptions(AutoWrap.WORDS,
			GeneralConstantsUtility.SCREEN_WIDTH_RESOLUTION,
			HorizontalAlign.CENTER, Text.LEADING_DEFAULT),
		MainParameters.vbom);

	// Generate the random sentence
	this.setRandomSentence();

	// INITIAL ITEMS ALPHA
	this.mTextLoading.setAlpha(0);
	this.mTextSentence.setAlpha(0);
	this.mTextAuthor.setAlpha(0);

	// ATTACH ITEMS
	this.attachChild(mSpriteBackground);
	this.attachChild(mSpriteLogo);
	this.attachChild(this.mTextLoading);
	this.attachChild(this.mTextSentence);
	this.attachChild(this.mTextAuthor);
    }

    /**
     * Loads all clickable items on the screen.
     */
    @Override
    public void createChildMenuScene() {
	// Not implemented because the user does not interact with this scene
    }

    /**
     * Apply the effects when scene is attached
     */
    @Override
    public void applyScreenInActions() {
	this.mSpriteLogo.registerEntityModifier(new LoopEntityModifier(
		new RotationModifier(5, 0, 360)));
	this.regFadeInEntityModifier(this.mTextLoading,
		MenuConstantsUtility.ANIMATION_FAST_SPEED, true);
	this.regFadeInEntityModifier(this.mTextSentence,
		MenuConstantsUtility.ANIMATION_FAST_SPEED, true);
	this.regFadeInEntityModifier(this.mTextAuthor,
		MenuConstantsUtility.ANIMATION_FAST_SPEED, true);
    }

    /**
     * Apply the effects when scene is detached
     */
    @Override
    public void applyScreenOutActions() {
	// Register new modifiers.
	this.regFadeOutEntityModifier(this.mSpriteLogo,
		MenuConstantsUtility.ANIMATION_FAST_SPEED, true);
	this.regFadeOutEntityModifier(this.mTextLoading,
		MenuConstantsUtility.ANIMATION_FAST_SPEED, true);
	this.regFadeOutEntityModifier(this.mTextSentence,
		MenuConstantsUtility.ANIMATION_FAST_SPEED, true);
	this.regFadeOutEntityModifier(this.mTextAuthor,
		MenuConstantsUtility.ANIMATION_FAST_SPEED, true);
    }

    /**
     * Listen when press a button on the Loading Scene
     */
    @Override
    public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
	    float pMenuItemLocalX, float pMenuItemLocalY) {
	// Not implemented because the user does not interact with this scene
	return false;
    }

    /**
     * Dispose all resources for the scene
     */
    @Override
    public void disposeScene() {
	// Not implemented because this scene never has dispose
    }

    // ===========================================================
    // Methods
    // ===========================================================

    /**
     * Generate new random sentence
     */
    public void setRandomSentence() {
	// Obtain all sentences from file String.xml
	String[] sentences = MainParameters.activity.getResources()
		.getStringArray(R.array.randomSentencesArray);
	// Select one randomly
	final String randomSentence = sentences[new Random().nextInt(70)];
	// Separate the phrase of the author
	String[] fields = randomSentence.split("\n");
	// Set the phrase
	this.mTextSentence.setText(fields[0]);
	// Set the author
	this.mTextAuthor.setText(fields[1]);
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}
