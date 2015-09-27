/**
 * Federal University of Minas Gerais 
 * Department of Computer Science
 * ConcernReCS Project
 *
 * Created by Pericles Alves
 * Date: 08/01/2011
 */

/**Abstract class for code smells*/

package ufmg.crcs.smells;

public abstract class CodeSmell 
{
	private String name; //The name of the code smell
	private String mistake; //The mistake that the smell can lead
	private String concern; //Name of the concern related to the code smell
	private double error_proneness; //Error-proneness of the code smell
	private String source; //File in which the code smell was found
	private String where; //Where this code smells was found
	
	protected CodeSmell(String name,String mistake,String concern,double error_proneness,String source,String where)
	{
		setName(name);
		setMistake(mistake);
		setConcern(concern);
		setErrorProneness(error_proneness);
		setSource(source);
		setWhere(where);
	}
	
	private void setName(String name)
	{
		this.name=name;
	}
	
	public String getName()
	{
		return name;
	}
	
	private void setMistake(String mistake)
	{
		this.mistake=mistake;
	}
	
	public String getMistake()
	{
		return mistake;
	}
	
	private void setConcern(String concern)
	{
		this.concern=concern;
	}
	
	public String getConcern()
	{
		return concern;
	}
	
	private void setErrorProneness(double error_proneness)
	{
		this.error_proneness=error_proneness;
	}
	
	public String getErrorProneness()
	{
		String error_proneness=new String(""+this.error_proneness);
		
		return error_proneness;
	}
	
	private void setSource(String source)
	{
		this.source=source;
	}
	
	public String getSource()
	{
		return source;
	}
	
	private void setWhere(String where)
	{
		this.where=where;
	}
	
	public String getWhere()
	{
		return where;
	}
}