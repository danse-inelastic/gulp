package javagulp.view;

import java.io.Serializable;

import javagulp.controller.IncompleteOptionException;
import javagulp.controller.InvalidOptionException;
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
		energyMatProps.setBounds(10, 10, 936, 221);
		add(energyMatProps);
		freeEnergy.setBounds(10, 237, 936, 445);
		add(freeEnergy);
	}
	
	public String write() throws IncompleteOptionException, InvalidOptionException {
		return energyMatProps.write() 
		+ freeEnergy.write();
	}
	
}