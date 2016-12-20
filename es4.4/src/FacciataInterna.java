import javax.media.j3d.Appearance;
import javax.media.j3d.Group;
import javax.media.j3d.Material;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Primitive;

public class FacciataInterna extends Group{
	private Texture texture;
	private static final int N_COL = 4;
	private static final float DIST_COL = 0.15f;

	public FacciataInterna(Texture texture){
		this.texture = texture;
		for(int i=0; i<N_COL; i++){
			addChild(addColonna(i, createApp()));
		}
		
		addChild(addArchitrave(createApp()));
		
	}
	
	/**
	 * Metodo che aggiunge la colonna alla scena
	 * @param i numero di colonna da inserire
	 * @param app Appearance dell'oggetto
	 * @return TransformGroup da aggiungere al BranchGroup
	 */
	public TransformGroup addColonna(int i, Appearance app){
		TransformGroup transform = new TransformGroup(); //creo oggettp TG
		
		//trasformazioni e inserimento colonna
		Transform3D t3d = new Transform3D();

		t3d.setTranslation(new Vector3d((i-((float)N_COL-1)/2)*DIST_COL, 0.0f, 0.0f));
		
		Transform3D t3ds = new Transform3D();
		t3ds.setScale(0.2d);
		t3d.mul(t3ds);
		transform.setTransform(t3d);
		
		transform.addChild(new Colonna(texture));	
		
		return transform;
	}
	
	/**
	 * Metodo che aggiunge l'architrave alla scena
	 * @param app Appearance dell'oggetto
	 * @return TransformGroup da aggiungere al BranchGroup
	 */
	public TransformGroup addArchitrave(Appearance app){
		TransformGroup transform = new TransformGroup(); //creo oggettp TG
		
		Transform3D t3d = new Transform3D();
		t3d.setTranslation(new Vector3d(0.0, 0.19f, 0.0f));
		Transform3D t3ds = new Transform3D();
		t3ds.setScale(0.6d);
		t3d.mul(t3ds);
		transform.setTransform(t3d);
		
		Box box = new Box(0.47f, 0.05f, 0.07f, Primitive.GENERATE_TEXTURE_COORDS | Primitive.GENERATE_NORMALS, app);
		transform.addChild(box);
		
		return transform;
	}
	
	/**
	 * Metodo che crea l'appearance 
	 * Definisce il materiale e applica la texture
	 * @return l'appearance da applicare agli oggetti
	 */
	private Appearance createApp(){
		Appearance app = new Appearance();
		
		//impostazione aspetto
		app.setTexture(texture);
		
		//impostazioni per fondere il colore dell'oggetto con la texture
		TextureAttributes tA = new TextureAttributes();
		tA.setTextureMode(TextureAttributes.MODULATE);
		app.setTextureAttributes(tA);
		
		//imposto materiale
		Material mat = new Material();
		mat.setDiffuseColor(new Color3f(0.5f, 0.4f, 0.2f));
		mat.setColorTarget(Material.DIFFUSE);
		app.setMaterial(mat);
		
		return app;
	}
}
