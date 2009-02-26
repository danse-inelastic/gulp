package javagulp.controller;

import javagulp.view.Back;

public class LaunchGulpUi {

	private static Back b = null;
	
	
	public LaunchGulpUi(String[] simulationParams) {

		if (b == null)
			b = new Back(simulationParams);
		else
			b.addTab(simulationParams);

	}
	

	public static void main(String[] args){
		//try simple iron as 9TAL9D
		if (args.length==0){
			args=new String[]{"username=demo","ticket=5X","simulationId=1","matterId=9TAL9D"};
		}
		new LaunchGulpUi(args);
	}

}