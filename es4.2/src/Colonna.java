import javax.media.j3d.Appearance;
import javax.media.j3d.Group;
import javax.media.j3d.Material;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;

public class Colonna extends Group{
	private static final float HEIGHT = 1.2f;
	private Texture texture;
	
	public Colonna(Texture texture){
		this.texture = texture;
		addChild(addFusto(createApp()));
		addChild(addEchino(createApp()));
		addChild(addAbaco(createApp()));
	}
	
	/**
	 * Metodo che aggiunge la classe Fusto
	 * @param appear l'Appearance per l'oggetto
	 * @return il TransformGroup contenente la classe Fusto
	 */
	public TransformGroup addFusto(Appearance appear){
		TransformGroup transform = new TransformGroup();
		Transform3D t3d = new Transform3D();
		t3d.setTranslation(new Vector3d(0.0d, 0.0d, 0.0d));
		transform.setTransform(t3d);
		transform.addChild(new Fusto(HEIGHT, appear));

		return transform;
	}
	
	/**
	 * Metodo che aggiunge la classe Echino
	 * @param appear l'Appearance per l'oggetto
	 * @return il TransformGroup contenente la classe Echino
	 */
	public TransformGroup addEchino(Appearance appear){
		TransformGroup transform = new TransformGroup();
		Transform3D t3d = new Transform3D();
		t3d.setTranslation(new Vector3d(0.0d, HEIGHT/2, 0.0d));
		t3d.setScale(HEIGHT-0.3);
		transform.setTransform(t3d);
		transform.addChild(new Echino(HEIGHT, appear));

		return transform;
	}
	
	/**
	 * Metodo che aggiunge la classe Abaco
	 * @param appear l'Appearance per l'oggetto
	 * @return il TransformGroup contenente la classe Abaco
	 */
	public TransformGroup addAbaco(Appearance appear){
		TransformGroup transform = new TransformGroup();
		Transform3D t3d = new Transform3D();
		t3d.setTranslation(new Vector3d(0.0d, (HEIGHT/2) + (HEIGHT/4.5f/2), 0.0d));
		transform.setTransform(t3d);
		transform.addChild(new Abaco(HEIGHT, appear));
		
		return transform;
	}
	
	/**
	 * Metodo che crea l'appearance 
	 * Definisce il materiale e applica la texture
	 * @return l'appearance da applicare agli oggetti
	 */
	public Appearance createApp(){
		Appearance app = new Appearance();
		
		//impostazione aspetto
		app.setTexture(texture);
		
		//impostazioni per fondere il colore dell'oggetto con la texture
		TextureAttributes tA = new TextureAttributes();
		tA.setTextureMode(TextureAttributes.MODULATE);
		app.setTextureAttributes(tA);
		
		//imposta materiale
		Material mat = new Material();
		mat.setDiffuseColor(new Color3f(0.5f, 0.4f, 0.2f));
		mat.setColorTarget(Material.DIFFUSE);
		app.setMaterial(mat);
		
		return app;
	}
}
