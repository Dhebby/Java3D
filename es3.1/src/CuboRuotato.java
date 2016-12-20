import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class CuboRuotato extends Applet{
	private static final double X = Math.PI*0.25;
	private static final double Y = Math.PI*-0.25;
	private static final double Z = Math.PI*0.16; //30 gradi
	
	public CuboRuotato() {
		setLayout(new BorderLayout()); //layout manager del container
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration(); //trova la miglior configurazione grafica per il sistema
		Canvas3D canvas3D = new Canvas3D(config); //si occupa del rendering 3D on-screen e off-screen
		add("Center", canvas3D);
		
		// definisco il BranchGroup che conterrà la scena
		BranchGroup scene = createSceneGraph();
		scene.compile();
		
		// definisco il SimpleUniverse e vi collego in BranchGroup creato
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
	 * Funzione che applica la trasformazione di rotazione al ColorCube
	 * @return il TransformGroup da collegare al BranchGroup
	 */
	public TransformGroup createSubGraph(){
		TransformGroup transform = new TransformGroup(); //creo oggettp TG
		
		Transform3D t3d = new Transform3D(); // creo oggetto per la trasformazione
		t3d.rotX(X); //definisco rotazione su x
		
		Transform3D t3dY = new Transform3D();
		t3dY.rotY(Y); //definisco rotazione su y
		
		Transform3D t3dZ = new Transform3D();
		t3dZ.rotZ(Z); //definisco rotazione su z
		
		//combino le tre trasformazioni
		t3d.mul(t3dY);
		t3d.mul(t3dZ);
		
		transform.setTransform(t3d); //assegno al TG la trasformazione
		transform.addChild(new ColorCube(0.3)); //aggiungo al TG come figlio il cubo
		return transform;
	}
	
	public static void main(String[] args){
		new MainFrame(new CuboTraslato(), 1024, 768);
	}
	
}
