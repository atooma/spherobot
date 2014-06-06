package com.atooma.spherobot;

import java.util.ArrayList;
import java.util.List;

import orbotix.robot.base.Robot;
import orbotix.robot.base.RobotProvider;
import orbotix.sphero.ConnectionListener;
import orbotix.sphero.DiscoveryListener;
import orbotix.sphero.Sphero;
import android.content.Context;
import android.os.Handler;

import com.atooma.spherobot.commands.Command;
import com.atooma.spherobot.commands.CommandListener;
import com.atooma.spherobot.commands.CommandParser;

public class SpheroBot {

	private Sphero mRobot;
	private ArrayList<Command> commands = new ArrayList<Command>();
	private int currentCommand = 0;
	private Context ctx;
	private String name;
	private SpheroBotListener listener;
	private boolean isRunning;

	public SpheroBot(Context ctx) {
		this.ctx = ctx;
	}

	public SpheroBot(Context ctx, String name) {
		this.ctx = ctx;
		this.name = name;
	}

	public synchronized void start() {
		start(new SpheroBotListener() {
			@Override
			public void onConnected(SpheroBot sphero) {
			}

			@Override
			public void onDisconnected(SpheroBot sphero) {
			}

			@Override
			public void onConnectionFailed(SpheroBot sphero) {
			}

			@Override
			public void onStop(SpheroBot sphero) {
			}
		});
	}

	public synchronized void start(final SpheroBotListener botListener) {
		isRunning = true;
		this.listener = botListener;
		if (mRobot != null) {
			startBot();
			return;
		}
		RobotProvider.getDefaultProvider().addConnectionListener(
				new ConnectionListener() {
					@Override
					public void onConnected(Robot sphero) {
						mRobot = (Sphero) sphero;
						listener.onConnected(SpheroBot.this);
						startBot();
					}

					@Override
					public void onConnectionFailed(Robot sphero) {
						listener.onConnectionFailed(SpheroBot.this);
					}

					@Override
					public void onDisconnected(Robot sphero) {
						mRobot = null;
						listener.onDisconnected(SpheroBot.this);
					}

				});

		RobotProvider.getDefaultProvider().addDiscoveryListener(
				new DiscoveryListener() {
					@Override
					public void onBluetoothDisabled() {
					}

					@Override
					public void discoveryComplete(List<Sphero> spheros) {
					}

					@Override
					public void onFound(List<Sphero> spheros) {
						if (name != null) {
							for (Sphero sphero : spheros) {
								if (sphero.getName().equals(name)) {
									RobotProvider.getDefaultProvider().connect(
											sphero);
								}
							}
						} else {
							RobotProvider.getDefaultProvider().connect(
									spheros.iterator().next());
						}
					}
				});

		boolean success = RobotProvider.getDefaultProvider()
				.startDiscovery(ctx);
		if (!success) {
			listener.onConnectionFailed(SpheroBot.this);
		}
	}

	public synchronized void drive(float degrees, float speed, long duration) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.add(degrees);
		arguments.add(speed);
		arguments.add(duration);
		Command command = new Command("drive", duration, arguments);
		command.setListener(new CommandListener() {
			@Override
			public void onEnd() {
				mRobot.stop();
			}
		});
		commands.add(command);
	}

	public synchronized void setColor(int r, int g, int b, long duration) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.add(r);
		arguments.add(g);
		arguments.add(b);
		commands.add(new Command("color", duration, arguments));
	}

	public void disconnect() {
		if (mRobot != null)
			mRobot.disconnect();
	}

	private void reset() {
		commands.clear();
		currentCommand = 0;
	}

	private void startBot() {
		processCommand(commands.get(0));
	}

	private synchronized void processCommand(final Command command) {
		try {
			CommandParser.parseAndExecute(mRobot, command);
			Thread thread=  new Thread(){
		        @Override
		        public void run(){
		            try {
		                synchronized(this){
		                    wait(command.getDuration());
		                }
		                command.end();
						currentCommand++;
						if (currentCommand >= commands.size()) {
							reset();
							isRunning = false;
							if (listener != null)
								listener.onStop(SpheroBot.this);
							return;
						}
		            } catch(InterruptedException ex) {  
		            	ex.printStackTrace();
		            } catch(Exception ex) {
		    			ex.printStackTrace();
		    			reset();
						isRunning = false;
		    		}
		        }
		    };
	
		    thread.start(); 
		} catch(Exception ex) {
			ex.printStackTrace();
			reset();
			isRunning = false;
		}
	}

	public synchronized boolean isRunning() {
		return isRunning;
	}
	
}
