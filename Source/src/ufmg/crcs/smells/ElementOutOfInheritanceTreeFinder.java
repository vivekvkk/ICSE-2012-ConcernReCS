package ufmg.crcs.smells;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.search.FieldReferenceMatch;
import org.eclipse.jdt.core.search.SearchMatch;

import ufmg.crcs.concernmapper.ConcernMapperInterface;
import ufmg.crcs.properties.SearchMatchesCollector;

public class ElementOutOfInheritanceTreeFinder extends CodeSmellFinder 
{
	protected ArrayList <CodeSmell> findCodeSmells(ArrayList<String> concerns)
	{
		ArrayList <CodeSmell> code_smells = new ArrayList <CodeSmell>();
		SearchMatchesCollector searchmatchescollector = new SearchMatchesCollector();
		
		//Looks for the Code Smell in each of the selected concerns
		for(String concern:concerns)
		{
			ArrayList<IJavaElement> concern_elements = ConcernMapperInterface.getConcernElements(concern); //Elements added to the ConcernMapper plug-in
			
			for(IJavaElement element:concern_elements)
			{
				//Candidates to Non-dedicated implementation elements
				ArrayList<SearchMatch> element_references = searchmatchescollector.getElementReferences(element);
				
				//Remove references that appear within Dedicated implementation elements
				for(IJavaElement concern_element:concern_elements)
				{
					//Iterators are the only safe way to iterate over a list in which elements are being removed
					Iterator<SearchMatch> search_match = element_references.iterator();
					
					while(search_match.hasNext())
					{
						IJavaElement match_element = (IJavaElement)((SearchMatch)search_match.next()).getElement();
					
						if(match_element.equals(concern_element))
							search_match.remove();
					}
				}
				
				
				Set<SearchMatch> leaves = new HashSet<SearchMatch>();
				
				//Tries to find inheritance trees among the Non-dedicated implementation elements
				for(SearchMatch a_match:element_references)
				{
					for(SearchMatch b_match:element_references)
					{
						if(a_match == b_match)
							continue;
						
						String a_signature = "a"; //Super type signature of the first element
						String b_signature = "b"; //Super type signature of the second element
						
						IType a_type = null;
						IType b_type = null;
					
						if((((IJavaElement)a_match.getElement()) instanceof IField)||(((IJavaElement)a_match.getElement()) instanceof IMethod))
						{
							try
							{
								a_type = ((IMember)a_match.getElement()).getDeclaringType();
								a_signature = a_type.getSuperclassTypeSignature();
							}
							catch(JavaModelException exception)
							{
								return null;
							}
						}
						else if(((IJavaElement)a_match.getElement()) instanceof IField)
						{
							try
							{
								a_type = ((IType)a_match.getElement());
								a_signature = a_type.getSuperclassTypeSignature();
							}
							catch(JavaModelException exception)
							{
								return null;
							}
						}
						
						if((((IJavaElement)b_match.getElement()) instanceof IField)||(((IJavaElement)b_match.getElement()) instanceof IMethod))
						{
							try
							{
								b_type = ((IMember)b_match.getElement()).getDeclaringType();
								b_signature = b_type.getSuperclassTypeSignature();
							}
							catch(JavaModelException exception)
							{
								return null;
							}
						}
						else if(((IJavaElement)b_match.getElement()) instanceof IField)
						{
							try
							{
								b_type = ((IType)b_match.getElement());
								b_signature = b_type.getSuperclassTypeSignature();
							}
							catch(JavaModelException exception)
							{
								return null;
							}
						}
						
						if((a_signature != null) && (b_signature != null) && (!a_type.equals(b_type)))
						{
							if(a_signature.equals(b_signature))
							{
								leaves.add(a_match);
								leaves.add(b_match);
							}
						}
					}
				}
				
				if(!leaves.isEmpty())
				{
					//Remove leaf-classes from the list
					for(SearchMatch leaf:leaves)
					{
						element_references.remove(element_references.indexOf(leaf));
					}
				
					//Create the resulting Code Smells
					for(SearchMatch search_match:element_references)
					{
						String source = ((IJavaElement)search_match.getElement()).getResource().getName(); //File in which the element is declared
						String where = "";

						if(element instanceof IField)
						{
							if(((FieldReferenceMatch)search_match).isReadAccess())
								where = "Read acces to the "+((IField)element).getElementName()+" field";
							else if(((FieldReferenceMatch)search_match).isWriteAccess())
								where = "Write acces to the "+((IField)element).getElementName()+" field";
						}
						else if(element instanceof IMethod)
						{
							where = "Call to the "+((IMethod)element).getElementName()+" method";
						}
					
						ElementOutOfInheritanceTree smell=new ElementOutOfInheritanceTree(concern,source,where);
				
						code_smells.add(smell); //Stores the code smell
					}
				}
			}
		}
	
		return code_smells;
	}
}