package util;

public abstract class Tarefa {

	public int prioridade;
	
	public Tarefa(int prioridade) {
		this.prioridade = prioridade;
	}
	
	public abstract void script();
}
