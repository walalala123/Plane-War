package main;
import java.util.Random;
//Be---������Ʒ  ���Ƿ����Ҳ�ܻ�ȡ����
/*
* award���ƶ��켣��
* */
public class Bee extends FlyingObject implements Award{
	private int xSpeed = 1;     //x�����߲�����
	private int ySpeed = 2;     //y�����߲�����
	private int awardType;      //��������
	
	public Bee(){
		Random rand = new Random();// ������ɲ�ͬ����
		image = ShootGame.bee;
		width = image.getWidth();
		height = image.getHeight();
		x = rand.nextInt(ShootGame.WIDTH - this.width);
	    y = -this.height; 
		awardType = rand.nextInt(3);//������ɽ�������
	}
	
	public int getType(){
		return awardType;
	}
    public void step(){   	
    	if(x >= ShootGame.WIDTH - this.width){
    		xSpeed = -1;
    	}
    	if(x <= 0){
    		xSpeed = 1;
    	}
    	x += xSpeed;
    	y += ySpeed;		
	}
    public boolean outOfBounds(){
    	return this.y > ShootGame.HEIGHT;  
	}
}
