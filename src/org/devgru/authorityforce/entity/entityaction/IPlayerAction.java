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

package org.devgru.authorityforce.entity.entityaction;

import org.devgru.authorityforce.entity.Bullet;

/**
 * IPlayerAction.java
 * 
 * Interface to all players
 * 
 * @version 1.0 Apr 19, 2013
 * @author Aitor Gonzalez Martinez
 * @author Xavier Martinez de Francisco
 * @author Toni Trigo Diaz
 */
public interface IPlayerAction {

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    /**
     * The move of the player
     * 
     * @param pX
     *            the x coordinate of the joystick
     * @param pY
     *            the y coordinate of the joystick
     */
    public void move(final float pX, final float pY);

    /**
     * The attack of the player
     * 
     * @param pWeaponUsed
     *            the weapon that makes the attack
     */
    public Bullet attack(int pWeaponUsed);

}
