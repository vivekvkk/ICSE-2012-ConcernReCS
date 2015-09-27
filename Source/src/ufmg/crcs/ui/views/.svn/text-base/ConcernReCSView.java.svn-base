/**
 * Federal University of Minas Gerais 
 * Department of Computer Science
 * ConcernReCS Project
 *
 * Created by Pericles Alves
 * Date: 08/01/2011
 */

/**Main ConcernReCS view, to show the found Code Smells*/

package ufmg.crcs.ui.views;

import org.eclipse.jface.action.*;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.part.ViewPart;

import ufmg.crcs.ConcernReCS;
import ufmg.crcs.smells.*;
import ufmg.crcs.ui.actions.*;

public class ConcernReCSView extends ViewPart 
{	
	public static final String ID = "ufmg.crcs.views.ConcernReCSView"; //The ID of the view

	private ConcernReCSViewerComparator comparator; //The table sorter
	
	private TableViewer viewer; //Main viewer of the view
	private CodeSmellFilter filter; //Filter for the search field
	private RefreshAction refreshaction;
	private SaveAction saveaction;
	private SaveAsAction saveasaction;
	
	/**
	 * Creates the layout of the view
	 */
	public void createPartControl(Composite parent) 
	{
		//Sets the layout
		GridLayout layout = new GridLayout(2, false);
		parent.setLayout(layout);
		
		//Creates the search field
		Label search_label = new Label(parent, SWT.NONE);
		search_label.setText("Search: ");
		final Text search_text = new Text(parent, SWT.BORDER | SWT.SEARCH);
		search_text.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL));
		
		createViewer(parent);
		
		//Disposes the actions
		makeActions();
		hookContextMenu();
		contributeToActionBars();
		
		//Sets the viewer sorter
		comparator= new ConcernReCSViewerComparator();
		viewer.setComparator(comparator);
		
		//Supports the case-sensitive search
		search_text.addKeyListener(new KeyAdapter() 
		{
			public void keyReleased(KeyEvent event) 
			{
				filter.setSearchText(search_text.getText());
				viewer.refresh();
			}
		});

		filter = new CodeSmellFilter();
		viewer.addFilter(filter);
	}
	
	/**
	 * Creates the TableViewer 
	 * @param parent
	 */
	private void createViewer(Composite parent) 
	{
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		
		createColumns(parent, viewer);
		
		final Table table = viewer.getTable();
		
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		//Sets the viewer data source
		viewer.setContentProvider(new ArrayContentProvider());
		viewer.setInput(ConcernReCSModelProvider.INSTANCE.initializeModelProvider(this));
		
		getSite().setSelectionProvider(viewer); // Make the selection available to other views

		// Layout the viewer
		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		viewer.getControl().setLayoutData(gridData);
	}
	
	/**
	 * Initializes the actions
	 */
	private void makeActions() 
	{
		refreshaction = new RefreshAction(viewer) ;
		saveaction = new SaveAction(this); 
		saveasaction = new SaveAsAction(this); 
		
		saveaction.setEnabled(ConcernReCS.getDefault().isDirty());
	}

	/**
	 * Updates the action buttons
	 */
	public void updateActionState()
	{
		boolean enabled= ConcernReCS.getDefault().isDirty(); //Indicates if the view content has changed
		
		saveaction.setEnabled(enabled);

		getViewSite().getActionBars().updateActionBars();
	}
	
	/**
	 * Sets the context menu layout
	 */
	private void hookContextMenu() 
	{
		MenuManager menuMgr = new MenuManager("#PopupMenu");

		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() 
		{
			public void menuAboutToShow(IMenuManager manager) 
			{
				ConcernReCSView.this.fillContextMenu(manager);
			}
		});
		
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		
		viewer.getControl().setMenu(menu);
		
		getSite().registerContextMenu(menuMgr, viewer);
	}

	/**
	 * Sets the actions bar
	 */
	private void contributeToActionBars() 
	{
		IActionBars bars = getViewSite().getActionBars();
		
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) 
	{
		manager.add(refreshaction);
		manager.add(new Separator());
		manager.add(saveaction);
		manager.add(saveasaction);
	}

	private void fillContextMenu(IMenuManager manager) 
	{
		manager.add(refreshaction);
		manager.add(saveaction);
		manager.add(saveasaction);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
	
	private void fillLocalToolBar(IToolBarManager manager) 
	{
		manager.add(refreshaction);
		manager.add(saveaction);
		manager.add(saveasaction);
	}
	
	/**
	 * This will creates all the columns for the TableViewer
	 */
	private void createColumns(final Composite parent, final TableViewer viewer) 
	{
		String[] titles = { "Name", "Mistake", "Concern", "Error-proneness" , "Source" , "Where" }; //Columns tiltes
		int[] bounds = { 250, 180, 140, 100, 150, 160 }; //Columns sizes

		// First column is for the name
		TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
		
		col.setLabelProvider(new ColumnLabelProvider() 
			{
				public String getText(Object element) 
				{
					CodeSmell smell = (CodeSmell) element;
			
					return smell.getName();
				}
			}
		);

		// Second column is for the mistake
		col = createTableViewerColumn(titles[1], bounds[1], 1);
		
		col.setLabelProvider(new ColumnLabelProvider() 
			{
				public String getText(Object element) 
				{
					CodeSmell smell = (CodeSmell) element;
					
					return smell.getMistake();
				}
			}
		);

		// Third column is for the concern
		col = createTableViewerColumn(titles[2], bounds[2], 2);
				
		col.setLabelProvider(new ColumnLabelProvider() 
			{
				public String getText(Object element) 
				{
					CodeSmell smell = (CodeSmell) element;
							
					return smell.getConcern();
				}
			}
		);
				
		// Fourth column is for the error-proneness
		col = createTableViewerColumn(titles[3], bounds[3], 3);
				
		col.setLabelProvider(new ColumnLabelProvider() 
			{
				public String getText(Object element) 
				{
					CodeSmell smell = (CodeSmell) element;
							
					return smell.getErrorProneness();
				}
			}
		);
				
		// Fifth column is for the source
		col = createTableViewerColumn(titles[4], bounds[4], 4);
				
		col.setLabelProvider(new ColumnLabelProvider() 
			{
				public String getText(Object element) 
				{
					CodeSmell smell = (CodeSmell) element;
					
					return smell.getSource();
				}
			}
		);
				
		// Sixth column is for the where
		col = createTableViewerColumn(titles[5], bounds[5], 5);
				
		col.setLabelProvider(new ColumnLabelProvider() 
			{
				public String getText(Object element) 
				{
					CodeSmell smell = (CodeSmell) element;
							
					return smell.getWhere();
				}
			}
		);
	}

	/**
	 * Creates one column of the table
	 */
	private TableViewerColumn createTableViewerColumn(String title, int bound, final int column_number) 
	{
		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer,SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		
		//Sets the column layout
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		column.addSelectionListener(getSelectionAdapter(column, column_number));
		
		return viewerColumn;
	}
	
	//Provides the widget to support the table sorter
	private SelectionAdapter getSelectionAdapter(final TableColumn column,final int index) 
	{
		SelectionAdapter selectionAdapter = new SelectionAdapter() 
		{
			public void widgetSelected(SelectionEvent event) 
			{
				comparator.setColumn(index);
		
				int direction = comparator.getDirection();
				
				viewer.getTable().setSortDirection(direction);
				viewer.getTable().setSortColumn(column);
				viewer.refresh();
			}
		};
		return selectionAdapter;
	}


	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() 
	{
		viewer.getControl().setFocus();
	}
	
	public TableViewer getViewer()
	{
		return viewer;
	}
}