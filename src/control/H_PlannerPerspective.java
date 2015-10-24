package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import main.Constants;
import view.PlannerPerspective;
import view.ResultsPerspective;

public class H_PlannerPerspective {
	
	public PlannerPerspective _view = null;
	
	public H_PlannerPerspective (PlannerPerspective i_view){
		_view = i_view;
		installListeners();
	}


	private void installListeners() {
		
	
		_view.getPreviousStepButton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
            	
            	_view.dispose();
                 Constants.getRulesPerspective().setComponentEnabled(true);
            }
        });
		
				
		_view.getRunPlannerButton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
            	new ResultsPerspective();
            }
        });
		
		
	}
	
}
