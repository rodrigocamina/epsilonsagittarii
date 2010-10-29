package md2;
import java.io.IOException;

import util.LittleEndianDataInputStream;



public class MD2_Skin_T {

	char[] name = new char[64];
	
	public MD2_Skin_T read(LittleEndianDataInputStream fi) throws IOException{
		name[0] = (char)fi.readByte();
		name[1] = (char)fi.readByte();
		name[2] = (char)fi.readByte();
		name[3] = (char)fi.readByte();
		name[4] = (char)fi.readByte();
		name[5] = (char)fi.readByte();
		name[6] = (char)fi.readByte();
		name[7] = (char)fi.readByte();
		name[8] = (char)fi.readByte();
		name[9] = (char)fi.readByte();
		name[10] = (char)fi.readByte();
		name[11] = (char)fi.readByte();
		name[12] = (char)fi.readByte();
		name[13] = (char)fi.readByte();
		name[14] = (char)fi.readByte();
		name[15] = (char)fi.readByte();
		name[16] = (char)fi.readByte();
		name[17] = (char)fi.readByte();
		name[18] = (char)fi.readByte();
		name[19] = (char)fi.readByte();
		name[20] = (char)fi.readByte();
		name[21] = (char)fi.readByte();
		name[22] = (char)fi.readByte();
		name[23] = (char)fi.readByte();
		name[24] = (char)fi.readByte();
		name[25] = (char)fi.readByte();
		name[26] = (char)fi.readByte();
		name[27] = (char)fi.readByte();
		name[28] = (char)fi.readByte();
		name[29] = (char)fi.readByte();
		name[30] = (char)fi.readByte();
		name[31] = (char)fi.readByte();
		name[32] = (char)fi.readByte();
		name[33] = (char)fi.readByte();
		name[34] = (char)fi.readByte();
		name[35] = (char)fi.readByte();
		name[36] = (char)fi.readByte();
		name[37] = (char)fi.readByte();
		name[38] = (char)fi.readByte();
		name[39] = (char)fi.readByte();
		name[40] = (char)fi.readByte();
		name[41] = (char)fi.readByte();
		name[42] = (char)fi.readByte();
		name[43] = (char)fi.readByte();
		name[44] = (char)fi.readByte();
		name[45] = (char)fi.readByte();
		name[46] = (char)fi.readByte();
		name[47] = (char)fi.readByte();
		name[48] = (char)fi.readByte();
		name[49] = (char)fi.readByte();
		name[50] = (char)fi.readByte();
		name[51] = (char)fi.readByte();
		name[52] = (char)fi.readByte();
		name[53] = (char)fi.readByte();
		name[54] = (char)fi.readByte();
		name[55] = (char)fi.readByte();
		name[56] = (char)fi.readByte();
		name[57] = (char)fi.readByte();
		name[58] = (char)fi.readByte();
		name[59] = (char)fi.readByte();
		name[60] = (char)fi.readByte();
		name[61] = (char)fi.readByte();
		name[62] = (char)fi.readByte();
		name[63] = (char)fi.readByte();
		return this;
	}
}
