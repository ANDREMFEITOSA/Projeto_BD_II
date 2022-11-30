package transacoes;

import java.util.ArrayList;
import java.util.List;

public class Transacao implements Runnable {
	
	private ArrayList<Long> tempo = new ArrayList<Long>();
	
	public Transacao(ArrayList<Long> tempo) {
		this.tempo = tempo;
	}

	public void run() {
		// TODO Auto-generated method stub

	}

	public ArrayList<Long> getTempo() {
		return tempo;
	}

	public void setTempo(ArrayList<Long> tempo) {
		this.tempo = tempo;
	}
	
	

}
