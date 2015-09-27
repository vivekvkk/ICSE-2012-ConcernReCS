/**
 * Federal University of Minas Gerais 
 * Department of Computer Science
 * ConcernReCS Project
 *
 * Created by Pericles Alves
 * Date: 16/11/2011
 */

/**Implements the case-sensitive search filter for the main view of ConcernReCS*/

package ufmg.crcs.ui.views;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import ufmg.crcs.smells.CodeSmell;

public class CodeSmellFilter extends ViewerFilter
{
	private String search_string; //String representing the search

	public void setSearchText(String search_string) 
	{
		this.search_string = ".*" + search_string + ".*"; // Search text must be a substring of the text in the view
	}

	/**
	 * Verifies if the text being sought matches at least one property of the Code Smells being shown in the view
	 */
	public boolean select(Viewer viewer, Object parentElement, Object element) 
	{
		//If the string being sought is empty
		if (search_string == null || search_string.length() == 0) 
		{
			return true;
		}
		
		CodeSmell code_smell=(CodeSmell) element;
		
		//Verifies the name
		if (code_smell.getName().matches(search_string)) 
		{
			return true;
		}
	
		//Verifies the mistake
		if (code_smell.getMistake().matches(search_string)) 
		{
			return true;
		}
	
		//Verifies the concern
		if (code_smell.getConcern().matches(search_string)) 
		{
			return true;
		}
	
		//Verifies the error-proneness
		if (code_smell.getErrorProneness().matches(search_string)) 
		{
			return true;
		}
	
		//Verifies the source
		if (code_smell.getSource().matches(search_string)) 
		{
			return true;
		}
	
		//Verifies the where
		if (code_smell.getWhere().matches(search_string)) 
		{
			return true;
		}

		return false;
	}
}