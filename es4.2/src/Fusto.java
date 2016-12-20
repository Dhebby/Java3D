import javax.media.j3d.Appearance;
import javax.media.j3d.Group;
import javax.media.j3d.TransformGroup;

import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Primitive;

public class Fusto extends Group{
	public Fusto(float height, Appearance appearance){
		TransformGroup TG = new TransformGroup();
		Cylinder cylinder = new Cylinder(height/4.5f/2, height,Primitive.GENERATE_TEXTURE_COORDS | Primitive.GENERATE_NORMALS, appearance);
		TG.addChild(cylinder);
		addChild(TG);
	}

}
