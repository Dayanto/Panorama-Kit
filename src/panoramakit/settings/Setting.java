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
	public abstract void save();
	
	public abstract void load();
	
	public abstract void setDefaultValue();
	
	public abstract String getDisplayString();
}