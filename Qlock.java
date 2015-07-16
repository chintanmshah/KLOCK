import java.awt.*;
import java.applet.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/*
<applet code="Qlock" width=550 height=500>
<param name=fontSize value=20>
</applet>
*/

interface GRID {
	char grid[][] = {{'I','T','O','I','S','B','F','A','M','P','M'},
					{'A','C','Q','U','A','R','T','E','R','D','C'},
					{'T','W','E','N','T','Y','F','I','V','E','X'},
					{'H','A','L','F','B','T','E','N','F','T','O'},
					{'P','A','S','T','E','R','U','N','I','N','E'},
					{'O','N','E','S','I','X','T','H','R','E','E'},
					{'F','O','U','R','F','I','V','E','T','W','O'},
					{'E','I','G','H','T','E','L','E','V','E','N'},
					{'S','E','V','E','N','T','W','E','L','V','E'},
					{'T','E','N','S','E','O','C','L','O','C','K'}};
}

class TimeOps {
	public String getTime() {
		DateFormat df = new SimpleDateFormat("HH mm ss");
		Calendar calobj = Calendar.getInstance();
		
		return String.valueOf(df.format(calobj.getTime()));
	}
	
	public int[] notation(int min) {
		int [] note = new int[3];
		if(min<5 || min>55) {
			note[0] = 9;
			note[1] = 5;
			note[2] = 11;
		}
		else if(min>=5 && min<35) {
			note[0] = 4;
			note[1] = 0;
			note[2] = 4;
		}
		else {
			note[0] = 3;
			note[1] = 9;
			note[2] = 11;
		}
		return note;
	}
	
	public int[] minutes(int min) {
		int note[] = new int[3];
		if(min>=15 && min<20) {
			return (new int[] {1,2,9});
		}
		else if((min>=5 && min<10) || (min>=20 && min<30))
		{
			note[0] = 2;
			if(min<10) {
				note[1] = 6;
				note[2] = 10;
			}
			else if(min<25) {
				note[1] = 0;
				note[2] = 6;
			}
			else {
				note[1] = 0;
				note[2] = 10;
			}
		}
		else if(min>=10 && min<15)
			return (new int[] {3,5,8});
		else if(min>=30 && min<35)
			return (new int[] {3,0,4});
		return note;
	}
	
	public int[] hours(int hour) {
		switch(hour)
		{
			case 12:
			case 0:
				return (new int[] {8,5,11});
			case 1:
				return (new int[] {5,0,3});
			case 2:
				return (new int[] {6,8,11});
			case 3:
				return (new int[] {5,6,11});
			case 4:
				return (new int[] {6,0,4});
			case 5:
				return (new int[] {6,4,8});
			case 6:
				return (new int[] {5,3,6});
			case 7:
				return (new int[] {8,0,5});
			case 8:
				return (new int[] {7,0,5});
			case 9:
				return (new int[] {4,7,11});
			case 10:
				return (new int[] {9,0,3});
			default:
				return (new int[] {7,5,11});
		}
	}
}

public class Qlock extends Applet implements GRID {
	int fontSize;
	String str,currentTime;
	int [] time = new int[3];
	int [] getParam = new int[3];
	
	TimeOps t = new TimeOps();
	
	public void init() {
		setBackground(Color.black);
		setForeground(Color.white);
	}
	
	public void start() {
		String param;
		param = getParameter("fontSize");
		try {
			if(param != null) // if not found
				fontSize = Integer.parseInt(param);
			else
				fontSize = 0;
		} 
		catch(NumberFormatException e) {
			fontSize = -1;
		}
	}

	public void paint(Graphics g) {
		repaint();
		for(int i=0;i<10;i++)
			for(int j=0;j<11;j++)
			{
				str = String.valueOf(grid[i][j]);
				g.drawString(str,30+j*45,30+i*45);
			}

		g.setColor(Color.red);
		for(int i=0;i<5;i++) {
			if(i != 2) {
				str = String.valueOf(grid[0][i]);
				g.drawString(str,30+i*45,30);
			}
		}		
		currentTime = t.getTime();
		g.drawString(currentTime,220,490);
		
		int temp = 0;
		for(String retval: currentTime.split(" ",3))
		{
			time[temp] = Integer.parseInt(retval);
			temp++;
		}
					
		getParam = t.notation(time[1]);
		for(int i=getParam[1];i<getParam[2];i++) {
				str = String.valueOf(grid[getParam[0]][i]);
				g.drawString(str,30+i*45,30+getParam[0]*45);			
		}
		
		if(time[1] >= 35) {
			time[0]++;
			time[1] = 60 - time[1];
		}

		if(getParam[0] != 9) {
			getParam = t.minutes(time[1]);
			for(int i=getParam[1];i<getParam[2];i++) {
					str = String.valueOf(grid[getParam[0]][i]);
					g.drawString(str,30+i*45,30+getParam[0]*45);			
			}
		}

		getParam = t.hours(time[0]%12);
		for(int i=getParam[1];i<getParam[2];i++) {
				str = String.valueOf(grid[getParam[0]][i]);
				g.drawString(str,30+i*45,30+getParam[0]*45);			
		}
	}
}



