/**
 * Federal University of Minas Gerais 
 * Department of Computer Science
 * ConcernReCS Project
 *
 * Created by Pericles Alves
 * Date: 10/31/2011
 */

/**This class works as interface between ConcernReCS and the ConcernMapper plug-in*/

package ufmg.crcs.concernmapper;

import java.util.*;

import ca.mcgill.cs.serg.cm.*;
import org.eclipse.jdt.core.*;

public abstract class ConcernMapperInterface 
{
	/**
	 * @return the names of all the concerns
	 */
	public static ArrayList<String> getConcernNames()
	{
		String[] concerns_array; //Concerns names obtained from the ConcernMapper
		
		ArrayList<String> concerns=new ArrayList<String>(); //Resulting list of concerns names
		
		concerns_array=ConcernMapper.getDefault().getConcernModel().getConcernNames();
	
		//Converts the array of concerns names to an ArrayList
		for(String concern:concerns_array)
		{
			concerns.add(concern);
		}
		
		return concerns;
	}
	
	/**
	 * Returns all the elements of a given concern
	 * @param concern
	 * @return an array with the elements
	 */
	public static ArrayList<IJavaElement> getConcernElements(String concern)
	{
		int i; //Iterator
		
		Object[] obj_elements; //Elements of the given concern as simple objects
		
		obj_elements=ConcernMapper.getDefault().getConcernModel().getElements(concern).toArray();

		ArrayList<IJavaElement> elements=new ArrayList<IJavaElement>(); //Elements of the given concern as Java elements
		
		//Converts each element in the concern from type Object to IJavaElement
		for(i=0;i<obj_elements.length;i++)
		{
			elements.add((IJavaElement)obj_elements[i]);
		}
		
		return elements;
	}
}