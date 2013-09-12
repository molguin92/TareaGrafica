package cate.agustin.manuel;

import org.mini2Dx.core.graphics.Graphics;

import com.badlogic.gdx.math.Vector2;

/**
 * @author arachnid92
 *
 */
public interface SpaceObject {

	/**
	 * Interfaz para simplificar el manejo
	 * de objetos del juego que implementan
	 * los mismo metodos.
	 */
	
	public boolean deleteThis();
	
	public void renderObject(Graphics g);
	
	public void updatePosition(float delta);
	
	public Vector2 getPosition();
	
	public Vector2 getRealPosition();
	
	public void decIntegrity(int damage);
	
	public void incIntegrity(int life);
	
}
