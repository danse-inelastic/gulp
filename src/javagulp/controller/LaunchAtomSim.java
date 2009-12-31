package javagulp.controller;

import javagulp.view.Back;

public class LaunchAtomSim {

	private static Back b = null;

	public LaunchAtomSim(String[] simulationParams) {
		if (b == null)
			b = new Back(simulationParams);
		else
			b.addTab(simulationParams);
	}


	public static void main(String[] args){
		//try simple iron as 9TAL9D
		if (args.length==0){
			//args=new String[]{"sentry.username=demo","sentry.ticket=5X","simulationId=1","structureId=9TAL9D"};
		}
		new LaunchAtomSim(args);
	}

}