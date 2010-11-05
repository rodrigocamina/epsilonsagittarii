package gameobjects;

import frustum.FrustumV2;

import javax.media.opengl.GL;

import main.CanvasGame;
import matematcbase.Util;
import obj.ObjModel;

public class Nave extends GameObj {

	public Nave(float x, float y, float z, float w, float h, float d, float vx, float vy, float vz, ObjModel model) {
		super(x, y, z, w, h, d, vx, vy, vz, model);
	}
	
	@Override
	public void simulate(long diffTime) {
		float dX =frontV.x*speed.x*diffTime/1000.0f;
		float dY =frontV.y*speed.y*diffTime/1000.0f;
		float dZ =frontV.z*speed.z*diffTime/1000.0f;
		CanvasGame.X+=dX;
		CanvasGame.Y+=dY;
		CanvasGame.Z+=dZ;
	}
	@Override
	public void draw(GL canvas, FrustumV2 camera) {
		canvas.glPushMatrix();
		{
			canvas.glTranslatef(position.x+CanvasGame.X, position.y+CanvasGame.Y, position.z+CanvasGame.Z);
			Util.rotacionaGLViaVetores(canvas, frontV, rightV, upV);
			canvas.glRotatef(90, 0, 0, 1);
			canvas.glRotatef(10, 1, 0, 0);
			canvas.glScalef(0.3f, 0.3f, 0.3f);
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
}
