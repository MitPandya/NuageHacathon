package com.meet.NuageHackathon;

import java.io.IOException;

public interface MainServer {
	
	void runServer(int port) throws IOException;
	double dedicatedFunction(double in);

}
