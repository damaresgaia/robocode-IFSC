package Gaia;
import robocode.*;
//import java.awt.Color;

// API help : https://robocode.sourceforge.io/docs/robocode/robocode/Robot.html

/**
 * Luiz - a robot by (your name here)
 */
public class Luiz extends Robot
{
	/**
	 * run: Luiz's default behavior
	 */
	public void run() {
		// Initialization of the robot should be put here

		// After trying out your robot, try uncommenting the import at the top,
		// and the next line:

		// setColors(Color.red,Color.blue,Color.green); // body,gun,radar

		// Robot main loop
		while(true) {
			// Girar o radar 360 graus continuamente para escanear inimigos
			turnRadarRight(360);
			
			// Movimentação básica para evitar ser alvo fácil
			ahead(100);
			back(100);
		}
	}

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		// Calcular o ângulo para alinhar o canhão na direção do inimigo
		double absoluteBearing = getHeading() + e.getBearing();
		double gunTurn = normalizeBearing(absoluteBearing - getGunHeading());
		
		// Girar o canhão para mirar no inimigo
		turnGunRight(gunTurn);
		
		// Ajustar potência do tiro dependendo da distância do inimigo
		double distance = e.getDistance();
		double firePower;
		if (distance > 200) {
			firePower = 1; // tiro fraco para economizar energia em longas distâncias
		} else if (distance > 50) {
			firePower = 2;
		} else {
			firePower = 3; // tiro forte para inimigos próximos
		}
		
		// Atirar quando o canhão estiver alinhado no alvo (pequeno erro tolerado)
		if (Math.abs(gunTurn) <= 10) {
			fire(firePower);
		}
		
		// Esquivar após atirar para dificultar que o inimigo acerte
		// Alterna entre avançar e recuar rapidamente
		if (Math.random() > 0.5) {
			ahead(50);
		} else {
			back(50);
		}
	}
	
	/**
	 * onHitByBullet: What to do when you're hit by a bullet
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		// Recuar um pouco e se mover lateralmente para evitar ser alvejado repetidamente
		back(20);
		turnRight(90);
		ahead(50);
	}
	
	/**
	 * onHitWall: What to do when you hit a wall
	 */
	public void onHitWall(HitWallEvent e) {
		// Recuar para evitar ficar preso e mudar de direção
		back(20);
		turnRight(90);
	}
	
	/**
	 * normalizeBearing: normaliza um ângulo para o intervalo entre -180 e 180 graus
	 * Isso ajuda a girar a menor distância possível para o canhão ou corpo
	 */
	private double normalizeBearing(double angle) {
		while (angle > 180) angle -= 360;
		while (angle < -180) angle += 360;
		return angle;
	}
}

