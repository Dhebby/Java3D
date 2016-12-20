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

public class ProiezioniView extends Applet{
	
	public ProiezioniView(){
		setLayout(new BorderLayout());
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
		Canvas3D canvas3D = new Canvas3D(config);
		add("Center", canvas3D);
		
		//creazione del SimpleUniverse
		SimpleUniverse simpleU = new SimpleUniverse(canvas3D);
		
		//accedo all'oggetto view del SimpleUniverse
		View myView = simpleU.getViewer().getView();
		
		double fieldOfView = myView.getFieldOfView(); // 0.25∗Math.PI
		// Trasformazione applicata alla ViewPlarform
		Transform3D viewTransform = new Transform3D ( ) ;
		double distance = 1.0/Math.tan(fieldOfView/2.0);
		viewTransform.lookAt(new Point3d(1.0, 1.0, distance), //EYE: definisce quanti punti di fuga
							new Point3d(0.0, 0.0, 0.0), //CENTER: definisce centro visuale
							new Vector3d(0.0, 1.0, 0.0)); //UP: definisce il "su" rispetto a dove guardo
		viewTransform.invert();		
		//Attivazione della trasformazione appena ricavata
		ViewingPlatform vp = simpleU.getViewingPlatform();
		TransformGroup vtg = vp.getViewPlatformTransform () ;
		vtg.setTransform (viewTransform);
		
		//Impostazione della distanza dal piano sullo sfondo
		myView.setBackClipDistance(0.54);
		
		//Impostazione del clip dal piano frontale
		myView.setFrontClipDistance(0.49);
		
		//Impostazione del campo visivo
		myView.setFieldOfView(-Math.PI/8);
		
		//Impostazione del tipo di proiezione
		//myView.setProjectionPolicy(View.PERSPECTIVE_PROJECTION);
		myView.setProjectionPolicy(View.PARALLEL_PROJECTION);
		
		//creazione del sottografo principale
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
		transform.addChild(new ColorCube(0.2)); //aggiungo al TG come figlio il cubo
		return transform;
	}
	
	public static void main(String[] args){
		new MainFrame(new ProiezioniView(), 1024, 768);
	}
}