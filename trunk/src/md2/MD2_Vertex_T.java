package md2;
import java.io.IOException;

import util.LittleEndianDataInputStream;


public class MD2_Vertex_T {

	char[] v = new char[3];
	char normalIndex;
	
	public MD2_Vertex_T read(LittleEndianDataInputStream fi) throws IOException{
		v[0] = (char) (fi.readByte()&0x000000FF);
		v[1] = (char) (fi.readByte()&0x000000FF);
		v[2] = (char) (fi.readByte()&0x000000FF);
		normalIndex = (char) (fi.readByte()&0x000000FF);
		return this;
	}
}
