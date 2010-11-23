package gameobjects;

import javax.media.opengl.GL;

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
				/*
				float width = size.x;
				float height = size.y;
				float depth = size.z;
				canvas.glPushMatrix();
				{
					
					//frente
					canvas.glBegin(GL.GL_QUADS);
					canvas.glVertex3f(position.x, position.y, position.z);
					canvas.glVertex3f(position.x+width, position.y, position.z);
					canvas.glVertex3f(position.x+width, position.y+height, position.z);
					canvas.glVertex3f(position.x, position.y+height, position.z);
					canvas.glEnd();
					//tras
					canvas.glBegin(GL.GL_QUADS);
					canvas.glVertex3f(position.x, position.y, position.z+depth);
					canvas.glVertex3f(position.x+width, position.y, position.z+depth);
					canvas.glVertex3f(position.x+width, position.y+height, position.z+depth);
					canvas.glVertex3f(position.x, position.y+height, position.z+depth);
					canvas.glEnd();
					//esquerda
					canvas.glBegin(GL.GL_QUADS);
					canvas.glVertex3f(position.x, position.y, position.z);
					canvas.glVertex3f(position.x, position.y, position.z+depth);
					canvas.glVertex3f(position.x, position.y+height, position.z+depth);
					canvas.glVertex3f(position.x, position.y+height, position.z);
					canvas.glEnd();
					//direita
					canvas.glBegin(GL.GL_QUADS);
					canvas.glVertex3f(position.x+width, position.y, position.z);
					canvas.glVertex3f(position.x+width, position.y, position.z+depth);
					canvas.glVertex3f(position.x+width, position.y+height, position.z+depth);
					canvas.glVertex3f(position.x+width, position.y+height, position.z);
					canvas.glEnd();


					canvas.glBegin(GL.GL_QUADS);
					canvas.glVertex3f(position.x, position.y, position.z);
					canvas.glVertex3f(position.x, position.y, position.z+depth);
					canvas.glVertex3f(position.x+width, position.y, position.z+depth);
					canvas.glVertex3f(position.x+width, position.y, position.z);
					canvas.glEnd();

					canvas.glBegin(GL.GL_QUADS);
					canvas.glVertex3f(position.x, position.y+height, position.z);
					canvas.glVertex3f(position.x, position.y+height, position.z+depth);
					canvas.glVertex3f(position.x+width, position.y+height, position.z+depth);
					canvas.glVertex3f(position.x+width, position.y+height, position.z);
					canvas.glEnd();
				}
				canvas.glPopMatrix();*/
				
				
				Util.rotacionaGLViaVetores(canvas, frontV, rightV, upV);
				canvas.glRotatef(0.001f, 0, 1, 0);
				canvas.glRotatef(90, 1, 0, 0);
				canvas.glScalef(3f, 3f, 3f);
				model.desenhase(canvas);
			}
			canvas.glPopMatrix();
	}
}
