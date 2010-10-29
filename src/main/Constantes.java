package main;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.Raster;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Properties;

import javax.imageio.ImageIO;

/*
 * Created on 21/03/2010
 * Atualizado Para Verção 1.0
 * Desenvolvido Por Dennis Kerr Coelho
 * PalmSoft Tecnologia
 */

public class Constantes {

    public static String APP_VERSION = "0.00";

    public static int width =950;
    public static int height = 512;

    public static DecimalFormat FORMATANUMERO = new DecimalFormat( "#,##0.##" );
    public static DecimalFormat FORMATADINHEIRO = new DecimalFormat( "#,##0.00" );
    public static DecimalFormat FTT = new DecimalFormat( "00.##" );
}
