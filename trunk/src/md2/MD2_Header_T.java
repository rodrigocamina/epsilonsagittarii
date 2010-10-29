package md2;
import java.io.IOException;

import util.LittleEndianDataInputStream;


public class MD2_Header_T {

	int ident;
	int version;

	int skinwidth;
	int skinheight;

	int framesize;

	int num_skins;
	int num_vertices;
	int num_st;
	int num_tris;
	int num_glcmds;
	int num_frames;

	int offset_skins;
	int offset_st;
	int offset_tris;
	int offset_frames;
	int offset_glcmds;
	int offset_end;
	
	int size = 68;
	
	public MD2_Header_T read(LittleEndianDataInputStream fi) throws IOException{

		ident = fi.readInt();
		version = fi.readInt();

		skinwidth = fi.readInt();
		skinheight = fi.readInt();

		framesize = fi.readInt();

		num_skins = fi.readInt();
		num_vertices = fi.readInt();
		num_st = fi.readInt();
		num_tris = fi.readInt();
		num_glcmds = fi.readInt();
		num_frames = fi.readInt();

		offset_skins = fi.readInt();
		offset_st = fi.readInt();
		offset_tris = fi.readInt();
		offset_frames = fi.readInt();
		offset_glcmds = fi.readInt();
		offset_end = fi.readInt();
		return this;
	}
}
