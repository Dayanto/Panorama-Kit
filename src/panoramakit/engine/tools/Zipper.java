/* 
 * This code is in the public domain. You are free to do whatever you want with it. :)
 */
package panoramakit.engine.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**  
 * @author dayanto
 * 
 * Based on code from here (http://stackoverflow.com/questions/740375/directories-in-a-zip-file-when-using-java-util-zip-zipoutputstream) 
 */

public class Zipper
{	
	/**
	 * Packages a folder with all of its content into a zip file.
	 */
	public static void zipFolder(File folder, File zipFile) throws IOException, Exception
	{
		// handle bad input
		if (!folder.exists()) {
			throw new FileNotFoundException("Folder doesn't exist: " + folder.getName());
		} 
		if (!folder.isDirectory()) {
			throw new IllegalArgumentException("That's not a folder!");
		}
		
		// attempt to clear any existing file.
		if(zipFile.exists()) {
			if(!zipFile.delete()) {
				throw new Exception("Zip Failed: Existing file can't be deleted.");
			}
		}
		
		if(!zipFile.getParentFile().exists()) zipFile.mkdirs();
		zipFile.createNewFile();
		
		// create the output stream to zip file result
		FileOutputStream fileOut = new FileOutputStream(zipFile); 
		ZipOutputStream zipOut = new ZipOutputStream(fileOut);
		
		// add the folder to the zip
		addFolder("", folder, zipOut);
		
		// close the zip objects
		zipOut.flush();
		zipOut.close();
	}
	
	private static void addFolder(String currentPath, File folder, ZipOutputStream zipOut) throws IOException
	{
		// update the internal folder path
		currentPath = currentPath + folder.getName() + "/";
		
		for(File item : folder.listFiles()) {
			if(item.isDirectory()){
				// recursively add subfolders
				addFolder(currentPath, item, zipOut);
			} else {
				// add files to the zip
				addFileEntry(currentPath, item, zipOut);
			}
		}
	}
	
	private static void addFileEntry(String currentPath, File file, ZipOutputStream zipOut) throws IOException
	{	
		// 4 MiB buffer 
		byte[] buffer = new byte[4 * 1024 * 1024];
		int length;
		
		FileInputStream in = new FileInputStream(file);
		ZipEntry entry = new ZipEntry(currentPath + file.getName());
		
		// write the entry to file
		zipOut.putNextEntry(entry);
		while ((length = in.read(buffer)) > 0) {
			zipOut.write(buffer, 0, length);
		}
		
		// clean up resources
		in.close();
		zipOut.closeEntry();
	}
}