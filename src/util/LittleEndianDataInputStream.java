package util;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class LittleEndianDataInputStream extends InputStream implements DataInput {

	long currentOffset = 0;
	
	public LittleEndianDataInputStream(InputStream in) {
		this.in = in;
		this.d = new DataInputStream(in);
		w = new byte[8];
	}

	public int available() throws IOException {
		return d.available();
	}


	public final short readShort() throws IOException
	{
		currentOffset+=2;
		d.readFully(w, 0, 2);
		return (short)(
				(w[1]&0xff) << 8 |
				(w[0]&0xff));
	}

	/**
	 * Note, returns int even though it reads a short.
	 */
	 public final int readUnsignedShort() throws IOException
	 {
			currentOffset+=2;
		 d.readFully(w, 0, 2);
		 return (
				 (w[1]&0xff) << 8 |
				 (w[0]&0xff));
	 }

	 /**
	  * like DataInputStream.readChar except little endian.
	  */
	 public final char readChar() throws IOException
	 {
			currentOffset+=2;
		 d.readFully(w, 0, 2);
		 return (char) (
				 (w[1]&0xff) << 8 |
				 (w[0]&0xff));
	 }

	 /**
	  * like DataInputStream.readInt except little endian.
	  */
	 public final int readInt() throws IOException
	 {
			currentOffset+=4;
		 d.readFully(w, 0, 4);
		 return
		 (w[3])      << 24 |
		 (w[2]&0xff) << 16 |
		 (w[1]&0xff) <<  8 |
		 (w[0]&0xff);
	 }

	 /**
	  * like DataInputStream.readLong except little endian.
	  */
	 public final long readLong() throws IOException
	 {
			currentOffset+=8;
		 d.readFully(w, 0, 8);
		 return
		 (long)(w[7])      << 56 | 
		 (long)(w[6]&0xff) << 48 |
		 (long)(w[5]&0xff) << 40 |
		 (long)(w[4]&0xff) << 32 |
		 (long)(w[3]&0xff) << 24 |
		 (long)(w[2]&0xff) << 16 |
		 (long)(w[1]&0xff) <<  8 |
		 (long)(w[0]&0xff);
	 }

	 public final float readFloat() throws IOException {
		 return Float.intBitsToFloat(readInt());
	 }

	 public final double readDouble() throws IOException {
		 return Double.longBitsToDouble(readLong());
	 }

	 public final int read(byte b[], int off, int len) throws IOException {
			currentOffset+=off+len;
		 return in.read(b, off, len);
	 }

	 public final void readFully(byte b[]) throws IOException {
			currentOffset+=b.length;
		 d.readFully(b, 0, b.length);
	 }

	 public final void readFully(byte b[], int off, int len) throws IOException {
			currentOffset+=b.length;
		 d.readFully(b, off, len);
	 }

	 public final int skipBytes(int n) throws IOException {
			currentOffset+=n;
		 return d.skipBytes(n);
	 }

	 public final int seek(int n) throws IOException{
		 System.out.println("seek "+n+" current "+currentOffset+" skiped "+(n-currentOffset));
		 if(n>currentOffset){
			 return skipBytes(n-(int)currentOffset);
		 }else{
			 return 0;
		 }
	 }
	 
	 public final boolean readBoolean() throws IOException {
		 currentOffset++;
		 return d.readBoolean();
	 }

	 public final byte readByte() throws IOException {
		 currentOffset++;
		 return d.readByte();
	 }

	 public int read() throws IOException {
		 currentOffset++;
		 return in.read();
	 }

	 public final int readUnsignedByte() throws IOException {
		 currentOffset++;
		 return d.readUnsignedByte();
	 }

	 @Deprecated
	 public final String readLine() throws IOException {
		 return d.readLine();
	 }

	 public final String readUTF() throws IOException {
		 return d.readUTF();
	 }

	 public final void close() throws IOException {
		 d.close();
	 }

	 private DataInputStream d; // to get at high level readFully methods of
	 // DataInputStream
	 private InputStream in; // to get at the low-level read methods of
	 // InputStream
	 private byte w[]; // work array for buffering input
}


