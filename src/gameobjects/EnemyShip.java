package gameobjects;

import java.util.Random;

import javax.media.opengl.GL;

import frustum.Esfera;
import frustum.FrustumV2;
import main.CanvasGame;
import matematcbase.Vector3f;
import obj.ObjModel;
import util.Util;


public class EnemyShip extends GameObj{

	public static final int FIGHTER = 10;
	public static final int BOMBER = 20;
	public static final int MOTHER = 30;
	
	GameObj target;
	Vector3f targetPosition;
	Vector3f scale;
	Vector3f targetRelativePosition;
	EnemyGroup group;
	float space = 1;
	float rotY;
	float rotX;
	float rotZ;
	int state = 0;
	boolean scout = false;
	long retreatTimer = 5000;
	private int indiceTextura ;
	Esfera escudo;
	long timerShot;
	int type;
	Weapon weaponMain;
	Weapon weaponSub;
		
	private final static int ATTACKING = 0;
	private final static int RETREATING = 1;
	private final static int REGROUPING = 2;
	private final static int PREPARINGATTACK = 3;
	Vector3f speedRegroup;
	Vector3f speedMax;
	//attacking = 0, retreating = 1 , regrouping = 2
	
	
	
	public EnemyShip(float x, float y, float z, float w, float h, float d,
			float vx, float vy, float vz, ObjModel model, GameObj station, EnemyGroup group,int type) {
		super(x, y, z, w, h, d, vx, vy, vz, model);
		target = station;
		targetPosition = target.position;
		this.group = group;
		group.members.add(this);
		this.radius= 2.0f;
		escudo = new Esfera(x, y, z, radius, 0,this);
		ObjModel target = new ObjModel();
		target.loadObj("/res/MiraLaser.obj");
		int vel = 100;
		weaponMain = new Laser(x, y, z, 0.2f,0.2f,2.0f, vel, vel, vel, null, 500, 2*CanvasGame.TEX_TIRO_BLUE, 700, target);
		weaponMain.setPaiEnemyShip(this);
		weaponMain.setTextureTiro(CanvasGame.textures[CanvasGame.TEX_TIRO_BLUE]);
		
		this.type = type;

		
		if(type==FIGHTER){
			indiceTextura = CanvasGame.TEX_ENEMY_FIGHTER;
			this.speed = speed.multiply(3);
			scale = new Vector3f(0.1f, 0.1f, 0.1f);
		}else if(type==BOMBER){
			indiceTextura = CanvasGame.TEX_ENEMY_BOMBER;
			this.speed = speed.multiply(1.2);
			scale = new Vector3f(0.15f, 0.15f, 0.15f);
			radius*=1.5;
		}else if(type==MOTHER){
			indiceTextura = CanvasGame.TEX_ENEMY_MOTHER;
			this.speed = speed.multiply(0.4);
			scale = new Vector3f(0.5f, 0.5f, 0.5f);
			radius*=5;
		}
		speedRegroup = speed.multiply(0.6);
		speedMax = new Vector3f(speed);
	}

	@Override
	public void simulate(long diffTime) {
		if(getLife()>0){
			adjustDirection(diffTime);
			move(diffTime);
			ai(diffTime);
			escudo.Simulase((int) diffTime);
		}
	}
	
	public int getIndiceTextura() {
		return indiceTextura;
	}
	
	
	private void adjustDirection(long diffTime){
		if(target instanceof PlayerShip){
			targetRelativePosition = new Vector3f(CanvasGame.X+targetPosition.x-position.x, CanvasGame.Y+targetPosition.y-position.y, CanvasGame.Z+targetPosition.z-position.z);
		}else{
			targetRelativePosition = new Vector3f(targetPosition.x-position.x, targetPosition.y-position.y, targetPosition.z-position.z);
		}
		Util.transformToRelativePosition(targetRelativePosition, frontV, rightV, upV);
		//seens ok
		if(state==ATTACKING||state==REGROUPING){
			if(targetRelativePosition.z<0){
				if(targetRelativePosition.y>0){
					rotY -= (30*diffTime/1000.0f);
				}else if(targetRelativePosition.y<0){
					rotY += (30*diffTime/1000.0f);
				}
				if(targetRelativePosition.x>0){
					rotX += (30*diffTime/1000.0f);
				}else if(targetRelativePosition.x<0){
					rotX -= (30*diffTime/1000.0f);
				}
			}else{
				if(targetRelativePosition.y>0){
					rotY -= (30*diffTime/1000.0f);
				}else{
					rotY += (30*diffTime/1000.0f);
				}

				if(targetRelativePosition.x>0){
					rotX += (30*diffTime/1000.0f);
				}else{
					rotX -= (30*diffTime/1000.0f);
				}
			}
		}else if(state==RETREATING){
			if(targetRelativePosition.z<0){
				if(targetRelativePosition.y>0){
					rotY += (30*diffTime/1000.0f);
				}else if(targetRelativePosition.y<0){
					rotY -= (30*diffTime/1000.0f);
				}
				if(targetRelativePosition.x>0){
					rotX -= (30*diffTime/1000.0f);
				}else if(targetRelativePosition.x<0){
					rotX += (30*diffTime/1000.0f);
				}
			}else{
				if(targetRelativePosition.y>0){
					rotY += (30*diffTime/1000.0f);
				}else{
					rotY -= (30*diffTime/1000.0f);
				}

				if(targetRelativePosition.x>0){
					rotX -= (30*diffTime/1000.0f);
				}else{
					rotX += (30*diffTime/1000.0f);
				}
			}
		}//else nothing
		if(rotX>=1){
			rotate(AXIS_X, 1);
			rotX-=1;
		}else if(rotX<=-1){
			rotate(AXIS_X, -1);
			rotX+=1;
		}
		if(rotY>=1){
			rotate(AXIS_Y, 1);
			rotY-=1;
		}else if(rotY<=-1){
			rotate(AXIS_Y, -1);
			rotY+=1;
		}
	}
	
	private void move(long diffTime){
		
		float dX =frontV.x*speed.x*diffTime/1000.0f;
		float dY =frontV.y*speed.y*diffTime/1000.0f;
		float dZ =frontV.z*speed.z*diffTime/1000.0f;
		position.x+=dX;
		position.y+=dY;
		position.z+=dZ;
		escudo.SetPosition(position);
		
		if(type==FIGHTER&&position.mydistance(CanvasGame.nave.position)<20){
			takeDamage(0);
		}
		
	}
	
	@Override
	public void draw(GL canvas, FrustumV2 camera) {
		if(getLife()>0){
			canvas.glPushMatrix();
			{
				canvas.glTranslatef(position.x, position.y, position.z);
				Util.rotacionaGLViaVetores(canvas, frontV, rightV, upV);
				canvas.glRotatef(-90, 0, 0, 1);
				canvas.glScalef(scale.x, scale.y, scale.z);
				CanvasGame.textures[indiceTextura].enable();
				CanvasGame.textures[indiceTextura].bind();
				model.desenhase(canvas);
				CanvasGame.textures[indiceTextura].disable();
			}
			canvas.glPopMatrix();
			escudo.DesenhaSe(canvas);
		}else{
			
		}
	}
	
	private void ai(long diffTime){
		if(state==ATTACKING){
			retreatTimer -= diffTime;
			if((targetRelativePosition.y<2&&targetRelativePosition.x<2)&&(targetRelativePosition.y>-2&&targetRelativePosition.x>-2)){
				if(targetRelativePosition.z<10&&targetRelativePosition.z>-5){
					state=RETREATING;
				}else{ 
					shot(diffTime);
				}
			}else if(retreatTimer<0){
				state = RETREATING;
			}
		}else if(state==RETREATING){
			if(targetRelativePosition.weight()>50){
				if(target instanceof PlayerShip){
					state = ATTACKING;					
					retreatTimer = 60000;
				}else{
					state = REGROUPING;
					targetPosition = group.getTargetPosition(this);
					
				}
			}
		}else if(state==REGROUPING){
			//check all ok, go to next
			if(group.getLeader()==this){
				speed = speedRegroup;
				//check all
				int sz = group.members.size();
				int contOK = 1;
 				for (int i = 1; i < sz; i++) {
					EnemyShip ship = group.members.get(i);
					if(ship.targetPosition.mydistance(ship.position)<2){
						contOK++;
					}
				}
 				System.out.println("OK "+contOK);
 				if(contOK==sz){
 					state = PREPARINGATTACK;
 					targetPosition = target.position;
 					retreatTimer = (int)targetPosition.mydistance(position);
 					if(retreatTimer<5000){
 						retreatTimer=5000;
 					}
 				}else{
 					if(targetPosition.mydistance(position)<10){
 						Random rnd = new Random();
 						int n = rnd.nextInt(4);
 						if(n==0){
 							targetPosition = new Vector3f(150,150,150);
 						}else if(n==1){
 							targetPosition = new Vector3f(-150,150,150);
 						}else if(n==2){
 							targetPosition = new Vector3f(-150,-150,150);
 						}else if(n==3){
 							targetPosition = new Vector3f(150,-150,150);
 						}
 						
 					}
 				}
				
			}else{
				targetPosition = group.getTargetPosition(this);
				if(targetPosition.mydistance(position)<2){
					speed = group.getLeader().speedRegroup;
				}else{
					speed = speedMax;
				}
			}
		}else{
			retreatTimer-=diffTime;
			if(retreatTimer<0){
				int sz = group.members.size();
				for (int i = 0; i < sz; i++) {
						EnemyShip ship = group.members.get(i);
						ship.state = ATTACKING;
						ship.targetPosition = target.position;
						ship.retreatTimer = 60000;
						ship.speed = ship.speedMax;
					}
			}
		}
	}

	


	private void shot(long difftime){
		//SHOT!
		if(timerShot<=0){
			timerShot = weaponMain.cadence;
			//aqui tenho que mexer 
			
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
			
			
			Weapon w = new Weapon(position.x+frontV.x*0.6f, position.y+frontV.y*0.6f, position.z+frontV.z*0.6f, weaponMain.size.x, weaponMain.size.y, weaponMain.size.z, velX, velY, velZ, weaponMain.model,weaponMain.range,weaponMain.damage,weaponMain.cadence);
			w.setPaiEnemyShip(this);
			w.setTextureTiro(CanvasGame.textures[CanvasGame.TEX_TIRO_BLUE]);
			w.setRotation(frontV, rightV, upV);
			
			//adiciona pro canvas
			CanvasGame.shots.add(w);
		}else{
			timerShot-=difftime;
		}
	}
	
	public void setIndiceTextura(int indiceTextura) {
		this.indiceTextura = indiceTextura;
	}
	
	@Override
	public void takeDamage(double damage) {
		super.takeDamage(damage);
		if(type<MOTHER){
			target = CanvasGame.nave;
			targetPosition = CanvasGame.nave.position;
			group.members.remove(this);
			group = new EnemyGroup();
			state = ATTACKING;					
			retreatTimer = 60000;
		}
	}
}
