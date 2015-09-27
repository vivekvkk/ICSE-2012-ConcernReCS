/**
 * Federal University of Minas Gerais 
 * Department of Computer Science
 * ConcernReCS Project
 *
 * Created by Pericles Alves
 * Date: 08/01/2011
 */

/**ConcernReCS is a plug-in to find Concern Refactoring Code Smells*/

package ufmg.crcs;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class ConcernReCS extends AbstractUIPlugin 
{
	public static final String PLUGIN_ID = "ufmg.crcs"; //The plug-in ID
	private static ConcernReCS plugin; //The shared instance
	private IFile file=null; //Location in which the Code Smells should be saved
	private boolean dirty=false; //Indicates if the view content has changed
	
	public ConcernReCS() 
	{
	}

	public void start(BundleContext context) throws Exception 
	{
		super.start(context);
		plugin = this;
	}

	public void stop(BundleContext context) throws Exception 
	{
		plugin = null;
		super.stop(context);
	}

	public static ImageDescriptor getImageDescriptor(String path) 
	{
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
	
	/**
	 * @return the shared instance
	 */
	public static ConcernReCS getDefault() 
	{
		return plugin;
	}
	
	/**
	 * Returns if the view content has changed 
	 */
	public boolean isDirty()
	{
		return dirty;
	}
	
	/**
	 * Indicates that the view content has been saved
	 */
	public void resetDirty()
	{
		dirty = false;
	}
	
	/**
	 * Indicates that the view content has changed
	 */
	public void contentChanged() 
	{
			dirty = true;
	}
	
	/**
	 * Sets the default location in which the Code Smells should be saved
	 * @param file
	 */
	public void setDefaultResource(IFile file)
	{
		this.file = file;
	}
	
	/**
	 * Returns the default location in which the Code Smells should be saved
	 */
	public IFile getDefaultResource()
	{
		return file;
	}
}