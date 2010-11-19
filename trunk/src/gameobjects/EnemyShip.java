package gameobjects;

import javax.media.opengl.GL;

import frustum.FrustumV2;
import main.CanvasGame;
import matematcbase.Vector3f;
import obj.ObjModel;
import util.Util;

public class EnemyShip extends GameObj{

	GameObj target;
	Vector3f targetPosition;
	Vector3f targetRelativePosition;
	float space = 1;
	float rotY;
	float rotX;
	float rotZ;
	int state = 0;
	long retreatTimer = 5000;
	public final static int ATTACKING = 0;
	public final static int RETREATING = 1;
	public final static int REGROUPING = 2;
	//attacking = 0, retreating = 1 , regrouping = 2
	
	public EnemyShip(float x, float y, float z, float w, float h, float d,
			float vx, float vy, float vz, ObjModel model, GameObj station) {
		super(x, y, z, w, h, d, vx, vy, vz, model);
		target = station;
		targetPosition = target.position;
	}

	@Override
	public void simulate(long diffTime) {
		adjustDirection(diffTime);
		move(diffTime);
		ai(diffTime);
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
		//System.out.println("Mov");
		
		float dX =frontV.x*speed.x*diffTime/1000.0f;
		float dY =frontV.y*speed.y*diffTime/1000.0f;
		float dZ =frontV.z*speed.z*diffTime/1000.0f;
		position.x+=dX;
		position.y+=dY;
		position.z+=dZ;
	}
	
	@Override
	public void draw(GL canvas, FrustumV2 camera) {
		canvas.glPushMatrix();
		{
			canvas.glTranslatef(position.x, position.y, position.z);
			Util.rotacionaGLViaVetores(canvas, frontV, rightV, upV);
			canvas.glRotatef(-90, 0, 0, 1);
			canvas.glScalef(0.1f, 0.1f, 0.1f);
			model.desenhase(canvas);
		}
		canvas.glPopMatrix();
	}
	
	private void ai(long diffTime){
		if(state==ATTACKING){
			retreatTimer -= diffTime;
			if((targetRelativePosition.y<2&&targetRelativePosition.x<2)&&(targetRelativePosition.y>-2&&targetRelativePosition.x>-2)){
				if(targetRelativePosition.z<0&&targetRelativePosition.z>-5){
					state=RETREATING;
					retreatTimer = 5000;
				}else{ 
					shot();
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
				}
			}
		}else{
			//check all ok, go to next
			
			
		}
	}
	private void shot(){
		//SHOT!
	}
	
}
