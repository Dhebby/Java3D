import javax.media.j3d.Alpha;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Group;
import javax.media.j3d.Material;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.AxisAngle4d;
import javax.vecmath.Color3f;

import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;

public class Earth extends Group{
	public Earth(){
		Sphere earth = new Sphere(0.4f, Primitive.GENERATE_TEXTURE_COORDS | Primitive.GENERATE_NORMALS, 50, createApp());
		
		//Crea un gruppo per le trasformazioni affini
		TransformGroup TG = new TransformGroup();
		//Imposta la capacità di scrivere la trasformazione
		TG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		//Crea un timer
		Alpha alpha = new Alpha(-1, 8000);
		
		//Crea un interpolatore per le rotazioni collegato con il gruppo di trasformazione
		RotationInterpolator rotator = new RotationInterpolator(alpha, TG);
		
		/*
		//rotazione sull'asse delle z
		Transform3D rotZ = new Transform3D();
		rotZ.setRotation(new AxisAngle4d(1,0,0,Math.PI/2));
		RotationInterpolator rotator = new RotationInterpolator(alpha, TG, rotZ, 0.0f, (float)(2*Math.PI));
		*/
		
		//Imposta il raggio d'azione del behavior
		rotator.setSchedulingBounds(new BoundingSphere());
		//Aggiunge l'interpolatore al gruppo di trasformazione
		TG.addChild(rotator);
		
		TG.addChild(earth);
		addChild(TG);
		
	}
	
	/**
	 * Metodo che crea l'appearance
	 * Aggiunge la texture e il materiale
	 * @return l'appearance da applicare agli oggetti
	 */
	public Appearance createApp(){

		Appearance appear = new Appearance();
		
		//carica la texture da file
		TextureLoader tLoader = new TextureLoader("../texture/earth.jpg", TextureLoader.GENERATE_MIPMAP, null);
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
