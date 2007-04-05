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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.font.TextLayout;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class GACanvas
        extends JPanel
{
    private static final Color BACKGROUND = new Color(255, 255, 235);
    private static final Color FOREGROUND = new Color(128, 0, 0);
    private static final int OFFSET = 70;
    List<Double> fitnesses = new ArrayList<Double>();
    private Image offScreenBuffer;
    private int numGens = 0;
    private Double optima = 0.0;
    private double xScale;
    private double yScale;

    public GACanvas()
    {
    // createBufferStrategy(2);
    }

    public void init(int xfactor, Double yfactor)
    {
        this.fitnesses = new ArrayList<Double>();
        this.numGens = xfactor;
        this.optima = yfactor;
    }

    public void clear()
    {
        init(0, 0.0);
        repaint();
    }

    public void addFitnessValue(Double fitness)
    {
        fitnesses.add(fitness);
        bufferedPaint();
        // paint(getGraphics());
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

        xScale = (d.width - 2 * OFFSET) * 0.8 / numGens;
        yScale = (d.height - 2 * OFFSET) * 0.8 / optima;

        g2.translate(OFFSET, d.height - OFFSET);
        g2.setFont(Font.decode("Lucida Sans Demibold"));
        drawAxesLabels(g2, d);

        g2.scale(1, -1);

        drawAxes(g2, d);
        drawOptimalFitness(g2, d);
        drawFitnesses(g2, d);
    }

    private void drawAxesLabels(Graphics2D g2, Dimension d)
    {
        int xAxisLength = d.width - 2 * OFFSET;
        int yAxisLength = d.height - 2 * OFFSET;
        g2.drawString("Generation Number", xAxisLength / 3, 35);
        g2.drawString("Best", -OFFSET + 5, -yAxisLength / 2);
        g2.drawString("Fitness", -OFFSET + 5, -yAxisLength / 2 + 15);

        if (numGens != 0) {
            Font f = g2.getFont();
            TextLayout tl = new TextLayout(Integer.toString(numGens), f, g2
                    .getFontRenderContext());
            tl.draw(g2, (float)(xScale * numGens - 5), 20.0f);
            tl = new TextLayout(Double.toString(optima), f, g2
                    .getFontRenderContext());
            tl.draw(g2, -30.0f, -(float)(yScale * optima - 5));
        }
    }

    private void drawAxes(Graphics2D g2, Dimension d)
    {
        int tickSize = 4;

        GeneralPath axes = new GeneralPath(GeneralPath.WIND_EVEN_ODD);

        // x-axis.
        int xAxisLength = d.width - 2 * OFFSET;
        axes.moveTo(-OFFSET, 0);
        axes.lineTo(xAxisLength, 0);

        // y-axis.
        int yAxisLength = d.height - 2 * OFFSET;
        axes.moveTo(0, -OFFSET);
        axes.lineTo(0, yAxisLength);

        Stroke stroke = new BasicStroke(2, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_ROUND);
        g2.setStroke(stroke);
        g2.setPaint(Color.DARK_GRAY);
        g2.draw(axes);

        GeneralPath axesTicks = new GeneralPath(GeneralPath.WIND_EVEN_ODD);
        float cm = 72 / 2.54f;

        // x-axis tick marks
        float lengthCentimeter = xAxisLength / cm;
        for (float i = 1.0f; i < lengthCentimeter; i += 1.0f) {
            float tick = i * cm;
            axesTicks.moveTo(tick, -tickSize);
            axesTicks.lineTo(tick, tickSize);
        }

        // y-axis tick marks
        lengthCentimeter = yAxisLength / cm;
        for (float i = 1.0f; i < lengthCentimeter; i += 1.0f) {
            float tick = i * cm;
            axesTicks.moveTo(-tickSize, tick);
            axesTicks.lineTo(tickSize, tick);
        }

        stroke = new BasicStroke(1, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_ROUND);
        g2.setStroke(stroke);
        g2.setPaint(Color.GRAY);
        g2.draw(axesTicks);
    }

    private void drawOptimalFitness(Graphics2D g2, Dimension d)
    {
        Stroke stroke = new BasicStroke(2, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_ROUND);
        g2.setStroke(stroke);
        g2.setPaint(Color.LIGHT_GRAY);

        GeneralPath p = new GeneralPath(GeneralPath.WIND_EVEN_ODD);

        int x = 1;
        p.moveTo(0, (float)(yScale * optima));
        for (Double fitness : fitnesses) {
            p.lineTo((float)(xScale * x), (float)(yScale * optima));
            p.moveTo((float)(xScale * x++), (float)(yScale * optima));
        }

        g2.draw(p);
    }

    private void drawFitnesses(Graphics2D g2, Dimension d)
    {
        Stroke stroke = new BasicStroke(3, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_ROUND);
        g2.setStroke(stroke);
        g2.setPaint(FOREGROUND);

        GeneralPath p = new GeneralPath(GeneralPath.WIND_EVEN_ODD);

        int x = 1;
        p.moveTo(0, 0);
        for (Double fitness : fitnesses) {
            p.lineTo((float)(xScale * x), (float)(yScale * fitness));
            p.moveTo((float)(xScale * x++), (float)(yScale * fitness));
        }
        g2.draw(p);
    }

    protected void bufferedPaint()
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
