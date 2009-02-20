package javagulp.view.structures;

import java.awt.BorderLayout;
import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.model.G;
import javagulp.view.Back;
//import javagulp.view.structures.UnitCellPanel.OneDUnitCell.OneDCellParameters;
//import javagulp.view.structures.UnitCellPanel.OneDUnitCell.OneDCellVectors;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

	class OneDUnitCell extends JPanel {

		private static final long serialVersionUID = -2905642930994016008L;

		private JTabbedPane tabbedPane = new JTabbedPane();

		private OneDCellParameters oneDCellParameters = new OneDCellParameters();
		private OneDCellVectors oneDCellVectors = new OneDCellVectors();

		public OneDUnitCell() {
			super();
			setLayout(new BorderLayout());

			add(tabbedPane);
			tabbedPane.add(oneDCellVectors, "vectors");
			tabbedPane.add(oneDCellParameters, "parameters");
			//oneDCellParameters.setLayout(null);
			
		}

		public String writeOneDUnitCell() throws IncompleteOptionException {
			if (tabbedPane.getSelectedIndex() == 0)
				return oneDCellParameters.writePcell();
			else
				return oneDCellVectors.writePvectors();
		}
	}