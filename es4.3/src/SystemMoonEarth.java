import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;

import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3d;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class SystemMoonEarth extends Applet{
	public SystemMoonEarth(){
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
	 * Aggiunge la luce, la texture dello sfondo, gli oggetti e la rotazione col mouse
	 * @return il BranchGroup da aggiungere al SimpleUniverse
	 */
	public BranchGroup createSceneGraph(){
		BranchGroup node = new BranchGroup();
		TransformGroup TG = new TransformGroup();
		
		TG.addChild(new Moon());
		TG.addChild(new Earth());
		
		//aggiunta luce
		node.addChild(dirLight());
		
		//sfondo
		TextureLoader sLoader = new TextureLoader("../texture/stars.jpg", this);
		ImageComponent2D image = sLoader.getImage();
		Background back = new Background();
		back.setImage(image);
		back.setImageScaleMode(Background.SCALE_FIT_MAX);
		BoundingSphere bounds = new BoundingSphere(new Point3d(), 1000.0);
		back.setApplicationBounds(bounds);
		node.addChild(back);
		
		//rotazione degli oggetti col mouse
		TG.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		TG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		MouseRotate mRot = new MouseRotate(TG);
		mRot.setSchedulingBounds(new BoundingSphere(new Point3d(), 10000.0d));
		TG.addChild(mRot);
		
		node.addChild(TG); //aggiunge l'oggetto TG come figlio di BranchGroup
		
		return node;
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
		lightD1.setDirection(-1.0f, -1.0f, -1.0f);
		//impostazione del bound
		lightD1.setInfluencingBounds(bounds);
		
		return lightD1;
	}
	
	public static void main(String[] args) {
        new MainFrame(new SystemMoonEarth(), 1920, 1080);
    }
}
