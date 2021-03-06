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

import javagulp.controller.CgiCommunicate;
import javagulp.model.CartesianTableModel;
import javagulp.model.FractionalTableModel;
import javagulp.model.Keywords;
import javagulp.model.Material;
import javagulp.model.TaskKeywords;
import javagulp.view.Structures.Structure;
import javagulp.view.potential.ChargesElementsBonding;
import javagulp.view.potential.Electrostatics;
import javagulp.view.potential.EwaldOptions;
import javagulp.view.potential.PotentialOptions;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class GulpRun extends JPanel implements Serializable {

	private static final long serialVersionUID = -4350272075095363083L;

	private final String[] tabNames = { "Structures", "Potential",
			//"PotentialOptions", "ChargesElementsBonding", "Electrostatics", "EwaldOptions", 
			"ExternalForce", "Energetics", "RunType", "Output", "Execution", "XYZFit"};

	//private String[] bottomNames = {};
	private final JPanel[] top = new JPanel[tabNames.length];
	//	private JPanel[] bottom = new JPanel[bottomNames.length];

	public JTabbedPane topPane = new JTabbedPane();
	public JScrollPane topScroll = new JScrollPane(topPane);
	private final TopListener keyTop = new TopListener();

	public Map<String,String> cgiMap;

	//	public JTabbedPane bottomPane = new JTabbedPane();
	//	public JScrollPane bottomScroll = new JScrollPane(bottomPane);
	//	private JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
	//			topScroll, bottomScroll);

	private Keywords keywords = null;
	private TaskKeywords taskKeywords = null;
	boolean vnfMode = false;

	public GulpRun() {
		super();
		setLayout(new BorderLayout());
		add(topPane, BorderLayout.CENTER);
		topPane.addChangeListener(keyTop);

		topPane.add(null, "structures");
		topPane.add(null, "potentials");
		//topPane.add(null, "potential options");
		//topPane.add(null, "charges, elements and bonding");
		//topPane.add(null, "electrostatics");
		//topPane.add(null, "ewald options");
		
		topPane.add(null, "external force");
		topPane.add(null, "energetics");
		topPane.add(null, "run type");
		topPane.add(null, "output");
		topPane.add(null, "execution");
		topPane.add(null, "xyz fit (experimental)");
	}

	public void processArguments(String[] simulationParams) {
		cgiMap = new HashMap<String,String>();
		if (simulationParams.length==0){
			return;
			//simulationParams=new String[]{"username=demo","ticket=5X","simulationId=1","structureId=9TAL9D"};
		} else{
			for(final String param: simulationParams){
				final String[] keyVal = param.split("=");
				if (keyVal.length==1){
					cgiMap.put(keyVal[0],"");
				} else
					cgiMap.put(keyVal[0],keyVal[1]);
			}
			// declare launchmode
			//if (cgiMap.containsKey("sentry.username") && cgiMap.get("launchmode").equals("vnf")){
			if (cgiMap.containsKey("sentry.username"))
				vnfMode = true;
			// if matter is passed, retrieve it and load it
			if(cgiMap.containsKey("structureId")){
				//final Material mat = getMaterialFromDb();
				final Material mat = getMaterialFromHttp();
				// set fractional coordinates
				((FractionalTableModel) getStructure().atomicCoordinates.getTableModel()).setCoordinates(mat);
				// then set Cartesian coordinates
				getStructure().atomicCoordinates.setTable("cartesian");
				((CartesianTableModel) getStructure().atomicCoordinates.getTableModel()).setCoordinates(mat);
				getStructure().unitCellAndSymmetry.unitCellPanel.threeDUnitCell.setVectors(mat);
				getStructure().unitCellAndSymmetry.spaceGroup.txtSpaceGroup.setText(Integer.toString((Integer)mat.spaceGroup));
				//if fractional coordinates is nonzero (i.e. the primary one), put that back in view
				if(mat.fractionalCoordinatesVec.length>0){
					getStructure().atomicCoordinates.setTable("3d fractional");	
				}
			}
		}
		//keep the rest of the parameters and pass them to the job submission post and other widgets
	}

	public HashMap<String,String> putInAuthenticationInfo2(HashMap<String,String> map){
		map.put("sentry.username", cgiMap.get("sentry.username"));
		if(cgiMap.containsKey("sentry.ticket")&&(cgiMap.get("sentry.ticket")!=null)){
			final String val = cgiMap.get("sentry.ticket");
			map.put("sentry.ticket", val);
		} else {
			final String val = cgiMap.get("sentry.passwd");
			map.put("sentry.passwd", val);
		}
		return map;
	}

	public void putInAuthenticationInfo(Map<String,String> map){
		map.put("sentry.username", cgiMap.get("sentry.username"));
		if(cgiMap.containsKey("sentry.ticket")&&(cgiMap.get("sentry.ticket")!=null)){
			final String val = cgiMap.get("sentry.ticket");
			map.put("sentry.ticket", val);
		} else {
			final String val = cgiMap.get("sentry.passwd");
			map.put("sentry.passwd", val);
		}
	}

	Material getMaterialFromHttp(){
		final String cgihome = cgiMap.get("cgihome");
		final CgiCommunicate cgiCom = new CgiCommunicate(cgihome);
		final Map<String,String> keyValsForMatter = new HashMap<String,String>();
		putInAuthenticationInfo(keyValsForMatter);
		keyValsForMatter.put("actor", "dbObjToWeb");
		keyValsForMatter.put("routine", "getMaterial");
		//keyValsForMatter.put("dbObjToWeb.tables", "atomicstructures");
		keyValsForMatter.put("actor.id", cgiMap.get("structureId"));
		//keyValsForMatter.put("content", "json");
		cgiCom.setCgiParams(keyValsForMatter);
		final JSONObject matterAsJSON = cgiCom.postAndGetJSONObject();
		final Material mat = new Material();
//		String[] materialParameters = new String(){"cartesian_lattice", "fractional_coordinates", 
//			"cartesian_coordinates", "atom_symbols"}
//		for (String materialParameter : materialParameters){
//			if(matterAsJSON.has(materialParameter))	
//		}
		//System.out.print(matterAsJSON.names());
		try {
			mat.latticeVec = ((JSONArray)matterAsJSON.get("cartesian_lattice")).getArrayList();
			if(matterAsJSON.has("fractional_coordinates"))
				mat.fractionalCoordinatesVec = ((JSONArray)matterAsJSON.get("fractional_coordinates")).getArrayList();
			if(matterAsJSON.has("cartesian_coordinates"))
				mat.cartesianCoordinatesVec = ((JSONArray)matterAsJSON.get("cartesian_coordinates")).getArrayList();
			mat.atomSymbols = ((JSONArray)matterAsJSON.get("atom_symbols")).getArrayList();//.getArrayList().toArray();
			mat.spaceGroup = matterAsJSON.get("space_group");
			mat.charges = ((JSONArray)matterAsJSON.get("charges")).getArrayList();
		} catch (final JSONException e) {
			e.printStackTrace();
		}
		return mat;
	}

	Material getMaterialFromDb(){
		String structureId = cgiMap.get("structureId");
		final String query = "SELECT * FROM atomicstructures WHERE id = '"+structureId+"'";
		// get the material parameters from the db (eventually use ORM tool)
		final Material mat = new Material();
		try {
			final Properties props = new Properties();
			props.setProperty("user","vnf");
			props.setProperty("password","");
			final String url = "jdbc:postgresql://vnf.caltech.edu:5432/vnfa2b";
			Class.forName("org.postgresql.Driver");
			final Connection con = DriverManager.getConnection(url,props);
			final Statement stmt = con.createStatement();
			final ResultSet rs = stmt.executeQuery(query);
			rs.next();
			///latticeArray = rs.getArray("cartesian_lattice");
			mat.latticeVec = (Object[])rs.getArray("cartesian_lattice").getArray();
			mat.fractionalCoordinatesVec = (Object[])rs.getArray("fractional_coordinates").getArray();
			mat.atomSymbols = (Object[])rs.getArray("atom_symbols").getArray();
			rs.close();
			stmt.close();
			con.close();
		} catch (final Exception ex) {
			ex.printStackTrace();
		}
		return mat;
	}

	private class TopListener implements ChangeListener, Serializable {
		private static final long serialVersionUID = -7619847591444570775L;

		public void stateChanged(ChangeEvent e) {
			final int index = topPane.getSelectedIndex();
			topPane.setComponentAt(index, getTopPanel(index));
		}
	};

	JPanel getTopPanel(int index) {
		if (top[index] == null) {
			final String pkg = "javagulp.view.";
			try {
				final Class c = Class.forName(pkg + tabNames[index]);
				top[index] = (JPanel) c.newInstance();
			} catch (final ClassNotFoundException e) {
				e.printStackTrace();
			} catch (final InstantiationException e) {
				e.printStackTrace();
			} catch (final IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return top[index];
	}

	public String getWD() {
		return Back.atomSimProps.getProperty("workingDirectory");
		//return getExecution().txtWorkingDirectory.getText();
	}

	public String getBinary() {
		return Back.atomSimProps.getProperty("executablePath");
		//return getExecution().txtGulpBinary.getText();
	}

	public Structure getStructure() {
		return (Structure)getStructures().tabs.getSelectedComponent();
	}

	public Keywords getKeywords() {
		if (keywords == null)
			keywords = new Keywords();
		return keywords;
	}

	public TaskKeywords getTaskKeywords() {
		if (taskKeywords == null)
			taskKeywords = new TaskKeywords();
		return taskKeywords;
	}

	public String getRunTypeKeyword() {
		return (String) getRunTypePanel().cboRunType.getSelectedItem();
	}

	public JPanel getSelectedRunTypePanel(String type) {
		return getRunTypePanel().getSelectedRunTypePanel(type);
	}

	public Structures getStructures() {
		return (Structures) getTopPanel(0);
	}

	public Potential getPotential() {
		return (Potential) getTopPanel(1);
	}

//	public PotentialOptions getPotentialOptions() {
//		return (PotentialOptions) getTopPanel(2);
//	}
//
//	public ChargesElementsBonding getChargesElementsBonding() {
//		return (ChargesElementsBonding) getTopPanel(3);
//	}
//
//	public Electrostatics getElectrostatics() {
//		return (Electrostatics) getTopPanel(4);
//	}
//
//	public EwaldOptions getEwaldOptions() {
//		return (EwaldOptions) getTopPanel(5);
//	}

	public ExternalForce getExternalForce() {
		return (ExternalForce) getTopPanel(2);
	}

	public Energetics getEnergeticsPanel() {
		return (Energetics) getTopPanel(3);
	}
	
	public RunType getRunTypePanel() {
		return (RunType) getTopPanel(4);
	}

	public Output getOutput() {
		return (Output) getTopPanel(5);
	}

	public Execution getExecution() {
		return (Execution) getTopPanel(6);
	}
	
	public XYZFit getXYZFit() {
		return (XYZFit) getTopPanel(7);
	}

}