import javax.media.j3d.Alpha;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Group;
import javax.media.j3d.Material;
import javax.media.j3d.RotPosPathInterpolator;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.AxisAngle4f;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;
import javax.vecmath.Quat4f;

import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;

public class Moon extends Group{
	private static final int STEPS = 100; //numero di knots
	public Moon(){
		Sphere moon = new Sphere(0.1f, Primitive.GENERATE_TEXTURE_COORDS | Primitive.GENERATE_NORMALS, 50, createApp());
		
		//Crea un gruppo per le trasformazioni affini
		TransformGroup target = new TransformGroup();
		
		//Crea un timer
		Alpha alpha = new Alpha(-1, 10000);
		
		//Crea asse di rotazione/posizione
		Transform3D axisOfRotPos = new Transform3D();
		AxisAngle4f axis = new AxisAngle4f(1.0f, 0.0f, 0.0f, 0.0f);
		axisOfRotPos.set(axis);
		
		//creo i nodi per l'animazione
		float[] knots = new float[STEPS];
		//creo i quaternioni
		Quat4f[] quats = new Quat4f[STEPS];
		//creo le posizioni
	    Point3f[] positions = new Point3f[STEPS];
	    
	    //riempio gli array
	    for(int i=0; i<STEPS; i++){
	    	knots[i] = (float)i/(float)STEPS;
	    	
	    	quats[i] = new Quat4f();
	    	
	    	double angle = 2.0*Math.PI*(double)i/(double)STEPS;
			float x = (float)Math.sin(angle);
			float z = (float)Math.cos(angle);
			positions[i] = new Point3f(2*x/3, 0.0f, z);	    	
		}
	    //riempio l'ultimo elemento degli array
	    knots[STEPS-1] = 1.0f;
	    quats[STEPS-1] = quats[0];
	    positions[STEPS-1] = positions[0];
		
		//Imposta la capacità di scrivere la trasformazione
		target.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		
		//Crea un interpolatore per le rotazioni e le posizioni collegato con il gruppo di trasformazione
		RotPosPathInterpolator rotPosPath = new RotPosPathInterpolator(alpha, target, axisOfRotPos, knots, quats, positions);
		//Imposta il raggio d'azione del behavior
		rotPosPath.setSchedulingBounds(new BoundingSphere());
		//Aggiunge l'interpolatore
		addChild(rotPosPath);
		
		target.addChild(moon);
		addChild(target);
		
	}
	
	/**
	 * Metodo che crea l'appearance
	 * Aggiunge la texture e il materiale
	 * @return l'appearance da applicare agli oggetti
	 */
	public Appearance createApp(){

		Appearance appear = new Appearance();
		
		//carica la texture da file
		TextureLoader tLoader = new TextureLoader("../texture/moon.jpg", TextureLoader.GENERATE_MIPMAP, null);
		//inizializza oggetto Texture
		Texture texture = tLoader.getTexture();
		//impostazione aspetto
		appear.setTexture(texture);
		
		//impostazioni per fondere il colore dell'oggetto con la texture
		TextureAttributes tA = new TextureAttributes();
		tA.setTextureMode(TextureAttributes.MODULATE);
		appear.setTextureAttributes(tA);
		
		//impostazione materiale
		Material mat = new Material();
		mat.setDiffuseColor(new Color3f(1.0f, 1.0f, 1.0f));
		mat.setColorTarget(Material.DIFFUSE);
		appear.setMaterial(mat);
		
		return appear;
	}
}
