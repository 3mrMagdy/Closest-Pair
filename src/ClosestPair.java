import java.io.File;
import java.util.*;

class point
{
	long x, y;
}

public class ClosestPair
{
	public static void main (String arg[])
	{
		Scanner in = new Scanner(System.in);
		ArrayList<point> Xpoints = new ArrayList<>(), Ypoints = new ArrayList<>();
		String path;
		
		path = in.nextLine();
		
		try
		{
			in = new Scanner(new File(path));
			
			int n = in.nextInt();
			point tmp;
			
			while (n-- > 0)
			{
				tmp = new point();
				
				tmp.x = in.nextLong();
				tmp.y = in.nextLong();
				
				Xpoints.add(tmp);	
				Ypoints.add(tmp);
			}
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		new ClosestPair().Xsort(Xpoints);
		new ClosestPair().Ysort(Ypoints);
				
		System.out.println(new ClosestPair().ClosestPairDest(Xpoints, Ypoints));
		
		in.close();
	}
	
	public void Xsort(ArrayList<point> points)
	{
		Collections.sort(points,new Comparator<point>()
		{
			@Override
			public int compare(point p1, point p2) 
			{
				return p1.x>p2.x || (p1.x==p2.x && p1.y>p2.y) ? 1 : (p1.x<p2.x ? -1 : 0);
			}
		});
	}
	
	public void Ysort(ArrayList<point> points)
	{
		Collections.sort(points,new Comparator<point>()
		{
			@Override
			public int compare(point p1, point p2) 
			{
				return p1.y>p2.y || (p1.y==p2.y && p1.x>p2.x) ? 1 : (p1.y<p2.y ? -1 : 0);
			}
		});
	}
	
	public double dest (point p1, point p2)
	{
		return Math.sqrt((p1.x-p2.x)*(p1.x-p2.x) + (p1.y-p2.y)*(p1.y-p2.y));
	}
	
	public double minD (ArrayList<point> points)
	{
		double mn=Double.MAX_VALUE;
		
		for(int i=0 ; i<points.size() ; i++)
			for(int j=i+1 ; j<points.size() ; j++)
				mn = Math.min(mn, dest(points.get(i),points.get(j)));
		
		return mn;
	}
	
	public double ClosestStrip (ArrayList<point> points, double mn)
	{		
		for(int i=0 ; i<points.size() ; i++)
			for(int j=i+1 ; j<points.size() && points.get(j).y-points.get(i).y<mn ; j++)
				mn = Math.min(mn, dest(points.get(i),points.get(j)));
		
		return mn;
	}
	
	public double ClosestPairDest (ArrayList<point> Xpoints, ArrayList<point> Ypoints)
	{
		if(Xpoints.size()<4)
			return minD(Xpoints);
		
		int mid = Xpoints.size()/2;
		long midX = Xpoints.get(mid).x;
		
		ArrayList<point> Xh1=new ArrayList<>(), Xh2=new ArrayList<>();
		ArrayList<point> Yh1=new ArrayList<>(), Yh2=new ArrayList<>();
		
		for(int i=0 ; i<Ypoints.size() ; i++)
		{
			if(Ypoints.get(i).x<midX)
				Yh1.add(Ypoints.get(i));
			if(i<mid)
				Xh1.add(Xpoints.get(i));
			else
				Xh2.add(Xpoints.get(i));
		}
		
		for(int i=0 ; i<Ypoints.size() ; i++)
			if(Ypoints.get(i).x==midX && Yh1.size()!=mid)
				Yh1.add(Ypoints.get(i));
			else if (Ypoints.get(i).x>=midX)
				Yh2.add(Ypoints.get(i));		
				
		double d = Math.min(ClosestPairDest(Xh1, Yh1),ClosestPairDest(Xh2, Yh2));
		
		ArrayList<point> strip = new ArrayList<>();
		
		for(point tmp : Ypoints)
			if(Math.abs(tmp.x-midX)<d)
				strip.add(tmp);
		
		return Math.min(d, ClosestStrip(strip, d));
	}
}
