package hellofx;

import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Stopwatch {

	@FXML
	private Text hours;

	@FXML
	private Text seconds;

	@FXML
	private Text minutes;

	@FXML
	private Button start;

	@FXML
	private Button stop;

	@FXML
	private Button reset;

	Thread thrd;
	boolean state = true;

	static int h = 0;
	static int m = 0;
	static int s = 0;
	static int ms = 0;	

	@FXML
	void start(ActionEvent event) {
		TranslateTransition trl1 = new TranslateTransition();
		trl1.setDuration(Duration.millis(100));
		trl1.setToX(0);
		trl1.setToY(200);
		trl1.setNode(start);
		
		TranslateTransition trl2 = new TranslateTransition();
		trl2.setDuration(Duration.millis(100));
		trl2.setFromX(0);
		trl2.setFromY(200);
		trl2.setToX(0);
		trl2.setToY(0);
		trl2.setNode(stop);
		
		ParallelTransition pt = new ParallelTransition(trl1, trl2);
		pt.setOnFinished(e -> {
			try {
				System.out.println("Start!");
				startCountDown();
			} catch (Exception e2) {
			}
		});
		pt.play();

	}

	@FXML
	void stop(ActionEvent event) {
		TranslateTransition trl1 = new TranslateTransition();
		trl1.setDuration(Duration.millis(100));
		trl1.setToX(0);
		trl1.setToY(200);
		trl1.setNode(stop);
		TranslateTransition trl2 = new TranslateTransition();
		trl2.setDuration(Duration.millis(100));
		trl2.setFromX(0);
		trl2.setFromY(200);
		trl2.setToX(0);
		trl2.setToY(0);
		trl2.setNode(start);
		ParallelTransition pt = new ParallelTransition(trl1, trl2);
		pt.setOnFinished(e -> {
			try {
				System.out.println("Stopped");
				state = false;

			} catch (Exception e2) {
			}
		});
		pt.play();

	}

	@FXML
	void reset(ActionEvent event) {
		if(state = true)
			stop(event);
		h = 0;
		m = 0;
		s = 0;
		ms = 0;		
		
		seconds.setText("00");
		minutes.setText("00");
		hours.setText("00");
		System.out.println("Reset");
	}

	void startCountDown() {
		state = true;
		thrd = new Thread(new Runnable() {

			@Override
			public void run() {
				while (state) {
					try {
						Thread.sleep(1);

						if (ms > 550) {
							ms = 0;
							s++;
						}
						if (s > 59) {
							s = 0;
							m++;
						}
						if (m > 59) {
							ms = 0;
							s = 0;
							m = 0;
							h++;
						}
						ms++;

						if (s < 10) {
							seconds.setText("0" + s);
						} else
							seconds.setText("" + s);
						if (m < 10) {
							minutes.setText("0" + m);
						} else
							minutes.setText("" + m);
						if (h < 10) {
							hours.setText("0" + h);
						} else
							hours.setText("" + h);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		thrd.start();
	}

}