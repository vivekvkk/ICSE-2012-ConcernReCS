package ufmg.crcs.smells;

import java.util.ArrayList;

import org.eclipse.jdt.core.IJavaElement;

import ufmg.crcs.concernmapper.ConcernMapperInterface;

public class DivergentAdviceFinder extends CodeSmellFinder 
{
	protected ArrayList <CodeSmell> findCodeSmells(ArrayList<String> concerns)
	{
		ArrayList <CodeSmell> code_smells=new ArrayList <CodeSmell>();
		
		//Looks for the Code Smell in each of the selected concerns
		for(String concern:concerns)
		{
			ArrayList<IJavaElement> concern_elements=ConcernMapperInterface.getConcernElements(concern); //Elements added to the ConcernMapper plug-in
			
			//...
		}
	
		return code_smells;
	}
}