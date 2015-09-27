/**
 * Federal University of Minas Gerais 
 * Department of Computer Science
 * ConcernReCS Project
 *
 * Created by Pericles Alves
 * Date: 10/27/2011
 */

/**
 * Class used to initialize default preferences values
 */

package ufmg.crcs.ui.preferences;

import java.util.*;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import ufmg.crcs.concernmapper.*;
import ufmg.crcs.smells.CodeSmellsCollector;
import ufmg.crcs.ConcernReCS;

public class PreferenceInitializer extends AbstractPreferenceInitializer 
{
	/**
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() 
	{
	}
	
	/**
	 * Initializes all the boolean fields with the same value
	 */
	public static void initializeDefaultPreferences(boolean value)
	{
		CodeSmellsCollector collector=new CodeSmellsCollector(); //Collector which will provide the names of the Code Smells
		
		ArrayList<String> concerns=ConcernMapperInterface.getConcernNames(); //Concerns added to the ConcernMapper plug-in
		
		ArrayList<String> smells_names=collector.getSmellsNames(); //Names of all kinds of Code Smells
				
		//Initializes the fields of the Code Smells filter area
		for(String smell:smells_names)
		{	
			IPreferenceStore store = ConcernReCS.getDefault().getPreferenceStore();
			store.setDefault(smell, value);
		}
		
		//Initializes the fields of the concern filter area
		for(String concern:concerns)
		{
			IPreferenceStore store = ConcernReCS.getDefault().getPreferenceStore();
			store.setDefault(concern, value);
		}
	}
}