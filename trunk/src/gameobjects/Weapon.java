package gameobjects;

import java.util.ArrayList;
import java.util.Random;

import javax.media.opengl.GL;
import EfeitosParticulas.Explosao;
import com.sun.opengl.util.texture.Texture;


import main.CanvasGame;
import matematcbase.Vector3f;

import frustum.FrustumV2;
import obj.ObjModel;
import util.Util;

public class Weapon extends GameObj {

	Target target;
	GameObj shooter;
	Texture textureTiro = null;
	private boolean explode = false;
	@SuppressWarnings("unchecked")
	public ArrayList<Explosao>explosao;
	public int numeroDeParticulas;



	long cadence;//frequencia que dispara os tiros
	int range;
	float rangeWalked = 0;
	float damage;
	boolean dead = false;
	float life =1.0f;
	public float red;
	public float green;
	public float blue;
	
	Random rand= new Random();

	public float [][]colors = new float [][]{	
			{1.0f,0.5f,0.5f},{1.0f,0.75f,0.5f},{1.0f,1.0f,0.5f},{0.75f,1.0f,0.5f},
			{0.5f,1.0f,0.5f},{0.5f,1.0f,0.75f},{0.5f,1.0f,1.0f},{0.5f,0.75f,1.0f},
			{0.5f,0.5f,1.0f},{0.75f,0.5f,1.0f},{1.0f,0.5f,1.0f},{1.0f,0.5f,0.75f}
	};

	@SuppressWarnings("unchecked")
	public Weapon(float x, float y, float z, float w, float h, float d, float vx,
			float vy, float vz, ObjModel model, int range, float damage, long cadence) {
		super(x, y, z, w, h, d, vx, vy, vz, model);
		this.range = range;
		this.damage = damage;
		this.cadence = cadence;
		explosao  = new ArrayList<Explosao>();
		numeroDeParticulas = 5;
		for (int i = 0; i < numeroDeParticulas; i++) {
			explosao.add(new Explosao(this.getX(),this.getY(),this.getZ(), numeroDeParticulas));
			
		}
		this.red = this.colors[rand.nextInt(12)][0];
		this.green = colors[rand.nextInt(12)][1];
		this.blue = colors[rand.nextInt(12)][2];
		textureTiro = CanvasGame.textures[rand.nextInt(4)+1];
		
	}

	@SuppressWarnings("unchecked")
	public Weapon(float x, float y, float z, float w, float h, float d, float vx,
			float vy, float vz, ObjModel model,Target target, int range, float damage, long cadence) {
		super(x, y, z, w, h, d, vx, vy, vz, model);
		this.target = target;
		this.range = range;
		this.cadence = cadence;
		this.damage = damage;
		explosao  = new ArrayList<Explosao>();
		numeroDeParticulas = 5;
		for (int i = 0; i < numeroDeParticulas; i++) {
			explosao.add(new Explosao(this.getX(),this.getY(),this.getZ(), numeroDeParticulas));
			
		}
		this.red = this.colors[rand.nextInt(12)][0];
		this.green = colors[rand.nextInt(12)][1];
		this.blue = colors[rand.nextInt(12)][2];
		textureTiro = CanvasGame.textures[rand.nextInt(5)+1];
	}

//		@Override
@SuppressWarnings("static-access")
	//		public void draw(GL canvas, FrustumV2 camera) {
//			canvas.glPushMatrix();
//			{
//				canvas.glTranslatef(position.x, position.y, position.z);
//				float width = size.x;
//				float height = size.y;
//				float depth = size.z;
//				Util.rotacionaGLViaVetores(canvas, frontV, rightV, upV);
//				
//				
//								// Really Nice Point Smoothing
////	
//				//frente
//				canvas.glBegin(GL.GL_QUADS);
//				canvas.glVertex3f(-width, -height, -depth);
//				canvas.glVertex3f(width, -height, -depth);
//				canvas.glVertex3f(width,height, -depth);
//				canvas.glVertex3f(-width,height, -depth);
//				canvas.glEnd();
//				//tras
//				canvas.glBegin(GL.GL_QUADS);
//				canvas.glVertex3f(-width, -height, depth);
//				canvas.glVertex3f(width, -height, depth);
//				canvas.glVertex3f(width,height, depth);
//				canvas.glVertex3f(-width,height, depth);
//				canvas.glEnd();
//				//esquerda
//				canvas.glBegin(GL.GL_QUADS);
//				canvas.glVertex3f(-width, -height, -depth);
//				canvas.glVertex3f(-width, -height, depth);
//				canvas.glVertex3f(-width,height, depth);
//				canvas.glVertex3f(-width,height, -depth);
//				canvas.glEnd();
//				//direita
//				canvas.glBegin(GL.GL_QUADS);
//				canvas.glVertex3f(width, -height, -depth);
//				canvas.glVertex3f(width, -height, depth);
//				canvas.glVertex3f(width,height, depth);
//				canvas.glVertex3f(width,height, -depth);
//				canvas.glEnd();
//	
//	
//				canvas.glBegin(GL.GL_QUADS);
//				canvas.glVertex3f(-width, -height, -depth);
//				canvas.glVertex3f(-width, -height, depth);
//				canvas.glVertex3f(width, -height, depth);
//				canvas.glVertex3f(width, -height, -depth);
//				canvas.glEnd();
//	
//				canvas.glBegin(GL.GL_QUADS);
//				canvas.glVertex3f(-width,height, -depth);
//				canvas.glVertex3f(-width,height, depth);
//				canvas.glVertex3f(width,height, depth);
//				canvas.glVertex3f(width,height, -depth);
//				canvas.glEnd();
//				
//				canvas.glEnd();			
//				
//			}
//			canvas.glPopMatrix();
//		}
	@Override
	public void draw(GL canvas, FrustumV2 camera) {
		
		
		if(model==null){			
			float X = getX();
			float Y = getY();
			float Z = getZ();
			
			canvas.glPushMatrix();

	
				//frente
				canvas.glPushMatrix();
					canvas.glEnable(canvas.GL_TEXTURE);
					Texture textura = textureTiro;
					textura.enable();
					textura.bind();
		
					canvas.glShadeModel(canvas.GL_SMOOTH);						// Enables Smooth Shading
					canvas.glClearDepth(1.0f);							// Depth Buffer Setup
					canvas.glDisable(canvas.GL_DEPTH_TEST);						// Disables Depth Testing
					canvas.glEnable(canvas.GL_BLEND);							// Enable Blending
					canvas.glBlendFunc(canvas.GL_SRC_ALPHA,canvas.GL_ONE);					// Type Of Blending To Perform
					canvas.glHint(canvas.GL_PERSPECTIVE_CORRECTION_HINT,canvas.GL_NICEST);			// Really Nice Perspective Calculations
					canvas.glHint(canvas.GL_POINT_SMOOTH_HINT,canvas.GL_NICEST);					// Really Nice Point Smoothing
		
		
		
					float size = 0.08f;
		
				
					canvas.glColor4f(red,green,blue , this.life);
//					canvas.glEnable(canvas.GL_COLOR_MATERIAL);
					
					canvas.glBegin(canvas.GL_TRIANGLE_STRIP);
						canvas.glTexCoord2d(1, 1); canvas.glVertex3f(X+size, Y+size, Z); //TOP Right
						canvas.glTexCoord2d(0, 1); canvas.glVertex3f(X-size, Y+size, Z); //TOP Left
						canvas.glTexCoord2d(1, 0); canvas.glVertex3f(X+size, Y-size, Z); //Botton Right
						canvas.glTexCoord2d(0, 0); canvas.glVertex3f(X-size, Y-size, Z); //Botton Left
					canvas.glEnd();	
					
					canvas.glBegin(canvas.GL_TRIANGLE_STRIP);
						canvas.glTexCoord2d(1, 1); canvas.glVertex3f(X+size, Y, Z+size); //TOP Right
						canvas.glTexCoord2d(0, 1); canvas.glVertex3f(X-size, Y, Z+size); //TOP Left
						canvas.glTexCoord2d(1, 0); canvas.glVertex3f(X+size, Y, Z-size); //Botton Right
						canvas.glTexCoord2d(0, 0); canvas.glVertex3f(X-size, Y, Z-size); //Botton Left
					canvas.glEnd();	
					
					canvas.glBegin(canvas.GL_TRIANGLE_STRIP);
						canvas.glTexCoord2d(1, 1); canvas.glVertex3f(X, Y+size, Z+size); //TOP Right
						canvas.glTexCoord2d(0, 1); canvas.glVertex3f(X, Y+size, Z-size); //TOP Left
						canvas.glTexCoord2d(1, 0); canvas.glVertex3f(X, Y-size, Z+size); //Botton Right
						canvas.glTexCoord2d(0, 0); canvas.glVertex3f(X, Y-size, Z-size); //Botton Left
					canvas.glEnd();
					
					canvas.glBegin(canvas.GL_TRIANGLE_STRIP);
						canvas.glTexCoord2d(1, 1); canvas.glVertex3f(X+size, Y+size, Z); //TOP Right
						canvas.glTexCoord2d(0, 1); canvas.glVertex3f(X-size, Y+size, Z); //TOP Left
						canvas.glTexCoord2d(1, 0); canvas.glVertex3f(X+size, Y-size, Z); //Botton Right
						canvas.glTexCoord2d(0, 0); canvas.glVertex3f(X-size, Y-size, Z); //Botton Left
					canvas.glEnd();
					//parte 2
					canvas.glBegin(canvas.GL_TRIANGLE_STRIP);
					canvas.glNormal3f(0.0f, 0.0f, -1.0f);
					canvas.glTexCoord2d(1, 1); canvas.glVertex3f(X+size, Y+size, Z); //TOP Right
					canvas.glTexCoord2d(0, 1); canvas.glVertex3f(X-size, Y+size, Z); //TOP Left
					canvas.glTexCoord2d(1, 0); canvas.glVertex3f(X+size, Y-size, Z); //Botton Right
					canvas.glTexCoord2d(0, 0); canvas.glVertex3f(X-size, Y-size, Z); //Botton Left
				canvas.glEnd();	
				
				canvas.glBegin(canvas.GL_TRIANGLE_STRIP);
				canvas.glNormal3f(0.0f, -1.0f, 0.0f);
					canvas.glTexCoord2d(1, 1); canvas.glVertex3f(X+size, Y, Z+size); //TOP Right
					canvas.glTexCoord2d(0, 1); canvas.glVertex3f(X-size, Y, Z+size); //TOP Left
					canvas.glTexCoord2d(1, 0); canvas.glVertex3f(X+size, Y, Z-size); //Botton Right
					canvas.glTexCoord2d(0, 0); canvas.glVertex3f(X-size, Y, Z-size); //Botton Left
				canvas.glEnd();	
				
				canvas.glBegin(canvas.GL_TRIANGLE_STRIP);
					canvas.glNormal3f(-1.0f, 0.0f, 0.0f);
					canvas.glTexCoord2d(1, 1); canvas.glVertex3f(X, Y+size, Z+size); //TOP Right
					canvas.glTexCoord2d(0, 1); canvas.glVertex3f(X, Y+size, Z-size); //TOP Left
					canvas.glTexCoord2d(1, 0); canvas.glVertex3f(X, Y-size, Z+size); //Botton Right
					canvas.glTexCoord2d(0, 0); canvas.glVertex3f(X, Y-size, Z-size); //Botton Left
				canvas.glEnd();
				
				canvas.glBegin(canvas.GL_TRIANGLE_STRIP);
					canvas.glNormal3f(0.0f, 0.0f, -1.0f);
					canvas.glTexCoord2d(1, 1); canvas.glVertex3f(X+size, Y+size, Z); //TOP Right
					canvas.glTexCoord2d(0, 1); canvas.glVertex3f(X-size, Y+size, Z); //TOP Left
					canvas.glTexCoord2d(1, 0); canvas.glVertex3f(X+size, Y-size, Z); //Botton Right
					canvas.glTexCoord2d(0, 0); canvas.glVertex3f(X-size, Y-size, Z); //Botton Left
				canvas.glEnd();	
		
					//segundo desenho
					
//					canvas.glBegin(canvas.GL_TRIANGLE_STRIP);
//						canvas.glTexCoord2d(0, 0); canvas.glVertex3f(X+size, Y+size, Z); //TOP Right
//						canvas.glTexCoord2d(1, 0); canvas.glVertex3f(X-size, Y+size, Z); //TOP Left
//						canvas.glTexCoord2d(0, 1); canvas.glVertex3f(X+size, Y-size, Z); //Botton Right
//						canvas.glTexCoord2d(1, 1); canvas.glVertex3f(X-size, Y-size, Z); //Botton Left
//					canvas.glEnd();	
//					
//					canvas.glBegin(canvas.GL_TRIANGLE_STRIP);
//						canvas.glTexCoord2d(0, 0); canvas.glVertex3f(X+size, Y, Z+size); //TOP Right
//						canvas.glTexCoord2d(1, 0); canvas.glVertex3f(X-size, Y, Z+size); //TOP Left
//						canvas.glTexCoord2d(0, 1); canvas.glVertex3f(X+size, Y, Z-size); //Botton Right
//						canvas.glTexCoord2d(1, 1); canvas.glVertex3f(X-size, Y, Z-size); //Botton Left
//					canvas.glEnd();
//					
//					canvas.glBegin(canvas.GL_TRIANGLE_STRIP);
//						canvas.glTexCoord2d(0, 0);canvas.glVertex3f(X, Y+size, Z+size); //TOP Right
//						canvas.glTexCoord2d(1, 0);canvas.glVertex3f(X, Y+size, Z-size); //TOP Left
//						canvas.glTexCoord2d(0, 1);canvas.glVertex3f(X, Y-size, Z+size); //Botton Right
//						canvas.glTexCoord2d(1, 1);canvas.glVertex3f(X, Y-size, Z-size); //Botton Left
//					canvas.glEnd();	
//					
//					canvas.glBegin(canvas.GL_TRIANGLE_STRIP);
//						canvas.glTexCoord2d(0, 0); canvas.glVertex3f(X+size, Y+size, Z); //TOP Right
//						canvas.glTexCoord2d(1, 0); canvas.glVertex3f(X-size, Y+size, Z); //TOP Left
//						canvas.glTexCoord2d(0, 1); canvas.glVertex3f(X+size, Y-size, Z); //Botton Right
//						canvas.glTexCoord2d(1, 1); canvas.glVertex3f(X-size, Y-size, Z); //Botton Left
//					canvas.glEnd();			
		
					canvas.glDisable(canvas.GL_SMOOTH);
					canvas.glDisable(canvas.GL_BLEND);
					canvas.glEnable(canvas.GL_DEPTH_TEST);		
		
					textura.disable();
//					canvas.glDisable(canvas.GL_COLOR_MATERIAL);
					canvas.glDisable(canvas.GL_TEXTURE);
					
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
	
	@Override
	public void simulate(long diffTime) {

		float dX =frontV.x*speed.x*diffTime/1000.0f;
		float dY =frontV.y*speed.y*diffTime/1000.0f;
		float dZ =frontV.z*speed.z*diffTime/1000.0f;
		
		
		
		position.x+=dX;
		position.y+=dY;
		position.z+=dZ;
		
		if(dX<0){
			dX=-dX;
		}
		if(dY<0){
			dY=-dY;
		}
		if(dZ<0){
			dZ=-dZ;
		}
		
//		System.out.println(dX+" / "+dY+" / "+dZ);
		rangeWalked += dX+dY+dZ;
		if(rangeWalked>range){
			explode = true;
			System.out.println("Explodiu aquiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii");
			for (int i = 0; i < explosao.size(); i++) {
				explosao.get(i).SimulaSe(i, (int)diffTime);
				Vector3f v = new Vector3f(getX(), getY(), getZ());
				explosao.get(i).SetPosition(v);
				
			}
			dead = true;			
			
		}
		//		System.out.println(rangeWalked+"/"+range);
	}
	
	
	public boolean isDead() {
		return dead;
	}

	public void setTextureTiro(Texture textureTiro) {
		this.textureTiro = textureTiro;
	}

}
