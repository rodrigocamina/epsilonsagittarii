package gameobjects;

import java.util.List;

import javax.media.opengl.GL;

import matematcbase.Util;
import matematcbase.Vector3f;
import obj.ObjModel;
import octtree.AreaV2;
import octtree.Obj8T;
import frustum.FrustumV2;

public class GameObj implements Obj8T{


	public static final int AXIS_X = 0;
	public static final int AXIS_Y = 1;
	public static final int AXIS_Z = 2;
	
	ObjModel model;
	Vector3f position;
	Vector3f rightV = new Vector3f(1, 0, 0);
	Vector3f upV = new Vector3f(0, 1, 0);
	Vector3f frontV = new Vector3f(0, 0, 1);
	Vector3f size;
	Vector3f centerPosition;
	float radius;
	Vector3f speed;
	AreaV2 area;
	
	public GameObj(float x, float y, float z, float w, float h, float d, float vx, float vy, float vz, ObjModel model) {
		position = new Vector3f(x, y, z);
		size = new Vector3f(w,h,d);
		speed = new Vector3f(vx, vy, vz);
		float x2 = x+(w/2);
		float y2 = y+(h/2);
		float z2 = z+(y/2);
		centerPosition = new Vector3f(x2,y2,z2);
		radius = (float)Math.sqrt((x2-x)*(x2-x)+(y2-y)*(y2-y)+(z2-z)*(z2-z));
		this.model = model;
	}
	
	@Override
	public void draw(GL canvas, FrustumV2 camera) {
		if(model==null){
			float width = size.x;
			float height = size.y;
			float depth = size.z;
			canvas.glPushMatrix();
			{
				canvas.glTranslatef(0,0,0);
				//frente
				canvas.glBegin(GL.GL_QUADS);
				canvas.glVertex3f(position.x, position.y, position.z);
				canvas.glVertex3f(position.x+width, position.y, position.z);
				canvas.glVertex3f(position.x+width, position.y+height, position.z);
				canvas.glVertex3f(position.x, position.y+height, position.z);
				canvas.glEnd();
				//tras
				canvas.glBegin(GL.GL_QUADS);
				canvas.glVertex3f(position.x, position.y, position.z+depth);
				canvas.glVertex3f(position.x+width, position.y, position.z+depth);
				canvas.glVertex3f(position.x+width, position.y+height, position.z+depth);
				canvas.glVertex3f(position.x, position.y+height, position.z+depth);
				canvas.glEnd();
				//esquerda
				canvas.glBegin(GL.GL_QUADS);
				canvas.glVertex3f(position.x, position.y, position.z);
				canvas.glVertex3f(position.x, position.y, position.z+depth);
				canvas.glVertex3f(position.x, position.y+height, position.z+depth);
				canvas.glVertex3f(position.x, position.y+height, position.z);
				canvas.glEnd();
				//direita
				canvas.glBegin(GL.GL_QUADS);
				canvas.glVertex3f(position.x+width, position.y, position.z);
				canvas.glVertex3f(position.x+width, position.y, position.z+depth);
				canvas.glVertex3f(position.x+width, position.y+height, position.z+depth);
				canvas.glVertex3f(position.x+width, position.y+height, position.z);
				canvas.glEnd();


				canvas.glBegin(GL.GL_QUADS);
				canvas.glVertex3f(position.x, position.y, position.z);
				canvas.glVertex3f(position.x, position.y, position.z+depth);
				canvas.glVertex3f(position.x+width, position.y, position.z+depth);
				canvas.glVertex3f(position.x+width, position.y, position.z);
				canvas.glEnd();

				canvas.glBegin(GL.GL_QUADS);
				canvas.glVertex3f(position.x, position.y+height, position.z);
				canvas.glVertex3f(position.x, position.y+height, position.z+depth);
				canvas.glVertex3f(position.x+width, position.y+height, position.z+depth);
				canvas.glVertex3f(position.x+width, position.y+height, position.z);
				canvas.glEnd();
			}
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
	public void simulate(long difftime) {
		
		
		
	}
	
	public boolean checkColision(Obj8T obj){
		return getX()<=obj.getX()+obj.getWidth() &&getX()+getWidth()>=obj.getX()&&
		getY()<=obj.getY()+obj.getHeight()&&getY()+getHeight()>=obj.getY()&&
		getZ()<=obj.getZ()+obj.getDepth() &&getZ()+getDepth()>=obj.getZ();
	}
	
	public void checkChild(AreaV2 a, long difftime){
		checkList(a.getElements(),difftime);
		if(a.getChildren()!=null){
			for (int i = 0; i < 8; i++) {
				checkChild(a.getChildren()[i], difftime);
			}
		}
	}
	
	public void checkList(List<Obj8T> list,long difftime){
		for (int i = 0; i < list.size(); i++) {
			Obj8T obj = list.get(i);
			if(obj!=this){
			if(checkColision(obj)){
				speed.x = -speed.x;
				speed.y = -speed.y;
				speed.z = -speed.z;
				position.x += difftime*speed.x*2/1000;
				position.y += difftime*speed.y*2/1000;
				position.z += difftime*speed.z*2/1000;
			}
				if(getX()>200&&speed.x>0||getX()<-200&&speed.x<0){
					speed.x = -speed.x;
				}
				if(getY()>200&&speed.y>0||getY()<-200&&speed.y<0){
					speed.y = -speed.y;
				}
				if(getZ()>200&&speed.z>0||getZ()<-200&&speed.z<0){
					speed.z = -speed.z;
				}
			}
		}
	}
	
	@Override
	public AreaV2 getArea() {
		return area;
	}

	public Vector3f getPosition() {
		return position;
	}
	
	public Vector3f getFrontV() {
		return frontV;
	}
	
	public Vector3f getRightV() {
		return rightV;
	}
	
	public Vector3f getUpV() {
		return upV;
	}
	
	@Override
	public float getDepth() {
		return size.z;
	}

	@Override
	public float getHeight() {
		return size.y;
	}

	@Override
	public float getWidth() {
		return size.x;
	}

	@Override
	public float getX() {
		return position.x;
	}

	@Override
	public float getY() {
		return position.y;
	}

	@Override
	public float getZ() {
		return position.z;
	}

	@Override
	public void setArea(AreaV2 newArea) {
		area = newArea;
	}

	@Override
	public Vector3f getCenterPoint() {
		// TODO Auto-generated method stub
		return centerPosition;
	}

	@Override
	public float getRadius() {
		// TODO Auto-generated method stub
		return radius;
	}

	public void rotate(int rotationVector, int angle){
		if(rotationVector==AXIS_X){
			
		}else if(rotationVector==AXIS_Y){
			
		}else if(rotationVector==AXIS_Z){
			
		}
	}
	
	
}
