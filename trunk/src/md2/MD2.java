package md2;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

import main.CanvasGame;

import util.LittleEndianDataInputStream;
import util.Tarefa;

import com.sun.opengl.util.texture.Texture;

public class MD2 {
	/*
	 * md2.c -- md2 model loader last modification: aug. 14, 2007
	 * 
	 * Copyright (c) 2005-2007 David HENRY
	 * 
	 * Permission is hereby granted, free of charge, to any person obtaining a
	 * copy of this software and associated documentation files (the
	 * "Software"), to deal in the Software without restriction, including
	 * without limitation the rights to use, copy, modify, merge, publish,
	 * distribute, sublicense, and/or sell copies of the Software, and to permit
	 * persons to whom the Software is furnished to do so, subject to the
	 * following conditions:
	 * 
	 * The above copyright notice and this permission notice shall be included
	 * in all copies or substantial portions of the Software.
	 * 
	 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
	 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
	 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN
	 * NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
	 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
	 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE
	 * USE OR OTHER DEALINGS IN THE SOFTWARE.
	 * 
	 * gcc -Wall -ansi -lGL -lGLU -lglut md2.c -o md2
	 */

	/* Vector */
	// typedef float float[][3];

	/* Table of precalculated normals */
	FloatBuffer[] anorms_table = new FloatBuffer[162];

	/*** An MD2 model ***/

	int n = 0; /* The current frame */
	float interp = 0.0f;
	double current_time;
	long last_time = 0;
	LittleEndianDataInputStream fp;
	MD2_Model_T mdl = new MD2_Model_T();
    private Texture [] textures;
    private int choosenTexture = 0;
	/**
	 * Load an MD2 model from file.
	 * 
	 * Note: MD2 format stores model's data in little-endian ordering. On
	 * big-endian machines, you'll have to perform proper conversions.
	 * 
	 * @throws Exception
	 */
	public MD2(String filename,String[] texturenames) throws Exception {
		fp = new LittleEndianDataInputStream(getClass().getResourceAsStream(
				filename));

		if (fp == null) {
			throw new Exception("Error: File not found");
		}

		/* Read header */
		mdl.header.read(fp);

		if ((mdl.header.ident != 844121161) || (mdl.header.version != 8)) {
			/* Error! */
			throw new Exception("Error: Bad version or identifier");
		}

		/* Memory allocations */
		mdl.skins = new MD2_Skin_T[mdl.header.num_skins];
		mdl.texcoords = new MD2_TexCoord_T[mdl.header.num_st];
		mdl.triangles = new MD2_Triangle_T[mdl.header.num_tris];
		mdl.frames = new MD2_Frame_T[mdl.header.num_frames];
		mdl.glcmds = new int[mdl.header.num_glcmds];

		/* Read model data */
		List<Tarefa> tarefasDeCarregamento = new ArrayList<Tarefa>();

		tarefasDeCarregamento.add(new Tarefa(mdl.header.offset_skins) {
			@Override
			public void script() {
				try {
					fp.seek(mdl.header.offset_skins);
					//System.out.println("loading skins");
					for (int j = 0; j < mdl.header.num_skins; j++) {
						mdl.skins[j] = new MD2_Skin_T();
						mdl.skins[j].read(fp);
					}
				} catch (Exception e) {
				}
			}
		});

		tarefasDeCarregamento.add(new Tarefa(mdl.header.offset_st) {
			@Override
			public void script() {
				try {
					fp.seek(mdl.header.offset_st);
					//System.out.println("loading st");
					for (int j = 0; j < mdl.header.num_st; j++) {
						mdl.texcoords[j] = new MD2_TexCoord_T();
						mdl.texcoords[j].read(fp);
					}

				} catch (Exception e) {
				}
			}
		});
		
		tarefasDeCarregamento.add(new Tarefa(mdl.header.offset_tris) {
			@Override
			public void script() {
				try {
					fp.seek(mdl.header.offset_tris);
					//System.out.println("loading tris");
					for (int j = 0; j < mdl.header.num_tris; j++) {
						mdl.triangles[j] = new MD2_Triangle_T();
						mdl.triangles[j].read(fp);
					}

				} catch (Exception e) {
				}
			}
		});

		tarefasDeCarregamento.add(new Tarefa(mdl.header.offset_glcmds) {
			@Override
			public void script() {
				try {
					
					fp.seek(mdl.header.offset_glcmds);
					//System.out.println("loading commands");
					for (int j = 0; j < mdl.header.num_glcmds; j++) {
						mdl.glcmds[j] = fp.readInt();
					}

				} catch (Exception e) {
				}
			}
		});
		
		tarefasDeCarregamento.add(new Tarefa(mdl.header.offset_frames) {
			@Override
			public void script() {
				try {
					fp.seek(mdl.header.offset_frames);
					//System.out.println("loading frames");
					for (int i = 0; i < mdl.header.num_frames; ++i) {
						//System.out.println("loading frame "+i);
						/* Memory allocation for vertices of this frame */
						mdl.frames[i] = new MD2_Frame_T();
						/* Read frame data */
						mdl.frames[i].scale[0] = fp.readFloat();
						mdl.frames[i].scale[1] = fp.readFloat();
						mdl.frames[i].scale[2] = fp.readFloat();
						mdl.frames[i].translate[0] = fp.readFloat();
						mdl.frames[i].translate[1] = fp.readFloat();
						mdl.frames[i].translate[2] = fp.readFloat();
						mdl.frames[i].name[0] = (char) fp.readByte();
						mdl.frames[i].name[1] = (char) fp.readByte();
						mdl.frames[i].name[2] = (char) fp.readByte();
						mdl.frames[i].name[3] = (char) fp.readByte();
						mdl.frames[i].name[4] = (char) fp.readByte();
						mdl.frames[i].name[5] = (char) fp.readByte();
						mdl.frames[i].name[6] = (char) fp.readByte();
						mdl.frames[i].name[7] = (char) fp.readByte();
						mdl.frames[i].name[8] = (char) fp.readByte();
						mdl.frames[i].name[9] = (char) fp.readByte();
						mdl.frames[i].name[10] = (char) fp.readByte();
						mdl.frames[i].name[11] = (char) fp.readByte();
						mdl.frames[i].name[12] = (char) fp.readByte();
						mdl.frames[i].name[13] = (char) fp.readByte();
						mdl.frames[i].name[14] = (char) fp.readByte();
						mdl.frames[i].name[15] = (char) fp.readByte();
						//System.out.println("loading vertices "+mdl.header.num_vertices);
						mdl.frames[i].verts = new MD2_Vertex_T[mdl.header.num_vertices];
						for (int j = 0; j < mdl.header.num_vertices; j++) {
							mdl.frames[i].verts[j] = new MD2_Vertex_T();
							mdl.frames[i].verts[j].read(fp);
						}
					}

				} catch (Exception e) {
				}
			}
		});
		while(tarefasDeCarregamento.size()>0){
			int best = 0;
			for (int j = 1; j < tarefasDeCarregamento.size(); j++) {
				if(tarefasDeCarregamento.get(j).prioridade<tarefasDeCarregamento.get(best).prioridade){
					best = j;
				}
			}
			tarefasDeCarregamento.remove(best).script();
		}

		loadAnorms_Table();

	    textures = new Texture [texturenames.length];
	    for (int j = 0; j < texturenames.length; j++) {
	    	try{
	    		textures [j] = CanvasGame.loadTexture(texturenames[j]);
	    	}catch (Exception e) {
				e.printStackTrace();
			}
		}
	    
	}

	private void loadAnorms_Table(){
		//162
		anorms_table[0] = FloatBuffer.allocate(3).put(new float[]{ -0.525731f,  0.000000f,  0.850651f });
		anorms_table[1] = FloatBuffer.allocate(3).put(new float[]{ -0.442863f,  0.238856f,  0.864188f });
		anorms_table[2] = FloatBuffer.allocate(3).put(new float[]{ -0.295242f,  0.000000f,  0.955423f });
		anorms_table[3] = FloatBuffer.allocate(3).put(new float[]{ -0.309017f,  0.500000f,  0.809017f });
		anorms_table[4] = FloatBuffer.allocate(3).put(new float[]{ -0.162460f,  0.262866f,  0.951056f });
		anorms_table[5] = FloatBuffer.allocate(3).put(new float[]{  0.000000f,  0.000000f,  1.000000f });
		anorms_table[6] = FloatBuffer.allocate(3).put(new float[]{  0.000000f,  0.850651f,  0.525731f });
		anorms_table[7] = FloatBuffer.allocate(3).put(new float[]{ -0.147621f,  0.716567f,  0.681718f });
		anorms_table[8] = FloatBuffer.allocate(3).put(new float[]{  0.147621f,  0.716567f,  0.681718f });
		anorms_table[9] = FloatBuffer.allocate(3).put(new float[]{  0.000000f,  0.525731f,  0.850651f });
		anorms_table[10] = FloatBuffer.allocate(3).put(new float[]{  0.309017f,  0.500000f,  0.809017f });
		anorms_table[11] = FloatBuffer.allocate(3).put(new float[]{  0.525731f,  0.000000f,  0.850651f });
		anorms_table[12] = FloatBuffer.allocate(3).put(new float[]{  0.295242f,  0.000000f,  0.955423f });
		anorms_table[13] = FloatBuffer.allocate(3).put(new float[]{  0.442863f,  0.238856f,  0.864188f });
		anorms_table[14] = FloatBuffer.allocate(3).put(new float[]{  0.162460f,  0.262866f,  0.951056f });
		anorms_table[15] = FloatBuffer.allocate(3).put(new float[]{ -0.681718f,  0.147621f,  0.716567f });
		anorms_table[16] = FloatBuffer.allocate(3).put(new float[]{ -0.809017f,  0.309017f,  0.500000f });
		anorms_table[17] = FloatBuffer.allocate(3).put(new float[]{ -0.587785f,  0.425325f,  0.688191f });
		anorms_table[18] = FloatBuffer.allocate(3).put(new float[]{ -0.850651f,  0.525731f,  0.000000f });
		anorms_table[19] = FloatBuffer.allocate(3).put(new float[]{ -0.864188f,  0.442863f,  0.238856f });
		anorms_table[20] = FloatBuffer.allocate(3).put(new float[]{ -0.716567f,  0.681718f,  0.147621f });
		anorms_table[21] = FloatBuffer.allocate(3).put(new float[]{ -0.688191f,  0.587785f,  0.425325f });
		anorms_table[22] = FloatBuffer.allocate(3).put(new float[]{ -0.500000f,  0.809017f,  0.309017f });
		anorms_table[23] = FloatBuffer.allocate(3).put(new float[]{ -0.238856f,  0.864188f,  0.442863f });
		anorms_table[24] = FloatBuffer.allocate(3).put(new float[]{ -0.425325f,  0.688191f,  0.587785f });
		anorms_table[25] = FloatBuffer.allocate(3).put(new float[]{ -0.716567f,  0.681718f, -0.147621f });
		anorms_table[26] = FloatBuffer.allocate(3).put(new float[]{ -0.500000f,  0.809017f, -0.309017f });
		anorms_table[27] = FloatBuffer.allocate(3).put(new float[]{ -0.525731f,  0.850651f,  0.000000f });
		anorms_table[28] = FloatBuffer.allocate(3).put(new float[]{  0.000000f,  0.850651f, -0.525731f });
		anorms_table[29] = FloatBuffer.allocate(3).put(new float[]{ -0.238856f,  0.864188f, -0.442863f });
		anorms_table[30] = FloatBuffer.allocate(3).put(new float[]{  0.000000f,  0.955423f, -0.295242f });
		anorms_table[31] = FloatBuffer.allocate(3).put(new float[]{ -0.262866f,  0.951056f, -0.162460f });
		anorms_table[32] = FloatBuffer.allocate(3).put(new float[]{  0.000000f,  1.000000f,  0.000000f });
		anorms_table[33] = FloatBuffer.allocate(3).put(new float[]{  0.000000f,  0.955423f,  0.295242f });
		anorms_table[34] = FloatBuffer.allocate(3).put(new float[]{ -0.262866f,  0.951056f,  0.162460f });
		anorms_table[35] = FloatBuffer.allocate(3).put(new float[]{  0.238856f,  0.864188f,  0.442863f });
		anorms_table[36] = FloatBuffer.allocate(3).put(new float[]{  0.262866f,  0.951056f,  0.162460f });
		anorms_table[37] = FloatBuffer.allocate(3).put(new float[]{  0.500000f,  0.809017f,  0.309017f });
		anorms_table[38] = FloatBuffer.allocate(3).put(new float[]{  0.238856f,  0.864188f, -0.442863f });
		anorms_table[39] = FloatBuffer.allocate(3).put(new float[]{  0.262866f,  0.951056f, -0.162460f });
		anorms_table[40] = FloatBuffer.allocate(3).put(new float[]{  0.500000f,  0.809017f, -0.309017f });
		anorms_table[41] = FloatBuffer.allocate(3).put(new float[]{  0.850651f,  0.525731f,  0.000000f });
		anorms_table[42] = FloatBuffer.allocate(3).put(new float[]{  0.716567f,  0.681718f,  0.147621f });
		anorms_table[43] = FloatBuffer.allocate(3).put(new float[]{  0.716567f,  0.681718f, -0.147621f });
		anorms_table[44] = FloatBuffer.allocate(3).put(new float[]{  0.525731f,  0.850651f,  0.000000f });
		anorms_table[45] = FloatBuffer.allocate(3).put(new float[]{  0.425325f,  0.688191f,  0.587785f });
		anorms_table[46] = FloatBuffer.allocate(3).put(new float[]{  0.864188f,  0.442863f,  0.238856f });
		anorms_table[47] = FloatBuffer.allocate(3).put(new float[]{  0.688191f,  0.587785f,  0.425325f });
		anorms_table[48] = FloatBuffer.allocate(3).put(new float[]{  0.809017f,  0.309017f,  0.500000f });
		anorms_table[49] = FloatBuffer.allocate(3).put(new float[]{  0.681718f,  0.147621f,  0.716567f });
		anorms_table[50] = FloatBuffer.allocate(3).put(new float[]{  0.587785f,  0.425325f,  0.688191f });
		anorms_table[51] = FloatBuffer.allocate(3).put(new float[]{  0.955423f,  0.295242f,  0.000000f });
		anorms_table[52] = FloatBuffer.allocate(3).put(new float[]{  1.000000f,  0.000000f,  0.000000f });
		anorms_table[53] = FloatBuffer.allocate(3).put(new float[]{  0.951056f,  0.162460f,  0.262866f });
		anorms_table[54] = FloatBuffer.allocate(3).put(new float[]{  0.850651f, -0.525731f,  0.000000f });
		anorms_table[55] = FloatBuffer.allocate(3).put(new float[]{  0.955423f, -0.295242f,  0.000000f });
		anorms_table[56] = FloatBuffer.allocate(3).put(new float[]{  0.864188f, -0.442863f,  0.238856f });
		anorms_table[57] = FloatBuffer.allocate(3).put(new float[]{  0.951056f, -0.162460f,  0.262866f });
		anorms_table[58] = FloatBuffer.allocate(3).put(new float[]{  0.809017f, -0.309017f,  0.500000f });
		anorms_table[59] = FloatBuffer.allocate(3).put(new float[]{  0.681718f, -0.147621f,  0.716567f });
		anorms_table[60] = FloatBuffer.allocate(3).put(new float[]{  0.850651f,  0.000000f,  0.525731f });
		anorms_table[61] = FloatBuffer.allocate(3).put(new float[]{  0.864188f,  0.442863f, -0.238856f });
		anorms_table[62] = FloatBuffer.allocate(3).put(new float[]{  0.809017f,  0.309017f, -0.500000f });
		anorms_table[63] = FloatBuffer.allocate(3).put(new float[]{  0.951056f,  0.162460f, -0.262866f });
		anorms_table[64] = FloatBuffer.allocate(3).put(new float[]{  0.525731f,  0.000000f, -0.850651f });
		anorms_table[65] = FloatBuffer.allocate(3).put(new float[]{  0.681718f,  0.147621f, -0.716567f });
		anorms_table[66] = FloatBuffer.allocate(3).put(new float[]{  0.681718f, -0.147621f, -0.716567f });
		anorms_table[67] = FloatBuffer.allocate(3).put(new float[]{  0.850651f,  0.000000f, -0.525731f });
		anorms_table[68] = FloatBuffer.allocate(3).put(new float[]{  0.809017f, -0.309017f, -0.500000f });
		anorms_table[69] = FloatBuffer.allocate(3).put(new float[]{  0.864188f, -0.442863f, -0.238856f });
		anorms_table[70] = FloatBuffer.allocate(3).put(new float[]{  0.951056f, -0.162460f, -0.262866f });
		anorms_table[71] = FloatBuffer.allocate(3).put(new float[]{  0.147621f,  0.716567f, -0.681718f });
		anorms_table[72] = FloatBuffer.allocate(3).put(new float[]{  0.309017f,  0.500000f, -0.809017f });
		anorms_table[73] = FloatBuffer.allocate(3).put(new float[]{  0.425325f,  0.688191f, -0.587785f });
		anorms_table[74] = FloatBuffer.allocate(3).put(new float[]{  0.442863f,  0.238856f, -0.864188f });
		anorms_table[75] = FloatBuffer.allocate(3).put(new float[]{  0.587785f,  0.425325f, -0.688191f });
		anorms_table[76] = FloatBuffer.allocate(3).put(new float[]{  0.688191f,  0.587785f, -0.425325f });
		anorms_table[77] = FloatBuffer.allocate(3).put(new float[]{ -0.147621f,  0.716567f, -0.681718f });
		anorms_table[78] = FloatBuffer.allocate(3).put(new float[]{ -0.309017f,  0.500000f, -0.809017f });
		anorms_table[79] = FloatBuffer.allocate(3).put(new float[]{  0.000000f,  0.525731f, -0.850651f });
		anorms_table[80] = FloatBuffer.allocate(3).put(new float[]{ -0.525731f,  0.000000f, -0.850651f });
		anorms_table[81] = FloatBuffer.allocate(3).put(new float[]{ -0.442863f,  0.238856f, -0.864188f });
		anorms_table[82] = FloatBuffer.allocate(3).put(new float[]{ -0.295242f,  0.000000f, -0.955423f });
		anorms_table[83] = FloatBuffer.allocate(3).put(new float[]{ -0.162460f,  0.262866f, -0.951056f });
		anorms_table[84] = FloatBuffer.allocate(3).put(new float[]{  0.000000f,  0.000000f, -1.000000f });
		anorms_table[85] = FloatBuffer.allocate(3).put(new float[]{  0.295242f,  0.000000f, -0.955423f });
		anorms_table[86] = FloatBuffer.allocate(3).put(new float[]{  0.162460f,  0.262866f, -0.951056f });
		anorms_table[87] = FloatBuffer.allocate(3).put(new float[]{ -0.442863f, -0.238856f, -0.864188f });
		anorms_table[88] = FloatBuffer.allocate(3).put(new float[]{ -0.309017f, -0.500000f, -0.809017f });
		anorms_table[89] = FloatBuffer.allocate(3).put(new float[]{ -0.162460f, -0.262866f, -0.951056f });
		anorms_table[90] = FloatBuffer.allocate(3).put(new float[]{  0.000000f, -0.850651f, -0.525731f });
		anorms_table[91] = FloatBuffer.allocate(3).put(new float[]{ -0.147621f, -0.716567f, -0.681718f });
		anorms_table[92] = FloatBuffer.allocate(3).put(new float[]{  0.147621f, -0.716567f, -0.681718f });
		anorms_table[93] = FloatBuffer.allocate(3).put(new float[]{  0.000000f, -0.525731f, -0.850651f });
		anorms_table[94] = FloatBuffer.allocate(3).put(new float[]{  0.309017f, -0.500000f, -0.809017f });
		anorms_table[95] = FloatBuffer.allocate(3).put(new float[]{  0.442863f, -0.238856f, -0.864188f });
		anorms_table[96] = FloatBuffer.allocate(3).put(new float[]{  0.162460f, -0.262866f, -0.951056f });
		anorms_table[97] = FloatBuffer.allocate(3).put(new float[]{  0.238856f, -0.864188f, -0.442863f });
		anorms_table[98] = FloatBuffer.allocate(3).put(new float[]{  0.500000f, -0.809017f, -0.309017f });
		anorms_table[99] = FloatBuffer.allocate(3).put(new float[]{  0.425325f, -0.688191f, -0.587785f });
		anorms_table[100] = FloatBuffer.allocate(3).put(new float[]{  0.716567f, -0.681718f, -0.147621f });
		anorms_table[101] = FloatBuffer.allocate(3).put(new float[]{  0.688191f, -0.587785f, -0.425325f });
		anorms_table[102] = FloatBuffer.allocate(3).put(new float[]{  0.587785f, -0.425325f, -0.688191f });
		anorms_table[103] = FloatBuffer.allocate(3).put(new float[]{  0.000000f, -0.955423f, -0.295242f });
		anorms_table[104] = FloatBuffer.allocate(3).put(new float[]{  0.000000f, -1.000000f,  0.000000f });
		anorms_table[105] = FloatBuffer.allocate(3).put(new float[]{  0.262866f, -0.951056f, -0.162460f });
		anorms_table[106] = FloatBuffer.allocate(3).put(new float[]{  0.000000f, -0.850651f,  0.525731f });
		anorms_table[107] = FloatBuffer.allocate(3).put(new float[]{  0.000000f, -0.955423f,  0.295242f });
		anorms_table[108] = FloatBuffer.allocate(3).put(new float[]{  0.238856f, -0.864188f,  0.442863f });
		anorms_table[109] = FloatBuffer.allocate(3).put(new float[]{  0.262866f, -0.951056f,  0.162460f });
		anorms_table[110] = FloatBuffer.allocate(3).put(new float[]{  0.500000f, -0.809017f,  0.309017f });
		anorms_table[111] = FloatBuffer.allocate(3).put(new float[]{  0.716567f, -0.681718f,  0.147621f });
		anorms_table[112] = FloatBuffer.allocate(3).put(new float[]{  0.525731f, -0.850651f,  0.000000f });
		anorms_table[113] = FloatBuffer.allocate(3).put(new float[]{ -0.238856f, -0.864188f, -0.442863f });
		anorms_table[114] = FloatBuffer.allocate(3).put(new float[]{ -0.500000f, -0.809017f, -0.309017f });
		anorms_table[115] = FloatBuffer.allocate(3).put(new float[]{ -0.262866f, -0.951056f, -0.162460f });
		anorms_table[116] = FloatBuffer.allocate(3).put(new float[]{ -0.850651f, -0.525731f,  0.000000f });
		anorms_table[117] = FloatBuffer.allocate(3).put(new float[]{ -0.716567f, -0.681718f, -0.147621f });
		anorms_table[118] = FloatBuffer.allocate(3).put(new float[]{ -0.716567f, -0.681718f,  0.147621f });
		anorms_table[119] = FloatBuffer.allocate(3).put(new float[]{ -0.525731f, -0.850651f,  0.000000f });
		anorms_table[120] = FloatBuffer.allocate(3).put(new float[]{ -0.500000f, -0.809017f,  0.309017f });
		anorms_table[121] = FloatBuffer.allocate(3).put(new float[]{ -0.238856f, -0.864188f,  0.442863f });
		anorms_table[122] = FloatBuffer.allocate(3).put(new float[]{ -0.262866f, -0.951056f,  0.162460f });
		anorms_table[123] = FloatBuffer.allocate(3).put(new float[]{ -0.864188f, -0.442863f,  0.238856f });
		anorms_table[124] = FloatBuffer.allocate(3).put(new float[]{ -0.809017f, -0.309017f,  0.500000f });
		anorms_table[125] = FloatBuffer.allocate(3).put(new float[]{ -0.688191f, -0.587785f,  0.425325f });
		anorms_table[126] = FloatBuffer.allocate(3).put(new float[]{ -0.681718f, -0.147621f,  0.716567f });
		anorms_table[127] = FloatBuffer.allocate(3).put(new float[]{ -0.442863f, -0.238856f,  0.864188f });
		anorms_table[128] = FloatBuffer.allocate(3).put(new float[]{ -0.587785f, -0.425325f,  0.688191f });
		anorms_table[129] = FloatBuffer.allocate(3).put(new float[]{ -0.309017f, -0.500000f,  0.809017f });
		anorms_table[130] = FloatBuffer.allocate(3).put(new float[]{ -0.147621f, -0.716567f,  0.681718f });
		anorms_table[131] = FloatBuffer.allocate(3).put(new float[]{ -0.425325f, -0.688191f,  0.587785f });
		anorms_table[132] = FloatBuffer.allocate(3).put(new float[]{ -0.162460f, -0.262866f,  0.951056f });
		anorms_table[133] = FloatBuffer.allocate(3).put(new float[]{  0.442863f, -0.238856f,  0.864188f });
		anorms_table[134] = FloatBuffer.allocate(3).put(new float[]{  0.162460f, -0.262866f,  0.951056f });
		anorms_table[135] = FloatBuffer.allocate(3).put(new float[]{  0.309017f, -0.500000f,  0.809017f });
		anorms_table[136] = FloatBuffer.allocate(3).put(new float[]{  0.147621f, -0.716567f,  0.681718f });
		anorms_table[137] = FloatBuffer.allocate(3).put(new float[]{  0.000000f, -0.525731f,  0.850651f });
		anorms_table[138] = FloatBuffer.allocate(3).put(new float[]{  0.425325f, -0.688191f,  0.587785f });
		anorms_table[139] = FloatBuffer.allocate(3).put(new float[]{  0.587785f, -0.425325f,  0.688191f });
		anorms_table[140] = FloatBuffer.allocate(3).put(new float[]{  0.688191f, -0.587785f,  0.425325f });
		anorms_table[141] = FloatBuffer.allocate(3).put(new float[]{ -0.955423f,  0.295242f,  0.000000f });
		anorms_table[142] = FloatBuffer.allocate(3).put(new float[]{ -0.951056f,  0.162460f,  0.262866f });
		anorms_table[143] = FloatBuffer.allocate(3).put(new float[]{ -1.000000f,  0.000000f,  0.000000f });
		anorms_table[144] = FloatBuffer.allocate(3).put(new float[]{ -0.850651f,  0.000000f,  0.525731f });
		anorms_table[145] = FloatBuffer.allocate(3).put(new float[]{ -0.955423f, -0.295242f,  0.000000f });
		anorms_table[146] = FloatBuffer.allocate(3).put(new float[]{ -0.951056f, -0.162460f,  0.262866f });
		anorms_table[147] = FloatBuffer.allocate(3).put(new float[]{ -0.864188f,  0.442863f, -0.238856f });
		anorms_table[148] = FloatBuffer.allocate(3).put(new float[]{ -0.951056f,  0.162460f, -0.262866f });
		anorms_table[149] = FloatBuffer.allocate(3).put(new float[]{ -0.809017f,  0.309017f, -0.500000f });
		anorms_table[150] = FloatBuffer.allocate(3).put(new float[]{ -0.864188f, -0.442863f, -0.238856f });
		anorms_table[151] = FloatBuffer.allocate(3).put(new float[]{ -0.951056f, -0.162460f, -0.262866f });
		anorms_table[152] = FloatBuffer.allocate(3).put(new float[]{ -0.809017f, -0.309017f, -0.500000f });
		anorms_table[153] = FloatBuffer.allocate(3).put(new float[]{ -0.681718f,  0.147621f, -0.716567f });
		anorms_table[154] = FloatBuffer.allocate(3).put(new float[]{ -0.681718f, -0.147621f, -0.716567f });
		anorms_table[155] = FloatBuffer.allocate(3).put(new float[]{ -0.850651f,  0.000000f, -0.525731f });
		anorms_table[156] = FloatBuffer.allocate(3).put(new float[]{ -0.688191f,  0.587785f, -0.425325f });
		anorms_table[157] = FloatBuffer.allocate(3).put(new float[]{ -0.587785f,  0.425325f, -0.688191f });
		anorms_table[158] = FloatBuffer.allocate(3).put(new float[]{ -0.425325f,  0.688191f, -0.587785f });
		anorms_table[159] = FloatBuffer.allocate(3).put(new float[]{ -0.425325f, -0.688191f, -0.587785f });
		anorms_table[160] = FloatBuffer.allocate(3).put(new float[]{ -0.587785f, -0.425325f, -0.688191f });
		anorms_table[161] = FloatBuffer.allocate(3).put(new float[]{ -0.688191f, -0.587785f, -0.425325f });
	}
	
	/**
	 * Free resources allocated for the model.
	 */
	void FreeModel(MD2_Model_T mdl) {
		int i;

		if (mdl.skins != null) {
			mdl.skins = null;
		}

		if (mdl.texcoords != null) {
			mdl.texcoords = null;
		}

		if (mdl.triangles != null) {
			mdl.triangles = null;
		}

		if (mdl.glcmds != null) {
			mdl.glcmds = null;
		}

		if (mdl.frames != null) {
			for (i = 0; i < mdl.header.num_frames; ++i) {
				mdl.frames[i].verts = null;
			}

			mdl.frames = null;
		}
	}

	/**
	 * draw the model at frame n.
	 */
	public void drawFrame(GL canvas) {
		canvas.glPushMatrix();
		int i, j;
		float s, t;
		FloatBuffer v = FloatBuffer.allocate(3);
		MD2_Frame_T pframe;
		MD2_Vertex_T pvert;

		/* Check if n is in a valid range */
		if ((n < 0) || (n > mdl.header.num_frames - 1))
			return;

		/* Enable model's texture */

		//canvas.glBindTexture(GL.GL_TEXTURE_2D, mdl.tex_id);

		/* Draw the model */
		canvas.glBegin(GL.GL_TRIANGLES);
		/* Draw each triangle */
		for (i = 0; i < mdl.header.num_tris; ++i) {
			/* Draw each vertex */
			for (j = 0; j < 3; ++j) {
				pframe = mdl.frames[n];
				pvert = pframe.verts[mdl.triangles[i].vertex[j]];

				/* Compute texture coordinates */
				s = (float) mdl.texcoords[mdl.triangles[i].st[j]].s
						/ mdl.header.skinwidth;
				t = (float) mdl.texcoords[mdl.triangles[i].st[j]].t
						/ mdl.header.skinheight;

				/* Pass texture coordinates to OpenGL */
				canvas.glTexCoord2f(s, t);

				/* Normal vector */
				canvas.glNormal3fv(anorms_table[pvert.normalIndex]);

				/* Calculate vertex real position */
				v.put(0, (pframe.scale[0] * pvert.v[0]) + pframe.translate[0]);
				v.put(1, (pframe.scale[1] * pvert.v[1]) + pframe.translate[1]);
				v.put(2, (pframe.scale[2] * pvert.v[2]) + pframe.translate[2]);

				canvas.glVertex3fv(v);
			}
		}
		canvas.glEnd();
		canvas.glPopMatrix();
	}

	/**
	 * draw the model with interpolation between frame n and n+1. interp is
	 * the interpolation percent. (from 0.0 to 1.0)
	 */
	public void drawFrameItp(GL canvas) {
		canvas.glPushMatrix();
		int i, j;
		float s, t;
		FloatBuffer v_curr = FloatBuffer.allocate(3), v_next = FloatBuffer
				.allocate(3), v = FloatBuffer.allocate(3), norm = FloatBuffer
				.allocate(3);
		FloatBuffer n_curr = FloatBuffer.allocate(3), n_next = FloatBuffer
				.allocate(3);
		MD2_Frame_T pframe1, pframe2;
		MD2_Vertex_T pvert1, pvert2;

		/* Check if n is in a valid range */
		if ((n < 0) || (n > mdl.header.num_frames))
			return;

		/* Enable model's texture */
		//canvas.glBindTexture(GL.GL_TEXTURE_2D, mdl.tex_id);

		/* Draw the model */
		canvas.glBegin(GL.GL_TRIANGLES);
		/* Draw each triangle */
		//System.out.println("---");
		//System.out.println(mdl.header.num_frames);
		//System.out.println(mdl.frames.length);
		for (i = 0; i < mdl.header.num_tris; ++i) {
			/* Draw each vertex */
			for (j = 0; j < 3; ++j) {
				pframe1 = mdl.frames[n];
				pframe2 = mdl.frames[n + 1];
				pvert1 = pframe1.verts[mdl.triangles[i].vertex[j]];
				pvert2 = pframe2.verts[mdl.triangles[i].vertex[j]];

				/* Compute texture coordinates */
				s = (float) mdl.texcoords[mdl.triangles[i].st[j]].s
						/ mdl.header.skinwidth;
				t = (float) mdl.texcoords[mdl.triangles[i].st[j]].t
						/ mdl.header.skinheight;

				/* Pass texture coordinates to OpenGL */
				canvas.glTexCoord2f(s, t);

				/* Interpolate normals */
				n_curr = anorms_table[pvert1.normalIndex];
				n_next = anorms_table[pvert2.normalIndex];

				norm.put(0, (n_curr.get(0) + interp
						* (n_next.get(0) - n_curr.get(0))));
				norm.put(1, (n_curr.get(1) + interp
						* (n_next.get(1) - n_curr.get(1))));
				norm.put(2, (n_curr.get(2) + interp
						* (n_next.get(2) - n_curr.get(2))));

				canvas.glNormal3fv(norm);

				/* Interpolate vertices */
				v_curr.put(0, pframe1.scale[0] * pvert1.v[0]
						+ pframe1.translate[0]);
				v_curr.put(1, pframe1.scale[1] * pvert1.v[1]
						+ pframe1.translate[1]);
				v_curr.put(2, pframe1.scale[2] * pvert1.v[2]
						+ pframe1.translate[2]);

				v_next.put(0, pframe2.scale[0] * pvert2.v[0]
						+ pframe2.translate[0]);
				v_next.put(1, pframe2.scale[1] * pvert2.v[1]
						+ pframe2.translate[1]);
				v_next.put(2, pframe2.scale[2] * pvert2.v[2]
						+ pframe2.translate[2]);

				v.put(0, v_curr.get(0) + interp
						* (v_next.get(0) - v_curr.get(0)));
				v.put(1, v_curr.get(1) + interp
						* (v_next.get(1) - v_curr.get(1)));
				v.put(2, v_curr.get(2) + interp
						* (v_next.get(2) - v_curr.get(2)));

				canvas.glVertex3fv(v);
			}
		}
		canvas.glEnd();
		canvas.glPopMatrix();
	}

	/**
	 * draw the model at frame n using model's GL command list.
	 */
	public void drawFrameWithGLCmds( GL canvas) {
		int i;
		int[] pglcmds;
		FloatBuffer v = FloatBuffer.allocate(3);
		MD2_Frame_T pframe;
		MD2_Vertex_T pvert;
		MD2_GLcmd_T packet;

		/* Check if n is in a valid range */
		if ((n < 0) || (n > mdl.header.num_frames - 1))
			return;

		/* Enable model's texture */
		//canvas.glBindTexture(GL.GL_TEXTURE_2D, mdl.tex_id);

		/* pglcmds points at the start of the command list */
		pglcmds = mdl.glcmds;

		/* Draw the model */
		int pglcmdsindex = 0;
		while ((i = pglcmds[pglcmdsindex++]) != 0) {
			if (i < 0) {
				canvas.glBegin(GL.GL_TRIANGLE_FAN);
				i = -i;
			} else {
				canvas.glBegin(GL.GL_TRIANGLE_STRIP);
			}

			/* Draw each vertex of this group */
			for (/* Nothing */; i > 0; --i, pglcmdsindex += 3) {
				packet = new MD2_GLcmd_T(pglcmds, pglcmdsindex);
				pframe = mdl.frames[n];
				pvert = pframe.verts[packet.index];

				/* Pass texture coordinates to OpenGL */
				canvas.glTexCoord2f(packet.s, packet.t);

				/* Normal vector */
				canvas.glNormal3fv(anorms_table[pvert.normalIndex]);

				/* Calculate vertex real position */
				v.put(0, pframe.scale[0] * pvert.v[0] + pframe.translate[0]);
				v.put(1, pframe.scale[1] * pvert.v[1] + pframe.translate[1]);
				v.put(2, pframe.scale[2] * pvert.v[2] + pframe.translate[2]);

				canvas.glVertex3fv(v);
			}

			canvas.glEnd();
		}
		System.out.println("-");
	}

	/**
	 * draw the model with interpolation between frame n and n+1 using model's
	 * GL command list. interp is the interpolation percent. (from 0.0 to 1.0)
	 */
	public void drawFrameItpWithGLCmds(GL canvas) {
		canvas.glPushMatrix();
		int i;
		int[] pglcmds;
		FloatBuffer v_curr = FloatBuffer.allocate(3), v_next = FloatBuffer
				.allocate(3), v = FloatBuffer.allocate(3), norm = FloatBuffer
				.allocate(3);
		FloatBuffer n_curr = FloatBuffer.allocate(3), n_next = FloatBuffer
				.allocate(3);
		MD2_Frame_T pframe1, pframe2;
		MD2_Vertex_T pvert1, pvert2;
		MD2_GLcmd_T packet;

		/* Check if n is in a valid range */
		if ((n < 0) || (n > mdl.header.num_frames - 1))
			return;

		/* Enable model's texture */
		//canvas.glBindTexture(GL.GL_TEXTURE_2D, mdl.tex_id);

		/* pglcmds points at the start of the command list */
		pglcmds = mdl.glcmds;

		/* Draw the model */
		int pglcmdsindex = 0;
		while ((i = pglcmds[pglcmdsindex++]) != 0) {
			if (i < 0) {
				canvas.glBegin(GL.GL_TRIANGLE_FAN);
				i = -i;
			} else {
				canvas.glBegin(GL.GL_TRIANGLE_STRIP);
			}

			/* Draw each vertex of this group */
			for (/* Nothing */; i > 0; --i, pglcmdsindex += 3) {
				packet = new MD2_GLcmd_T(pglcmds, pglcmdsindex);
				pframe1 = mdl.frames[n];
				pframe2 = mdl.frames[n + 1];
				pvert1 = pframe1.verts[packet.index];
				pvert2 = pframe2.verts[packet.index];

				/* Pass texture coordinates to OpenGL */
				canvas.glTexCoord2f(packet.s, packet.t);

				/* Interpolate normals */
				n_curr = anorms_table[pvert1.normalIndex];
				n_next = anorms_table[pvert2.normalIndex];

				norm.put(0, (n_curr.get(0) + interp
						* (n_next.get(0) - n_curr.get(0))));
				norm.put(1, (n_curr.get(1) + interp
						* (n_next.get(1) - n_curr.get(1))));
				norm.put(2, (n_curr.get(2) + interp
						* (n_next.get(2) - n_curr.get(2))));

				canvas.glNormal3fv(norm);

				/* Interpolate vertices */
				v_curr.put(0, pframe1.scale[0] * pvert1.v[0]
						+ pframe1.translate[0]);
				v_curr.put(1, pframe1.scale[1] * pvert1.v[1]
						+ pframe1.translate[1]);
				v_curr.put(2, pframe1.scale[2] * pvert1.v[2]
						+ pframe1.translate[2]);

				v_next.put(0, pframe2.scale[0] * pvert2.v[0]
						+ pframe2.translate[0]);
				v_next.put(1, pframe2.scale[1] * pvert2.v[1]
						+ pframe2.translate[1]);
				v_next.put(2, pframe2.scale[2] * pvert2.v[2]
						+ pframe2.translate[2]);

				v.put(0, v_curr.get(0) + interp
						* (v_next.get(0) - v_curr.get(0)));
				v.put(1, v_curr.get(1) + interp
						* (v_next.get(1) - v_curr.get(1)));
				v.put(2, v_curr.get(2) + interp
						* (v_next.get(2) - v_curr.get(2)));

				canvas.glVertex3fv(v);
			}

			canvas.glEnd();
		}
		canvas.glPopMatrix();
	}

	/**
	 * Calculate the current frame in animation beginning at frame 'start' and
	 * ending at frame 'end', given interpolation percent. interp will be
	 * reseted to 0.0 if the next frame is reached.
	 */
	// TODO checar se funciona corretamente
	void Animate(int start, int end, int frame, float interp) {
		if ((frame < start) || (frame > end))
			frame = start;

		if (interp >= 1.0f) {
			/* Move to next frame */
			interp = 0.0f;
			(frame)++;

			if (frame >= end)
				frame = start;
		}
		this.n = frame;
		this.interp = interp;
	}

	// ?? usar??

	void cleanup() {
		FreeModel(mdl);
	}

	void reshape(int w, int h, GL canvas, GLU glu) {
		if (h == 0)
			h = 1;

		canvas.glViewport(0, 0, w, h);

		canvas.glMatrixMode(GL.GL_PROJECTION);
		canvas.glLoadIdentity();

		// TODO ?? o que fazer com isso ??
		glu.gluPerspective(45.0, w / (double) h, 0.1, 1000.0);

		canvas.glMatrixMode(GL.GL_MODELVIEW);
		canvas.glLoadIdentity();
	}

	void display(GL canvas) {

	   	   Texture texture = textures[choosenTexture];
	   	   texture.enable ();
	   	   texture.bind (); 
		//canvas.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		//canvas.glLoadIdentity();

		//System.out.println("mdl.tex_id "+mdl.tex_id);
		current_time = (System.currentTimeMillis() - last_time) / 100.0;
		last_time = System.currentTimeMillis();
		/* Animate model from frames 0 to num_frames-1 */
		interp += current_time;
		Animate(0, mdl.header.num_frames - 1, n, interp);

		//canvas.glTranslatef(0.0f, 0.0f, -100.0f);
		//canvas.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
		//canvas.glRotatef(-90.0f, 0.0f, 0.0f, 1.0f);

		/* Draw the model */
		/* drawFrame (n, md2file,canvas); */
		/* drawFrameWithGLCmds (n, md2file,canvas); */
		/* drawFrameItp (n, interp, md2file,canvas); */
		//drawFrameItpWithGLCmds(canvas);
		if(mdl.header.num_frames>1){
			drawFrameItp(canvas);
			//drawFrameWithGLCmds(canvas);
		}else{
			drawFrame(canvas);
		}
		// TODO ?? o que fazer com isso ??
		// glutSwapBuffers ();
		// glutPostRedisplay ();
	       texture.disable();
	}

	void keyboard(char key, int x, int y) {
		/* Escape */
		if (key == 27){
			// TODO
			//System.out.println("EXIT");
		}
	}

	/*
	 * int main (int argc, char *argv[]) { if (argc < 2) { fprintf (stderr,
	 * "usage: %s <filename.md2>\n", argv[0]); return -1; }
	 * 
	 * glutInit (&argc, argv); glutInitDisplayMode (GLUT_RGBA | GLUT_DOUBLE |
	 * GLUT_DEPTH); glutInitWindowSize (640, 480); glutCreateWindow
	 * ("MD2 Model");
	 * 
	 * atexit (cleanup); init (argv[1]);
	 * 
	 * glutReshapeFunc (reshape); glutDisplayFunc (display); glutKeyboardFunc
	 * (keyboard);
	 * 
	 * glutMainLoop ();
	 * 
	 * return 0; }
	 */

}
