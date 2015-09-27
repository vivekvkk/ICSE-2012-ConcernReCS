/**
 * Federal University of Minas Gerais 
 * Department of Computer Science
 * ConcernReCS Project
 *
 * Created by Pericles Alves
 * Date: 08/01/2011
 */

/**An action to find code smells in the source code*/

package ufmg.crcs.ui.actions;

import java.util.ArrayList;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.TableViewer;

import ufmg.crcs.ConcernReCS;
import ufmg.crcs.concernmapper.*;
import ufmg.crcs.smells.*;
import ufmg.crcs.ui.views.ConcernReCSModelProvider;

public class RefreshAction extends Action
{	
	public RefreshAction(TableViewer viewer)
	{	
		setText("Refresh");
		setToolTipText("Refresh");
		setImageDescriptor( ConcernReCS.getImageDescriptor("icons/refresh.gif") );
	}
	
	/**
	 * Executes the refresh action
	 */
	public void run() 
	{	
		ArrayList<CodeSmell> code_smells; //Concern Refactoring Code Smells found in the source code 
		IPreferenceStore store=ConcernReCS.getDefault().getPreferenceStore(); //Preference store of the ConcernReCS plug-in
		CodeSmellsCollector collector=new CodeSmellsCollector();
		ArrayList<String> disabled_smells=new ArrayList<String>(); //Code Smells which should not to be sought in the code
		ArrayList<String> disabled_concerns=new ArrayList<String>(); //Concerns in which Code Smells should not to be sought
		ArrayList<String> smells_names=collector.getSmellsNames(); //The names of all possible kinds of Code Smells
		ArrayList<String> concerns_names=ConcernMapperInterface.getConcernNames(); //The names of all concerns added to the ConcernMapper
		
		//Verifies which Code Smells should not to be sought in the code
		for(String smell:smells_names)
		{
			if(store.contains(smell))
			{
				if(store.getBoolean(smell)==false)disabled_smells.add(smell);
			}
		}
		
		//Verifies the concerns in which Code Smells should not to be sought
		for(String concern:concerns_names)
		{
			if(store.contains(concern))
			{
				if(store.getBoolean(concern)==false)disabled_concerns.add(concern);
			}
		}
				
		collector.disableSmells(disabled_smells);
		
		collector.disableConcerns(disabled_concerns);		
		
		code_smells=collector.collectCodeSmells(); //Retrieves the list of Code Smells existing in the source code
		
		ConcernReCSModelProvider.INSTANCE.codeSmellsChanged(code_smells); //Reset the input data for the ConcernReCS main view
	}
}