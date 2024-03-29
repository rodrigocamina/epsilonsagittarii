package gameobjects;



import javax.media.opengl.GL;

import obj.ObjModel;
import util.Util;
import frustum.FrustumV2;

public class Laser extends Weapon {
	
	private float life;

	
	
	public Laser(float x, float y, float z, float w, float h, float d,
			float vx, float vy, float vz, ObjModel model, int range, float damage, long cadence, ObjModel targetModel) {
		
		super(x, y, z, w, h, d, vx, vy, vz, model, range, damage, cadence);
//		target = new LaserTarget(0, 0, 0, 10, 10, 10, 0, 0, 0, targetModel);
		target = new LaserTarget(0, -5, 50, 10, 10, 10, 0, 0, 0, targetModel);
		
	}
	
	public Laser(float x, float y, float z, float w, float h, float d,
			float vx, float vy, float vz, ObjModel model,Target target, int range, float damage, long cadence) {
		super(x, y, z, w, h, d, vx, vy, vz, model, target, range, damage, cadence);
	
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void simulate(long diffTime) {
		super.simulate(diffTime);
		
		
	}

	public void draw(GL canvas, FrustumV2 camera) {
		if(model==null){
			
			 float X =position.x;
			 float Y =position.y;
			 float Z =position.z;
			
			canvas.glPushMatrix();
			{
				
				//frente
				canvas.glPushMatrix();
				canvas.glShadeModel(GL.GL_SMOOTH);						// Enables Smooth Shading
				canvas.glClearDepth(1.0f);							// Depth Buffer Setup
				canvas.glDisable(GL.GL_DEPTH_TEST);						// Disables Depth Testing
				canvas.glEnable(GL.GL_BLEND);							// Enable Blending
				canvas.glBlendFunc(GL.GL_SRC_ALPHA,GL.GL_ONE);					// Type Of Blending To Perform
				canvas.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT,GL.GL_NICEST);			// Really Nice Perspective Calculations
				canvas.glHint(GL.GL_POINT_SMOOTH_HINT,GL.GL_NICEST);					// Really Nice Point Smoothing
				
					
				
						float size = 2.0f;
						
//						canvas.glColor4f(this.red, this.green, this.blue, this.life);
						canvas.glColor4f(1.0f,1.0f, 1.0f, this.life);
						canvas.glBegin(GL.GL_TRIANGLE_STRIP);
							canvas.glTexCoord2d(1, 1); canvas.glVertex3f(X+size, Y+size, Z); //TOP Right
							canvas.glTexCoord2d(0, 1); canvas.glVertex3f(X-size, Y+size, Z); //TOP Left
							canvas.glTexCoord2d(1, 0); canvas.glVertex3f(X+size, Y-size, Z); //Botton Right
							canvas.glTexCoord2d(0, 0); canvas.glVertex3f(X-size, Y-size, Z); //Botton Left
						canvas.glEnd();	
						canvas.glBegin(GL.GL_TRIANGLE_STRIP);
							canvas.glTexCoord2d(1, 1); canvas.glVertex3f(X+size, Y, Z+size); //TOP Right
							canvas.glTexCoord2d(0, 1); canvas.glVertex3f(X-size, Y, Z+size); //TOP Left
							canvas.glTexCoord2d(1, 0); canvas.glVertex3f(X+size, Y, Z-size); //Botton Right
							canvas.glTexCoord2d(0, 0); canvas.glVertex3f(X-size, Y, Z-size); //Botton Left
						canvas.glEnd();	
						canvas.glBegin(GL.GL_TRIANGLE_STRIP);
							canvas.glTexCoord2d(1, 1); canvas.glVertex3f(X, Y+size, Z+size); //TOP Right
							canvas.glTexCoord2d(0, 1); canvas.glVertex3f(X, Y+size, Z-size); //TOP Left
							canvas.glTexCoord2d(1, 0); canvas.glVertex3f(X, Y-size, Z+size); //Botton Right
							canvas.glTexCoord2d(0, 0); canvas.glVertex3f(X, Y-size, Z-size); //Botton Left
						canvas.glEnd();	
			
					}
				canvas.glDisable(GL.GL_SMOOTH);
				canvas.glDisable(GL.GL_BLEND);
				canvas.glEnable(GL.GL_DEPTH_TEST);		
				canvas.glPopMatrix();
				canvas.glEnd();
			
			canvas.glPopMatrix();
		}else{
			canvas.glPushMatrix();
			{
				canvas.glTranslatef(position.x, position.y, position.z);
				Util.rotacionaGLViaVetores(canvas, frontV, rightV, upV);
				model.desenhase(canvas);
			}
			canvas.glPopMatrix();
		}
	}
	
	
}
