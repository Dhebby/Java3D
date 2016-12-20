import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;

import javax.media.j3d.Alpha;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.View;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;

public class Piramide extends Applet{
	
	public Piramide(){
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
		viewTransform.lookAt(new Point3d(1.0, 1.0, distance+2), // definisce quanti punti di fuga
							new Point3d(0.0, 0.0, 0.0), //definisce centro visuaale
							new Vector3d(0.0, 1.0, 0.0)); //su rispetto a dove guardo
		viewTransform.invert();		
		//Attivazione della trasformazione appena ricavata
		ViewingPlatform vp = simpleU.getViewingPlatform();
		TransformGroup vtg = vp.getViewPlatformTransform () ;
		vtg.setTransform (viewTransform);
		
		//Impostazione della distanza dal piano sullo sfondo
		myView.setBackClipDistance(10.0);
		//Impostazione del clip dal piano frontale
		myView.setFrontClipDistance(0.1);
		//Impostazione del campo visivo
		myView.setFieldOfView(Math.PI/4);
		//Impostazione del tipo di proiezione
		myView.setProjectionPolicy(View.PERSPECTIVE_PROJECTION);
		//myView.setProjectionPolicy(View.PARALLEL_PROJECTION);
		
		//comportamento predefinito di rotazione legata agli eventi del mouse
		MouseRotate rotateBehavior = new MouseRotate();
		//legame fra il comportamento e il TransformGroup
		rotateBehavior.setTransformGroup(vtg);
		//zona in cui tenere conto degli eventi. Sfera di raggio 1 e centro in 0
		rotateBehavior.setSchedulingBounds(new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 1.0));
		
		//creazione del sottografo principale
		BranchGroup scene = createSceneGraph();
		scene.addChild(rotateBehavior);
		simpleU.addBranchGraph(scene);
	}
	
	/**
	 * Funzione che crea il sottografo
	 * @return il BranchGroup da aggiungere al SimpleUniverse
	 */
	public BranchGroup createSceneGraph(){
		BranchGroup objRoot = new BranchGroup();
		objRoot.addChild(createSubGraph());

		return objRoot;
	}
	
	/**
	 * Funzione che aggiunge l'oggetto alla scena
	 * @return il TransformGroup da collegare al BranchGroup
	 */
	public TransformGroup createSubGraph(){
		TransformGroup objRotate = new TransformGroup(); //creo oggetto TG
		
		//rotazione automatica su se stesso
		TransformGroup objSpin = new TransformGroup();
		objSpin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		Transform3D xAxis = new Transform3D();
		xAxis.rotZ(Math.PI/3.0f);
		Alpha rotationAlpha2 = new Alpha(-1, 5000);
		RotationInterpolator rotatorX = new RotationInterpolator(
				rotationAlpha2, objSpin, xAxis, 0.0f, (float)Math.PI*2);
		BoundingSphere bounds = new BoundingSphere();
		rotatorX.setSchedulingBounds(bounds);
		objSpin.addChild(rotatorX);
		
		//objSpin.addChild(new Tronco());
		objSpin.addChild(new Maya());
		
		objRotate.addChild(objSpin);
		return objRotate;
	}
	
	public static void main(String[] args){
		new MainFrame(new Piramide(), 1024, 768);
	}
}