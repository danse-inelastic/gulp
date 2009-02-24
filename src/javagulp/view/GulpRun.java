package javagulp.view;

import java.awt.BorderLayout;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javagulp.model.Keywords;
import javagulp.model.Material;
import javagulp.view.Structures.Structure;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GulpRun extends JPanel implements Serializable {

	private static final long serialVersionUID = -4350272075095363083L;

	private String[] tabNames = { "MolecularDynamics", "MonteCarlo",
			"EnergeticsMatProp", "Optimization", "Constraints", "Fit",
			"Phonons", "FreeEnergy", "TransitionState", "StructurePrediction", 
			"Surface", "ExternalForce","Structures", "Potential",
			"PotentialOptions", "ChargesElementsBonding", "Electrostatics",
			"EwaldOptions", "Output", "Execution" };
	
	//private String[] bottomNames = {};

	private JPanel[] top = new JPanel[tabNames.length];
//	private JPanel[] bottom = new JPanel[bottomNames.length];

	public JTabbedPane topPane = new JTabbedPane();
	public JScrollPane topScroll = new JScrollPane(topPane);
	private TopListener keyTop = new TopListener();

	public Map<String,String> keyVals;



//	public JTabbedPane bottomPane = new JTabbedPane();
//	public JScrollPane bottomScroll = new JScrollPane(bottomPane);

//	private JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
//			topScroll, bottomScroll);

	private Keywords keywords = null;

	public GulpRun() {
		super();
		//setLayout(new CardLayout());
		setLayout(new BorderLayout());

//		splitPane.setDividerLocation((Back.frame.getHeight() - 135) / 2);
//		splitPane.setDividerSize(4);
//		splitPane.setResizeWeight(0.5);
//		add(splitPane, BorderLayout.CENTER);
		
		add(topPane, BorderLayout.CENTER);

		topPane.addChangeListener(keyTop);
		topPane.add(null, "molecular dynamics");
		topPane.add(null, "monte carlo");
		topPane.add(null, "energetics and material properties");
		topPane.add(null, "optimization");
		topPane.add(null, "constraints");
		topPane.add(null, "fit");
		topPane.add(null, "phonons");
		topPane.add(null, "free energy");
		topPane.add(null, "transition state");
		topPane.add(null, "structure prediction");
		topPane.add(null, "surface");
		topPane.add(null, "external force");

		topPane.add(null, "structures");
		topPane.add(null, "potentials");
		topPane.add(null, "potential options");
		topPane.add(null, "charges, elements and bonding");
		topPane.add(null, "electrostatics");
		topPane.add(null, "ewald options");

		topPane.add(null, "output");
		topPane.add(null, "execution");
		
	}
	
	void processArguments(String[] simulationParams) {
		keyVals = new HashMap<String,String>();
		if (simulationParams.length==0){
			simulationParams=new String[]{"username=demo","ticket=5X",
					"simulationId=1","matterId=9TAL9D"};
		}
		for(String param: simulationParams){
			String[] keyVal = param.split("=");
			keyVals.put(keyVal[0],keyVal[1]);
		}
		//retrieve matter and load it
		Material mat = getMaterial(keyVals.get("matterId"));
		getStructure().atomicCoordinates.getTableModel().importCoordinates(mat);
		getStructure().unitCellAndSymmetry.unitCellPanel.threeDUnitCell.setVectors(mat);
		//keep the rest of the parameters and pass them to the job submission post
	}
	
	Material getMaterial(String id){
		String query = "SELECT * FROM polycrystals WHERE id = '"+id+"' UNION "+
		"SELECT * FROM singlecrystals WHERE id = '"+id+"' UNION "+
		"SELECT * FROM disordered WHERE id = '"+id+"'";
		//Array latticeArray = null;
		double[] latticeVec = null;
		//Array fractionalCoordinatesArray = null;
		double[] fractionalCoordinatesVec = null;
		String[] atomSymbols = null;
		// get the material parameters from the db (eventually use ORM tool)
		try {
			Properties props = new Properties();
//			props.setProperty("user","linjiao");
//			props.setProperty("password","4OdACm#");
//			String url = "jdbc:postgresql://localhost:54321/vnf";
			props.setProperty("user","vnf");
			props.setProperty("password","A4*gl8D");
			String url = "jdbc:postgresql://vnf.caltech.edu:5432/vnf";
			Class.forName("org.postgresql.Driver");
			Connection con = DriverManager.getConnection(url,props);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
			///latticeArray = rs.getArray("cartesian_lattice");
			latticeVec = (double[])rs.getArray("cartesian_lattice").getArray();
			fractionalCoordinatesVec = (double[])rs.getArray("fractional_coordinates").getArray();
			atomSymbols = (String[])rs.getArray("atom_symbols").getArray();
			rs.close();
			stmt.close();
			con.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			System.out.println(ex.toString());
			ex.printStackTrace();
		}
		Material mat = new Material();
		mat.latticeVec = latticeVec;
		mat.fractionalCoordinatesVec = fractionalCoordinatesVec;
		mat.atomSymbols = atomSymbols;
		return mat;
	}


	private class TopListener implements ChangeListener, Serializable {
		private static final long serialVersionUID = -7619847591444570775L;
		
		public void stateChanged(ChangeEvent e) {
			int index = topPane.getSelectedIndex();
			topPane.setComponentAt(index, getTopPanel(index));
		}
	};

	
	private JPanel getTopPanel(int index) {
		if (top[index] == null) {
			String pkg = "javagulp.view.";
			try {
				Class c = Class.forName(pkg + tabNames[index]);
				top[index] = (JPanel) c.newInstance();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return top[index];
	}
	
	public String getWD() {
		return getExecution().txtWorkingDirectory.getText();
	}
	
	public String getBinary() {
		return getExecution().txtGulpBinary.getText();
	}
	
	public Structure getStructure() {
		return (Structure)getStructures().tabs.getSelectedComponent();
	}
	
	public Keywords getKeywords() {
		if (keywords == null)
			keywords = new Keywords();
		return keywords;
	}
	
	//Top

	public MolecularDynamics getMd() {
		return (MolecularDynamics) getTopPanel(0);
	}

//	public MDRestartInit getMdRestartInit() {
//		return (MDRestartInit) getTopPanel(1);
//	}

	public MonteCarlo getMonteCarlo() {
		return (MonteCarlo) getTopPanel(1);
	}

	private EnergeticsMatProp getEnergeticsMatProp() {
		return (EnergeticsMatProp) getTopPanel(2);
	}

	public Optimization getOptimization() {
		return (Optimization) getTopPanel(3);
	}
	
	public Constraints getConstraints() {
		return (Constraints) getTopPanel(4);
	}
	public Fit getFit() {
		return (Fit) getTopPanel(5);
	}

//	public XYZFit getXyzfit() {
//		return (XYZFit) getTopPanel(7);
//	}

	public Phonons getPhonon() {
		return (Phonons) getTopPanel(6);
	}
	
	public FreeEnergy getFreeEnergy() {
		return (FreeEnergy) getTopPanel(7);
	}

	public TransitionState getTransitionState() {
		return (TransitionState) getTopPanel(8);
	}
	
	public StructurePrediction getStructurePrediction() {
		return (StructurePrediction) getTopPanel(9);
	}

//	public GeneticAlgorithm getGeneticAlgorithm() {
//		return (GeneticAlgorithm) getTopPanel(10);
//	}

//	public Defect getDefect() {
//		return (Defect) getTopPanel(13);
//	}

	public Surface getSurface() {
		return (Surface) getTopPanel(10);
	}

	public ExternalForce getExternalForce() {
		return (ExternalForce) getTopPanel(11);
	}
	
	public Structures getStructures() {
		return (Structures) getTopPanel(12);
	}

	public Potential getPotential() {
		return (Potential) getTopPanel(13);
	}

	public PotentialOptions getPotentialOptions() {
		return (PotentialOptions) getTopPanel(14);
	}

	public ChargesElementsBonding getChargesElementsBonding() {
		return (ChargesElementsBonding) getTopPanel(15);
	}

	public Electrostatics getElectrostatics() {
		return (Electrostatics) getTopPanel(16);
	}

	public EwaldOptions getEwaldOptions() {
		return (EwaldOptions) getTopPanel(17);
	}

	public Output getOutput() {
		return (Output) getTopPanel(18);
	}

	public Execution getExecution() {
		return (Execution) getTopPanel(19);
	}
}