package view;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Enumeration;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import control.H_ResultsPerspective;

import main.Constants;
import main.Trace;

public class ResultsPerspective extends JDialog
{

	private static final long serialVersionUID = 1L;
	
	private JTextArea resultsArea;
	private JScrollPane resultsScrollPane;	
	private JLabel resultsLabel;

	private JButton okButton;
	
	protected H_ResultsPerspective _handler;
	
	public ResultsPerspective()
	{
		super();
		initComponent();
		initHandler();
		this.setModal(true);
		this.setVisible(true);
	}

	public void initComponent()
	{		
		Container content = this.getContentPane();
	    //content.setBackground(Color.white);
	    
	    content.setLayout(new FlowLayout()); 	
	   	
		resultsArea = new JTextArea();
		resultsArea.setEditable(false);
		resultsScrollPane = new JScrollPane(resultsArea);
		resultsScrollPane.setPreferredSize(new Dimension(490,600));
		resultsScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		resultsScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);   
		
		resultsLabel = new JLabel("Alignment of the traces in the log: ");
		resultsLabel.setPreferredSize(new Dimension(490,25));
				  
		okButton = new JButton("OK");
		
		this.add(resultsLabel);	
	    this.add(resultsScrollPane);	
	   
	    this.add(okButton); 
		
	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	    this.setLocation ( ( screenSize.width / 2 ) - ( this.getWidth ( ) / 2 ), (
	    screenSize.height / 2 ) - ( this.getHeight ( ) / 2 ) );

	    this.invokePlanner(this);
	    
		this.setTitle("Traces Alignment");
	    this.setSize(500, 700);
	    this.setResizable(true);
	   	   
	}
	
	private void initHandler() {
		
		_handler = new H_ResultsPerspective(this);
		
	}
	
	public void invokePlanner(ResultsPerspective resP)  {
		
		
		final ResultsPerspective rp = resP;
		
    	Thread thread = new Thread(new Runnable() {
                		
             public void run() {
            	 try
                 {        
            		 
            	for(int k=0;k<Constants.get_all_traces_vector().size();k++) {
            				
                 		Trace trace = Constants.get_all_traces_vector().elementAt(k);
                 		
     	            	Constants.setPDDLRulesVector(new Vector<String>());     

     	            	StringBuffer sb_domain = createDomain();
     	            	StringBuffer sb_problem = createProblem(trace);
     	            	
     	            	scriviFile("fast-downward/src/Conformance_Checking/domain.pddl", sb_domain);
     	            	scriviFile("fast-downward/src/Conformance_Checking/problem.pddl", sb_problem);	 
     	            	
     	         rp.getResultsArea().append("*******************************\n");   	
            	 rp.getResultsArea().append("ALIGNMENT FOR " + trace.getTraceName() + "\n");
     	         rp.getResultsArea().append("*******************************\n"); 
            	 
            	 Thread.sleep(1000);
            	 
            	 Timestamp tmsp1 = new Timestamp(new java.util.Date().getTime());
            		 
            	 Process pr = Runtime.getRuntime().exec("gnome-terminal -e ./translate_script");                 		
         	     pr.waitFor();
         	     
         	     Timestamp tmsp2_a = new Timestamp(new java.util.Date().getTime());
         	     	      
        	     rp.getResultsArea().append("---- START THE ALIGNMENT PROCESS ----\n");
        	     
        	     long translation_time = tmsp2_a.getTime()-tmsp1.getTime();
        	     
        	     rp.getResultsArea().append(">>>> TRANSLATION TIME : ");
        	     rp.getResultsArea().append(translation_time + " ms.\n");
         	     
         	     Thread.sleep(1000);
         	     
         	     Timestamp tmsp2_b = new Timestamp(new java.util.Date().getTime());
         	    
         	     Process pr2 = Runtime.getRuntime().exec("gnome-terminal -e ./preprocess_script");                 		
        	     pr2.waitFor();
        	     
            	 Timestamp tmsp3 = new Timestamp(new java.util.Date().getTime());
  	     
        	     long preprocessing_time = tmsp3.getTime()-tmsp2_b.getTime();
        	     
        	     rp.getResultsArea().append(">>>> PREPROCESSING TIME : ");
        	     rp.getResultsArea().append(preprocessing_time + " ms.\n\n");

        	     if(Constants.getMenuPerspective().getSubOptimalPlanningMenuItem().isSelected())  {
        	     
        	    	 Thread.sleep(1000);
        	    	 
        	    	 rp.getResultsArea().append("---- SEARCH HEURISTIC: Lazy Greedy ----\n");                	    	 
        	    	 
        	    	 Timestamp tmsp4 = new Timestamp(new java.util.Date().getTime());
            	     Process pr3 = Runtime.getRuntime().exec("gnome-terminal -e ./planner_subopt_script");                  		
             	     pr3.waitFor();
             	     Timestamp tmsp5 = new Timestamp(new java.util.Date().getTime());
             	     
             	     long searching_time = tmsp5.getTime()-tmsp4.getTime();
             	     
            	     rp.getResultsArea().append(">>>> SEARCHING TIME : ");
            	     rp.getResultsArea().append(searching_time + " ms.\n");
             	     
            	     Thread.sleep(1000);
            	     
             	     rp.createResults(trace);
                 }
        	     
        	     if(Constants.getMenuPerspective().getOptimalPlanningMenuItem().isSelected())  {
            	     
        	    	 Thread.sleep(1000);
        	    	 
        	    	 rp.getResultsArea().append("\n---- SEARCH HEURISTIC: Blind A* ----\n");                	    	 
        	    	 
        	    	 Timestamp tmsp6 = new Timestamp(new java.util.Date().getTime());
            	     Process pr3 = Runtime.getRuntime().exec("gnome-terminal -e ./planner_opt_script");                  		
             	     pr3.waitFor();
             	     Timestamp tmsp7 = new Timestamp(new java.util.Date().getTime());
             	     
             	     long searching_time = tmsp7.getTime()-tmsp6.getTime();
             	     
            	     rp.getResultsArea().append(">>>> SEARCHING TIME : ");
            	     rp.getResultsArea().append(searching_time + " ms.\n");
             	     
            	     Thread.sleep(1000);
            	     
             	     rp.createResults(trace);
                 }
        	                     	     

            		 }
    	     
                 }
        	 
             catch(Exception e)
     	    {e.printStackTrace();
     	    }
        	 
         }
     });
	 thread.start();
}

	
	public void createResults(Trace trace) {
		
		//
		//Create the results to be shown by the planner
		//
		new File("fast-downward/src/sas_plan");
    	    
   	    Vector<String> plan_vector = new Vector<String>();
   	     
   	       try
   	    	 {
			 BufferedReader reader = new BufferedReader(new FileReader("fast-downward/src/sas_plan"));
			 String line = reader.readLine();
			 while(line!=null) {
				 if(line!=null && line.contains("(") && line.contains(")")){
			    	 plan_vector.addElement(line);
			     }
			     line = reader.readLine();
			 }
   	    	}
           catch(Exception e)
   	    {e.printStackTrace();
   	    }
		/////////////////////////////////////////

		resultsArea.append(">>>> ORIGINAL TRACE: " + trace.get_Original_Trace_content_vector()+"\n");
		resultsArea.append(">>>> DECLARE RULES: " + Constants.getRulesVector()+"\n");
		
		resultsArea.append(">>>> STARTING TRACE in PDDL: " + trace.get_PDDL_Trace_content_vector()+"\n");
				
		Vector<String> intermediate_trace_vector = new Vector<String>(trace.get_PDDL_Trace_content_vector()); 
		
		for (int index=0;index<plan_vector.size();index++) {
			
			int indice = index+1;
			
			if(index==0)
			resultsArea.append("###############################\n");
			
			String planning_action = (String) plan_vector.elementAt(index);
			
			String[] split = planning_action.split("\\(");
			String[] split1 = split[1].split("\\)");
			
			String[] complete_action = split1[0].split(" ");
			
			String action = complete_action[0];
			
			if(action.equalsIgnoreCase("addToTheEmptyTrace")) {

				resultsArea.append(">>>> PLANNING ACTION # " + indice + ": " + split1[0] + "\n");
												
				String task = complete_action[1];
				intermediate_trace_vector.insertElementAt(task, 0);
				
				resultsArea.append("++++ EFFECT ON THE TRACE : add '" + task + "' to the trace\n");
				
				resultsArea.append("^^^^ INT.TRACE: " + intermediate_trace_vector+"\n");
				
			}
			else if(action.equalsIgnoreCase("addAtTheBeginning")) {
				
				resultsArea.append(">>>> PLANNING ACTION # " + indice + ": " +  split1[0] + "\n");
								
				String task = complete_action[2];
				intermediate_trace_vector.insertElementAt(task, 0);
				
				resultsArea.append("++++ EFFECT ON THE TRACE : add task '" + task + "' at the beginning of the trace\n");
							
				resultsArea.append("^^^^ INT.TRACE: " + intermediate_trace_vector+"\n");
				
				
			}
			else if(action.equalsIgnoreCase("addAtTheEnd")) {
				
				resultsArea.append(">>>> PLANNING ACTION # " + indice + ": " + split1[0] + "\n");
				
				String task = complete_action[2];
				intermediate_trace_vector.insertElementAt(task,intermediate_trace_vector.size());
				
				resultsArea.append("++++ EFFECT ON THE TRACE : add task '" + task + "' at the end of the trace\n");
				
				resultsArea.append("^^^^ INT.TRACE: " + intermediate_trace_vector+"\n");
				
			}
			else if(action.equalsIgnoreCase("addBetween")) {
				
				resultsArea.append(">>>> PLANNING ACTION # " + indice + ": " + split1[0] + "\n");				
				
				String task_prev = complete_action[1];
				int index_task_prev = intermediate_trace_vector.indexOf(task_prev);
				String task = complete_action[2];
				intermediate_trace_vector.insertElementAt(task,index_task_prev+1);
				
				resultsArea.append("++++ EFFECT ON THE TRACE : add '" + split1[0] + "' after task '" + task_prev + "' \n");
						
				resultsArea.append("^^^^ INT.TRACE: " + intermediate_trace_vector+"\n");
			}
			
			else if(action.equalsIgnoreCase("deleteSingleTask")) {
				resultsArea.append(">>>> PLANNING ACTION # " + indice + ": " + split1[0] + "\n");
								
				String task = complete_action[1];
				intermediate_trace_vector.removeElement(task);
				
				resultsArea.append("++++ EFFECT ON THE TRACE : remove task '" + task + "' from the trace\n");
				
				resultsArea.append("^^^^ INT.TRACE: " + intermediate_trace_vector+"\n");
				
				
			}
			else if(action.equalsIgnoreCase("deleteAtTheBeginning")) {
				resultsArea.append(">>>> PLANNING ACTION # " + indice + ": " + split1[0] + "\n");
				
				String task = complete_action[1];
				intermediate_trace_vector.removeElement(task);
				
				resultsArea.append("++++ EFFECT ON THE TRACE : remove task '" + task + "' from the trace\n");
				
				resultsArea.append("^^^^ INT.TRACE: " + intermediate_trace_vector+"\n");
								
			}
			else if(action.equalsIgnoreCase("deleteAtTheEnd")) {
				resultsArea.append(">>>> PLANNING ACTION # " + indice + ": " + split1[0] + "\n");
				
				String task = complete_action[2];
				intermediate_trace_vector.removeElement(task);
				
				resultsArea.append("++++ EFFECT ON THE TRACE : remove task '" + task + "' from the trace\n");
				
				resultsArea.append("^^^^ INT.TRACE: " + intermediate_trace_vector+"\n");
				
			}
			else if(action.equalsIgnoreCase("deleteBetween")) {
				resultsArea.append(">>>> PLANNING ACTION # " + indice + ": " + split1[0] + "\n");

				String task = complete_action[2];
				intermediate_trace_vector.removeElement(task);
				
				resultsArea.append("++++ EFFECT ON THE TRACE : remove task '" + task + "' from the trace\n");
				
				resultsArea.append("^^^^ INT.TRACE: " + intermediate_trace_vector+"\n");
			}
		}
		
		Vector<String> aligned_trace_vector = new Vector<String>();
		for(int index = 0;index<intermediate_trace_vector.size();index++) {
			
			String el = (String) intermediate_trace_vector.elementAt(index);
			aligned_trace_vector.addElement(el.substring(0,el.length()-1));
			
		}
		
		resultsArea.append("###############################\n");
		resultsArea.append("<<<< ALIGNED PDDL TRACE : " + intermediate_trace_vector +"\n");
		resultsArea.append("<<<< ALIGNED TRACE : " + aligned_trace_vector +"\n\n");
	}

	public JTextArea getResultsArea() {
		return resultsArea;
	}

	public JButton getOkButton() {
		return okButton;
	}
	
	private StringBuffer createDomain() {
		
		StringBuffer PDDL_domain_buffer = new StringBuffer();
		StringBuffer PDDL_rules_buffer = new StringBuffer();
		
		PDDL_domain_buffer.append("(define (domain Mining)\n");
		PDDL_domain_buffer.append("(:requirements :derived-predicates :typing :equality)\n");
		PDDL_domain_buffer.append("(:types ");
		
		for(int i=0;i<Constants.getTasksRepositoryVector().size();i++) {
			PDDL_domain_buffer.append(Constants.getTasksRepositoryVector().elementAt(i) + " ");
		}
		
		PDDL_domain_buffer.append("- task)\n\n");		
		
		PDDL_domain_buffer.append(";;\n");
		PDDL_domain_buffer.append(";;Basic Predicates\n");	
		PDDL_domain_buffer.append(";;\n");

		PDDL_domain_buffer.append("(:predicates\n");
		PDDL_domain_buffer.append("(pre ?x - task ?y - task)\n");				 
		PDDL_domain_buffer.append("(traced ?x - task)\n");					
		PDDL_domain_buffer.append("(first_task_of_the_trace ?x - task)\n");					
		PDDL_domain_buffer.append("(last_task_of_the_trace ?x - task)\n\n");	
		
		PDDL_domain_buffer.append(";;\n");
		PDDL_domain_buffer.append(";;DECLARE LTL rules\n");	
		PDDL_domain_buffer.append(";;\n");

		for(int j=0;j<Constants.getRulesVector().size();j++) {
			
			String DECLARE_rule = (String) Constants.getRulesVector().elementAt(j);
			
			String single_rule_array[] = DECLARE_rule.split("\\(");
			
			String declare_rule_name = single_rule_array[0];  
			
			//
			//DECLARE RULE: EXISTENCE(A) --- (FINALLY A) - An instance of the task A must occur at least once in the trace.
			//
			if(declare_rule_name.equalsIgnoreCase("existence")) {				
				String[] task = single_rule_array[1].split("\\)");
				PDDL_domain_buffer.append("(existence-" + task[0] + ")\n");
				
				Constants.getPDDLRulesVector().addElement("existence-" + task[0]);
				
				PDDL_rules_buffer.append(";;\n");
				PDDL_rules_buffer.append(";;EXISTENCE-" + task[0] + " - (FINALLY " + task[0] + ") - An instance of the task " + task[0] + " must occur at least once in the trace.\n");				
				PDDL_rules_buffer.append(";;\n\n");

				PDDL_rules_buffer.append("(:derived (existence-" + task[0] + ")\n");
				PDDL_rules_buffer.append("(exists (?t - " + task[0] + ") (traced ?t)))\n\n");				
				
			}
			//
			//DECLARE RULE: ABSENCE(A) --- (NOT FINALLY A) - No instance of the task A can occur in the trace (A NEVER occurs in the trace).
			//
			else if(declare_rule_name.equalsIgnoreCase("absence")) {
				String[] task = single_rule_array[1].split("\\)");
				PDDL_domain_buffer.append("(absence-" + task[0] + ")\n");
				
				Constants.getPDDLRulesVector().addElement("absence-" + task[0]);
				
				PDDL_rules_buffer.append(";;\n");
				PDDL_rules_buffer.append(";;ABSENCE-" + task[0] + " - (NOT (FINALLY " + task[0] + ")) - " + task[0] + " never occur in the trace.\n");				
				PDDL_rules_buffer.append(";;\n\n");

				PDDL_rules_buffer.append("(:derived (absence-" + task[0] + ")\n");
				PDDL_rules_buffer.append("(not (exists (?t - " + task[0] + ") (traced ?t))))\n\n");
			}
			//
			//DECLARE RULE: CHOICE(A,B) --- (FINALLY A OR FINALLY B) - A or B occur eventually in the trace. The formula is TRUE also if they both occur in the trace, while it is FALSE if no one of them occurs in the trace.
			//
			else if(declare_rule_name.equalsIgnoreCase("choice")) {
				String[] tasks = single_rule_array[1].split("\\)");
				String[] task = tasks[0].split(",");
				PDDL_domain_buffer.append("(choice-" + task[0] + "-" + task[1] + ")\n");
				
				Constants.getPDDLRulesVector().addElement("choice-" + task[0] + "-" + task[1]);
				
				PDDL_rules_buffer.append(";;\n");
				PDDL_rules_buffer.append(";;CHOICE-" + task[0] + "-" + task[1] + " - (FINALLY " + task[0] + " OR FINALLY " + task[1] + ") - " + task[0] + " or " + task[1] + " occur eventually in the trace. The formula is TRUE also if they both occur in the trace.\n");				
				PDDL_rules_buffer.append(";;\n\n");
				
				PDDL_rules_buffer.append("(:derived (choice-" + task[0] + "-" + task[1] + ")\n");
				PDDL_rules_buffer.append("(or (exists (?ta - " + task[0] + ") (traced ?ta))\n");
				PDDL_rules_buffer.append("(exists (?tb - " + task[1] + ") (traced ?tb))))\n\n");
			}
			//
			//DECLARE RULE: EXCLUSIVE_CHOICE(A,B) --- (FINALLY A OR FINALLY B) AND (NOT (FINALLY A AND FINALLY B)) - Only instances of A or only instances of B can occur eventually in the trace (but not together). The formula is FALSE also if no instance of A and if no instance of B occur in the trace.
			//			
			else if(declare_rule_name.equalsIgnoreCase("ex.choice")) {
				String[] tasks = single_rule_array[1].split("\\)");
				String[] task = tasks[0].split(",");
				PDDL_domain_buffer.append("(ex_choice-" + task[0] + "-" + task[1] + ")\n");				
				
				Constants.getPDDLRulesVector().addElement("ex_choice-" + task[0] + "-" + task[1]);
				
				PDDL_rules_buffer.append(";;\n");
				PDDL_rules_buffer.append(";;EXCLUSIVE_CHOICE-" + task[0] + "-" + task[1] + " - (FINALLY " + task[0] + " OR FINALLY " + task[1] + ") AND (NOT (FINALLY " + task[0] + " AND FINALLY " + task[1] + ")) - Only instances of " + task[0] + " or only instances of " + task[1] + " can occur eventually in the trace (but not together). The formula is FALSE also if no instance of A and if no instance of B occur in the trace.\n");				
				PDDL_rules_buffer.append(";;\n\n");
				
				PDDL_rules_buffer.append("(:derived (ex_choice-" + task[0] + "-" + task[1] + ")\n");
				PDDL_rules_buffer.append("(and \n");
				PDDL_rules_buffer.append("(exists (?ta - " + task[0] + " ?tb - " + task[1] + ") (or (traced ?ta) (traced ?tb)))\n");
				PDDL_rules_buffer.append("(not (exists (?ta - " + task[0] + " ?tb - " + task[1] + ") (and (traced ?ta) (traced ?tb))))))\n\n");				
			}
			//
			//DECLARE RULE: RESPONDED_EXISTENCE(A,B) --- (FINALLY A --> FINALLY B) - If an instance of A occurs in the trace, then an instance of B must occur in the trace as well (before or after A, the order here is not important).
			//		
			else if(declare_rule_name.equalsIgnoreCase("resp.existence")) {
				String[] tasks = single_rule_array[1].split("\\)");
				String[] task = tasks[0].split(",");
				PDDL_domain_buffer.append("(resp_existence-" + task[0] + "-" + task[1] + ")\n");				
				
				Constants.getPDDLRulesVector().addElement("resp_existence-" + task[0] + "-" + task[1]);
				
				PDDL_rules_buffer.append(";;\n");
				PDDL_rules_buffer.append(";;RESPONDED_EXISTENCE-" + task[0] + "-" + task[1] + " - (FINALLY " + task[0] + " --> FINALLY " + task[1] + ") - If an instance of " + task[0] + " occurs in the trace, then an instance of " + task[1] + " must occur in the trace as well (before or after " + task[0] + ", the order here is not important). The formula is TRUE also if " + task[0] + " never appears in the trace.\n");				
				PDDL_rules_buffer.append(";;\n\n");
				
				PDDL_rules_buffer.append("(:derived (resp_existence-" + task[0] + "-" + task[1] + ")\n");
				
				PDDL_rules_buffer.append("(forall (?ta - " + task[0] + ")\n");
				PDDL_rules_buffer.append("(or (not (traced ?ta))\n");
				PDDL_rules_buffer.append("(and (traced ?ta)\n");
				PDDL_rules_buffer.append("(exists (?tb - " + task[1] + ") (traced ?tb))))))\n\n");
	
			}

		}
		
		PDDL_domain_buffer.append(")\n\n");			
		
		//INSERT PDDL ACTIONS REPRESENTING TASKS 
		
		PDDL_domain_buffer.append(";;\n");
		PDDL_domain_buffer.append(";;PLANNING ACTIONS\n");		
		PDDL_domain_buffer.append(";;\n\n");

		PDDL_domain_buffer.append(";;\n");
		PDDL_domain_buffer.append(";;If the trace is empty, the action --addToTheEmptyTrace-- adds a task 'x1' (which is actually not in the trace) to the trace.\n");		
		PDDL_domain_buffer.append(";;After the action happening, 'x1' will be contemporaneously the first and the last task of the trace.\n");		
		PDDL_domain_buffer.append(";;\n\n");

		PDDL_domain_buffer.append("(:action addToTheEmptyTrace\n");		
		PDDL_domain_buffer.append(":parameters (?x1 - task)\n");		
		PDDL_domain_buffer.append(":precondition (not (exists (?x - task) (traced ?x)))\n");		 
		PDDL_domain_buffer.append(":effect (and (traced ?x1) (first_task_of_the_trace ?x1) (last_task_of_the_trace ?x1))\n");			
		PDDL_domain_buffer.append(")\n\n");		
		
		PDDL_domain_buffer.append(";;\n");
		PDDL_domain_buffer.append(";;If 'x1' is the first task of the trace, the action --addAtTheBeginning-- adds a task 'x2' (which is actually not in the trace) before 'x1'.\n");
		PDDL_domain_buffer.append(";;\n\n");

		PDDL_domain_buffer.append("(:action addAtTheBeginning\n");
		PDDL_domain_buffer.append(":parameters (?x1 - task ?x2 - task)\n");		
		PDDL_domain_buffer.append(":precondition (and (traced ?x1) (not (traced ?x2)) (first_task_of_the_trace ?x1))\n");		 
		PDDL_domain_buffer.append(":effect (and (not (first_task_of_the_trace ?x1)) (first_task_of_the_trace ?x2) (traced ?x2) (pre ?x2 ?x1))\n");		
		PDDL_domain_buffer.append(")\n\n");

		PDDL_domain_buffer.append(";;\n");
		PDDL_domain_buffer.append(";;If 'x1' is the last task of the trace, the action --addAtTheEnd-- adds a task 'x2' (which is actually not in the trace) after 'x1'.\n");
		PDDL_domain_buffer.append(";;\n\n");

		PDDL_domain_buffer.append("(:action addAtTheEnd\n");
		PDDL_domain_buffer.append(":parameters (?x1 - task ?x2 - task)\n");		
		PDDL_domain_buffer.append(":precondition (and (traced ?x1) (not (traced ?x2)) (last_task_of_the_trace ?x1))\n");		 
		PDDL_domain_buffer.append(":effect (and (not (last_task_of_the_trace ?x1)) (last_task_of_the_trace ?x2) (traced ?x2) (pre ?x1 ?x2))\n");
		PDDL_domain_buffer.append(")\n\n");

		PDDL_domain_buffer.append(";;\n");
		PDDL_domain_buffer.append(";;The action --addBetween-- inserts the task 'x2' between the tasks 'x1' and 'x3' (that are already included in the trace).\n");
		PDDL_domain_buffer.append(";;After the action happening, two new couples of actions will be created: '(x1,x2)' and '(x2,x3)'.\n");
		PDDL_domain_buffer.append(";;\n\n");

		PDDL_domain_buffer.append("(:action addBetween\n");
		PDDL_domain_buffer.append(":parameters (?x1 - task ?x2 - task ?x3 - task)\n");		
		PDDL_domain_buffer.append(":precondition (and (pre ?x1 ?x3) (not (traced ?x2)))\n");			 
		PDDL_domain_buffer.append(":effect (and (traced ?x2) (pre ?x1 ?x2) (pre ?x2 ?x3) (not (pre ?x1 ?x3)))\n");
		PDDL_domain_buffer.append(")\n\n");

		PDDL_domain_buffer.append(";;\n");
		PDDL_domain_buffer.append(";;The action --deleteSingleTask-- deletes the task 'x1', if 'x1' it is the only task in the trace.\n");		
		PDDL_domain_buffer.append(";;\n\n");

		PDDL_domain_buffer.append("(:action deleteSingleTask\n");
		PDDL_domain_buffer.append(":parameters (?x1 - task)\n");		
		PDDL_domain_buffer.append(":precondition (and (traced ?x1) (first_task_of_the_trace ?x1) (last_task_of_the_trace ?x1))\n");			 
		PDDL_domain_buffer.append(":effect (and (not (traced ?x1)) (not (last_task_of_the_trace ?x1)) (not (first_task_of_the_trace ?x1)))\n");			
		PDDL_domain_buffer.append(")\n\n");

		PDDL_domain_buffer.append(";;\n");
		PDDL_domain_buffer.append(";;If 'x1' is the first task of the trace, and the task 'x2' follows 'x1', the action --deleteAtTheBeginning-- deletes 'x1' and makes 'x2' the new first task of the trace.\n");		
		PDDL_domain_buffer.append(";;\n\n");

		PDDL_domain_buffer.append("(:action deleteAtTheBeginning\n");
		PDDL_domain_buffer.append(":parameters (?x1 - task ?x2 - task)\n");		
		PDDL_domain_buffer.append(":precondition (and (traced ?x1) (traced ?x2) (pre ?x1 ?x2) (first_task_of_the_trace ?x1))\n");		 
		PDDL_domain_buffer.append(":effect (and (not (traced ?x1)) (not (first_task_of_the_trace ?x1)) (first_task_of_the_trace ?x2) (not (pre ?x1 ?x2)))\n");		
		PDDL_domain_buffer.append(")\n\n");

		PDDL_domain_buffer.append(";;\n");
		PDDL_domain_buffer.append(";;If 'x2' is the last task of the trace, and the task 'x2' follows 'x1', the action --deleteAtTheEnd-- deletes 'x2' and makes 'x1' the new last task of the trace.\n");		
		PDDL_domain_buffer.append(";;\n\n");

		PDDL_domain_buffer.append("(:action deleteAtTheEnd\n");
		PDDL_domain_buffer.append(":parameters (?x1 - task ?x2 - task)\n");		
		PDDL_domain_buffer.append(":precondition (and (traced ?x1) (traced ?x2) (pre ?x1 ?x2) (last_task_of_the_trace ?x2))\n");		 
		PDDL_domain_buffer.append(":effect (and (not (traced ?x2)) (not (last_task_of_the_trace ?x2)) (last_task_of_the_trace ?x1) (not (pre ?x1 ?x2)))\n");			
		PDDL_domain_buffer.append(")\n\n");

		PDDL_domain_buffer.append(";;\n");
		PDDL_domain_buffer.append(";;The action --deleteBetween-- deletes the task x2 that was previously situated between the tasks x1 and x3.\n");		
		PDDL_domain_buffer.append(";;\n\n");

		PDDL_domain_buffer.append("(:action deleteBetween\n");
		PDDL_domain_buffer.append(":parameters (?x1 - task ?x2 - task ?x3 - task)\n");		
		PDDL_domain_buffer.append(":precondition (and (traced ?x1) (traced ?x2) (traced ?x3) (pre ?x1 ?x2) (pre ?x2 ?x3))\n");			
		PDDL_domain_buffer.append(":effect (and (not (traced ?x2)) (not (pre ?x1 ?x2)) (not (pre ?x2 ?x3)) (pre ?x1 ?x3))\n");		
		PDDL_domain_buffer.append(")\n\n");		
		
		////////////////////////////////////////
		
		PDDL_domain_buffer.append(PDDL_rules_buffer);
		PDDL_domain_buffer.append(")");
		
		return PDDL_domain_buffer;
	}

	private StringBuffer createProblem(Trace trace) {

		StringBuffer PDDL_problem_buffer = new StringBuffer();
		
		PDDL_problem_buffer.append("(define (problem Align) (:domain Mining)\n");
		PDDL_problem_buffer.append("(:objects\n");		
		
		for(int k=0;k<Constants.getTasksRepositoryVector().size();k++) {
					
			Enumeration<String> enum_trace_hashtable = trace.get_Trace_Hashtable().keys();
			
			while(enum_trace_hashtable.hasMoreElements()) {
				String key = (String) enum_trace_hashtable.nextElement();
				String value = (String) trace.get_Trace_Hashtable().get(key);
								
				if(value.equalsIgnoreCase((String) Constants.getTasksRepositoryVector().elementAt(k)))
					PDDL_problem_buffer.append(key + " ");
			}
			
			PDDL_problem_buffer.append("- " + Constants.getTasksRepositoryVector().elementAt(k) + "\n");
		}
		
		PDDL_problem_buffer.append(")\n");		
		PDDL_problem_buffer.append("(:init\n");
		
		if(trace.get_PDDL_Trace_content_vector().size()==1) { //Case of a single task instance in the trace
			String current_task =  (String) trace.get_PDDL_Trace_content_vector().elementAt(0);
			PDDL_problem_buffer.append("(first_task_of_the_trace " + current_task + ")\n");
			PDDL_problem_buffer.append("(last_task_of_the_trace " + current_task + ")\n");
			
		}
		else  {
		
			for(int inx=0;inx<trace.get_PDDL_Trace_content_vector().size()-1;inx++) { //Case of multiple task instances in the trace
				
				String current_task =  (String) trace.get_PDDL_Trace_content_vector().elementAt(inx);
				String next_task = (String) trace.get_PDDL_Trace_content_vector().elementAt(inx+1);
				
				if(inx==0) PDDL_problem_buffer.append("(first_task_of_the_trace " + current_task + ")\n");
				
				PDDL_problem_buffer.append("(pre " + current_task + " " + next_task + ")\n");
				
				if(inx==trace.get_PDDL_Trace_content_vector().size()-2) PDDL_problem_buffer.append("(last_task_of_the_trace " + next_task + ")\n");
				
			}
		}
		
		for(int inx=0;inx<trace.get_PDDL_Trace_content_vector().size();inx++) {
			PDDL_problem_buffer.append("(traced " + trace.get_PDDL_Trace_content_vector().elementAt(inx) + ")\n");
		}
		
		PDDL_problem_buffer.append(")\n");		
		
		PDDL_problem_buffer.append("(:goal\n");		
		
		
		if(Constants.getPDDLRulesVector().size()>1)  {
			PDDL_problem_buffer.append("(and \n");			
				for(int index=0;index<Constants.getPDDLRulesVector().size();index++) {
					PDDL_problem_buffer.append("(" + Constants.getPDDLRulesVector().elementAt(index) + ")\n");
			    }
			PDDL_problem_buffer.append(")\n");		
		}
		else {
			PDDL_problem_buffer.append("(" + Constants.getPDDLRulesVector().elementAt(0) + ")\n");
		}
		PDDL_problem_buffer.append(")\n");		
		
		PDDL_problem_buffer.append(")");	
		
		return PDDL_problem_buffer;
	}
	
	private void scriviFile(String nomeFile, StringBuffer buffer) {
		 
		File file = null;
	    FileWriter fw = null;
		   
		   try {
			file = new File(nomeFile);
			file.setExecutable(true);
			
			fw = new FileWriter(file);
			fw.write(buffer.toString());
			fw.close();
			
		   //fw.flush();
		   //fw.close();
		   }
		   catch(IOException e) {
		   e.printStackTrace();
		   }
	}
	
}