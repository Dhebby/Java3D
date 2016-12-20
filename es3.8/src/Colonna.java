import javax.media.j3d.Appearance;
import javax.media.j3d.Group;
import javax.media.j3d.Material;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;

public class Colonna extends Group{
	private static final float HEIGHT = 1.2f;
	public Colonna(){
		addChild(addFusto());
		addChild(addEchino());
		addChild(addAbaco());
	}
	
	/**
	 * Metodo che crea l'appearance e ne definisce il materiale
	 * @return l'appearance da applicare agli oggetti
	 */
	private Appearance createApp(){
		Appearance app = new Appearance();
		
		Material mat = new Material();
		mat.setDiffuseColor(new Color3f(0.5f, 0.4f, 0.2f));
		mat.setColorTarget(Material.DIFFUSE);
		app.setMaterial(mat);
		
		return app;
	}
	
	/**
	 * Metodo che aggiunge la classe Fusto
	 * @return il TransformGroup contenente la classe Fusto
	 */
	public TransformGroup addFusto(){
		TransformGroup transform = new TransformGroup();
		Transform3D t3d = new Transform3D();
		t3d.setTranslation(new Vector3d(0.0d, 0.0d, 0.0d));
		transform.setTransform(t3d);
		transform.addChild(new Fusto(HEIGHT, createApp()));

		return transform;
	}
	
	/**
	 * Metodo che aggiunge la classe Echino
	 * @return il TransformGroup contenente la classe Echino
	 */
	public TransformGroup addEchino(){
		TransformGroup transform = new TransformGroup();
		Transform3D t3d = new Transform3D();
		t3d.setTranslation(new Vector3d(0.0d, HEIGHT/2, 0.0d));
		t3d.setScale(HEIGHT-0.3);
		transform.setTransform(t3d);
		transform.addChild(new Echino(HEIGHT, createApp()));

		return transform;
	}
	
	/**
	 * Metodo che aggiunge la classe Abaco
	 * @return il TransformGroup contenente la classe Abaco
	 */
	public TransformGroup addAbaco(){
		TransformGroup transform = new TransformGroup();
		Transform3D t3d = new Transform3D();
		t3d.setTranslation(new Vector3d(0.0d, (HEIGHT/2) + (HEIGHT/4.5f/2), 0.0d));
		transform.setTransform(t3d);
		transform.addChild(new Abaco(HEIGHT, createApp()));
		
		return transform;
	}
}
