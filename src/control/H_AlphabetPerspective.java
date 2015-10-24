package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import main.Constants;
import main.Trace;
import view.AlphabetPerspective;

public class H_AlphabetPerspective {
	
	private AlphabetPerspective _view = null;
	
	public H_AlphabetPerspective (AlphabetPerspective i_view){
		_view = i_view;
		installListeners();
	}

	private void installListeners() {
	
		_view.getNextStepButton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
            	if(_view.getAlphabetListModel().getSize()>0) {
        			 Constants.getAlphabetPerspective().setComponentEnabled(false);
        			 Constants.getTracePerspective().setComponentEnabled(true);
        			 
        			// Update Constants.tasks_repository_vector -- 1
         			Constants.setTasksRepositoryVector(new Vector<String>()); 
         			////////////////////////////////////////////////
         			
             		for(int i=0;i<_view.getAlphabetListModel().getSize();i++) {
            			String string = (String) _view.getAlphabetListModel().getElementAt(i);
            			
            			// Update the alphabet of tasks for the second panel that manages traces (TracePerspective)
            			if(!Constants.getTracePerspective().getAlphabetListModel().contains(string))
            				Constants.getTracePerspective().getAlphabetListModel().addElement(string);
            			
            			// Update Constants.tasks_repository_vector -- 2
            			Constants.getTasksRepositoryVector().addElement(string);            			
            			////////////////////////////////////////////////
             			}
        			}
        		else {
        			JOptionPane.showMessageDialog(null, "The repository of tasks can not be empty!", "ATTENTION!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/info_icon.png"));
        		}
            }
        });
		
		_view.getRightButton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
            	if(_view.getTaskField().getText().equalsIgnoreCase("") || 
            			_view.getTaskField().getText().equalsIgnoreCase(" ") ||	_view.getTaskField().getText().contains(" ")) {
            		
            			JOptionPane.showMessageDialog(null, "The name of the task can not be empty and can not contain blank spaces!", "ATTENTION!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/info_icon.png")); 		
            	}
            	else if(isUpperCase(_view.getTaskField().getText()))
            		JOptionPane.showMessageDialog(null, "Please use only lower space characters for the task name!", "ATTENTION!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/info_icon.png"));
            	
            	else if(_view.getTaskField().getText().charAt(0) >= '0' && _view.getTaskField().getText().charAt(0) <= '9')           	
            		JOptionPane.showMessageDialog(null, "It is not allowed to use an Integer as the first character of the task name!", "ATTENTION!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/info_icon.png"));
		
            	else {
            		
            		boolean task_name_already_exists = false;
            		
            		for(int i=0;i<_view.getAlphabetListModel().getSize();i++) {
            			String string = (String) _view.getAlphabetListModel().getElementAt(i);
            			if(string.equalsIgnoreCase(_view.getTaskField().getText())) {
            				JOptionPane.showMessageDialog(null, "The task '" + _view.getTaskField().getText() + "' already exists. Please choose a different name for the task!", "ATTENTION!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/info_icon.png"));
            				task_name_already_exists = true;
            				break;
            			}
            			}
            	
            		if(!task_name_already_exists){
	            		_view.getAlphabetListModel().addElement(_view.getTaskField().getText());
	            		_view.getTaskField().setText("");
            		}
            	}
            	
            }
        });
		
		_view.getRemoveButton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
            	
            	if(_view.getAlphabetList().getSelectedIndex() == -1) { //no task selected
            		JOptionPane.showMessageDialog(null, "Please select a task to remove from the repository!", "ATTENTION!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/info_icon.png"));
            	} 
               	else {
                    	int index = _view.getAlphabetList().getSelectedIndex();
                    	String elem = (String) _view.getAlphabetList().getSelectedValue();
                		_view.getAlphabetListModel().removeElementAt(index);
                
                		// Remove the selected task from the alphabet of tasks in the second panel that manages traces (TracePerspective)
                		if(Constants.getTracePerspective().getAlphabetListModel().contains(elem))
                			Constants.getTracePerspective().getAlphabetListModel().removeElement(elem);                		              		
                		
                		for(int k=0;k<Constants.get_all_traces_vector().size();k++) {
                			
                			Trace trace = Constants.get_all_traces_vector().elementAt(k);
                			
                			for(int g=0;g<trace.get_Original_Trace_content_vector().size();g++) {
                				
                				String item = trace.get_Original_Trace_content_vector().elementAt(g);
                				
                				if(item.equalsIgnoreCase(elem)) {
                					trace.get_Original_Trace_content_vector().removeElement(elem);
                				}
                			}
                			
                		}
                		while(Constants.getTracePerspective().getTraceListModel().contains(elem))
                			Constants.getTracePerspective().getTraceListModel().removeElement(elem);
                     		
                		                		
                		for(int k=0;k<Constants.getRulesPerspective().getRulesListModel().getSize();k++) {
                			String string = (String) Constants.getRulesPerspective().getRulesListModel().getElementAt(k);
                			String[] split = string.split("\\(");
                			if(split[1].contains(elem))
                				Constants.getRulesPerspective().getRulesListModel().removeElementAt(k);
                		}
                	}
            }
        });
		
	}
	
	private boolean isUpperCase(String str){
		
		for(int i=0; i<str.length(); i++){
			char c = str.charAt(i);
			
			if(Character.isUpperCase(c))
				return true;
			}
		return false;

	}

}
