package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JOptionPane;

import main.Constants;
import main.Trace;

import view.MenuPerspective;

public class H_MenuPerspective {
	
	public MenuPerspective _view = null;
	
	public H_MenuPerspective (MenuPerspective i_view){
		_view = i_view;
		installListeners();
	}

	private void installListeners() {
		
		_view.getExitMenuItem().addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				Constants.getDesktop().dispose();
				
			}
		});
		
		_view.getNewMenuItem().addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				String[] options = new String[] {"Yes", "No"};
        	    int response = JOptionPane.showOptionDialog(null, "Lose changes?", "Create new log file",JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,null, options, options[0]);

        	    if(response==0) {
				
					Constants.getAlphabetPerspective().resetComponent();
					Constants.setTasksRepositoryVector(new Vector<String>());
					
					Constants.getTracePerspective().resetComponent();
					Constants.set_all_traces_vector(new Vector<Trace>());
					
					Constants.getRulesPerspective().resetComponent();
					Constants.setRulesVector(new Vector<String>());
					Constants.setPDDLRulesVector(new Vector<String>());
					
					Constants.getAlphabetPerspective().setComponentEnabled(true);
					Constants.getTracePerspective().setComponentEnabled(false);
					Constants.getRulesPerspective().setComponentEnabled(false);
        	    }

			}
		});
		
	}

}
