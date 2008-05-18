package javagulp.view.potential.twocenter;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.view.Back;
import javagulp.view.bottom.Potential;
import javagulp.view.potential.PPP;
import javagulp.view.potential.PotentialPanel;

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

//import utility.function.AbstractFunction;
//import utility.function.Value;
//import utility.function.twocenter.BuckinghamFunction;
import javagulp.model.G;

public class Buckingham extends PotentialPanel implements Serializable {

	private static final long serialVersionUID = 4469214289057439073L;

	private PPP A = new PPP("A (eV)");
	private PPP Rho = new PPP("<html>&#961; (&Aring;)</html>");
	private PPP C = new PPP("<html>C (eV/&Aring;<sup>6</sup>)</html>");

	private JFormattedTextField txtrmin = new JFormattedTextField(new Double(0.5));
	private JFormattedTextField txtrmax = new JFormattedTextField(new Double(10.0));

	private G g = new G();

	private JComboBox cboUnits = new JComboBox(new String[] {"kjmol", "kcal"});
	private JComboBox cboEnerGra = new JComboBox(new String[] {"energy", "gradient"});

	private JLabel lblUnits = new JLabel("units");
	private JLabel lblInnerCutoff = new JLabel(g.html("inner cutoff (&Aring;)"));
	private JLabel lblOuterCutoff = new JLabel(g.html("outer cutoff (&Aring;)"));
	private JLabel lblBuckinghamEq = new JLabel("<html>E = A exp(-r/&#961;) - C r<sup>-6</sup></html>");

	//private Graph graph = new Graph();
	JTabbedPane pane = new JTabbedPane();

	public Buckingham() {
		super(2);
		setTitle("buckingham");
		enabled = new boolean[] { true, true, true, true, true, true };

		lblBuckinghamEq.setOpaque(false);
		lblBuckinghamEq.setBounds(20, 23, 145, 25);
		add(lblBuckinghamEq);
		A.setBounds(10, 63, 225, 25);
		add(A);
		//A.txt.getDocument().addDocumentListener(da);
		Rho.setBounds(10, 89, 225, 25);
		add(Rho);
		//Rho.txt.getDocument().addDocumentListener(da);
		C.setBounds(10, 113, 225, 25);
		add(C);
		//C.txt.getDocument().addDocumentListener(da);
		lblInnerCutoff.setBounds(255, 66, 110, 15);
		add(lblInnerCutoff);
		txtrmin.setBackground(Back.grey);
		txtrmin.setBounds(371, 65, 77, 20);
		add(txtrmin);
		//txtrmin.getDocument().addDocumentListener(da);
		lblOuterCutoff.setBounds(255, 92, 110, 15);
		add(lblOuterCutoff);
		txtrmax.setBounds(370, 91, 78, 20);
		add(txtrmax);
		//txtrmax.getDocument().addDocumentListener(da);
		lblUnits.setBounds(255, 120, 70, 21);
		add(lblUnits);
		cboUnits.setBounds(370, 120, 85, 21);
		add(cboUnits);
		cboEnerGra.setBounds(10, 145, 85, 21);
		add(cboEnerGra);

		pane.setBounds(460, 10, 240, 250);
		add(pane);
		//pane.addTab("" + (pane.getTabCount()+1), graph);
		//graph.addFunction(new BuckinghamFunction());
		//graph.setWindow(0.5, 4, 0, 10);

		params = new PPP[]{A, Rho, C};

		Rho.min = 0;
		Rho.max = 1;
		A.min = 100;
		A.max = 25000;
	}

//	private class DocumentAdapter implements DocumentListener, Serializable {
//		private static final long serialVersionUID = 8888791144962018223L;
//		public void insertUpdate(DocumentEvent e) {
//			regraph();
//		}
//		public void removeUpdate(DocumentEvent e) {
//			regraph();
//		}
//		public void changedUpdate(DocumentEvent e) {}
//	}
//	private DocumentAdapter da = new DocumentAdapter();

	@Override
	public String writePotential() throws IncompleteOptionException {
		Back.checkAndParseD(params);
		Potential pot = Back.getPanel().getPotential();

		String lines = "buckingham " + pot.twoAtomBondingOptions.getInterIntraBond();
		if (cboUnits.getSelectedIndex() != 0)
			lines += cboUnits.getSelectedItem() + " "; 
		if (cboEnerGra.getSelectedIndex() == 1)
			lines += "grad";
		else
			lines += "ener";
		lines += " " + pot.twoAtomBondingOptions.getScale14() + Back.newLine + pot.getAtomCombos() 
		+ Back.concatFields(params);
		if (!pot.twoAtomBondingOptions.Bond()) {
			if (txtrmax.getText().equals(""))
				throw new IncompleteOptionException("Please enter a value for outer cutoff");
			if (!txtrmin.getText().equals("")
					&& !txtrmin.getText().equals("0.0")) {
				Double.parseDouble(txtrmin.getText());
				lines += " " + txtrmin.getText();
			}
			Double.parseDouble(txtrmax.getText());
			lines += " " + txtrmax.getText();
		}
		return lines + Back.writeFits(params) + Back.newLine;
	}

	private double distance(double[] d1, double[] d2) {
		return Math.sqrt(Math.pow(d1[0]-d2[0], 2)
				+ Math.pow(d1[1]-d2[1], 2)
				+ Math.pow(d1[2]-d2[2], 2));
	}

//	private void regraph() {
//		//update params
//		AbstractFunction f = graph.getFunction(0);
//		for (int i=0; i < params.length; i++) {
//			try {
//				double d = Double.parseDouble(params[i].txt.getText());
//				f.setParam(i+1, d);
//			} catch (NumberFormatException nfe) {}
//		}
//		double xmin=0.5, xmax=10;
//		try {
//			xmin = Double.parseDouble(txtrmin.getText());
//			xmax = Double.parseDouble(txtrmax.getText());
//		} catch (NumberFormatException nfe) {}
//		graph.setWindow(xmin, xmax, -10, 10);
//
//		//calculate bond lengths
//		ArrayList<double[]> xyz = Back.getStructure().atomicCoordinates.getTableModel().getCoordinates();
//		double[][] lengths = new double[xyz.size()][xyz.size()];
//		for (int j=0; j < xyz.size(); j++) 
//			for (int k=0; k < xyz.size(); k++) {
//				lengths[j][k] = distance(xyz.get(j), xyz.get(k));
//			}
//
//		//calculate point on curve
//		String atom1 = (String) Back.getPanel().getPotential().pnlAtom.cboAtom[0].getSelectedItem(),
//		atom2 = (String) Back.getPanel().getPotential().pnlAtom.cboAtom[1].getSelectedItem();
//		ArrayList<String> atoms = Back.getStructure().atomicCoordinates.getTableModel().getAllAtoms();
//		ArrayList<Value> bonds = new ArrayList<Value>();
//		for (int i=0; i < atoms.size(); i++) {
//			if (atoms.get(i).equals(atom1)) {
//				for (int j=0; j < atoms.size(); j++) {
//					if (i != j && atoms.get(j).equals(atom2)) {
//						Value v = new Value();
//						f.setParam(0, lengths[i][j]);
//						v.x = lengths[i][j];
//						v.y[0] = f.evaluate();
//						bonds.add(v);
//					}
//				}
//			}
//		}
//		graph.removeAllData();
//		graph.addData(bonds);
//
//		graph.autoAdjustWindowSettings();
//		graph.repaint();
//	}

	public void setRadiiEnabled(boolean b) {
		txtrmin.setEnabled(b);
		txtrmax.setEnabled(b);
	}

	@Override
	public PotentialPanel clone() {
		Buckingham b = new Buckingham();
		b.txtrmin.setText(this.txtrmin.getText());
		b.txtrmax.setText(this.txtrmax.getText());
		b.cboEnerGra.setSelectedIndex(this.cboEnerGra.getSelectedIndex());
		b.cboUnits.setSelectedIndex(this.cboUnits.getSelectedIndex());
		return super.clone(b);
	}
}
