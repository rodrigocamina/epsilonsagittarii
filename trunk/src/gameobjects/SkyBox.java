package gameobjects;


import javax.media.opengl.GL;

import main.CanvasGame;

public class SkyBox {

	public SkyBox() {
		
	}
	
	protected void DesenhaSe(GL canvas) {
		//---------------------------------------------------------------------------------------------
		//top
		
		float far = (float)(CanvasGame.FAR*0.55f);
		

		canvas.glPushMatrix();
		
//		canvas.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_CLAMP_TO_EDGE);
//		canvas.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_CLAMP_TO_EDGE);
//		canvas.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
//		canvas.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);
		
			CanvasGame.textures [CanvasGame.TEX_SKYBOX_TOP].enable();
			
			CanvasGame.textures[CanvasGame.TEX_SKYBOX_TOP].setTexParameteri(GL.GL_TEXTURE_WRAP_S, GL.GL_CLAMP_TO_EDGE);
			CanvasGame.textures[CanvasGame.TEX_SKYBOX_TOP].setTexParameteri(GL.GL_TEXTURE_WRAP_T, GL.GL_CLAMP_TO_EDGE);
			CanvasGame.textures[CanvasGame.TEX_SKYBOX_TOP].setTexParameteri(GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
			CanvasGame.textures[CanvasGame.TEX_SKYBOX_TOP].setTexParameteri(GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);
			
			CanvasGame.textures [CanvasGame.TEX_SKYBOX_TOP].bind();
			canvas.glBegin(GL.GL_QUADS);
			{
				
				canvas.glNormal3f(0, -1, 0);
				canvas.glTexCoord2d(0,1);
				//canvas.glTexCoord2f(coordsSkyNORD.left(), coordsSkyNORD.bottom());
				canvas.glVertex3f(-far, far, -far);
	
				canvas.glNormal3f(0, -1, 0);
				canvas.glTexCoord2d(1,1);
				//canvas.glTexCoord2f(coordsSkyNORD.right(), coordsSkyNORD.bottom());
				canvas.glVertex3f(far, far, -far);
	
				canvas.glNormal3f(0, -1, 0);
				canvas.glTexCoord2d(1,0);
				//canvas.glTexCoord2f(coordsSkyNORD.right(), coordsSkyNORD.top());
				canvas.glVertex3f(far, far, far);
	
				canvas.glNormal3f(0, -1, 0);
				canvas.glTexCoord2d(0,0);
				//canvas.glTexCoord2f(coordsSkyNORD.left(), coordsSkyNORD.top());
				canvas.glVertex3f(-far, far, far);
	
			}
			canvas.glEnd();
			CanvasGame.textures [CanvasGame.TEX_SKYBOX_TOP].disable();
			
	
			// SKYBOX left
			CanvasGame.textures [CanvasGame.TEX_SKYBOX_LEFT].enable();
			CanvasGame.textures [CanvasGame.TEX_SKYBOX_LEFT].bind();
			canvas.glBegin(GL.GL_QUADS);
			{
				canvas.glNormal3f(-1, 0, 0);
				canvas.glTexCoord2d(0,1);
				//canvas.glTexCoord2f(coordsSkyEST.left(), coordsSkyEST.bottom());
				canvas.glVertex3f(far, far, -far);
	
				canvas.glNormal3f(-1, 0, 0);
				canvas.glTexCoord2d(1,1);
				//canvas.glTexCoord2f(coordsSkyEST.right(), coordsSkyEST.bottom());
				canvas.glVertex3f(far, -far, -far);
	
				canvas.glNormal3f(-1, 0, 0);
				canvas.glTexCoord2d(1,0);
				//canvas.glTexCoord2f(coordsSkyEST.right(), coordsSkyEST.top());
				canvas.glVertex3f(far, -far, far);
	
				canvas.glNormal3f(-1, 0, 0);
				canvas.glTexCoord2d(0,0);
				//canvas.glTexCoord2f(coordsSkyEST.left(), coordsSkyEST.top());
				canvas.glVertex3f(far, far, far);
			}
			canvas.glEnd();
			CanvasGame.textures [CanvasGame.TEX_SKYBOX_LEFT].disable();
	
	
	//		// SKYBOX down
			CanvasGame.textures [CanvasGame.TEX_SKYBOX_DOWN].enable();
			CanvasGame.textures [CanvasGame.TEX_SKYBOX_DOWN].bind();
			canvas.glBegin(GL.GL_QUADS);
			{
				canvas.glNormal3f(0, 1, 0);
				canvas.glTexCoord2d(0,1);
				//canvas.glTexCoord2f(coordsSkySUD.left(), coordsSkySUD.bottom());
				canvas.glVertex3f(far, -far, -far);
	
				canvas.glNormal3f(0, 1, 0);
				canvas.glTexCoord2d(1,1);
				//canvas.glTexCoord2f(coordsSkySUD.right(), coordsSkySUD.bottom());
				canvas.glVertex3f(-far, -far, -far);
	
				canvas.glNormal3f(0, 1, 0);
				canvas.glTexCoord2d(1,0);
				//canvas.glTexCoord2f(coordsSkySUD.right(), coordsSkySUD.top());
				canvas.glVertex3f(-far, -far, far);
	
				canvas.glNormal3f(0, 1, 0);
				canvas.glTexCoord2d(0,0);
				//canvas.glTexCoord2f(coordsSkyEST.left(), coordsSkyEST.top());
				canvas.glVertex3f(far, -far, far);
			}
			canvas.glEnd();
			CanvasGame.textures [CanvasGame.TEX_SKYBOX_DOWN].disable();
	
	
			// SKYBOX ringht
			CanvasGame.textures [CanvasGame.TEX_SKYBOX_RIGT].enable();
			CanvasGame.textures [CanvasGame.TEX_SKYBOX_RIGT].bind();
			canvas.glBegin(GL.GL_QUADS);
			{
				canvas.glNormal3f(1, 0, 0);
				canvas.glTexCoord2d(0,1);
				//canvas.glTexCoord2f(coordsSkyOUEST.left(), coordsSkyOUEST.bottom());
				canvas.glVertex3f(-far, -far, far);
				
	
				canvas.glNormal3f(1, 0, 0);
				canvas.glTexCoord2d(1,1);
				//canvas.glTexCoord2f(coordsSkyOUEST.right(), coordsSkyOUEST.bottom());
				canvas.glVertex3f(-far, -far, -far);

	
				canvas.glNormal3f(1, 0, 0);
				canvas.glTexCoord2d(1,0);
				//canvas.glTexCoord2f(coordsSkyOUEST.right(), coordsSkyOUEST.top());
				canvas.glVertex3f(-far, far, -far);
				
	
				canvas.glNormal3f(1, 0, 0);
				canvas.glTexCoord2d(0,0);
				//canvas.glTexCoord2f(coordsSkyOUEST.left(), coordsSkyOUEST.top());
				canvas.glVertex3f(-far, far, far);
				
			}
			canvas.glEnd();
			CanvasGame.textures [CanvasGame.TEX_SKYBOX_RIGT].disable();
			
			//front
			CanvasGame.textures [CanvasGame.TEX_SKYBOX_FRONT].enable();
			CanvasGame.textures [CanvasGame.TEX_SKYBOX_FRONT].bind();
			canvas.glBegin(GL.GL_QUADS);
			{
				canvas.glNormal3f(0, 0, -1);
				canvas.glTexCoord2d(0,1);
				//GL_Arg.glTexCoord2f(coordsSkyUP.left(), coordsSkyUP.bottom());
				canvas.glVertex3f(-far, far, far);

				canvas.glNormal3f(0, 0, -1);
				canvas.glTexCoord2d(1,1);
				//canvas.glTexCoord2f(coordsSkyUP.right(), coordsSkyUP.bottom());
				canvas.glVertex3f(far, far, far);

				canvas.glNormal3f(0, 0, -1);
				canvas.glTexCoord2d(1,0);
				//canvas.glTexCoord2f(coordsSkyUP.right(), coordsSkyUP.top());
				canvas.glVertex3f(far, -far, far);

				canvas.glNormal3f(0, 0, -1);
				canvas.glTexCoord2d(0,0);
				//canvas.glTexCoord2f(coordsSkyUP.left(), coordsSkyUP.top());
				canvas.glVertex3f(-far, -far, far);
			}
			canvas.glEnd();
			CanvasGame.textures [CanvasGame.TEX_SKYBOX_FRONT].disable();
			
			//back
			CanvasGame.textures [CanvasGame.TEX_SKYBOX_BACK].enable();
			CanvasGame.textures [CanvasGame.TEX_SKYBOX_BACK].bind();
			canvas.glBegin(GL.GL_QUADS);
			{
				canvas.glNormal3f(0, 0, 1);
				canvas.glTexCoord2d(0,1);
				//GL_Arg.glTexCoord2f(coordsSkyUP.left(), coordsSkyUP.bottom());
				canvas.glVertex3f(far, -far, -far);

				canvas.glNormal3f(0, 0, -1);
				canvas.glTexCoord2d(1,1);
				//canvas.glTexCoord2f(coordsSkyUP.right(), coordsSkyUP.bottom());
				canvas.glVertex3f(-far, -far, -far);

				canvas.glNormal3f(0, 0, 1);
				canvas.glTexCoord2d(1,0);
				//canvas.glTexCoord2f(coordsSkyUP.right(), coordsSkyUP.top());
				canvas.glVertex3f(-far, far, -far);

				canvas.glNormal3f(0, 0, 1);
				canvas.glTexCoord2d(0,0);
				//canvas.glTexCoord2f(coordsSkyUP.left(), coordsSkyUP.top());
				canvas.glVertex3f(far, far, -far);
			}
			canvas.glEnd();
			CanvasGame.textures [CanvasGame.TEX_SKYBOX_BACK].disable();

		canvas.glPopMatrix();


		//---------------------------------------------------------------------------------------------
			

	}
}
