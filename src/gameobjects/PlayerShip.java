package gameobjects;

import frustum.FrustumV2;

import javax.media.opengl.GL;

import main.CanvasGame;
import matematcbase.Util;
import matematcbase.Vector3f;
import obj.ObjModel;

public class PlayerShip extends GameObj {

	long timerShot;
	Weapon weaponMain;
	Weapon weaponSub;
	boolean shooting = false;
	
	public PlayerShip(float x, float y, float z, float w, float h, float d, float vx, float vy, float vz, ObjModel model) {
		super(x, y, z, w, h, d, vx, vy, vz, model);
		weaponMain = new Laser(x, y, z, 0.01f, 0.01f, 2f, 60, 60, 60, null, 100, 1, 200);
	}
	
	@Override
	public void simulate(long diffTime) {
		float dX =frontV.x*speed.x*diffTime/1000.0f;
		float dY =frontV.y*speed.y*diffTime/1000.0f;
		float dZ =frontV.z*speed.z*diffTime/1000.0f;
		CanvasGame.X+=dX;
		CanvasGame.Y+=dY;
		CanvasGame.Z+=dZ;
		if(shooting){
			if(timerShot<=0){
				timerShot = weaponMain.cadence;
				Weapon w = new Weapon(CanvasGame.X+position.x, CanvasGame.Y+position.y, CanvasGame.Z+position.z, weaponMain.size.x, weaponMain.size.y, weaponMain.size.z, weaponMain.speed.x, weaponMain.speed.y, weaponMain.speed.z, weaponMain.model,weaponMain.target,weaponMain.range,weaponMain.damage,weaponMain.cadence);
				w.setRotation(frontV, rightV, upV);
				
				//adiciona pro canvas
				CanvasGame.shots.add(w);
			}else{
				timerShot-=diffTime;
			}
		}
	}
	@Override
	public void draw(GL canvas, FrustumV2 camera) {
		canvas.glPushMatrix();
		{
			canvas.glTranslatef(CanvasGame.X, CanvasGame.Y, CanvasGame.Z);
			canvas.glTranslatef(position.x, position.y, position.z);
			canvas.glScalef(0.01f, 0.01f, 0.01f);
			Util.rotacionaGLViaVetores(canvas, frontV, rightV, upV);
			canvas.glRotatef(90, 0, 0, 1);
			canvas.glRotatef(10, 1, 0, 0);
			model.desenhase(canvas);
		}
		canvas.glPopMatrix();
	}
	
	public void addSpeed(float increment){
		if(((speed.x<50||speed.y<50||speed.z<50)&&(increment>0))||((speed.x>-20||speed.y>-20||speed.z>-20)&&(increment<0))){
			speed.x += increment;
			speed.y += increment;
			speed.z += increment;
		}
		System.out.println("Speed "+speed);
	}
	
	public void setShooting(boolean shooting) {
		this.shooting = shooting;
	}
}