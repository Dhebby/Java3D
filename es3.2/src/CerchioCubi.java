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

public class CerchioCubi extends Applet{
	
	private static final int N_CUBI = 12;
	
	public CerchioCubi(){
		setLayout(new BorderLayout()); //layout manager del container
		//trova la miglior configurazione grafica per il sistema
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
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
		for(int i = 1; i <= N_CUBI; i++){
			node.addChild(TraslaCubo(i, N_CUBI));
		} //aggiunge gli oggetti TG come figli di BranchGroup
		return node;
	}
	
	/**
	 * Funzione che applica le trasformazioni al ColorCube
	 * @param i cubo corrente
	 * @param n numero totale di cubi
	 * @return il TransformGroup da collegare al BranchGroup
	 */
	public TransformGroup TraslaCubo(int i, int n){
		Transform3D translate = new Transform3D();
		translate.setTranslation(new Vector3d(Math.sin(2*Math.PI/n *i)/2.8, Math.cos(2*Math.PI/n *i)/2.8, 1));
		//translate.setTranslation(new Vector3d(Math.sin(2*Math.PI/n *i)/2.8, Math.cos(2*Math.PI/n *i)/2.8, i * 0.1d));
		/*
		Transform3D rotaz = new Transform3D();
		rotaz.rotY(Math.PI * i/10);
		translate.mul(rotaz);
		*/
		TransformGroup translateTG = new TransformGroup(translate);
		translateTG.addChild(new ColorCube(0.05));
		
		return translateTG;
	}
	
	public static void main(String[] args){
		new MainFrame(new CerchioCubi(), 1024, 768);
	}
}
