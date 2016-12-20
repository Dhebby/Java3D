import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class ShowColonna extends Applet{
	public ShowColonna(){
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
		TransformGroup TG = createSubGraph();
		node.addChild(TG); //aggiunge l'oggetto TG come figlio di BranchGroup
		
		node.addChild(dirLight());
		node.addChild(ambLight());
		
		return node;
	}
	
	/**
	 * Applicazione della rotazione del mouse e agiunta della colonna alla scena
	 * @return il TransformGroup da collegare al BranchGroup
	 */
	public TransformGroup createSubGraph(){
		TransformGroup transform = new TransformGroup(); //creo oggettp TG
		
		//rotazione mouse
		transform.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		transform.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		MouseRotate mRot = new MouseRotate(transform);
		mRot.setSchedulingBounds(new BoundingSphere(new Point3d(), 10000.0d));
		transform.addChild(mRot);
		
		transform.addChild(new Colonna());
		
		return transform;
	}
	
	/**
	 * Metodo che crea la luce direzionale
	 * @return la DirectionalLight da applicare alla scena
	 */
	private DirectionalLight dirLight(){
		//creazione del bound
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0d, 0.0d, 0.0d), 50.0d);
		//creazione della luce direzionale
		DirectionalLight lightD1 = new DirectionalLight();
		//imposto la direzione del fascio di luce
		lightD1.setDirection(-1.0f, 0.0f, -1.0f);
		lightD1.setColor(new Color3f(1.0f, 1.0f, 1.0f));
		//impostazione del bound
		lightD1.setInfluencingBounds(bounds);
		
		return lightD1;
	}
	
	/**
	 * Metodo che crea la luce ambientale
	 * @return l'AmbientLight da applicare alla scena
	 */
	private AmbientLight ambLight(){
		//la reazione del bound definisce o spazio dell'illuminazione
		//mi dice quali sono gli oggetti che posso eliminare
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0d, 0.0d, 0.0d), 10.0d);
		//creazione di una sorgente di luce
		AmbientLight lightP1 = new AmbientLight();
		Color3f color = new Color3f(1.0f, 1.0f, 1.0f);
		lightP1.setColor(color);
		lightP1.setInfluencingBounds(bounds);
		
		return lightP1;
	}
	
	public static void main(String[] args){
		new MainFrame(new ShowColonna(), 1024, 768);
	}
}
