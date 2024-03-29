package EfeitosParticulas;

import java.util.ArrayList;
import java.util.List;
import javax.media.opengl.GL;



public class Explosao {
	private int numeroParticula;	
	private double timer;
	private List<Particula>particulaDeExplosao;
	 public boolean dead = true;
	
	

	public  Explosao(double x, double y, double z, float size) {
		// TODO Auto-generated constructor stub
		particulaDeExplosao = new ArrayList<Particula>();
		numeroParticula = 50;
		timer = 1;
		
		for (int i = 0; i < numeroParticula; i++) {
			particulaDeExplosao.add(new Particula(x, y, z, size));
			
			
		}
		
	}
	
	public void SimulaSe(long diffTime){	
		
		if(particulaDeExplosao.size()==0){
			dead = false;
		}
		
		for (int i = 0; i < particulaDeExplosao.size(); i++) {
			
			if(particulaDeExplosao.get(i).life<0){
				particulaDeExplosao.remove(particulaDeExplosao.get(i));
			}
			particulaDeExplosao.get(i).SimulaSe((int)diffTime );
			
		}
		timer -= diffTime/1000.0f;	
		if(timer<0){
			dead = false;
			particulaDeExplosao.clear();
		}
	}
	public void DesenhaSe(GL gl){
		if(dead){
			for (int i = 0; i < particulaDeExplosao.size(); i++) {
				particulaDeExplosao.get(i).DesenhaSe(gl);
				
			}
		}
	}
	
	public void SetNumeroParticula(int quantParticula) {
		numeroParticula = quantParticula;
	}


}
