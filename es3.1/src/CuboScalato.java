import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseZoom;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class CuboScalato extends Applet{
	private static final double X = 2.0;
	private static final double Y = 2.0;
	private static final double Z = 4.0;
	
	//3.0, 1.5, 1.5;
	
	public CuboScalato() {
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
		return node;
	}
	
	/**
	 * Funzione che applica la trasformazione di scalatura al ColorCube
	 * @return il TransformGroup da collegare al BranchGroup
	 */
	public TransformGroup createSubGraph(){
		TransformGroup transform = new TransformGroup(); //creo oggetto TG
		Transform3D t3d = new Transform3D(); //creo oggetto per la trasformazione
		t3d.setScale(new Vector3d(X, Y, Z)); //definisco scaling
		
		transform.setTransform(t3d); //assegno a transform la trasformazione
		transform.addChild(new ColorCube(0.1)); //aggiungo al TG come figlio il cubo
		return transform;
	}

	public static void main(String[] args){
		new MainFrame(new CuboTraslato(), 1024, 768);
	}
}