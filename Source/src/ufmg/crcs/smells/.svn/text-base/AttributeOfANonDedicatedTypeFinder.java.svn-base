/**
 * Federal University of Minas Gerais 
 * Department of Computer Science
 * ConcernReCS Project
 *
 * Created by Pericles Alves
 * Date: 20/14/2011
 */

package ufmg.crcs.smells;

import java.util.ArrayList;

import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IType;

import ufmg.crcs.concernmapper.ConcernMapperInterface;
import ufmg.crcs.properties.SearchMatchesCollector;

public class AttributeOfANonDedicatedTypeFinder extends CodeSmellFinder 
{	
	/**
	 * Implements the algorithm to find the Dedicated implementation constant code smell
	 */
	protected ArrayList <CodeSmell> findCodeSmells(ArrayList<String> concerns)
	{	
		ArrayList <CodeSmell> code_smells=new ArrayList <CodeSmell>();
		
		//Looks for the Code Smell in each of the selected concerns
		for(String concern:concerns)
		{
			ArrayList<IType> dedicated_types=CodeSmellsCollector.getDedicatedTypes(concern); //Types completely dedicated to the concern
			ArrayList<IJavaElement> concern_elements=ConcernMapperInterface.getConcernElements(concern); //Elements added to the ConcernMapper plug-in

			//Looks for the code smells in all the elements of the concern
			for(IJavaElement element:concern_elements)
			{
				//If the element is a class field
				if(element.getElementType()==field)
				{	
					boolean dedicated=false;
					
					//Verifies if the field belongs to a dedicated type
					for(IType type:dedicated_types)
					{
						SearchMatchesCollector matches_collector=new SearchMatchesCollector();
						
						for(IField field:matches_collector.getFieldToTypeReferences(type))
						{
							if(field.equals(element))dedicated=true;
						}
					}
					
					if(!dedicated)
					{
						String source=element.getResource().getName(); //File in which this element is declared
							
						String where; //Which of the file elements this element is
							
						where="Field "+element.getElementName();
					
						AttributeOfANonDedicatedType smell=new AttributeOfANonDedicatedType(concern,source,where);
						
						code_smells.add(smell); //Stores the code smell
					}
				}
			}
		}
	
		return code_smells;
	}
}
