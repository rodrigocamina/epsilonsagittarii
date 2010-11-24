package frustum;
import gameobjects.GameObj;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import matematcbase.Vector3f;

import com.sun.opengl.util.texture.Texture;


public class Esfera {
	double X, Y, Z;
	double Raio;
	double velX, velY, velZ;
	boolean colisao;

	private GLU glu = new GLU();
	GLUquadric quad;

	double CorR = 1;
	double CorG = 1;
	double CorB = 1;
	int ponto = 30;
	private Texture texturaEsfera;
	private double life;
	GameObj father;
	
	public Esfera(double x, double y, double z, double raio, double vel, GameObj father) {
		this.X = x;
		this.Y = y;
		this.Z = z;
		this.Raio = raio;
//		this.velX = vel+(Math.random()*10);
//		this.velY = vel+(Math.random()*vel);
//		this.velZ = vel+(Math.random()*15);		
		quad = glu.gluNewQuadric();
		glu.gluQuadricNormals(quad, GLU.GLU_SMOOTH);
		this.CorR =  (Math.random()*1);
		this.CorR =  (Math.random()*1);
		this.CorR =  (Math.random()*1);
		colisao = false;
		this.life = 100;
		this.father = father;
	}

	@SuppressWarnings("static-access")
	public void DesenhaSe(GL gl){
		if(life>0){
			if(colisao){
				gl.glShadeModel(gl.GL_SMOOTH);						// Enables Smooth Shading
				gl.glClearDepth(1.0f);							// Depth Buffer Setup
				gl.glDisable(gl.GL_DEPTH_TEST);						// Disables Depth Testing
				gl.glEnable(gl.GL_BLEND);							// Enable Blending
				gl.glBlendFunc(gl.GL_SRC_ALPHA,gl.GL_ONE_MINUS_CONSTANT_ALPHA);					// Type Of Blending To Perform
//				gl.glHint(gl.GL_PERSPECTIVE_CORRECTION_HINT,gl.GL_NICEST);			// Really Nice Perspective Calculations
//				gl.glHint(gl.GL_POINT_SMOOTH_HINT,gl.GL_NICEST);	
//				
				//gl.glEnable(gl.GL_TEXTURE_2D);
				gl.glTexGend(gl.GL_S, gl.GL_TEXTURE_GEN_MODE, gl.GL_SPHERE_MAP);
				Texture textura = texturaEsfera;
				textura.enable();
				textura.bind();
				
				
				gl.glPushMatrix();
					gl.glPushMatrix();
						//gl.glEnable(gl.GL_COLOR);
						gl.glBegin(gl.GL_LINES);
						
						//linas de profundidades
						gl.glVertex3d(-30, -30, -30);
						gl.glVertex3d(-30, -30, 30);
				
						gl.glVertex3d(30, -30, -30);
						gl.glVertex3d(30, -30, 30);
				
						gl.glVertex3d(-30, 30, -30);
						gl.glVertex3d(-30, 30, 30);
				
						gl.glVertex3d(30, 30, -30);
						gl.glVertex3d(30, 30, 30);
						// fim linas de profundidades
				
						//lina horizontal
						gl.glVertex3d(30, 30, 30);
						gl.glVertex3d(-30, 30, 30);
				
						gl.glVertex3d(30, 30, -30);
						gl.glVertex3d(-30, 30, -30);
				
						gl.glVertex3d(30, -30, 30);
						gl.glVertex3d(-30,- 30, 30);
				
						gl.glVertex3d(30, -30, -30);
						gl.glVertex3d(-30, -30, -30);					
						//end lina horizontal
				
						//lina vertical
						gl.glVertex3d(-30, -30, 30);
						gl.glVertex3d(-30, 30, 30);
				
						gl.glVertex3d(-30, 30, -30);
						gl.glVertex3d(-30, -30, -30);
				
						gl.glVertex3d(30, -30, 30);
						gl.glVertex3d(30,30, 30);
				
						gl.glVertex3d(30, 30, -30);
						gl.glVertex3d(30, -30, -30);					
						//end lina vertical
						gl.glEnd();
					gl.glPopMatrix();
						//gl.glColor3d(CorR, CorG, CorB);
						gl.glTranslated(X, Y, Z);
						gl.glColor4f(0.0f,0.0f, 1.0f, 1.0f);
						glu.gluSphere(quad, Raio, 20, 20);
				gl.glPopMatrix();
				gl.glDisable(gl.GL_SMOOTH);
				gl.glDisable(gl.GL_BLEND);
				gl.glEnable(gl.GL_DEPTH_TEST);
				textura.disable();
				//gl.glDisable(gl.GL_TEXTURE);
				//gl.glDisable(gl.GL_TEXTURE_2D);
				colisao = false;
			}
		}
	}
	
	public void Simulase(int DiffTime){
		
						
	}


	public boolean ColideEsfera(Esfera esf){
		double difX = esf.X - this.X;
		double difY = esf.Y - this.Y;
		double difZ = esf.Z - this.Z;

		double RaioQuadrado = ((this.Raio+esf.Raio)*(this.Raio+esf.Raio));
		double somaDiferenca = (difX * difX)+(difY * difY)+(difZ * difZ);

		if(RaioQuadrado>somaDiferenca){
			return true;
		}else{
			return false;
		}
	}
	public void SetPosition(Vector3f position){
		this.X = position.x;
		this.Y = position.y;
		this.Z = position.z;
	}
	
	public void EfeitoColisaoEscudo(boolean estado, Texture texturaTiro, double damage){
		this.life -= damage;
		this.colisao = estado;
		this.texturaEsfera = texturaTiro;
		father.takeDamage(0);
	}

	public double getLife() {
		return life;
	}
	
}
