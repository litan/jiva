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

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

public class GACanvas
        extends Canvas
{
    private static final Color BACKGROUND = new Color(255, 255, 187);
    private static final Color FOREGROUND = new Color(128, 0, 0);
    List<Double> fitnesses = new ArrayList<Double>();
    private Image offScreenBuffer;

    public GACanvas()
    {
    // createBufferStrategy(2);
    }

    public void addFitnessValue(double fitness)
    {
        fitnesses.add(fitness);
        render();
    }

    @Override
    public void paint(Graphics g)
    {
        Graphics2D g2 = (Graphics2D)g;
        Dimension d = getSize();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setBackground(BACKGROUND);
        g2.clearRect(0, 0, d.width, d.height);

        Stroke stroke = new BasicStroke(3, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_ROUND);
        g2.setStroke(stroke);
        g2.setPaint(FOREGROUND);

        Path2D.Double p = new Path2D.Double(Path2D.WIND_EVEN_ODD);
        int x = 1;
        double xScale = 2;
        double yScale = d.height * 0.8;
        for (Double fitness : fitnesses) {
            if (x == 1) {
                p.moveTo(xScale * x++, yScale * fitness);
            }
            else {
                p.lineTo(xScale * x++, yScale * fitness);
            }
        }
        g2.translate(0, d.height);
        g2.scale(1, -1);
        g2.draw(p);
    }

    protected void render()
    {
        Graphics g = getGraphics();
        if (g != null) {
            Dimension d = getSize();
            if (validateBuffer(d)) {
                Graphics imageGraphics = offScreenBuffer.getGraphics();
                imageGraphics.setColor(getBackground());
                imageGraphics.fillRect(0, 0, d.width, d.height);
                imageGraphics.setColor(getForeground());
                paint(imageGraphics);
                // Now put the offscreen image on the screen.
                g.drawImage(offScreenBuffer, 0, 0, null);
                // Clean up.
                imageGraphics.dispose();
            }
            g.dispose();
        }
    }

    private boolean validateBuffer(Dimension d)
    {
        if (d.width == 0 || d.height == 0) return false;
        if (offScreenBuffer == null
                || offScreenBuffer.getWidth(null) != d.width
                || offScreenBuffer.getHeight(null) != d.height) {
            offScreenBuffer = createImage(d.width, d.height);
        }
        return true;
    }

}
