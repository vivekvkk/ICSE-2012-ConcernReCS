/**
 * Federal University of Minas Gerais 
 * Department of Computer Science
 * ConcernReCS Project
 *
 * Created by Pericles Alves
 * Date: 11/11/2011
 */

/**An action to save the found code smells in a specific location*/

package ufmg.crcs.ui.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.SaveAsDialog;

import ufmg.crcs.ConcernReCS;
import ufmg.crcs.ui.views.ConcernReCSModelProvider;
import ufmg.crcs.ui.views.ConcernReCSView;
import ufmg.crcs.smells.io.*;

public class SaveAsAction extends Action
{
	ConcernReCSView view; //The ConcernReCS main view
	private static final String extension=new String("xml"); //Extension of the saved file
	
	public SaveAsAction(ConcernReCSView view)
	{
		this.view=view;
		
		//Sets the action text
		setText("Save as");
		setToolTipText("Save as");
		
		setImageDescriptor(ConcernReCS.getImageDescriptor("icons/saveas.gif")); //Sets the action icon
	}
	
	public void run()
	{	
		IFile file = null;
		
		SaveAsDialog dialog = new ConcernReCSSaveAsDialog( PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell() );
				
		IFile current_file = ConcernReCS.getDefault().getDefaultResource();
		
		if(current_file!=null)
		{
			dialog.setOriginalFile(current_file);
		}
		dialog.open();
		
		if( dialog.getReturnCode()==Window.CANCEL )
		{
			return;
		}
		
		IPath path = dialog.getResult();
		
		path=addFileExtension(path);
		
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		file = workspace.getRoot().getFile(path);
		
		if(!file.exists())
		{
			try
			{
				file.create(null,true,null);
			}
			catch(CoreException exception)
			{
				MessageDialog.openError( PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),"Save error",
					"The file could not be saved." + " " + exception.getMessage());
				
				return;
			}
		}
		
		try
		{
			ConcernReCS.getDefault().setDefaultResource(file);
			
			//Writes in the chose file the Code Smells being shown in the view
			CodeSmellsWriter writer = new CodeSmellsWriter(ConcernReCSModelProvider.INSTANCE.getCodeSmells());
			writer.write(file);
		}
		catch(CodeSmellsIOException exception)
		{
			MessageDialog.openError( PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),"Save error",
					"The file could not be saved." + " " + exception.getMessage());
			
			return;
		}
		
		ConcernReCS.getDefault().resetDirty(); //The Code Smells are now saved
		
		if(view!=null)
		{
			view.updateActionState();
		}
	}
	
	class ConcernReCSSaveAsDialog extends SaveAsDialog
	{
		/**
		 * Creates a SaveAsDialog
		 */
		public ConcernReCSSaveAsDialog(Shell shell)
		{
			super(shell);
		}
		
	    protected Control createContents( Composite parent )
	    {
	        Control lContents = super.createContents( parent );

	        setTitle("Save as");
	        setMessage("Save as");

	        return lContents;
	    }
	}
	
	/**
	 * Adds the extension to the file to be saved
	 */
	private static IPath addFileExtension(IPath path)
	{
		IPath path_returned = path;
		
		if ( path_returned.getFileExtension() == null )
		{
			path_returned = path_returned.addFileExtension(extension);
		}
		else if ( !path_returned.getFileExtension().equals(extension) )
		{
			path_returned = path_returned.removeFileExtension();
			path_returned = path_returned.addFileExtension(extension);
		}

		return path_returned;
	}
}