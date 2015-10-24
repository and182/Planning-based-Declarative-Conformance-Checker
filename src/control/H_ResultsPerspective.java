package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.ResultsPerspective;


public class H_ResultsPerspective {
	
	public ResultsPerspective _view = null;
	
	public H_ResultsPerspective (ResultsPerspective i_view){
		_view = i_view;
		installListeners();
	}


	private void installListeners() {
		
	
		_view.getOkButton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
               	_view.dispose();
            }
        });
		
	}
	
}