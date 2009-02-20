package javagulp.model;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;

public class ButtonGroupTransitional extends ButtonGroup {
	
	ButtonModel selection = null;
	
	public ButtonGroupTransitional(){
		super();
		selection=super.getSelection();
	}
	
    @Override
	public void clearSelection() {
        if (selection != null) {
            ButtonModel oldSelection = selection;
            selection = null;
            oldSelection.setSelected(false);            
        }
    }
	
//	public ButtonGroupTransitional {
//
//	}
//	
//	public clearSelection(){
//		
//	}

}
