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

package org.trypticon.hex.examples;

import org.trypticon.hex.HexViewer;
import org.trypticon.hex.accessory.AccessoryBar;
import org.trypticon.hex.accessory.ExpandableAccessoryBar;
import org.trypticon.hex.binary.Binary;
import org.trypticon.hex.binary.BinaryFactory;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.nio.file.Paths;
import java.util.prefs.Preferences;

/**
 * Basic example for showing a hex viewer.
 *
 * @author trejkaz
 */
public class HexViewerExample {
    public static void main(String[] args) throws Exception {
        // Replace this with a file you have available.
        //final Binary binary = BinaryFactory.open(Paths.get("/Volumes/Media/Software/OS Updates/snowleopard.dmg"));
        final Binary binary = BinaryFactory.open(Paths.get("/Users/Trejkaz/Downloads/lol emoticons.rtf"));

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                HexViewer viewer = new HexViewer();
                viewer.setBinary(binary);

                AccessoryBar accessoryBar = new ExpandableAccessoryBar(viewer);
                accessoryBar.setPreferencesNode(Preferences.userNodeForPackage(HexViewerExample.class));

                JFrame frame = new JFrame("Example");
                frame.setLayout(new BorderLayout());
                frame.add(viewer, BorderLayout.CENTER);
                frame.add(accessoryBar, BorderLayout.SOUTH);
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}
