package gameobjects;

import javax.media.opengl.GL;

import main.CanvasGame;

//import matematcbase.Util;
import frustum.FrustumV2;
import obj.ObjModel;
import util.Util;

public class LaserTarget extends Target{

	public LaserTarget(float x, float y, float z, float w, float h, float d,
			float vx, float vy, float vz, ObjModel model) {
		super(x, y, z, w, h, d, vx, vy, vz, model);
		// TODO Auto-generated constructor stub
	}

	public void draw(GL canvas, FrustumV2 camera) {
		
			canvas.glPushMatrix();
			{
				canvas.glTranslatef(position.x, position.y, position.z);
				Util.rotacionaGLViaVetores(canvas, frontV, rightV, upV);
//				canvas.glRotatef(0.001f, 0, 1, 0);
//				canvas.glRotatef(90, 1, 0, 0);
				canvas.glScalef(3f, 3f, 3f);
				CanvasGame.textures[CanvasGame.TEX_Mira].enable();
				CanvasGame.textures[CanvasGame.TEX_Mira].bind();
				model.desenhase(canvas);
				CanvasGame.textures[CanvasGame.TEX_Mira].disable();
			}
			canvas.glPopMatrix();
	}
}
