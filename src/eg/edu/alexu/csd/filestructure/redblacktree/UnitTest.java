package eg.edu.alexu.csd.filestructure.redblacktree;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import javax.management.RuntimeErrorException;

import org.hamcrest.core.IsInstanceOf;
import org.junit.Assert;
import org.junit.Test;


public class UnitTest {
	private final boolean debug = false;

	/** 
	 * test get a null root.
	 */
	@Test
	public void testRootNull() {

		IRedBlackTree<String, String> redBlackTree = (IRedBlackTree<String, String>) TestRunner.getImplementationInstanceForInterface(IRedBlackTree.class);
		INode<String, String> root = null;

		try {
			root = redBlackTree.getRoot();
			if (debug)
				System.out.println("TestRootNull: (case null)");
			boolean check = false;
			if (root == null) 
				check = true;
			if (!check)
				Assert.assertEquals(true, root.isNull());
		} catch (RuntimeErrorException ex) {
			if (debug)
				System.out.println("TestRootNull: (case runtime exception)");
		} catch (Throwable e) {
			TestRunner.fail("Fail to getRoot of tree", e);
		}
	}

	/**
	 * Test get the root of the tree.
	 */
	@Test
	public void testGetRoot() {

		IRedBlackTree<String, String> redBlackTree = (IRedBlackTree<String, String>) TestRunner.getImplementationInstanceForInterface(IRedBlackTree.class);
		INode<String, String> root = null;

		try {
			redBlackTree.insert("Soso", "Toto");
			root = redBlackTree.getRoot();
			Assert.assertEquals("Soso", root.getKey());
			Assert.assertEquals("Toto", root.getValue());
		}catch (Throwable e) {
			TestRunner.fail("Fail to getRoot of tree", e);
		}
	}

	/**
	 * check isEmpty true case.
	 */
	@Test
	public void testIsEmptyTrue() {

		IRedBlackTree<String, String> redBlackTree = (IRedBlackTree<String, String>) TestRunner.getImplementationInstanceForInterface(IRedBlackTree.class);

		try {
			Assert.assertEquals(true, redBlackTree.isEmpty());
		}catch (Throwable e) {
			TestRunner.fail("Fail to test if tree is Empty", e);
		}
	}	

	/**
	 * check isEmpty false case.
	 */
	@Test
	public void testIsEmptyFalse() {

		IRedBlackTree<String, String> redBlackTree = (IRedBlackTree<String, String>) TestRunner.getImplementationInstanceForInterface(IRedBlackTree.class);

		try {
			redBlackTree.insert("soso", "toto");
			Assert.assertEquals(false, redBlackTree.isEmpty());
		}catch (Throwable e) {
			TestRunner.fail("Fail to test if tree is Empty", e);
		}
	}
	
	/**
	 * Test clearing the tree.
	 */
	@Test
	public void testClearTree() {

		IRedBlackTree<String, String> redBlackTree = (IRedBlackTree<String, String>) TestRunner.getImplementationInstanceForInterface(IRedBlackTree.class);

		try {
			redBlackTree.insert("soso", "toto");
			redBlackTree.clear();
			redBlackTree.clear();
			redBlackTree.insert("soso", "toto");
			redBlackTree.insert("toto", "toto");
			redBlackTree.insert("fofo", "toto");
			redBlackTree.insert("koko", "toto");
			Assert.assertEquals(false, redBlackTree.isEmpty());
			redBlackTree.clear();
			Assert.assertEquals(true, redBlackTree.isEmpty());
		}catch (Throwable e) {
			TestRunner.fail("Fail to clear the tree", e);
		}
	}

	/**
	 * Test normal search in a tree.
	 */
	@Test
	public void testNormalSearch() {

		IRedBlackTree<Integer, String> redBlackTree = (IRedBlackTree<Integer, String>) TestRunner.getImplementationInstanceForInterface(IRedBlackTree.class);

		try {
			Random r = new Random();
			ArrayList<Integer> keysToSearch = new ArrayList<>();
			for(int i = 0; i < 1000; i++) {
				int key = r.nextInt(10000);
				if (i % 50 == 0) 
					keysToSearch.add(key);
				redBlackTree.insert(key, "toto" + key);
			}
			for (int i = 0; i < keysToSearch.size(); i++) {
				String ans = redBlackTree.search(keysToSearch.get(i));
				Assert.assertEquals("toto" + keysToSearch.get(i), ans);				
			}
		}catch (Throwable e) {
			TestRunner.fail("Fail to search for a key in the tree", e);
		}
	}

	/**
	 * Test search in an empty tree.
	 */
	@Test
	public void testSearchEmpty() {

		IRedBlackTree<Integer, String> redBlackTree = (IRedBlackTree<Integer, String>) TestRunner.getImplementationInstanceForInterface(IRedBlackTree.class);
		
		try {
			Assert.assertNull(redBlackTree.search(123));
		}catch (Throwable e) {
			TestRunner.fail("Fail to search for a key in the tree", e);
		}
	}
	
	/**
	 * Test search an absent key in a tree.
	 */
	@Test
	public void testSearchAbsentKey() {

		IRedBlackTree<Integer, String> redBlackTree = (IRedBlackTree<Integer, String>) TestRunner.getImplementationInstanceForInterface(IRedBlackTree.class);
		
		try {
			for (int i = 0; i < 10000; i++)
				redBlackTree.insert(i, "koko" + i);
			Assert.assertNull(redBlackTree.search(100000));
		}catch (Throwable e) {
			TestRunner.fail("Fail to search for a key in the tree", e);
		}
	}

	/**
	 * Test stress search.
	 */
	@Test(timeout = 10000)
	public void testStressSearch() {

		IRedBlackTree<Integer, String> redBlackTree = (IRedBlackTree<Integer, String>) TestRunner.getImplementationInstanceForInterface(IRedBlackTree.class);
		
		try {
			Random r = new Random();
			ArrayList<Integer> keysToSearch = new ArrayList<>();
			for(int i = 0; i < 10000000; i++) {
				int key = r.nextInt(100000);
				if (i % 50 == 0) 
					keysToSearch.add(key);
				redBlackTree.insert(key, "toto" + key);
			}
			for (int i = 0; i < keysToSearch.size(); i++) {
				String ans = redBlackTree.search(keysToSearch.get(i));
				Assert.assertEquals("toto" + keysToSearch.get(i), ans);				
			}
		}catch (Throwable e) {
			TestRunner.fail("Fail to search for a key in the tree", e);
		}
	}
	
	/**
	 * Test search with null.
	 */
	@Test
	public void testSearchWithNull() {

		IRedBlackTree<Integer, String> redBlackTree = (IRedBlackTree<Integer, String>) TestRunner.getImplementationInstanceForInterface(IRedBlackTree.class);
		
		try {
			Random r = new Random();
			for(int i = 0; i < 100; i++) {
				int key = r.nextInt(100000);
				redBlackTree.insert(key, "toto" + key);
			}
			redBlackTree.search(null);
			Assert.fail();
		} catch (RuntimeErrorException ex) {
			
		}catch (Throwable e) {
			TestRunner.fail("Fail to handle search with null parameter", e);
		}
	}
	
	/**
	 * Test normal contains in a tree.
	 */
	@Test
	public void testNormalContains() {

		IRedBlackTree<Integer, String> redBlackTree = (IRedBlackTree<Integer, String>) TestRunner.getImplementationInstanceForInterface(IRedBlackTree.class);

		try {
			Random r = new Random();
			ArrayList<Integer> keysToSearch = new ArrayList<>();
			for(int i = 0; i < 1000; i++) {
				int key = r.nextInt(10000);
				if (i % 50 == 0) 
					keysToSearch.add(key);
				redBlackTree.insert(key, "toto" + key);
			}
			for (int i = 0; i < keysToSearch.size(); i++) {
				boolean ans = redBlackTree.contains(keysToSearch.get(i));
				Assert.assertEquals(true, ans);				
			}
		}catch (Throwable e) {
			TestRunner.fail("Fail contains a key in the tree", e);
		}
	}

	/**
	 * Test contains with null.
	 */
	@Test
	public void testContainsWithNull() {

		IRedBlackTree<Integer, String> redBlackTree = (IRedBlackTree<Integer, String>) TestRunner.getImplementationInstanceForInterface(IRedBlackTree.class);
		
		try {
			Random r = new Random();
			for(int i = 0; i < 100; i++) {
				int key = r.nextInt(100000);
				redBlackTree.insert(key, "toto" + key);
			}
			redBlackTree.contains(null);
			Assert.fail();
		} catch (RuntimeErrorException ex) {
			
		}catch (Throwable e) {
			TestRunner.fail("Fail to handle contains with null parameter", e);
		}
	}
	
	/**
	 * Test contains in an empty tree.
	 */
	@Test
	public void testContainsEmpty() {

		IRedBlackTree<Integer, String> redBlackTree = (IRedBlackTree<Integer, String>) TestRunner.getImplementationInstanceForInterface(IRedBlackTree.class);
		
		try {
			Assert.assertEquals(false, redBlackTree.contains(123));
		}catch (Throwable e) {
			TestRunner.fail("Fail contains a key in the tree", e);
		}
	}
	
	/**
	 * Test contains an absent key in a tree.
	 */
	@Test
	public void testContainsAbsentKey() {

		IRedBlackTree<Integer, String> redBlackTree = (IRedBlackTree<Integer, String>) TestRunner.getImplementationInstanceForInterface(IRedBlackTree.class);
		
		try {
			for (int i = 0; i < 10000; i++)
				redBlackTree.insert(i, "koko" + i);
			Assert.assertEquals(false, redBlackTree.contains(100000));
		}catch (Throwable e) {
			TestRunner.fail("Fail contains a key in the tree", e);
		}
	}

	/**
	 * Test stress contains.
	 */
	@Test(timeout = 10000)
	public void testStressContains() {

		IRedBlackTree<Integer, String> redBlackTree = (IRedBlackTree<Integer, String>) TestRunner.getImplementationInstanceForInterface(IRedBlackTree.class);
		
		try {
			Random r = new Random();
			ArrayList<Integer> keysToSearch = new ArrayList<>();
			for(int i = 0; i < 10000000; i++) {
				int key = r.nextInt(100000);
				if (i % 50 == 0) 
					keysToSearch.add(key);
				redBlackTree.insert(key, "toto" + key);
			}
			for (int i = 0; i < keysToSearch.size(); i++) {
				boolean ans = redBlackTree.contains(keysToSearch.get(i));
				Assert.assertEquals(true, ans);
			}
		}catch (Throwable e) {
			TestRunner.fail("Fail contains a key in the tree", e);
		}
	}	

	/**
	 * Test insert with null key.
	 */
	@Test
	public void testInsertionWithNullKey() {

		IRedBlackTree<Integer, String> redBlackTree = (IRedBlackTree<Integer, String>) TestRunner.getImplementationInstanceForInterface(IRedBlackTree.class);
		
		try {
			Random r = new Random();
			for(int i = 0; i < 100; i++) {
				int key = r.nextInt(100000);
				redBlackTree.insert(key, "toto" + key);
			}
			redBlackTree.insert(null, "soso");
			Assert.fail();
		} catch (RuntimeErrorException ex) {
			
		}catch (Throwable e) {
			TestRunner.fail("Fail to handle search with null parameter", e);
		}
	}

	/**
	 * Test insert with null value.
	 */
	@Test
	public void testInsertionWithNullValue() {

		IRedBlackTree<Integer, String> redBlackTree = (IRedBlackTree<Integer, String>) TestRunner.getImplementationInstanceForInterface(IRedBlackTree.class);
		
		try {
			Random r = new Random();
			for(int i = 0; i < 100; i++) {
				int key = r.nextInt(100000);
				redBlackTree.insert(key, "toto" + key);
			}
			redBlackTree.insert(123, null);
			Assert.fail();
		} catch (RuntimeErrorException ex) {
			
		}catch (Throwable e) {
			TestRunner.fail("Fail to handle search with null parameter", e);
		}
	}
	
	/**
	 * Test insert normal with random data.
	 */
	@Test
	public void testNormalInsertionWithRandomData() {

		IRedBlackTree<Integer, String> redBlackTree = (IRedBlackTree<Integer, String>) TestRunner.getImplementationInstanceForInterface(IRedBlackTree.class);
		
		try {
			Random r = new Random();
			for(int i = 0; i < 100; i++) {
				int key = r.nextInt(1000);
				redBlackTree.insert(key, "toto" + key);
				Assert.assertTrue(verifyProps(redBlackTree.getRoot()));
			}
				
		}catch (Throwable e) {
			TestRunner.fail("Fail to insert a key in the tree", e);
		}
	}	
	
	/**
	 * Test insert normal.
	 */
	@Test
	public void testNormalInsertion() {

		IRedBlackTree<Integer, String> redBlackTree = (IRedBlackTree<Integer, String>) TestRunner.getImplementationInstanceForInterface(IRedBlackTree.class);
		
		try {
			redBlackTree.insert(20, "soso");
			redBlackTree.insert(15, "soso");
			redBlackTree.insert(10, "soso");
			redBlackTree.insert(7, "soso");
			redBlackTree.insert(9, "soso");
			redBlackTree.insert(12, "soso");
			redBlackTree.insert(24, "soso");
			redBlackTree.insert(22, "soso");
			redBlackTree.insert(13, "soso");
			redBlackTree.insert(11, "soso");
			
			String expectedAns = "12B9R15R7B10B13B22BNBNBNB11RNBNB20R24RNBNBNBNBNBNB";
			String actualAns = levelOrder(redBlackTree.getRoot());
			Assert.assertEquals(expectedAns, actualAns);
		}catch (Throwable e) {
			TestRunner.fail("Fail to insert a key in the tree", e);
		}
	}	
	
	/**
	 * Test update normal.
	 */
	@Test
	public void testUpdateValue() {

		IRedBlackTree<Integer, String> redBlackTree = (IRedBlackTree<Integer, String>) TestRunner.getImplementationInstanceForInterface(IRedBlackTree.class);
		
		try {
			redBlackTree.insert(20, "soso" + 20);
			redBlackTree.insert(15, "soso" + 15);
			redBlackTree.insert(10, "soso" + 10);
			redBlackTree.insert(7, "soso" + 7);
			redBlackTree.insert(9, "soso" + 9);
			redBlackTree.insert(12, "soso" + 12);
			redBlackTree.insert(24, "soso" + 24);
			redBlackTree.insert(22, "soso" + 22);
			redBlackTree.insert(13, "soso" + 13);
			redBlackTree.insert(11, "soso" + 11);
			
			Assert.assertEquals("soso" + 13, redBlackTree.search(13));
			redBlackTree.insert(13, "koko" + 13);
			Assert.assertEquals("koko" + 13, redBlackTree.search(13));
		}catch (Throwable e) {
			TestRunner.fail("Fail to insert a key in the tree", e);
		}
	}	

	/**
	 * Test delete with null parameter.
	 */
	@Test
	public void testDeleteWithNull() {

		IRedBlackTree<Integer, String> redBlackTree = (IRedBlackTree<Integer, String>) TestRunner.getImplementationInstanceForInterface(IRedBlackTree.class);
		
		try {
			redBlackTree.delete(null);
			Assert.fail("Fail to handle deletion with null parameter");
		} catch (RuntimeErrorException ex) {
			
		} catch (Throwable e) {
			TestRunner.fail("Fail to handle deletion with null parameter", e);
		}
	}	





	private String levelOrder(INode<Integer, String> root) {
		StringBuilder sb = new StringBuilder();
		Queue<INode<Integer, String>> q = new LinkedList<>();
		q.add(root);
		while (!q.isEmpty()) {
			int qSize = q.size();
			for (int i = 0; i < qSize; i++) {
				INode<Integer, String> cur = q.poll();
				if (cur != null && !cur.isNull()) {
					sb.append(cur.getKey() + (cur.getColor()? "R" : "B"));
					q.add(cur.getLeftChild());
					q.add(cur.getRightChild());
				}
				else {
					sb.append("NB");
				}
			}
		}
		return sb.toString();
	}







	private boolean validateBST(INode<Integer, String> node, INode<Integer, String> leftRange, INode<Integer, String> rightRange) {
		if (node == null || node.isNull()) return true;
		
		if ((leftRange == null || node.getKey().compareTo(leftRange.getKey()) > 0) &&
				(rightRange == null || node.getKey().compareTo(rightRange.getKey()) < 0))
			return validateBST(node.getLeftChild(), leftRange, node) && 
					validateBST(node.getRightChild(), node, rightRange);
		return false;
	}
	
	private boolean verifyProperty2(INode<Integer, String> node) {
		return node.getColor() == INode.BLACK;
	}

	private boolean verifyProperty3(INode<Integer, String> node) {
		if (node == null || node.isNull()) return node.getColor() == INode.BLACK;

		return verifyProperty3(node.getLeftChild()) && verifyProperty3(node.getRightChild());
	}

	private boolean verifyProperty4(INode<Integer, String> node) {
		if (node == null || node.isNull()) return true;
		if (isRed(node)) {
			return !isRed(node.getParent()) && !isRed(node.getLeftChild()) && !isRed(node.getRightChild());
		}

		return verifyProperty4(node.getLeftChild()) && verifyProperty4(node.getRightChild());
	}
	
	private boolean verifyProperty5(INode<Integer, String> node) {
		boolean[] ans = new boolean[]{true};
		verifyProperty5Helper(node, ans);
		return ans[0];
	}

	private int verifyProperty5Helper(INode<Integer, String> node, boolean[] ans) {
		if (node == null) return 1;

		int leftCount = verifyProperty5Helper(node.getLeftChild(), ans);
		int rightCount = verifyProperty5Helper(node.getRightChild(), ans);

		ans[0] = ans[0] && (leftCount == rightCount);
		return leftCount + (!isRed(node)? 1 : 0);
	}

	private boolean verifyProps(INode<Integer, String> root) {
		return verifyProperty2(root) && verifyProperty3(root) && verifyProperty4(root) && verifyProperty5(root) && validateBST(root, null, null);
	}

	private boolean isRed(INode<Integer, String> node) {
		if (node == null || node.isNull()) return INode.BLACK;
		return node.getColor() == INode.RED;
	}
}
