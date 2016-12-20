import javax.media.j3d.Appearance;
import javax.media.j3d.Group;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3d;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Primitive;

public class Stilobate extends Group{
	public static final float HEIGHT = 0.03f;
	public static final float LENGTH = 0.1f;
	public Stilobate(int gradini, float width, Appearance appear){
		//creazione gradini
		for(int i=0; i<gradini; i++){
			TransformGroup TG = new TransformGroup();
			
			Transform3D t3d = new Transform3D();
			t3d.setTranslation(new Vector3d(0.0f, i*(-0.05f), 0.0f));
			TG.setTransform(t3d);
			
			Box box = new Box(width + (i * 0.05f), HEIGHT, LENGTH + (i * 0.05f), Primitive.GENERATE_TEXTURE_COORDS | Primitive.GENERATE_NORMALS, appear);
			TG.addChild(box);
			addChild(TG);
		}
	}
	
}
