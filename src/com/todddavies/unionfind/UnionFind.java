package com.todddavies.unionfind;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

/**
 * Implements UnionFind using the "Union by Rank" and "Path halving" algorithms from
 * <a href="https://en.wikipedia.org/wiki/Disjoint-set_data_structure">the wikipedia page</a>.
 */
public final class UnionFind<T> implements Collection<T> {

  private final HashMap<T, TreeNode<T>> tree;

  public UnionFind() {
    tree = new HashMap<>();
  }

  public void makeSet(T item) {
    if (tree.containsKey(item)) {
      // We already have this item
      return;
    }
    tree.put(item, new TreeNode<>(item));
  }

  public void union(T item1, T item2) {
    TreeNode<T> root1 = find(item1);
    TreeNode<T> root2 = find(item2);

    if (root1 == root2) {
      // Already in the same set
      return;
    }

    if (root1.rank < root2.rank) {
      TreeNode<T> tmp = root1;
      root1 = root2;
      root2 = tmp;
    }

    root2.parent = root1;
    if (root1.rank == root2.rank) {
      root1.rank++;
    }
  }

  public boolean find(T item1, T item2) {
    return find(item1) == find(item2);
  }

  public Collection<Set<T>> allSets() {
    return tree.keySet().stream().collect(Collectors.groupingBy(this::find, toSet())).values();
  }

  private TreeNode<T> find(T item1) {
    if (!tree.containsKey(item1)) {
      throw new IllegalArgumentException(String.format("Element does not exist: %s", item1));
    }

    TreeNode<T> node = tree.get(item1);
    while(node.parent != node) {
      node.parent = node.parent.parent;
      node = node.parent;
    }
    return node;
  }

  /**
   * Internal node class.
   */
  private static final class TreeNode<T> {
    final T value;
    TreeNode<T> parent;
    int rank;

    TreeNode(T value) {
      this.value = value;
      this.parent = this;
      this.rank = 0;
    }
  }

  // Below are all the methods required to implement Collection<T>.

  @Override
  public int size() {
    return tree.size();
  }

  @Override
  public boolean isEmpty() {
    return tree.isEmpty();
  }

  @Override
  public boolean contains(Object o) {
    return tree.containsKey(o);
  }

  @Override
  public Iterator<T> iterator() {
    return tree.keySet().iterator();
  }

  @Override
  public Object[] toArray() {
    return tree.keySet().toArray();
  }

  @Override
  public <T1> T1[] toArray(T1[] t1s) {
    return (T1[]) tree.keySet().toArray();
  }

  @Override
  public boolean add(T t) {
    boolean out = !tree.containsKey(t);
    makeSet(t);
    return out;
  }

  @Override
  public boolean containsAll(Collection<?> collection) {
    return tree.keySet().containsAll(collection);
  }

  @Override
  public boolean addAll(Collection<? extends T> collection) {
    boolean out = false;
    for (T item : collection) {
      if (add(item)) {
        out = true;
      }
    }
    return out;
  }

  @Override
  public boolean remove(Object o) {
    // Can't remove elements from Union Find
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean removeAll(Collection<?> collection) {
    // Can't remove elements from Union Find
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean retainAll(Collection<?> collection) {
    // Can't remove elements from Union Find
    throw new UnsupportedOperationException();
  }

  @Override
  public void clear() {
    tree.clear();
  }
}
