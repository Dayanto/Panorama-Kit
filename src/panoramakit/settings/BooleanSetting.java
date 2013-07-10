package panoramakit.settings;

/**
 * BooleanSetting
 *  
 * @author dayanto
 * @license GNU Lesser General Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 *
 */
public abstract class BooleanSetting
{
	public boolean value;
	
	public BooleanSetting(boolean defaultValue)
	{
		value = defaultValue;
	}
	
	public void setValueFromString(String value)
	{
		try
		{
			
		}
		catch(Exception e)
		{
			System.out.println("Parsing failed: " + " " + "boolean");
		}
	}
	
}
