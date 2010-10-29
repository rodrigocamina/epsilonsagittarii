package main;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;

import javax.media.opengl.GLAutoDrawable;

/*
 * Created on 28/09/2009
 * Atualizado Para Verção 1.0
 * Desenvolvido Por Dennis Kerr Coelho
 * PalmSoft Tecnologia
 */

public abstract class PS_3DCanvas {
	
	abstract public void init(GLAutoDrawable drawable);
	
	abstract public void reshape(GLAutoDrawable drawable, int x, int y, int width,int height);
    
    abstract public void DesenhaSe(GLAutoDrawable drawable);

    abstract public void SimulaSe(long diftime); 
    
    
    public void TratadorDoMousePressed(int x, int y,int c)
    {
    }
    
    public void TratadorDoMouseReleased(int x, int y,int c)
    {
    }

    public void TratadorDoMouseMove(int x, int y,int c){    
    }

    public void TratadorDoMouseClic(int x, int y,int c){   
    }

    public void TratadorDeTecladoPressed(KeyEvent e){   
    }
    
    public void TratadorDeTecladoReleased(KeyEvent e){ 
    }
    
    public void TratadorMouseWheel(MouseWheelEvent e){
    }
 
}
