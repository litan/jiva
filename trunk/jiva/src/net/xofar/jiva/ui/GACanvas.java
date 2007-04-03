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

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class GACanvas
        extends Canvas
{
    List<Double> fitnesses = new ArrayList<Double>();
    
    public void addFitnessValue(double fitness) {
        fitnesses.add(fitness);
    }
    
    @Override
    public void paint(Graphics g)
    {
        System.out.println("************************************Paint called");
        Graphics2D gr = (Graphics2D)g;
        super.paint(g);
        double prevFitness = 0.0;
        int x = 0;
        for (Double fitness : fitnesses) {
            gr.drawLine(x, (int)(prevFitness*50), ++x, (int)(fitness*50));
            prevFitness = fitness;
        }
    }
    
}
