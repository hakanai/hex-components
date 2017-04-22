/*
 * Hex - a hex viewer and annotator
 * Copyright (C) 2009-2014,2016-2017  Trejkaz, Hex Project
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.trypticon.hex.util;

import javax.swing.plaf.UIResource;
import java.awt.Color;

/**
 * A special {@link Color} derived from a base one. Used so that derived colours can immediately update
 * when used under a look and feel which uses dynamic colour objects (like Aqua.)
 */
public class DerivedColor extends Color implements UIResource {
    private final Color color;
    private final float saturationMultiplier;
    private final float lightnessAddend;
    private final float alphaMultiplier;

    int cachedRGB;
    int cachedForRGB;

    public DerivedColor(Color color, float saturationMultiplier, float lightnessAddend, float alphaMultiplier) {
        super(color.getRGB());
        this.color = color;
        this.saturationMultiplier = saturationMultiplier;
        this.lightnessAddend = lightnessAddend;
        this.alphaMultiplier = alphaMultiplier;
    }

    @Override
    public int getRGB() {
        if (color.getRGB() != cachedForRGB) {
            cachedRGB = deriveColor();
            cachedForRGB = color.getRGB();
        }
        return cachedRGB;
    }

    /**
     * Recalculates the derived color.
     *
     * @return the RGB value.
     */
    public int deriveColor() {
        float[] tmp = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        //tmp[0] = rotate(tmp[0], hOffset);
        tmp[1] = clamp(tmp[1] * saturationMultiplier);
        tmp[2] = clamp(tmp[2] + lightnessAddend);
//        int alpha = clamp(color.getAlpha() + aOffset);
        int alpha = clamp((int) (color.getAlpha() * alphaMultiplier));
        return (Color.HSBtoRGB(tmp[0], tmp[1], tmp[2]) & 0xFFFFFF) | (alpha << 24);
    }

    // Rotate a colour value
    private float rotate(float value, float rotation) {
        value += rotation;
        if (value < 0.0f) {
            value += 1.0f;
        } else if (value >= 1.0f) {
            value -= 1.0f;
        }
        return clamp(value);
    }

    private float clamp(float value) {
        if (value < 0) {
            value = 0;
        } else if (value > 1) {
            value = 1;
        }
        return value;
    }

    private int clamp(int value) {
        if (value < 0) {
            value = 0;
        } else if (value > 255) {
            value = 255;
        }
        return value;
    }
}
