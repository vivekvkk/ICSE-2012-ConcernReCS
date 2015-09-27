/**
 * Federal University of Minas Gerais 
 * Department of Computer Science
 * ConcernReCS Project
 *
 * Created by Pericles Alves
 * Date: 08/01/2011
 */

/**Class responsible to store and gather all the kinds of Code Smells*/

package ufmg.crcs.smells;

import java.util.*;

import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

import ufmg.crcs.concernmapper.*;

public class CodeSmellsCollector 
{
	//Strings representing all the kinds of Code Smells
	private final String PRIMITIVE_CONSTANT_ATTRIBUTE="Primitive constant attribute";
	private final String STATIC_ELEMENT = "Static element";
	private final String ATTRIBUTE_OF_A_NON_DEDICATED_TYPE = "Attribute of a non-dedicated type";
	private final String CONDITIONAL_CONSTRUCTION_EVAL_LOCAL_VAR = "Conditional construction evaluating a local variable";
	private final String DIVERGENT_ADVICE = "Divergent advice";
	private final String ELEMENT_OUT_OF_INHERIT_TREE = "Element out of inheritance tree";
	
	private Hashtable<String, Boolean> smells = new Hashtable<String, Boolean>(); //Stores if each Code Smell should or not to be sought
	private ArrayList<String> concerns; //Stores the names of the concerns in which Code Smells should be sought
	
	/**
	 * All kinds of Code Smells should be sought in all the concerns by default 
	 */
	public CodeSmellsCollector ()
	{
		concerns=ConcernMapperInterface.getConcernNames();
		
		smells.put(PRIMITIVE_CONSTANT_ATTRIBUTE,true);
		smells.put(STATIC_ELEMENT,true);
		smells.put(ATTRIBUTE_OF_A_NON_DEDICATED_TYPE,true);
		smells.put(CONDITIONAL_CONSTRUCTION_EVAL_LOCAL_VAR,true);
		smells.put(DIVERGENT_ADVICE,true);
		smells.put(ELEMENT_OUT_OF_INHERIT_TREE,true);
	}
	
	/**
	 * @return an ArrayList with the names of all kinds of CodeSmells
	 */
	public ArrayList<String> getSmellsNames()
	{
		ArrayList<String> smells_names=new ArrayList<String>();
				
		//Adds the names of all kinds of Code Smells
		smells_names.add(PRIMITIVE_CONSTANT_ATTRIBUTE);
		smells_names.add(STATIC_ELEMENT);
		smells_names.add(ATTRIBUTE_OF_A_NON_DEDICATED_TYPE);
		smells_names.add(CONDITIONAL_CONSTRUCTION_EVAL_LOCAL_VAR);
		smells_names.add(DIVERGENT_ADVICE);
		smells_names.add(ELEMENT_OUT_OF_INHERIT_TREE);
		
		return smells_names;
	}
	
	/**
	 * Defines the kinds of Code Smells which the ConcernReCS plug-in should not look for
	 * @param Code Smells to be disabled
	 */
	public void disableSmells(ArrayList<String> disabled_smells)
	{
		for(String smell:disabled_smells)
		{
			smells.put(smell,false);
		}
	}
	
	/**
	 * Defines the names of the concerns in which Code Smells should not be sought
	 * @param concerns to be disabled
	 */
	public void disableConcerns(ArrayList<String> disabled_concerns)
	{
		int flag; //Flag indicating that a concern has been already disabled. It avoids unnecessary iterations
		
		//Removes all the disabled concerns of the concerns in which Code Smells will be sought
		for(String disabled_concern:disabled_concerns)
		{
			flag=0;
			
			for(String concern:concerns)
			{
				if(concern==disabled_concern) 
				{
					concerns.remove(concerns.indexOf(concern));
					
					flag=1;
				}
				
				if(flag==1)break;
			}
		}
	}
	
	public ArrayList<CodeSmell> collectCodeSmells()
	{	
		ArrayList<CodeSmell> found_smells=new ArrayList<CodeSmell>(); //Code Smells found in the source code
		
		//Collect each kind of Code Smell if it should be collected
		if(smells.get(PRIMITIVE_CONSTANT_ATTRIBUTE)==true)
		{
			PrimitiveConstantAttributeFinder finder=new PrimitiveConstantAttributeFinder();
			
			found_smells.addAll(finder.findCodeSmells(concerns));
		}
		
		if(smells.get(STATIC_ELEMENT)==true)
		{
			StaticElementFinder finder=new StaticElementFinder();
			
			found_smells.addAll(finder.findCodeSmells(concerns));
		}
		
		if(smells.get(ATTRIBUTE_OF_A_NON_DEDICATED_TYPE)==true)
		{
			AttributeOfANonDedicatedTypeFinder finder=new AttributeOfANonDedicatedTypeFinder();
			
			found_smells.addAll(finder.findCodeSmells(concerns));
		}
		
		if(smells.get(CONDITIONAL_CONSTRUCTION_EVAL_LOCAL_VAR)==true)
		{
			ConditionalConstructionEvaluatingALocalVariableFinder finder=new ConditionalConstructionEvaluatingALocalVariableFinder();
			
			found_smells.addAll(finder.findCodeSmells(concerns));
		}
		
		if(smells.get(DIVERGENT_ADVICE)==true)
		{
			DivergentAdviceFinder finder=new DivergentAdviceFinder();
			
			found_smells.addAll(finder.findCodeSmells(concerns));
		}
		
		if(smells.get(ELEMENT_OUT_OF_INHERIT_TREE)==true)
		{
			ElementOutOfInheritanceTreeFinder finder=new ElementOutOfInheritanceTreeFinder();
			
			found_smells.addAll(finder.findCodeSmells(concerns));
		}
		
		return found_smells;
	}

	public static ArrayList<IType> getDedicatedTypes(String concern) 
	{
		ArrayList<IType> dedicated_types=new ArrayList<IType>();
		ArrayList<IJavaElement> concern_elements=ConcernMapperInterface.getConcernElements(concern); //Elements added to the ConcernMapper plug-in
		
		//Inspects the type of each concern element
		for(IJavaElement element:concern_elements)
		{
			IType type=((IMember)element).getDeclaringType(); //Element type
			
			boolean dedicated=true; //Indicates whether this type is completely dedicated to the concern 
			
			try
			{	
				//Inspect all the fields of the type
				for(IField field:type.getFields())
				{
					boolean exists=false;
					
					for(IJavaElement concern_element:concern_elements)
					{
						if(concern_element.equals(field))exists=true;
					}
					
					if(exists==false)dedicated=false;
					if(exists==false)break;
				}
			
				if(dedicated==true)
				{
					for(IMethod method:type.getMethods())
					{
						boolean exists=false;
						
						for(IJavaElement concern_element:concern_elements)
						{
							if(concern_element.equals(method))exists=true;
						}
						
						if(exists==false)dedicated=false;
						if(exists==false)break;
					}
				}
			}
			catch(JavaModelException exception)
			{
				return null;
			}
			
			if((dedicated==true)&&(!dedicated_types.contains(type)))dedicated_types.add(type);
		}
		
		return dedicated_types;
	}
}