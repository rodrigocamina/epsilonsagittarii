
package EfeitosParticulas;

import java.util.Random;

import javax.media.opengl.GL;
import javax.swing.text.html.HTMLDocument.HTMLReader.SpecialAction;

import main.CanvasGame;
import matematcbase.Vector3f;

import com.sun.opengl.util.texture.Texture;


public class Particula {
	
	public float slowdown = 2.0f;
	public float xspeed;
	public float yspeed;
	public float zspeed;
	public float zoom = -0.0f;
	public float angX;
	public float angY;
	public float angZ;
	public float sizeX;
	public float sizeY;
	public float sizeZ;

//	public int loop;
	public int col;
	public int delay;

	boolean	active = false;					// Active (Yes/No)
	public float life;					// Particle Life
	public float fade;					// Fade Speed
	public float red;
	public float green;
	public float blue;
//posicao
	public float x;
	public float y;
	public float z;						
//	direcao
	public float xi;
	public float yi;
	public float zi;		
	//gravidade
	public float xg;
	public float yg;
	public float zg;
	Random rand= new Random();

	public float [][]colors = new float [][]{	
			{1.0f,0.5f,0.5f},{1.0f,0.75f,0.5f},{1.0f,1.0f,0.5f},{0.75f,1.0f,0.5f},
			{0.5f,1.0f,0.5f},{0.5f,1.0f,0.75f},{0.5f,1.0f,1.0f},{0.5f,0.75f,1.0f},
			{0.5f,0.5f,1.0f},{0.75f,0.5f,1.0f},{1.0f,0.5f,1.0f},{1.0f,0.5f,0.75f}
	};

	public Particula(double X, double Y, double Z) {
		this.x = (float) X;
		this.y = (float) Y;
		this.z = (float) Z;
		this.active = true;
		
		this.xspeed = 0.5f;
		this.yspeed = 0.5f;
		this.zspeed = 0.5f;
		
		angX = (float) ((rand.nextDouble()*360)-180);
		angY = (float)  ((rand.nextDouble()*360)-180);
		angZ = (float)  ((rand.nextDouble()*360)-180);
		this.life = 1.0f;
		sizeX = 0.01f;
		sizeY = 0.01f;
		sizeZ = 0.01f;
		active = true;
	}

	public void SimulaSe(int i, int difftime){		
			col++;		
		if(col>11){
			col=0;
		}		
		this.fade = (float)(Math.random()*100)/1000.0f+0.003f;	
		
		this.xi = (float)((Math.random()*50)-26.0f)*10.0f;
		this.yi = (float)((Math.random()*50)-25.0f)*10.0f;
		this.zi = (float)((Math.random()*50)-25.0f)*10.0f;
		
		this.x +=Math.cos(angX)*xspeed* difftime/1000.f; 
		this.y +=Math.sin(angY)*yspeed* difftime/1000.0f; 
		this.z +=Math.cos(angZ)*zspeed* difftime/1000.0f; 
		
		this.life -= difftime/1000.0f;
		SetSize(life);
	}
	@SuppressWarnings("static-access")
	public void DesenhaSe(GL gl){
		// Enable Texture Mapping
		System.out.println(" explosao desenhada");
		gl.glPushMatrix();
		gl.glShadeModel(gl.GL_SMOOTH);						// Enables Smooth Shading
		gl.glClearDepth(1.0f);							// Depth Buffer Setup
		gl.glDisable(gl.GL_DEPTH_TEST);						// Disables Depth Testing
		gl.glEnable(gl.GL_BLEND);							// Enable Blending
		gl.glBlendFunc(gl.GL_SRC_ALPHA,gl.GL_ONE);					// Type Of Blending To Perform
		gl.glHint(gl.GL_PERSPECTIVE_CORRECTION_HINT,gl.GL_NICEST);			// Really Nice Perspective Calculations
		gl.glHint(gl.GL_POINT_SMOOTH_HINT,gl.GL_NICEST);					// Really Nice Point Smoothing
		
			
			if(this.active){
				
				float X = this.x;
				float Y = this.y;
				float Z = this.z;
				
				Texture texture = CanvasGame.textures[CanvasGame.TEX_FOGO];
				texture.enable();
				texture.bind();
				
				
				
				gl.glBegin(gl.GL_TRIANGLE_STRIP);
					gl.glTexCoord2d(1, 1); gl.glVertex3f(X+sizeX, Y+sizeY, Z); //TOP Right
					gl.glTexCoord2d(0, 1); gl.glVertex3f(X-sizeX, Y+sizeY, Z); //TOP Left
					gl.glTexCoord2d(1, 0); gl.glVertex3f(X+sizeX, Y-sizeY, Z); //Botton Right
					gl.glTexCoord2d(0, 0); gl.glVertex3f(X-sizeX, Y-sizeY, Z); //Botton Left
				gl.glEnd();	
				
				gl.glBegin(gl.GL_TRIANGLE_STRIP);
					gl.glTexCoord2d(1, 1); gl.glVertex3f(X+sizeX, Y, Z+sizeZ); //TOP Right
					gl.glTexCoord2d(0, 1); gl.glVertex3f(X-sizeX, Y, Z+sizeZ); //TOP Left
					gl.glTexCoord2d(1, 0); gl.glVertex3f(X+sizeX, Y, Z-sizeZ); //Botton Right
					gl.glTexCoord2d(0, 0); gl.glVertex3f(X-sizeX, Y, Z-sizeZ); //Botton Left
				gl.glEnd();	
				
				gl.glBegin(gl.GL_TRIANGLE_STRIP);
					gl.glTexCoord2d(1, 1); gl.glVertex3f(X, Y+sizeY, Z+sizeZ); //TOP Right
					gl.glTexCoord2d(0, 1); gl.glVertex3f(X, Y+sizeY, Z-sizeZ); //TOP Left
					gl.glTexCoord2d(1, 0); gl.glVertex3f(X, Y-sizeY, Z+sizeZ); //Botton Right
					gl.glTexCoord2d(0, 0); gl.glVertex3f(X, Y-sizeY, Z-sizeZ); //Botton Left
				gl.glEnd();	
				
				
				//====================
				
//				gl.glPushMatrix();
//					gl.glRotatef(life*90, 0.0f, 0.0f, 1.0f);
//					gl.glBegin(gl.GL_TRIANGLE_STRIP);
//						gl.glTexCoord2d(1, 1); gl.glVertex3f(X+sizeX, Y+sizeY, Z); //TOP Right
//						gl.glTexCoord2d(0, 1); gl.glVertex3f(X-sizeX, Y+sizeY, Z); //TOP Left
//						gl.glTexCoord2d(1, 0); gl.glVertex3f(X+sizeX, Y-sizeY, Z); //Botton Right
//						gl.glTexCoord2d(0, 0); gl.glVertex3f(X-sizeX, Y-sizeY, Z); //Botton Left
//					gl.glEnd();	
//				gl.glPopMatrix();
//				
//				
//				gl.glPushMatrix();
//					gl.glRotatef(life*90, 0.0f, 1.0f, 0.0f);
//					gl.glBegin(gl.GL_TRIANGLE_STRIP);
//						gl.glTexCoord2d(1, 1); gl.glVertex3f(X+sizeX, Y, Z+sizeZ); //TOP Right
//						gl.glTexCoord2d(0, 1); gl.glVertex3f(X-sizeX, Y, Z+sizeZ); //TOP Left
//						gl.glTexCoord2d(1, 0); gl.glVertex3f(X+sizeX, Y, Z-sizeZ); //Botton Right
//						gl.glTexCoord2d(0, 0); gl.glVertex3f(X-sizeX, Y, Z-sizeZ); //Botton Left
//					gl.glEnd();	
//				gl.glPopMatrix();
//				
//				
//				gl.glPushMatrix();
//					gl.glRotatef(life*90, 1.0f, 0.0f, 0.0f);
//					gl.glBegin(gl.GL_TRIANGLE_STRIP);
//						gl.glTexCoord2d(1, 1); gl.glVertex3f(X, Y+sizeY, Z+sizeZ); //TOP Right
//						gl.glTexCoord2d(0, 1); gl.glVertex3f(X, Y+sizeY, Z-sizeZ); //TOP Left
//						gl.glTexCoord2d(1, 0); gl.glVertex3f(X, Y-sizeY, Z+sizeZ); //Botton Right
//						gl.glTexCoord2d(0, 0); gl.glVertex3f(X, Y-sizeY, Z-sizeZ); //Botton Left
//					gl.glEnd();	
//				gl.glPopMatrix();
				texture.disable();
			}
			
		gl.glDisable(GL.GL_SMOOTH);
		gl.glDisable(gl.GL_BLEND);
		gl.glEnable(gl.GL_DEPTH_TEST);		
		gl.glPopMatrix();
	}
	
	public void SetPosition(Vector3f v){
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;		
	}
	public void SetSize(float size){
		this.sizeX = size;
		this.sizeY = size;
		this.sizeZ = size;
	}

}
