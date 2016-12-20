import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.View;
import javax.vecmath.Vector3d;
import javax.vecmath.Point3d;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;

public class Proiezioni extends Applet{
	//valori proiezione ortogonale
	private static final double LEFT = -1.0;
	private static final double RIGHT = 2.0;
	private static final double BOTTOM = -1.0;
	private static final double TOP = 2.0;
	private static final double NEAR = 0.1;
	private static final double FAR = 10.0;
	//valori proiezione prospettica
	private static final double FOVX = 0.25*Math.PI; //angolo di visuale
	private static final double ASPECT = 2000.0/1000.0; //width/height
	private static final double ZNEAR = 2.3;
	private static final double ZFAR = 2.9;
	
	public Proiezioni(){
		setLayout(new BorderLayout()); //layout manager del container
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
		Canvas3D canvas3D = new Canvas3D(config);
		add("Center", canvas3D);
		
		SimpleUniverse simpleU = new SimpleUniverse(canvas3D);
		
		//accedo all'oggetto view del SimpleUniverse
		View myView = simpleU.getViewer().getView();
		
		double fieldOfView = myView.getFieldOfView(); // 0.25∗Math.PI
		// Trasformazione applicata alla ViewPlarform
		Transform3D viewTransform = new Transform3D ( ) ;
		double distance = 1.0/Math.tan(fieldOfView/2.0);
		viewTransform.lookAt(new Point3d(0.0, 1.0, distance), //EYE: definisce quanti punti di fuga
							new Point3d(0.0, 0.0, 0.0), //CENTER: definisce centro visuale
							new Vector3d(0.0, 1.0, 0.0)); //UP: definisce il "su" rispetto a dove guardo
		viewTransform.invert();		
		//Attivazione della trasformazione appena ricavata
		ViewingPlatform vp = simpleU.getViewingPlatform();
		TransformGroup vtg = vp.getViewPlatformTransform () ;
		vtg.setTransform (viewTransform ) ;
		
		//Abilitazione del compatibility mode per modificare la matrice di proiezione
		myView.setCompatibilityModeEnable(true);
		
		//Creazione di una trasformazione
		Transform3D proj = new Transform3D();
		
		
		//Impostazione della matrice di proiezione ortografica
		//proj.ortho(LEFT, RIGHT, BOTTOM, TOP, NEAR, FAR);
		
		//Impostazione della matrice di proiezione prospettica
		proj.perspective(FOVX, ASPECT, ZNEAR, ZFAR);
		
		myView.setLeftProjection(proj);
		
		// definisco il BranchGroup che conterrà la scena
		BranchGroup scene = createSceneGraph();
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
	 * Funzione che aggiunge il ColorCube alla scena
	 * @return il TransformGroup da collegare al BranchGroup
	 */
	public TransformGroup createSubGraph(){
		TransformGroup transform = new TransformGroup(); //crea oggetto TG
		transform.addChild(new ColorCube(0.3)); //aggiungo al TG come figlio il cubo
		return transform;
	}
	
	public static void main(String[] args){
		new MainFrame(new Proiezioni(), 1024, 768);
	}
}