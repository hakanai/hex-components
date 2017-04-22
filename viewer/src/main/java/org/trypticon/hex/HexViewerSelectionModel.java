/*
 * Hex - a hex viewer and annotator
 * Copyright (C) 2009-2014,2017  Trejkaz, Hex Project
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

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

/**
 * Model maintaining the range of selected binary in the viewer.
 *
 * @author trejkaz
 */
public class HexViewerSelectionModel {
    private EventListenerList listenerList;

    private long selectionStart;
    private long selectionEnd;
    private long cursor;

    /**
     * Gets the start of the selection.
     *
     * @return the start of the selection, inclusive.
     */
    public long getSelectionStart() {
        return selectionStart;
    }

    /**
     * Gets the end of the selection.
     *
     * @return the end of the selection, inclusive.
     */
    public long getSelectionEnd() {
        return selectionEnd;
    }

    /**
     * Gets the position of the cursor.
     *
     * @return the position of the cursor.
     */
    public long getCursor() {
        return cursor;
    }

    /**
     * Sets the position of the cursor.
     *
     * @param cursor the position of the cursor.
     */
    public void setCursor(long cursor) {
        if (this.cursor != cursor || selectionStart != cursor || selectionEnd != cursor) {
            this.cursor = cursor;
            selectionStart = cursor;
            selectionEnd = cursor;
            fireStateChanged();
        }
    }

    /**
     * Sets the position of the cursor, extending the selection to move to where the cursor lands.
     *
     * @param cursor the new position of the cursor.
     */
    public void setCursorAndExtendSelection(long cursor) {
        if (this.cursor != cursor) {
            // Finds which end anchors the selection.
            long anchor = this.cursor == selectionStart ? selectionEnd : selectionStart;

            this.cursor = cursor;
            selectionStart = Math.min(anchor, cursor);
            selectionEnd = Math.max(anchor, cursor);

            fireStateChanged();
        }
    }

    /**
     * <p>Sets the selection start and end. Sets the cursor to the start of the selection.</p>
     *
     * <p>Essentially equivalent to:</p>
     * <pre>
     *     viewer.setCursor(selectionEnd);
     *     viewer.setCursorAndExtendSelection(selectionStart);
     * </pre>
     *
     * <p>Except that less events are fired because it is done in a single operation.</p>
     *
     * @param selectionStart the first selected position (inclusive.)
     * @param selectionEnd the last selected position (inclusive.)
     */
    public void setSelection(long selectionStart, long selectionEnd) {
        if (this.selectionStart != selectionStart ||
                this.selectionEnd != selectionEnd ||
                cursor != selectionStart) {
            // Bit of DWIM here.
            if (selectionStart > selectionEnd) {
                long temp = selectionStart;
                selectionStart = selectionEnd;
                selectionEnd = temp;
            }

            cursor = selectionStart;
            this.selectionStart = selectionStart;
            this.selectionEnd = selectionEnd;

            fireStateChanged();
        }
    }

    /**
     * Adds a listener for changes in the model.
     *
     * @param listener the listener to add.
     */
    public void addChangeListener(ChangeListener listener) {
        if (listenerList == null) {
            listenerList = new EventListenerList();
        }
        listenerList.add(ChangeListener.class, listener);
    }

    /**
     * Removes a listener from changes in the model.
     *
     * @param listener the listener to remove.
     */
    public void removeChangeListener(ChangeListener listener) {
        if (listenerList != null) {
            listenerList.remove(ChangeListener.class, listener);
        }
    }

    /**
     * Fires a {@code stateChanged} event to all listeners.
     */
    private void fireStateChanged() {
        if (listenerList != null) {
            ChangeEvent event = new ChangeEvent(this);
            for (ChangeListener listener : listenerList.getListeners(ChangeListener.class)) {
                listener.stateChanged(event);
            }
        }
    }
}
