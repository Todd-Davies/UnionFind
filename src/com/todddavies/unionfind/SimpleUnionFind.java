package com.todddavies.unionfind;

import java.util.Arrays;

/**
 * Simple implementation of the UnionFind (Disjoint-Set) datastructure.
 *
 * This is the implementation used in the tech talk.
 */
public final class SimpleUnionFind {

  public static void main(String[] args) {
    SimpleUnionFind uf = new SimpleUnionFind(10);

    // Do a bunch of unions
    uf.union(0,1);
    System.out.println("uf.union(0,1);");
    System.out.println(uf);
    uf.union(5,8);
    System.out.println("uf.union(5,8);");
    System.out.println(uf);
    uf.union(6,9);
    System.out.println("uf.union(6,9);");
    System.out.println(uf);
    uf.union(0,9);
    System.out.println("uf.union(0,9);");
    System.out.println(uf);

    // Query using find
    System.out.printf("find(0,1) => %b\n", uf.find(0,1));
    System.out.printf("find(0,9) => %b\n", uf.find(0,9));
    System.out.printf("find(5,7) => %b\n", uf.find(5,7));
    System.out.printf("find(0,2) => %b\n", uf.find(0,2));
  }

  private final int[] ids;
  private final int[] objects;

  /**
   * Initialises a new {@link SimpleUnionFind} with {@code size} entries.
   */
  public SimpleUnionFind(int size) {
    ids = new int[size];
    objects = new int[size];
    for (int i = 0; i < size; i++) {
      ids[i] = i;
      objects[i] = i;
    }
  }

  public void union(int a, int b) {
    int id = ids[a];
    for (int i = 0; i < ids.length; i++) {
      if (ids[i] == id) {
        ids[i] = ids[b];
      }
    }
  }

  public boolean find(int a, int b) {
    return ids[a] == ids[b];
  }

  public String toString() {
    return String.format("{ objects: %s,\n      ids: %s }",
        Arrays.toString(objects), Arrays.toString(ids));
  }
}

