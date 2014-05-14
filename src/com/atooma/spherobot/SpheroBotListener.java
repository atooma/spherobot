package com.atooma.spherobot;

public interface SpheroBotListener {
	public void onConnected(SpheroBot sphero);
	public void onDisconnected(SpheroBot sphero);
	public void onConnectionFailed(SpheroBot sphero);
}
