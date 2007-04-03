/*
 * Copyright (C) 2007 Lalit Pant <pant.lalit@gmail.com>
 *
 * The contents of this file are subject to the GNU General Public License 
 * Version 2 or later (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of
 * the License at http://www.gnu.org/copyleft/gpl.html
 *
 * Software distributed under the License is distributed on an "AS
 * IS" basis, WITHOUT WARRANTY OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * rights and limitations under the License.
 *
 */

package net.xofar.jiva.ui;

import net.xofar.jiva.population.Population;
import net.xofar.util.listener.EventListener;

public class UiGAListener<T>
        implements EventListener<Population<T>>
{
    private GACanvas canvas;

    public UiGAListener(GACanvas canvas)
    {
        this.canvas = canvas;
    }
    
    public void eventFired(Population<T> pop)
    {
        double fitness = pop.getFittest().getFitnessValue(); 
        System.out.println("****** Fittest Value: "
                + fitness);
        canvas.addFitnessValue(fitness);
        canvas.repaint();
    }
}