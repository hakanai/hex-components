/*
 * Hex - a hex viewer and annotator
 * Copyright (C) 2009-2014,2016-2017,2021  Hakanai, Hex Project
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

package org.trypticon.hex;

import javax.swing.BoundedRangeModel;
import javax.swing.JScrollBar;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

/**
 * Helper class to take care of syncing the viewer to its scroll bar.
 *
 * @author trejkaz
 */
class ScrollBarSync {
    /**
     * Maximum value for the scroll bar. It just has to be some value large enough that you won't
     * notice the rounding errors.
     */
    private static final int MAXIMUM = 1000000;

    private final HexViewer viewer;
    private final JScrollBar scrollBar;

    private boolean updating = false;

    ScrollBarSync(HexViewer viewer, JScrollBar scrollBar) {
        this.viewer = viewer;
        this.scrollBar = scrollBar;

        scrollBar.setMinimum(0);
        scrollBar.setMaximum(MAXIMUM);

        scrollBar.addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent event) {
                updatePositionFromScrollBar();
            }
        });

    }

    /**
     * Updates properties of the scroll bar which depend on the view.
     */
    void updateScrollBarFromView() {
        RangeProperties properties = new RangeProperties(viewer);

        if (properties.range <= 0) {
            // The entire binary fits in one page.
            scrollBar.setValue(0);
            scrollBar.setVisibleAmount(MAXIMUM);
        } else {
            double extentRatio = (double) viewer.getVisibleRowCount() / properties.range;
            extentRatio = Math.min(Math.max(0.0, extentRatio), 1.0);
            int extent = (int) (MAXIMUM * extentRatio);
            extent = Math.max(1, extent);
            scrollBar.setVisibleAmount(extent);

            // Block increment is one less than the extent so that the bottom row is still visible
            // at the top after paging down one block.
            double blockIncrementRatio = (double) (viewer.getVisibleRowCount() - 1) / properties.range;
            blockIncrementRatio = Math.min(Math.max(0.0, blockIncrementRatio), 1.0);
            int blockIncrement = (int) (MAXIMUM * blockIncrementRatio);
            blockIncrement = Math.max(1, blockIncrement);
            scrollBar.setBlockIncrement(blockIncrement);
        }
    }

    /**
     * Updates the scroll bar from the current position of the viewer.
     */
    void updateScrollBarFromPosition() {
        if (updating) {
            return;
        }

        updating = true;
        try {
            RangeProperties properties = new RangeProperties(viewer);

            double ratio = ((double) viewer.getFirstVisibleRow() - properties.minPosition) / properties.range;

            scrollBar.setValue((int) (ratio * MAXIMUM));
        } finally {
            updating = false;
        }
    }

    /**
     * Updates the current position of the viewer from the scroll bar..
     */
    private void updatePositionFromScrollBar() {
        if (updating) {
            return;
        }

        updating = true;
        try {
            RangeProperties properties = new RangeProperties(viewer);

            BoundedRangeModel boundedRangeModel = scrollBar.getModel();
            int value = boundedRangeModel.getValue();
            // Special case at the end so that you can drag right to the bottom. Usually it would round down,
            // making it impossible to get there.
            if (value == MAXIMUM - boundedRangeModel.getExtent()) {
                viewer.setFirstVisibleRow(properties.maxPosition);
            } else {
                long row = properties.minPosition + (long) (properties.range * (double) value / MAXIMUM);
                viewer.setFirstVisibleRow(row);
            }
        } finally {
            updating = false;
        }
    }

    /**
     * Holds the logic for computing the min, max, range of the viewer.
     */
    private static class RangeProperties {
        private final int minPosition;
        private final long maxPosition;
        private final long range;

        private RangeProperties(HexViewer viewer) {
            // minPosition accounts for the extra blank row at the start
            // maxPosition accounts for the extra blank row at the end
            minPosition = -1;
            maxPosition = viewer.getRowCount() + 1;
            range = maxPosition - minPosition;
        }
    }
}
