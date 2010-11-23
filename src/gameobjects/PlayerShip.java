package gameobjects;

import frustum.FrustumV2;

import javax.media.opengl.GL;

import main.CanvasGame;
import matematcbase.Vector3f;
import obj.ObjModel;
import util.Util;

public class PlayerShip extends GameObj {

	long timerShot;
	Weapon weaponMain;
	Weapon weaponSub;
	boolean shooting = false;
	private int  indiceTextura =0;
	
	

	public PlayerShip(float x, float y, float z, float w, float h, float d, float vx, float vy, float vz, ObjModel model) {
		super(x, y, z, w, h, d, vx, vy, vz,  model);
//		System.out.println("!?");
		ObjModel target = new ObjModel();
		target.loadObj("/res/MiraLaser.obj");
		//System.out.println("!?!");
		weaponMain = new Laser(x, y, z, 0.2f,0.2f,2f, 20, 20, 20, null, 100, 1, 200, target);
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
				//aqui tenho que mexer 
//				Weapon w = new Weapon(CanvasGame.X+position.x, CanvasGame.Y+position.y, CanvasGame.Z+position.z, weaponMain.size.x, weaponMain.size.y, weaponMain.size.z, weaponMain.speed.x, weaponMain.speed.y, weaponMain.speed.z, weaponMain.model,weaponMain.target,weaponMain.range,weaponMain.damage,weaponMain.cadence);
				float velX = 0;
				float velY = 0;
				float velZ = 0;
				
				if(speed.x<0)
				{
					velX = weaponMain.speed.x;
				}else{
					velX = speed.x+weaponMain.speed.x;
				}
				if(speed.y<0)
				{
					velY = weaponMain.speed.y;
				}else{
					velY = speed.y+weaponMain.speed.y;
				}
				if(speed.z<0)
				{
					velZ = weaponMain.speed.z;
				}else{
					velZ = speed.z+weaponMain.speed.z;
				}
				
				Weapon w = new Weapon(CanvasGame.X+position.x+frontV.x*0.6f, CanvasGame.Y+position.y+frontV.y*0.6f, CanvasGame.Z+position.z+frontV.z*0.6f, weaponMain.size.x, weaponMain.size.y, weaponMain.size.z, velX, velY, velZ, weaponMain.model,weaponMain.target,weaponMain.range,weaponMain.damage,weaponMain.cadence);
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
			weaponMain.target.draw(canvas, camera);
			//TODO extra, talvez seja retirado
			if(CanvasGame.UP){
				canvas.glRotatef(10, 1, 0, 0);
			}else
			if(CanvasGame.DOWN){
				canvas.glRotatef(-10, 1, 0, 0);
			}
			if(CanvasGame.RIGHT){
				canvas.glRotatef(10, 0, 0, 1);
			}else
			if(CanvasGame.LEFT){
				canvas.glRotatef(-10, 0, 0, 1);
			}
			//TODO fim do extra
			CanvasGame.textures [indiceTextura].enable();
			CanvasGame.textures [indiceTextura].bind();
			model.desenhase(canvas);
			CanvasGame.textures [indiceTextura].disable();
		}
		canvas.glPopMatrix();
	}
	
	public void addSpeed(float increment){
		if(((speed.x<50||speed.y<50||speed.z<50)&&(increment>0))||((speed.x>-20||speed.y>-20||speed.z>-20)&&(increment<0))){
			speed.x += increment;
			speed.y += increment;
			speed.z += increment;
		}
//		System.out.println("Speed "+speed);
	}
	
	public void setShooting(boolean shooting) {
		this.shooting = shooting;
	}
	
	public void setIndiceTextura(int indiceTextura) {
		this.indiceTextura = indiceTextura;
		System.out.println("indice da textura da nave "+CanvasGame.textures[0]);
	}
	
	
}
