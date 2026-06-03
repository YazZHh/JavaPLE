package engine;

public interface iShape {

	public boolean intersects(iShape shape);

	public boolean intersects(Circle circle);

	public boolean intersects(Rect rect);
	
	public void rotateAround(ISU.Coord c, int angle_degree);

}
