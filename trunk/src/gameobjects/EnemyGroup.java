package gameobjects;

import java.util.ArrayList;
import java.util.List;

import matematcbase.Vector3f;

public class EnemyGroup {

	List<EnemyShip> members = new ArrayList<EnemyShip>();
	int formation = 0;

	public static final int ARROWFORMATION = 0;
	public static final int BOXFORMATION = 0;
	public static final int LINEFORMATION = 0;
	public static final int COLUMNFORMATION = 0;

	public EnemyShip getLeader(){
		return members.get(0);
	}
	/*
	 if(n==0){
				return getLeader().getPosition();
			}
			int a = 2;
			int b = 0;
			int x = 0;
			while(x!=n){
				for (int i = 0; i < a; i++) {
					x++;
					b++;
					if(x==n){
						break;
					}
				}
				a++;
				b=0;
			}
			a-=2;
	 */
	public Vector3f getTargetPosition(int n){
		if(formation==ARROWFORMATION){
			if(n==0){
				return getLeader().getPosition();
			}
			int a = 2;
			int b = 0;
			int x = 0;
			Vector3f Lp = getLeader().getPosition();
			while(x!=n){
				for (int i = 0; i < a; i++) {
					x++;
					b++;
					if(x==n){
						break;
					}
				}
				b=0;
				a++;
			}
			a-=2;
			

			Vector3f result = new Vector3f(Lp);
			result = result.add(getLeader().rightV.multiply((-5*a)+(10*b)));
			result = result.add(getLeader().frontV.multiply((10*a)));
			return result;
		}else if(formation==BOXFORMATION){
			if(n==0){
				return getLeader().getPosition();
			}
			int a = 2;
			int aa = 4;
			while(aa<n){
				a++;
				aa=a*a;
			}
			int l = 0;
			int c = 1;
			for (int i = 1; i!=n ; i++) {
				c++;
				if(c>a){
					c=0;
					l++;
				}
			}
			Vector3f Lp = getLeader().getPosition();
			Vector3f result = new Vector3f(Lp);
			result = result.add(getLeader().rightV.multiply(c*10));
			result = result.add(getLeader().frontV.multiply(l*10));
			
			return result;//new Vector3f(Lp.x+(c*10), Lp.y, Lp.z+(l*10));
		}else if(formation==LINEFORMATION){
			if(n==0){
				return getLeader().getPosition();
			}
			int a = members.size()/3;
			int l = 0;
			int c = 1;
			for (int i = 1; i!=n ; i++) {
				c++;
				if(c>a){
					c=0;
					l++;
				}
			}
			Vector3f Lp = getLeader().getPosition();
			Vector3f result = new Vector3f(Lp);
			result = result.add(getLeader().rightV.multiply(c*10));
			result = result.add(getLeader().frontV.multiply(l*10));
			return result;//new Vector3f(Lp.x+(c*10), Lp.y, Lp.z+(l*10));
		}else {
			if(n==0){
				return getLeader().getPosition();
			}
			int a = members.size()/3;
			int l = 0;
			int c = 1;
			for (int i = 1; i!=n ; i++) {
				c++;
				if(c>a){
					c=0;
					l++;
				}
			}
			Vector3f Lp = getLeader().getPosition();
			Vector3f result = new Vector3f(Lp);
			result = result.add(getLeader().rightV.multiply(l*10));
			result = result.add(getLeader().frontV.multiply(c*10));
			return result;//new Vector3f(Lp.x+(l*10), Lp.y, Lp.z+(c*10));
		}
		
		
	}
	
}
