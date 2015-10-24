package view;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import control.H_MenuPerspective;

public class MenuPerspective extends JMenuBar{

	private static final long serialVersionUID = 1L;
	
	private JMenu fileMenu;
	private JMenu editMenu;
	
	private JMenuItem newMenuItem;
	private JMenuItem openMenuItem;
	private JMenuItem saveMenuItem;
	private JMenuItem exitMenuItem;
	
	private JMenu heuristicMenu;
	
	private JCheckBoxMenuItem optimalPlanningMenuItem;
	private JCheckBoxMenuItem subOptimalPlanningMenuItem;

	protected H_MenuPerspective _handler;
	
	public MenuPerspective(){
		super();
		initComponent();
		initHandler();		
	}

	private void initComponent() {
		
		fileMenu = new JMenu("File");
	    editMenu = new JMenu("Edit");
	    
	    newMenuItem = new JMenuItem("New ");	  
	    openMenuItem = new JMenuItem("Open ");
	    saveMenuItem = new JMenuItem("Save ");
	    
	    newMenuItem.setEnabled(true);
	    openMenuItem.setEnabled(false);
	    saveMenuItem.setEnabled(false);
	    	    
	    exitMenuItem = new JMenuItem("Exit ");
	       
	    fileMenu.add(newMenuItem);
	    fileMenu.add(openMenuItem);
	    fileMenu.add(saveMenuItem);
	    fileMenu.addSeparator();
	    fileMenu.add(exitMenuItem);
	    
	    heuristicMenu = new JMenu("Search Heuristic ");
	    
	    optimalPlanningMenuItem = new JCheckBoxMenuItem("Blind A* ");
	    subOptimalPlanningMenuItem = new JCheckBoxMenuItem("Lazy Greedy ");
	    
	    editMenu.add(heuristicMenu);
	    
	    heuristicMenu.add(subOptimalPlanningMenuItem);
	    heuristicMenu.add(optimalPlanningMenuItem);
	    
	    this.add(fileMenu);
	    this.add(editMenu);
		
	}
	
	private void initHandler() {
		_handler = new H_MenuPerspective(this);
	}

	public JMenuItem getNewMenuItem() {
		return newMenuItem;
	}

	public void setNewMenuItem(JMenuItem newMenuItem) {
		this.newMenuItem = newMenuItem;
	}

	public JMenuItem getOpenMenuItem() {
		return openMenuItem;
	}

	public void setOpenMenuItem(JMenuItem openMenuItem) {
		this.openMenuItem = openMenuItem;
	}

	public JMenuItem getSaveMenuItem() {
		return saveMenuItem;
	}

	public void setSaveMenuItem(JMenuItem saveMenuItem) {
		this.saveMenuItem = saveMenuItem;
	}
	
	public JMenuItem getExitMenuItem() {
		return exitMenuItem;
	}

	public void setExitMenuItem(JMenuItem saveMenuItem) {
		this.exitMenuItem = saveMenuItem;
	}

	public JCheckBoxMenuItem getOptimalPlanningMenuItem() {
		return optimalPlanningMenuItem;
	}

	public void setOptimalPlanningMenuItem(JCheckBoxMenuItem optimalPlanningMenuItem) {
		this.optimalPlanningMenuItem = optimalPlanningMenuItem;
	}

	public JCheckBoxMenuItem getSubOptimalPlanningMenuItem() {
		return subOptimalPlanningMenuItem;
	}

	public void setSubOptimalPlanningMenuItem(
			JCheckBoxMenuItem subOptimalPlanningMenuItem) {
		this.subOptimalPlanningMenuItem = subOptimalPlanningMenuItem;
	}	

}
