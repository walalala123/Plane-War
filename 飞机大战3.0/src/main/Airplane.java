package main;

import java.util.Random;

//Airplane----�л����Ƿ����
public class Airplane extends FlyingObject implements Enemy {
	private int speed = 2;// �л��߲��Ĳ���
	private int type;
	public Airplane() {
		Random rand = new Random();// ������ɴ���С�͵���
		int varity = rand.nextInt(20);
		if (varity <13){
			image = ShootGame.airplane;
			type = 1;
			this.airplane_type=1;
		}
		else if (varity < 17){
			image = ShootGame.airplane2;
			type = 2;
			this.airplane_type=2;
		}
		else {
			image = ShootGame.airplane3;
			type = 3;
			this.airplane_type=3;
		}
		width = image.getWidth();
		height = image.getHeight();
		x = rand.nextInt(ShootGame.WIDTH - this.width);
		y = -this.height; // y:���ĵл��ĸ�
	}

	// ��д getScore();
	@Override
	public int getScore() {
		if (type==1)
			return 5;  //С�ɻ�
		else if (type ==2)
			return 10;	//�зɻ�
		else
			return 15; //��ɻ�
	}
	public int gettype() {
		return type;
	}
	public void step() {
		y += speed;
	}

	public boolean outOfBounds() {
		return this.y > ShootGame.HEIGHT; // �л���y������ڴ��ڵĸ�
	}
}
