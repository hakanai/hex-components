/*
 * Hex - a hex viewer and annotator
 * Copyright (C) 2009-2014  Trejkaz, Hex Project
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

package org.trypticon.hex.anno;

import javax.swing.event.EventListenerList;
import java.util.List;

/**
 * Base abstract class for implementing annotation collections.
 *
 * @author trejkaz
 */
public abstract class AbstractAnnotationCollection implements AnnotationCollection {
    private EventListenerList listenerList;

    protected void fireAnnotationsAdded(List<? extends GroupAnnotation> parentPath,
                                        List<Integer> childIndices,
                                        List<? extends Annotation> children) {
        if (listenerList != null) {
            fireAnnotationsAdded(new AnnotationCollectionEvent(this, parentPath, childIndices, children));
        }
    }

    protected void fireAnnotationsAdded(AnnotationCollectionEvent event) {
        if (listenerList != null) {
            for (AnnotationCollectionListener listener :
                listenerList.getListeners(AnnotationCollectionListener.class)) {

                listener.annotationsAdded(event);
            }
        }
    }

    protected void fireAnnotationsRemoved(List<? extends GroupAnnotation> parentPath,
                                          List<Integer> childIndices,
                                          List<? extends Annotation> children) {
        if (listenerList != null) {
            fireAnnotationsRemoved(new AnnotationCollectionEvent(this, parentPath, childIndices, children));
        }
    }

    protected void fireAnnotationsRemoved(AnnotationCollectionEvent event) {
        if (listenerList != null) {
            for (AnnotationCollectionListener listener :
                listenerList.getListeners(AnnotationCollectionListener.class)) {

                listener.annotationsAdded(event);
            }
        }
    }

    @Override
    public void addAnnotationCollectionListener(AnnotationCollectionListener listener) {
        if (listenerList == null) {
            listenerList = new EventListenerList();
        }
        listenerList.add(AnnotationCollectionListener.class, listener);
    }

    @Override
    public void removeAnnotationCollectionListener(AnnotationCollectionListener listener) {
        if (listenerList != null) {
            listenerList.remove(AnnotationCollectionListener.class, listener);
        }
    }
}
