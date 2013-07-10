package panoramakit.settings;

/**
 * Setting
 *  
 * @author dayanto
 * @license GNU Lesser General Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 *
 */
public abstract class Setting
{
	public String id;
	
	public Setting(String id)
	{
		this.id = id;
	}
	
	public String getID()
	{
		return id;
	}
	
	public abstract String getValueAsString();
	
	public abstract void setValueFromString(String value);
	
	public abstract String getDisplayString();
}