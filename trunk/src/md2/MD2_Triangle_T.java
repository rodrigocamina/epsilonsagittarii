package md2;
import java.io.IOException;

import util.LittleEndianDataInputStream;


public class MD2_Triangle_T {

	 int[] vertex = new int[3];
	 int[] st = new int[3];
	 
	 public MD2_Triangle_T read(LittleEndianDataInputStream fi) throws IOException{
		 vertex[0] = fi.readUnsignedShort();
		 vertex[1] = fi.readUnsignedShort();
		 vertex[2] = fi.readUnsignedShort();
		 st[0] = fi.readUnsignedShort();
		 st[1] = fi.readUnsignedShort();
		 st[2] = fi.readUnsignedShort();
		 return this;
	}
}
