package octtree;

import javax.media.opengl.GL;

import matematcbase.Vector3f;

import frustum.FrustumV2;

public interface Obj8T {

	public abstract float getX();
	public abstract float getY();
	public abstract float getZ();
	public abstract float getRadius();
	public abstract Vector3f getCenterPoint();
	public abstract float getWidth();//tamanho em X
	public abstract float getHeight();//tamanho em Y
	public abstract float getDepth();//tamanho em Z
	public abstract AreaV2 getArea();
	public abstract void setArea(AreaV2 newArea);
	public abstract void draw(GL canvas, FrustumV2 camera);
	public abstract void simulate(long difftime); 
}
