package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import main.Constants;
import main.Trace;
import view.TracePerspective;

public class H_TracePerspective {
	
	public TracePerspective _view = null;
	
	public H_TracePerspective (TracePerspective i_view){
		_view = i_view;
		installListeners();
	}


	private void installListeners() {	
	
		_view.getRightButton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
            	if(_view.getAlphabetList().getSelectedIndex() == -1) {            		
            		JOptionPane.showMessageDialog(null, "Please select a task to be inserted in the trace!", "ATTENTION!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/info_icon.png"));
            	}   
            	else if(_view.getTracesComboBox().getSelectedItem().toString().equalsIgnoreCase(" --- ")) {            		
            		JOptionPane.showMessageDialog(null, "Please select a valid trace for inserting the task!", "ATTENTION!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/info_icon.png"));
            	}   
            	else {
            		String selected_element = (String) _view.getAlphabetList().getSelectedValue();
            		_view.getTraceListModel().addElement(selected_element);     
            		
            		//Update the selected trace (rebuild every time the structures associated to the trace)
            		String selected_trace_name = (String) _view.getTracesComboBox().getSelectedItem();
            		Vector<Trace> all_traces_vector = Constants.get_all_traces_vector();
            		
            		for(int h=0;h<all_traces_vector.size();h++) {
            			Trace t = all_traces_vector.elementAt(h);
            			
            			if(t.getTraceName().equalsIgnoreCase(selected_trace_name)) {
            				updateTrace(t);
            				break;
            			}
            		}
            	}
            }
        });
		
		_view.getRemoveButton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
            	if(_view.getTraceList().getSelectedIndex() == -1) { //no task selected
            		JOptionPane.showMessageDialog(null, "Please select a task to be removed from the trace!", "ATTENTION!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/info_icon.png"));
            	} 
               	else {
                    	int index = _view.getTraceList().getSelectedIndex();   	
                		_view.getTraceListModel().removeElementAt(index);
                		                		
                		//Update the selected trace (rebuild every time the structures associated to the trace)
                		String selected_trace_name = (String) _view.getTracesComboBox().getSelectedItem();
                		Vector<Trace> all_traces_vector = Constants.get_all_traces_vector();
                		
                		for(int h=0;h<all_traces_vector.size();h++) {
                			Trace t = all_traces_vector.elementAt(h);
                			
                			if(t.getTraceName().equalsIgnoreCase(selected_trace_name)) {
                				updateTrace(t);
                				break;
                			}
                		}
                	}
            }
        });
		
		_view.getUpButton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
            	if(_view.getAlphabetList().getSelectedIndex() == -1) {            		
            		JOptionPane.showMessageDialog(null, "Please select the task of the trace you want to move!", "ATTENTION!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/info_icon.png"));
            	}            	
            	else {

            		if(_view.getTraceList().getSelectedIndex() > 0) {
            			
            			int index = _view.getTraceList().getSelectedIndex();
            			String element = (String) _view.getTraceList().getSelectedValue();
            			
            			int index_down = index -1;
            			_view.getTraceListModel().removeElementAt(index);
            			_view.getTraceListModel().insertElementAt(element, index_down);
            			_view.getTraceList().setSelectedIndex(index_down);
            			
            			
                		//Update the selected trace (rebuild every time the structures associated to the trace)
                		String selected_trace_name = (String) _view.getTracesComboBox().getSelectedItem();
                		Vector<Trace> all_traces_vector = Constants.get_all_traces_vector();
                		
                		for(int h=0;h<all_traces_vector.size();h++) {
                			Trace t = all_traces_vector.elementAt(h);
                			
                			if(t.getTraceName().equalsIgnoreCase(selected_trace_name)) {
                				updateTrace(t);
                				break;
                			}
                		}
            		}
            	}
            	           	
            }
        });
		
		_view.getDownButton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
            	if(_view.getTraceList().getSelectedIndex() == -1) {            		
            		JOptionPane.showMessageDialog(null, "Please select the task of the trace you want to move!", "ATTENTION!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/info_icon.png"));
            	}            	
            	else {

            		if(_view.getTraceList().getSelectedIndex() + 1 < _view.getTraceListModel().size()) {
            			
            			int index = _view.getTraceList().getSelectedIndex();
            			String element = (String) _view.getTraceList().getSelectedValue();
            			
            			int index_up = index + 1;
            			_view.getTraceListModel().removeElementAt(index);
            			_view.getTraceListModel().insertElementAt(element, index_up);
            			_view.getTraceList().setSelectedIndex(index_up);
            			
                		//Update the selected trace (rebuild every time the structures associated to the trace)
                		String selected_trace_name = (String) _view.getTracesComboBox().getSelectedItem();
                		Vector<Trace> all_traces_vector = Constants.get_all_traces_vector();
                		
                		for(int h=0;h<all_traces_vector.size();h++) {
                			Trace t = all_traces_vector.elementAt(h);
                			
                			if(t.getTraceName().equalsIgnoreCase(selected_trace_name)) {
                				updateTrace(t);
                				break;
                			}
                		}
            		}
            	}
            	           	
            }
        });
		
		_view.getEditTraceButton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {

            	    String[] options = new String[] {"Create", "Remove", "Cancel"};
            	    int response = JOptionPane.showOptionDialog(null, "Do you want to create a new trace, \nor to remove an existing one?", "Create/Remove Traces",JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,null, options, options[0]);

            	    //Create a new trace
            	    if(response==0) {
            	    	int number_of_elements_of_combo_box = _view.getTracesComboBox().getItemCount();
            	    	
            	    	String new_item = new String();
            	    	
            	    	for(int i=1;i<=number_of_elements_of_combo_box;i++) {
            	    		new_item = "Trace#" + i;            	    		
            	    		String item = _view.getTracesComboBox().getItemAt(i);
            	    		if(!new_item.equalsIgnoreCase(item)) {
            	    			_view.getTracesComboBox().insertItemAt(new_item, i);
            	    			_view.getTracesComboBox().setSelectedIndex(i);
            	    			_view.resetTraceListModel();
            	    			break;
            	    		}
            	    	}
            	    	Constants.get_all_traces_vector().addElement(new Trace(new_item));
            	    }

            	    //Remove an existing trace
            	    else if(response==1) {
            	    	String old_item = (String) _view.getTracesComboBox().getSelectedItem();
            	    	
            	    	if (old_item.equalsIgnoreCase(" --- "))
            	    		JOptionPane.showMessageDialog(null, "Please select a valid trace to remove!", "ATTENTION!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/info_icon.png"));
            	    	else {
            	    		_view.getTracesComboBox().removeItem(old_item);
            	    		_view.resetTraceListModel();
            	    		_view.getTracesComboBox().setSelectedItem(" --- ");
            	    		
            	    		//Remove the selected trace from the vector of traces
                    		Vector<Trace> all_traces_vector = Constants.get_all_traces_vector();
                    		
                    		for(int h=0;h<all_traces_vector.size();h++) {
                    			Trace t = all_traces_vector.elementAt(h);
                    			
                    			if(t.getTraceName().equalsIgnoreCase(old_item)) {
                    				Constants.get_all_traces_vector().removeElementAt(h);
                    				break;
                    			}
                    		}
            	    		
            	    		JOptionPane.showMessageDialog(null, old_item + " correctly removed!", "CONFIRMATION!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/info_icon.png"));
            	    	}
            	    	
            	    	}


            	
            }
        });
            	
		
		_view.getTracesComboBox().addItemListener(new ItemListener()
		{
			 public void itemStateChanged(ItemEvent event)
			{
				String selected_trace_name = (String) _view.getTracesComboBox().getSelectedItem();
			 
				if (event.getStateChange() == ItemEvent.SELECTED && !(selected_trace_name.equalsIgnoreCase(" --- ")) )
				 {
					_view.resetTraceListModel();
					
					Vector<Trace> all_traces_vector = Constants.get_all_traces_vector();
            		
            		for(int h=0;h<all_traces_vector.size();h++) {
            			Trace t = all_traces_vector.elementAt(h);
            			
            			if(t.getTraceName().equalsIgnoreCase(selected_trace_name)) {
            				
            				for(int index=0;index<t.get_Original_Trace_content_vector().size();index++) {
            					_view.getTraceListModel().addElement(t.get_Original_Trace_content_vector().elementAt(index));
            				}
            				
            				break;
            			}
            		}
					
				 }
				else if(event.getStateChange() == ItemEvent.SELECTED && (selected_trace_name.equalsIgnoreCase(" --- "))) {
					_view.resetTraceListModel();
				}
			}
		});
		
		_view.getNextStepButton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
            	if(Constants.get_all_traces_vector().size()==0)  {
            		JOptionPane.showMessageDialog(null, "There is no trace defined for the log!\nAt least a trace is required to run the software!", "ATTENTION!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/info_icon.png"));
            	}
            	else {
            		Constants.getAlphabetPerspective().setComponentEnabled(false);
       			 	Constants.getTracePerspective().setComponentEnabled(false);
       			 	Constants.getRulesPerspective().setComponentEnabled(true);
       			 	
	       			Constants.getRulesPerspective().getFirstTaskComboBox().removeAllItems();
	     			Constants.getRulesPerspective().getSecondTaskComboBox().removeAllItems();
	     			Constants.getRulesPerspective().getFirstTaskComboBox().addItem("---");
	    			Constants.getRulesPerspective().getSecondTaskComboBox().addItem("---");
	    			
	    			for(int k=0;k<Constants.get_all_traces_vector().size();k++) {
	    					Trace trace = Constants.get_all_traces_vector().elementAt(k);
	    					trace.set_missing_tasks_vector(new Vector<String>(Constants.getTasksRepositoryVector()));
	    				    			
	    					//Update the missing tasks
	    					for(int j=0;j<trace.get_Original_Trace_content_vector().size();j++) {
	                 			String string = trace.get_Original_Trace_content_vector().elementAt(j);
	                 			trace.get_missing_tasks_vector().removeElement(string);

	                 			for(int pind=0;pind<trace.get_missing_tasks_vector().size();pind++) {
	                  				String missing_task = (String) trace.get_missing_tasks_vector().elementAt(pind);
	                  				String missing_task_key = missing_task + "0";
	                  				trace.get_Trace_Hashtable().put(missing_task_key, missing_task);
	                  			}
	                  		 }
	    					
	    					///////////////////////////////
	    					System.out.println("************************");
	    					System.out.println(trace.getTraceName());
	    					System.out.println(trace.get_Original_Trace_content_vector());
	    					System.out.println(trace.get_Trace_Hashtable());
	    					System.out.println(trace.get_PDDL_Trace_content_vector());
	    					System.out.println(trace.get_missing_tasks_vector());
	    					////////////////////////////////
	    			}
	    			for(int i=0;i<_view.getAlphabetListModel().getSize();i++) {
            			String string = (String) _view.getAlphabetListModel().getElementAt(i);
            			Constants.getRulesPerspective().getFirstTaskComboBox().addItem(string);
            			Constants.getRulesPerspective().getSecondTaskComboBox().addItem(string);
            			}
             		 
             		 Constants.getRulesPerspective().getFirstTaskComboBox().setEnabled(false);
        			 Constants.getRulesPerspective().getSecondTaskComboBox().setEnabled(false);
	    			
	    			}
            	
            }
        });
		
		_view.getPreviousStepButton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
        			 Constants.getAlphabetPerspective().setComponentEnabled(true);
        			 Constants.getTracePerspective().setComponentEnabled(false);

            }
        });
		
	}
	
	private void updateTrace(Trace trace) {
		
		trace.set_Trace_Hashtable(new Hashtable<String,String>());
		trace.set_Original_Trace_content_vector(new Vector<String>());
		trace.set_PDDL_Trace_content_vector(new Vector<String>());
		
		for(int j=0;j<_view.getTraceListModel().size();j++) {
  			String string = (String) _view.getTraceListModel().getElementAt(j);
  			
  			for(int p=0;p<_view.getTraceListModel().size();p++) {
  			
  				String string_key = string + p;
  				
  				if(!trace.get_Trace_Hashtable().containsKey(string_key)) {
  					trace.get_Trace_Hashtable().put(string_key,string);
  					trace.get_PDDL_Trace_content_vector().addElement(string_key);
  					trace.get_Original_Trace_content_vector().addElement(string);
  					break;
  				}
  			}
   		 }
		
		System.out.println("************************");
		System.out.println(trace.getTraceName());
		System.out.println(trace.get_Original_Trace_content_vector());
		System.out.println(trace.get_Trace_Hashtable());
		System.out.println(trace.get_PDDL_Trace_content_vector());
	}

}
