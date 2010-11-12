package gameobjects;

import obj.ObjModel;

public class Laser extends Weapon {

	
	
	public Laser(float x, float y, float z, float w, float h, float d,
			float vx, float vy, float vz, ObjModel model, int range, float damage, long cadence) {
		super(x, y, z, w, h, d, vx, vy, vz, model, range, damage, cadence);
		
	}
	
	public Laser(float x, float y, float z, float w, float h, float d,
			float vx, float vy, float vz, ObjModel model,Target target, int range, float damage, long cadence) {
		super(x, y, z, w, h, d, vx, vy, vz, model, target, range, damage, cadence);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void simulate(long diffTime) {
		super.simulate(diffTime);
		System.out.println("P "+position);
	}
	
	
}
