/**
 * Federal University of Minas Gerais 
 * Department of Computer Science
 * ConcernReCS Project
 *
 * Created by Pericles Alves
 * Date: 08/01/2011
 */

/**Class responsible to find a specific kind of code smells in the source code*/

package ufmg.crcs.smells;

import java.util.ArrayList;

public abstract class CodeSmellFinder 
{
	protected final int field=8; //Constant value used by the Java Model to identify a field
	protected final int method=9; //Constant value used by the Java Model to identify a method
	
	/**
	 * This method should be overwritten by the children of the CodeSmellFinder class
	 * @param concerns
	 * @return an ArrayList of Code Smells if them have been found in the selected concerns
	 */
	protected abstract ArrayList<CodeSmell> findCodeSmells(ArrayList<String> concerns);
}