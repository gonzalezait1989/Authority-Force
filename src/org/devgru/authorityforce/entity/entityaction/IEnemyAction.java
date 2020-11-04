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

/**
 * IEnemyAction.java
 * 
 * Interface to all enemies
 * 
 * @version 1.0 Apr 19, 2013
 * @author Aitor Gonzalez Martinez
 * @author Xavier Martinez de Francisco
 * @author Toni Trigo Diaz
 */
public interface IEnemyAction {

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    /**
     * The behavior of the enemy.
     */
    public void behavior();

    /**
     * The attack of the enemy.
     */
    public void attack();
}
