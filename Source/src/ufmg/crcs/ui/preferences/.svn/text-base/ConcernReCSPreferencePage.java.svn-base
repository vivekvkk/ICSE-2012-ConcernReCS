/**
 * Federal University of Minas Gerais 
 * Department of Computer Science
 * ConcernReCS Project
 *
 * Created by Pericles Alves
 * Date: 10/27/2011
 */

/**
 * Class responsible to show the ConcerReCS preference page
 */

package ufmg.crcs.ui.preferences;

import java.util.*;

import org.eclipse.jface.preference.*;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;

import ufmg.crcs.ConcernReCS;
import ufmg.crcs.concernmapper.*;
import ufmg.crcs.smells.*;

public class ConcernReCSPreferencePage	extends FieldEditorPreferencePage implements IWorkbenchPreferencePage 
{
	public static final String ERROR_PRONENESS= "error_proneness_filter"; //Constant for the error-proneness filter	
	
	public ConcernReCSPreferencePage() 
	{
		super(GRID);
		setPreferenceStore(ConcernReCS.getDefault().getPreferenceStore());
		setDescription("");
	}
	
	/**
	 *Creates the fields of the preference page
	 */
	public void createFieldEditors() 
	{	
		CodeSmellsCollector collector=new CodeSmellsCollector(); //Collector which will provide the names of the Code Smells
		
		ArrayList<String> concerns=ConcernMapperInterface.getConcernNames(); //Concerns added to the ConcernMapper
		ArrayList<String> smells_names=collector.getSmellsNames(); //Names of all kinds of Code Smells
		
		//Creates the label for the concern filter area
		addField(new LabelFieldEditor(
			"Concerns in which Refactoring Code Smells should be sought", 
			getFieldEditorParent() ) );
		
		//Creates the concern filter area, i.e.,  a boolean field for each concern 
		for(String concern:concerns)
		{	
			addField(new BooleanFieldEditor(concern,concern,getFieldEditorParent()));
		}
		
		//Creates the label for the Code Smells filter area
		addField(new LabelFieldEditor(
			"\nConcern Refactoring Code Smells which will be sought in the code", 
			getFieldEditorParent() ) );
				
		//Creates the Code Smells filter area, i.e.,  a boolean field for each Code Smell 
		for(String smell:smells_names)
		{	
			addField(new BooleanFieldEditor(smell,smell,getFieldEditorParent()));
		}
		
		//Sets all the default values as "true"
		PreferenceInitializer.initializeDefaultPreferences(true);
	}

	/**
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) 
	{
	}
	
	//Class responsible to print a text line in the preference page
	class LabelFieldEditor extends FieldEditor
	{
		private Label aLabel;

		/**
		 * All labels can use the same preference name since they don't
		 * store any preference.
		 * @param pValue The value for the label.
		 * @param pParent The parent widget.
		 */
		public LabelFieldEditor(String pValue, Composite pParent)
		{
			super( "label", pValue, pParent );
		}

		/**
		 * Adjusts the field editor to be displayed correctly
		 * for the given number of columns.
		 * @see org.eclipse.jface.preference.FieldEditor#adjustForNumColumns(int)
		 * @param pNumColumns the number of columns
		 */
		protected void adjustForNumColumns(int pNumColumns)
		{
			((GridData) aLabel.getLayoutData()).horizontalSpan = pNumColumns;
		}

		/**
		 * Fills the field editor's controls into the given parent.
		 * @see org.eclipse.jface.preference.FieldEditor#doFillIntoGrid(org.eclipse.swt.widgets.Composite, int)
		 * @param pParent the composite used as a parent for the basic controls;
		 *	the parent's layout must be a <code>GridLayout</code>
		 * @param pNumColumns the number of columns
		 * 
		 */
		protected void doFillIntoGrid( Composite pParent, int pNumColumns )
		{
			aLabel = getLabelControl( pParent );
			
			GridData lGridData = new GridData();
			lGridData.horizontalSpan = pNumColumns;
			lGridData.horizontalAlignment = GridData.FILL;
			lGridData.grabExcessHorizontalSpace = false;
			lGridData.verticalAlignment = GridData.CENTER;
			lGridData.grabExcessVerticalSpace = false;
			
			aLabel.setLayoutData( lGridData );
		}

		/**
		 * @see org.eclipse.jface.preference.FieldEditor#getNumberOfControls()
		 * @return the number of controls
		 */
		public int getNumberOfControls() 
		{ return 1; }
		
		/**
		 * @see org.eclipse.jface.preference.FieldEditor#doLoad()
		 */
		protected void doLoad() {}
		
		/**
		 * @see org.eclipse.jface.preference.FieldEditor#doLoadDefault()
		 */
		protected void doLoadDefault() {}
		
		/**
		 * @see org.eclipse.jface.preference.FieldEditor#doStore()
		 */
		protected void doStore() {}
	}
}