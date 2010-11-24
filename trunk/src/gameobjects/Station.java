package gameobjects;

import javax.media.opengl.GL;

import frustum.FrustumV2;
import obj.ObjModel;
import util.Util;

public class Station extends GameObj{

	public Station(float x, float y, float z, float w, float h, float d,
			float vx, float vy, float vz, ObjModel model) {
		super(x, y, z, w, h, d, vx, vy, vz, model);
	}

	@Override
	public void draw(GL canvas, FrustumV2 camera) {
		canvas.glPushMatrix();
		{
			canvas.glTranslatef(position.x, position.y, position.z);
			canvas.glScalef(0.1f, 0.1f, 0.1f);
			Util.rotacionaGLViaVetores(canvas, frontV, rightV, upV);
			model.desenhase(canvas);
		}
		canvas.glPopMatrix();
	}
	
}
