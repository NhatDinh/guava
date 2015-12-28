/*
 * Copyright (C) 2009 The Guava Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.common.collect;

import org.checkerframework.dataflow.qual.Pure;
import org.checkerframework.dataflow.qual.SideEffectFree;
import org.checkerframework.framework.qual.AnnotatedFor;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;

/**
 * Implementation of {@link ImmutableList} used for 0 or 2+ elements (not 1).
 *
 * @author Kevin Bourrillion
 */
@AnnotatedFor({"nullness"})
@GwtCompatible(serializable = true, emulated = true)
@SuppressWarnings("serial") // uses writeReplace(), not default serialization
class RegularImmutableList<E> extends ImmutableList<E> {
  static final ImmutableList<Object> EMPTY =
      new RegularImmutableList<Object>(ObjectArrays.EMPTY_ARRAY);

  private final transient int offset;
  private final transient int size;
  private final transient Object[] array;

  RegularImmutableList(Object[] array, int offset, int size) {
    this.offset = offset;
    this.size = size;
    this.array = array;
  }

  RegularImmutableList(Object[] array) {
    this(array, 0, array.length);
  }

  @Pure
  @Override
  public int size() {
    return size;
  }

  @Override
  boolean isPartialView() {
    return size != array.length;
  }

  @Override
  int copyIntoArray(Object[] dst, int dstOff) {
    System.arraycopy(array, offset, dst, dstOff, size);
    return dstOff + size;
  }

  // The fake cast to E is safe because the creation methods only allow E's
  @Override
  @SuppressWarnings("unchecked")
  public E get(int index) {
    Preconditions.checkElementIndex(index, size);
    return (E) array[index + offset];
  }

  @Override
  ImmutableList<E> subListUnchecked(int fromIndex, int toIndex) {
    return new RegularImmutableList<E>(array, offset + fromIndex, toIndex - fromIndex);
  }

  @SuppressWarnings("unchecked")
  @Override
  public UnmodifiableListIterator<E> listIterator(int index) {
    // for performance
    // The fake cast to E is safe because the creation methods only allow E's
    return (UnmodifiableListIterator<E>) Iterators.forArray(array, offset, size, index);
  }

  // TODO(lowasser): benchmark optimizations for equals() and see if they're worthwhile

@Override
public boolean contains(/*@org.checkerframework.checker.nullness.qual.Nullable*/ Object arg0) { return super.contains(arg0); }

@Pure
@Override
public boolean equals(/*@org.checkerframework.checker.nullness.qual.Nullable*/ Object arg0) { return super.equals(arg0); }

@Override
public int indexOf(/*@org.checkerframework.checker.nullness.qual.Nullable*/ Object arg0) { return super.indexOf(arg0); }

@Override
public int lastIndexOf(/*@org.checkerframework.checker.nullness.qual.Nullable*/ Object arg0) { return super.lastIndexOf(arg0); }

@SideEffectFree
@Override
public ImmutableList<E> subList(int arg0, int arg1) { return super.subList(arg0, arg1); }

@Pure
@Override
public int hashCode() { return super.hashCode(); }

@Pure
@Override
public String toString() { return super.toString(); }
}
