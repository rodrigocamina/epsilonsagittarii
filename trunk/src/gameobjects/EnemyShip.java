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

	GameObj target;
	Vector3f targetPosition;
	Vector3f targetRelativePosition;
	EnemyGroup group;
	float space = 1;
	float rotY;
	float rotX;
	float rotZ;
	int state = 0;
	long retreatTimer = 5000;
	private int indiceTextura = 0;
	Esfera escudo;
	
	public final static int ATTACKING = 0;
	public final static int RETREATING = 1;
	public final static int REGROUPING = 2;
	public final static int PREPARINGATTACK = 2;
	Vector3f speedRegroup;
	Vector3f speedMax;
	//attacking = 0, retreating = 1 , regrouping = 2
	
	public EnemyShip(float x, float y, float z, float w, float h, float d,
			float vx, float vy, float vz, ObjModel model, GameObj station, EnemyGroup group) {
		super(x, y, z, w, h, d, vx, vy, vz, model);
		target = station;
		targetPosition = target.position;
		this.group = group;
		group.members.add(this);
		speedRegroup = speed.multiply(0.0);
		speedMax = new Vector3f(speed);
		this.radius= 1.0f;
		escudo = new Esfera(x, y, z, radius, 0);
		

	}

	@Override
	public void simulate(long diffTime) {
		if(life>0){
			adjustDirection(diffTime);
			move(diffTime);
			ai(diffTime);
			escudo.Simulase((int) diffTime);
		}
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
	}
	
	@Override
	public void draw(GL canvas, FrustumV2 camera) {
		if(life>0){
			canvas.glPushMatrix();
			{
				canvas.glTranslatef(position.x, position.y, position.z);
				Util.rotacionaGLViaVetores(canvas, frontV, rightV, upV);
				canvas.glRotatef(-90, 0, 0, 1);
				canvas.glScalef(0.1f, 0.1f, 0.1f);
				
				model.desenhase(canvas);
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
				if(targetRelativePosition.z<0&&targetRelativePosition.z>-5){
					state=RETREATING;
					retreatTimer = 5000;
				}else{ 
					shot(diffTime);
				}
			}else if(retreatTimer<0){
				state = RETREATING;
				retreatTimer = 5000;
			}
		}else if(state==RETREATING){
			retreatTimer -= diffTime;
			if(targetRelativePosition.weight()>10||retreatTimer<0){
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
 				if(contOK==sz){
 					state = PREPARINGATTACK;
 					targetPosition = target.position;
 					retreatTimer = (int)targetPosition.mydistance(position);
 					if(retreatTimer>5000){
 						retreatTimer=5000;
 					}
 				}else{
 					if(targetPosition.mydistance(position)<10){
 						Random rnd = new Random();
 						int n = rnd.nextInt(4);
 						if(n==0){
 							targetPosition = new Vector3f(50,50,50);
 						}else if(n==1){
 							targetPosition = new Vector3f(-50,50,50);
 						}else if(n==2){
 							targetPosition = new Vector3f(-50,-50,50);
 						}else if(n==3){
 							targetPosition = new Vector3f(50,-50,50);
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
	}
	
	public void setIndiceTextura(int indiceTextura) {
		this.indiceTextura = indiceTextura;
	}
	
}
