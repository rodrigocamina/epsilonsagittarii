package md2;


public class MD2_GLcmd_T {

	float s;
	float t;
	int index;
	
	public MD2_GLcmd_T(int[] pglcmds,int i){
		s = pglcmds[i];
		t = pglcmds[1+i];
		this.index = pglcmds[2+i];
		/*
		System.out.println("-----");
		System.out.println(s);
		System.out.println(t);
		System.out.println(index);*/
	}
	public MD2_GLcmd_T() {
	}
}
