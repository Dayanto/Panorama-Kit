/* 
 * This code isn't copyrighted. Do what you want with it. :) 
 */
package panoramakit.gui.util;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import panoramakit.gui.settings.ModSettings;
import panoramakit.mod.PanoramaKit;

/** 
 * @author dayanto
 */
public class FileNumerator
{
	public static File numberFile(File file)
	{	
		ModSettings settings = PanoramaKit.instance.getModSettings();
		
		if(ModSettings.fileNumberingOptions[settings.getFileNumbering()] == ModSettings.increment) return increment(file);
		if(ModSettings.fileNumberingOptions[settings.getFileNumbering()] == ModSettings.date) return date(file);
		
		return file;
	}
	
	public static File increment(File file)
	{
		file = cleanFilePath(file);
		
		File parent = file.getParentFile();
		String name = file.getName().substring(0,file.getName().lastIndexOf('.'));
		String extension = file.getName().substring(file.getName().lastIndexOf('.'));
		
		int fileNumber = 2;
		while(file.exists()){
			file = new File(parent, name + fileNumber + extension);
			fileNumber++;
		}
		
		return file;
	}
	
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
	
	public static File date(File file)
	{
		file = cleanFilePath(file);
		
		File parent = file.getParentFile();
		
		String fileName = file.getName();
		String name = fileName.substring(0, fileName.lastIndexOf('.'));
		String extension = fileName.substring(fileName.lastIndexOf('.'));
		
		return new File(parent, DATE_FORMAT.format(new Date()) + " " + name + extension);
	}
	
	public static File cleanFilePath(File file)
	{
		String filePath = file.getPath();
		filePath = filePath.replaceAll("/./", "/");
		filePath = filePath.replaceAll("\\\\.\\\\", "\\\\");
		return new File(filePath);
	}
}
