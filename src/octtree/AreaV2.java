package octtree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.media.opengl.GL;

import main.CanvasGame;
import matematcbase.Vector3f;
import frustum.FrustumV2;

public class AreaV2 {

	private AreaV2 parent;
	private List<Obj8T> elements = new ArrayList<Obj8T>(); 
	private AreaV2[] children;
	private Vector3f posInicial;
	private Vector3f posFinal;
	private int layer;
	private Vector3f centerPoint;
	private float radius;
	
	//width, height, depth?profundidade
	
	AreaV2(int layer,float startX, float startY, float startZ, float xEnd, float yEnd, float zEnd) {
		this.layer = layer;
		posInicial = new Vector3f(startX, startY, startZ);
		posFinal = new Vector3f(xEnd, yEnd, zEnd);
		float x2 = posInicial.x+((posFinal.x-posInicial.x)/2);
		float y2 = posInicial.y+((posFinal.y-posInicial.y)/2);
		float z2 = posInicial.z+((posFinal.z-posInicial.z)/2);
		centerPoint = new Vector3f(x2,y2,z2);
		radius = (float)Math.sqrt((x2-startX)*(x2-startX)+(y2-startY)*(y2-startY)+(z2-startZ)*(z2-startZ));
		
	}
	
	private void split(){
		float x2 = posInicial.x+((posFinal.x-posInicial.x)/2);
		float y2 = posInicial.y+((posFinal.y-posInicial.y)/2);
		float z2 = posInicial.z+((posFinal.z-posInicial.z)/2);
		children= new AreaV2[8];
		children[0] = new AreaV2(layer-1,posInicial.x, posInicial.y, posInicial.z, x2, y2, z2);
		children[1] = new AreaV2(layer-1,x2, posInicial.y, posInicial.z, posFinal.x, y2, z2);
		children[2] = new AreaV2(layer-1,posInicial.x, y2, posInicial.z, x2, posFinal.y, z2);
		children[3] = new AreaV2(layer-1,x2, y2, posInicial.z, posFinal.x, posFinal.y, z2);
		children[4] = new AreaV2(layer-1,posInicial.x, posInicial.y, z2, x2, y2, posFinal.z);
		children[5] = new AreaV2(layer-1,x2, posInicial.y, z2, posFinal.x, y2, posFinal.z);
		children[6] = new AreaV2(layer-1,posInicial.x, y2, z2, x2, posFinal.y, posFinal.z);
		children[7] = new AreaV2(layer-1,x2, y2, z2, posFinal.x, posFinal.y, posFinal.z);
		for (int i = 0; i < 8; i++) {
			children[i].parent = this;
		}
	}
		
	private boolean surrounds(Obj8T obj){
		return 
		posInicial.x<=obj.getX()&&posFinal.x>=obj.getX()+obj.getWidth() &&
		posInicial.y<=obj.getY()&&posFinal.y>=obj.getY()+obj.getHeight()&&
		posInicial.z<=obj.getZ()&&posFinal.z>=obj.getZ()+obj.getDepth();
	}
	/*
	private boolean touches(Obj8T obj){
		return 
		posInicial.x<=obj.getX()+obj.getWidth() &&posFinal.x>=obj.getX()&&
		posInicial.y<=obj.getY()+obj.getHeight()&&posFinal.y>=obj.getY()&&
		posInicial.z<=obj.getZ()+obj.getDepth() &&posFinal.z>=obj.getZ();
	}
	private boolean surrounds(Vector3f obj){
		return 
		posInicial.x<=obj.x&&posFinal.x>=obj.x &&
		posInicial.y<=obj.y&&posFinal.y>=obj.y &&
		posInicial.z<=obj.z&&posFinal.z>=obj.z;
	}*/
	
	public static void updateElement(Obj8T obj){
		if(obj.getArea().surrounds(obj)){
			AreaV2 x = obj.getArea().downgradeElementOnTree(obj);
			if(x!=obj.getArea()){
				obj.getArea().elements.remove(obj);
				x.elements.add(obj);
				obj.setArea(x);
			}else{
			}
		}else{
			AreaV2 x = obj.getArea().upgradeElementOnTree(obj);
			obj.getArea().elements.remove(obj);
			x.elements.add(obj);
			obj.setArea(x);
		}
	}
	
	
	//procura por um nodo em nivel mais baixo que o atual para comportar o objeto
	private AreaV2 downgradeElementOnTree(Obj8T obj){
		if(layer>0){
			if(children==null){
				split();
			}
			for (int i = 0; i < 8; i++) {
				if(children[i].surrounds(obj)){
					return children[i].downgradeElementOnTree(obj);
				}
			}
		}
		return this;
	}
	
	//procura por um nodo em nivel acima do atual para comportar o objeto
	private AreaV2 upgradeElementOnTree(Obj8T obj){
		if(parent.surrounds(obj)){
			return parent;
		}else{
			return parent.upgradeElementOnTree(obj);
		}
	}
	
	//adiciona um nodo novo a arvore
	AreaV2 addElement(Obj8T obj){
		AreaV2 added = downgradeElementOnTree(obj);
		added.elements.add(obj);
		obj.setArea(added);
		return added;
	}
	
	
	//TODO add elementos de baixo E melhorar performance
	public LinkedList<Obj8T> getPossibleColisionList(){
		LinkedList<Obj8T> allElements = new LinkedList<Obj8T>(elements);
		AreaV2 parent = this.parent;
		while(parent!=null){
			allElements.addAll(parent.elements);
			parent = parent.parent;
		}
		//System.out.println("All "+allElements.size());
		return allElements;
	}
	
	public List<Obj8T> getElements() {
		return elements;
	}
	
	public AreaV2[] getChildren() {
		return children;
	}
	
	public AreaV2 getParent() {
		return parent;
	}
	
	void simulate(long difftime){
		for (int i = 0; i < elements.size(); i++) {
			elements.get(i).simulate(difftime);
		}
		if(children!=null){
			for (int i = 0; i < 8; i++) {
				children[i].simulate(difftime);
			}
		}
	}
	
	private void drawElements(GL canvas, FrustumV2 camera) {
		if(children!=null){
			try{
			for (int i = 0; i < 8; i++) {
				children[i].drawElements(canvas, camera);
			}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(elements.size()>0){
			for (int i = 0; i < elements.size(); i++) {
				elements.get(i).draw(canvas, camera);
			}
		}
	}
	
	
	void  draw(GL canvas, FrustumV2 camera) {
		//verifica se camera esta dentro da area
		
		
		
		
		if(CanvasGame.frustumCulling){
		int n = camera.sphereInFrustum(centerPoint, radius);
		
		if(n!=FrustumV2.OUTSIDE){
			
			if(n==FrustumV2.INTERSECT){
				if(children!=null){
					for (int i = 0; i < 8; i++) {
						children[i].draw(canvas, camera);
					}
				}
				canvas.glColor3f(1,0,0);
				if(elements.size()>0){
					for (int i = 0; i < elements.size(); i++) {
						Obj8T elemento = elements.get(i);
						if(camera.sphereInFrustum(elemento.getCenterPoint(), elemento.getRadius())!=FrustumV2.OUTSIDE){
							elemento.draw(canvas, camera);
						}
					}
				}
			}else{
				canvas.glColor3f(0,0,1);
				drawElements(canvas, camera);
			}
			
		}else{
			//canvas.glColor3f(0,1,0);
			//drawLines(canvas);
			//outside =p
			//nothing to do then....?
		}
		}else{
			canvas.glColor3f(0,0,1);
			drawElements(canvas, camera);
		}

		
		
		
	}
	/*
	private void drawLines(GL canvas){
		if(elements.size()>0){
		canvas.glPushMatrix();
		canvas.glTranslatef(0,0,0);
		canvas.glBegin(GL.GL_LINE_LOOP);
		canvas.glVertex3f(posInicial.x,posInicial.y,posInicial.z);
		canvas.glVertex3f(posInicial.x,posFinal.y,posInicial.z);
		canvas.glVertex3f(posFinal.x,posFinal.y,posInicial.z);
		canvas.glVertex3f(posFinal.x,posInicial.y,posInicial.z);
	canvas.glEnd();
	canvas.glBegin(GL.GL_LINE_LOOP);
		canvas.glVertex3f(posInicial.x,posInicial.y,posFinal.z);
		canvas.glVertex3f(posInicial.x,posFinal.y,posFinal.z);
		canvas.glVertex3f(posFinal.x,posFinal.y,posFinal.z);
		canvas.glVertex3f(posFinal.x,posInicial.y,posFinal.z);
	canvas.glEnd();
	canvas.glBegin(GL.GL_LINES);
		canvas.glVertex3f(posInicial.x,posInicial.y,posInicial.z);
		canvas.glVertex3f(posInicial.x,posInicial.y,posFinal.z);
		canvas.glVertex3f(posInicial.x,posFinal.y,posInicial.z);
		canvas.glVertex3f(posInicial.x,posFinal.y,posFinal.z);
		canvas.glVertex3f(posFinal.x,posFinal.y,posInicial.z);
		canvas.glVertex3f(posFinal.x,posFinal.y,posFinal.z);
		canvas.glVertex3f(posFinal.x,posInicial.y,posInicial.z);
		canvas.glVertex3f(posFinal.x,posInicial.y,posFinal.z);
	canvas.glEnd();
		canvas.glPopMatrix();
		}
	}
	*/
}
