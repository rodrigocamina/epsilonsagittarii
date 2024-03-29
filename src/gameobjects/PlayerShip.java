package gameobjects;

import frustum.Esfera;
import frustum.FrustumV2;

import javax.media.opengl.GL;

import EfeitosParticulas.Propulsor;

import main.CanvasGame;
import matematcbase.Vector3f;
import obj.ObjModel;
import util.Util;

public class PlayerShip extends GameObj {

	long timerShot;
	Weapon weaponMain;
	Weapon weaponSub;
	boolean shooting = false;
	private int  indiceTextura =0;
	Esfera escudo;
	SkyBox skybox;
	Propulsor propulsor;
	
	int shotState = 0;
	int shotType = 4;
	long timerLaser = 0;
	int energy = 1;
	

	public PlayerShip(float x, float y, float z, float w, float h, float d, float vx, float vy, float vz, ObjModel model) {
		super(x, y, z, w, h, d, vx, vy, vz,  model);
//		System.out.println("!?");
		ObjModel target = new ObjModel();
		target.loadObj("/res/Mira.obj");
		//System.out.println("!?!");
		this.radius = 1.0f;
		weaponMain = new Laser(x, y, z, 0.2f,0.2f,2f, 20, 20, 20, null, 500, 1, 1000, target);
		weaponMain.setPaiPlayerShip(this);
		escudo = new Esfera(x, y,z,0.1f, 0.0f,this);
		skybox = new SkyBox();
		propulsor = new Propulsor(position, CanvasGame.textures[CanvasGame.TEX_FOGO], speed,0.08f, frontV, rightV, upV);
	}
	
	public Weapon getWeaponMain() {
		return weaponMain;
	}
	
	@Override
	public void simulate(long diffTime) {
		
		float dX =frontV.x*speed.x*diffTime/1000.0f;
		float dY =frontV.y*speed.y*diffTime/1000.0f;
		float dZ =frontV.z*speed.z*diffTime/1000.0f;
		CanvasGame.X+=dX;
		CanvasGame.Y+=dY;
		CanvasGame.Z+=dZ;
		Vector3f posicao = new Vector3f(CanvasGame.X + position.x,CanvasGame.Y+position.y,CanvasGame.Z+position.z );
		escudo.SetPosition(posicao);
		escudo.Simulase((int)diffTime);
		Vector3f posicaoPropulsor = new Vector3f(CanvasGame.X + position.x-frontV.x*0.3f,CanvasGame.Y+position.y-frontV.y*0.3f,CanvasGame.Z+position.z-frontV.z*0.3f );
		propulsor.setPosicao(posicaoPropulsor);
		propulsor.simulate((int)diffTime);
		
		if(shotType==4){

			if(shotState==0){
				if(shooting){
					shotState = 1;
					timerLaser = 1;
					timerShot = weaponMain.cadence/5;
					//aqui tenho que mexer 
				}
			}else if(shotState==1){
				timerLaser-=diffTime;
				if(timerLaser<0){
					energy--;
					timerLaser+=10;
					float velX = 0;
					float velY = 0;
					float velZ = 0;
					
					if(speed.x<0)
					{
						velX = weaponMain.speed.x;
					}else{
						velX = speed.x+weaponMain.speed.x;
					}
					if(speed.y<0)
					{
						velY = weaponMain.speed.y;
					}else{
						velY = speed.y+weaponMain.speed.y;
					}
					if(speed.z<0)
					{
						velZ = weaponMain.speed.z;
					}else{
						velZ = speed.z+weaponMain.speed.z;
					}
					
					Weapon w = new Weapon(CanvasGame.X+position.x+frontV.x*0.6f, CanvasGame.Y+position.y+frontV.y*0.6f, CanvasGame.Z+position.z+frontV.z*0.6f, weaponMain.size.x, weaponMain.size.y, weaponMain.size.z, velX, velY, velZ, weaponMain.model,weaponMain.target,weaponMain.range,weaponMain.damage,weaponMain.cadence);
					w.setPaiPlayerShip(this);
					w.setTextureTiro(weaponMain.textureTiro);
					w.setRotation(frontV, rightV, upV);
					CanvasGame.shots.add(w);

					rotate(AXIS_X, 2);
					w = new Weapon(CanvasGame.X+position.x+frontV.x*0.6f, CanvasGame.Y+position.y+frontV.y*0.6f, CanvasGame.Z+position.z+frontV.z*0.6f, weaponMain.size.x, weaponMain.size.y, weaponMain.size.z, velX, velY, velZ, weaponMain.model,weaponMain.target,weaponMain.range,weaponMain.damage,weaponMain.cadence);
					w.setPaiPlayerShip(this);
					w.setTextureTiro(weaponMain.textureTiro);
					w.setRotation(frontV, rightV, upV);
					CanvasGame.shots.add(w);
					
					rotate(AXIS_X, -4);
					w = new Weapon(CanvasGame.X+position.x+frontV.x*0.6f, CanvasGame.Y+position.y+frontV.y*0.6f, CanvasGame.Z+position.z+frontV.z*0.6f, weaponMain.size.x, weaponMain.size.y, weaponMain.size.z, velX, velY, velZ, weaponMain.model,weaponMain.target,weaponMain.range,weaponMain.damage,weaponMain.cadence);
					w.setPaiPlayerShip(this);
					w.setTextureTiro(weaponMain.textureTiro);
					w.setRotation(frontV, rightV, upV);
					CanvasGame.shots.add(w);
					rotate(AXIS_X, 2);

					rotate(AXIS_Y, 2);
					w = new Weapon(CanvasGame.X+position.x+frontV.x*0.6f, CanvasGame.Y+position.y+frontV.y*0.6f, CanvasGame.Z+position.z+frontV.z*0.6f, weaponMain.size.x, weaponMain.size.y, weaponMain.size.z, velX, velY, velZ, weaponMain.model,weaponMain.target,weaponMain.range,weaponMain.damage,weaponMain.cadence);
					w.setPaiPlayerShip(this);
					w.setTextureTiro(weaponMain.textureTiro);
					w.setRotation(frontV, rightV, upV);
					CanvasGame.shots.add(w);
					
					rotate(AXIS_Y, -4);
					w = new Weapon(CanvasGame.X+position.x+frontV.x*0.6f, CanvasGame.Y+position.y+frontV.y*0.6f, CanvasGame.Z+position.z+frontV.z*0.6f, weaponMain.size.x, weaponMain.size.y, weaponMain.size.z, velX, velY, velZ, weaponMain.model,weaponMain.target,weaponMain.range,weaponMain.damage,weaponMain.cadence);
					w.setPaiPlayerShip(this);
					w.setTextureTiro(weaponMain.textureTiro);
					w.setRotation(frontV, rightV, upV);
					CanvasGame.shots.add(w);
					rotate(AXIS_Y, 2);

					
					if(energy==0){
						shotState=2;
					}
				}
				
			}else if(shotState==2){
				timerShot-=diffTime;
				if(timerShot<0){
					shotState=0;
					energy=3;
				}
			}
		}else if(shotType==2){
			if(shotState==0){
				if(shooting){
					shotState = 1;
					timerLaser = 1;
					timerShot = weaponMain.cadence/5;
					//aqui tenho que mexer 
				}
			}else if(shotState==1){
				timerLaser-=diffTime;
				if(timerLaser<0){
					energy--;
					timerLaser+=10;
					float velX = 0;
					float velY = 0;
					float velZ = 0;
					
					if(speed.x<0)
					{
						velX = weaponMain.speed.x;
					}else{
						velX = speed.x+weaponMain.speed.x;
					}
					if(speed.y<0)
					{
						velY = weaponMain.speed.y;
					}else{
						velY = speed.y+weaponMain.speed.y;
					}
					if(speed.z<0)
					{
						velZ = weaponMain.speed.z;
					}else{
						velZ = speed.z+weaponMain.speed.z;
					}
					
					Weapon w = new Weapon(CanvasGame.X+position.x+frontV.x*0.6f, CanvasGame.Y+position.y+frontV.y*0.6f, CanvasGame.Z+position.z+frontV.z*0.6f, weaponMain.size.x, weaponMain.size.y, weaponMain.size.z, velX, velY, velZ, weaponMain.model,weaponMain.target,weaponMain.range,weaponMain.damage,weaponMain.cadence);
					w.setPaiPlayerShip(this);
					w.setTextureTiro(weaponMain.textureTiro);
					w.setRotation(frontV, rightV, upV);
					CanvasGame.shots.add(w);
					
					rotate(AXIS_X, 2);
					w = new Weapon(CanvasGame.X+position.x+frontV.x*0.6f, CanvasGame.Y+position.y+frontV.y*0.6f, CanvasGame.Z+position.z+frontV.z*0.6f, weaponMain.size.x, weaponMain.size.y, weaponMain.size.z, velX, velY, velZ, weaponMain.model,weaponMain.target,weaponMain.range,weaponMain.damage,weaponMain.cadence);
					w.setPaiPlayerShip(this);
					w.setTextureTiro(weaponMain.textureTiro);
					w.setRotation(frontV, rightV, upV);
					CanvasGame.shots.add(w);
					
					rotate(AXIS_X, -4);
					w = new Weapon(CanvasGame.X+position.x+frontV.x*0.6f, CanvasGame.Y+position.y+frontV.y*0.6f, CanvasGame.Z+position.z+frontV.z*0.6f, weaponMain.size.x, weaponMain.size.y, weaponMain.size.z, velX, velY, velZ, weaponMain.model,weaponMain.target,weaponMain.range,weaponMain.damage,weaponMain.cadence);
					w.setPaiPlayerShip(this);
					w.setTextureTiro(weaponMain.textureTiro);
					w.setRotation(frontV, rightV, upV);
					CanvasGame.shots.add(w);
					rotate(AXIS_X, 2);

					
					if(energy==0){
						shotState=2;
					}
				}
				
			}else if(shotState==2){
				timerShot-=diffTime;
				if(timerShot<0){
					shotState=0;
					energy=3;
				}
			}
		}else if(shotType==3){
			if(shotState==0){
				if(shooting){
					shotState = 1;
					timerLaser = 1;
					timerShot = weaponMain.cadence/5;
					//aqui tenho que mexer 
				}
			}else if(shotState==1){
				timerLaser-=diffTime;
				if(timerLaser<0){
					energy--;
					timerLaser+=10;
					float velX = 0;
					float velY = 0;
					float velZ = 0;
					
					if(speed.x<0)
					{
						velX = weaponMain.speed.x;
					}else{
						velX = speed.x+weaponMain.speed.x;
					}
					if(speed.y<0)
					{
						velY = weaponMain.speed.y;
					}else{
						velY = speed.y+weaponMain.speed.y;
					}
					if(speed.z<0)
					{
						velZ = weaponMain.speed.z;
					}else{
						velZ = speed.z+weaponMain.speed.z;
					}
					
					Weapon w = new Weapon(CanvasGame.X+position.x+frontV.x*0.6f, CanvasGame.Y+position.y+frontV.y*0.6f, CanvasGame.Z+position.z+frontV.z*0.6f, weaponMain.size.x, weaponMain.size.y, weaponMain.size.z, velX, velY, velZ, weaponMain.model,weaponMain.target,weaponMain.range,weaponMain.damage,weaponMain.cadence);
					w.setPaiPlayerShip(this);
					w.setTextureTiro(weaponMain.textureTiro);
					w.setRotation(frontV, rightV, upV);
					CanvasGame.shots.add(w);

					rotate(AXIS_Y, 5);
					w = new Weapon(CanvasGame.X+position.x+frontV.x*0.6f, CanvasGame.Y+position.y+frontV.y*0.6f, CanvasGame.Z+position.z+frontV.z*0.6f, weaponMain.size.x, weaponMain.size.y, weaponMain.size.z, velX, velY, velZ, weaponMain.model,weaponMain.target,weaponMain.range,weaponMain.damage,weaponMain.cadence);
					w.setPaiPlayerShip(this);
					w.setTextureTiro(weaponMain.textureTiro);
					w.setRotation(frontV, rightV, upV);
					CanvasGame.shots.add(w);
					

					rotate(AXIS_X, 3);
					w = new Weapon(CanvasGame.X+position.x+frontV.x*0.6f, CanvasGame.Y+position.y+frontV.y*0.6f, CanvasGame.Z+position.z+frontV.z*0.6f, weaponMain.size.x, weaponMain.size.y, weaponMain.size.z, velX, velY, velZ, weaponMain.model,weaponMain.target,weaponMain.range,weaponMain.damage,weaponMain.cadence);
					w.setPaiPlayerShip(this);
					w.setTextureTiro(weaponMain.textureTiro);
					w.setRotation(frontV, rightV, upV);
					CanvasGame.shots.add(w);
					
					rotate(AXIS_X, -6);
					w = new Weapon(CanvasGame.X+position.x+frontV.x*0.6f, CanvasGame.Y+position.y+frontV.y*0.6f, CanvasGame.Z+position.z+frontV.z*0.6f, weaponMain.size.x, weaponMain.size.y, weaponMain.size.z, velX, velY, velZ, weaponMain.model,weaponMain.target,weaponMain.range,weaponMain.damage,weaponMain.cadence);
					w.setPaiPlayerShip(this);
					w.setTextureTiro(weaponMain.textureTiro);
					w.setRotation(frontV, rightV, upV);
					CanvasGame.shots.add(w);
					rotate(AXIS_X, 3);
					
					rotate(AXIS_Y, -10);
					w = new Weapon(CanvasGame.X+position.x+frontV.x*0.6f, CanvasGame.Y+position.y+frontV.y*0.6f, CanvasGame.Z+position.z+frontV.z*0.6f, weaponMain.size.x, weaponMain.size.y, weaponMain.size.z, velX, velY, velZ, weaponMain.model,weaponMain.target,weaponMain.range,weaponMain.damage,weaponMain.cadence);
					w.setPaiPlayerShip(this);
					w.setTextureTiro(weaponMain.textureTiro);
					w.setRotation(frontV, rightV, upV);
					CanvasGame.shots.add(w);
					

					rotate(AXIS_X, 3);
					w = new Weapon(CanvasGame.X+position.x+frontV.x*0.6f, CanvasGame.Y+position.y+frontV.y*0.6f, CanvasGame.Z+position.z+frontV.z*0.6f, weaponMain.size.x, weaponMain.size.y, weaponMain.size.z, velX, velY, velZ, weaponMain.model,weaponMain.target,weaponMain.range,weaponMain.damage,weaponMain.cadence);
					w.setPaiPlayerShip(this);
					w.setTextureTiro(weaponMain.textureTiro);
					w.setRotation(frontV, rightV, upV);
					CanvasGame.shots.add(w);
					
					rotate(AXIS_X, -6);
					w = new Weapon(CanvasGame.X+position.x+frontV.x*0.6f, CanvasGame.Y+position.y+frontV.y*0.6f, CanvasGame.Z+position.z+frontV.z*0.6f, weaponMain.size.x, weaponMain.size.y, weaponMain.size.z, velX, velY, velZ, weaponMain.model,weaponMain.target,weaponMain.range,weaponMain.damage,weaponMain.cadence);
					w.setPaiPlayerShip(this);
					w.setTextureTiro(weaponMain.textureTiro);
					w.setRotation(frontV, rightV, upV);
					CanvasGame.shots.add(w);
					rotate(AXIS_X, 3);
					
					rotate(AXIS_Y, 5);

					rotate(AXIS_X, 5);
					w = new Weapon(CanvasGame.X+position.x+frontV.x*0.6f, CanvasGame.Y+position.y+frontV.y*0.6f, CanvasGame.Z+position.z+frontV.z*0.6f, weaponMain.size.x, weaponMain.size.y, weaponMain.size.z, velX, velY, velZ, weaponMain.model,weaponMain.target,weaponMain.range,weaponMain.damage,weaponMain.cadence);
					w.setPaiPlayerShip(this);
					w.setTextureTiro(weaponMain.textureTiro);
					w.setRotation(frontV, rightV, upV);
					CanvasGame.shots.add(w);
					
					rotate(AXIS_X, -10);
					w = new Weapon(CanvasGame.X+position.x+frontV.x*0.6f, CanvasGame.Y+position.y+frontV.y*0.6f, CanvasGame.Z+position.z+frontV.z*0.6f, weaponMain.size.x, weaponMain.size.y, weaponMain.size.z, velX, velY, velZ, weaponMain.model,weaponMain.target,weaponMain.range,weaponMain.damage,weaponMain.cadence);
					w.setPaiPlayerShip(this);
					w.setTextureTiro(weaponMain.textureTiro);
					w.setRotation(frontV, rightV, upV);
					CanvasGame.shots.add(w);
					rotate(AXIS_X, 5);
					
					if(energy==0){
						shotState=2;
					}
				}
				
			}else if(shotState==2){
				timerShot-=diffTime;
				if(timerShot<0){
					shotState=0;
					energy=3;
				}
			}
		}else if(shotType==1){
			if(shotState==0){
				if(shooting){
					shotState = 1;
					timerLaser = 1;
					timerShot = weaponMain.cadence/5;
					//aqui tenho que mexer 
				}
			}else if(shotState==1){
				timerLaser-=diffTime;
				if(timerLaser<0){
					energy--;
					timerLaser+=10;
					float velX = 0;
					float velY = 0;
					float velZ = 0;
					
					if(speed.x<0)
					{
						velX = weaponMain.speed.x;
					}else{
						velX = speed.x+weaponMain.speed.x;
					}
					if(speed.y<0)
					{
						velY = weaponMain.speed.y;
					}else{
						velY = speed.y+weaponMain.speed.y;
					}
					if(speed.z<0)
					{
						velZ = weaponMain.speed.z;
					}else{
						velZ = speed.z+weaponMain.speed.z;
					}
					
					Weapon w = new Weapon(CanvasGame.X+position.x+frontV.x*0.6f, CanvasGame.Y+position.y+frontV.y*0.6f, CanvasGame.Z+position.z+frontV.z*0.6f, weaponMain.size.x, weaponMain.size.y, weaponMain.size.z, velX, velY, velZ, weaponMain.model,weaponMain.target,weaponMain.range,weaponMain.damage,weaponMain.cadence);
					w.setPaiPlayerShip(this);
					w.setTextureTiro(weaponMain.textureTiro);
					w.setRotation(frontV, rightV, upV);
					CanvasGame.shots.add(w);
					
					rotate(AXIS_Y, 90);
					Vector3f temp = position.add(frontV.multiply(0.1));
					rotate(AXIS_Y, -90);
					w = new Weapon(CanvasGame.X+temp.x+frontV.x*0.6f, CanvasGame.Y+temp.y+frontV.y*0.6f, CanvasGame.Z+temp.z+frontV.z*0.6f, weaponMain.size.x, weaponMain.size.y, weaponMain.size.z, velX, velY, velZ, weaponMain.model,weaponMain.target,weaponMain.range,weaponMain.damage,weaponMain.cadence);
					w.setPaiPlayerShip(this);
					w.setTextureTiro(weaponMain.textureTiro);
					w.setRotation(frontV, rightV, upV);
					CanvasGame.shots.add(w);

					rotate(AXIS_Y, -90);
					temp = position.add(frontV.multiply(0.1));
					rotate(AXIS_Y, 90);
					w = new Weapon(CanvasGame.X+temp.x+frontV.x*0.6f, CanvasGame.Y+temp.y+frontV.y*0.6f, CanvasGame.Z+temp.z+frontV.z*0.6f, weaponMain.size.x, weaponMain.size.y, weaponMain.size.z, velX, velY, velZ, weaponMain.model,weaponMain.target,weaponMain.range,weaponMain.damage,weaponMain.cadence);
					w.setPaiPlayerShip(this);
					w.setTextureTiro(weaponMain.textureTiro);
					w.setRotation(frontV, rightV, upV);
					CanvasGame.shots.add(w);

					
					if(energy==0){
						shotState=2;
					}
				}
				
			}else if(shotState==2){
				timerShot-=diffTime;
				if(timerShot<0){
					shotState=0;
					energy=3;
				}
			}
		}else{
			if(shotState==0){
				if(shooting){
					shotState = 1;
					timerLaser = 1;
					timerShot = weaponMain.cadence;
					//aqui tenho que mexer 
				}
			}else if(shotState==1){
				timerLaser-=diffTime;
				if(timerLaser<0){
					energy--;
					timerLaser+=10;
					float velX = 0;
					float velY = 0;
					float velZ = 0;
					
					if(speed.x<0)
					{
						velX = weaponMain.speed.x;
					}else{
						velX = speed.x+weaponMain.speed.x;
					}
					if(speed.y<0)
					{
						velY = weaponMain.speed.y;
					}else{
						velY = speed.y+weaponMain.speed.y;
					}
					if(speed.z<0)
					{
						velZ = weaponMain.speed.z;
					}else{
						velZ = speed.z+weaponMain.speed.z;
					}
					
					Weapon w = new Weapon(CanvasGame.X+position.x+frontV.x*0.6f, CanvasGame.Y+position.y+frontV.y*0.6f, CanvasGame.Z+position.z+frontV.z*0.6f, weaponMain.size.x, weaponMain.size.y, weaponMain.size.z, velX, velY, velZ, weaponMain.model,weaponMain.target,weaponMain.range,weaponMain.damage,weaponMain.cadence);
					w.setPaiPlayerShip(this);
					w.setRotation(frontV, rightV, upV);
					
					//adiciona pro canvas
					CanvasGame.shots.add(w);
					if(energy==0){
						shotState=2;
					}
				}
				
			}else{
				timerShot-=diffTime;
			}
		}
	}
	
	public void setShotType(int shotType) {
		this.shotType = shotType;
	}
	@Override
	public void draw(GL canvas, FrustumV2 camera) {
		canvas.glPushMatrix();
		canvas.glTranslatef(CanvasGame.X, CanvasGame.Y, CanvasGame.Z);
		skybox.DesenhaSe(canvas);
		canvas.glPopMatrix();
		
		canvas.glPushMatrix();
		{
			
			canvas.glTranslatef(CanvasGame.X, CanvasGame.Y, CanvasGame.Z);
			canvas.glTranslatef(position.x, position.y, position.z);
			canvas.glScalef(0.01f, 0.01f, 0.01f);
			Util.rotacionaGLViaVetores(canvas, frontV, rightV, upV);
			canvas.glRotatef(90, 0, 0, 1);
			canvas.glRotatef(10, 1, 0, 0);
			weaponMain.target.draw(canvas, camera);
			//TODO extra, talvez seja retirado
			if(CanvasGame.UP){
				canvas.glRotatef(10, 1, 0, 0);
			}else
			if(CanvasGame.DOWN){
				canvas.glRotatef(-10, 1, 0, 0);
			}
			if(CanvasGame.RIGHT){
				canvas.glRotatef(10, 0, 0, 1);
			}else
			if(CanvasGame.LEFT){
				canvas.glRotatef(-10, 0, 0, 1);
			}
			//TODO fim do extra
			CanvasGame.textures [indiceTextura].enable();
			CanvasGame.textures [indiceTextura].bind();
			model.desenhase(canvas);
			CanvasGame.textures [indiceTextura].disable();
		}
		canvas.glPopMatrix();
		canvas.glPushMatrix();
		escudo.DesenhaSe(canvas);
		canvas.glPopMatrix();
		canvas.glPushMatrix();
		propulsor.Draw(canvas);
		canvas.glPopMatrix();
		
		
		
		
		
	}
	
	public void addSpeed(float increment){
		if(((speed.x<50||speed.y<50||speed.z<50)&&(increment>0))||((speed.x>-20||speed.y>-20||speed.z>-20)&&(increment<0))){
			speed.x += increment;
			speed.y += increment;
			speed.z += increment;
		}
//		System.out.println("Speed "+speed);
	}
	
	public void setShooting(boolean shooting) {
		this.shooting = shooting;
	}
	
	public void setIndiceTextura(int indiceTextura) {
		this.indiceTextura = indiceTextura;
		
	}
	
	
}
