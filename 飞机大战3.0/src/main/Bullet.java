package main;
//�ӵ�--������
class Bullet extends FlyingObject{
	private int speed = 6;  //�ӵ��߲�������ֻ��y�����ڱ�
	private int speed2 = 10;
	public Bullet(int x,int y){//�ӵ�����������Ӣ�ۻ��ı仯���仯		
		image = ShootGame.bullet;
		width = image.getWidth();
		height = image.getHeight();
		this.x = x;
		this.y = y;
	}
    public void step(){
		y -= speed;
	}
    public boolean outOfBounds(){
		return this.y < -this.height;
	}
}

class Bullet2 extends FlyingObject{
	private int speed = 10;  //�ӵ��߲�������ֻ��y�����ڱ�
	public Bullet2(int x,int y){//�ӵ�����������Ӣ�ۻ��ı仯���仯
		image = ShootGame.bullet2;
		width = image.getWidth();
		height = image.getHeight();
		this.x = x;
		this.y = y;
	}
	@Override
	public void step() {
		y-=speed;
	}

	@Override
	public boolean outOfBounds() {
		{
			return this.y < -this.height;
	}
}
}