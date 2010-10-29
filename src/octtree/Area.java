package octtree;

import java.util.List;

public class Area {

	private Area parent;
	private int elementsCount;
	private List<Obj8T> elements; 
	private Area[] children;
	private int x;
	private int y;
	private int z;
	private int xEnd;
	private int yEnd;
	private int zEnd;
	private final int UP=0,DOWN=1,RIGHT=2,LEFT=3,FRONT=4,BEHIND=5;
	//width, height, depth?profundidade
	
	public static Area createOctMap(int layers, int startX, int startY, int startZ, int endX, int endY, int endZ){
		Area result = new Area();
		result.x = startX;
		result.y = startY;
		result.z = startZ;
		result.xEnd = endX;
		result.yEnd = endY;
		result.zEnd = endZ;
		if(layers>0){
			int startX2 = startX+((endX-startX)>>1);
			int startY2 = startY+((endY-startY)>>1);
			int startZ2 = startZ+((endZ-startZ)>>1);
			layers--;
			result.children= new Area[8];
			result.children[0] = createOctMap(layers, startX, startY, startZ, startX2, startY2, startZ2);
			result.children[1] = createOctMap(layers, startX2, startY, startZ, endX, startY2, startZ2);
			result.children[2] = createOctMap(layers, startX, startY2, startZ, startX2, endY, startZ2);
			result.children[3] = createOctMap(layers, startX2, startY2, startZ, endX, endY, startZ2);
			result.children[4] = createOctMap(layers, startX, startY, startZ2, startX2, startY2, endZ);
			result.children[5] = createOctMap(layers, startX2, startY, startZ2, endX, startY2, endZ);
			result.children[6] = createOctMap(layers, startX, startY2, startZ2, startX2, endY, endZ);
			result.children[7] = createOctMap(layers, startX2, startY2, startZ2, endX, endY, endZ);
		}
		return result;
	}
	
	//reorganiza
	public void update(){
		if(children!=null){
			children[0].update();
			children[1].update();
			children[2].update();
			children[3].update();
			children[4].update();
			children[5].update();
			children[6].update();
			children[7].update();
		}
		for (int i = 0; i < elements.size(); i++) {
			if(inside(elements.get(i))){
				fall(elements.get(i));
			}else{
				if(parent!=null){
					parent.elements.add(elements.remove(i));
				}
			}
		}
	}
	
	private void fall(Obj8T obj){
		if(children!=null){
			for (int j = 0; j < 8; j++) {
				if(children[j].inside(obj)){
					elements.remove(obj);
					children[j].elements.add(obj);
					children[j].fall(obj);
					break;
				}
			}
		}
	}
	
	public boolean inside(Obj8T obj){
		return 
		x<=obj.getX()&&xEnd>=obj.getX()+obj.getWidth()&&
		y<=obj.getY()&&yEnd>=obj.getY()+obj.getHeight()&&
		z<=obj.getZ()&&zEnd>=obj.getZ()+obj.getDepth();
	}
	
	public boolean outside(Obj8T obj){
		return 
		xEnd>obj.getX()||x<obj.getX()+obj.getWidth()||
		yEnd>obj.getY()||y<obj.getY()+obj.getHeight()||
		zEnd>obj.getZ()||z<obj.getZ()+obj.getDepth();
	}
	
	public boolean intersect(Obj8T obj){
		return !inside(obj)&&!outside(obj);
	}
	
	private Area getParentChild(int n, int direction){
		if(parent!=null){
			Area a = null;
			switch (direction) {
			case UP:
				a = parent.getAreaAboveChildArea(this);
				break;
			case DOWN:
				a = parent.getAreaBelowChildArea(this);
				break;
			case RIGHT:
				a = parent.getAreaRightChildArea(this);
				break;
			case LEFT:
				a = parent.getAreaLeftChildArea(this);
				break;
			case FRONT:
				a = parent.getAreaFrontChildArea(this);
				break;
			default:
				a = parent.getAreaBehindChildArea(this);
				break;
			}
			if(a!=null){
				return a.children[n];
			}
			return null;
		}else{
			return null;
		}
	}
	
	//pega area acima da area passada
	private Area getAreaAboveChildArea(Area child){
		int n = indexOf(child);
		if(n<4){
			if(n<2){
				if(n==0){
					return getParentChild(2,UP);
				}else{
					return getParentChild(3,UP);
				}
			}else{
				if(n==2){
					return children[0];
				}else{
					return children[1];
				}
			}
		}else{
			if(n<6){
				if(n==4){
					return getParentChild(6,UP);
				}else{
					return getParentChild(7,UP);
				}
			}else{
				if(n==6){
					return children[4];
				}else{
					return children[5];
				}
			}
		}
	}

	//pega area abaixo da area passada
	private Area getAreaBelowChildArea(Area child){
		int n = indexOf(child);
		if(n<4){
			if(n<2){
				if(n==0){
					return children[2];
				}else{
					return children[3];
				}
			}else{
				if(n==2){
					return getParentChild(0,DOWN);
				}else{
					return getParentChild(1,DOWN);
				}
			}
		}else{
			if(n<6){
				if(n==4){
					return children[6];
				}else{
					return children[7];
				}
			}else{
				if(n==6){
					return getParentChild(4,DOWN);
				}else{
					return getParentChild(5,DOWN);
				}
			}
		}
	}

	//pega area a direita da area passada
	private Area getAreaRightChildArea(Area child){
		int n = indexOf(child);
		if(n<4){
			if(n<2){
				if(n==0){
					return children[1];
				}else{
					return getParentChild(0,RIGHT);
				}
			}else{
				if(n==2){
					return children[3];
				}else{
					return getParentChild(2,RIGHT);
				}
			}
		}else{
			if(n<6){
				if(n==4){
					return children[5];
				}else{
					return getParentChild(4,RIGHT);
				}
			}else{
				if(n==6){
					return children[7];
				}else{
					return getParentChild(6,RIGHT);
				}
			}
		}
	}

	//pega area a direita da area passada
	private Area getAreaLeftChildArea(Area child){
		int n = indexOf(child);
		if(n<4){
			if(n<2){
				if(n==0){
					return getParentChild(1,LEFT);
				}else{
					return children[0];
				}
			}else{
				if(n==2){
					return getParentChild(3,LEFT);
				}else{
					return children[2];
				}
			}
		}else{
			if(n<6){
				if(n==4){
					return getParentChild(5,LEFT);
				}else{
					return children[4];
				}
			}else{
				if(n==6){
					return getParentChild(7,LEFT);
				}else{
					return children[6];
				}
			}
		}
	}

	private Area getAreaFrontChildArea(Area child){
		int n = indexOf(child);
		if(n>4){
			return children[n-4];
		}else{
			return getParentChild(n+4, FRONT);
		}
	}
	
	private Area getAreaBehindChildArea(Area child){
		int n = indexOf(child);
		if(n<4){
			return getParentChild(n+4, BEHIND);
		}else{
			return children[n-4];
		}
	}
	private int indexOf(Area child){
		for (int i = 0; i < 8; i++) {
			if(children[i]==child){
				return i;
			}
		}
		return -1;
	}
	private Area getChild(int n){
		return children[n];
	}
}
