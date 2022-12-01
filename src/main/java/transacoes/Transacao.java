package transacoes;

import java.util.ArrayList;
import java.util.List;

public class Transacao implements Runnable {
	
	private static ArrayList<Long> tempo = new ArrayList<Long>();
	
	private static long tempoTransacao;
	
	/*public Transacao(ArrayList<Long> tempo) {
		this.tempo = tempo;
	}*/
	
	public Transacao() {
		
	}

	public void run() {
		// TODO Auto-generated method stub

	}

	public static ArrayList<Long> getTempo() {
		return tempo;
	}

	public void setTempo(ArrayList<Long> tempo) {
		this.tempo = tempo;
	}
	
	public static void incrementTempo(long a) {
		tempo.add(a);
	}

	public static long getTempoTransacao() {
		return tempoTransacao;
	}

	public static void setTempoTransacao(long tempoTransacao) {
		Transacao.tempoTransacao = tempoTransacao;
	}
	
	
	

}
