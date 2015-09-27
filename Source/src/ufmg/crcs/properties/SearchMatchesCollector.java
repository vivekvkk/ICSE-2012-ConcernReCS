/**
 * Federal University of Minas Gerais 
 * Department of Computer Science
 * ConcernReCS Project
 *
 * Created by Pericles Alves
 * Date: 09/19/2011
 */

/**Class responsible to find references to fields and methods*/

package ufmg.crcs.properties;

import java.util.*;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.*;
import org.eclipse.jdt.core.search.*;

public class SearchMatchesCollector 
{
	/**
	 * Collect references to a type method or field using the Java search engine
	 * @param the element whose the references should be collected
	 * @return the search matches corresponding to the references to the given type
	 */
	public ArrayList<SearchMatch> getElementReferences(IJavaElement element)
	{
		if((!(element instanceof IType))&&(!(element instanceof IMethod))&&(!(element instanceof IField))) return null; //In case the given element is not a type
		
		SearchPattern pattern=SearchPattern.createPattern(element,IJavaSearchConstants.REFERENCES); //A pattern to the search, represented by references to the given method or field

		IJavaSearchScope scope=SearchEngine.createJavaSearchScope(new IJavaElement[] {element.getJavaProject()}); //The search scope, represented by the project which the method or field belongs
	
		MySearchRequester requester=new MySearchRequester(); //Search requester
		
		//Begins the search
		try
		{
			SearchEngine searchEngine = new SearchEngine(); //Creates a new search engine 
			
			searchEngine.search(pattern, new SearchParticipant[] {SearchEngine.getDefaultSearchParticipant()}, scope, requester, null); //Perform the search
		}
		catch(CoreException exception)
		{
			return null; //If an error occur during the search
		}
			
		return requester.getSearchMatches(); //Return the collected search matches
	}
	
	/**
	 * Returns all the fields referencing a given type
	 */
	public ArrayList<IField> getFieldToTypeReferences(IType type)
	{
		ArrayList<IField> fields=new ArrayList<IField>();
		ArrayList<SearchMatch> matches=getElementReferences(type);
		
		for(SearchMatch match:matches)
		{
			Object element=match.getElement();
			
			if(element instanceof IField)fields.add((IField)element);
		}
		
		return fields;
	}
}
