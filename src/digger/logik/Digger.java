package digger.logik;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Digger {

	Image digUp1;
	Image digUp2;
	Image digUp3;
	Image digUp4;
	Image digUp5;
	Image digUp6;
	Image currentImageOfDigUp;
	Image digDown1;
	Image digDown2;
	Image digDown3;
	Image digDown4;
	Image digDown5;
	Image digDown6;
	Image currentImageOfDigDown;
	Image digRight1;
	Image digRight2;
	Image digRight3;
	Image digRight4;
	Image digRight5;
	Image digRight6;
	Image currentImageOfDigRight;
	Image digLeft1;
	Image digLeft2;
	Image digLeft3;
	Image digLeft4;
	Image digLeft5;
	Image digLeft6;
	Image currentImageOfDigLeft;
	Image currentImageOfDig;
	Image fireBall1;
	Image fireBall2;
	Image fireBall3;
	Image currentImageOfFireBall;
	Image DeadDig;
	Image RIP1;
	Image RIP2;
	Image RIP3;
	Image RIP4;
	Image currentImg;
	
	String dir = "UP";

	public int diggerX = 400, diggerY = 540;
	public int fireBallX = diggerX, fireBallY = diggerY;

	public Digger() {
		digUp1 = new Image(this.getClass().getResourceAsStream("/digger/gui/extend/DigUp1.jpg"));
		digUp2 = new Image(this.getClass().getResourceAsStream("/digger/gui/extend/DigUp2.jpg"));
		digUp3 = new Image(this.getClass().getResourceAsStream("/digger/gui/extend/DigUp3.jpg"));
		digUp4 = new Image(this.getClass().getResourceAsStream("/digger/gui/extend/DigUp4.jpg"));
		digUp5 = new Image(this.getClass().getResourceAsStream("/digger/gui/extend/DigUp5.jpg"));
		digUp6 = new Image(this.getClass().getResourceAsStream("/digger/gui/extend/DigUp6.jpg"));
		currentImageOfDigUp = digUp1;
		digDown1 = new Image(this.getClass().getResourceAsStream("/digger/gui/extend/DigDown1.jpg"));
		digDown2 = new Image(this.getClass().getResourceAsStream("/digger/gui/extend/DigDown2.jpg"));
		digDown3 = new Image(this.getClass().getResourceAsStream("/digger/gui/extend/DigDown3.jpg"));
		digDown4 = new Image(this.getClass().getResourceAsStream("/digger/gui/extend/DigDown4.jpg"));
		digDown5 = new Image(this.getClass().getResourceAsStream("/digger/gui/extend/DigDown5.jpg"));
		digDown6 = new Image(this.getClass().getResourceAsStream("/digger/gui/extend/DigDown6.jpg"));
		currentImageOfDigDown = digDown1;
		digRight1 = new Image(this.getClass().getResourceAsStream("/digger/gui/extend/DigRight1.jpg"));
		digRight2 = new Image(this.getClass().getResourceAsStream("/digger/gui/extend/DigRight2.jpg"));
		digRight3 = new Image(this.getClass().getResourceAsStream("/digger/gui/extend/DigRight3.jpg"));
		digRight4 = new Image(this.getClass().getResourceAsStream("/digger/gui/extend/DigRight4.jpg"));
		digRight5 = new Image(this.getClass().getResourceAsStream("/digger/gui/extend/DigRight5.jpg"));
		digRight6 = new Image(this.getClass().getResourceAsStream("/digger/gui/extend/DigRight6.jpg"));
		currentImageOfDigRight = digRight1;
		digLeft1 = new Image(this.getClass().getResourceAsStream("/digger/gui/extend/DigLeft1.jpg"));
		digLeft2 = new Image(this.getClass().getResourceAsStream("/digger/gui/extend/DigLeft2.jpg"));
		digLeft3 = new Image(this.getClass().getResourceAsStream("/digger/gui/extend/DigLeft3.jpg"));
		digLeft4 = new Image(this.getClass().getResourceAsStream("/digger/gui/extend/DigLeft4.jpg"));
		digLeft5 = new Image(this.getClass().getResourceAsStream("/digger/gui/extend/DigLeft5.jpg"));
		digLeft6 = new Image(this.getClass().getResourceAsStream("/digger/gui/extend/DigLeft6.jpg"));
		currentImageOfDigLeft = digLeft1;
		currentImageOfDig = currentImageOfDigUp; 
		fireBall1 = new Image(this.getClass().getResourceAsStream("/digger/gui/extend/FireBall1.png"));
		fireBall2 = new Image(this.getClass().getResourceAsStream("/digger/gui/extend/FireBall2.png"));
		fireBall3 = new Image(this.getClass().getResourceAsStream("/digger/gui/extend/FireBall3.png"));
		currentImageOfFireBall = fireBall1;
		DeadDig = new Image(this.getClass().getResourceAsStream("/digger/gui/extend/DeadDig.jpg"));
		RIP1 = new Image(this.getClass().getResourceAsStream("/digger/gui/extend/RIP1.png"));
		RIP2 = new Image(this.getClass().getResourceAsStream("/digger/gui/extend/RIP2.png"));
		RIP3 = new Image(this.getClass().getResourceAsStream("/digger/gui/extend/RIP3.png"));
		RIP4 = new Image(this.getClass().getResourceAsStream("/digger/gui/extend/RIP4.png"));
		currentImg = DeadDig;

	}
	
	public void setDir(String dir1) {
		dir = dir1;
	}

	
	
	private void setImageUp() {
		if (currentImageOfDigUp.equals(digUp1)) {
			currentImageOfDigUp = digUp2;
			currentImageOfDig = currentImageOfDigUp;
//			System.out.println("Chaning Image to up2");
			return;
		}
		if (currentImageOfDigUp.equals(digUp2)) {
			currentImageOfDigUp = digUp3;
			currentImageOfDig = currentImageOfDigUp;
			return;
		}
		if (currentImageOfDigUp.equals(digUp3)) {
			currentImageOfDigUp = digUp4;
			currentImageOfDig = currentImageOfDigUp;
			return;
		}
		if (currentImageOfDigUp.equals(digUp4)) {
			currentImageOfDigUp = digUp5;
			currentImageOfDig = currentImageOfDigUp;
			return;
		}
		if (currentImageOfDigUp.equals(digUp5)) {
			currentImageOfDigUp = digUp6;
			currentImageOfDig = currentImageOfDigUp;
			return;
		}
		if (currentImageOfDigUp.equals(digUp6)) {
			currentImageOfDigUp = digUp1;
			currentImageOfDig = currentImageOfDigUp;
			return;
		}
	
	}

	private void setImageDown() {
		if (currentImageOfDigDown.equals(digDown1)) {
			currentImageOfDigDown = digDown2;
			currentImageOfDig = currentImageOfDigDown;
			return;
		}
		if (currentImageOfDigDown.equals(digDown2)) {
			currentImageOfDigDown = digDown3;
			currentImageOfDig = currentImageOfDigDown;
			return;
		}
		if (currentImageOfDigDown.equals(digDown3)) {
			currentImageOfDigDown = digDown4;
			currentImageOfDig = currentImageOfDigDown;
			return;
		}
		if (currentImageOfDigDown.equals(digDown4)) {
			currentImageOfDigDown = digDown5;
			currentImageOfDig = currentImageOfDigDown;
			return;
		}
		if (currentImageOfDigDown.equals(digDown5)) {
			currentImageOfDigDown = digDown6;
			currentImageOfDig = currentImageOfDigDown;
			return;
		}
		if (currentImageOfDigDown.equals(digDown6)) {
			currentImageOfDigDown = digDown1;
			currentImageOfDig = currentImageOfDigDown;
			return;
		}
		
	}

	private void setImageRight() {
		if (currentImageOfDigRight.equals(digRight1)) {
			currentImageOfDigRight = digRight2;
			currentImageOfDig = currentImageOfDigRight;
			return;
		}
		if (currentImageOfDigRight.equals(digRight2)) {
			currentImageOfDigRight = digRight3;
			currentImageOfDig = currentImageOfDigRight;
			return;
		}
		if (currentImageOfDigRight.equals(digRight3)) {
			currentImageOfDigRight = digRight4;
			currentImageOfDig = currentImageOfDigRight;
			return;
		}
		if (currentImageOfDigRight.equals(digRight4)) {
			currentImageOfDigRight = digRight5;
			currentImageOfDig = currentImageOfDigRight;
			return;
		}
		if (currentImageOfDigRight.equals(digRight5)) {
			currentImageOfDigRight = digRight6;
			currentImageOfDig = currentImageOfDigRight;
			return;
		}
		if (currentImageOfDigRight.equals(digRight6)) {
			currentImageOfDigRight = digRight1;
			currentImageOfDig = currentImageOfDigRight;
			return;
		}
	}

	private void setImageLeft() {

		if (currentImageOfDigLeft.equals(digLeft1)) {
			currentImageOfDigLeft = digLeft2;
			currentImageOfDig = currentImageOfDigLeft;
			return;
		}
		if (currentImageOfDigLeft.equals(digLeft2)) {
			currentImageOfDigLeft = digLeft3;
			currentImageOfDig = currentImageOfDigLeft;
			return;
		}
		if (currentImageOfDigLeft.equals(digLeft3)) {
			currentImageOfDigLeft = digLeft4;
			currentImageOfDig = currentImageOfDigLeft;
			return;
		}
		if (currentImageOfDigLeft.equals(digLeft4)) {
			currentImageOfDigLeft = digLeft5;
			currentImageOfDig = currentImageOfDigLeft;
			return;
		}
		if (currentImageOfDigLeft.equals(digLeft5)) {
			currentImageOfDigLeft = digLeft6;
			currentImageOfDig = currentImageOfDigLeft;
			return;
		}
		if (currentImageOfDigLeft.equals(digLeft6)) {
			currentImageOfDigLeft = digLeft1;
			currentImageOfDig = currentImageOfDigLeft;
			return;
		}
	}
	
	public void setFireBallImage() {
		
		
		if(currentImageOfFireBall.equals(fireBall1)) {
			currentImageOfFireBall = fireBall2;
			return;
		}
		if(currentImageOfFireBall.equals(fireBall2)) {
			currentImageOfFireBall = fireBall3;
			return;
		}
		if(currentImageOfFireBall.equals(fireBall3)) {
			currentImageOfFireBall = fireBall1;
			return;
		}

	}
	
	public void setDeadImg() {
		if(currentImg.equals(DeadDig)) {
			currentImg = RIP1;
			return;
		}
		if(currentImg.equals(RIP1)) {
			currentImg = RIP2;
			return;
		}
		if(currentImg.equals(RIP2)) {
			currentImg = RIP3;
			return;
		}
		if(currentImg.equals(RIP3)) {
			currentImg = RIP4;
			return;
		}
	}

	public void changeImage(String direction) {
		switch (direction) {
		case "UP":
			setImageUp();
			break;
		case "DOWN":
			setImageDown();
			break;
		case "RIGHT":
			setImageRight();
			break;
		case "LEFT":
			setImageLeft();
			break;

		}
	}
	public void drawFireBall(GraphicsContext gc )
    {
     
//        gc.setFill(Color.BLUE);
//        gc.fillOval(fireBallX,fireBallY , 15, 15);
		setFireBallImage();
		gc.drawImage(currentImageOfFireBall, fireBallX, fireBallY + 5, 20, 20);

    
    
    }

	public void draw(GraphicsContext gc) {
		

			gc.drawImage(currentImageOfDig, diggerX, diggerY, 30, 30);

	}
	public void drawDead(GraphicsContext gc) {
		setDeadImg();
		gc.drawImage(currentImg, diggerX, diggerY, 30, 30);
  
	}
}