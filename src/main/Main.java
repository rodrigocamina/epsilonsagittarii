package main;
import java.awt.BorderLayout;
import java.awt.Cursor;

import javax.swing.JApplet;

/*
 * Created on 21/03/2010
 * Atualizado Para Verção 1.0
 * Desenvolvido Por Dennis Kerr Coelho
 * PalmSoft Tecnologia
 */


public class Main extends JApplet /*implements MouseWheelListener,KeyListener,MouseListener,MouseMotionListener*/
{
   //private static final int DEFAULT_FPS = 80;

   /**
	 * 
	 */
	private static final long serialVersionUID = 3818567970932384088L;

private GameMain wp;        // where the worm is drawn
   //private JTextField jtfBox;   // displays no.of boxes used
   //private JTextField jtfTime;  // displays time spent in game

   int mousecontext = 0;

   public static Main instance;

   
   public void init()
   {
	   System.out.println("iniciado");
     instance = this;

     setFocusable(true);

    setLayout(new BorderLayout());

     wp = new GameMain();
     
     add(wp.canvas, BorderLayout.CENTER);
	 wp.start();
   }

  public void setaCursor(Cursor cursor){
	  this.setCursor(cursor);
  }
  public void setaCursor(int i){
  }
//  public Cursor pegaCursor(){
//	  return(this.getCursor());
//  }
  public void setaCursorPadrao(){
	  this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
  }
  // -------------------- applet life cycle methods --------------

   public void start()
   {  
   
   }

   public void stop()
   {
	   
   }

   public void destroy()
   { 
	   
   }


} // end of WormChaseApplet class

