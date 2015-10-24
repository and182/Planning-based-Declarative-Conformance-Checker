package view;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import javax.swing.JFrame;
import main.Constants;

public class Desktop extends JFrame
{

	private static final long serialVersionUID = 1L;
	
	private MenuPerspective menuBar;
	private AlphabetPerspective alphabetPanel;
	private TracePerspective tracePanel;
	private RulesPerspective rulePanel;
	
	public Desktop()
	{
		super();
		initComponent();
	}

	public void initComponent()
	{		
		Container content = this.getContentPane();
	    
	    content.setLayout(new FlowLayout()); 	
		
	    menuBar = new MenuPerspective();	    
	    alphabetPanel = new AlphabetPerspective();
	    tracePanel = new TracePerspective();
	    rulePanel = new RulesPerspective();
	    
	    Constants.setMenuPerspective(menuBar);
	    Constants.setAlphabetPerspective(alphabetPanel);
	    Constants.setTracePerspective(tracePanel);
	    Constants.setRulesPerspective(rulePanel);
	    
	    Constants.getTracePerspective().setComponentEnabled(false);
	    Constants.getRulesPerspective().setComponentEnabled(false);

	    this.setJMenuBar(menuBar);
	    this.add(alphabetPanel);	
	    this.add(tracePanel);	
	    this.add(rulePanel);	

	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	    this.setLocation ( ( screenSize.width / 3 ) - ( this.getWidth ( ) / 3 ), (
	    screenSize.height / 3 ) - ( this.getHeight ( ) / 3 ) );

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);		
		this.setTitle("Plan-based Conformance Checker for Declarative Processes");
		
		Constants.setDesktop(this);
		
	    this.setSize(550, 660);
	    this.setResizable(true);
	    this.setVisible(true);
	}
}