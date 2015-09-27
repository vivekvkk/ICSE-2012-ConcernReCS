/**
 * Federal University of Minas Gerais 
 * Department of Computer Science
 * ConcernReCS Project
 *
 * Created by Pericles Alves
 * Date: 11/11/2011
 */

/**The singleton model provider for the ConcernReCS view*/

package ufmg.crcs.ui.views;

import java.util.*;

import org.eclipse.jface.viewers.TableViewer;

import ufmg.crcs.ConcernReCS;
import ufmg.crcs.smells.*;

public enum ConcernReCSModelProvider 
{
	INSTANCE; //The singleton instance

	private ArrayList<CodeSmell> code_smells; //Code Smells to be shown in the viewer
	private TableViewer viewer; //TableViewer in which the Code Smells should be shown
	private ConcernReCSView view; //ConcernReCS main view
	
	private ConcernReCSModelProvider() 
	{
		code_smells=null;
	}

	/**
	 * Initializes the model provider and return an empty list of Code Smells
	 */
	public ArrayList<CodeSmell> initializeModelProvider(ConcernReCSView view) 
	{
		this.view=view;
		
		setViewer(view.getViewer());
		
		return code_smells;
	}
	
	/**
	 * Sets the viewer in which the Code Smells will be shown
	 * @param viewer
	 */
	private void setViewer(TableViewer viewer)
	{
		this.viewer=viewer;
	}
	
	/**
	 * Sets the new list of Code Smells to be shown in the viewer
	 * @param code_smells
	 */
	private void setCodeSmells(ArrayList<CodeSmell> code_smells)
	{
		this.code_smells=code_smells;
	}
	
	/**
	 * Returns the Code Smells being presented in the viewer
	 */
	public ArrayList<CodeSmell> getCodeSmells()
	{
		return code_smells;
	}
	
	/**
	 * Responsible for change the Smells being presented in the viewer
	 * @param code_smells
	 */
	public void codeSmellsChanged(ArrayList<CodeSmell> code_smells)
	{
		setCodeSmells(code_smells);
		
		resetViewerInput();
		
		ConcernReCS.getDefault().contentChanged();
		
		view.updateActionState();
	}
	
	/**
	 * Reset the input data in order to redrawn the viewer
	 */
	private void resetViewerInput()
	{
		viewer.setInput(code_smells.toArray());
	}
}