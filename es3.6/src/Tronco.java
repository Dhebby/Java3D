import javax.media.j3d.Appearance;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TriangleStripArray;
import javax.vecmath.Point3f;

class Tronco extends Shape3D{
	public static final float TOP = 1.0f;
	public static final float BOTTOM = 0.0f;
	
	protected Point3f v[] = null;
	protected TriangleStripArray triangleStrip = null;
	protected PolygonAttributes polyAttributes = new PolygonAttributes();
	protected Appearance appearance = new Appearance();
	
	public Tronco(){
		int faces = 4; //numero di facce
		v = new Point3f[(faces+1)*2];
		
		//creo l'array di punti
		for(int i=0; i<faces; i++){
			double angle = 2.0*Math.PI*(double)i/(double)faces;
			float x = (float)Math.sin(angle);
			float y = (float)Math.cos(angle);
			v[i*2+0] = new Point3f(x, y, BOTTOM);
			v[i*2+1] = new Point3f(x/3, y/3, TOP);
		}
		//impostazione ultimo punto
		v[faces*2+0] = new Point3f(0.0f, 1.0f, BOTTOM);
		v[faces*2+1] = new Point3f(0.0f, 1.0f/3, TOP);
		
		//collego i punti per creare i triangoli
		int [] stripCounts = {(faces+1)*2};
		triangleStrip = new TriangleStripArray((faces+1)*2,GeometryArray.COORDINATES, stripCounts);
		triangleStrip.setCoordinates(0, v);
		setGeometry(triangleStrip);
		
		//impostazione aspetto del wireframe
		polyAttributes.setPolygonMode(PolygonAttributes.POLYGON_LINE);
		polyAttributes.setCullFace(PolygonAttributes.CULL_NONE);
		appearance.setPolygonAttributes(polyAttributes);
		setAppearance(appearance);
	}
	
}