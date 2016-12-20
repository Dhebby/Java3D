import javax.media.j3d.Appearance;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TexCoordGeneration;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.TriangleStripArray;
import javax.vecmath.Point3f;

import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.NormalGenerator;
import com.sun.j3d.utils.image.TextureLoader;

class Echino extends Shape3D{
	public static final int STEPS = 600;
	public static final float BOTTOM = 0.0f;
	
	protected Point3f v[] = null;
	protected TriangleStripArray triangleStrip = null;
	
	public Echino(float height, Appearance appearance){
		float top = height/4.5f/4;
		v = new Point3f[(STEPS+1)*2];
		
		//creo l'array di punti
		for(int i=0; i<STEPS; i++){
			double angle = 2.0*Math.PI*(double)i/(double)STEPS;
			float x = (float)Math.sin(angle);
			float z = (float)Math.cos(angle);
			v[i*2+0] = new Point3f(x/4, top, z/4);
			v[i*2+1] = new Point3f(x/7, BOTTOM, z/7);
		}
		
		//imposto l'ultimo punto
		v[STEPS*2+0] = new Point3f(0.0f, top, 1.0f/4);
		v[STEPS*2+1] = new Point3f(0.0f, BOTTOM, 1.0f/7);
		
		//collego i punti per creare i triangoli
		int [] stripCounts = {(STEPS+1)*2};
		triangleStrip = new TriangleStripArray((STEPS+1)*2,GeometryArray.COORDINATES, stripCounts);
		triangleStrip.setCoordinates(0, v);
		
		//creo coordinate per applicare le textures
		TexCoordGeneration tCoord = new TexCoordGeneration(TexCoordGeneration.TEXTURE_COORDINATE_2, TexCoordGeneration.OBJECT_LINEAR);
		appearance.setTexCoordGeneration(tCoord);
		
		//imposto i poligoni
		PolygonAttributes pA = new PolygonAttributes();
		pA.setPolygonMode(PolygonAttributes.POLYGON_LINE);
		pA.setCullFace(PolygonAttributes.CULL_NONE);
		appearance.setPolygonAttributes(pA);
		setAppearance(appearance);
		
		//imposto normali
		GeometryInfo info = new GeometryInfo(triangleStrip);
		NormalGenerator normal = new NormalGenerator();
		normal.generateNormals(info);
		GeometryArray geo = info.getGeometryArray();
		setGeometry(geo);
		
		//carica la texture da file
		TextureLoader tLoader = new TextureLoader("../texture/PietraColonna.jpg", TextureLoader.GENERATE_MIPMAP, null);
		//inizializza oggetto Texture
		Texture texture = tLoader.getTexture();
		//impostazione aspetto
		appearance.setTexture(texture);
		
		//impostazioni per fondere il colore dell'oggetto con la texture
		TextureAttributes tA = new TextureAttributes();
		tA.setTextureMode(TextureAttributes.MODULATE);
		appearance.setTextureAttributes(tA);
	}
	
}

