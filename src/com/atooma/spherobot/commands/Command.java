package com.atooma.spherobot.commands;

import java.util.ArrayList;

public class Command {
		
	private String command;
	private long duration;
	private ArrayList<Object> arguments = new ArrayList<Object>();
	private CommandListener listener;
	
	public Command(String command, long duration, ArrayList<Object> arguments) {
		setCommand(command);
		setDuration(duration);
		setArguments(arguments);
	}
	
	public void setListener(CommandListener listener) {
		this.listener = listener;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public ArrayList<Object> getArguments() {
		return arguments;
	}

	public void setArguments(ArrayList<Object> arguments) {
		this.arguments = arguments;
	}

	public void end() {
		if (listener != null)
			listener.onEnd();
	}
	
}
