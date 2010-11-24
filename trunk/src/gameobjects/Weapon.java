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

	private static final ObjModel PlayerShip = null;
	Target target;
	GameObj shooter;
	Texture textureTiro = null;		
	long cadence;//frequencia que dispara os tiros
	int range;
	float rangeWalked = 0;
	float damage;
	boolean dead = false;
	boolean colidiuObjeto = false;
	public Explosao explosao; 
	Random rand= new Random();
	boolean explode = true;
	EnemyShip paiEnemyShip;
	PlayerShip paiPlayerShip;

		
	public Weapon(float x, float y, float z, float w, float h, float d, float vx,
			float vy, float vz, ObjModel model, int range, float damage, long cadence) {
		super(x, y, z, w, h, d, vx, vy, vz, model);
		this.range = range;
		this.damage = damage;
		this.cadence = cadence;				
		textureTiro = CanvasGame.textures[rand.nextInt(5)+1];
		System.out.println("inimigo");
		
	}

	
	public Weapon(float x, float y, float z, float w, float h, float d, float vx,
			float vy, float vz, ObjModel model,Target target, int range, float damage, long cadence) {
		super(x, y, z, w, h, d, vx, vy, vz, model);
		this.target = target;
		this.range = range;
		this.cadence = cadence;
		this.damage = damage;
		textureTiro = CanvasGame.textures[rand.nextInt(5)+1];		
	}

	@SuppressWarnings("static-access")
	@Override
	public void draw(GL canvas, FrustumV2 camera) {		
		if(colidiuObjeto){
			explosao.DesenhaSe(canvas);
		}else{
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
								
			
						canvas.glDisable(canvas.GL_SMOOTH);
						canvas.glDisable(canvas.GL_BLEND);
						canvas.glEnable(canvas.GL_DEPTH_TEST);		
			
						textura.disable();//					
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
		
	}
	
	@Override
	public void simulate(long diffTime) {
		float dX =frontV.x*speed.x*diffTime/1000.0f;
		float dY =frontV.y*speed.y*diffTime/1000.0f;
		float dZ =frontV.z*speed.z*diffTime/1000.0f;
		
		if(!colidiuObjeto){	
			
			position.x+=dX;
			position.y+=dY;
			position.z+=dZ;
			
			if(paiPlayerShip == CanvasGame.nave){
				for (int i = 0; i < CanvasGame.enemySheeps.size(); i++) {
					EnemyShip inimigo = CanvasGame.enemySheeps.get(i);
					if(ColideLaser(inimigo)){	
						colidiuObjeto = true;			
						explosao = new Explosao(this.getX(), this.getY(), this.getZ());
						if(inimigo.escudo.life>0){
							inimigo.escudo.EfeitoColisaoEscudo(true, textureTiro, damage);
						}else{
							CanvasGame.enemySheeps.get(i).life -= damage;
						}
					}
				}
			}else{
				
				if(ColideLaserP()){
					colidiuObjeto = true;			
					explosao = new Explosao(this.getX(), this.getY(), this.getZ());
					if(CanvasGame.nave.escudo.life>0){
						CanvasGame.nave.escudo.EfeitoColisaoEscudo(true, textureTiro, damage);
					}else{
						CanvasGame.nave.life -= damage;
					}
				}
			}			
		}else{
			if(explosao.dead){
				explosao.SimulaSe(diffTime);
			}
			else{
				this.dead= true;
			}
		}
		
		if(dX<0){
			dX=-dX;
		}
		if(dY<0){
			dY=-dY;
		}
		if(dZ<0){
			dZ=-dZ;
		}		

		rangeWalked += dX+dY+dZ;

		if((rangeWalked>range) && colidiuObjeto==false){
			this.dead = true;			
		}
		

	}
	
	
	public EnemyShip getPaiEnemyShip() {
		return paiEnemyShip;
	}


	public void setPaiEnemyShip(EnemyShip paiEnemyShip) {
		this.paiEnemyShip = paiEnemyShip;
	}


	public PlayerShip getPaiPlayerShip() {
		return paiPlayerShip;
	}


	public void setPaiPlayerShip(PlayerShip paiPlayerShip) {
		this.paiPlayerShip = paiPlayerShip;
	}


	public boolean isDead() {
		return dead;
	}

	public void setTextureTiro(Texture textureTiro) {
		this.textureTiro = textureTiro;
	}
	
	public boolean ColideLaser(GameObj inimigo){
		double difX = inimigo.getX() - this.getX();
		double difY = inimigo.getY() - this.getY();
		double difZ = inimigo.getZ() - this.getZ();
		//System.out.println("inimigo.radius"+inimigo.radius);
		double RaioQuadrado = inimigo.radius*1+inimigo.radius*1;
		double somaDiferenca = (difX * difX)+(difY * difY)+(difZ * difZ);

		if(RaioQuadrado>somaDiferenca){
			return true;
		}else{
			return false;
		}
	}

	public boolean ColideLaserP(){
		GameObj inimigo = CanvasGame.nave;
		double difX = inimigo.getX()+CanvasGame.X - this.getX();
		double difY = inimigo.getY()+CanvasGame.Y - this.getY();
		double difZ = inimigo.getZ()+CanvasGame.Z - this.getZ();
		//System.out.println("inimigo.radius"+inimigo.radius);
		double RaioQuadrado = inimigo.radius+inimigo.radius;
		double somaDiferenca = (difX * difX)+(difY * difY)+(difZ * difZ);

		if(RaioQuadrado>somaDiferenca){
			return true;
		}else{
			return false;
		}
	}
}
