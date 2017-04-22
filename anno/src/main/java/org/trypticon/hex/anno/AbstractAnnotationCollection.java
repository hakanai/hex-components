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

package org.trypticon.hex.anno;

import org.trypticon.hex.anno.util.AnnotationRangeSearchHit;
import org.trypticon.hex.anno.util.AnnotationRangeSearcher;

import javax.annotation.Nonnull;
import javax.swing.event.EventListenerList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Base abstract class for implementing annotation collections.
 *
 * @author trejkaz
 */
public abstract class AbstractAnnotationCollection implements AnnotationCollection {
    private EventListenerList listenerList;

    @Override
    public void add(@Nonnull Annotation annotation) throws OverlappingAnnotationException {
        doAdd(Collections.singletonList(getRootGroup()), annotation);
    }

    /**
     * Recursively finds the group annotation to add the annotation to and adds it.
     *
     * @param parentAnnotationPath the path to the current parent being searched.
     * @param annotation the annotation being added.
     * @throws OverlappingAnnotationException if the annotation would overlap another annotation.
     */
    private void doAdd(List<GroupAnnotation> parentAnnotationPath, Annotation annotation)
            throws OverlappingAnnotationException {

        GroupAnnotation parentAnnotation = parentAnnotationPath.get(parentAnnotationPath.size() - 1);
        List<? extends Annotation> annotations = parentAnnotation.getAnnotations();

        List<AnnotationRangeSearchHit> hits = new AnnotationRangeSearcher().findAllInRange(annotations, annotation);
        if (hits.size() == 0) {
            // No annotations in the vicinity at all, just add it and bail.
            int index = parentAnnotation.add(annotation);
            fireAnnotationsAdded(parentAnnotationPath, Collections.singletonList(index), Collections.singletonList(annotation));
            return;
        }

        if (hits.get(0).getRelation() == AnnotationRangeSearchHit.Relation.INTERSECTING_START) {
            throw new OverlappingAnnotationException(hits.get(0).getAnnotation(), annotation);
        }

        if (hits.get(hits.size() - 1).getRelation() == AnnotationRangeSearchHit.Relation.INTERSECTING_END) {
            throw new OverlappingAnnotationException(hits.get(hits.size() - 1).getAnnotation(), annotation);
        }

        Annotation newParentAnnotation = hits.get(0).getAnnotation();
        List<GroupAnnotation> newParentAnnotationPath = null;
        if (newParentAnnotation instanceof GroupAnnotation) {
            newParentAnnotationPath = new ArrayList<>(parentAnnotationPath.size() + 1);
            newParentAnnotationPath.addAll(parentAnnotationPath);
            newParentAnnotationPath.add((GroupAnnotation) newParentAnnotation);
        }

        // Dealing with surrounding is simple.  If it was a group then we recurse to add inside the group,
        // otherwise it's illegal.
        if (hits.get(0).getRelation() == AnnotationRangeSearchHit.Relation.SURROUNDING) {
            if (newParentAnnotation instanceof GroupAnnotation) {
                // No problem, the new annotation will go into that group.
                doAdd(newParentAnnotationPath, annotation);
                return;
            } else {
                throw new OverlappingAnnotationException(newParentAnnotation, annotation);
            }
        }

        // For the same range, the order we nest will depend on which one is a group vs. a leaf.
        if (hits.get(0).getRelation() == AnnotationRangeSearchHit.Relation.SAME_RANGE) {
            if (newParentAnnotation instanceof GroupAnnotation) {
                // The case of annotation also being a GroupAnnotation is ambiguous in that we could nest
                // them either way.  But we'll just treat the new one as inside the old one, which is simpler.
                doAdd(newParentAnnotationPath, annotation);
                return;
            } else {
                // Otherwise we treat it the same as CONTAINED_WITHIN which is handled below.
            }
        }

        // Now the hits are entirely contained within the range.  As was the case with the surrounding case,
        // this is only legal if the one containing the others is a group.
        if (annotation instanceof GroupAnnotation) {
            GroupAnnotation group = (GroupAnnotation) annotation;

            if (group.getAnnotations().isEmpty()) {
                // Move the contained annotations inside the group.  This should succeed unless the caller does
                // something dumb like putting some annotations inside the group.  If it fails, at least the
                // subsequent calls will not be made, so things should still be consistent.
                // This is done in reverse so that we can collect the indices.
                List<Integer> childIndices = new LinkedList<>();
                List<Annotation> children = new LinkedList<>();
                for (ListIterator<AnnotationRangeSearchHit> iterator = hits.listIterator(hits.size());
                     iterator.hasPrevious(); ) {

                    AnnotationRangeSearchHit hit = iterator.previous();
                    Annotation child = hit.getAnnotation();
                    int index = parentAnnotation.remove(child);
                    childIndices.add(0, index);
                    children.add(0, child);
                    group.add(child);
                }
                fireAnnotationsRemoved(parentAnnotationPath, childIndices, children);

                // And finally add the group to ourselves.  We know this must be safe because we just removed all the
                // annotations in its location.
                int index = parentAnnotation.add(annotation);
                fireAnnotationsAdded(parentAnnotationPath, Collections.singletonList(index), Collections.singletonList(annotation));
            } else {
                // We annotation we're trying to add contains children which may themselves overlap
                // with the existing annotations in the region.
                // Recursively try to add each child. This will catch overlaps further down the tree
                // and since we're calling it recursively, it will take care of firing events for us.
                for (Annotation child : group.getAnnotations()) {
                    doAdd(parentAnnotationPath, child);
                }

                // All children of the node we're adding were added, so add the node itself.
                // This should pass because it will come back through this method and take the isEmpty()
                // path next time.
                group.removeAllDescendants();
                doAdd(parentAnnotationPath, group);
            }
        } else {
            throw new OverlappingAnnotationException(hits.get(0).getAnnotation(), annotation); // picks the first one
        }
    }

    @Override
    public void remove(@Nonnull Annotation annotation) {
        doRemove(Collections.singletonList(getRootGroup()), annotation, false);
    }

    @Override
    public void removeWithDescendants(@Nonnull Annotation annotation) {
        doRemove(Collections.singletonList(getRootGroup()), annotation, true);
    }

    private void doRemove(List<GroupAnnotation> parentAnnotationPath, Annotation annotation,
                          boolean removeDescendants) {

        GroupAnnotation parentAnnotation = parentAnnotationPath.get(parentAnnotationPath.size() - 1);
        Annotation foundAnnotation = parentAnnotation.findAnnotationAt(annotation.getPosition());

        if (foundAnnotation == null) {
            // No annotation at that position at all, let alone the one we wanted.
            throw new IllegalArgumentException("Annotation is not present so cannot be removed: " + annotation);
        }

        if (foundAnnotation.equals(annotation)) {
            int removedIndex = parentAnnotation.remove(annotation);
            fireAnnotationsRemoved(parentAnnotationPath, Collections.singletonList(removedIndex), Collections.singletonList(annotation));

            if (!removeDescendants) {
                // We removed a group so we have to add its children back.
                if (annotation instanceof GroupAnnotation) {
                    GroupAnnotation groupAnnotation = (GroupAnnotation) annotation;
                    List<Integer> childIndices = new LinkedList<>();
                    List<Annotation> children = new LinkedList<>();
                    for (Annotation child : groupAnnotation.getAnnotations()) {
                        int index = parentAnnotation.add(child);
                        childIndices.add(index);
                        children.add(child);
                    }
                    fireAnnotationsAdded(parentAnnotationPath, childIndices, children);

                    // Remove descendants from this copy because its parent now owns those children.
                    groupAnnotation.removeAllDescendants();
                }
            }
        } else {
            // Found one but it wasn't the one we were looking for.
            // If it's a group annotation then we might find it further down the tree.
            if (foundAnnotation instanceof GroupAnnotation) {
                List<GroupAnnotation> newParentAnnotationPath =
                        new ArrayList<>(parentAnnotationPath.size() + 1);
                newParentAnnotationPath.addAll(parentAnnotationPath);
                newParentAnnotationPath.add((GroupAnnotation) foundAnnotation);
                doRemove(newParentAnnotationPath, annotation, removeDescendants);
            } else {
                throw new IllegalArgumentException("Annotation is not present so cannot be removed: " + annotation);
            }
        }
    }

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

                listener.annotationsRemoved(event);
            }
        }
    }

    protected void fireAnnotationsChanged(List<? extends GroupAnnotation> parentPath,
                                          List<Integer> childIndices,
                                          List<? extends Annotation> children) {
        if (listenerList != null) {
            fireAnnotationsChanged(new AnnotationCollectionEvent(this, parentPath, childIndices, children));
        }
    }

    protected void fireAnnotationChanged(@Nonnull Annotation annotation) {
        if (listenerList != null) {
            List<? extends Annotation> path = this.getAnnotationPathFor(annotation);
            @SuppressWarnings("unchecked") // safe for now because all ancestors must be GroupAnnotation.
                    List<? extends GroupAnnotation> parentPath = (List<? extends GroupAnnotation>)
                    path.subList(0, path.size() - 1);
            int index = parentPath.get(parentPath.size() - 1).getAnnotations().indexOf(annotation);
            fireAnnotationsChanged(parentPath, Collections.singletonList(index), Collections.singletonList(annotation));
        }
    }

    protected void fireAnnotationsChanged(@Nonnull AnnotationCollectionEvent event) {
        if (listenerList != null) {
            for (AnnotationCollectionListener listener :
                listenerList.getListeners(AnnotationCollectionListener.class)) {

                listener.annotationsChanged(event);
            }
        }
    }

    @Override
    public void addAnnotationCollectionListener(@Nonnull AnnotationCollectionListener listener) {
        if (listenerList == null) {
            listenerList = new EventListenerList();
        }
        listenerList.add(AnnotationCollectionListener.class, listener);
    }

    @Override
    public void removeAnnotationCollectionListener(@Nonnull AnnotationCollectionListener listener) {
        if (listenerList != null) {
            listenerList.remove(AnnotationCollectionListener.class, listener);
        }
    }
}
