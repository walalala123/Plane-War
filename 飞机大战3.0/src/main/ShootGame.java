package main;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

//�����������
public class ShootGame extends JPanel {
	public ShootGame(int highestScore, String name){
		setHighestScore(highestScore);
		this.name = name;
	}

	public void setHighestScore(int highestScore) {
		this.highestScore = highestScore;
	}

	public static final int WIDTH = 400; // ���ڵĿ�
	public static final int HEIGHT = 654; // ���ڵĸ�
	// ��̬��Դ
	public static BufferedImage background; // ����ͼ
	public static BufferedImage start; // ��ʼͼ
	public static BufferedImage pause; // ��ͣͼ
	public static BufferedImage gameover; // ��Ϸ����ͼ
	public static BufferedImage airplane; // С�л�ͼ
	public static BufferedImage airplane2; // �ел�ͼ
	public static BufferedImage airplane3; // ��л�ͼ
	public static BufferedImage hit1_1;
	public static BufferedImage hit1_2;
	public static BufferedImage hit1_3;
	public static BufferedImage hit1_4;
	public static BufferedImage hit2_1;
	public static BufferedImage hit2_2;
	public static BufferedImage hit2_3;
	public static BufferedImage hit2_4;
	public static BufferedImage hit3_1;
	public static BufferedImage hit3_2;
	public static BufferedImage hit3_3;
	public static BufferedImage hit3_4;
	public static BufferedImage hit3_5;
	private static int count = 0;
	public static BufferedImage bee; // ����ͼ
	public static BufferedImage bee2;
	public static BufferedImage bullet; // �ӵ�ͼ
	public static BufferedImage bullet2; // �ӵ�ͼ
	public static BufferedImage hero0; // Ӣ�ۻ�0ͼ
	public static BufferedImage hero1; // Ӣ�ۻ�1ͼ
	public static String url = "jdbc:mysql://127.0.0.1:3306/planewar?" +
			"useUnicode=true&characterEncoding=UTF-8&userSSL=false&serverTimezone=GMT%2B8";
	public static String user = "root";
	public static String password = "zyh20000205";
	public static Connection con;
	public static AudioClip music;
	public static String select_sql = "select max(score) from score;";
	public  int highestScore ;
	public String name;
	//��Ϸ״̬
	public static final int START = 0;
	public static final int RUNNING = 1;
	public static final int PAUSE = 2;
	public static final int GAME_OVER = 3;
	private int state = 0; // ��ǰ״̬

	private Hero hero = new Hero(); // Ӣ�ۻ�
	private Bullet[] bullets = {}; // �ӵ�����
	private FlyingObject[] flyings = {}; // ��������

	private Timer timer;
	private int interval = 10; // ���ʱ�䣺��λ--����

	// ��̬��
	static {
		try {
			background = ImageIO.read(ShootGame.class.getResource("background.png"));
			start = ImageIO.read(ShootGame.class.getResource("start.png"));
			pause = ImageIO.read(ShootGame.class.getResource("pause.png"));
			gameover = ImageIO.read(ShootGame.class.getResource("gameover.png"));
			airplane = ImageIO.read(ShootGame.class.getResource("airplane.png"));
			airplane2 = ImageIO.read(ShootGame.class.getResource("airplane2.png"));
			airplane3 = ImageIO.read(ShootGame.class.getResource("airplane3.png"));
			bee = ImageIO.read(ShootGame.class.getResource("bee.png"));
			bee2 = ImageIO.read(ShootGame.class.getResource("bee2.png"));
			bullet = ImageIO.read(ShootGame.class.getResource("bullet.png"));
			bullet2 = ImageIO.read(ShootGame.class.getResource("bullet2.png"));
			hero0 = ImageIO.read(ShootGame.class.getResource("hero0.png"));
			hero1 = ImageIO.read(ShootGame.class.getResource("hero1.png"));
			URL musicPath = ShootGame.class.getResource("game_music.wav");
			hit1_1 = ImageIO.read(ShootGame.class.getResource("enemy1_blowup_1.png"));
			hit1_2 = ImageIO.read(ShootGame.class.getResource("enemy1_blowup_2.png"));
			hit1_3 = ImageIO.read(ShootGame.class.getResource("enemy1_blowup_3.png"));
			hit1_4 = ImageIO.read(ShootGame.class.getResource("enemy1_blowup_4.png"));
			hit2_1 = ImageIO.read(ShootGame.class.getResource("enemy2_blowup_1.png"));
			hit2_2 = ImageIO.read(ShootGame.class.getResource("enemy2_blowup_2.png"));
			hit2_3 = ImageIO.read(ShootGame.class.getResource("enemy2_blowup_3.png"));
			hit2_4 = ImageIO.read(ShootGame.class.getResource("enemy2_blowup_4.png"));
			hit3_1 = ImageIO.read(ShootGame.class.getResource("enemy3_blowup_1.png"));
			hit3_2 = ImageIO.read(ShootGame.class.getResource("enemy3_blowup_2.png"));
			hit3_3 = ImageIO.read(ShootGame.class.getResource("enemy3_blowup_3.png"));
			hit3_4 = ImageIO.read(ShootGame.class.getResource("enemy3_blowup_4.png"));
			hit3_5 = ImageIO.read(ShootGame.class.getResource("enemy3_blowup_4.png"));
			con = DriverManager.getConnection(url, user, password);
			music = Applet.newAudioClip(musicPath);
			} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static FlyingObject nextOne() {
		Random rand = new Random();
		int type = rand.nextInt(20); // ����0��19�������
		if (type == 0) { // �����Ϊ0�����ز���;���򷵻صл�
			return new Bee();
		} else {
			return new Airplane();
		}
	}

	int flyEnteredIndex = 0;

	// ���˵ǳ�
	public void enterAction() {// 10������һ��
		flyEnteredIndex++; // ÿ10������1
		if (flyEnteredIndex % 40 == 0) {
			FlyingObject obj = nextOne();
			flyings = Arrays.copyOf(flyings, flyings.length + 1);
			flyings[flyings.length - 1] = obj;// �����˸�ֵ��flyings��������һ��Ԫ��
		}
	}

	public void stepAction() { // �����Ѷ�ѡ��
		hero.step(); // Ӣ�ۻ���һ��
		int num = time1 / 15;  //�����Ѷȵȼ�
		if(num>=3)
			num = 3;
		for (FlyingObject flying : flyings) {
			for (int j = 0; j <= num; j++) {
				flying.step(); // ������һ��
			}
		}
		for (Bullet value : bullets) {
			for (int j = 0; j <= num / 2; j++) {
				value.step(); // �ӵ���һ��
			}
		}
	}

	int shootIndex = 0;

	public void shootAction() { // 10������һ��
		shootIndex++; // ÿ10������1
		if (shootIndex % 20 == 0) { // 20���뷢��һ���ӵ�
			Bullet[] bs = hero.shoot();// ��ȡ�ӵ�����
			bullets = Arrays.copyOf(bullets, bullets.length + bs.length);
			System.arraycopy(bs, 0, bullets, bullets.length - bs.length, bs.length);
		}
	}

	// ɾ��Խ�������
	public void outOfBoundsAction() {
		int index = 0;
		FlyingObject[] flyingLives = new FlyingObject[flyings.length];
		for (FlyingObject f : flyings) {
			if (!f.outOfBounds()) {
				flyingLives[index] = f;// ��Խ�磬����װ��flyingLives[]������
				index++;
			}
		}
		flyings = Arrays.copyOf(flyingLives, index);

		index = 0;
		Bullet[] bulletsLives = new Bullet[bullets.length];
		for (Bullet bs : bullets) {
			if (!bs.outOfBounds()) {
				bulletsLives[index] = bs;// ��Խ�磬����װ��flyingLives[]������
				index++;
			}
		}
		bullets = Arrays.copyOf(bulletsLives, index);
	}

	int score = 0; // �÷�
	int level=1;//�ȼ�
	static double time = 0.00;//ʱ��
	static int time1 = 0;
	// �����ӵ������е���ײ

	public void bangAction() {
		for (Bullet value : bullets) {
			bang(value);
		}
	}

	// һ���ӵ������е���ײ
	public void bang(Bullet b) {
		int index = -1;// ��ײ���˵�����
		for (int i = 0; i < flyings.length; i++) {// �������еĵ���
			if (flyings[i].shootBy(b)) {// �ж��Ƿ�ײ��
				index = i; // ��¼��ײ���˵�����
				break;
			}
		}
		if (index != -1) {// ײ����
			FlyingObject one = flyings[index];
			if (one instanceof Enemy) {
				Airplane airplane = (Airplane) one;
				// Enemy e = (Enemy) one;
				if(!airplane.ishit)
					score += airplane.getScore();
			}
			if (one instanceof Award) {
				Award a = (Award) one;
				int type = a.getType();
				switch (type) {
				case Award.DOUBLE_FIRE: // ��������ֵ
					hero.addDoubleFire(); // Ӣ�ۻ����ӻ���
					break;
				case Award.LIFE: // ������
					hero.addLife(); // Ӣ�ۻ�����
					break;
				case Award.addscore://������ֵ
					score+=20;
					break;
				}
			}
			// ��ײ������flyings�����е����һ��Ԫ�ؽ���
			FlyingObject t = flyings[index];
			flyings[index].ishit=true;
			flyings[index] = flyings[flyings.length - 1];
			flyings[flyings.length - 1] = t;
		}
	}

	public void checkGameOverAction() throws SQLException, ClassNotFoundException {
		if (isGameOver()) { // ������Ϸ
			state = GAME_OVER;
			time1 = 0;
			time = 0;
			Date date = new Date();
			SimpleDateFormat simpleDateFormat =  new SimpleDateFormat("yyyy-MM-dd");
			System.out.print(simpleDateFormat.format(date));
			String sql = "insert into score(score, time, user) values (?, ?, ?);";
			//����һ��preparedstatment����
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setObject(1,score);
			ps.setObject(2,simpleDateFormat.format(date));
			ps.setObject(3,name);
			//ִ�в������
			int rows = ps.executeUpdate();
			System.out.println(rows);
			//�ر�
			ps.close();
			highestScore=getHighestScore();
		}
	}

	public boolean isGameOver() {
		for (int i = 0; i < flyings.length; i++) { // ײ���ˣ�
			if (hero.hit(flyings[i])) {
				hero.subtractLife(); // ������1
				hero.setDoubleFire(0); // ����ֵ����

				// ��ײ֮�󣬽�������
				FlyingObject t = flyings[i];
				flyings[i] = flyings[flyings.length - 1];
				flyings[flyings.length - 1] = t;
				flyings = Arrays.copyOf(flyings, flyings.length - 1);
			}
		}
		return hero.getLife() <= 0; // Ӣ�ۻ�����<=0,��Ϸ����
	}

	// ����ִ�д���
	public void action() {

		MouseAdapter l = new MouseAdapter() {
			public void mouseMoved(MouseEvent e) {
				if (state == RUNNING) { // ����״̬��ִ��
					int x = e.getX(); // ���Y����
					int y = e.getY(); // ���X����
					hero.moveTo(x, y); // Ӣ�ۻ���������ƶ����ƶ�
				}
			}


			// ���ĵ���¼�
			public void mouseClicked(MouseEvent e) {
				switch (state) {
					case PAUSE:
						state = RUNNING;
						System.out.print("1");
						music.stop();
						music.loop();
						break;
					case RUNNING:
						System.out.print("  2");
						state = PAUSE;
						music.stop();
						break;
					case START:
						state = RUNNING;
						System.out.print("   3");
						music.stop();
						music.loop();
						System.out.print("   4");
						break;
					case GAME_OVER:
						System.out.print("   5");
						System.out.println(score);
						hero = new Hero();// �����ֳ�
						flyings = new FlyingObject[0];
						bullets = new Bullet[0];
						score = 0;
						state = START;
						music.stop();
						System.out.print("  6");
						break;
				}
			}

			public void mouseEntered(MouseEvent e) {
				if (state == PAUSE) {
					state = RUNNING;
					music.loop();
				}
			}

			public void mouseExited(MouseEvent e) {
				if (state == RUNNING) {
					state = PAUSE;
				}
			}
		};
		this.addMouseListener(l); // �����������¼�
		this.addMouseMotionListener(l);// ��������ƶ��¼�

		timer = new Timer(); // ������ʱ������
		timer.schedule(new TimerTask() {
			public void run() { // ��ʱ--10������һ��
				if (state == RUNNING) { // ����״̬��ִ��
					enterAction();
					stepAction();// ��������һ��
					shootAction();// �ӵ��볡
					outOfBoundsAction();// ɾ��Խ�������
					bangAction(); // �ӵ������ײ
					time = time + 0.01;
					time1 = (int) time;

					try {
						checkGameOverAction();  //SQL�������ݿ��ȡ��߷���
					} catch (SQLException | ClassNotFoundException throwables) {
						throwables.printStackTrace();
					}
				}
				count++;
				repaint(); // �ػ�������paint()
			}
		}, interval, interval);
			// ��ʱ��ʾ�Ѷ�
				timer.schedule(new TimerTask() {
					@Override
					public void run() {
						if (level <= 2)
							level++;
					}
				}, 15000, 15000);

	}

	// ��дpaint()���� g����ʾ����
	public void paint(Graphics g) {
		g.drawImage(background, 0, 0, null); // ������ͼ
		paintHero(g);
		paintFlyingObjects(g);
		paintBullets(g);
		try {
			paintScore(g); // ���֣�����
		} catch (SQLException | ClassNotFoundException throwables) {
			throwables.printStackTrace();
		}
		paintState(g);
	}

	// ��״̬
	public void paintState(Graphics g) {
		switch (state) {
		case START: // ����״̬������ͼ
			g.drawImage(start, 0, 0, null);
			break;
		case PAUSE: // ��ͣ״̬����ͣͼ
			g.drawImage(pause, 0, 0, null);
			break;
		case GAME_OVER: // ����״̬������ͼ
			g.drawImage(gameover, 0, 0, null);
			break;

		}
	}

	public void paintHero(Graphics g) {
		g.drawImage(hero.image, hero.x, hero.y, null); // ��Ӣ�ۻ�����
	}

	public void paintFlyingObjects(Graphics g) {
		for (int i = 0; i < flyings.length; i++) {
			FlyingObject f = flyings[i];
			g.drawImage(f.image, f.x, f.y, null);

			if (flyings[i].ishit) {

				switch (f.airplane_type) {
					case 1: {
						int boomPictureChoose = count % 20;
						if (boomPictureChoose < 5)
							g.drawImage(hit1_1, f.x, f.y, null);
						else if (boomPictureChoose < 10)
							g.drawImage(hit1_2, f.x, f.y, null);
						else if (boomPictureChoose < 15)
							g.drawImage(hit1_3, f.x, f.y, null);
						else{
							g.drawImage(hit1_4, f.x, f.y, null);
							flyings = Arrays.copyOf(flyings, flyings.length - 1);
						}
						break;
					}
					case 2: {
						int boomPictureChoose = count % 20;
						if (boomPictureChoose < 5)
							g.drawImage(hit2_1, f.x, f.y, null);
						else if (boomPictureChoose < 10)
							g.drawImage(hit2_2, f.x, f.y, null);
						else if (boomPictureChoose < 15)
							g.drawImage(hit2_3, f.x, f.y, null);
						else{
							g.drawImage(hit2_4, f.x, f.y, null);
							flyings = Arrays.copyOf(flyings, flyings.length - 1);
						}
						break;
					}
					case 3: {
						int boomPictureChoose = count % 25;
						if (boomPictureChoose < 5)
							g.drawImage(hit3_1, f.x, f.y, null);
						else if (boomPictureChoose < 10)
							g.drawImage(hit3_2, f.x, f.y, null);
						else if (boomPictureChoose < 15)
							g.drawImage(hit3_3, f.x, f.y, null);
						else if(boomPictureChoose<20)
							g.drawImage(hit3_4, f.x, f.y, null);
						else{
							g.drawImage(hit3_5, f.x, f.y, null);
							flyings = Arrays.copyOf(flyings, flyings.length - 1);
						}
						break;
					}
					default: {
						g.drawImage(hit2_4, f.x, f.y, null);
						flyings = Arrays.copyOf(flyings, flyings.length - 1);
						break;
					}
				}
			}
		}
	}


	public void paintBullets(Graphics g) {
		for (Bullet b : bullets) {
			g.drawImage(b.image, b.x, b.y, null);
		}

	}

	public int getHighestScore() throws ClassNotFoundException, SQLException {
		Statement stat;
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection(url, user, password);
		stat = con.createStatement();//�ҵ����
		String sql = "select max(score) score from score;";//��ѯ���
		ResultSet rs=stat.executeQuery(sql);//��ѯ
//����ѯ���Ľ�����
		String score = "0";
		while (rs.next()) {
			score=rs.getString("score");
		}
		rs.close();
		stat.close();
		con.close();
		return Integer.parseInt(score);
	}

	public void paintScore(Graphics g) throws SQLException, ClassNotFoundException { // ���֣�����
		//highestScore = getHighestScore();
		g.setColor(new Color(0xFF0000));
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		g.drawString("SCORE: " + score, 20, 25);
		g.drawString("LIFE: " + hero.getLife(), 20, 45);
		g.drawString("TIME:" +time1,20,65);
		g.drawString("HIGHEST: " + highestScore, 20, 85);
		g.drawString("LEVEL:"+level,280,25);
	}


	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Statement stat;
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection(url, user, password);
		stat = con.createStatement();//�ҵ����
		String sql = "select max(score) score from score;";//��ѯ���
		ResultSet rs=stat.executeQuery(sql);//��ѯ
//����ѯ���Ľ�����
		String score = "0";
		while (rs.next()) {
			score=rs.getString("score");
		}
		System.out.print(score);
		rs.close();
		stat.close();
		con.close();
		JFrame frame = new JFrame("�ɻ���ս  developed by ��զ�����а�"); // ���ڶ���
		ShootGame game = new ShootGame(Integer.parseInt(score),"zyh"); // ���
		//ShootGame game = new ShootGame();
		frame.add(game); // �������ӵ�������
    	BufferedImage image;
		try {
			image = ImageIO.read(ShootGame.class.getResource("icon.png"));
			frame.setIconImage(image);
		} catch (IOException e) {
			System.out.print(Arrays.toString(e.getStackTrace()));
		}

		frame.setSize(WIDTH, HEIGHT); // ���ô��ڵĿ�͸�
		frame.setAlwaysOnTop(true); // ����һֱ��������
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ����Ĭ�ϹرյĲ��������ڹر�ʱ�˳�����
		frame.setLocationRelativeTo(null); // ���ô��ڳ�ʼλ�ã�����)
		frame.setVisible(true); // ���ô���ɼ�

		game.action(); // ����ִ��
	}
}
