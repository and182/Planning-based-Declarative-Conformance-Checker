package view;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import control.H_PlannerPerspective;

public class PlannerPerspective extends JDialog
{

	private static final long serialVersionUID = 1L;
	
	private JTextArea tasksArea;
	private JScrollPane tasksScrollPane;	
	
	private JTextArea rulesArea;	
	private JScrollPane rulesScrollPane;
	
	private JTextArea traceArea = new JTextArea();
	private JScrollPane traceScrollPane;
	
	private JLabel tasksRepoLabel;
	private JLabel traceLabel;
	private JLabel rulesListLabel;
	private JLabel blankLabel;

	private JButton runPlannerButton;
	private JButton previousStepButton;
	
	protected H_PlannerPerspective _handler;
	
	public PlannerPerspective()
	{
		super();
		initComponent();
		initHandler();
	}

	public void initComponent()
	{		
		Container content = this.getContentPane();
	    //content.setBackground(Color.white);
	    
	    content.setLayout(new FlowLayout()); 	

		tasksRepoLabel = new JLabel("Tasks repository");
		tasksRepoLabel.setPreferredSize(new Dimension(290,25));
		traceLabel = new JLabel("Traces");
		traceLabel.setPreferredSize(new Dimension(290,25));
		rulesListLabel = new JLabel("List of DECLARE rules");
		rulesListLabel.setPreferredSize(new Dimension(290,25));
		
		tasksArea = new JTextArea();
		tasksArea.setEditable(false);
		tasksScrollPane = new JScrollPane(tasksArea);
		tasksScrollPane.setPreferredSize(new Dimension(290,80));
		tasksScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		tasksScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		traceArea = new JTextArea();
		traceArea.setEditable(false);
		traceScrollPane = new JScrollPane(traceArea);
		traceScrollPane.setPreferredSize(new Dimension(290,80));
		traceScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		traceScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		rulesArea = new JTextArea();
		rulesArea.setEditable(false);
		rulesScrollPane = new JScrollPane(rulesArea);
		rulesScrollPane.setPreferredSize(new Dimension(290,80));
		rulesScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		rulesScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    
		blankLabel = new JLabel();
		blankLabel.setPreferredSize(new Dimension(50,30));
		runPlannerButton = new JButton("Run the Planner");
		previousStepButton = new JButton("< Back");
		
		this.add(tasksRepoLabel);	
	    this.add(tasksScrollPane);	
	    this.add(traceLabel);	
	    this.add(traceScrollPane);
	    this.add(rulesListLabel);	
	    this.add(rulesScrollPane);    
	    this.add(previousStepButton); 
	    this.add(blankLabel); 
	    this.add(runPlannerButton); 
		
	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	    this.setLocation ( ( screenSize.width / 2 ) - ( this.getWidth ( ) / 2 ), (
	    screenSize.height / 2 ) - ( this.getHeight ( ) / 2 ) );


		this.setTitle("Launching the Planner. RECAP");
	    this.setSize(300, 415);
	    this.setResizable(false);
	   	   
	}
	
	private void initHandler() {
		
		_handler = new H_PlannerPerspective(this);
		
	}

	public JTextArea getTasksArea() {
		return tasksArea;
	}

	public JTextArea getRulesArea() {
		return rulesArea;
	}

	public JTextArea getTraceArea() {
		return traceArea;
	}

	public JButton getRunPlannerButton() {
		return runPlannerButton;
	}

	public JButton getPreviousStepButton() {
		return previousStepButton;
	}
	
}