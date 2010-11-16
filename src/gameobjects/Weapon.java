package gameobjects;

import javax.media.opengl.GL;

import main.CanvasGame;
import matematcbase.Util;

import frustum.FrustumV2;
import obj.ObjModel;

public class Weapon extends GameObj {

	Target target;
	GameObj shooter;
	
	long cadence;//frequencia que dispara os tiros
	int range;
	float rangeWalked = 0;
	float damage;
	boolean dead = false;
	
	public Weapon(float x, float y, float z, float w, float h, float d, float vx,
			float vy, float vz, ObjModel model, int range, float damage, long cadence) {
		super(x, y, z, w, h, d, vx, vy, vz, model);
		this.range = range;
		this.damage = damage;
		this.cadence = cadence;
	}
	
	public Weapon(float x, float y, float z, float w, float h, float d, float vx,
			float vy, float vz, ObjModel model,Target target, int range, float damage, long cadence) {
		super(x, y, z, w, h, d, vx, vy, vz, model);
		this.target = target;
		this.range = range;
		this.cadence = cadence;
		this.damage = damage;
	}

	@Override
	public void draw(GL canvas, FrustumV2 camera) {
		canvas.glPushMatrix();
		{
			canvas.glTranslatef(position.x, position.y, position.z);
			float width = size.x;
			float height = size.y;
			float depth = size.z;
			Util.rotacionaGLViaVetores(canvas, frontV, rightV, upV);
			//frente
			canvas.glBegin(GL.GL_QUADS);
			canvas.glVertex3f(-width, -height, -depth);
			canvas.glVertex3f(width, -height, -depth);
			canvas.glVertex3f(width,height, -depth);
			canvas.glVertex3f(-width,height, -depth);
			canvas.glEnd();
			//tras
			canvas.glBegin(GL.GL_QUADS);
			canvas.glVertex3f(-width, -height, depth);
			canvas.glVertex3f(width, -height, depth);
			canvas.glVertex3f(width,height, depth);
			canvas.glVertex3f(-width,height, depth);
			canvas.glEnd();
			//esquerda
			canvas.glBegin(GL.GL_QUADS);
			canvas.glVertex3f(-width, -height, -depth);
			canvas.glVertex3f(-width, -height, depth);
			canvas.glVertex3f(-width,height, depth);
			canvas.glVertex3f(-width,height, -depth);
			canvas.glEnd();
			//direita
			canvas.glBegin(GL.GL_QUADS);
			canvas.glVertex3f(width, -height, -depth);
			canvas.glVertex3f(width, -height, depth);
			canvas.glVertex3f(width,height, depth);
			canvas.glVertex3f(width,height, -depth);
			canvas.glEnd();


			canvas.glBegin(GL.GL_QUADS);
			canvas.glVertex3f(-width, -height, -depth);
			canvas.glVertex3f(-width, -height, depth);
			canvas.glVertex3f(width, -height, depth);
			canvas.glVertex3f(width, -height, -depth);
			canvas.glEnd();

			canvas.glBegin(GL.GL_QUADS);
			canvas.glVertex3f(-width,height, -depth);
			canvas.glVertex3f(-width,height, depth);
			canvas.glVertex3f(width,height, depth);
			canvas.glVertex3f(width,height, -depth);
			canvas.glEnd();
		}
		canvas.glPopMatrix();
	}
	@Override
	public void simulate(long diffTime) {
		float dX = frontV.x*speed.x*diffTime/1000.0f;
		float dY = frontV.y*speed.y*diffTime/1000.0f;
		float dZ = frontV.z*speed.z*diffTime/1000.0f;
		position.x+=dX;
		position.y+=dY;
		position.z+=dZ;
		if(dX<0){
			dX=-dX;
		}
		if(dY<0){
			dY=-dY;
		}
		if(dZ<0){
			dZ=-dZ;
		}
		System.out.println(dX+" / "+dY+" / "+dZ);
		rangeWalked += dX+dY+dZ;
		if(rangeWalked>range){
			dead = true;
		}
		System.out.println(rangeWalked+"/"+range);
	}
	public boolean isDead() {
		return dead;
	}
}
