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

    protected void fireAnnotationAdded(List<? extends GroupAnnotation> parentAnnotationPath, Annotation annotation) {
        if (listenerList != null) {
            AnnotationCollectionEvent event = new AnnotationCollectionEvent(this, parentAnnotationPath, annotation);

            for (AnnotationCollectionListener listener :
                    listenerList.getListeners(AnnotationCollectionListener.class)) {

                listener.annotationAdded(event);
            }
        }
    }

    protected void fireAnnotationRemoved(List<? extends GroupAnnotation> parentAnnotationPath, Annotation annotation) {
        if (listenerList != null) {
            AnnotationCollectionEvent event = new AnnotationCollectionEvent(this, parentAnnotationPath, annotation);

            for (AnnotationCollectionListener listener :
                    listenerList.getListeners(AnnotationCollectionListener.class)) {

                listener.annotationRemoved(event);
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
