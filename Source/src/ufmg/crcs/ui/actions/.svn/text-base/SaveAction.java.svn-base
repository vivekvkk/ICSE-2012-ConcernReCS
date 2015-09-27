/**
 * Federal University of Minas Gerais 
 * Department of Computer Science
 * ConcernReCS Project
 *
 * Created by Pericles Alves
 * Date: 08/01/2011
 */

/**An action to save the found code smells*/

package ufmg.crcs.ui.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;

import ufmg.crcs.ConcernReCS;
import ufmg.crcs.ui.views.ConcernReCSModelProvider;
import ufmg.crcs.ui.views.ConcernReCSView;
import ufmg.crcs.smells.io.*;

public class SaveAction extends Action
{
	ConcernReCSView view; //The ConcernReCS main view
	
	public SaveAction(ConcernReCSView view)
	{
		this.view=view;
		
		//Sets the action text
		setText("Save");
		setToolTipText("Save");
		
		//Sets the action image
		setImageDescriptor( ConcernReCS.getImageDescriptor("icons/save.gif") );
		setDisabledImageDescriptor( ConcernReCS.getImageDescriptor("icons/saved.gif")); 		 
	}
	
	public void run()
	{
		IFile file = ConcernReCS.getDefault().getDefaultResource();
		
		if( file == null)
		{
			new SaveAsAction(view).run();
			
			return;
		}
		
		try
		{
			file.getParent().refreshLocal(IResource.DEPTH_ONE,null);
		}
		catch(CoreException exception)
		{
			MessageDialog.openError( PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),"Save error",
					"The file could not be saved." + " " + exception.getMessage());
		}
		catch(OperationCanceledException exception)
		{
			MessageDialog.openError( PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),"Save error",
					"The file could not be saved." + " " + exception.getMessage());
		}
	
		if( !file.exists() )
		{
			new SaveAsAction(view).run();
			
			return;
		}
		
		try
		{
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
		
		ConcernReCS.getDefault().resetDirty();
		
		if(view!=null)
		{
			view.updateActionState();
		}
	}
}
