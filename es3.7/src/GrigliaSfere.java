import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Material;
import javax.media.j3d.PointLight;
import javax.media.j3d.SpotLight;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class GrigliaSfere extends Applet{
	private static final int MATR_DIM = 5;
	
	public GrigliaSfere(){
		setLayout(new BorderLayout());
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
		Canvas3D canvas3D = new Canvas3D(config);
		add("Center", canvas3D);
		BranchGroup scene = createSceneGraph();
		scene.compile();
		
		SimpleUniverse simpleU = new SimpleUniverse(canvas3D);
		simpleU.getViewingPlatform().setNominalViewingTransform();
		simpleU.addBranchGraph(scene);
	}
	
	/**
	 * Funzione che crea il sottografo
	 * @return il BranchGroup da aggiungere al SimpleUniverse
	 */
	public BranchGroup createSceneGraph(){
		BranchGroup node = new BranchGroup();
		Appearance appearance = createApp();
		
		//creo ma matrice di sfere
		for (int i=0; i<MATR_DIM; i++){
			for (int j=0; j<MATR_DIM; j++){
				TransformGroup transform = new TransformGroup();
				Transform3D t3d = new Transform3D();
				t3d.setTranslation(new Vector3d((i-MATR_DIM/2)*0.3, (j-MATR_DIM/2)*0.3, 0.0f));
				transform.setTransform(t3d);
				
				Sphere sphere = new Sphere(0.1f);
				sphere.setAppearance(appearance);
				
				transform.addChild(sphere);
				node.addChild(transform);
				
			}
		}
		
		//Aggiunta delle luci alla scena
		node.addChild(ambLight());
		node.addChild(dirLight());
		node.addChild(pLight());
		node.addChild(sLight());
		
		return node;
	}
	
	/**
	 * Metodo che crea la luce ambientale
	 * @return l'AmbientLight da applicare alla scena
	 */
	private AmbientLight ambLight(){
		//la reazione del bound definisce lo spazio dell'illuminazione
		//mi dice quali sono gli oggetti che posso eliminare
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0d, 0.0d, 0.0d), 1.0d);
		//creazione di una sorgente di luce
		AmbientLight lightP1 = new AmbientLight();
		Color3f color = new Color3f(0.0f, 0.0f, 1.0f);
		lightP1.setColor(color);
		lightP1.setInfluencingBounds(bounds);
		
		return lightP1;
	}
	
	/**
	 * Metodo che crea la luce direzionale
	 * @return la DirectionalLight da applicare alla scena
	 */
	private DirectionalLight dirLight(){
		//creazione del bound
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0d, 0.0d, 0.0d), 0.3d); //aggiustando l'ultimo numero tra 0 e 1 regolo il raggio d'azione sulle sfere
		//creazione della luce direzionale
		DirectionalLight lightD1 = new DirectionalLight();
		//imposto la direzione del fascio di luce
		lightD1.setDirection(1.0f, 1.0f, -1.0f);
		//impostazione del bound
		lightD1.setInfluencingBounds(bounds);
		
		return lightD1;
	}
	
	/**
	 * Metodo che crea la luce puntuale
	 * @return la PointLight da applicare alla scena
	 */
	private PointLight pLight(){
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0d, 0.0d, 0.0d), 0.5d);
		PointLight lightP1 = new PointLight(
				new Color3f(1.0f, 0.0f, 0.0f), //color
				new Point3f(1.0f,1.0f,1.0f), //position
				new Point3f(1.0f, 1.0f, 0.0f)); //attenuation
		
		lightP1.setInfluencingBounds(bounds);
		
		return lightP1;
	}
	
	/**
	 * Metodo che crea la luce puntuale
	 * @return la SpotLight da applicare alla scena
	 */
	private SpotLight sLight(){
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0d, 0.0d, 0.0d), 10.0d);
		SpotLight lightS1 = new SpotLight(
				new Color3f(0.0f, 1.0f, 0.0f), //color
				new Point3f(1.0f,-1.0f,0.6f), //position
				new Point3f(1.0f, 0.0f, 0.0f), //attenuation
				new Vector3f(1.0f, 1.0f, 1.0f), //direction
				(float)(Math.PI), //spreadAngle
				5f); //concentration
		lightS1.setInfluencingBounds(bounds);
		
		return lightS1;
	}
	
	/**
	 * Metodo che definisce l'appearance per gli oggetti
	 * @return l'Appearance da applicare agli oggetti
	 */
	private Appearance createApp(){
		Appearance app = new Appearance();
		Material mat = new Material();
		app.setMaterial(mat);
		
		return app;
	}
	
	public static void main(String[] args){
		new MainFrame(new GrigliaSfere(), 1024, 768);
	}
}
