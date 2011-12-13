package octtree;


public class AreaV1 {
/*
	private AreaV1 parent;
	private int elementsCount;
	private List<Obj8T> elements; 
	private AreaV1[] children;
	private int x;
	private int y;
	private int z;
	private int xEnd;
	private int yEnd;
	private int zEnd;
	private int layer;
	private AreaV1 upArea,downArea,leftArea,rightArea,frontArea,behindArea;
	//width, height, depth?profundidade
	
	public AreaV1(int layer,int startX, int startY, int startZ, int xEnd, int yEnd, int zEnd) {
		this.layer = layer;
		x = startX;
		y = startY;
		z = startZ;
		this.xEnd = xEnd;
		this.yEnd = yEnd;
		this.zEnd = zEnd;
	}
	
	private void split(){
		int x2 = x+((xEnd-x)>>1);
		int y2 = y+((yEnd-y)>>1);
		int z2 = z+((zEnd-z)>>1);
		children= new AreaV1[8];
		children[0] = new AreaV1(layer-1,x, y, z, x2, y2, z2);
		children[1] = new AreaV1(layer-1,x2, y, z, xEnd, y2, z2);
		children[2] = new AreaV1(layer-1,x, y2, z, x2, yEnd, z2);
		children[3] = new AreaV1(layer-1,x2, y2, z, xEnd, yEnd, z2);
		children[4] = new AreaV1(layer-1,x, y, z2, x2, y2, zEnd);
		children[5] = new AreaV1(layer-1,x2, y, z2, xEnd, y2, zEnd);
		children[6] = new AreaV1(layer-1,x, y2, z2, x2, yEnd, zEnd);
		children[7] = new AreaV1(layer-1,x2, y2, z2, xEnd, yEnd, zEnd);
		for (int i = 0; i < 8; i++) {
			children[i].parent = this;
			setAreaAboveChildArea(children[i]);
			setAreaBehindChildArea(children[i]);
			setAreaLeftChildArea(children[i]);
		}
	}
	
	private boolean touches(Obj8T obj){
		return 
		x<=obj.getX()+obj.getWidth() &&xEnd>=obj.getX()&&
		y<=obj.getY()+obj.getHeight()&&yEnd>=obj.getY()&&
		z<=obj.getZ()+obj.getDepth() &&zEnd>=obj.getZ();
	}
	
	private boolean surrounds(Obj8T obj){
		return 
		x<=obj.getX()&&xEnd>=obj.getX()+obj.getWidth() &&
		y<=obj.getY()&&yEnd>=obj.getY()+obj.getHeight()&&
		z<=obj.getZ()&&zEnd>=obj.getZ()+obj.getDepth();
	}
	
	public static void updateElement(Obj8T obj){
		
		if(obj.getArea().surrounds(obj)){
			AreaV1 x = obj.getArea().downgradeElementOnTree(obj);
			if(x!=obj.getArea()){
				obj.getArea().elements.remove(obj);
				x.elements.add(obj);
				obj.setArea(x);
			}
		}else{
			AreaV1 x = obj.getArea().upgradeElementOnTree(obj);
			obj.getArea().elements.remove(obj);
			x.elements.add(obj);
			obj.setArea(x);
		}
	}
	
	//procura por um nodo em nivel mais baixo que o atual para comportar o objeto
	private AreaV1 downgradeElementOnTree(Obj8T obj){
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
	private AreaV1 upgradeElementOnTree(Obj8T obj){
		if(parent.surrounds(obj)){
			return parent;
		}else{
			return parent.upgradeElementOnTree(obj);
		}
	}
	
	//adiciona um nodo novo a arvore
	public AreaV1 addElement(Obj8T obj){
		downgradeElementOnTree(obj).elements.add(obj);
		return this;
	}
	
	//pega area acima da area passada
	private void setAreaAboveChildArea(AreaV1 child){
		int n = indexOf(child);
		if(n<4){
			if(n<2){
				if(n==0){
					if(upArea!=null){
						child.upArea = upArea.children[2];
						upArea.children[2].downArea = child;
					}else{
						child.upArea =null;	
					}
				}else{
					if(upArea!=null){
						child.upArea = upArea.children[3];
						upArea.children[3].downArea = child;
					}else{
						child.upArea =null;	
					}
				}
			}else{
				if(n==2){
					child.upArea = children[0];
					children[0].downArea = child;
				}else{
					child.upArea = children[1];
					children[1].downArea = child;
				}
			}
		}else{
			if(n<6){
				if(n==4){
					if(upArea!=null){
						child.upArea = upArea.children[6];
						upArea.children[6].downArea = child;
					}else{
						child.upArea =null;	
					}
				}else{
					if(upArea!=null){
						child.upArea = upArea.children[7];
						upArea.children[7].downArea = child;
					}else{
						child.upArea =null;	
					}
				}
			}else{
				if(n==6){
					child.upArea =  children[4];
					children[4].downArea = child;
				}else{
					child.upArea =  children[5];
					children[5].downArea = child;
				}
			}
		}
	}

	//pega area a direita da area passada
	private void setAreaLeftChildArea(AreaV1 child){
		int n = indexOf(child);
		if(n<4){
			if(n<2){
				if(n==0){
					if(leftArea!=null){
						child.leftArea = leftArea.children[1];
						leftArea.children[1].rightArea = child;
					}else{
						child.leftArea =null;	
					}
				}else{
					child.leftArea = children[0];
					children[0].rightArea = child;
				}
			}else{
				if(n==2){
					if(leftArea!=null){
						child.leftArea = leftArea.children[3];
						leftArea.children[3].rightArea = child;
					}else{
						child.leftArea =null;	
					}
				}else{
					child.leftArea = children[2];
					children[2].rightArea = child;
				}
			}
		}else{
			if(n<6){
				if(n==4){
					if(leftArea!=null){
						child.leftArea = leftArea.children[5];
						leftArea.children[5].rightArea = child;
					}else{
						child.leftArea =null;	
					}
				}else{
					child.leftArea = children[4];
					children[4].rightArea = child;
				}
			}else{
				if(n==6){
					if(leftArea!=null){
						child.leftArea = leftArea.children[7];
						leftArea.children[7].rightArea = child;
					}else{
						child.leftArea =null;	
					}
				}else{
					child.leftArea = children[6];
					children[6].rightArea = child;
				}
			}
		}
	}
	
	private void setAreaBehindChildArea(AreaV1 child){
		int n = indexOf(child);
		if(n<4){
			if(behindArea!=null){
				child.behindArea = behindArea.children[n+4];
				behindArea.children[n+4].frontArea = child;
			}
		}else{
			child.behindArea =  children[n-4];
			children[n-4].frontArea = child;
		}
	}
	
	private int indexOf(AreaV1 child){
		for (int i = 0; i < 8; i++) {
			if(children[i]==child){
				return i;
			}
		}
		return -1;
	}
	*/
	
}
