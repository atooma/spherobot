package com.atooma.spherobot.commands;

import orbotix.sphero.Sphero;

public class CommandParser {
	
	public static void parseAndExecute(Sphero robot, Command command) {
		
		String cmdName = command.getCommand();
		
		if (cmdName.equals("drive")) {
			robot.drive((Float)command.getArguments().get(0), 
					(Float)command.getArguments().get(1));
		}
		
		else if (cmdName.equals("color")) {
			robot.setColor((Integer)command.getArguments().get(0), 
					(Integer)command.getArguments().get(1),
					(Integer)command.getArguments().get(2));
		}
		
	}

}
