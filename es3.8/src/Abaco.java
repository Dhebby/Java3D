import javax.media.j3d.Appearance;
import javax.media.j3d.Group;
import javax.media.j3d.TransformGroup;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Primitive;

public class Abaco extends Group{
	public Abaco(float height, Appearance appearance){
		TransformGroup TG = new TransformGroup();
		Box box = new Box(height/4.5f, height/4.5f/4, height/4.5f, Primitive.GENERATE_NORMALS, appearance);
		TG.addChild(box);
		addChild(TG);
	}
}
