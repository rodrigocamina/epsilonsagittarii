package gameobjects;

import obj.ObjModel;

public class Missile extends Weapon{

	public Missile(float x, float y, float z, float w, float h, float d,
			float vx, float vy, float vz, ObjModel model, int range, float damage, long cadence) {
		super(x, y, z, w, h, d, vx, vy, vz, model, range, damage, cadence);
		
	}

	public Missile(float x, float y, float z, float w, float h, float d,
			float vx, float vy, float vz, ObjModel model,Target target, int range, float damage, long cadence) {
		super(x, y, z, w, h, d, vx, vy, vz, model,target, range, damage, cadence);
	}
	
	
}
