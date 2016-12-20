import javax.media.j3d.Appearance;
import javax.media.j3d.Geometry;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TexCoordGeneration;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.TriangleArray;
import javax.media.j3d.TriangleStripArray;
import javax.vecmath.Point3d;

import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.NormalGenerator;
import com.sun.j3d.utils.image.TextureLoader;

public class Timpano extends Shape3D{
	public Timpano(double width, Appearance appear){
		//punti che compongono il timpano
		Point3d A = new Point3d(-(width/2), 0.0, 0.08);
	    Point3d B = new Point3d(0.0, 0.2, 0.08);
	    Point3d C = new Point3d(width/2, 0.0, 0.08);
	    Point3d D = new Point3d(width/2, 0.0, -0.08);
	    Point3d E = new Point3d(0.0, 0.2, -0.08);
	    Point3d F = new Point3d(-(width/2), 0.0, -0.08);
	    
	    //array di triangoli che compongono il poligono
	    Point3d[] faces = {
	    		C, B, A,
	    		A, B, F,
	    		A, F, C,
	    		B, E, C,
	    		B, E, F,
	    		C, D, E,
	    		C, F, D,
	    		D, E, F
	    };
	    
	    setGeometry(createGeometry(faces));
	    
	    //creo coordinate per applicare le textures
		TexCoordGeneration tCoord = new TexCoordGeneration(TexCoordGeneration.TEXTURE_COORDINATE_2, TexCoordGeneration.OBJECT_LINEAR);
		appear.setTexCoordGeneration(tCoord);
	    
	    appear.setPolygonAttributes(new PolygonAttributes(PolygonAttributes.POLYGON_FILL, PolygonAttributes.CULL_NONE, 0));
	    setAppearance(appear);
	    
	    //carica la texture da file
		TextureLoader tLoader = new TextureLoader("../texture/PietraColonna.jpg", TextureLoader.GENERATE_MIPMAP, null);
		//inizializza oggetto Texture
		Texture texture = tLoader.getTexture();
		//impostazione aspetto
		appear.setTexture(texture);
		
		//impostazioni per fondere il colore dell'oggetto con la texture
		TextureAttributes tA = new TextureAttributes();
		tA.setTextureMode(TextureAttributes.MODULATE);
		appear.setTextureAttributes(tA);
	}
	
	/**
	 * Metodo che crea la Geometry
	 * @param faces facce che compongono l'oggetto
	 * @return la Geometry da applicare all'oggetto
	 */
	public Geometry createGeometry(Point3d[] faces){
		int [] stripCounts = {(faces.length+1)*2};
	    TriangleStripArray triangleStripArray = new TriangleStripArray(
	    		stripCounts[0],
	    		TriangleArray.COORDINATES | TriangleArray.NORMALS | TriangleArray.TEXTURE_COORDINATE_2,
	    		stripCounts);
	    triangleStripArray.setCoordinates(0, faces);
	    
	    //normali generate automaticamente
	    GeometryInfo gi = new GeometryInfo(triangleStripArray);
	    NormalGenerator normalGenerator = new NormalGenerator();
	    normalGenerator.generateNormals(gi);
	    		
	    return gi.getGeometryArray();
	}
}
