import javax.media.j3d.Appearance;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Group;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Box;

public class Maya extends Group{
	private static final int N_LEV = 6; //numero di livelli della piramide
	
	private static final float LEV_HEIGHT = 0.2f;
	private static final float BASE_SIZE = 1.0f;
	private static final float DELTA_SIZE = 0.2f;
	
	//static final protected Appearance appearance = new Appearance();
	
	public Maya(){
		float size;
		//ciclo che aggiunge i gradoni
		for(int i = 0; i < N_LEV; i++){
			size = BASE_SIZE - i * DELTA_SIZE;
			//Box lev = new Box(size, size, LEV_HEIGHT, appearance);
			Box lev = new Box(size, size, LEV_HEIGHT, createApp());
			
			//trasformazione per posizionare i gradoni uno sopra l'altro
			Transform3D trans = new Transform3D();
			trans.setTranslation(new Vector3f(0, 0, i * LEV_HEIGHT));
			TransformGroup TG = new TransformGroup(trans);
			
			TG.addChild(lev);
			addChild(TG);
		}
	}
	
	/**
	 * Funzione che aggiunge un colore random all'appearance
	 * @return l'Appearance da applicare agli oggetti
	 */
	public Appearance createApp(){
		Appearance app = new Appearance();
		
		//assegno un colore random
		ColoringAttributes cA = new ColoringAttributes();
		cA.setColor(new Color3f((float)Math.random(), (float)Math.random(),(float)Math.random()));
		app.setColoringAttributes(cA);
		
		return app;
	}
}