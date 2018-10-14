package com.todddavies.unionfind;

import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public final class UnionFindTest {

  private UnionFind<String> unionFind;

  @Before
  public void setUp() {
    unionFind = new UnionFind<>();
  }

  @Test
  public void testEmpty() {
    assertThat(unionFind.allSets().size(), is(0));
  }

  @Test
  public void testAdd() {
    unionFind.makeSet("Added");
    assertThat(unionFind.contains("Added"), is(true));
    assertThat(unionFind.contains("Not added"), is(false));
  }

  @Test
  public void testFind() {
    unionFind.makeSet("One");
    assertThat(unionFind.find("One", "One"), is(true));
    try {
      assertThat(unionFind.find("One", "Two"), is(true));
      fail("Expected exception here");
    } catch (IllegalArgumentException e) {
      // Check it complains about two
      assertThat(e.getMessage(), containsString("Two"));
    }
  }

  @Test
  public void testUnion() {
    unionFind.makeSet("One");
    unionFind.makeSet("Two");
    unionFind.makeSet("Three");
    unionFind.union("One", "Two");
    assertThat(unionFind.find("One", "Two"), is(true));
    assertThat(unionFind.find("Two", "One"), is(true));
    assertThat(unionFind.find("One", "Three"), is(false));
    assertThat(unionFind.find("Two", "Three"), is(false));
    assertThat(unionFind.find("Three", "One"), is(false));
    assertThat(unionFind.find("Three", "Two"), is(false));
  }

  @Test
  public void testSets() {
    HashSet<String> firstSet = new HashSet<>();
    firstSet.add("One");
    firstSet.add("Two");
    HashSet<String> secondSet = new HashSet<>();
    secondSet.add("Three");

    unionFind.makeSet("One");
    unionFind.makeSet("Two");
    unionFind.makeSet("Three");
    unionFind.union("One", "Two");
    Collection<Set<String>> result = unionFind.allSets();

    assertThat(result.contains(firstSet), is(true));
    assertThat(result.contains(secondSet), is(true));
    assertThat(result.size(), is(2));
  }
}