package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import main.Constants;
import main.Trace;
import view.PlannerPerspective;
import view.RulesPerspective;

public class H_RulesPerspective {
	
	public RulesPerspective _view = null;
	
	public H_RulesPerspective (RulesPerspective i_view){
		_view = i_view;
		installListeners();
	}


	private void installListeners() {
		
		_view.getRuleComboBox().addItemListener(new ItemListener()
		{

			 public void itemStateChanged(ItemEvent event)
			{
				
				 String rule = (String) _view.getRuleComboBox().getSelectedItem();
				 
				 if (event.getStateChange() == ItemEvent.SELECTED && rule.equalsIgnoreCase("---") )
				 {
					 _view.getFirstTaskComboBox().setSelectedIndex(0);
					 _view.getSecondTaskComboBox().setSelectedIndex(0);
					 _view.getFirstTaskComboBox().setEnabled(false);
					 _view.getSecondTaskComboBox().setEnabled(false);
				 }
				 else if (event.getStateChange() == ItemEvent.SELECTED && (rule.equalsIgnoreCase("existence") || rule.equalsIgnoreCase("absence"))) 
				 {
					 _view.getFirstTaskComboBox().setSelectedIndex(0);
					 _view.getSecondTaskComboBox().setSelectedIndex(0);
					 _view.getFirstTaskComboBox().setEnabled(true);
					 _view.getSecondTaskComboBox().setEnabled(false);
				 }
				 else 
				 {
					 _view.getFirstTaskComboBox().setSelectedIndex(0);
					 _view.getSecondTaskComboBox().setSelectedIndex(0);
					 _view.getFirstTaskComboBox().setEnabled(true);
					 _view.getSecondTaskComboBox().setEnabled(true);
				 }
				 
			}
		});
		
		_view.getRulesList().addListSelectionListener(new ListSelectionListener() {
		      public void valueChanged(ListSelectionEvent e) {
		    	  
		    	  if(e.getValueIsAdjusting()) { // -- Used to avoid that the ListSelectionListener is invoked twice.
		    	  
		    	  String task_selected = (String) _view.getRulesList().getSelectedValue();
		    	  String[] split = task_selected.split("\\(");
		    	 
		    	  String rule = split[0];
		    	  
		    	  String[] split1 = split[1].split("\\)");
		    	  
		    	  if(rule.equalsIgnoreCase("existence")) {
		    		  String LTLrule = Constants.LTL_eventually + split1[0];
		    		  _view.getLTLconstraintLabel().setText("Corresponding LTL constraint : " + LTLrule);
		    	  }
		    	  else if(rule.equalsIgnoreCase("absence")) {		    		  
		    		  String LTLrule = Constants.LTL_NOT + Constants.LTL_eventually + split1[0];
		    		  _view.getLTLconstraintLabel().setText("Corresponding LTL constraint : " + LTLrule);
		    	  }
		    	  else if(rule.equalsIgnoreCase("choice")) {	
		    		  String[] split2 = split1[0].split("\\,");
		    		  String LTLrule = Constants.LTL_eventually + split2[0] + " " + Constants.LTL_OR + " " + Constants.LTL_eventually + split2[1];
		    		  _view.getLTLconstraintLabel().setText("Corresponding LTL constraint : " + LTLrule);
		    	  }
		    	  else if(rule.equalsIgnoreCase("ex.choice")) {	
		    		  String[] split2 = split1[0].split("\\,");
		    		  String LTLrule = "(" + Constants.LTL_eventually + split2[0] + " " + Constants.LTL_OR + " " + Constants.LTL_eventually + split2[1] + ")" + " " + Constants.LTL_AND + " " + Constants.LTL_NOT + "(" + Constants.LTL_eventually + split2[0] + " " + Constants.LTL_AND + " " + Constants.LTL_eventually + split2[1] + ")";
		    		  _view.getLTLconstraintLabel().setText("Corresponding LTL constraint : " + LTLrule);
		    	  }
		    	  else if(rule.equalsIgnoreCase("resp.existence")) {	
		    		  String[] split2 = split1[0].split("\\,");
		    		  String LTLrule = Constants.LTL_eventually + split2[0] + " " + Constants.LTL_IMPLIES + " " + Constants.LTL_eventually + split2[1];
		    		  _view.getLTLconstraintLabel().setText("Corresponding LTL constraint : " + LTLrule);
		    	  }
		    	  else if(rule.equalsIgnoreCase("response")) {	
		    		  String[] split2 = split1[0].split("\\,");
		    		  String LTLrule = Constants.LTL_globally + "(" + split2[0] + " " + Constants.LTL_IMPLIES + " " + Constants.LTL_eventually + split2[1] + ")";
		    		  _view.getLTLconstraintLabel().setText("Corresponding LTL constraint : " + LTLrule);
		    	  }
		    	  else if(rule.equalsIgnoreCase("precedence")) {	
		    		  String[] split2 = split1[0].split("\\,");
		    		  String LTLrule = Constants.LTL_NOT + split2[1] + " " + Constants.LTL_weak_until + " " + split2[0];
		    		  _view.getLTLconstraintLabel().setText("Corresponding LTL constraint : " + LTLrule);
		    	  }		    	  
		    	  else if(rule.equalsIgnoreCase("alt.response")) {	
		    		  String[] split2 = split1[0].split("\\,");
		    		  String LTLrule = Constants.LTL_globally + "(" + split2[0] + " " + Constants.LTL_IMPLIES + " " + Constants.LTL_next + "(" + Constants.LTL_NOT + split2[0] + " " + Constants.LTL_until + " " + split2[1] + "))";
		    		  _view.getLTLconstraintLabel().setText("Corresponding LTL constraint : " + LTLrule);
		    	  }	
		    	  else if(rule.equalsIgnoreCase("alt.precedence")) {	
		    		  String[] split2 = split1[0].split("\\,");
		    		  String LTLrule = "(" + Constants.LTL_NOT + split2[1] + " " + Constants.LTL_weak_until + " " + split2[0] + ")" + " " + Constants.LTL_AND + " " + Constants.LTL_globally + "(" + split2[1] + " " + Constants.LTL_IMPLIES + " " + Constants.LTL_next + "(" + Constants.LTL_NOT + split2[1] + " " + Constants.LTL_weak_until + " " + split2[0] + "))";
		    		  _view.getLTLconstraintLabel().setText("Corresponding LTL constraint : " + LTLrule);
		    	  }	
		    	  else if(rule.equalsIgnoreCase("chain response")) {	
		    		  String[] split2 = split1[0].split("\\,");
		    		  String LTLrule = Constants.LTL_globally + "(" + split2[0] + " " + Constants.LTL_IMPLIES + " " + Constants.LTL_next + split2[1] + ")";
		    		  _view.getLTLconstraintLabel().setText("Corresponding LTL constraint : " + LTLrule);
		    	  }		
		    	  else if(rule.equalsIgnoreCase("chain precedence")) {	
		    		  String[] split2 = split1[0].split("\\,");
		    		  String LTLrule = Constants.LTL_globally + "(" + Constants.LTL_next + split2[1] + " " + Constants.LTL_IMPLIES + " " + split2[0] + ")";
		    		  _view.getLTLconstraintLabel().setText("Corresponding LTL constraint : " + LTLrule);
		    	  }		
		    	  else if(rule.equalsIgnoreCase("not resp.existence")) {	
		    		  String[] split2 = split1[0].split("\\,");
		    		  String LTLrule = Constants.LTL_eventually + split2[0] + " " + Constants.LTL_IMPLIES + " " + Constants.LTL_NOT + Constants.LTL_eventually + split2[1];
		    		  _view.getLTLconstraintLabel().setText("Corresponding LTL constraint : " + LTLrule);
		    	  }
		    	  else if(rule.equalsIgnoreCase("not response")) {	
		    		  String[] split2 = split1[0].split("\\,");
		    		  String LTLrule = Constants.LTL_globally + "(" + split2[0] + " " + Constants.LTL_IMPLIES + " " + Constants.LTL_NOT + Constants.LTL_eventually + split2[1] + ")";
		    		  _view.getLTLconstraintLabel().setText("Corresponding LTL constraint : " + LTLrule);
		    	  }		
		    	  else if(rule.equalsIgnoreCase("not precedence")) {	
		    		  String[] split2 = split1[0].split("\\,");
		    		  String LTLrule = Constants.LTL_globally + "(" + split2[0] + " " + Constants.LTL_IMPLIES + " " + Constants.LTL_NOT + Constants.LTL_eventually + split2[1] + ")";
		    		  _view.getLTLconstraintLabel().setText("Corresponding LTL constraint : " + LTLrule);
		    	  }	
		    	  else if(rule.equalsIgnoreCase("not chain resp.")) {	
		    		  String[] split2 = split1[0].split("\\,");
		    		  String LTLrule = Constants.LTL_globally + "(" + split2[0] + " " + Constants.LTL_IMPLIES + " " + Constants.LTL_NOT + Constants.LTL_next + split2[1] + ")";
		    		  _view.getLTLconstraintLabel().setText("Corresponding LTL constraint : " + LTLrule);
		    	  }	
		    	  else if(rule.equalsIgnoreCase("not chain prec.")) {	
		    		  String[] split2 = split1[0].split("\\,");
		    		  String LTLrule = Constants.LTL_globally + "(" + split2[0] + " " + Constants.LTL_IMPLIES + " " + Constants.LTL_NOT + Constants.LTL_next + split2[1] + ")";
		    		  _view.getLTLconstraintLabel().setText("Corresponding LTL constraint : " + LTLrule);
		    	  }	
		    	  }
	            }
	        });
		
		
	
		_view.getRightButton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
            	if(((String)_view.getRuleComboBox().getSelectedItem()).equalsIgnoreCase("---")) {            		
            		JOptionPane.showMessageDialog(null, "Please select a valid DECLARE rule!", "ATTENTION!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/info_icon.png"));
            	}            	
            	else if(((String)_view.getRuleComboBox().getSelectedItem()).equalsIgnoreCase("existence") || ((String)_view.getRuleComboBox().getSelectedItem()).equalsIgnoreCase("absence")) {
            			
            		if(((String)_view.getFirstTaskComboBox().getSelectedItem()).equalsIgnoreCase("---")) {
            	       		JOptionPane.showMessageDialog(null, "Please associate a valid task to the DECLARE rule!", "ATTENTION!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/info_icon.png"));
            			}
            		else {
            			String rule = (String)_view.getRuleComboBox().getSelectedItem();
            			String task = (String)_view.getFirstTaskComboBox().getSelectedItem();
            			String declare_rule = rule + "(" + task + ")";
            			if(_view.getRulesListModel().contains(declare_rule))
            				JOptionPane.showMessageDialog(null, "The DECLARE rule '" + declare_rule + "' already exists!", "ATTENTION!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/info_icon.png"));
            			else
            				_view.getRulesListModel().addElement(declare_rule);
            		}
            	}
            	else if(
            			(((String)_view.getFirstTaskComboBox().getSelectedItem()).equalsIgnoreCase("---")) || ((String)_view.getSecondTaskComboBox().getSelectedItem()).equalsIgnoreCase("---"))
            			{       		
            	       		JOptionPane.showMessageDialog(null, "Please associate a valid task to the DECLARE rule!", "ATTENTION!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/info_icon.png"));
            			}
            	else {
        			String rule = (String)_view.getRuleComboBox().getSelectedItem();
        			String task1 = (String)_view.getFirstTaskComboBox().getSelectedItem();
        			String task2 = (String)_view.getSecondTaskComboBox().getSelectedItem();
        			String declare_rule = rule + "(" + task1 + "," + task2 + ")";
        			if(_view.getRulesListModel().contains(declare_rule))
        				JOptionPane.showMessageDialog(null, "The DECLARE rule '" + declare_rule + "' already exists!", "ATTENTION!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/info_icon.png"));
        			else if(task1.equalsIgnoreCase(task2))
        				JOptionPane.showMessageDialog(null, "The tasks used as arguments of the DECLARE rule must be different!", "ATTENTION!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/info_icon.png"));
        			else
        				_view.getRulesListModel().addElement(declare_rule);
        		}
            	           	
            }
        });
		
		_view.getRemoveButton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
            	
            	if(_view.getRulesList().getSelectedIndex() == -1) { //no rule selected
            		JOptionPane.showMessageDialog(null, "Please select a rule to remove!", "ATTENTION!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/info_icon.png"));
            	} 
               	else {
                    	int index = _view.getRulesList().getSelectedIndex();   	
                		_view.getRulesListModel().removeElementAt(index);
                		
                	}
            	
            }
        });
		
		_view.getPreviousStepButton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
        			 Constants.getTracePerspective().setComponentEnabled(true);
        			 Constants.getRulesPerspective().setComponentEnabled(false);
            }
        });
		
				
		_view.getNextStepButton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {            	
            	if(!Constants.getMenuPerspective().getOptimalPlanningMenuItem().isSelected() && !Constants.getMenuPerspective().getSubOptimalPlanningMenuItem().isSelected())
            		JOptionPane.showMessageDialog(null, "It is required to choose at least a valid \nsearch heuristic to run the planner!", "ATTENTION!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/info_icon.png"));
            	else {
	        		if(_view.getRulesListModel().getSize()>0) {
	        			PlannerPerspective ple = new PlannerPerspective();
	        			
	             		for(int i=0;i<Constants.getAlphabetPerspective().getAlphabetListModel().size();i++) {
	            			String string = (String) Constants.getAlphabetPerspective().getAlphabetListModel().getElementAt(i);
	            			ple.getTasksArea().append(string + "\n");
	            			ple.getTasksArea().setCaretPosition(0);
	             			}
	             		
	             		for(int j=0;j<Constants.get_all_traces_vector().size();j++) {
	             			Trace trace = Constants.get_all_traces_vector().elementAt(j);
	             			ple.getTraceArea().append("****************\n");
	             			ple.getTraceArea().append(trace.getTraceName() + "\n");
	             			ple.getTraceArea().append("****************\n");
	             			for(int jind=0;jind<trace.get_Original_Trace_content_vector().size();jind++) {
	             				ple.getTraceArea().append(trace.get_Original_Trace_content_vector().elementAt(jind) + "\n");
	             			}
	             			ple.getTraceArea().append("\n");
	             		}
	             		
	             		ple.getTraceArea().setCaretPosition(0);
	             		             		
	        			// Update Constants.rules_vector -- 1
	         			Constants.setRulesVector(new Vector<String>()); 
	         			////////////////////////////////////////////////
	         			
	             		for(int k=0;k<Constants.getRulesPerspective().getRulesListModel().size();k++) {
	            			String string = (String) Constants.getRulesPerspective().getRulesListModel().getElementAt(k);
	            			ple.getRulesArea().append(string + "\n");
	            			ple.getRulesArea().setCaretPosition(0);
	            			
	            			// Update Constants.rules_vector -- 2
	             			Constants.getRulesVector().addElement(string); 
	             			////////////////////////////////////////////////	
	             			}
	        			
	             		_view.getRuleComboBox().setSelectedIndex(0);
	             		_view.setComponentEnabled(false);
	             		
	             	 	ple.setModal(true);
	             		ple.setVisible(true);
	        		}	        		
	        		else {
	        			JOptionPane.showMessageDialog(null, "The list of DECLARE rules can not be empty!", "ATTENTION!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/info_icon.png"));
	        		}
            	}
            }
        });
		
		
	}

}
