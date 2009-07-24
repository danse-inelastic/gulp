package javagulp.view.phonons;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.controller.InvalidOptionException;
import javagulp.view.TitledPanel;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class BrillouinIntegration extends TitledPanel implements Serializable {

	private JPanel pnlNone;
	private JRadioButton noneRadioButton;
	private final ButtonGroup kptButtonGroup = new ButtonGroup();
	private JRadioButton explicitKpointsRadioButton;
	private JRadioButton kpointMeshRadioButton;
	private TitledPanel pnlKpointChoose;
	private JPanel pnlBackdrop;
	private static final long serialVersionUID = 3117144370107037990L;



	private final KpointsMesh pnlKpointsMesh = new KpointsMesh();
	private final ExplicitKpoints pnlExplicitKpoints = new ExplicitKpoints();

	public BrillouinIntegration() {
		super();
		add(getPnlBackdrop());
		add(getPnlKpointChoose());
	}

	public String write() throws IncompleteOptionException,
	InvalidOptionException {
		return pnlExplicitKpoints.writeKpoints() + pnlKpointsMesh.writeShrink();
	}
	/**
	 * @return
	 */
	protected JPanel getPnlBackdrop() {
		if (pnlBackdrop == null) {
			pnlBackdrop = new JPanel();
			pnlBackdrop.setLayout(new CardLayout());
			pnlBackdrop.setBounds(10, 139, 487, 182);
			pnlBackdrop.add(getPnlNone(), getPnlNone().getName());
			pnlKpointsMesh.setName("pnlKpointsMesh");
			pnlBackdrop.add(pnlKpointsMesh, pnlKpointsMesh.getName());
			pnlKpointsMesh.setBounds(673, 137, 273, 81);
			pnlExplicitKpoints.setName("pnlExplicitKpoints");
			pnlBackdrop.add(pnlExplicitKpoints, pnlExplicitKpoints.getName());
			pnlExplicitKpoints.setBounds(666, 245, 402, 75);
		}
		return pnlBackdrop;
	}
	/**
	 * @return
	 */
	protected TitledPanel getPnlKpointChoose() {
		if (pnlKpointChoose == null) {
			pnlKpointChoose = new TitledPanel();
			pnlKpointChoose.setTitle("type of kpoint sampling");
			pnlKpointChoose.setBounds(10, 10, 246, 123);
			pnlKpointChoose.add(getKpointMeshRadioButton());
			pnlKpointChoose.add(getExplicitKpointsRadioButton());
			pnlKpointChoose.add(getNoneRadioButton());
		}
		return pnlKpointChoose;
	}
	/**
	 * @return
	 */
	protected JRadioButton getKpointMeshRadioButton() {
		if (kpointMeshRadioButton == null) {
			kpointMeshRadioButton = new JRadioButton();
			kpointMeshRadioButton.addActionListener(new ActionListener() {
				public void actionPerformed(final ActionEvent e) {
					if (kpointMeshRadioButton.isSelected()){
						final CardLayout cl = (CardLayout)(getPnlBackdrop().getLayout());
						cl.show(getPnlBackdrop(), pnlKpointsMesh.getName());
					}
				}
			});
			kptButtonGroup.add(kpointMeshRadioButton);
			kpointMeshRadioButton.setBounds(10, 54, 220, 23);
			kpointMeshRadioButton.setText("kpoint mesh");
		}
		return kpointMeshRadioButton;
	}
	/**
	 * @return
	 */
	protected JRadioButton getExplicitKpointsRadioButton() {
		if (explicitKpointsRadioButton == null) {
			explicitKpointsRadioButton = new JRadioButton();
			kptButtonGroup.add(explicitKpointsRadioButton);
			explicitKpointsRadioButton.setText("explicit kpoints");
			explicitKpointsRadioButton.setBounds(10, 83, 220, 23);
			explicitKpointsRadioButton.setEnabled(false);
		}
		return explicitKpointsRadioButton;
	}
	/**
	 * @return
	 */
	protected JRadioButton getNoneRadioButton() {
		if (noneRadioButton == null) {
			noneRadioButton = new JRadioButton();
			noneRadioButton.addActionListener(new ActionListener() {
				public void actionPerformed(final ActionEvent e) {
					if (noneRadioButton.isSelected()){
						final CardLayout cl = (CardLayout)(getPnlBackdrop().getLayout());
						cl.show(getPnlBackdrop(), getPnlNone().getName());
					}
				}
			});
			kptButtonGroup.add(noneRadioButton);
			noneRadioButton.setSelected(true);
			noneRadioButton.setText("none");
			noneRadioButton.setBounds(10, 25, 138, 23);
		}
		return noneRadioButton;
	}
	/**
	 * @return
	 */
	protected JPanel getPnlNone() {
		if (pnlNone == null) {
			pnlNone = new JPanel();
			pnlNone.setName("pnlNone");
		}
		return pnlNone;
	}
}