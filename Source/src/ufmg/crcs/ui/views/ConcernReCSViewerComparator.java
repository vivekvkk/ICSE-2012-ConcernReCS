/**
 * Federal University of Minas Gerais 
 * Department of Computer Science
 * ConcernReCS Project
 *
 * Created by Pericles Alves
 * Date: 11/14/2011
 */

/**Class defining the viewer sort criteria*/

package ufmg.crcs.ui.views;

import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;

import ufmg.crcs.smells.CodeSmell;

public class ConcernReCSViewerComparator extends ViewerComparator
{
	private int property_index; //Property being evaluated
	private static final int DESCENDING = 1; //Descending direction
	private int direction = DESCENDING; //Sort direction

	public ConcernReCSViewerComparator() 
	{
		//Initializes the default values
		this.property_index = 0;
		direction = DESCENDING;
	}

	/**
	 * Returns the current sort direction
	 * @return the direction
	 */
	public int getDirection() 
	{
		return direction == 1 ? SWT.DOWN : SWT.UP;
	}

	/**
	 * Sets the column being sorted
	 * @param column
	 */
	public void setColumn(int column) 
	{
		if (column == this.property_index) 
		{
			//If the column is the same as last sort, it toggles the direction
			direction = 1 - direction;
		} 
		else 
		{
			//If the column is a new one, it does an ascending sort
			this.property_index = column;
			direction = DESCENDING;
		}
	}

	/**
	 * Compares in alphabetic order an specific property of two Code Smells 
	 * @return a number indicating the Code Smells order
	 */
	public int compare(Viewer viewer, Object e1, Object e2) 
	{
		//Code Smells to be compared
		CodeSmell code_smell1 = (CodeSmell) e1;	
		CodeSmell code_smell2 = (CodeSmell) e2;
		
		int order = 0; //Represents the alphabetical order of the given Code Smells
		
		//Sort the Code Smells according to the chosen property
		switch (property_index) 
		{
			case 0: //For the name column
				order=code_smell1.getName().compareTo(code_smell2.getName());
			
				break;
		
			case 1: //For the mistake column
				order=code_smell1.getMistake().compareTo(code_smell2.getMistake()); 
				
				break;
			
			case 2: //For the concern column
				order=code_smell1.getConcern().compareTo(code_smell2.getConcern());
				
				break;
			
			case 3: //For the error-proneness column
				order=code_smell1.getErrorProneness().compareTo(code_smell2.getErrorProneness());
				
				break;
			
			case 4: //For the source column
				order=code_smell1.getSource().compareTo(code_smell2.getSource());
				
				break;
				
			case 5: //For the where column
				order=code_smell1.getWhere().compareTo(code_smell2.getWhere());
				
				break;
				
			default:
				order=0;
		}
		
		//If descending order, it flips the direction
		if (direction == DESCENDING) 
		{
			order = -order;
		}

		return order;
	}
}