package org.trypticon.hex.examples;

import org.trypticon.hex.HexViewer;
import org.trypticon.hex.anno.MemoryAnnotationCollection;
import org.trypticon.hex.binary.Binary;
import org.trypticon.hex.binary.BinaryFactory;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.io.File;

/**
 * Basic example for showing a hex viewer.
 *
 * @author trejkaz
 */
public class HexViewerExample {
    public static void main(String[] args) throws Exception {
        HexViewer viewer = new HexViewer();
        // Replace this with a file you have available.
        Binary binary = BinaryFactory.open(new File("/Volumes/Media/Software/OS Updates/snowleopard.dmg"));
        viewer.setBinary(binary);
        viewer.setAnnotations(new MemoryAnnotationCollection(binary.length()));

        JFrame frame = new JFrame("Example");
        frame.setLayout(new BorderLayout());
        frame.add(viewer, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
