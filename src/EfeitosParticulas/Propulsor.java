package EfeitosParticulas;

import javax.media.opengl.GL;

import util.Util;

import main.CanvasGame;
import matematcbase.Vector3f;

import com.sun.opengl.util.texture.Texture;

public class Propulsor {
	
	Texture texturaPropulsor;
	Vector3f position;
	Vector3f frontV;
	Vector3f rightV;
	Vector3f upV;
	Vector3f speed;
	float size;
	float life;
	boolean dead;

	public Propulsor( Vector3f posicao, Texture texturaRastro, Vector3f speed, float size,Vector3f frontV, Vector3f rightV, Vector3f upV ) {
		// TODO Auto-generated constructor stub
		this.position = posicao;
		this.texturaPropulsor =  CanvasGame.textures[CanvasGame.TEX_FOGO];
		this.speed = speed;
		this.size = 0.01f;
		this.life = 1.0f;
		this.frontV = frontV;
		this.rightV = rightV;
		this.upV = upV;
		this.dead = false;
		System.out.println("rastro criado");
	}
	
	public void Draw(GL canvas){
		System.out.println("propulsor desenhado");
		canvas.glPushMatrix();		
		float X = getX();
		float Y = getY();
		float Z = getZ();
		
		
		canvas.glPushMatrix();
		

			//frente
			canvas.glPushMatrix();
				canvas.glEnable(canvas.GL_TEXTURE);
				Texture textura = getTextura();
				textura.enable();
				textura.bind();
	
				canvas.glShadeModel(canvas.GL_SMOOTH);						// Enables Smooth Shading
				canvas.glClearDepth(1.0f);							// Depth Buffer Setup
				canvas.glDisable(canvas.GL_DEPTH_TEST);						// Disables Depth Testing
				canvas.glEnable(canvas.GL_BLEND);							// Enable Blending
				canvas.glBlendFunc(canvas.GL_SRC_ALPHA,canvas.GL_ONE);					// Type Of Blending To Perform
				canvas.glHint(canvas.GL_PERSPECTIVE_CORRECTION_HINT,canvas.GL_4D_COLOR_TEXTURE);			// Really Nice Perspective Calculations
				canvas.glHint(canvas.GL_POINT_SMOOTH_HINT,canvas.GL_NICEST);					// Really Nice Point Smoothing
				
							
									
				canvas.glBegin(canvas.GL_QUADS);
					canvas.glTexCoord2d(1, 1); canvas.glVertex3f(X+size, Y+size, Z); //TOP Right
					canvas.glTexCoord2d(0, 1); canvas.glVertex3f(X-size, Y+size, Z); //TOP Left
					canvas.glTexCoord2d(1, 0); canvas.glVertex3f(X+size, Y-size, Z); //Botton Right
					canvas.glTexCoord2d(0, 0); canvas.glVertex3f(X-size, Y-size, Z); //Botton Left
				canvas.glEnd();	
				
				canvas.glBegin(canvas.GL_QUADS);
					canvas.glTexCoord2d(1, 1); canvas.glVertex3f(X+size, Y, Z+size); //TOP Right
					canvas.glTexCoord2d(0, 1); canvas.glVertex3f(X-size, Y, Z+size); //TOP Left
					canvas.glTexCoord2d(1, 0); canvas.glVertex3f(X+size, Y, Z-size); //Botton Right
					canvas.glTexCoord2d(0, 0); canvas.glVertex3f(X-size, Y, Z-size); //Botton Left
				canvas.glEnd();	
				
				canvas.glBegin(canvas.GL_QUADS);
					canvas.glTexCoord2d(1, 1); canvas.glVertex3f(X, Y+size, Z+size); //TOP Right
					canvas.glTexCoord2d(0, 1); canvas.glVertex3f(X, Y+size, Z-size); //TOP Left
					canvas.glTexCoord2d(1, 0); canvas.glVertex3f(X, Y-size, Z+size); //Botton Right
					canvas.glTexCoord2d(0, 0); canvas.glVertex3f(X, Y-size, Z-size); //Botton Left
				canvas.glEnd();
				//========================   ================================
				
				canvas.glBegin(canvas.GL_QUADS);
				canvas.glNormal3f(0, 0, -1);
				canvas.glTexCoord2d(1, 1); canvas.glVertex3f(X+size, Y+size, Z); //TOP Right
				canvas.glNormal3f(0, 0, -1);
				canvas.glTexCoord2d(0, 1); canvas.glVertex3f(X-size, Y+size, Z); //TOP Left
				canvas.glNormal3f(0, 0, -1);
				canvas.glTexCoord2d(1, 0); canvas.glVertex3f(X+size, Y-size, Z); //Botton Right
				canvas.glNormal3f(0, 0, -1);
				canvas.glTexCoord2d(0, 0); canvas.glVertex3f(X-size, Y-size, Z); //Botton Left
				canvas.glEnd();	
			
				canvas.glBegin(canvas.GL_QUADS);
				canvas.glNormal3f(0, -1, 0);
				canvas.glTexCoord2d(1, 1); canvas.glVertex3f(X+size, Y, Z+size); //TOP Right
				canvas.glNormal3f(0, -1, 0);
				canvas.glTexCoord2d(0, 1); canvas.glVertex3f(X-size, Y, Z+size); //TOP Left
				canvas.glNormal3f(0, -1, 0);
				canvas.glTexCoord2d(1, 0); canvas.glVertex3f(X+size, Y, Z-size); //Botton Right
				canvas.glNormal3f(0, -1, 0);
				canvas.glTexCoord2d(0, 0); canvas.glVertex3f(X-size, Y, Z-size); //Botton Left
				canvas.glEnd();	
			
				canvas.glBegin(canvas.GL_QUADS);
				canvas.glNormal3f(-1, 0, 0);
				canvas.glTexCoord2d(1, 1); canvas.glVertex3f(X, Y+size, Z+size); //TOP Right
				canvas.glNormal3f(-1, 0, 0);
				canvas.glTexCoord2d(0, 1); canvas.glVertex3f(X, Y+size, Z-size); //TOP Left
				canvas.glNormal3f(-1, 0, 0);
				canvas.glTexCoord2d(1, 0); canvas.glVertex3f(X, Y-size, Z+size); //Botton Right
				canvas.glNormal3f(-1, 0, 0);
				canvas.glTexCoord2d(0, 0); canvas.glVertex3f(X, Y-size, Z-size); //Botton Left
				canvas.glEnd();
			
					
	
				canvas.glDisable(canvas.GL_SMOOTH);
				canvas.glDisable(canvas.GL_BLEND);
				canvas.glEnable(canvas.GL_DEPTH_TEST);		
	
				textura.disable();//					
				canvas.glDisable(canvas.GL_TEXTURE);					
				canvas.glPopMatrix();
			canvas.glEnd();
			
			
		canvas.glPopMatrix();
	}
	
	public void simulate(int diffTime){
		
		
		
		
		this.size -=diffTime/500000.0f;
		if(size<0){
			System.out.println("size "+size);
			size =0.01f;
		}
		System.out.println("life "+life);
		System.out.println("size "+size);
		
		
		
	}
	public Texture getTextura() {
		return texturaPropulsor;
	}

	public void setTextura(Texture textura) {
		this.texturaPropulsor = textura;
	}

	public Vector3f getPosicao() {
		return position;
	}

	public void setPosicao(Vector3f posicao) {
		this.position = posicao;
	}

	public Vector3f getSpeed() {
		return speed;
	}

	public void setSpeed(Vector3f speed) {
		this.speed = speed;
	}

	public float getSize() {
		return size;
	}

	public void setSize(float size) {
		this.size = size;
	}

	public float getLife() {
		return life;
	}

	public void setLife(float life) {
		this.life = life;
	}
	public float getX(){
		return position.x;
	}
	public float getY(){
		return position.y;
	}
	public float getZ(){
		return position.z;
	}

}
