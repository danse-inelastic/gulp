package javagulp.model;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;

public class ButtonGroupTransitional extends ButtonGroup {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6110418044749153620L;
	ButtonModel selection = null;

	public ButtonGroupTransitional(){
		super();
		selection=super.getSelection();
	}

	//@Override
	public void clearSelection() {
		if (selection != null) {
			final ButtonModel oldSelection = selection;
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
