import javax.media.j3d.Appearance;
import javax.media.j3d.Group;
import javax.media.j3d.TransformGroup;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Primitive;

public class Architrave extends Group{
	public Architrave(float width, Appearance appear){
		TransformGroup TG = new TransformGroup();
		Box box = new Box(width-0.06f, 0.1f, 0.07f, Primitive.GENERATE_TEXTURE_COORDS | Primitive.GENERATE_NORMALS, appear);
		TG.addChild(box);
		addChild(TG);
	}
}
