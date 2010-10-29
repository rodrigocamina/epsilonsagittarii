package md2;

import util.LittleEndianDataInputStream;


public class MD2_Frame_T {

	float[] scale = new float[3];
	float[] translate = new float[3];
	char[] name = new char[16];
	MD2_Vertex_T[] verts;
	/*
  vec3_t scale;
  vec3_t translate;
  char name[16];
  struct md2_vertex_t *verts;
	*/
	public MD2_Frame_T read(LittleEndianDataInputStream fi){
		
		return this;
	}
}
