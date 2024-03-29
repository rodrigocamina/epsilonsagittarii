package landscape;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.*;

/*
 * Created on 02/09/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */

/**
 * @author Palm Soft
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class TileMap {
    
    public Image TileSet = null;
    
    int MapX;
    int MapY;
    int NTileX,NTileY;
    int Largura = 60;
    int Altura = 50;
    int TilePLinhaTileset;
    
    int [][]mapa;
	int [][]mapa2;            		
            		
    public TileMap(Image tileset,int tilestelaX,int tilestelaY){
        NTileX = tilestelaX;
        NTileY = tilestelaY;
        TileSet = tileset;
        MapX = 0;
        MapY = 0;
        //TilePLinhaTileset = TileSet.getWidth(null) >>4;
    }
    
    public void AbreMapa(String nomemapa){
            	
	try{
    		InputStream In = getClass().getResourceAsStream(nomemapa);
    		
    		System.out.println(" In "+In.available());
    		
    		DataInputStream data = new DataInputStream(In); 
    		
    		

    		//int Versao = data.readInt(); // Versao
    		data.readInt(); // Versao
        	Largura = ReadCInt(data);    // Largura
        	Altura = ReadCInt(data);	// Largura
        	
        	System.out.println(" Largura "+Largura);
        	
           	System.out.println(" Altura "+Altura);
           	
        	int ltilex =  ReadCInt(data);// Larg Tile
        	int ltiley =  ReadCInt(data);// Altura Tile
        	
        	System.out.println(" ltilex "+ltilex);
        	
           	System.out.println(" ltiley "+ltiley);
           	
        	byte nome[] = new byte[32]; 

        	data.read(nome,0,32);       // Nome Tilemap
        	data.read(nome,0,32); 
        	
        	int numLayers =  ReadCInt(data);// numero de Layers
        	int numTiles =  ReadCInt(data);// numero de Tiles
        	
           	System.out.println(" numLayers "+numLayers);
           	System.out.println(" numTiles "+numTiles);
        	
            int BytesPorTiles =  ReadCInt(data); // numero de bytes por tile;
            
           	System.out.println(" BytesPorTiles "+BytesPorTiles);
           	
           	/*
            int vago1 =  ReadCInt(data); // vago;
            int vago2 =  ReadCInt(data); // vago;   */   
            ReadCInt(data); // vago1
            ReadCInt(data); // vago2  
            
        	mapa = new int[Largura][Altura];
        	mapa2 = new int[Largura][Altura];
        	
        	if(BytesPorTiles==1){
                
        	    for(int j = 0; j < Altura; j++){            
                    for(int i = 0; i < Largura; i++){
                    	int dado;
                    	
                    	int b1 = data.readByte();
                    	int b2 = data.readByte();            	
                    	
                    	dado = ((int)b1&0x00ff)|(((int)b2&0x00ff)<<8);            	
                    	
                    	mapa[j][i] = dado;	            	
                    	//System.out.println(" "+mapa[j][i]);            	
                    }
                }
        	    if(numLayers==2){
	        	    for(int j = 0; j < Altura; j++){            
	                    for(int i = 0; i < Largura; i++){
	                    	int dado;
	                    	
	                    	int b1 = data.readByte();
	                    	int b2 = data.readByte();            	
	                    	
	                    	dado = ((int)b1&0x00ff)|(((int)b2&0x00ff)<<8);            	
	                    	
	                    	mapa2[j][i] = dado;	            	
	                    	//System.out.println(" "+mapa2[j][i]);            	
	                    }
	                }
        	    }
                
        	}else if(BytesPorTiles==2){
        	    for(int j = 0; j < Altura; j++){            
                    for(int i = 0; i < Largura; i++){
                    	int dado;
                    	
                    	
                    	int b1 = data.readByte();
                    	int b2 = data.readByte(); 
                    	int b3 = data.readByte(); 
                    	int b4 = data.readByte();                     	
                    	
                    	dado = ((int)b1&0x00ff)|(((int)b2&0x00ff)<<8)|(((int)b3&0x00ff)<<16)|(((int)b4&0x00ff)<<24);           	
                    	
                    	mapa[j][i] = dado;	            	
                    	//System.out.println(" "+mapa[j][i]);            	
                    }
                }
        	    if(numLayers==2){
	        	    for(int j = 0; j < Altura; j++){            
	                    for(int i = 0; i < Largura; i++){
	                    	int dado;
	                    	
	                    	int b1 = data.readByte();
	                    	int b2 = data.readByte(); 
	                    	int b3 = data.readByte(); 
	                    	int b4 = data.readByte();                     	
	                    	
	                    	dado = ((int)b1&0x00ff)|(((int)b2&0x00ff)<<8)|(((int)b3&0x00ff)<<16)|(((int)b4&0x00ff)<<24);            	
	                    	
	                    	mapa2[j][i] = dado;	            	
	                    	//System.out.println(" "+mapa2[j][i]);            	
	                    }
	                } 
        	    }
        	}else{
        	    for(int j = 0; j < Altura; j++){            
                    for(int i = 0; i < Largura; i++){
                    	int dado;
                    	
                    	int b1 = data.readByte(); 
                    	
                    	//System.out.println(" "+b1);
                    	
                    	dado = ((int)b1&0x00ff);            	
                    	
                    	mapa[j][i] = dado;	            	
                    	//System.out.println(" "+mapa[j][i]);            	
                    }
                }
        	    if(numLayers==2){
	        	    for(int j = 0; j < Altura; j++){            
	                    for(int i = 0; i < Largura; i++){
	                    	int dado;
	                    	
	                    	int b1 = data.readByte(); 
	                    	
	                    	dado = ((int)b1&0x00ff);            	
	                    	
	                    	mapa2[j][i] = dado;	            	
	                    	//System.out.println(" "+mapa2[j][i]);            	
	                    }
	                } 
        	    }
        	}
  		

        
        
       	data.close();
    		
    	In.close();
    		
	    }//fim try
	    catch (Exception e)
	    {
	    	e.printStackTrace();
	      System.out.println(e.getMessage()+ "  abreaMapaPau!!!");
	    }    		
		    	
    	
    }
   
    
    public void DesenhaSe(Graphics2D dbg){
    	int offx = MapX&0x0f; 
    	int offy = MapY&0x0f;
    	int somax,somay;
    	if(offx>0){
    		somax = 1;
    	}else{
    		somax = 0;
    	}
    	if(offy>0){
    		somay = 1;
    	}else{
    		somay = 0;
    	}
        for(int j = 0; j < NTileY + somay; j++){            
            for(int i = 0; i < NTileX + somax; i++){
            	
                int tilex = (mapa[j+(MapY>>4)][i+(MapX>>4)]%TilePLinhaTileset)<<4;
                int tiley = (mapa[j+(MapY>>4)][i+(MapX>>4)]/TilePLinhaTileset)<<4;
                dbg.drawImage(TileSet,(i<<4)-offx,(j<<4)-offy,(i<<4)+16-offx,(j<<4)+16-offy,tilex,tiley,tilex+16,tiley+16,null);
                
            }
        }
        for(int j = 0; j < NTileY + somay; j++){            
            for(int i = 0; i < NTileX + somax; i++){
            	
                int tilex = (mapa2[j+(MapY>>4)][i+(MapX>>4)]%TilePLinhaTileset)<<4;
                int tiley = (mapa2[j+(MapY>>4)][i+(MapX>>4)]/TilePLinhaTileset)<<4;
                dbg.drawImage(TileSet,(i<<4)-offx,(j<<4)-offy,(i<<4)+16-offx,(j<<4)+16-offy,tilex,tiley,tilex+16,tiley+16,null);
                
            }
        }        
    }
    
    public void Posiciona(int x,int y){
    	int X = x>>4;
    	int Y = y>>4;
    	
        if(X < 0){
            MapX = 0;
        }else if(X >= (Largura-NTileX)){
            MapX = ((Largura-NTileX))<<4;
        }else{
            MapX = x;
            System.out.println(MapX);
        }
        
        if(Y < 0){
            MapY = 0;
        }else if(Y >= (Altura-NTileY)){
            MapY = ((Altura-NTileY))<<4;
        }else{
            MapY = y;
        }        

    }
    
    public int ReadCInt(DataInputStream data) throws IOException{
    	int b1 = data.readByte();
    	int b2 = data.readByte(); 
    	int b3 = data.readByte(); 
    	int b4 = data.readByte();                     	
    	
    	return ((int)b1&0x00ff)|(((int)b2&0x00ff)<<8)|(((int)b3&0x00ff)<<16)|(((int)b4&0x00ff)<<24);            	    	
    }
}
