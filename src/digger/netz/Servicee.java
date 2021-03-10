package digger.netz;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import digger.gui.controller.Controller;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class Servicee extends Service<int[]>{
	
	int id, answer;
	DataInputStream fromServer;
	DataOutputStream toServer;
	
	public Servicee() {}

	@Override
	protected Task<int[]> createTask() {
		
		return new Task<int[]>() {

			@Override
			protected int[] call() throws Exception {
				int a [] = {0,0,0};
				a[0] = Controller.getFromServer().readInt();
				a[1] = (int) Controller.getFromServer().readDouble();
				a[2] = Controller.getFromServer().readInt();
				return a;
			}
		};
	}

}
