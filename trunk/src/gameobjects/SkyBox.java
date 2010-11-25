package gameobjects;


import java.io.File;
import java.io.IOException;

import javax.media.opengl.GL;
import javax.media.opengl.GLException;

import main.CanvasGame;
import matematcbase.Vector2f;
import matematcbase.Vector3f;

import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureCoords;
import com.sun.opengl.util.texture.TextureIO;

public class SkyBox {

	public SkyBox() {
		
	}
	
	protected void DesenhaSe(GL canvas) {
		//---------------------------------------------------------------------------------------------
		//top
		
		float far = (float)(CanvasGame.FAR*0.7f);
		canvas.glPushMatrix();
			CanvasGame.textures [CanvasGame.TEX_SKYBOX_UP].enable();
			CanvasGame.textures [CanvasGame.TEX_SKYBOX_UP].bind();
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
			CanvasGame.textures [CanvasGame.TEX_SKYBOX_UP].disable();
			
	
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
			CanvasGame.textures [CanvasGame.TEX_SKYBOX_RIGT].enable();
			CanvasGame.textures [CanvasGame.TEX_SKYBOX_RIGT].bind();
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
			CanvasGame.textures [CanvasGame.TEX_SKYBOX_LEFT].disable();
	
	
			// SKYBOX ringht
			CanvasGame.textures [CanvasGame.TEX_SKYBOX_DOWN].enable();
			CanvasGame.textures [CanvasGame.TEX_SKYBOX_DOWN].bind();
			canvas.glBegin(GL.GL_QUADS);
			{
				canvas.glNormal3f(1, 0, 0);
				canvas.glTexCoord2d(0,1);
				//canvas.glTexCoord2f(coordsSkyOUEST.left(), coordsSkyOUEST.bottom());
				canvas.glVertex3f(-far, -far, -far);
	
				canvas.glNormal3f(1, 0, 0);
				canvas.glTexCoord2d(1,1);
				//canvas.glTexCoord2f(coordsSkyOUEST.right(), coordsSkyOUEST.bottom());
				canvas.glVertex3f(-far, far, -far);
	
				canvas.glNormal3f(1, 0, 0);
				canvas.glTexCoord2d(1,0);
				//canvas.glTexCoord2f(coordsSkyOUEST.right(), coordsSkyOUEST.top());
				canvas.glVertex3f(-far, far, far);
	
				canvas.glNormal3f(1, 0, 0);
				canvas.glTexCoord2d(0,0);
				//canvas.glTexCoord2f(coordsSkyOUEST.left(), coordsSkyOUEST.top());
				canvas.glVertex3f(-far, -far, far);
			}
			canvas.glEnd();
			CanvasGame.textures [CanvasGame.TEX_SKYBOX_DOWN].disable();
			
			//front
			CanvasGame.textures [CanvasGame.TEX_SKYBOX_DOWN].enable();
			CanvasGame.textures [CanvasGame.TEX_SKYBOX_DOWN].bind();
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
			
			//back
			CanvasGame.textures [CanvasGame.TEX_SKYBOX_DOWN].enable();
			CanvasGame.textures [CanvasGame.TEX_SKYBOX_DOWN].bind();
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

		canvas.glPopMatrix();


		//---------------------------------------------------------------------------------------------
			

	}
}
