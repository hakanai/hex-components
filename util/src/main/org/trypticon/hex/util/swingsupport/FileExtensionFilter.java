/*
 * Hex - a hex viewer and annotator
 * Copyright (C) 2009-2012  Trejkaz, Hex Project
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

package org.trypticon.hex.util.swingsupport;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * Filters by file extension.
 *
 * @author trejkaz
 */
public abstract class FileExtensionFilter extends FileFilter {

    /**
     * Gets the description without the bit at the end which has the file extensions.
     *
     * @return the description.
     */
    protected abstract String getShortDescription();

    /**
     * Gets the file extension which the filter will accept.
     *
     * @return the file extension.
     */
    protected abstract String getExtension();

    @Override
    public boolean accept(File file) {
        return file.isDirectory() || file.getName().endsWith('.' + getExtension());
    }

    @Override
    public String getDescription() {
        return String.format("%s (*.%s)", getShortDescription(), getExtension());
    }
}
