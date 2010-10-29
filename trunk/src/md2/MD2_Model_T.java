package md2;


public class MD2_Model_T {

	MD2_Header_T header;
	MD2_Skin_T[] skins;
	MD2_TexCoord_T[] texcoords;
	MD2_Triangle_T[] triangles;
	MD2_Frame_T[] frames;
	int[] glcmds;
	//TODO ???????GLuint tex_id??????????
	int tex_id;
	
	public MD2_Model_T() {
		header = new MD2_Header_T();
	}
	
}
