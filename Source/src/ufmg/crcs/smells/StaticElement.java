/**
 * Federal University of Minas Gerais 
 * Department of Computer Science
 * ConcernReCS Project
 *
 * Created by Pericles Alves
 * Date: 20/14/2011
 */

package ufmg.crcs.smells;

public class StaticElement extends CodeSmell 
{
	private static final String NAME="Static element"; //The name of the code smell
	private static final String MISTAKE="Incomplete refactoring"; //The mistake that the smell can lead
	private static final double ERROR_PRONENESS=1; //Error-proneness of the code smell
	
	/**
	 * Initializes the code smell with a constant value for the name, mistake and error_proneness
	 */
	public StaticElement(String concern,String source,String where)
	{
		super(NAME,MISTAKE,concern,ERROR_PRONENESS,source,where);
	}

}
