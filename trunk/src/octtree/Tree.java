package octtree;

import javax.media.opengl.GL;

import frustum.FrustumV2;

public class Tree {

	AreaV2 root;
	
	public Tree(int layer,int startX, int startY, int startZ, int xEnd, int yEnd, int zEnd) {
		root = new AreaV2(layer,startX, startY, startZ, xEnd, yEnd, zEnd);
	}
	
	public AreaV2 addElement(Obj8T obj){
		return root.addElement(obj);
	}
	
	public void draw(GL canvas, FrustumV2 camera){
		root.draw(canvas, camera);
	}
	
	public void simulate(long difftime){
		root.simulate(difftime);
	}
}
