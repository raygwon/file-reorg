/**
 * 
 */
package com.netiskra;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author ray
 *
 */
public class FileHolderTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.netiskra.FileHolder#getFileList()}.
	 */
	@Test
	public void testGetFileList() {
		
	}
	
	/**
	 * Test method for {@link com.netiskra.FileHolder#classifyFile()}.
	 */
	@Test
	public void testClassifyFile() {
		File fileHolder = new File(".");
		assertEquals(fileHolder.isDirectory(), true);
		
		List files = new ArrayList();
		List directories = new ArrayList();
		
		File[] _files = fileHolder.listFiles();
		File _file = null;
		for (int i=0,j=_files.length; i<j; i++) {
			_file = _files[i];
			if (_file.isDirectory()) {
				directories.add(_file);
			} else {
				files.add(_file);
			}
		}
		System.out.println(directories);
		System.out.println(files);
	}

}
