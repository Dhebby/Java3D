import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3d;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class CuboTraslato extends Applet{
	private static final double X = 0.5;
	private static final double Y = -0.5;
	private static final double Z = -1.0;
	
	public CuboTraslato() {
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
	 * Funzione che applica la trasformazione di traslazione al ColorCube
	 * @return il TransformGroup da collegare al BranchGroup
	 */
	public TransformGroup createSubGraph(){
		TransformGroup transform = new TransformGroup(); //creo oggetto TG
		
		Transform3D t3d = new Transform3D(); //creo oggetto per la trasformazione
		t3d.setTranslation(new Vector3d(X, Y, Z)); //definisco la traslazione
		/*
		Transform3D t3ds = new Transform3D();
		t3ds.setScale(new Vector3d(1.5d, 1.0d, 3.0d));
		Transform3D t3dr = new Transform3D();
		t3dr.rotY(Math.PI*0.16);
		t3d.mul(t3ds);
		t3d.mul(t3dr);
		*/
		transform.setTransform(t3d); //assegno a transform la trasformazione
		transform.addChild(new ColorCube(0.3)); //aggiungo al TG come figlio il cubo
		return transform;
	}

	public static void main(String[] args){
		new MainFrame(new CuboTraslato(), 1024, 768);
	}
}
