package javagulp.view;

import java.io.Serializable;

import javagulp.view.energetics.EnergyMatProps;
import javagulp.view.energetics.FreeEnergy;

import javax.swing.JPanel;

public class Energetics extends JPanel implements Serializable {

	private static final long serialVersionUID = -2561314899818589985L;

	private final EnergyMatProps energyMatProps = new EnergyMatProps();
	private final FreeEnergy freeEnergy = new FreeEnergy();

	public Energetics() {
		super();
		setLayout(null);
		energyMatProps.setBounds(10, 10, 874, 221);
		add(energyMatProps);
		freeEnergy.setBounds(10, 237, 874, 311);
		add(freeEnergy);
	}
}