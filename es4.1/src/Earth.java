import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;

import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.Material;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3d;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class Earth extends Applet{
	public Earth(){
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
	 * Aggiunge la luce, la texture dello sfondo e l'oggetto
	 * @return il BranchGroup da aggiungere al SimpleUniverse
	 */
	public BranchGroup createSceneGraph(){
		BranchGroup node = new BranchGroup();
		TransformGroup TG = new TransformGroup();
		
		Sphere earth = new Sphere(0.5f, Primitive.GENERATE_TEXTURE_COORDS | Primitive.GENERATE_NORMALS, 50, createApp());
		TG.addChild(earth);
		
		//aggiunta luce
		TG.addChild(dirLight());
		
		//applico sfondo
		TextureLoader sLoader = new TextureLoader("../texture/stars.jpg", this);
		ImageComponent2D image = sLoader.getImage();
		Background back = new Background();
		back.setImage(image);
		back.setImageScaleMode(Background.SCALE_FIT_MAX);
		BoundingSphere bounds = new BoundingSphere(new Point3d(), 1000.0);
		back.setApplicationBounds(bounds);
		
		node.addChild(back);
		node.addChild(TG); //aggiunge l'oggetto TG come figlio di BranchGroup
		
		return node;
	}
	/**
	 * Metodo che aggiunge la texture e il materiale all'appearance
	 * @return l'appearance da aggiungere all'oggetto
	 */
	public Appearance createApp(){

		Appearance appear = new Appearance();
		
		//carica la texture da file
		TextureLoader tLoader = new TextureLoader("../texture/earth.jpg", TextureLoader.GENERATE_MIPMAP, this);
		//inizializza oggetto Texture
		Texture texture = tLoader.getTexture();
		//impostazione aspetto
		appear.setTexture(texture);
		
		//impostazioni per fondere il colore dell'oggetto con la texture
		TextureAttributes tA = new TextureAttributes();
		tA.setTextureMode(TextureAttributes.MODULATE);
		appear.setTextureAttributes(tA);
		
		//impostazione materiale
		appear.setMaterial(new Material());
		
		return appear;
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
        new MainFrame(new Earth(), 800, 800);
    }
}
