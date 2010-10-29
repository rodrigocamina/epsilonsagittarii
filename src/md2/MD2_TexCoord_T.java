package md2;
import java.io.IOException;

import util.LittleEndianDataInputStream;


public class MD2_TexCoord_T {

	short s;
	short t;
	
	public MD2_TexCoord_T read(LittleEndianDataInputStream fi) throws IOException{

		s = fi.readShort();
		t = fi.readShort();
		return this;
	}
}
