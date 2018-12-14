package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


public class PipeGameDisplayer extends Canvas{
	double w;
	double h;
	private StringProperty wallFileName;
	List<char[]> pipeGameBoard = new ArrayList<char[]>();
	
	@Override
	public boolean isResizable() {
		return true;
	}

	@Override
	public double minHeight(double width) {
		return 90;
	}

	@Override
	public double maxHeight(double width) {
		return 1200;
	}

	@Override
	public double prefHeight(double width) {
		return minHeight(width);
	}

	@Override
	public double minWidth(double height) {
		return 0;
	}

	@Override
	public double maxWidth(double height) {
		return 1200;
	}

	@Override
	public void resize(double width, double height) {
		super.setWidth(width);
		super.setHeight(height);
		this.redraw();
	}
	
	public double getW() {
		return w;
	}

	public void setW(double w) {
		this.w = w;
	}	
	public double getH() {
		return h;
	}

	public void setH(double h) {
		this.h = h;
	}


	public List<char[]> getPipeGameBoard() {
		return pipeGameBoard;
	}

	public void setPipeGameBoard(List<char[]> pipeGameBoard) {
		this.pipeGameBoard = pipeGameBoard;
		redraw();
	}

	public void redraw() {
		if(pipeGameBoard!=null) {
			double W = getWidth();
			double H = getHeight();
			w = W/pipeGameBoard.get(0).length;
			h = H/pipeGameBoard.size();

			GraphicsContext gc = getGraphicsContext2D();

			Image dash = null;
			Image pipe = null;
			Image L = null;
			Image F = null;
			Image seven = null;
			Image J = null;
			Image S = null;
			Image G = null;
			Image empty = null;
			Image background = null;
			try {
				dash = new Image(new FileInputStream("./resources/basicTheme/dash.png"));
				pipe = new Image(new FileInputStream("./resources/basicTheme/pipe.png"));
				L = new Image(new FileInputStream("./resources/basicTheme/L.png"));
				F = new Image(new FileInputStream("./resources/basicTheme/F.png"));
				seven = new Image(new FileInputStream("./resources/basicTheme/7.png"));
				J = new Image(new FileInputStream("./resources/basicTheme/J.png"));
				S = new Image(new FileInputStream("./resources/basicTheme/S.png"));
				G = new Image(new FileInputStream("./resources/basicTheme/G.png"));
				empty = new Image(new FileInputStream("./resources/basicTheme/default.png"));
				background = new Image(new FileInputStream("./resources/basicTheme/Background.jpg"));
				
			} catch (FileNotFoundException e) {
				System.out.println(e);
			}
			
			gc.clearRect(0, 0, W, H);
			gc.drawImage(background, 0, 0, getWidth(), getHeight());

			for(int i=0;i<pipeGameBoard.size();i++)
				for(int j=0; j<pipeGameBoard.get(0).length;j++) {
					switch (pipeGameBoard.get(i)[j]) {
					case 'L':
						if(L == null) 
							gc.fillRect(j*w,i*h,w,h);
						gc.drawImage(L,j*w,i*h,w,h);
						break;
					case '-':
						if(dash == null) 
							gc.fillRect(j*w,i*h,w,h);
						gc.drawImage(dash,j*w,i*h,w,h);
						break;
					case '|':
						if(pipe == null) 
							gc.fillRect(j*w,i*h,w,h);
						gc.drawImage(pipe,j*w,i*h,w,h);
						break;
					case 'F':
						if(F == null) 
							gc.fillRect(j*w,i*h,w,h);
						gc.drawImage(F,j*w,i*h,w,h);
						break;
					case '7':
						if(seven == null) 
							gc.fillRect(j*w,i*h,w,h);
						gc.drawImage(seven,j*w,i*h,w,h);
						break;
					case 'J':
					case 'j':
						if(J == null) 
							gc.fillRect(j*w,i*h,w,h);
						gc.drawImage(J,j*w,i*h,w,h);
						break;
					case 's':
					case 'S':
						if(S == null) 
							gc.fillRect(j*w,i*h,w,h);
						gc.drawImage(S,j*w,i*h,w,h);
						break;
					case 'g':
					case 'G':
						if(G == null) 
							gc.fillRect(j*w,i*h,w,h);
						gc.drawImage(G,j*w,i*h,w,h);
						break;

					default:
						if(empty == null) 
							gc.fillRect(j*w,i*h,w,h);
						gc.drawImage(empty,j*w,i*h,w,h);
						break;
					}
				}
		}

	}
}
