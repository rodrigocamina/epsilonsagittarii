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
	public void draw(GL canvas, FrustumV2 camera) {
		canvas.glPushMatrix();
		{
			canvas.glTranslatef(position.x+CanvasGame.X, position.y+CanvasGame.Y, position.z+CanvasGame.Z);
			Util.rotacionaGLViaVetores(canvas, frontV, rightV, upV);
			model.desenhase(canvas);
		}
		canvas.glPopMatrix();
	}
}
